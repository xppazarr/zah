/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util;
/*     */ 
/*     */ import java.lang.ref.WeakReference;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.CopyOnWriteArrayList;
/*     */ import java.util.concurrent.SynchronousQueue;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import java.util.concurrent.locks.LockSupport;
/*     */ import java.util.stream.Collectors;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ConcurrentBag<T extends ConcurrentBag.IConcurrentBagEntry>
/*     */   implements AutoCloseable
/*     */ {
/*  61 */   private static final Logger LOGGER = LoggerFactory.getLogger(ConcurrentBag.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final CopyOnWriteArrayList<T> sharedList;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final boolean weakThreadLocals;
/*     */ 
/*     */ 
/*     */   
/*     */   private final ThreadLocal<List<Object>> threadList;
/*     */ 
/*     */ 
/*     */   
/*     */   private final IBagStateListener listener;
/*     */ 
/*     */ 
/*     */   
/*     */   private final AtomicInteger waiters;
/*     */ 
/*     */ 
/*     */   
/*     */   private volatile boolean closed;
/*     */ 
/*     */ 
/*     */   
/*     */   private final SynchronousQueue<T> handoffQueue;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ConcurrentBag(IBagStateListener paramIBagStateListener) {
/*  97 */     this.listener = paramIBagStateListener;
/*  98 */     this.weakThreadLocals = useWeakThreadLocals();
/*     */     
/* 100 */     this.handoffQueue = new SynchronousQueue<>(true);
/* 101 */     this.waiters = new AtomicInteger();
/* 102 */     this.sharedList = new CopyOnWriteArrayList<>();
/* 103 */     if (this.weakThreadLocals) {
/* 104 */       this.threadList = ThreadLocal.withInitial(() -> new ArrayList(16));
/*     */     } else {
/*     */       
/* 107 */       this.threadList = ThreadLocal.withInitial(() -> new FastList(IConcurrentBagEntry.class, 16));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T borrow(long paramLong, TimeUnit paramTimeUnit) {
/* 123 */     List<Object> list = this.threadList.get(); int i;
/* 124 */     for (i = list.size() - 1; i >= 0; i--) {
/* 125 */       WeakReference weakReference = (WeakReference)list.remove(i);
/*     */       
/* 127 */       IConcurrentBagEntry iConcurrentBagEntry = this.weakThreadLocals ? ((WeakReference<IConcurrentBagEntry>)weakReference).get() : (IConcurrentBagEntry)weakReference;
/* 128 */       if (iConcurrentBagEntry != null && iConcurrentBagEntry.compareAndSet(0, 1)) {
/* 129 */         return (T)iConcurrentBagEntry;
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 134 */     i = this.waiters.incrementAndGet();
/*     */     try {
/* 136 */       for (IConcurrentBagEntry iConcurrentBagEntry : this.sharedList) {
/* 137 */         if (iConcurrentBagEntry.compareAndSet(0, 1)) {
/*     */           
/* 139 */           if (i > 1) {
/* 140 */             this.listener.addBagItem(i - 1);
/*     */           }
/* 142 */           return (T)iConcurrentBagEntry;
/*     */         } 
/*     */       } 
/*     */       
/* 146 */       this.listener.addBagItem(i);
/*     */       
/* 148 */       paramLong = paramTimeUnit.toNanos(paramLong);
/*     */       do {
/* 150 */         long l = ClockSource.currentTime();
/* 151 */         IConcurrentBagEntry iConcurrentBagEntry = (IConcurrentBagEntry)this.handoffQueue.poll(paramLong, TimeUnit.NANOSECONDS);
/* 152 */         if (iConcurrentBagEntry == null || iConcurrentBagEntry.compareAndSet(0, 1)) {
/* 153 */           return (T)iConcurrentBagEntry;
/*     */         }
/*     */         
/* 156 */         paramLong -= ClockSource.elapsedNanos(l);
/* 157 */       } while (paramLong > 10000L);
/*     */       
/* 159 */       return null;
/*     */     } finally {
/*     */       
/* 162 */       this.waiters.decrementAndGet();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void requite(T paramT) {
/* 177 */     paramT.setState(0);
/*     */     
/* 179 */     for (byte b = 0; this.waiters.get() > 0; b++) {
/* 180 */       if (paramT.getState() != 0 || this.handoffQueue.offer(paramT)) {
/*     */         return;
/*     */       }
/* 183 */       if ((b & 0xFF) == 255) {
/* 184 */         LockSupport.parkNanos(TimeUnit.MICROSECONDS.toNanos(10L));
/*     */       } else {
/*     */         
/* 187 */         Thread.yield();
/*     */       } 
/*     */     } 
/*     */     
/* 191 */     List list = this.threadList.get();
/* 192 */     if (list.size() < 50) {
/* 193 */       list.add(this.weakThreadLocals ? new WeakReference<>(paramT) : paramT);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void add(T paramT) {
/* 204 */     if (this.closed) {
/* 205 */       LOGGER.info("ConcurrentBag has been closed, ignoring add()");
/* 206 */       throw new IllegalStateException("ConcurrentBag has been closed, ignoring add()");
/*     */     } 
/*     */     
/* 209 */     this.sharedList.add(paramT);
/*     */ 
/*     */     
/* 212 */     while (this.waiters.get() > 0 && paramT.getState() == 0 && !this.handoffQueue.offer(paramT)) {
/* 213 */       Thread.yield();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean remove(T paramT) {
/* 228 */     if (!paramT.compareAndSet(1, -1) && !paramT.compareAndSet(-2, -1) && !this.closed) {
/* 229 */       LOGGER.warn("Attempt to remove an object from the bag that was not borrowed or reserved: {}", paramT);
/* 230 */       return false;
/*     */     } 
/*     */     
/* 233 */     boolean bool = this.sharedList.remove(paramT);
/* 234 */     if (!bool && !this.closed) {
/* 235 */       LOGGER.warn("Attempt to remove an object from the bag that does not exist: {}", paramT);
/*     */     }
/*     */     
/* 238 */     ((List)this.threadList.get()).remove(paramT);
/*     */     
/* 240 */     return bool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void close() {
/* 249 */     this.closed = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> values(int paramInt) {
/* 263 */     List<?> list = (List)this.sharedList.stream().filter(paramIConcurrentBagEntry -> (paramIConcurrentBagEntry.getState() == paramInt)).collect(Collectors.toList());
/* 264 */     Collections.reverse(list);
/* 265 */     return (List)list;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<T> values() {
/* 279 */     return (List<T>)this.sharedList.clone();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean reserve(T paramT) {
/* 296 */     return paramT.compareAndSet(0, -2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void unreserve(T paramT) {
/* 308 */     if (paramT.compareAndSet(-2, 0)) {
/*     */       
/* 310 */       while (this.waiters.get() > 0 && !this.handoffQueue.offer(paramT)) {
/* 311 */         Thread.yield();
/*     */       }
/*     */     } else {
/*     */       
/* 315 */       LOGGER.warn("Attempt to relinquish an object to the bag that was not reserved: {}", paramT);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWaitingThreadCount() {
/* 327 */     return this.waiters.get();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCount(int paramInt) {
/* 338 */     byte b = 0;
/* 339 */     for (IConcurrentBagEntry iConcurrentBagEntry : this.sharedList) {
/* 340 */       if (iConcurrentBagEntry.getState() == paramInt) {
/* 341 */         b++;
/*     */       }
/*     */     } 
/* 344 */     return b;
/*     */   }
/*     */ 
/*     */   
/*     */   public int[] getStateCounts() {
/* 349 */     int[] arrayOfInt = new int[6];
/* 350 */     for (IConcurrentBagEntry iConcurrentBagEntry : this.sharedList) {
/* 351 */       arrayOfInt[iConcurrentBagEntry.getState()] = arrayOfInt[iConcurrentBagEntry.getState()] + 1;
/*     */     }
/* 353 */     arrayOfInt[4] = this.sharedList.size();
/* 354 */     arrayOfInt[5] = this.waiters.get();
/*     */     
/* 356 */     return arrayOfInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int size() {
/* 366 */     return this.sharedList.size();
/*     */   }
/*     */ 
/*     */   
/*     */   public void dumpState() {
/* 371 */     this.sharedList.forEach(paramIConcurrentBagEntry -> LOGGER.info(paramIConcurrentBagEntry.toString()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private boolean useWeakThreadLocals() {
/*     */     try {
/* 384 */       if (System.getProperty("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.useWeakReferences") != null) {
/* 385 */         return Boolean.getBoolean("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.useWeakReferences");
/*     */       }
/*     */       
/* 388 */       return (getClass().getClassLoader() != ClassLoader.getSystemClassLoader());
/*     */     }
/* 390 */     catch (SecurityException securityException) {
/* 391 */       return true;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static interface IBagStateListener {
/*     */     void addBagItem(int param1Int);
/*     */   }
/*     */   
/*     */   public static interface IConcurrentBagEntry {
/*     */     public static final int STATE_NOT_IN_USE = 0;
/*     */     public static final int STATE_IN_USE = 1;
/*     */     public static final int STATE_REMOVED = -1;
/*     */     public static final int STATE_RESERVED = -2;
/*     */     
/*     */     boolean compareAndSet(int param1Int1, int param1Int2);
/*     */     
/*     */     void setState(int param1Int);
/*     */     
/*     */     int getState();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikar\\util\ConcurrentBag.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */