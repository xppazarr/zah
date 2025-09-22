/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionTransactionEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Transaction transaction;
/*    */   
/*    */   public AuctionTransactionEvent(Transaction paramTransaction) {
/* 15 */     this.transaction = paramTransaction;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Transaction getTransaction() {
/* 22 */     return this.transaction;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionTransactionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */