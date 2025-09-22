/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util;
/*    */ 
/*    */ import java.sql.SQLTransientException;
/*    */ import java.util.concurrent.Semaphore;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SuspendResumeLock
/*    */ {
/* 32 */   public static final SuspendResumeLock FAUX_LOCK = new SuspendResumeLock(false)
/*    */     {
/*    */       public void acquire() {}
/*    */ 
/*    */ 
/*    */       
/*    */       public void release() {}
/*    */ 
/*    */       
/*    */       public void suspend() {}
/*    */ 
/*    */       
/*    */       public void resume() {}
/*    */     };
/*    */ 
/*    */   
/*    */   private static final int MAX_PERMITS = 10000;
/*    */   
/*    */   private final Semaphore acquisitionSemaphore;
/*    */ 
/*    */   
/*    */   public SuspendResumeLock() {
/* 54 */     this(true);
/*    */   }
/*    */ 
/*    */   
/*    */   private SuspendResumeLock(boolean paramBoolean) {
/* 59 */     this.acquisitionSemaphore = paramBoolean ? new Semaphore(10000, true) : null;
/*    */   }
/*    */ 
/*    */   
/*    */   public void acquire() {
/* 64 */     if (this.acquisitionSemaphore.tryAcquire()) {
/*    */       return;
/*    */     }
/* 67 */     if (Boolean.getBoolean("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.throwIfSuspended")) {
/* 68 */       throw new SQLTransientException("The pool is currently suspended and configured to throw exceptions upon acquisition");
/*    */     }
/*    */     
/* 71 */     this.acquisitionSemaphore.acquireUninterruptibly();
/*    */   }
/*    */ 
/*    */   
/*    */   public void release() {
/* 76 */     this.acquisitionSemaphore.release();
/*    */   }
/*    */ 
/*    */   
/*    */   public void suspend() {
/* 81 */     this.acquisitionSemaphore.acquireUninterruptibly(10000);
/*    */   }
/*    */ 
/*    */   
/*    */   public void resume() {
/* 86 */     this.acquisitionSemaphore.release(10000);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikar\\util\SuspendResumeLock.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */