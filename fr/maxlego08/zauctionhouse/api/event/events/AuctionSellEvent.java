/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import fr.maxlego08.zauctionhouse.api.messages.IMessage;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionSellEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final AuctionItem auctionItem;
/*    */   private final long price;
/*    */   private final Player player;
/*    */   private final AuctionEconomy economy;
/*    */   private IMessage message;
/*    */   
/*    */   public AuctionSellEvent(AuctionItem paramAuctionItem, long paramLong, Player paramPlayer, AuctionEconomy paramAuctionEconomy, IMessage paramIMessage) {
/* 27 */     this.auctionItem = paramAuctionItem;
/* 28 */     this.price = paramLong;
/* 29 */     this.player = paramPlayer;
/* 30 */     this.economy = paramAuctionEconomy;
/* 31 */     this.message = paramIMessage;
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
/*    */   public long getPrice() {
/* 45 */     return this.price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 52 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionEconomy getEconomy() {
/* 59 */     return this.economy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public IMessage getMessage() {
/* 66 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessage(IMessage paramIMessage) {
/* 74 */     this.message = paramIMessage;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionSellEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */