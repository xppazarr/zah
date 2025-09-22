/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*    */ import fr.maxlego08.zauctionhouse.dto.TransactionDTO;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SelectTransactionsRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final IStorage iStorage;
/*    */   private final StorageManager manager;
/*    */   
/*    */   public SelectTransactionsRunnable(IConnection paramIConnection, IStorage paramIStorage, StorageManager paramStorageManager) {
/* 24 */     this.connection = paramIConnection;
/* 25 */     this.iStorage = paramIStorage;
/* 26 */     this.manager = paramStorageManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 31 */     this.manager.setReady(false);
/*    */     
/* 33 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 34 */     List list = requestHelper.select("%prefix%transactions", TransactionDTO.class, paramSchema -> {
/*    */         
/*    */         });
/* 37 */     this.iStorage.saveTransactions((List)list.stream().map(fr.maxlego08.zauctionhouse.transaction.ZTransaction::new).collect(Collectors.toList()));
/*    */     
/* 39 */     Logger.info("Loading of transactions successfully completed.", Logger.LogType.INFO);
/* 40 */     this.manager.setReady(true);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\SelectTransactionsRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */