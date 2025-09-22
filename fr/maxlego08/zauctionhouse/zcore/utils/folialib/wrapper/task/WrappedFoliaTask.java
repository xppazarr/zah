/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task;
/*    */ 
/*    */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class WrappedFoliaTask implements WrappedTask {
/*    */   private static final Class<? extends ScheduledTask> ASYNC_TASK_CLASS;
/*    */   private final ScheduledTask task;
/*    */   private final boolean async;
/*    */   
/*    */   static {
/* 12 */     Class<?> clazz = null;
/*    */     
/*    */     try {
/* 15 */       clazz = Class.forName("io.papermc.paper.threadedregions.scheduler.FoliaAsyncScheduler.AsyncScheduledTask");
/* 16 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */ 
/*    */     
/* 19 */     ASYNC_TASK_CLASS = (Class)clazz;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public WrappedFoliaTask(ScheduledTask paramScheduledTask) {
/* 27 */     this.task = paramScheduledTask;
/*    */     
/* 29 */     if (ASYNC_TASK_CLASS == null) { this.async = false; }
/* 30 */     else { this.async = ASYNC_TASK_CLASS.isAssignableFrom(paramScheduledTask.getClass()); }
/*    */   
/*    */   }
/*    */   
/*    */   public void cancel() {
/* 35 */     this.task.cancel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 40 */     return this.task.isCancelled();
/*    */   }
/*    */ 
/*    */   
/*    */   public Plugin getOwningPlugin() {
/* 45 */     return this.task.getOwningPlugin();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAsync() {
/* 50 */     return this.async;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\wrapper\task\WrappedFoliaTask.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */