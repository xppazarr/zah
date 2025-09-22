/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task;
/*    */ 
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ public class WrappedBukkitTask
/*    */   implements WrappedTask {
/*    */   private final BukkitTask task;
/*    */   
/*    */   public WrappedBukkitTask(BukkitTask paramBukkitTask) {
/* 11 */     this.task = paramBukkitTask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 16 */     this.task.cancel();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 21 */     return this.task.isCancelled();
/*    */   }
/*    */ 
/*    */   
/*    */   public Plugin getOwningPlugin() {
/* 26 */     return this.task.getOwner();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAsync() {
/* 31 */     return !this.task.isSync();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\wrapper\task\WrappedBukkitTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */