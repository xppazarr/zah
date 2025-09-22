/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.EconomyManager;
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ 
/*    */ public class AuctionLoadEconomyEvent
/*    */   extends AuctionEvent {
/*    */   private final AuctionPlugin plugin;
/*    */   private final EconomyManager economyManager;
/*    */   
/*    */   public AuctionLoadEconomyEvent(AuctionPlugin paramAuctionPlugin, EconomyManager paramEconomyManager) {
/* 13 */     this.plugin = paramAuctionPlugin;
/* 14 */     this.economyManager = paramEconomyManager;
/*    */   }
/*    */   
/*    */   public AuctionPlugin getPlugin() {
/* 18 */     return this.plugin;
/*    */   }
/*    */   
/*    */   public EconomyManager getEconomyManager() {
/* 22 */     return this.economyManager;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionLoadEconomyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */