/*    */ package fr.maxlego08.zauctionhouse.storage;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*    */ import fr.maxlego08.zscheduler.api.Implementation;
/*    */ import fr.maxlego08.zscheduler.api.Scheduler;
/*    */ import fr.maxlego08.zscheduler.api.SchedulerManager;
/*    */ import java.io.IOException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZAHImplementation
/*    */   implements Implementation
/*    */ {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final StorageManager manager;
/*    */   
/*    */   public ZAHImplementation(ZAuctionPlugin paramZAuctionPlugin) {
/* 21 */     this.plugin = paramZAuctionPlugin;
/* 22 */     this.manager = paramZAuctionPlugin.getStorageManager();
/*    */   }
/*    */   
/*    */   public void register() {
/* 26 */     SchedulerManager schedulerManager = (SchedulerManager)this.plugin.getProvider(SchedulerManager.class);
/* 27 */     schedulerManager.registerImplementation(this);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 32 */     return "ZAUCTIOHOUSE";
/*    */   }
/*    */ 
/*    */   
/*    */   public void schedule(Scheduler paramScheduler) {
/*    */     try {
/* 38 */       this.manager.createBackup();
/* 39 */     } catch (IOException iOException) {
/* 40 */       iOException.printStackTrace();
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\storage\ZAHImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */