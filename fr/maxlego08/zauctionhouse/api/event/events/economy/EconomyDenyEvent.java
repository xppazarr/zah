/*    */ package fr.maxlego08.zauctionhouse.api.event.events.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class EconomyDenyEvent
/*    */   extends AuctionEvent
/*    */ {
/*    */   private String message;
/*    */   
/*    */   public String getMessage() {
/* 18 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessage(String paramString) {
/* 26 */     this.message = paramString;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\economy\EconomyDenyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */