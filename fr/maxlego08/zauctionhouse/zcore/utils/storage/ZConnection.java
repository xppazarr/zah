/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.storage;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.DeleteRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.DeleteTransactionRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.InsertRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.InsertTransactionRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectClaimMoneyRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectClaimMoneySyncRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectPlayerRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectTransactionsRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectUnReadMoneyRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.SelectsRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.UpdateRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.UpdateTransactionRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.runnable.UpsertOptionRunnable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*     */ import java.sql.Connection;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class ZConnection
/*     */   implements IConnection
/*     */ {
/*  37 */   private final ExecutorService service = Executors.newFixedThreadPool(10);
/*     */   private final DatabaseConnection databaseConnection;
/*     */   private final RequestHelper requestHelper;
/*     */   
/*     */   public ZConnection(DatabaseConnection paramDatabaseConnection, RequestHelper paramRequestHelper) {
/*  42 */     this.databaseConnection = paramDatabaseConnection;
/*  43 */     this.requestHelper = paramRequestHelper;
/*     */   }
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  48 */     return this.databaseConnection.getConnection();
/*     */   }
/*     */ 
/*     */   
/*     */   public RequestHelper getRequestHelper() {
/*  53 */     return this.requestHelper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncInsert(AuctionItem paramAuctionItem, StorageType paramStorageType, String paramString, Consumer<AuctionItem> paramConsumer) {
/*  58 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new InsertRunnable(this, paramAuctionItem, paramStorageType, paramString, paramConsumer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncDelete(AuctionItem paramAuctionItem, StorageType paramStorageType, Runnable paramRunnable) {
/*  63 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new DeleteRunnable(this, paramAuctionItem.getUniqueId(), paramStorageType, paramRunnable)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectItems(AuctionPlugin paramAuctionPlugin, IStorage paramIStorage, StorageManager paramStorageManager) {
/*  68 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new SelectsRunnable(paramAuctionPlugin, this, paramIStorage, paramStorageManager)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncInsert(Transaction paramTransaction, Consumer<Transaction> paramConsumer) {
/*  73 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new InsertTransactionRunnable(this, paramTransaction, paramConsumer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void selectTransactions(AuctionPlugin paramAuctionPlugin, IStorage paramIStorage, StorageManager paramStorageManager) {
/*  78 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new SelectTransactionsRunnable(this, paramIStorage, paramStorageManager)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncUpdate(AuctionItem paramAuctionItem, StorageType paramStorageType) {
/*  83 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new UpdateRunnable(this, paramAuctionItem, paramStorageType)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncUpdate(List<Transaction> paramList) {
/*  88 */     getAndRefreshConnection(() -> {
/*     */           ZConnection zConnection = this;
/*     */           this.service.execute(());
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getAndRefreshConnection(Runnable paramRunnable) {
/* 101 */     if (!this.databaseConnection.isValid()) {
/* 102 */       this.service.submit(() -> {
/*     */             this.databaseConnection.connect();
/*     */             paramRunnable.run();
/*     */           });
/*     */     } else {
/* 107 */       paramRunnable.run();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void asyncDelete(Transaction paramTransaction) {
/* 114 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new DeleteTransactionRunnable(this, paramTransaction)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fetchClaimMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {
/* 119 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new SelectClaimMoneyRunnable(this, paramPlayer, paramConsumer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void fetchUnreadMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {
/* 124 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new SelectUnReadMoneyRunnable(this, paramPlayer, paramConsumer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public long fetchClaimMoneySync(UUID paramUUID, String paramString) {
/* 129 */     if (!this.databaseConnection.isValid()) {
/* 130 */       this.databaseConnection.connect();
/*     */     }
/* 132 */     return (new SelectClaimMoneySyncRunnable(this, paramUUID, paramString)).fetchMoney();
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncSelect(UUID paramUUID, Consumer<PlayerCache> paramConsumer) {
/* 137 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new SelectPlayerRunnable(this, paramUUID, paramConsumer)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void asyncUpsert(UUID paramUUID, PlayerCache paramPlayerCache) {
/* 142 */     getAndRefreshConnection(() -> this.service.execute((Runnable)new UpsertOptionRunnable(this, paramUUID, paramPlayerCache)));
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\storage\ZConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */