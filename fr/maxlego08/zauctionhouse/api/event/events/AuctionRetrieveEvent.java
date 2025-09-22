/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionRetrieveEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final boolean isAdmin;
/*    */   private final Player player;
/*    */   private final AuctionItem auctionItem;
/*    */   private long expireAt;
/*    */   
/*    */   public AuctionRetrieveEvent(boolean paramBoolean, Player paramPlayer, AuctionItem paramAuctionItem, long paramLong) {
/* 22 */     this.isAdmin = paramBoolean;
/* 23 */     this.player = paramPlayer;
/* 24 */     this.auctionItem = paramAuctionItem;
/* 25 */     this.expireAt = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isAdmin() {
/* 32 */     return this.isAdmin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 39 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 46 */     return this.auctionItem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getExpireAt() {
/* 53 */     return this.expireAt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExpireAt(long paramLong) {
/* 61 */     this.expireAt = paramLong;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionRetrieveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */