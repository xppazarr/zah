/*    */ package fr.maxlego08.zauctionhouse.listener;
/*    */ 
/*    */ import me.arcaniax.hdb.api.DatabaseLoadEvent;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class HeadListener
/*    */   implements Listener
/*    */ {
/*    */   private final Runnable runnable;
/*    */   
/*    */   public HeadListener(Runnable paramRunnable) {
/* 17 */     this.runnable = paramRunnable;
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onReady(DatabaseLoadEvent paramDatabaseLoadEvent) {
/* 22 */     this.runnable.run();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\listener\HeadListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */