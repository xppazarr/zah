/*    */ package fr.maxlego08.zauctionhouse.listener;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPreSellEvent;
/*    */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionSellEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*    */ 
/*    */ public class AuctionDupeListener
/*    */   implements Listener
/*    */ {
/*    */   @EventHandler(ignoreCancelled = true)
/*    */   public void onPreCommand(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent) {
/* 16 */     Player player = paramPlayerCommandPreprocessEvent.getPlayer();
/* 17 */     if (!player.isOnline() || player.isDead()) {
/* 18 */       paramPlayerCommandPreprocessEvent.setCancelled(true);
/*    */     }
/*    */   }
/*    */   
/*    */   @EventHandler(ignoreCancelled = true)
/*    */   public void onPreSell(AuctionPreSellEvent paramAuctionPreSellEvent) {
/* 24 */     handle(paramAuctionPreSellEvent.getPlayer(), (CancelledAuctionEvent)paramAuctionPreSellEvent);
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler(ignoreCancelled = true)
/*    */   public void onSell(AuctionSellEvent paramAuctionSellEvent) {
/* 30 */     handle(paramAuctionSellEvent.getPlayer(), (CancelledAuctionEvent)paramAuctionSellEvent);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void handle(Player paramPlayer, CancelledAuctionEvent paramCancelledAuctionEvent) {
/* 39 */     if (!paramPlayer.isOnline() || paramPlayer.isDead())
/* 40 */       paramCancelledAuctionEvent.setCancelled(true); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\listener\AuctionDupeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */