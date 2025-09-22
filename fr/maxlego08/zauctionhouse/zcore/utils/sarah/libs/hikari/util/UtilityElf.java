/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util;
/*     */ 
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.util.Locale;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.RejectedExecutionHandler;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ public final class UtilityElf
/*     */ {
/*     */   public static String getNullIfEmpty(String paramString) {
/*  43 */     return (paramString == null) ? null : (paramString.trim().isEmpty() ? null : paramString.trim());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void quietlySleep(long paramLong) {
/*     */     try {
/*  54 */       Thread.sleep(paramLong);
/*     */     }
/*  56 */     catch (InterruptedException interruptedException) {
/*     */       
/*  58 */       Thread.currentThread().interrupt();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean safeIsAssignableFrom(Object paramObject, String paramString) {
/*     */     try {
/*  70 */       Class<?> clazz = Class.forName(paramString);
/*  71 */       return clazz.isAssignableFrom(paramObject.getClass());
/*  72 */     } catch (ClassNotFoundException classNotFoundException) {
/*  73 */       return false;
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
/*     */   public static <T> T createInstance(String paramString, Class<T> paramClass, Object... paramVarArgs) {
/*  89 */     if (paramString == null) {
/*  90 */       return null;
/*     */     }
/*     */     
/*     */     try {
/*  94 */       Class<?> clazz = UtilityElf.class.getClassLoader().loadClass(paramString);
/*  95 */       if (paramVarArgs.length == 0) {
/*  96 */         return paramClass.cast(clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
/*     */       }
/*     */       
/*  99 */       Class[] arrayOfClass = new Class[paramVarArgs.length];
/* 100 */       for (byte b = 0; b < paramVarArgs.length; b++) {
/* 101 */         arrayOfClass[b] = paramVarArgs[b].getClass();
/*     */       }
/* 103 */       Constructor<?> constructor = clazz.getConstructor(arrayOfClass);
/* 104 */       return paramClass.cast(constructor.newInstance(paramVarArgs));
/*     */     }
/* 106 */     catch (Exception exception) {
/* 107 */       throw new RuntimeException(exception);
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
/*     */   public static ThreadPoolExecutor createThreadPoolExecutor(int paramInt, String paramString, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler) {
/* 122 */     if (paramThreadFactory == null) {
/* 123 */       paramThreadFactory = new DefaultThreadFactory(paramString, true);
/*     */     }
/*     */     
/* 126 */     LinkedBlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue(paramInt);
/* 127 */     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS, linkedBlockingQueue, paramThreadFactory, paramRejectedExecutionHandler);
/* 128 */     threadPoolExecutor.allowCoreThreadTimeOut(true);
/* 129 */     return threadPoolExecutor;
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
/*     */   public static ThreadPoolExecutor createThreadPoolExecutor(BlockingQueue<Runnable> paramBlockingQueue, String paramString, ThreadFactory paramThreadFactory, RejectedExecutionHandler paramRejectedExecutionHandler) {
/* 143 */     if (paramThreadFactory == null) {
/* 144 */       paramThreadFactory = new DefaultThreadFactory(paramString, true);
/*     */     }
/*     */     
/* 147 */     ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 5L, TimeUnit.SECONDS, paramBlockingQueue, paramThreadFactory, paramRejectedExecutionHandler);
/* 148 */     threadPoolExecutor.allowCoreThreadTimeOut(true);
/* 149 */     return threadPoolExecutor;
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
/*     */   public static int getTransactionIsolation(String paramString) {
/* 164 */     if (paramString != null) {
/*     */       
/*     */       try {
/* 167 */         String str = paramString.toUpperCase(Locale.ENGLISH);
/* 168 */         return IsolationLevel.valueOf(str).getLevelId();
/* 169 */       } catch (IllegalArgumentException illegalArgumentException) {
/*     */         
/*     */         try {
/* 172 */           int i = Integer.parseInt(paramString);
/* 173 */           for (IsolationLevel isolationLevel : IsolationLevel.values()) {
/* 174 */             if (isolationLevel.getLevelId() == i) {
/* 175 */               return isolationLevel.getLevelId();
/*     */             }
/*     */           } 
/*     */           
/* 179 */           throw new IllegalArgumentException("Invalid transaction isolation value: " + paramString);
/*     */         }
/* 181 */         catch (NumberFormatException numberFormatException) {
/* 182 */           throw new IllegalArgumentException("Invalid transaction isolation value: " + paramString, numberFormatException);
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 187 */     return -1;
/*     */   }
/*     */   
/*     */   public static final class DefaultThreadFactory
/*     */     implements ThreadFactory {
/*     */     private final String threadName;
/*     */     private final boolean daemon;
/*     */     
/*     */     public DefaultThreadFactory(String param1String, boolean param1Boolean) {
/* 196 */       this.threadName = param1String;
/* 197 */       this.daemon = param1Boolean;
/*     */     }
/*     */ 
/*     */     
/*     */     public Thread newThread(Runnable param1Runnable) {
/* 202 */       Thread thread = new Thread(param1Runnable, this.threadName);
/* 203 */       thread.setDaemon(this.daemon);
/* 204 */       return thread;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikar\\util\UtilityElf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */