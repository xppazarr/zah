/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionRemoveEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private final AuctionItem auctionItem;
/*    */   private final StorageType type;
/*    */   
/*    */   public AuctionRemoveEvent(Player paramPlayer, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 22 */     this.player = paramPlayer;
/* 23 */     this.auctionItem = paramAuctionItem;
/* 24 */     this.type = paramStorageType;
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
/*    */   public StorageType getType() {
/* 38 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 46 */     return this.auctionItem;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionRemoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */