/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.scheduler.BukkitScheduler;
/*    */ import org.bukkit.scheduler.BukkitTask;
/*    */ 
/*    */ public class WrappedLegacyBukkitTask
/*    */   implements WrappedTask {
/*    */   private final BukkitTask task;
/*    */   
/*    */   public WrappedLegacyBukkitTask(BukkitTask paramBukkitTask) {
/* 13 */     this.task = paramBukkitTask;
/*    */   }
/*    */ 
/*    */   
/*    */   public void cancel() {
/* 18 */     this.task.cancel();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isCancelled() {
/* 24 */     int i = this.task.getTaskId();
/* 25 */     BukkitScheduler bukkitScheduler = Bukkit.getScheduler();
/* 26 */     return (!bukkitScheduler.isCurrentlyRunning(i) && !bukkitScheduler.isQueued(i));
/*    */   }
/*    */ 
/*    */   
/*    */   public Plugin getOwningPlugin() {
/* 31 */     return this.task.getOwner();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isAsync() {
/* 36 */     return !this.task.isSync();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\wrapper\task\WrappedLegacyBukkitTask.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */