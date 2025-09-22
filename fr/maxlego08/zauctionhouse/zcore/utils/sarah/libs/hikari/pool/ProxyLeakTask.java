/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*    */ 
/*    */ import java.util.concurrent.ScheduledExecutorService;
/*    */ import java.util.concurrent.ScheduledFuture;
/*    */ import java.util.concurrent.TimeUnit;
/*    */ import org.slf4j.Logger;
/*    */ import org.slf4j.LoggerFactory;
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
/*    */ class ProxyLeakTask
/*    */   implements Runnable
/*    */ {
/* 34 */   private static final Logger LOGGER = LoggerFactory.getLogger(ProxyLeakTask.class);
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
/* 45 */   static final ProxyLeakTask NO_LEAK = new ProxyLeakTask()
/*    */     {
/*    */       void schedule(ScheduledExecutorService param1ScheduledExecutorService, long param1Long) {}
/*    */ 
/*    */       
/*    */       public void run() {}
/*    */       
/*    */       public void cancel() {}
/*    */     };
/*    */   
/*    */   private ScheduledFuture<?> scheduledFuture;
/*    */   private String connectionName;
/*    */   
/*    */   ProxyLeakTask(PoolEntry paramPoolEntry) {
/* 59 */     this.exception = new Exception("Apparent connection leak detected");
/* 60 */     this.threadName = Thread.currentThread().getName();
/* 61 */     this.connectionName = paramPoolEntry.connection.toString();
/*    */   }
/*    */   private Exception exception;
/*    */   private String threadName;
/*    */   private boolean isLeaked;
/*    */   
/*    */   private ProxyLeakTask() {}
/*    */   
/*    */   void schedule(ScheduledExecutorService paramScheduledExecutorService, long paramLong) {
/* 70 */     this.scheduledFuture = paramScheduledExecutorService.schedule(this, paramLong, TimeUnit.MILLISECONDS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 77 */     this.isLeaked = true;
/*    */     
/* 79 */     StackTraceElement[] arrayOfStackTraceElement1 = this.exception.getStackTrace();
/* 80 */     StackTraceElement[] arrayOfStackTraceElement2 = new StackTraceElement[arrayOfStackTraceElement1.length - 5];
/* 81 */     System.arraycopy(arrayOfStackTraceElement1, 5, arrayOfStackTraceElement2, 0, arrayOfStackTraceElement2.length);
/*    */     
/* 83 */     this.exception.setStackTrace(arrayOfStackTraceElement2);
/* 84 */     LOGGER.warn("Connection leak detection triggered for {} on thread {}, stack trace follows", new Object[] { this.connectionName, this.threadName, this.exception });
/*    */   }
/*    */ 
/*    */   
/*    */   void cancel() {
/* 89 */     this.scheduledFuture.cancel(false);
/* 90 */     if (this.isLeaked)
/* 91 */       LOGGER.info("Previously reported leaked connection {} on thread {} was returned to the pool (unleaked)", this.connectionName, this.threadName); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyLeakTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */