/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AuctionAdminRemoveEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private final AuctionItem auctionItem;
/*    */   private final boolean isSilent;
/*    */   private final boolean isForceRemove;
/*    */   
/*    */   public AuctionAdminRemoveEvent(Player paramPlayer, AuctionItem paramAuctionItem, boolean paramBoolean1, boolean paramBoolean2) {
/* 16 */     this.player = paramPlayer;
/* 17 */     this.auctionItem = paramAuctionItem;
/* 18 */     this.isSilent = paramBoolean1;
/* 19 */     this.isForceRemove = paramBoolean2;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 23 */     return this.player;
/*    */   }
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 27 */     return this.auctionItem;
/*    */   }
/*    */   
/*    */   public boolean isSilent() {
/* 31 */     return this.isSilent;
/*    */   }
/*    */   
/*    */   public boolean isForceRemove() {
/* 35 */     return this.isForceRemove;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionAdminRemoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */