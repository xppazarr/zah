/*     */ package fr.maxlego08.zauctionhouse.storage.storages;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionItemExpireEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.CustomStorageProvider;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.migrations.CreateItemMigration;
/*     */ import fr.maxlego08.zauctionhouse.migrations.CreateOptionMigration;
/*     */ import fr.maxlego08.zauctionhouse.migrations.CreateTransactionMigration;
/*     */ import fr.maxlego08.zauctionhouse.migrations.UpdateItemAddPriority;
/*     */ import fr.maxlego08.zauctionhouse.migrations.UpdateItemAddServerNameMigration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.GlobalDatabaseConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.HikariDatabaseConnection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.MigrationManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.SqliteConnection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.JULogger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.storage.ZConnection;
/*     */ import fr.traqueur.zauctionhouse.redis.sync.ZSyncManager;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class SqlStorage extends ZUtils implements IStorage {
/*  54 */   private final Map<UUID, PlayerCache> playerCaches = new HashMap<>();
/*     */   private final Storage storage;
/*  56 */   private List<AuctionItem> items = new ArrayList<>();
/*  57 */   private List<AuctionItem> buyingItems = new ArrayList<>();
/*  58 */   private List<AuctionItem> expiredItems = new ArrayList<>();
/*  59 */   private List<Transaction> transactions = new ArrayList<>();
/*     */   
/*     */   private IConnection iConnection;
/*     */   
/*     */   private DatabaseConnection databaseConnection;
/*     */   private RequestHelper requestHelper;
/*  65 */   private Map<UUID, Long> cooldowns = new HashMap<>();
/*     */   
/*     */   private String serverName;
/*     */   
/*     */   public SqlStorage(Storage paramStorage) {
/*  70 */     this.storage = paramStorage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void load(StorageManager paramStorageManager, AuctionPlugin paramAuctionPlugin) {
/*     */     HikariDatabaseConnection hikariDatabaseConnection;
/*  76 */     Logger.info("Load SQL...");
/*     */     
/*  78 */     this.serverName = "zauction";
/*     */     
/*  80 */     MigrationManager.setMigrationTableName("zauctionhouse_migrations");
/*     */     
/*  82 */     MigrationManager.registerMigration((Migration)new CreateItemMigration());
/*  83 */     MigrationManager.registerMigration((Migration)new CreateTransactionMigration());
/*  84 */     MigrationManager.registerMigration((Migration)new UpdateItemAddServerNameMigration());
/*  85 */     MigrationManager.registerMigration((Migration)new UpdateItemAddPriority());
/*  86 */     MigrationManager.registerMigration((Migration)new CreateOptionMigration());
/*     */     
/*  88 */     GlobalDatabaseConfiguration globalDatabaseConfiguration = new GlobalDatabaseConfiguration(paramAuctionPlugin.getConfig());
/*  89 */     String str1 = globalDatabaseConfiguration.getUser();
/*  90 */     String str2 = globalDatabaseConfiguration.getPassword();
/*  91 */     String str3 = globalDatabaseConfiguration.getHost();
/*  92 */     String str4 = globalDatabaseConfiguration.getDatabase();
/*  93 */     String str5 = globalDatabaseConfiguration.getTablePrefix();
/*  94 */     int i = globalDatabaseConfiguration.getPort();
/*  95 */     boolean bool1 = globalDatabaseConfiguration.isDebug();
/*  96 */     boolean bool2 = paramAuctionPlugin.getConfig().getBoolean("sql.fetchTransactions", false);
/*     */ 
/*     */     
/*  99 */     if (Objects.requireNonNull(this.storage) == Storage.SQLITE) {
/* 100 */       SqliteConnection sqliteConnection = new SqliteConnection(new DatabaseConfiguration(str5, str1, str2, i, str3, str4, bool1, DatabaseType.SQLITE), paramAuctionPlugin.getDataFolder());
/*     */     } else {
/* 102 */       hikariDatabaseConnection = new HikariDatabaseConnection(new DatabaseConfiguration(str5, str1, str2, i, str3, str4, bool1, DatabaseType.MYSQL));
/*     */     } 
/*     */     
/* 105 */     this.databaseConnection = (DatabaseConnection)hikariDatabaseConnection;
/*     */     
/* 107 */     Logger logger = JULogger.from(paramAuctionPlugin.getLogger());
/* 108 */     this.requestHelper = new RequestHelper(this.databaseConnection, logger);
/*     */     
/* 110 */     this.iConnection = (IConnection)new ZConnection(this.databaseConnection, this.requestHelper);
/* 111 */     runAsync(() -> {
/*     */           if (!this.databaseConnection.isValid()) {
/*     */             paramAuctionPlugin.getLogger().severe("Unable to connect to database !");
/*     */             
/*     */             Bukkit.getPluginManager().disablePlugin((Plugin)paramAuctionPlugin);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           paramAuctionPlugin.getLogger().info("The database connection is valid ! (" + this.databaseConnection.getDatabaseConfiguration().getHost() + ")");
/*     */           
/*     */           MigrationManager.execute(this.databaseConnection, paramLogger);
/*     */           
/*     */           try {
/*     */             Logger.info("Loading items", Logger.LogType.INFO);
/*     */             
/*     */             this.iConnection.selectItems(paramAuctionPlugin, this, paramStorageManager);
/*     */             
/*     */             if (paramBoolean) {
/*     */               Logger.info("Loading transactions", Logger.LogType.INFO);
/*     */               this.iConnection.selectTransactions(paramAuctionPlugin, this, paramStorageManager);
/*     */             } 
/* 133 */           } catch (Exception exception) {
/*     */             paramAuctionPlugin.getLogger().severe("An error occurred while loading items and/or transactions : " + exception.getMessage());
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   public RequestHelper getRequestHelper() {
/* 140 */     return this.requestHelper;
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(AuctionPlugin paramAuctionPlugin) {
/* 145 */     this.databaseConnection.disconnect();
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 150 */     paramAuctionItem.setStorageType(paramStorageType);
/*     */     
/* 152 */     CustomStorageProvider customStorageProvider = paramAuctionPlugin.getCustomStorageProvider();
/* 153 */     if (customStorageProvider != null && customStorageProvider.store(paramAuctionPlugin, paramAuctionItem, paramStorageType))
/*     */       return; 
/* 155 */     this.iConnection.asyncInsert(paramAuctionItem, paramStorageType, paramAuctionItem.getServer(), null);
/* 156 */     action(paramStorageType, paramList -> paramList.add(paramAuctionItem));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType, Consumer<AuctionItem> paramConsumer) {
/* 161 */     paramAuctionItem.setStorageType(paramStorageType);
/* 162 */     this.iConnection.asyncInsert(paramAuctionItem, paramStorageType, paramAuctionItem.getServer(), paramConsumer);
/* 163 */     action(paramStorageType, paramList -> paramList.add(paramAuctionItem));
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 168 */     this.iConnection.asyncDelete(paramAuctionItem, paramStorageType, () -> {
/*     */         
/* 170 */         }); action(paramStorageType, paramList -> paramList.remove(paramAuctionItem));
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveItems(AuctionPlugin paramAuctionPlugin, List<AuctionItem> paramList, StorageType paramStorageType) {
/* 175 */     action(paramStorageType, paramList2 -> {
/*     */           paramList2.clear();
/*     */           paramList2.addAll(paramList1);
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType) {
/* 183 */     action(paramStorageType, paramList -> update(paramAuctionPlugin, paramList, paramStorageType));
/*     */   }
/*     */   
/*     */   private void update(AuctionPlugin paramAuctionPlugin, List<AuctionItem> paramList, StorageType paramStorageType) {
/* 187 */     long l = System.currentTimeMillis();
/* 188 */     Iterator<AuctionItem> iterator = paramList.iterator();
/* 189 */     while (iterator.hasNext()) {
/* 190 */       AuctionItem auctionItem = iterator.next();
/*     */       
/* 192 */       if (auctionItem == null)
/*     */         continue; 
/* 194 */       if (System.currentTimeMillis() > auctionItem.getExpireAt() && auctionItem.getExpireAt() != -1L) {
/*     */         
/* 196 */         runNextTick(() -> {
/*     */               AuctionItemExpireEvent auctionItemExpireEvent = new AuctionItemExpireEvent(paramAuctionItem.getExpireAt(), paramLong, paramAuctionItem, paramStorageType);
/*     */ 
/*     */               
/*     */               auctionItemExpireEvent.call();
/*     */             });
/*     */         
/* 203 */         if (paramStorageType.equals(StorageType.STORAGE)) {
/*     */           
/* 205 */           this.iConnection.asyncDelete(auctionItem, paramStorageType, () -> {
/*     */                 long l = (AuctionConfiguration.expireTime != -1L) ? (System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime) : AuctionConfiguration.expireTime; paramAuctionItem.setExpireAt(l);
/*     */                 paramAuctionItem.setCanBuy(false);
/*     */                 if (paramAuctionPlugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null) {
/*     */                   ZAuctionPlugin zAuctionPlugin = (ZAuctionPlugin)paramAuctionPlugin;
/*     */                   if (((ZSyncManager)zAuctionPlugin.getProvider(ZSyncManager.class)).publishExpireAuction(paramAuctionItem))
/*     */                     saveItem(paramAuctionPlugin, paramAuctionItem, StorageType.EXPIRE); 
/*     */                 } else {
/*     */                   saveItem(paramAuctionPlugin, paramAuctionItem, StorageType.EXPIRE);
/*     */                 } 
/*     */               });
/* 216 */           iterator.remove();
/*     */           
/* 218 */           String str1 = "x%s %s on sale by %s has just expired, it goes in the inventory of expired items.";
/* 219 */           LoggerManager.getInstance().log(str1, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() });
/*     */           continue;
/*     */         } 
/* 222 */         iterator.remove();
/* 223 */         this.iConnection.asyncDelete(auctionItem, paramStorageType, () -> {
/*     */             
/*     */             });
/* 226 */         if (paramStorageType.equals(StorageType.BUY)) {
/*     */           
/* 228 */           String str1 = "x%s %s, bought by %s, has just expired, the item is definitively deleted.";
/* 229 */           LoggerManager.getInstance().log(str1, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() });
/*     */           
/*     */           continue;
/*     */         } 
/* 233 */         String str = "x%s %s, item of %s, has just expired, the item is definitively deleted.";
/* 234 */         LoggerManager.getInstance().log(str, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getItems(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType) {
/* 245 */     update(paramAuctionPlugin, paramStorageType);
/* 246 */     AtomicReference<List<AuctionItem>> atomicReference = new AtomicReference(new ArrayList());
/* 247 */     Objects.requireNonNull(atomicReference); action(paramStorageType, atomicReference::set);
/* 248 */     return atomicReference.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeTransaction(Transaction paramTransaction, Consumer<Transaction> paramConsumer) {
/* 253 */     this.transactions.add(paramTransaction);
/* 254 */     this.iConnection.asyncInsert(paramTransaction, paramConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveTransactions(List<Transaction> paramList) {
/* 259 */     this.transactions = paramList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 264 */     updateRedisItem(paramAuctionItem, paramStorageType);
/* 265 */     this.iConnection.asyncUpdate(paramAuctionItem, paramStorageType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateRedisItem(AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 270 */     this.items.removeIf(paramAuctionItem2 -> paramAuctionItem2.getUniqueId().equals(paramAuctionItem1.getUniqueId()));
/* 271 */     if (ZPlugin.z().getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null) {
/* 272 */       this.expiredItems.removeIf(paramAuctionItem2 -> paramAuctionItem2.getUniqueId().equals(paramAuctionItem1.getUniqueId()));
/* 273 */       this.buyingItems.removeIf(paramAuctionItem2 -> paramAuctionItem2.getUniqueId().equals(paramAuctionItem1.getUniqueId()));
/*     */     } 
/* 275 */     paramAuctionItem.setStorageType(paramStorageType);
/*     */     
/* 277 */     if (paramStorageType != StorageType.STORAGE) {
/* 278 */       action(paramStorageType, paramList -> paramList.add(paramAuctionItem));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void upsertPlayerOptions(UUID paramUUID) {
/* 285 */     if (!this.playerCaches.containsKey(paramUUID))
/*     */       return; 
/* 287 */     PlayerCache playerCache = this.playerCaches.get(paramUUID);
/* 288 */     this.iConnection.asyncUpsert(paramUUID, playerCache);
/*     */   }
/*     */ 
/*     */   
/*     */   public PlayerCache getCache(OfflinePlayer paramOfflinePlayer) {
/* 293 */     return this.playerCaches.computeIfAbsent(paramOfflinePlayer.getUniqueId(), paramUUID -> new PlayerCache());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerCache(UUID paramUUID) {
/* 298 */     this.playerCaches.remove(paramUUID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadPlayerCache(UUID paramUUID) {
/* 303 */     this.iConnection.asyncSelect(paramUUID, paramPlayerCache -> this.playerCaches.put(paramUUID, paramPlayerCache));
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Transaction> getTransactions() {
/* 308 */     return this.transactions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactions(List<Transaction> paramList) {
/* 315 */     this.transactions = paramList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateTransaction(List<Transaction> paramList) {
/* 320 */     this.iConnection.asyncUpdate(paramList);
/*     */     
/* 322 */     paramList.forEach(paramTransaction -> {
/*     */           Optional<Transaction> optional = this.transactions.stream().filter(()).findFirst();
/*     */           if (optional.isPresent()) {
/*     */             Transaction transaction = optional.get();
/*     */             transaction.setNeedMoney(false);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void purgeTransactions(long paramLong, Runnable paramRunnable) {
/* 333 */     Iterator<Transaction> iterator = this.transactions.iterator();
/* 334 */     while (iterator.hasNext()) {
/* 335 */       Transaction transaction = iterator.next();
/* 336 */       long l = Math.abs(System.currentTimeMillis() - transaction.getDate()) / 1000L;
/* 337 */       if (l > paramLong && !transaction.needMoney()) {
/* 338 */         iterator.remove();
/* 339 */         this.iConnection.asyncDelete(transaction);
/*     */       } 
/*     */     } 
/* 342 */     paramRunnable.run();
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 347 */     return "SqlStorage [items=" + this.items + ", buyingItems=" + this.buyingItems + ", expiredItems=" + this.expiredItems + ", transactions=" + this.transactions + ", storage=" + this.storage + ", iConnection=" + this.iConnection + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public void fetchClaimMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {
/* 352 */     this.iConnection.fetchClaimMoney(paramPlayer, paramConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void fetchUnreadMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {
/* 357 */     this.iConnection.fetchUnreadMoney(paramPlayer, paramConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCooldown(UUID paramUUID) {
/* 363 */     long l = ((Long)this.cooldowns.getOrDefault(paramUUID, Long.valueOf(0L))).longValue();
/*     */     
/* 365 */     if (l > System.currentTimeMillis()) {
/* 366 */       return true;
/*     */     }
/*     */     
/* 369 */     this.cooldowns.put(paramUUID, Long.valueOf(System.currentTimeMillis() + AuctionConfiguration.cooldownTransactions));
/*     */     
/* 371 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getItems() {
/* 378 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItems(List<AuctionItem> paramList) {
/* 385 */     this.items = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getBuyingItems() {
/* 392 */     return this.buyingItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuyingItems(List<AuctionItem> paramList) {
/* 399 */     this.buyingItems = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getExpiredItems() {
/* 406 */     return this.expiredItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiredItems(List<AuctionItem> paramList) {
/* 413 */     this.expiredItems = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Storage getStorage() {
/* 420 */     return this.storage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IConnection getiConnection() {
/* 427 */     return this.iConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setIConnection(IConnection paramIConnection) {
/* 434 */     this.iConnection = paramIConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<UUID, Long> getCooldowns() {
/* 441 */     return this.cooldowns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCooldowns(Map<UUID, Long> paramMap) {
/* 448 */     this.cooldowns = paramMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean uuidExist(UUID paramUUID) {
/* 453 */     return this.items.stream().anyMatch(paramAuctionItem -> (paramAuctionItem.getUniqueId() == paramUUID));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 458 */     return this.serverName;
/*     */   }
/*     */   
/*     */   public void setServerName(String paramString) {
/* 462 */     this.serverName = paramString;
/*     */   }
/*     */ 
/*     */   
/*     */   public long fetchClaimMoneySync(UUID paramUUID, String paramString) {
/* 467 */     return this.iConnection.fetchClaimMoneySync(paramUUID, paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkIsItemIsValid(Player paramPlayer, AuctionItem paramAuctionItem, Consumer<Boolean> paramConsumer) {
/* 472 */     paramConsumer.accept(Boolean.valueOf(true));
/*     */   }
/*     */   
/*     */   public DatabaseConnection getDatabaseConnection() {
/* 476 */     return this.databaseConnection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void action(StorageType paramStorageType, Consumer<List<AuctionItem>> paramConsumer) {
/* 486 */     switch (paramStorageType) {
/*     */       case BUY:
/* 488 */         paramConsumer.accept(this.buyingItems);
/*     */         break;
/*     */       case EXPIRE:
/* 491 */         paramConsumer.accept(this.expiredItems);
/*     */         break;
/*     */       case STORAGE:
/* 494 */         paramConsumer.accept(this.items);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeRedisItem(StorageType paramStorageType, AuctionItem paramAuctionItem) {
/* 503 */     action(paramStorageType, paramList -> paramList.add(paramAuctionItem));
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\storage\storages\SqlStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */