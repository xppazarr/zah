/*     */ package fr.maxlego08.zauctionhouse.storage.storages;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionItemExpireEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class JSonStorage
/*     */   extends ZUtils
/*     */   implements IStorage
/*     */ {
/*  30 */   private final Map<UUID, PlayerCache> playerCaches = new HashMap<>();
/*  31 */   private List<AuctionItem> items = new ArrayList<>();
/*  32 */   private List<AuctionItem> buyingItems = new ArrayList<>();
/*  33 */   private List<AuctionItem> expiredItems = new ArrayList<>();
/*  34 */   private List<Transaction> transactions = new ArrayList<>();
/*  35 */   private transient Map<UUID, Long> cooldowns = new HashMap<>();
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(StorageManager paramStorageManager, AuctionPlugin paramAuctionPlugin) {
/*  40 */     this.items.removeIf(Objects::isNull);
/*  41 */     this.buyingItems.removeIf(Objects::isNull);
/*  42 */     this.expiredItems.removeIf(Objects::isNull);
/*     */     
/*  44 */     this.buyingItems.forEach(paramAuctionItem -> {
/*     */           if (paramAuctionItem == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           paramAuctionItem.setCanBuy(false);
/*     */           
/*     */           paramAuctionItem.setStorageType(StorageType.BUY);
/*     */         });
/*     */     
/*  54 */     this.expiredItems.forEach(paramAuctionItem -> {
/*     */           if (paramAuctionItem == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           paramAuctionItem.setCanBuy(false);
/*     */           
/*     */           paramAuctionItem.setStorageType(StorageType.EXPIRE);
/*     */         });
/*     */     
/*  64 */     this.items.forEach(paramAuctionItem -> {
/*     */           if (paramAuctionItem == null) {
/*     */             return;
/*     */           }
/*     */           
/*     */           paramAuctionItem.setCategories(paramAuctionPlugin.getCategoryManager());
/*     */           
/*     */           paramAuctionItem.setStorageType(StorageType.STORAGE);
/*     */         });
/*  73 */     if (this.transactions.size() > 5000) {
/*  74 */       Logger.info("You have more than 5000 transactions, we advise you to switch to SQL storage!", Logger.LogType.ERROR);
/*     */     }
/*     */     
/*  77 */     purgeTransactions(AuctionConfiguration.removeTransactionAfterSeconds, () -> {
/*     */         
/*     */         });
/*     */   }
/*     */   
/*     */   public void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/*  83 */     paramAuctionItem.setStorageType(paramStorageType);
/*  84 */     switch (paramStorageType) {
/*     */       case BUY:
/*  86 */         this.buyingItems.add(paramAuctionItem);
/*     */         break;
/*     */       case EXPIRE:
/*  89 */         this.expiredItems.add(paramAuctionItem);
/*     */         break;
/*     */       case STORAGE:
/*  92 */         this.items.add(paramAuctionItem);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType, Consumer<AuctionItem> paramConsumer) {
/* 101 */     paramAuctionItem.setStorageType(paramStorageType);
/* 102 */     switch (paramStorageType) {
/*     */       case BUY:
/* 104 */         this.buyingItems.add(paramAuctionItem);
/*     */         break;
/*     */       case EXPIRE:
/* 107 */         this.expiredItems.add(paramAuctionItem);
/*     */         break;
/*     */       case STORAGE:
/* 110 */         this.items.add(paramAuctionItem);
/*     */         break;
/*     */     } 
/*     */ 
/*     */     
/* 115 */     paramConsumer.accept(paramAuctionItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 120 */     switch (paramStorageType) {
/*     */       case BUY:
/* 122 */         this.buyingItems.remove(paramAuctionItem);
/*     */         break;
/*     */       case EXPIRE:
/* 125 */         this.expiredItems.remove(paramAuctionItem);
/*     */         break;
/*     */       case STORAGE:
/* 128 */         this.items.remove(paramAuctionItem);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveItems(AuctionPlugin paramAuctionPlugin, List<AuctionItem> paramList, StorageType paramStorageType) {
/* 137 */     switch (paramStorageType) {
/*     */       case BUY:
/* 139 */         this.buyingItems = paramList;
/*     */         break;
/*     */       case EXPIRE:
/* 142 */         this.expiredItems = paramList;
/*     */         break;
/*     */       case STORAGE:
/* 145 */         this.items = paramList;
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType) {
/* 154 */     switch (paramStorageType) {
/*     */       case BUY:
/* 156 */         update(paramAuctionPlugin, this.buyingItems, paramStorageType);
/*     */         break;
/*     */       case EXPIRE:
/* 159 */         update(paramAuctionPlugin, this.expiredItems, paramStorageType);
/*     */         break;
/*     */       case STORAGE:
/* 162 */         update(paramAuctionPlugin, this.items, paramStorageType);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void update(AuctionPlugin paramAuctionPlugin, List<AuctionItem> paramList, StorageType paramStorageType) {
/* 170 */     long l = System.currentTimeMillis();
/* 171 */     Iterator<AuctionItem> iterator = paramList.iterator();
/* 172 */     while (iterator.hasNext()) {
/* 173 */       AuctionItem auctionItem = iterator.next();
/* 174 */       if (auctionItem == null)
/*     */         continue; 
/* 176 */       if (System.currentTimeMillis() > auctionItem.getExpireAt() && auctionItem.getExpireAt() != -1L) {
/*     */         
/* 178 */         runNextTick(() -> {
/*     */               AuctionItemExpireEvent auctionItemExpireEvent = new AuctionItemExpireEvent(paramAuctionItem.getExpireAt(), paramLong, paramAuctionItem, paramStorageType);
/*     */ 
/*     */               
/*     */               auctionItemExpireEvent.call();
/*     */             });
/*     */         
/* 185 */         if (paramStorageType.equals(StorageType.STORAGE)) {
/*     */           
/* 187 */           long l1 = (AuctionConfiguration.expireTime != -1L) ? (System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime) : AuctionConfiguration.expireTime;
/* 188 */           auctionItem.setExpireAt(l1);
/* 189 */           auctionItem.setCanBuy(false);
/* 190 */           saveItem(paramAuctionPlugin, auctionItem, StorageType.EXPIRE);
/* 191 */           iterator.remove();
/*     */           
/* 193 */           String str1 = "x%s %s on sale by %s has just expired, it goes in the inventory of expired items.";
/* 194 */           LoggerManager.getInstance().log(str1, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() }); continue;
/*     */         } 
/* 196 */         iterator.remove();
/*     */         
/* 198 */         if (paramStorageType.equals(StorageType.BUY)) {
/*     */           
/* 200 */           String str1 = "x%s %s, bought by %s, has just expired, the item is definitively deleted.";
/* 201 */           LoggerManager.getInstance().log(str1, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() });
/*     */           
/*     */           continue;
/*     */         } 
/* 205 */         String str = "x%s %s, item of %s, has just expired, the item is definitively deleted.";
/* 206 */         LoggerManager.getInstance().log(str, new Object[] { Integer.valueOf(auctionItem.getAmount()), paramAuctionPlugin.translateItemStack(auctionItem.getItemStack()), auctionItem.getSeller().getName() });
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getItems(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType) {
/* 216 */     update(paramAuctionPlugin, paramStorageType);
/* 217 */     switch (paramStorageType) {
/*     */       case BUY:
/* 219 */         return this.buyingItems;
/*     */       case EXPIRE:
/* 221 */         return this.expiredItems;
/*     */       case STORAGE:
/* 223 */         return this.items;
/*     */     } 
/* 225 */     return new ArrayList<>();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(AuctionPlugin paramAuctionPlugin) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeTransaction(Transaction paramTransaction, Consumer<Transaction> paramConsumer) {
/* 236 */     this.transactions.add(paramTransaction);
/* 237 */     if (paramConsumer != null) {
/* 238 */       paramConsumer.accept(paramTransaction);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void saveTransactions(List<Transaction> paramList) {
/* 244 */     this.transactions = paramList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 249 */     removeItem(paramAuctionPlugin, paramAuctionItem, StorageType.STORAGE);
/* 250 */     saveItem(paramAuctionPlugin, paramAuctionItem, paramStorageType);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Transaction> getTransactions() {
/* 255 */     return this.transactions;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactions(List<Transaction> paramList) {
/* 262 */     this.transactions = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateTransaction(List<Transaction> paramList) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void purgeTransactions(long paramLong, Runnable paramRunnable) {
/* 273 */     Iterator<Transaction> iterator = this.transactions.iterator();
/* 274 */     while (iterator.hasNext()) {
/* 275 */       Transaction transaction = iterator.next();
/* 276 */       long l = Math.abs(System.currentTimeMillis() - transaction.getDate()) / 1000L;
/* 277 */       if (l > paramLong && !transaction.needMoney()) {
/* 278 */         iterator.remove();
/*     */       }
/*     */     } 
/* 281 */     paramRunnable.run();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getItems() {
/* 288 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItems(List<AuctionItem> paramList) {
/* 295 */     this.items = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getBuyingItems() {
/* 302 */     return this.buyingItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuyingItems(List<AuctionItem> paramList) {
/* 309 */     this.buyingItems = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getExpiredItems() {
/* 316 */     return this.expiredItems;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiredItems(List<AuctionItem> paramList) {
/* 323 */     this.expiredItems = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 333 */     return "JSonStorage [items=" + this.items + ", buyingItems=" + this.buyingItems + ", expiredItems=" + this.expiredItems + ", transactions=" + this.transactions + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fetchClaimMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void fetchUnreadMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isCooldown(UUID paramUUID) {
/* 349 */     long l = ((Long)this.cooldowns.getOrDefault(paramUUID, Long.valueOf(0L))).longValue();
/*     */     
/* 351 */     if (l > System.currentTimeMillis()) {
/* 352 */       return true;
/*     */     }
/*     */     
/* 355 */     this.cooldowns.put(paramUUID, Long.valueOf(System.currentTimeMillis() + AuctionConfiguration.cooldownTransactions));
/*     */     
/* 357 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<UUID, Long> getCooldowns() {
/* 364 */     return this.cooldowns;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCooldowns(Map<UUID, Long> paramMap) {
/* 371 */     this.cooldowns = paramMap;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean uuidExist(UUID paramUUID) {
/* 376 */     return this.items.stream().anyMatch(paramAuctionItem -> (paramAuctionItem.getUniqueId() == paramUUID));
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServerName() {
/* 381 */     return "zauction";
/*     */   }
/*     */ 
/*     */   
/*     */   public long fetchClaimMoneySync(UUID paramUUID, String paramString) {
/* 386 */     return 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public void checkIsItemIsValid(Player paramPlayer, AuctionItem paramAuctionItem, Consumer<Boolean> paramConsumer) {
/* 391 */     paramConsumer.accept(Boolean.valueOf(true));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void storeRedisItem(StorageType paramStorageType, AuctionItem paramAuctionItem) {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRedisItem(AuctionItem paramAuctionItem, StorageType paramStorageType) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void upsertPlayerOptions(UUID paramUUID) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public PlayerCache getCache(OfflinePlayer paramOfflinePlayer) {
/* 411 */     return this.playerCaches.computeIfAbsent(paramOfflinePlayer.getUniqueId(), paramUUID -> new PlayerCache());
/*     */   }
/*     */ 
/*     */   
/*     */   public void removePlayerCache(UUID paramUUID) {
/* 416 */     this.playerCaches.remove(paramUUID);
/*     */   }
/*     */   
/*     */   public void loadPlayerCache(UUID paramUUID) {}
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\storage\storages\JSonStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */