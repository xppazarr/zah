/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class AuctionPostAdminRemoveEvent
/*    */   extends AuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private final AuctionItem auctionItem;
/*    */   private final boolean isSilent;
/*    */   private final boolean isForceRemove;
/*    */   
/*    */   public AuctionPostAdminRemoveEvent(Player paramPlayer, AuctionItem paramAuctionItem, boolean paramBoolean1, boolean paramBoolean2) {
/* 17 */     this.player = paramPlayer;
/* 18 */     this.auctionItem = paramAuctionItem;
/* 19 */     this.isSilent = paramBoolean1;
/* 20 */     this.isForceRemove = paramBoolean2;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 24 */     return this.player;
/*    */   }
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 28 */     return this.auctionItem;
/*    */   }
/*    */   
/*    */   public boolean isSilent() {
/* 32 */     return this.isSilent;
/*    */   }
/*    */   
/*    */   public boolean isForceRemove() {
/* 36 */     return this.isForceRemove;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionPostAdminRemoveEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */