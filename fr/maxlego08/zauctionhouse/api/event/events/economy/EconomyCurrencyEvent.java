/*    */ package fr.maxlego08.zauctionhouse.api.event.events.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ 
/*    */ @Deprecated
/*    */ public class EconomyCurrencyEvent
/*    */   extends AuctionEvent {
/*  8 */   private String currency = "$";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCurrency() {
/* 18 */     return this.currency;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setCurrency(String paramString) {
/* 26 */     this.currency = paramString;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\economy\EconomyCurrencyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */