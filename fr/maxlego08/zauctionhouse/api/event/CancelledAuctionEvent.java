/*    */ package fr.maxlego08.zauctionhouse.api.event;
/*    */ 
/*    */ import org.bukkit.event.Cancellable;
/*    */ 
/*    */ 
/*    */ public class CancelledAuctionEvent
/*    */   extends AuctionEvent
/*    */   implements Cancellable
/*    */ {
/*    */   private boolean cancelled;
/*    */   
/*    */   public boolean isCancelled() {
/* 13 */     return this.cancelled;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCancelled(boolean paramBoolean) {
/* 21 */     this.cancelled = paramBoolean;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\CancelledAuctionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */