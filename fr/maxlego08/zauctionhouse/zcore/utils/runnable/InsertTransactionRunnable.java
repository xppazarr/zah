/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.Objects;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ public class InsertTransactionRunnable
/*    */   implements Runnable {
/*    */   private final IConnection connection;
/*    */   private final Transaction transaction;
/* 14 */   private final int tryAmount = 0;
/*    */   
/*    */   private final Consumer<Transaction> consumer;
/*    */ 
/*    */   
/*    */   public InsertTransactionRunnable(IConnection paramIConnection, Transaction paramTransaction, Consumer<Transaction> paramConsumer) {
/* 20 */     this.connection = paramIConnection;
/* 21 */     this.transaction = paramTransaction;
/* 22 */     this.consumer = paramConsumer;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 38 */     Objects.requireNonNull(this.transaction); requestHelper.insert("%prefix%transactions", paramSchema -> { paramSchema.uuid("seller", this.transaction.getSeller()); paramSchema.uuid("buyer", this.transaction.getBuyer()); paramSchema.string("itemstack", this.transaction.getItemStack()); paramSchema.bigInt("transaction_date", this.transaction.getDate()); paramSchema.bigInt("price", this.transaction.getPrice()); paramSchema.string("economy", this.transaction.getEconomy()); paramSchema.bool("is_read", this.transaction.isRead()); paramSchema.bool("need_money", this.transaction.needMoney()); }this.transaction::setID);
/*    */     
/* 40 */     if (this.consumer != null)
/* 41 */       this.consumer.accept(this.transaction); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\InsertTransactionRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */