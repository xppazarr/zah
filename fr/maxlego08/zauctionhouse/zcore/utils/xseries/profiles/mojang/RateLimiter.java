/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang;
/*     */ 
/*     */ import com.google.errorprone.annotations.CanIgnoreReturnValue;
/*     */ import java.time.Duration;
/*     */ import java.util.Iterator;
/*     */ import java.util.concurrent.ConcurrentLinkedQueue;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ public final class RateLimiter
/*     */ {
/*  58 */   private final ConcurrentLinkedQueue<Long> requests = new ConcurrentLinkedQueue<>();
/*     */   private final int maxRequests;
/*     */   private final long per;
/*     */   
/*     */   RateLimiter(int paramInt, Duration paramDuration) {
/*  63 */     this.maxRequests = paramInt;
/*  64 */     this.per = paramDuration.toMillis();
/*     */   }
/*     */ 
/*     */   
/*     */   @CanIgnoreReturnValue
/*     */   private ConcurrentLinkedQueue<Long> getRequests() {
/*  70 */     if (this.requests.isEmpty()) return this.requests;
/*     */ 
/*     */ 
/*     */     
/*  74 */     long l = System.currentTimeMillis();
/*  75 */     Iterator<Long> iterator = this.requests.iterator();
/*  76 */     while (iterator.hasNext()) {
/*  77 */       long l1 = ((Long)iterator.next()).longValue();
/*  78 */       long l2 = l - l1;
/*  79 */       if (l2 > this.per) iterator.remove();
/*     */     
/*     */     } 
/*     */     
/*  83 */     return this.requests;
/*     */   }
/*     */   
/*     */   public int getRemainingRequests() {
/*  87 */     return Math.max(0, this.maxRequests - getRequests().size());
/*     */   }
/*     */   
/*     */   public int getEffectiveRequestsCount() {
/*  91 */     return getRequests().size();
/*     */   }
/*     */   
/*     */   public void instantRateLimit() {
/*  95 */     long l = System.currentTimeMillis();
/*  96 */     for (byte b = 0; b < getRemainingRequests(); b++) {
/*  97 */       this.requests.add(Long.valueOf(l));
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean acquire() {
/* 102 */     if (getRemainingRequests() <= 0) {
/* 103 */       return false;
/*     */     }
/* 105 */     this.requests.add(Long.valueOf(System.currentTimeMillis()));
/* 106 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public Duration timeUntilNextFreeRequest() {
/* 111 */     if (getRemainingRequests() == 0) {
/* 112 */       long l1 = System.currentTimeMillis();
/* 113 */       long l2 = ((Long)this.requests.peek()).longValue();
/* 114 */       long l3 = l1 - l2;
/* 115 */       return Duration.ofMillis(this.per - l3);
/*     */     } 
/* 117 */     return Duration.ZERO;
/*     */   }
/*     */   
/*     */   public synchronized void acquireOrWait() {
/* 121 */     long l = timeUntilNextFreeRequest().toMillis();
/* 122 */     if (l == 0L)
/*     */       return;  try {
/* 124 */       Thread.sleep(l);
/* 125 */     } catch (InterruptedException interruptedException) {
/* 126 */       throw new IllegalStateException("RateLimiter lock was interrupted unexpectedly", interruptedException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 132 */     return getClass().getSimpleName() + "[total=" + 
/* 133 */       getRequests().size() + ", remaining=" + 
/* 134 */       getRemainingRequests() + ", maxRequests=" + this.maxRequests + ", per=" + this.per + ']';
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\mojang\RateLimiter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */