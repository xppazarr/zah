/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ 
/*    */ public class DeleteTransactionRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final Transaction transaction;
/*    */   
/*    */   public DeleteTransactionRunnable(IConnection paramIConnection, Transaction paramTransaction) {
/* 15 */     this.connection = paramIConnection;
/* 16 */     this.transaction = paramTransaction;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 22 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/*    */     
/* 24 */     requestHelper.delete("%prefix%transactions", paramSchema -> paramSchema.where("id", Integer.valueOf(this.transaction.getID())));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\DeleteTransactionRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */