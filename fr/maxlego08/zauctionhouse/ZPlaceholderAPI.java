/*     */ package fr.maxlego08.zauctionhouse;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.placeholder.LocalPlaceholder;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.SellInventory;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.ConcurrentHashMap;
/*     */ import java.util.function.Supplier;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ public class ZPlaceholderAPI extends ZUtils {
/*  30 */   private final Map<Player, AuctionItem> players = new HashMap<>(); private final ZAuctionPlugin plugin;
/*  31 */   private final Map<Player, SellInventory> sellInventories = new HashMap<>();
/*  32 */   private final ConcurrentHashMap<UUID, ConcurrentHashMap<String, CacheEntry>> cache = new ConcurrentHashMap<>();
/*  33 */   private final ConcurrentHashMap<String, CacheEntry> serverCache = new ConcurrentHashMap<>();
/*     */   
/*     */   public ZPlaceholderAPI(ZAuctionPlugin paramZAuctionPlugin) {
/*  36 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */   
/*     */   public void setPlayer(Player paramPlayer, AuctionItem paramAuctionItem) {
/*  40 */     this.players.put(paramPlayer, paramAuctionItem);
/*     */   }
/*     */   
/*     */   public void setSellInventory(Player paramPlayer, SellInventory paramSellInventory) {
/*  44 */     this.sellInventories.put(paramPlayer, paramSellInventory);
/*     */   }
/*     */   
/*     */   public void onQuit(Player paramPlayer) {
/*  48 */     this.sellInventories.remove(paramPlayer);
/*  49 */     this.players.remove(paramPlayer);
/*  50 */     this.cache.remove(paramPlayer.getUniqueId());
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerPlaceholders() {
/*  55 */     AuctionManager auctionManager = this.plugin.getAuctionManager();
/*  56 */     TransactionManager transactionManager = this.plugin.getTransactionManager();
/*  57 */     CategoryManager categoryManager = this.plugin.getCategoryManager();
/*     */     
/*  59 */     LocalPlaceholder localPlaceholder = LocalPlaceholder.getInstance();
/*  60 */     localPlaceholder.register("max_items", paramPlayer -> String.valueOf(paramAuctionManager.getMaxSellPerPermission((Permissible)paramPlayer)));
/*  61 */     localPlaceholder.register("expired_item", paramPlayer -> get(paramPlayer.getUniqueId(), "expired_item", (), new Storage[0]));
/*  62 */     localPlaceholder.register("buying_item", paramPlayer -> get(paramPlayer.getUniqueId(), "buying_item", (), new Storage[0]));
/*  63 */     localPlaceholder.register("currents_item", paramPlayer -> get(paramPlayer.getUniqueId(), "currents_item", (), new Storage[0]));
/*  64 */     localPlaceholder.register("sorting", paramPlayer -> paramAuctionManager.getSort(paramPlayer).getName());
/*  65 */     localPlaceholder.register("sorting_type", paramPlayer -> paramAuctionManager.getSort(paramPlayer).name());
/*  66 */     localPlaceholder.register("claims", paramPlayer -> String.valueOf(paramTransactionManager.needMoney((OfflinePlayer)paramPlayer)));
/*  67 */     localPlaceholder.register("counts", paramPlayer -> get(paramPlayer.getUniqueId(), "counts", (), new Storage[0]));
/*  68 */     localPlaceholder.register("sellinventory_price", paramPlayer -> {
/*     */           if (this.sellInventories.containsKey(paramPlayer)) {
/*     */             SellInventory sellInventory = this.sellInventories.get(paramPlayer);
/*     */             return toPrice(sellInventory.getPrice());
/*     */           } 
/*     */           return "?";
/*     */         });
/*  75 */     localPlaceholder.register("sellinventory_currency", paramPlayer -> {
/*     */           if (this.sellInventories.containsKey(paramPlayer)) {
/*     */             SellInventory sellInventory = this.sellInventories.get(paramPlayer);
/*     */             return sellInventory.getEconomy().getCurrency();
/*     */           } 
/*     */           return "Not Found";
/*     */         });
/*  82 */     localPlaceholder.register("category", paramPlayer -> {
/*     */           Optional<Category> optional = paramAuctionManager.getCurrentCategory(paramPlayer);
/*     */           
/*     */           if (optional.isPresent()) {
/*     */             Category category = optional.get();
/*     */             
/*     */             return category.getDisplayName();
/*     */           } 
/*     */           
/*     */           return "Category not found";
/*     */         });
/*     */     
/*  94 */     localPlaceholder.register("sell_confirm_price", paramPlayer -> {
/*     */           PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/*     */           
/*     */           long l = playerCache.getSellPrice();
/*     */           return this.plugin.getAuctionManager().getPriceFormat(l);
/*     */         });
/* 100 */     localPlaceholder.register("buy_confirm_price", paramPlayer -> {
/*     */           PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/*     */           
/*     */           AuctionItem auctionItem = playerCache.getAuctionItem();
/*     */           
/*     */           long l = auctionItem.getPrice();
/*     */           
/*     */           return this.plugin.getAuctionManager().getPriceFormat(l);
/*     */         });
/* 109 */     localPlaceholder.register("price", paramPlayer -> {
/*     */           if (this.players.containsKey(paramPlayer)) {
/*     */             AuctionItem auctionItem = this.players.get(paramPlayer);
/*     */             return String.valueOf(auctionItem.getPrice());
/*     */           } 
/*     */           return "Value not found";
/*     */         });
/* 116 */     localPlaceholder.register("amount", paramPlayer -> {
/*     */           if (this.players.containsKey(paramPlayer)) {
/*     */             AuctionItem auctionItem = this.players.get(paramPlayer);
/*     */             return String.valueOf(auctionItem.getType().equals(AuctionType.INVENTORY) ? auctionItem.getItemStacks().size() : auctionItem.getAmount());
/*     */           } 
/*     */           return "Value not found";
/*     */         });
/* 123 */     localPlaceholder.register("currency", paramPlayer -> {
/*     */           if (this.players.containsKey(paramPlayer)) {
/*     */             AuctionItem auctionItem = this.players.get(paramPlayer);
/*     */             return auctionItem.getEconomy().getCurrency();
/*     */           } 
/*     */           return "Value not found";
/*     */         });
/* 130 */     localPlaceholder.register("category_item_", (paramPlayer, paramString) -> get("category_item_" + paramString, (), new Storage[0]));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     localPlaceholder.register("claim_", (paramPlayer, paramString) -> get(paramPlayer.getUniqueId(), "claim_" + paramString, (), new Storage[] { Storage.MYSQL, Storage.SQLITE, Storage.HIKARICP }));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 156 */     localPlaceholder.register("disable_sell_confirmation", paramPlayer -> String.valueOf(this.plugin.getStorage().getCache((OfflinePlayer)paramPlayer).isDisableSellConfirmation()));
/*     */   }
/*     */ 
/*     */   
/*     */   public String get(UUID paramUUID, String paramString, Supplier<String> paramSupplier, Storage... paramVarArgs) {
/* 161 */     if (Arrays.<Storage>stream(paramVarArgs).anyMatch(paramStorage -> (paramStorage == AuctionConfiguration.storage))) {
/* 162 */       ConcurrentHashMap<String, CacheEntry> concurrentHashMap = this.cache.computeIfAbsent(paramUUID, paramUUID -> new ConcurrentHashMap<>());
/* 163 */       CacheEntry cacheEntry = concurrentHashMap.computeIfAbsent(paramString, paramString -> new CacheEntry(paramSupplier));
/* 164 */       if (cacheEntry.isExpired()) {
/* 165 */         cacheEntry.refreshValueAsync();
/* 166 */         return AuctionConfiguration.placeholderLoader;
/*     */       } 
/* 168 */       return cacheEntry.getValue();
/* 169 */     }  return paramSupplier.get();
/*     */   }
/*     */   
/*     */   public String get(String paramString, Supplier<String> paramSupplier, Storage... paramVarArgs) {
/* 173 */     if (Arrays.<Storage>stream(paramVarArgs).anyMatch(paramStorage -> (paramStorage == AuctionConfiguration.storage))) {
/* 174 */       CacheEntry cacheEntry = this.serverCache.computeIfAbsent(paramString, paramString -> new CacheEntry(paramSupplier));
/* 175 */       if (cacheEntry.isExpired()) {
/* 176 */         cacheEntry.refreshValueAsync();
/* 177 */         return AuctionConfiguration.placeholderLoader;
/*     */       } 
/* 179 */       return cacheEntry.getValue();
/* 180 */     }  return paramSupplier.get();
/*     */   }
/*     */   
/*     */   public void clearCache(UUID paramUUID) {
/* 184 */     if (this.cache.containsKey(paramUUID))
/* 185 */       ((ConcurrentHashMap)this.cache.get(paramUUID)).clear(); 
/*     */   }
/*     */   
/*     */   private static class CacheEntry
/*     */   {
/*     */     private final Supplier<String> supplier;
/*     */     private volatile String value;
/*     */     private volatile long fetchTime;
/*     */     
/*     */     CacheEntry(Supplier<String> param1Supplier) {
/* 195 */       this.supplier = param1Supplier;
/* 196 */       this.value = null;
/* 197 */       this.fetchTime = 0L;
/*     */     }
/*     */     
/*     */     synchronized void refreshValueAsync() {
/* 201 */       ZPlugin.serverImplementation.runAsync(param1WrappedTask -> {
/*     */             this.value = this.supplier.get();
/*     */             this.fetchTime = System.currentTimeMillis();
/*     */           });
/*     */     }
/*     */     
/*     */     boolean isExpired() {
/* 208 */       return (System.currentTimeMillis() - this.fetchTime > AuctionConfiguration.cachePlaceholderDurationMillis);
/*     */     }
/*     */     
/*     */     String getValue() {
/* 212 */       return this.value;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ZPlaceholderAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */