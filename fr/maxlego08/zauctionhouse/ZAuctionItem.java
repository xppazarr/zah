/*     */ package fr.maxlego08.zauctionhouse;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*     */ import fr.maxlego08.zauctionhouse.dto.AuctionItemDTO;
/*     */ import fr.maxlego08.zauctionhouse.transaction.ZTransaction;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.builder.TimerBuilder;
/*     */ import fr.maxlego08.zauctionhouse.zmenu.CacheAuctionItem;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Predicate;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZAuctionItem
/*     */   extends ZUtils
/*     */   implements AuctionItem
/*     */ {
/*     */   private final UUID uuid;
/*     */   private final ItemStack itemStack;
/*     */   private final String economy;
/*     */   private final UUID sellerUUID;
/*     */   private final AuctionType auctionType;
/*     */   private final List<ItemStack> itemStacks;
/*  53 */   private final transient Map<UUID, CacheAuctionItem> cache = new HashMap<>();
/*     */   private final String serverName;
/*     */   private long price;
/*     */   private String sellerName;
/*  57 */   private int priority = 0;
/*     */   private boolean isAlreadyBuying = false;
/*     */   private boolean canBuy = true;
/*     */   private StorageType storageType;
/*     */   private long expireAt;
/*     */   private UUID buyerUUID;
/*     */   private transient AuctionEconomy auctionEconomy;
/*     */   private transient List<Category> categories;
/*     */   
/*     */   public ZAuctionItem(AuctionItemDTO paramAuctionItemDTO) {
/*  67 */     this(paramAuctionItemDTO.getId(), paramAuctionItemDTO.getItemStack(), AuctionType.valueOf(paramAuctionItemDTO.getAuctionType()), paramAuctionItemDTO.getPrice(), paramAuctionItemDTO.getEconomy(), paramAuctionItemDTO.getSeller(), paramAuctionItemDTO.getExpireAt(), paramAuctionItemDTO.getBuyer(), StorageType.valueOf(paramAuctionItemDTO.getStorageType()), paramAuctionItemDTO.getSellerName(), paramAuctionItemDTO.getPriority(), paramAuctionItemDTO.getServerName());
/*     */   }
/*     */ 
/*     */   
/*     */   public ZAuctionItem(UUID paramUUID1, ItemStack paramItemStack, AuctionType paramAuctionType, long paramLong1, String paramString1, UUID paramUUID2, long paramLong2, StorageType paramStorageType, String paramString2, int paramInt, String paramString3) {
/*  72 */     this.uuid = paramUUID1;
/*  73 */     this.itemStack = paramItemStack;
/*  74 */     this.price = paramLong1;
/*  75 */     this.economy = paramString1;
/*  76 */     this.sellerUUID = paramUUID2;
/*  77 */     this.auctionType = paramAuctionType;
/*  78 */     this.expireAt = paramLong2;
/*  79 */     this.storageType = paramStorageType;
/*  80 */     this.itemStacks = null;
/*  81 */     this.sellerName = paramString2;
/*  82 */     this.priority = paramInt;
/*  83 */     this.serverName = paramString3;
/*     */     
/*  85 */     setCategories(((AuctionPlugin)ZPlugin.z()).getCategoryManager());
/*     */   }
/*     */ 
/*     */   
/*     */   public ZAuctionItem(UUID paramUUID1, List<ItemStack> paramList, AuctionType paramAuctionType, long paramLong1, String paramString1, UUID paramUUID2, long paramLong2, StorageType paramStorageType, String paramString2, int paramInt, String paramString3) {
/*  90 */     this.uuid = paramUUID1;
/*  91 */     this.itemStack = null;
/*  92 */     this.itemStacks = paramList;
/*  93 */     this.price = paramLong1;
/*  94 */     this.economy = paramString1;
/*  95 */     this.sellerUUID = paramUUID2;
/*  96 */     this.auctionType = paramAuctionType;
/*  97 */     this.expireAt = paramLong2;
/*  98 */     this.storageType = paramStorageType;
/*  99 */     this.sellerName = paramString2;
/* 100 */     this.priority = paramInt;
/* 101 */     this.serverName = paramString3;
/*     */     
/* 103 */     setCategories(((AuctionPlugin)ZPlugin.z()).getCategoryManager());
/*     */   }
/*     */ 
/*     */   
/*     */   public ZAuctionItem(UUID paramUUID1, String paramString1, AuctionType paramAuctionType, long paramLong1, String paramString2, UUID paramUUID2, long paramLong2, UUID paramUUID3, StorageType paramStorageType, String paramString3, int paramInt, String paramString4) {
/* 108 */     this.uuid = paramUUID1;
/* 109 */     this.price = paramLong1;
/* 110 */     this.economy = paramString2;
/* 111 */     this.sellerUUID = paramUUID2;
/* 112 */     this.auctionType = paramAuctionType;
/* 113 */     this.buyerUUID = paramUUID3;
/* 114 */     this.expireAt = paramLong2;
/* 115 */     this.storageType = paramStorageType;
/* 116 */     this.sellerName = paramString3;
/* 117 */     this.priority = paramInt;
/* 118 */     this.serverName = paramString4;
/*     */     
/* 120 */     if (paramAuctionType.equals(AuctionType.INVENTORY)) {
/*     */       
/* 122 */       this.itemStacks = new ArrayList<>();
/* 123 */       this.itemStack = null;
/*     */       
/* 125 */       String[] arrayOfString = paramString1.split(";");
/* 126 */       for (String str : arrayOfString) {
/* 127 */         this.itemStacks.add(decode(str));
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 132 */       this.itemStack = decode(paramString1);
/* 133 */       this.itemStacks = null;
/*     */     } 
/*     */     
/* 136 */     setCategories(((AuctionPlugin)ZPlugin.z()).getCategoryManager());
/*     */   }
/*     */   
/*     */   public ZAuctionItem(UUID paramUUID1, ItemStack paramItemStack, AuctionType paramAuctionType, long paramLong1, String paramString1, UUID paramUUID2, long paramLong2, UUID paramUUID3, StorageType paramStorageType, String paramString2, int paramInt, String paramString3) {
/* 140 */     this(paramUUID1, paramItemStack, paramAuctionType, paramLong1, paramString1, paramUUID2, paramLong2, paramStorageType, paramString2, paramInt, paramString3);
/* 141 */     this.buyerUUID = paramUUID3;
/*     */   }
/*     */   
/*     */   public ZAuctionItem(UUID paramUUID1, List<ItemStack> paramList, AuctionType paramAuctionType, long paramLong1, String paramString1, UUID paramUUID2, long paramLong2, UUID paramUUID3, StorageType paramStorageType, String paramString2, int paramInt, String paramString3) {
/* 145 */     this(paramUUID1, paramList, paramAuctionType, paramLong1, paramString1, paramUUID2, paramLong2, paramStorageType, paramString2, paramInt, paramString3);
/* 146 */     this.buyerUUID = paramUUID3;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getUniqueId() {
/* 151 */     return this.uuid;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/* 156 */     return (this.auctionType == AuctionType.INVENTORY) ? Message.SELL_INVENTORY_ITEM.getItemStack() : this.itemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getPrice() {
/* 161 */     return this.price;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPrice(long paramLong) {
/* 166 */     this.price = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public AuctionEconomy getEconomy() {
/* 171 */     if (this.auctionEconomy != null) {
/* 172 */       return this.auctionEconomy;
/*     */     }
/* 174 */     ZAuctionPlugin zAuctionPlugin = (ZAuctionPlugin)ZPlugin.z();
/* 175 */     Optional<AuctionEconomy> optional = zAuctionPlugin.getEconomyManager().getEconomy(this.economy);
/* 176 */     if (optional.isPresent()) {
/* 177 */       return this.auctionEconomy = optional.get();
/*     */     }
/* 179 */     zAuctionPlugin.getLogger().severe("Impossible to find the economy " + this.economy + " for auction item " + this.uuid);
/* 180 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEconomyName() {
/* 185 */     return this.economy;
/*     */   }
/*     */ 
/*     */   
/*     */   public OfflinePlayer getSeller() {
/* 190 */     return Bukkit.getOfflinePlayer(this.sellerUUID);
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getSellerUniqueId() {
/* 195 */     return this.sellerUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getExpireAt() {
/* 200 */     return this.expireAt;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setExpireAt(long paramLong) {
/* 205 */     this.expireAt = paramLong;
/*     */   }
/*     */ 
/*     */   
/*     */   public OfflinePlayer getBuyer() {
/* 210 */     return Bukkit.getOfflinePlayer(this.buyerUUID);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setBuyer(UUID paramUUID) {
/* 215 */     this.buyerUUID = paramUUID;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getServer() {
/* 220 */     return this.serverName;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getBuyerUniqueId() {
/* 225 */     return this.buyerUUID;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String serializedItem() {
/*     */     String str;
/* 232 */     if (this.auctionType == AuctionType.INVENTORY) {
/* 233 */       str = this.itemStacks.stream().map(ItemStackUtils::serializeItemStack).collect(Collectors.joining(";"));
/*     */     } else {
/* 235 */       str = ItemStackUtils.serializeItemStack(this.itemStack);
/*     */     } 
/* 237 */     return str;
/*     */   }
/*     */ 
/*     */   
/*     */   public String timeFormat() {
/* 242 */     return TimerBuilder.getStringTime((getExpireAt() - System.currentTimeMillis()) / 1000L);
/*     */   }
/*     */ 
/*     */   
/*     */   public String priceFormat() {
/* 247 */     String str = toPrice();
/* 248 */     return color(getEconomy().format(str, this.price));
/*     */   }
/*     */ 
/*     */   
/*     */   public String statusFormat(UUID paramUUID) {
/* 253 */     return paramUUID.equals(this.sellerUUID) ? Message.STATUS_PLAYER.getMessage() : Message.STATUS_BUYER.getMessage();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack createItem(Player paramPlayer, Message paramMessage) {
/* 259 */     if (getItemStack() == null) {
/* 260 */       if (this.auctionType.equals(AuctionType.INVENTORY)) {
/* 261 */         Logger.info("Beware, you have misconfigured the plugin! Please change your configuration messages.sellinventory.item", Logger.LogType.WARNING);
/*     */       } else {
/* 263 */         Logger.info("Unable to find the itemstack for this item for the item with id " + getUniqueId());
/*     */       } 
/*     */     }
/*     */     
/* 267 */     if (this.cache.containsKey(paramPlayer.getUniqueId())) {
/* 268 */       CacheAuctionItem cacheAuctionItem = this.cache.get(paramPlayer.getUniqueId());
/* 269 */       if (cacheAuctionItem.isValid()) {
/* 270 */         return cacheAuctionItem.getItemStack();
/*     */       }
/*     */     } 
/*     */     
/* 274 */     ItemStack itemStack = (getItemStack() == null) ? new ItemStack(Material.STONE) : getItemStack().clone();
/* 275 */     ItemMeta itemMeta = itemStack.getItemMeta();
/* 276 */     if (itemMeta == null) {
/* 277 */       if (this.auctionType.equals(AuctionType.INVENTORY)) {
/* 278 */         Logger.info("Beware, you have misconfigured the plugin ! Please change your configuration messages.sellinventory.item", Logger.LogType.WARNING);
/*     */       } else {
/* 280 */         Logger.info("Unable to find the item meta for this item for the item with id " + getUniqueId());
/*     */       } 
/* 282 */       return itemStack;
/*     */     } 
/*     */     
/* 285 */     ZAuctionPlugin zAuctionPlugin = (ZAuctionPlugin)ZPlugin.z();
/*     */     
/* 287 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY && zAuctionPlugin.getzMenuHandler().isMiniMessage()) {
/*     */       
/* 289 */       zAuctionPlugin.getzMenuHandler().createItem(itemStack, paramPlayer, paramMessage, this);
/*     */     } else {
/*     */       
/* 292 */       List list = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList();
/*     */       
/* 294 */       paramMessage.getMessages().forEach(paramString -> {
/*     */             paramString = paramString.replace("%time%", timeFormat());
/*     */             
/*     */             paramString = paramString.replace("%price%", priceFormat());
/*     */             
/*     */             paramString = paramString.replace("%status%", statusFormat(paramPlayer.getUniqueId()));
/*     */             
/*     */             paramString = paramString.replace("%seller%", getSellerName());
/*     */             
/*     */             paramString = paramString.replace("%priority%", String.valueOf(this.priority));
/*     */             
/*     */             Optional<Priority> optional = paramZAuctionPlugin.getAuctionManager().getPriority(this.priority);
/*     */             paramString = paramString.replace("%priority_text%", optional.isPresent() ? color(((Priority)optional.get()).getText()) : Message.PRIORITY.msg());
/*     */             if (getBuyerUniqueId() != null) {
/*     */               OfflinePlayer offlinePlayer = getBuyer();
/*     */               paramString = paramString.replace("%buyer%", (offlinePlayer == null) ? this.buyerUUID.toString() : offlinePlayer.getName());
/*     */             } 
/*     */             paramList.add(color(paramString));
/*     */           });
/* 313 */       if (AuctionConfiguration.enablePapiInAuctionItemLore) {
/* 314 */         itemMeta.setLore(papi(list, paramPlayer));
/*     */       } else {
/* 316 */         itemMeta.setLore(list);
/*     */       } 
/*     */ 
/*     */       
/* 320 */       itemStack.setItemMeta(itemMeta);
/*     */     } 
/*     */     
/* 323 */     if (AuctionConfiguration.enableAddItemFlagToAuctionItem) {
/* 324 */       ItemMeta itemMeta1 = itemStack.getItemMeta();
/* 325 */       AuctionConfiguration.itemFlags.forEach(paramItemFlag -> {
/*     */             if (!paramItemMeta.hasItemFlag(paramItemFlag)) {
/*     */               paramItemMeta.addItemFlags(new ItemFlag[] { paramItemFlag });
/*     */             }
/*     */           });
/* 330 */       itemStack.setItemMeta(itemMeta1);
/*     */     } 
/*     */     
/* 333 */     this.cache.put(paramPlayer.getUniqueId(), new CacheAuctionItem(itemStack));
/* 334 */     return itemStack;
/*     */   }
/*     */   
/*     */   public String toPrice() {
/* 338 */     return toPrice(this.price);
/*     */   }
/*     */   
/*     */   public String toPriceFormat(long paramLong) {
/* 342 */     return toPrice(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean canBuy() {
/* 347 */     return this.canBuy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isAlreadyBuying() {
/* 352 */     return this.isAlreadyBuying;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setAlreadyBuying(boolean paramBoolean) {
/* 357 */     this.isAlreadyBuying = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getAmount() {
/* 362 */     return (this.itemStack != null) ? this.itemStack.getAmount() : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   public AuctionType getType() {
/* 367 */     return this.auctionType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCanBuy(boolean paramBoolean) {
/* 372 */     this.canBuy = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public Transaction buildTransaction(long paramLong) {
/* 377 */     OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(this.sellerUUID);
/* 378 */     boolean bool = offlinePlayer.isOnline();
/* 379 */     return (Transaction)new ZTransaction(this.buyerUUID, this.sellerUUID, serializedItem(), paramLong, this.economy, bool, AuctionConfiguration.enableClaimMoney);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ItemStack> getItemStacks() {
/* 384 */     return (this.itemStacks == null) ? new ArrayList<>() : (List<ItemStack>)this.itemStacks.stream().filter(Objects::nonNull).map(ItemStack::clone).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> serializedItems() {
/* 389 */     return (this.itemStacks == null) ? new ArrayList<>() : (List<String>)this.itemStacks.stream().filter(Objects::nonNull).map(paramItemStack -> paramZAuctionItem.encode(paramItemStack)).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasFreeSpace(Player paramPlayer) {
/* 394 */     if (this.auctionType.equals(AuctionType.INVENTORY)) {
/*     */       
/* 396 */       int i = this.itemStacks.size();
/* 397 */       return !hasInventoryFull(paramPlayer, i);
/*     */     } 
/* 399 */     return !hasInventoryFull(paramPlayer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 409 */     return "ZAuctionItem [uuid=" + this.uuid + ", itemStack=" + this.itemStack + ", price=" + this.price + ", economy=" + this.economy + ", sellerUUID=" + this.sellerUUID + ", auctionType=" + this.auctionType + ", itemStacks=" + this.itemStacks + ", isAlreadyBuying=" + this.isAlreadyBuying + ", canBuy=" + this.canBuy + ", expireAt=" + this.expireAt + ", buyerUUID=" + this.buyerUUID + "]";
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean match(Optional<Filter> paramOptional, String paramString) {
/*     */     boolean bool;
/* 415 */     if (paramString == null) return false;
/*     */     
/* 417 */     if (paramOptional.isPresent()) {
/*     */       
/* 419 */       Filter filter = paramOptional.get();
/* 420 */       return filter.perform(this, paramString);
/*     */     } 
/*     */     
/* 423 */     switch (getType()) {
/*     */       
/*     */       case DEFAULT:
/* 426 */         bool = matchItemStack(this.itemStack, paramString);
/* 427 */         if (bool) return true;
/*     */         
/*     */         break;
/*     */       
/*     */       case INVENTORY:
/* 432 */         for (ItemStack itemStack : this.itemStacks) {
/* 433 */           boolean bool1 = matchItemStack(itemStack, paramString);
/* 434 */           if (bool1) return true;
/*     */         
/*     */         } 
/*     */         break;
/*     */     } 
/*     */ 
/*     */ 
/*     */     
/* 442 */     String str = getSellerName();
/* 443 */     return (str != null && str.equalsIgnoreCase(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   private boolean matchItemStack(ItemStack paramItemStack, String paramString) {
/* 448 */     if (paramItemStack.getType().name().toLowerCase().contains(paramString.toLowerCase())) return true;
/*     */     
/* 450 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 451 */     if (itemMeta.hasDisplayName() && itemMeta.getDisplayName().toLowerCase().contains(paramString.toLowerCase())) {
/* 452 */       return true;
/*     */     }
/* 454 */     return (itemMeta.hasLore() && itemMeta.getLore().stream().anyMatch(paramString2 -> paramString2.toLowerCase().contains(paramString1.toLowerCase())));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isExpired() {
/* 459 */     return (System.currentTimeMillis() >= this.expireAt && this.expireAt != -1L);
/*     */   }
/*     */ 
/*     */   
/*     */   public StorageType getStorageType() {
/* 464 */     return this.storageType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStorageType(StorageType paramStorageType) {
/* 469 */     this.storageType = paramStorageType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getSellerName() {
/* 474 */     String str = (this.sellerName == null) ? (this.sellerName = Bukkit.getOfflinePlayer(this.sellerUUID).getName()) : this.sellerName;
/* 475 */     return (str == null) ? "Something wrong here, yeah its sad" : str;
/*     */   }
/*     */ 
/*     */   
/*     */   public void giveIf(Player paramPlayer, Predicate<AuctionItem> paramPredicate) {
/* 480 */     if (paramPredicate.test(this)) {
/* 481 */       this.itemStacks.forEach(paramItemStack -> give(paramPlayer, paramItemStack));
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPriority() {
/* 487 */     return this.priority;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Category> getCategories() {
/* 492 */     return this.categories;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCategories(CategoryManager paramCategoryManager) {
/* 497 */     this.categories = paramCategoryManager.getCategories(this);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemName() {
/* 502 */     ItemStack itemStack = getItemStack();
/* 503 */     if (itemStack.hasItemMeta()) {
/* 504 */       ItemMeta itemMeta = itemStack.getItemMeta();
/* 505 */       if (itemMeta.hasDisplayName()) {
/* 506 */         String str = ChatColor.stripColor(itemMeta.getDisplayName());
/* 507 */         return str.isEmpty() ? itemStack.getType().name() : str;
/*     */       } 
/* 509 */       return itemStack.getType().name();
/*     */     } 
/* 511 */     return itemStack.getType().name();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMaterialName() {
/* 516 */     return getItemStack().getType().name();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getItemStackSize() {
/* 521 */     return (this.auctionType == AuctionType.INVENTORY) ? this.itemStacks.stream().mapToInt(ItemStack::getAmount).sum() : this.itemStack.getAmount();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ZAuctionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */