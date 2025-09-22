/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionPostBuyEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private final AuctionItem auctionItem;
/*    */   private final Transaction transaction;
/*    */   
/*    */   public AuctionPostBuyEvent(Player paramPlayer, AuctionItem paramAuctionItem, Transaction paramTransaction) {
/* 22 */     this.player = paramPlayer;
/* 23 */     this.auctionItem = paramAuctionItem;
/* 24 */     this.transaction = paramTransaction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 31 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 38 */     return this.auctionItem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transaction getTransaction() {
/* 45 */     return this.transaction;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionPostBuyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */