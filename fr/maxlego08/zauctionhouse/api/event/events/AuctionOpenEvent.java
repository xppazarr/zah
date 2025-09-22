/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class AuctionOpenEvent
/*    */   extends CancelledAuctionEvent {
/*    */   private final Player player;
/*    */   
/*    */   public AuctionOpenEvent(Player paramPlayer) {
/* 11 */     this.player = paramPlayer;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 15 */     return this.player;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionOpenEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */