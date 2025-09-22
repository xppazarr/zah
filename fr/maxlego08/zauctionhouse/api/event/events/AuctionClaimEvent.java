/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
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
/*    */ 
/*    */ public class AuctionClaimEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private final Transaction transaction;
/*    */   private long price;
/*    */   private AuctionEconomy economy;
/*    */   
/*    */   public AuctionClaimEvent(Player paramPlayer, Transaction paramTransaction, long paramLong, AuctionEconomy paramAuctionEconomy) {
/* 24 */     this.player = paramPlayer;
/* 25 */     this.transaction = paramTransaction;
/* 26 */     this.price = paramLong;
/* 27 */     this.economy = paramAuctionEconomy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 34 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transaction getTransaction() {
/* 41 */     return this.transaction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getPrice() {
/* 48 */     return this.price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionEconomy getEconomy() {
/* 55 */     return this.economy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPrice(long paramLong) {
/* 63 */     this.price = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setEconomy(AuctionEconomy paramAuctionEconomy) {
/* 71 */     this.economy = paramAuctionEconomy;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionClaimEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */