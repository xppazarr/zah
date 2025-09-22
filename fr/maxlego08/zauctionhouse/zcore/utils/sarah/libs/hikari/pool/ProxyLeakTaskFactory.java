/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*    */ 
/*    */ import java.util.concurrent.ScheduledExecutorService;
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
/*    */ class ProxyLeakTaskFactory
/*    */ {
/*    */   private ScheduledExecutorService executorService;
/*    */   private long leakDetectionThreshold;
/*    */   
/*    */   ProxyLeakTaskFactory(long paramLong, ScheduledExecutorService paramScheduledExecutorService) {
/* 34 */     this.executorService = paramScheduledExecutorService;
/* 35 */     this.leakDetectionThreshold = paramLong;
/*    */   }
/*    */ 
/*    */   
/*    */   ProxyLeakTask schedule(PoolEntry paramPoolEntry) {
/* 40 */     return (this.leakDetectionThreshold == 0L) ? ProxyLeakTask.NO_LEAK : scheduleNewTask(paramPoolEntry);
/*    */   }
/*    */ 
/*    */   
/*    */   void updateLeakDetectionThreshold(long paramLong) {
/* 45 */     this.leakDetectionThreshold = paramLong;
/*    */   }
/*    */   
/*    */   private ProxyLeakTask scheduleNewTask(PoolEntry paramPoolEntry) {
/* 49 */     ProxyLeakTask proxyLeakTask = new ProxyLeakTask(paramPoolEntry);
/* 50 */     proxyLeakTask.schedule(this.executorService, this.leakDetectionThreshold);
/*    */     
/* 52 */     return proxyLeakTask;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyLeakTaskFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */