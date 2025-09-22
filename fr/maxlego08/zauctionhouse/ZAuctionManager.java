/*      */ package fr.maxlego08.zauctionhouse;
/*      */ 
/*      */ import fr.maxlego08.zauctionhouse.actions.AuctionPurchaseAction;
/*      */ import fr.maxlego08.zauctionhouse.actions.AuctionSellAction;
/*      */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*      */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*      */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*      */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*      */ import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
/*      */ import fr.maxlego08.zauctionhouse.api.blacklist.IWhitelistManager;
/*      */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*      */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*      */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.HistoryType;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*      */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*      */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionAdminRemoveEvent;
/*      */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionOpenEvent;
/*      */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPostAdminRemoveEvent;
/*      */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionRemoveEvent;
/*      */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionRetrieveEvent;
/*      */ import fr.maxlego08.zauctionhouse.api.filter.FilterManager;
/*      */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
/*      */ import fr.maxlego08.zauctionhouse.api.inventory.VInventoryManager;
/*      */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*      */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*      */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*      */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*      */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*      */ import fr.maxlego08.zauctionhouse.api.utils.BlacklistPlayers;
/*      */ import fr.maxlego08.zauctionhouse.api.utils.CacheAuctionItem;
/*      */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*      */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*      */ import fr.maxlego08.zauctionhouse.inventory.ZInventoryManager;
/*      */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.ElapsedTime;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.Pagination;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.yaml.YamlUtils;
/*      */ import java.text.SimpleDateFormat;
/*      */ import java.util.ArrayList;
/*      */ import java.util.Arrays;
/*      */ import java.util.Collection;
/*      */ import java.util.Collections;
/*      */ import java.util.Comparator;
/*      */ import java.util.Date;
/*      */ import java.util.HashMap;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Objects;
/*      */ import java.util.Optional;
/*      */ import java.util.UUID;
/*      */ import java.util.concurrent.ConcurrentHashMap;
/*      */ import java.util.stream.Collectors;
/*      */ import org.bukkit.Bukkit;
/*      */ import org.bukkit.GameMode;
/*      */ import org.bukkit.OfflinePlayer;
/*      */ import org.bukkit.block.ShulkerBox;
/*      */ import org.bukkit.command.CommandSender;
/*      */ import org.bukkit.configuration.ConfigurationSection;
/*      */ import org.bukkit.configuration.file.YamlConfiguration;
/*      */ import org.bukkit.entity.Player;
/*      */ import org.bukkit.inventory.ItemStack;
/*      */ import org.bukkit.inventory.meta.BlockStateMeta;
/*      */ import org.bukkit.permissions.Permissible;
/*      */ 
/*      */ public class ZAuctionManager
/*      */   extends YamlUtils
/*      */   implements AuctionManager
/*      */ {
/*      */   private final ZAuctionPlugin plugin;
/*   77 */   private final Map<String, String> citizenNames = new HashMap<>();
/*   78 */   private final List<Priority> priorities = new ArrayList<>();
/*      */   
/*   80 */   private final Map<Integer, Priority> priorityMap = new HashMap<>();
/*      */   private final IBlacklistManager blacklistManager;
/*      */   private final IWhitelistManager whitelistManager;
/*   83 */   private final Map<String, List<AuctionItem>> cache = new ConcurrentHashMap<>();
/*   84 */   private final Map<String, Long> lastUpdate = new ConcurrentHashMap<>();
/*      */   
/*   86 */   private final String duperAreDump = "Yes, duper are dump !";
/*   87 */   private final AuctionSellAction sellAction = new AuctionSellAction();
/*   88 */   private final AuctionPurchaseAction purchaseAction = new AuctionPurchaseAction();
/*      */   private CacheAuctionItem cacheAuctionItemList;
/*      */   private IStorage iStorage;
/*      */   
/*      */   public ZAuctionManager(ZAuctionPlugin paramZAuctionPlugin) {
/*   93 */     super(paramZAuctionPlugin);
/*   94 */     this.plugin = paramZAuctionPlugin;
/*   95 */     this.blacklistManager = paramZAuctionPlugin.getBlacklistManager();
/*   96 */     this.whitelistManager = paramZAuctionPlugin.getWhitelistManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getBannedWorld() {
/*  101 */     return this.bannedWorlds;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void open(Player paramPlayer, Command paramCommand) {
/*  107 */     if (!AuctionConfiguration.enablePlugin) {
/*  108 */       message((CommandSender)paramPlayer, Message.PLUGIN_DISABLE, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  112 */     if (this.bannedWorlds.contains(paramPlayer.getWorld().getName())) {
/*  113 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  117 */     if (isBlacklist((OfflinePlayer)paramPlayer)) {
/*  118 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  122 */     Inventory inventory = paramCommand.getInventory();
/*      */     
/*  124 */     AuctionOpenEvent auctionOpenEvent = new AuctionOpenEvent(paramPlayer);
/*  125 */     auctionOpenEvent.call();
/*      */     
/*  127 */     if (auctionOpenEvent.isCancelled())
/*      */       return; 
/*  129 */     ZInventoryManager zInventoryManager = this.plugin.getInventoryManager();
/*  130 */     zInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, new ArrayList(), paramCommand, null });
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void openDefault(Player paramPlayer) {
/*  136 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  137 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*      */       
/*      */       return;
/*      */     } 
/*  141 */     CommandObject commandObject = new CommandObject("zauctionhouse", new ArrayList(), this.plugin.getInventories().getInventory(InventoryName.AUCTION), null, "no description");
/*  142 */     open(paramPlayer, (Command)commandObject);
/*      */   }
/*      */ 
/*      */   
/*      */   public void reload() {
/*  147 */     long l = System.currentTimeMillis();
/*      */     
/*  149 */     info("Reload starting...");
/*      */     
/*  151 */     info("Closure of all inventories...");
/*  152 */     closeInventory();
/*      */     
/*  154 */     this.plugin.getConfiguration().load();
/*  155 */     this.plugin.getSavers().forEach(paramSavable -> paramSavable.load(this.plugin.getPersist()));
/*      */     
/*  157 */     info("Deleting inventories...");
/*  158 */     this.plugin.getInventories().delete();
/*      */     
/*  160 */     info("Reload categories");
/*  161 */     this.plugin.getCategoryManager().load(null);
/*  162 */     this.plugin.getStorage().getItems(this.plugin, StorageType.STORAGE).forEach(paramAuctionItem -> paramAuctionItem.setCategories(this.plugin.getCategoryManager()));
/*      */     
/*  164 */     info("Reload config file");
/*  165 */     this.plugin.reloadConfig();
/*      */     
/*  167 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  168 */       this.plugin.getzMenuHandler().loadInventories();
/*      */     } else {
/*      */       try {
/*  171 */         this.plugin.getInventories().loadInventories();
/*  172 */       } catch (Exception exception) {
/*  173 */         exception.printStackTrace();
/*      */         
/*      */         return;
/*      */       } 
/*      */     } 
/*  178 */     loadConfiguration();
/*      */     
/*  180 */     l = System.currentTimeMillis() - l;
/*  181 */     info("Reload done (" + l + " ms)");
/*      */   }
/*      */   
/*      */   private TransactionManager getTransaction() {
/*  185 */     return this.plugin.getTransactionManager();
/*      */   }
/*      */ 
/*      */   
/*      */   public void closeInventory() {
/*  190 */     this.plugin.getInventoryManager().close();
/*      */   }
/*      */ 
/*      */   
/*      */   public IStorage getStorage() {
/*  195 */     return (this.iStorage == null) ? (this.iStorage = this.plugin.getStorage()) : this.iStorage;
/*      */   }
/*      */ 
/*      */   
/*      */   public void update(StorageType paramStorageType) {
/*  200 */     IStorage iStorage = getStorage();
/*  201 */     iStorage.update(this.plugin, paramStorageType);
/*      */   }
/*      */   
/*      */   private List<ItemStack> getItems(AuctionItem paramAuctionItem) {
/*  205 */     return paramAuctionItem.getType().equals(AuctionType.INVENTORY) ? (List<ItemStack>)paramAuctionItem.getItemStacks().stream().map(this::getItemsOrSingle).flatMap(Collection::stream).collect(Collectors.toList()) : getItemsOrSingle(paramAuctionItem.getItemStack());
/*      */   }
/*      */ 
/*      */   
/*      */   private List<ItemStack> getItemsOrSingle(ItemStack paramItemStack) {
/*  210 */     if (NmsVersion.nmsVersion.isShulker() && paramItemStack.getItemMeta() instanceof BlockStateMeta) {
/*  211 */       BlockStateMeta blockStateMeta = (BlockStateMeta)paramItemStack.getItemMeta();
/*  212 */       if (blockStateMeta.getBlockState() instanceof ShulkerBox) {
/*  213 */         ShulkerBox shulkerBox = (ShulkerBox)blockStateMeta.getBlockState();
/*  214 */         List<ItemStack> list = (List)Arrays.<ItemStack>stream(shulkerBox.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList());
/*  215 */         list.add(paramItemStack);
/*  216 */         return list;
/*      */       } 
/*      */     } 
/*      */     
/*  220 */     return Collections.singletonList(paramItemStack);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean auctionHasBlacklistItems(AuctionItem paramAuctionItem) {
/*  225 */     return getItems(paramAuctionItem).stream().anyMatch(paramItemStack -> this.blacklistManager.isBlacklist(paramItemStack).isPresent());
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean auctionHasWhitelistItems(AuctionItem paramAuctionItem) {
/*  230 */     return getItems(paramAuctionItem).stream().anyMatch(paramItemStack -> this.whitelistManager.isWhitelist(paramItemStack).isPresent());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void sellItem(AuctionItem paramAuctionItem, ItemStack paramItemStack, Player paramPlayer, long paramLong, AuctionEconomy paramAuctionEconomy, int paramInt, AuctionType paramAuctionType) {
/*  236 */     this.sellAction.sellItem(this.plugin, this, paramAuctionItem, paramItemStack, paramPlayer, paramLong, paramAuctionEconomy, paramInt, paramAuctionType);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getSortItems(Player paramPlayer) {
/*  242 */     if (AuctionConfiguration.enableCacheItems && this.cacheAuctionItemList != null && this.cacheAuctionItemList.isValid()) {
/*  243 */       return this.cacheAuctionItemList.getAuctionItems();
/*      */     }
/*      */     
/*  246 */     IStorage iStorage = getStorage();
/*  247 */     List<AuctionItem> list = (List)iStorage.getItems(this.plugin, StorageType.STORAGE).stream().filter(paramAuctionItem -> (!paramAuctionItem.isAlreadyBuying() && paramAuctionItem.canBuy())).collect(Collectors.toList());
/*      */     
/*  249 */     Sorting sorting = getSort(paramPlayer);
/*  250 */     Comparator comparator = sorting.getComparator();
/*  251 */     list.sort(comparator);
/*      */     
/*  253 */     if (AuctionConfiguration.enablePrioritySort) {
/*      */       
/*  255 */       ArrayList<AuctionItem> arrayList = new ArrayList();
/*      */       
/*  257 */       ElapsedTime elapsedTime = new ElapsedTime("Sort priority");
/*  258 */       elapsedTime.start();
/*      */       
/*  260 */       List list1 = (List)this.priorities.stream().sorted(Comparator.comparingInt(Priority::getPriority).reversed()).collect(Collectors.toList());
/*      */       
/*  262 */       for (Priority priority : list1) {
/*      */         List list3;
/*      */         
/*  265 */         if (AuctionConfiguration.enablePrioritySortLimit) {
/*  266 */           list3 = (List)list.stream().filter(paramAuctionItem -> (paramAuctionItem.getPriority() == paramPriority.getPriority())).limit(priority.getLimit()).collect(Collectors.toList());
/*      */         } else {
/*  268 */           list3 = (List)list.stream().filter(paramAuctionItem -> (paramAuctionItem.getPriority() == paramPriority.getPriority())).collect(Collectors.toList());
/*      */         } 
/*      */         
/*  271 */         arrayList.addAll(list3);
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  277 */       List list2 = (List)list.stream().filter(paramAuctionItem -> !paramList.contains(paramAuctionItem)).collect(Collectors.toList());
/*      */       
/*  279 */       arrayList.addAll(list2);
/*      */       
/*  281 */       elapsedTime.endDisplay();
/*      */       
/*  283 */       this.cacheAuctionItemList = new CacheAuctionItem(arrayList, AuctionConfiguration.cacheSeconds);
/*      */       
/*  285 */       return arrayList;
/*      */     } 
/*      */     
/*  288 */     this.cacheAuctionItemList = new CacheAuctionItem(list, AuctionConfiguration.cacheSeconds);
/*  289 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getSortItems(Player paramPlayer, Category paramCategory) {
/*  294 */     List<AuctionItem> list = getItems(paramCategory);
/*      */ 
/*      */ 
/*      */     
/*  298 */     Sorting sorting = getSort(paramPlayer);
/*  299 */     Comparator<? super AuctionItem> comparator = sorting.getComparator();
/*      */     
/*  301 */     if (list == null) {
/*  302 */       list = new ArrayList<>();
/*      */     }
/*      */     
/*  305 */     list.sort(comparator);
/*      */     
/*  307 */     if (AuctionConfiguration.enablePrioritySort) {
/*      */       
/*  309 */       ArrayList<AuctionItem> arrayList = new ArrayList();
/*      */       
/*  311 */       ElapsedTime elapsedTime = new ElapsedTime("Sort priority");
/*  312 */       elapsedTime.start();
/*      */       
/*  314 */       List list1 = (List)this.priorities.stream().sorted(Comparator.comparingInt(Priority::getPriority).reversed()).collect(Collectors.toList());
/*      */       
/*  316 */       for (Priority priority : list1) {
/*      */         List list3;
/*      */         
/*  319 */         if (AuctionConfiguration.enablePrioritySortLimit) {
/*  320 */           list3 = (List)list.stream().filter(paramAuctionItem -> (paramAuctionItem.getPriority() == paramPriority.getPriority())).limit(priority.getLimit()).collect(Collectors.toList());
/*      */         } else {
/*  322 */           list3 = (List)list.stream().filter(paramAuctionItem -> (paramAuctionItem.getPriority() == paramPriority.getPriority())).collect(Collectors.toList());
/*      */         } 
/*      */         
/*  325 */         arrayList.addAll(list3);
/*      */       } 
/*      */ 
/*      */       
/*  329 */       List list2 = (List)list.stream().filter(paramAuctionItem -> !paramList.contains(paramAuctionItem)).collect(Collectors.toList());
/*      */       
/*  331 */       arrayList.addAll(list2);
/*      */       
/*  333 */       elapsedTime.endDisplay();
/*      */       
/*  335 */       return arrayList;
/*      */     } 
/*      */     
/*  338 */     return list;
/*      */   }
/*      */ 
/*      */   
/*      */   public Sorting getSort(Player paramPlayer) {
/*  343 */     return getCache((OfflinePlayer)paramPlayer).getSorting();
/*      */   }
/*      */ 
/*      */   
/*      */   public void setSort(Player paramPlayer, Sorting paramSorting) {
/*  348 */     getCache((OfflinePlayer)paramPlayer).setSorting(paramSorting);
/*  349 */     getStorage().upsertPlayerOptions(paramPlayer.getUniqueId());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDisableSellConfirmation(Player paramPlayer, boolean paramBoolean) {
/*  354 */     getCache((OfflinePlayer)paramPlayer).setDisableSellConfirmation(paramBoolean);
/*  355 */     getStorage().upsertPlayerOptions(paramPlayer.getUniqueId());
/*      */   }
/*      */   public void open(AuctionPlugin paramAuctionPlugin, InventoryType paramInventoryType, Player paramPlayer, AuctionItem paramAuctionItem, int paramInt, List<Inventory> paramList, String paramString) {
/*      */     Inventory inventory;
/*      */     CommandObject commandObject;
/*      */     VInventoryManager vInventoryManager;
/*  361 */     switch (paramInventoryType) {
/*      */       case BUY:
/*  363 */         inventory = paramAuctionPlugin.getInventories().getInventory(InventoryName.BUY_CONFIRM);
/*  364 */         commandObject = new CommandObject(null, null, inventory, null, null);
/*      */         
/*  366 */         vInventoryManager = paramAuctionPlugin.getInventoryManager();
/*  367 */         vInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, paramList, commandObject, paramString, paramAuctionItem, Integer.valueOf(paramInt), paramInventoryType });
/*      */         break;
/*      */       
/*      */       case EXPIRE:
/*  371 */         inventory = paramAuctionPlugin.getInventories().getInventory(InventoryName.REMOVE_CONFIRM);
/*  372 */         commandObject = new CommandObject(null, null, inventory, null, null);
/*      */         
/*  374 */         vInventoryManager = paramAuctionPlugin.getInventoryManager();
/*  375 */         vInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, paramList, commandObject, paramString, paramAuctionItem, Integer.valueOf(paramInt), paramInventoryType });
/*      */         break;
/*      */       
/*      */       case STORAGE:
/*  379 */         inventory = paramAuctionPlugin.getInventories().getInventory(InventoryName.SELL_SHOW);
/*  380 */         commandObject = new CommandObject(null, null, inventory, null, null);
/*      */         
/*  382 */         vInventoryManager = paramAuctionPlugin.getInventoryManager();
/*  383 */         vInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, paramList, commandObject, paramString, paramAuctionItem, Integer.valueOf(paramInt), paramInventoryType });
/*      */         break;
/*      */       
/*      */       case null:
/*  387 */         inventory = paramAuctionPlugin.getInventories().getInventory(InventoryName.ADMIN_REMOVE);
/*  388 */         commandObject = new CommandObject(null, null, inventory, null, null);
/*      */         
/*  390 */         vInventoryManager = paramAuctionPlugin.getInventoryManager();
/*  391 */         vInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, paramList, commandObject, paramString, paramAuctionItem, Integer.valueOf(paramInt), paramInventoryType });
/*      */         break;
/*      */       
/*      */       case null:
/*  395 */         inventory = paramAuctionPlugin.getInventories().getInventory(InventoryName.SHOW);
/*  396 */         commandObject = new CommandObject(null, null, inventory, null, null);
/*      */         
/*  398 */         vInventoryManager = paramAuctionPlugin.getInventoryManager();
/*  399 */         vInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, paramList, commandObject, paramString, paramAuctionItem, Integer.valueOf(paramInt), paramInventoryType });
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void loadConfiguration() {
/*  410 */     this.blacklistManager.unregisterAll();
/*  411 */     this.whitelistManager.unregisterAll();
/*      */     
/*  413 */     YamlConfiguration yamlConfiguration = (YamlConfiguration)getConfig();
/*      */     
/*  415 */     this.bannedWorlds = yamlConfiguration.getStringList("disableWorlds");
/*      */     
/*      */     try {
/*  418 */       this.permissionSells.clear();
/*  419 */       yamlConfiguration.getMapList("permissions").forEach(paramMap -> {
/*      */             String str = (String)paramMap.get("permission");
/*      */             long l = ((Number)paramMap.get("limit")).longValue();
/*      */             this.permissionSells.put(str, Long.valueOf(l));
/*      */           });
/*  424 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*      */     try {
/*  428 */       this.permissionEconomies.clear();
/*  429 */       ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("price.permissions");
/*  430 */       if (configurationSection != null) {
/*  431 */         configurationSection.getKeys(false).forEach(paramString -> paramConfigurationSection.getMapList(paramString).forEach(()));
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */ 
/*      */ 
/*      */     
/*      */     }
/*  440 */     catch (Exception exception) {}
/*      */ 
/*      */     
/*      */     try {
/*  444 */       this.permissionExpiration.clear();
/*  445 */       yamlConfiguration.getMapList("expirations").forEach(paramMap -> {
/*      */             String str = (String)paramMap.get("permission");
/*      */             long l = ((Number)paramMap.get("expiration")).longValue();
/*      */             this.permissionExpiration.put(str, Long.valueOf(l));
/*      */           });
/*  450 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*      */     try {
/*  454 */       this.citizenNames.clear();
/*  455 */       yamlConfiguration.getMapList("citizen").forEach(paramMap -> {
/*      */             String str1 = (String)paramMap.get("name");
/*      */             String str2 = (String)paramMap.get("inventory");
/*      */             this.citizenNames.put(str1, str2);
/*      */           });
/*  460 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  463 */     this.priorities.clear();
/*      */     try {
/*  465 */       yamlConfiguration.getMapList("priorities").forEach(paramMap -> {
/*      */             Priority priority = new Priority(paramMap);
/*      */             this.priorities.add(priority);
/*      */             this.priorityMap.put(Integer.valueOf(priority.getPriority()), priority);
/*      */           });
/*  470 */     } catch (Exception exception) {}
/*      */ 
/*      */     
/*  473 */     this.blacklistManager.loadConfigurations(this.plugin);
/*  474 */     this.whitelistManager.loadConfigurations(this.plugin);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getMaxSellPerPermission(Permissible paramPermissible) {
/*  479 */     return getMaxValueForPermission(paramPermissible, this.permissionSells, 0L);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getExpirationPerPermission(Permissible paramPermissible) {
/*  484 */     long l = AuctionConfiguration.sellTime;
/*  485 */     if (AuctionConfiguration.enablePermissionForSellingTime) {
/*  486 */       return getMaxValueForPermission(paramPermissible, this.permissionExpiration, l);
/*      */     }
/*  488 */     return l;
/*      */   }
/*      */ 
/*      */   
/*      */   public long getMaxPricePerEconomyPermission(AuctionEconomy paramAuctionEconomy, Permissible paramPermissible) {
/*  493 */     Map<String, Long> map = (Map)this.permissionEconomies.getOrDefault(paramAuctionEconomy.getName(), new HashMap<>());
/*  494 */     return getMaxValueForPermission(paramPermissible, map, -1L);
/*      */   }
/*      */   
/*      */   private long getMaxValueForPermission(Permissible paramPermissible, Map<String, Long> paramMap, long paramLong) {
/*  498 */     return paramMap.entrySet().stream().filter(paramEntry -> paramPermissible.hasPermission((String)paramEntry.getKey())).mapToLong(Map.Entry::getValue).max().orElse(paramLong);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean canSellMoreItem(Player paramPlayer) {
/*  504 */     return (count(paramPlayer, StorageType.STORAGE) < getMaxSellPerPermission((Permissible)paramPlayer));
/*      */   }
/*      */ 
/*      */   
/*      */   public long count(Player paramPlayer, StorageType paramStorageType) {
/*  509 */     IStorage iStorage = getStorage();
/*  510 */     return iStorage.getItems(this.plugin, paramStorageType).stream().filter(paramAuctionItem -> (paramAuctionItem == null) ? false : (paramStorageType.equals(StorageType.BUY) ? paramAuctionItem.getBuyerUniqueId().equals(paramPlayer.getUniqueId()) : paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId())))
/*      */ 
/*      */ 
/*      */       
/*  514 */       .count();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void remove(AuctionItem paramAuctionItem, Player paramPlayer, boolean paramBoolean) {
/*  520 */     if (!paramAuctionItem.canBuy() || paramAuctionItem.isAlreadyBuying()) {
/*  521 */       this.plugin.getInventoryManager().update(paramPlayer);
/*      */       
/*      */       return;
/*      */     } 
/*  525 */     long l = (AuctionConfiguration.expireTime == -1L) ? -1L : (System.currentTimeMillis() + AuctionConfiguration.expireTime * 1000L);
/*  526 */     AuctionRetrieveEvent auctionRetrieveEvent = new AuctionRetrieveEvent(paramBoolean, paramPlayer, paramAuctionItem, l);
/*  527 */     auctionRetrieveEvent.call();
/*      */     
/*  529 */     if (auctionRetrieveEvent.isCancelled())
/*      */       return; 
/*  531 */     message((CommandSender)paramPlayer, Message.REMOVE_MESSAGE, new Object[] { "%amount%", Integer.valueOf(paramAuctionItem.getAmount()), "%item%", this.plugin.translateItemStack(paramAuctionItem.getItemStack()), "%price%", paramAuctionItem.priceFormat() });
/*      */     
/*  533 */     IStorage iStorage = getStorage();
/*      */     
/*  535 */     paramAuctionItem.setExpireAt(auctionRetrieveEvent.getExpireAt());
/*  536 */     paramAuctionItem.setAlreadyBuying(false);
/*  537 */     paramAuctionItem.setCanBuy(false);
/*      */     
/*  539 */     if (AuctionConfiguration.giveItemAfterRemove && paramAuctionItem.hasFreeSpace(paramPlayer)) {
/*      */       
/*  541 */       giveAuctionItem(paramAuctionItem, paramPlayer);
/*      */     } else {
/*      */       
/*  544 */       iStorage.updateItem(this.plugin, paramAuctionItem, StorageType.EXPIRE);
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  549 */     if (AuctionConfiguration.enableCacheItems && this.cacheAuctionItemList != null) {
/*  550 */       this.cacheAuctionItemList.invalid();
/*      */     }
/*      */ 
/*      */     
/*  554 */     if (AuctionConfiguration.openInventoryAfterRemoveConfirm) {
/*  555 */       openDefault(paramPlayer);
/*      */     } else {
/*  557 */       paramPlayer.closeInventory();
/*      */     } 
/*      */     
/*  560 */     String str = "%s " + (paramBoolean ? "(an admin) " : "") + "remove x%s %s from storage";
/*  561 */     LoggerManager.getInstance().log(str, new Object[] { paramPlayer.getName(), Integer.valueOf(paramAuctionItem.getItemStack().getAmount()), this.plugin.translateItemStack(paramAuctionItem.getItemStack()) });
/*  562 */     this.plugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*      */   }
/*      */ 
/*      */   
/*      */   public void giveAuctionItem(AuctionItem paramAuctionItem, Player paramPlayer) {
/*  567 */     this.iStorage.removeItem(this.plugin, paramAuctionItem, StorageType.STORAGE);
/*  568 */     AuctionType auctionType = paramAuctionItem.getType();
/*      */     
/*  570 */     if (auctionType.equals(AuctionType.INVENTORY)) {
/*  571 */       paramAuctionItem.getItemStacks().forEach(paramItemStack -> give(paramPlayer, paramItemStack));
/*      */     } else {
/*  573 */       give(paramPlayer, paramAuctionItem.getItemStack());
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public PlayerCache getCache(OfflinePlayer paramOfflinePlayer) {
/*  579 */     return getStorage().getCache(paramOfflinePlayer);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getExpire(Player paramPlayer) {
/*  584 */     IStorage iStorage = getStorage();
/*  585 */     return (List<AuctionItem>)iStorage.getItems(this.plugin, StorageType.EXPIRE).stream().filter(paramAuctionItem -> (paramAuctionItem != null && paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId()))).collect(Collectors.toList());
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getBuying(Player paramPlayer) {
/*  590 */     IStorage iStorage = getStorage();
/*  591 */     return (List<AuctionItem>)iStorage.getItems(this.plugin, StorageType.BUY).stream().filter(paramAuctionItem -> (paramAuctionItem != null && paramAuctionItem.getBuyerUniqueId() != null && paramAuctionItem.getBuyerUniqueId().equals(paramPlayer.getUniqueId()))).collect(Collectors.toList());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeItem(Player paramPlayer, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/*  597 */     if (!paramAuctionItem.hasFreeSpace(paramPlayer)) {
/*  598 */       message((CommandSender)paramPlayer, Message.INVENTORY_FULL, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  602 */     IStorage iStorage = getStorage();
/*      */     
/*  604 */     AuctionRemoveEvent auctionRemoveEvent = new AuctionRemoveEvent(paramPlayer, paramAuctionItem, paramStorageType);
/*  605 */     auctionRemoveEvent.call();
/*  606 */     if (auctionRemoveEvent.isCancelled()) {
/*      */       return;
/*      */     }
/*      */     
/*  610 */     iStorage.removeItem(this.plugin, paramAuctionItem, paramStorageType);
/*  611 */     paramAuctionItem.setAlreadyBuying(true);
/*  612 */     paramAuctionItem.setCanBuy(false);
/*      */     
/*  614 */     AuctionType auctionType = paramAuctionItem.getType();
/*  615 */     if (auctionType.equals(AuctionType.INVENTORY)) {
/*  616 */       paramAuctionItem.getItemStacks().forEach(paramItemStack -> give(paramPlayer, paramItemStack));
/*      */     } else {
/*  618 */       give(paramPlayer, paramAuctionItem.getItemStack());
/*      */     } 
/*      */ 
/*      */     
/*  622 */     if (AuctionConfiguration.enableCacheItems && this.cacheAuctionItemList != null) {
/*  623 */       this.cacheAuctionItemList.invalid();
/*      */     }
/*      */     
/*  626 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  627 */       this.plugin.getzMenuHandler().update(paramPlayer);
/*      */     } else {
/*  629 */       this.plugin.getInventoryManager().update(paramPlayer);
/*      */     } 
/*      */     
/*  632 */     String str = "%s remove x%s %s from " + paramStorageType.name().toLowerCase();
/*  633 */     LoggerManager.getInstance().log(str, new Object[] { paramPlayer.getName(), Integer.valueOf(paramAuctionItem.getItemStack().getAmount()), this.plugin.translateItemStack(paramAuctionItem.getItemStack()) });
/*      */     
/*  635 */     clearCache();
/*  636 */     this.plugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAdmin(Player paramPlayer, AuctionItem paramAuctionItem, boolean paramBoolean1, boolean paramBoolean2) {
/*  642 */     if (!paramAuctionItem.canBuy() || paramAuctionItem.isAlreadyBuying()) {
/*  643 */       this.plugin.getInventoryManager().update(paramPlayer);
/*      */       
/*      */       return;
/*      */     } 
/*  647 */     IStorage iStorage = getStorage();
/*      */     
/*  649 */     AuctionAdminRemoveEvent auctionAdminRemoveEvent = new AuctionAdminRemoveEvent(paramPlayer, paramAuctionItem, paramBoolean1, paramBoolean2);
/*  650 */     auctionAdminRemoveEvent.call();
/*      */     
/*  652 */     if (auctionAdminRemoveEvent.isCancelled()) {
/*      */       return;
/*      */     }
/*      */     
/*  656 */     OfflinePlayer offlinePlayer = paramAuctionItem.getSeller();
/*  657 */     String str1 = paramAuctionItem.getSellerName();
/*      */     
/*  659 */     if (paramBoolean2) paramAuctionItem.setBuyer(paramPlayer.getUniqueId());
/*      */     
/*  661 */     paramAuctionItem.setAlreadyBuying(true);
/*  662 */     paramAuctionItem.setCanBuy(false);
/*      */     
/*  664 */     paramAuctionItem.setExpireAt((AuctionConfiguration.expireTime == -1L) ? -1L : (System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime));
/*  665 */     iStorage.updateItem(this.plugin, paramAuctionItem, paramBoolean2 ? StorageType.BUY : StorageType.EXPIRE);
/*      */     
/*  667 */     String str2 = "%s bought x%s %s to %s for %s.";
/*  668 */     LoggerManager.getInstance().log(str2, new Object[] { paramPlayer.getName(), Integer.valueOf(paramAuctionItem.getItemStack().getAmount()), this.plugin.translateItemStack(paramAuctionItem.getItemStack()), str1, paramAuctionItem.priceFormat() });
/*      */     
/*  670 */     Message message = Message.ITEM_REMOVE_ADMIN;
/*  671 */     message((CommandSender)paramPlayer, message, new Object[] { "%price%", paramAuctionItem.priceFormat(), "%item%", this.plugin.translateItemStack(paramAuctionItem.getItemStack()), "%seller%", str1, "%buyer%", paramPlayer.getName() });
/*      */     
/*  673 */     if (offlinePlayer != null && offlinePlayer.isOnline() && !paramBoolean1) {
/*  674 */       message = paramBoolean2 ? Message.ITEM_REMOVE_SELLER_DELETE : Message.ITEM_REMOVE_SELLER_GET;
/*  675 */       message((CommandSender)offlinePlayer.getPlayer(), message, new Object[] { "%price%", paramAuctionItem.priceFormat(), "%item%", this.plugin.translateItemStack(paramAuctionItem.getItemStack()), "%seller%", str1, "%buyer%", paramPlayer.getName() });
/*      */     } 
/*      */ 
/*      */     
/*  679 */     if (AuctionConfiguration.enableCacheItems && this.cacheAuctionItemList != null) {
/*  680 */       this.cacheAuctionItemList.invalid();
/*      */     }
/*      */     
/*  683 */     paramPlayer.closeInventory();
/*  684 */     clearCache();
/*  685 */     this.plugin.getPlaceholderAPI().clearCache(offlinePlayer.getUniqueId());
/*      */     
/*  687 */     (new AuctionPostAdminRemoveEvent(paramPlayer, paramAuctionItem, paramBoolean1, paramBoolean2)).call();
/*      */   }
/*      */ 
/*      */   
/*      */   public void buy(AuctionItem paramAuctionItem, Player paramPlayer) {
/*  692 */     this.purchaseAction.buy(this.plugin, this, paramAuctionItem, paramPlayer);
/*      */   }
/*      */ 
/*      */   
/*      */   public long count(StorageType paramStorageType) {
/*  697 */     return getStorage().getItems(this.plugin, paramStorageType).size();
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void nextSort(Player paramPlayer) {
/*  703 */     if (isCooldown(paramPlayer, "sort_change", AuctionConfiguration.sortCooldownChange))
/*      */       return; 
/*  705 */     Sorting sorting = getSort(paramPlayer).next();
/*  706 */     setSort(paramPlayer, sorting);
/*      */     
/*  708 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  709 */       this.plugin.getzMenuHandler().update(paramPlayer);
/*      */     } else {
/*  711 */       this.plugin.getInventoryManager().update(paramPlayer);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getItems(Player paramPlayer) {
/*  717 */     IStorage iStorage = getStorage();
/*  718 */     return (List<AuctionItem>)iStorage.getItems(this.plugin, StorageType.STORAGE).stream().filter(paramAuctionItem -> (paramAuctionItem != null && paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId()) && !paramAuctionItem.isAlreadyBuying() && paramAuctionItem.canBuy())).collect(Collectors.toList());
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getItems(Category paramCategory) {
/*  723 */     if (AuctionConfiguration.enableCategoriesCache && isCacheValid(paramCategory)) {
/*  724 */       return this.cache.getOrDefault(paramCategory.getName(), new ArrayList<>());
/*      */     }
/*      */     
/*  727 */     IStorage iStorage = getStorage();
/*  728 */     List list = (List)iStorage.getItems(this.plugin, StorageType.STORAGE).stream().filter(paramAuctionItem -> (paramAuctionItem != null && !paramAuctionItem.isAlreadyBuying() && paramAuctionItem.canBuy())).collect(Collectors.toList());
/*      */     
/*  730 */     ElapsedTime elapsedTime = new ElapsedTime("Sort AuctionItem category " + paramCategory.getName());
/*  731 */     elapsedTime.start();
/*  732 */     List<AuctionItem> list1 = (List)list.stream().filter(paramAuctionItem -> paramAuctionItem.getCategories().contains(paramCategory)).collect(Collectors.toList());
/*  733 */     elapsedTime.endDisplay();
/*      */     
/*  735 */     if (AuctionConfiguration.enableCategoriesCache) {
/*  736 */       this.cache.put(paramCategory.getName(), list1);
/*  737 */       this.lastUpdate.put(paramCategory.getName(), Long.valueOf(System.currentTimeMillis()));
/*      */     } 
/*      */     
/*  740 */     return list1;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void removeAll(Player paramPlayer, StorageType paramStorageType) {
/*  746 */     IStorage iStorage = getStorage();
/*  747 */     List<AuctionItem> list = null;
/*  748 */     switch (paramStorageType) {
/*      */       case BUY:
/*  750 */         list = getBuying(paramPlayer);
/*      */         break;
/*      */       case EXPIRE:
/*  753 */         list = getExpire(paramPlayer);
/*      */         break;
/*      */       case STORAGE:
/*  756 */         list = getItems(paramPlayer);
/*      */         break;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  762 */     if (list == null) {
/*      */       return;
/*      */     }
/*      */     
/*  766 */     if (list.size() == 0) {
/*  767 */       if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  768 */         this.plugin.getzMenuHandler().update(paramPlayer);
/*      */       } else {
/*  770 */         this.plugin.getInventoryManager().update(paramPlayer);
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/*  775 */     for (AuctionItem auctionItem : list) {
/*      */       
/*  777 */       if (!auctionItem.hasFreeSpace(paramPlayer)) {
/*  778 */         message((CommandSender)paramPlayer, Message.INVENTORY_FULL, new Object[0]);
/*      */         
/*      */         return;
/*      */       } 
/*  782 */       if (auctionItem.getStorageType() == StorageType.STORAGE && (!auctionItem.canBuy() || auctionItem.isAlreadyBuying())) {
/*      */         continue;
/*      */       }
/*      */       
/*  786 */       AuctionRemoveEvent auctionRemoveEvent = new AuctionRemoveEvent(paramPlayer, auctionItem, paramStorageType);
/*  787 */       auctionRemoveEvent.call();
/*  788 */       if (auctionRemoveEvent.isCancelled())
/*      */         return; 
/*  790 */       iStorage.removeItem(this.plugin, auctionItem, paramStorageType);
/*  791 */       auctionItem.setAlreadyBuying(true);
/*  792 */       auctionItem.setCanBuy(false);
/*      */       
/*  794 */       AuctionType auctionType = auctionItem.getType();
/*  795 */       if (auctionType.equals(AuctionType.INVENTORY)) {
/*  796 */         auctionItem.getItemStacks().forEach(paramItemStack -> give(paramPlayer, paramItemStack));
/*      */       } else {
/*  798 */         give(paramPlayer, auctionItem.getItemStack());
/*      */       } 
/*      */       
/*  801 */       String str = "%s remove x%s %s form " + paramStorageType.name().toLowerCase();
/*  802 */       LoggerManager.getInstance().log(str, new Object[] { paramPlayer.getName(), Integer.valueOf(auctionItem.getItemStack().getAmount()), this.plugin.translateItemStack(auctionItem.getItemStack()) });
/*      */     } 
/*      */ 
/*      */     
/*  806 */     if (AuctionConfiguration.enableCacheItems && this.cacheAuctionItemList != null) {
/*  807 */       this.cacheAuctionItemList.invalid();
/*      */     }
/*      */     
/*  810 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  811 */       this.plugin.getzMenuHandler().update(paramPlayer);
/*      */     } else {
/*  813 */       this.plugin.getInventoryManager().update(paramPlayer);
/*      */     } 
/*      */     
/*  816 */     clearCache();
/*  817 */     this.plugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void onNPCRequest(Player paramPlayer, String paramString) {
/*  823 */     String str = this.citizenNames.getOrDefault(paramString, null);
/*      */     
/*  825 */     if (str == null)
/*      */       return; 
/*  827 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  828 */       this.plugin.getzMenuHandler().openInventory(paramPlayer, str);
/*      */       
/*      */       return;
/*      */     } 
/*  832 */     Optional<Inventory> optional = this.plugin.getInventories().getInventoryByName(str);
/*  833 */     if (!optional.isPresent()) {
/*  834 */       message((CommandSender)paramPlayer, "§cImpossible de trouver l'inventaire %name%", new Object[] { "%name%", paramString });
/*      */       
/*      */       return;
/*      */     } 
/*      */     
/*  839 */     Inventory inventory = optional.get();
/*  840 */     CommandObject commandObject = new CommandObject("zauctionhouse", new ArrayList(), inventory, null, "no description");
/*  841 */     open(paramPlayer, (Command)commandObject);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isBlacklist(OfflinePlayer paramOfflinePlayer) {
/*  846 */     return (paramOfflinePlayer != null && BlacklistPlayers.blacklistPlayers.contains(paramOfflinePlayer.getUniqueId()));
/*      */   }
/*      */ 
/*      */   
/*      */   public void blacklistPlayer(OfflinePlayer paramOfflinePlayer) {
/*  851 */     if (!isBlacklist(paramOfflinePlayer)) {
/*  852 */       BlacklistPlayers.blacklistPlayers.add(paramOfflinePlayer.getUniqueId());
/*      */     }
/*      */   }
/*      */   
/*      */   public void unBlacklistPlayer(OfflinePlayer paramOfflinePlayer) {
/*  857 */     BlacklistPlayers.blacklistPlayers.remove(paramOfflinePlayer.getUniqueId());
/*      */   }
/*      */ 
/*      */   
/*      */   public void openConfiguration(Player paramPlayer, String paramString) {
/*  862 */     createInventory(this.plugin, paramPlayer, EnumInventory.INVENTORY_CONFIG, 1, new Object[] { paramString });
/*      */   }
/*      */ 
/*      */   
/*      */   public void showHistory(CommandSender paramCommandSender, OfflinePlayer paramOfflinePlayer, int paramInt, HistoryType paramHistoryType) {
/*  867 */     TransactionManager transactionManager = getTransaction();
/*  868 */     List list = transactionManager.getTransactions(paramOfflinePlayer, paramHistoryType);
/*  869 */     this.plugin.debug("Transactions for " + paramOfflinePlayer.getName() + ": " + list.size() + " - " + paramHistoryType);
/*  870 */     if (list.size() == 0) {
/*  871 */       message(paramCommandSender, (paramHistoryType == HistoryType.BOTH) ? Message.TRANSACTION_EMPTY_BOTH : ((paramHistoryType == HistoryType.SALE) ? Message.TRANSACTION_EMPTY_SALE : Message.TRANSACTION_EMPTY_PURCHASE), new Object[0]);
/*      */     } else {
/*      */       
/*  874 */       int i = AuctionConfiguration.transactionPageSize;
/*  875 */       i = (i <= 0) ? 1 : i;
/*      */       
/*  877 */       this.plugin.debug("Page: " + paramInt);
/*  878 */       this.plugin.debug("PageSize: " + i);
/*      */       
/*  880 */       int j = getMaxPage(list, i);
/*  881 */       int k = paramInt + 1;
/*  882 */       paramInt = (paramInt < 1) ? 1 : Math.min(paramInt, j);
/*  883 */       this.plugin.debug("MaxPage: " + j);
/*      */       
/*  885 */       Pagination pagination = new Pagination();
/*  886 */       list.sort(Comparator.comparingLong(Transaction::getDate).reversed());
/*  887 */       list = pagination.paginate(list, i, paramInt);
/*      */       
/*  889 */       List list1 = Message.TRANSACTION_MESSAGE_LIST.getMessages();
/*  890 */       boolean bool = false;
/*  891 */       if (bool == ((paramCommandSender instanceof org.bukkit.command.ConsoleCommandSender || !paramCommandSender.equals(paramOfflinePlayer)) ? true : false)) {
/*  892 */         list1 = Message.TRANSACTION_MESSAGE_SHOW.getMessages();
/*      */       }
/*  894 */       for (String str : list1) {
/*      */         
/*  896 */         if (str.equalsIgnoreCase("%content%")) {
/*      */           
/*  898 */           for (Transaction transaction : list) {
/*      */             String str2;
/*  900 */             HistoryType historyType = (paramHistoryType == HistoryType.BOTH) ? (transaction.getSeller().equals(paramOfflinePlayer.getUniqueId()) ? HistoryType.SALE : HistoryType.PURCHASE) : paramHistoryType;
/*      */             
/*  902 */             String str1 = transaction.getItemStack();
/*      */ 
/*      */             
/*  905 */             int m = 0;
/*  906 */             if (str1.contains(";")) {
/*      */               
/*  908 */               str2 = Arrays.<String>stream(str1.split(";")).map(paramString -> paramZAuctionManager.decode(paramString)).map(paramItemStack -> "x" + paramItemStack.getAmount() + " " + this.plugin.translateItemStack(paramItemStack)).collect(Collectors.joining(" "));
/*      */             } else {
/*      */               
/*  911 */               ItemStack itemStack = decode(str1);
/*  912 */               str2 = this.plugin.translateItemStack(itemStack);
/*  913 */               m = itemStack.getAmount();
/*      */             } 
/*      */             
/*  916 */             Date date = new Date(transaction.getDate());
/*  917 */             SimpleDateFormat simpleDateFormat = new SimpleDateFormat(AuctionConfiguration.transactionDateFormat);
/*      */             
/*  919 */             Message message = (historyType == HistoryType.SALE) ? Message.TRANSACTION_MESSAGE_CONTENT_SALE : Message.TRANSACTION_MESSAGE_CONTENT_PURCHASE;
/*      */             
/*  921 */             String str3 = (transaction.getSeller().equals(paramOfflinePlayer.getUniqueId()) && !bool) ? Message.TRANSACTION_MESSAGE_CONTENT_YOU.getMessage() : Bukkit.getOfflinePlayer(transaction.getSeller()).getName();
/*      */             
/*  923 */             String str4 = (transaction.getBuyer().equals(paramOfflinePlayer.getUniqueId()) && !bool) ? Message.TRANSACTION_MESSAGE_CONTENT_YOU.getMessage() : Bukkit.getOfflinePlayer(transaction.getBuyer()).getName();
/*      */             
/*  925 */             Optional<AuctionEconomy> optional = this.plugin.getEconomyManager().getEconomy(transaction.getEconomy());
/*      */             
/*  927 */             if (optional.isPresent()) {
/*  928 */               AuctionEconomy auctionEconomy = optional.get();
/*  929 */               messageWO(paramCommandSender, message, new Object[] { "%amount%", Integer.valueOf(m), "%item%", str2, "%player%", paramOfflinePlayer.getName(), "%price%", auctionEconomy.format(String.valueOf(transaction.getPrice()), transaction.getPrice()), "%date%", simpleDateFormat.format(date), "%buyer%", str4, "%seller%", str3 });
/*      */             } 
/*      */           } 
/*      */           
/*      */           continue;
/*      */         } 
/*  935 */         messageWO(paramCommandSender, str, new Object[] { "%page%", Integer.valueOf(paramInt), "%maxPage%", Integer.valueOf(j), "%nextPage%", Integer.valueOf(k), "%type%", name(paramHistoryType.name()), "%player%", paramOfflinePlayer.getName() });
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void createSellInventory(Player paramPlayer, long paramLong, AuctionEconomy paramAuctionEconomy, Command paramCommand) {
/*  942 */     if (!AuctionConfiguration.enablePlugin) {
/*  943 */       message((CommandSender)paramPlayer, Message.PLUGIN_DISABLE, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  947 */     if (this.bannedWorlds.contains(paramPlayer.getWorld().getName())) {
/*  948 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  952 */     if (isBlacklist((OfflinePlayer)paramPlayer)) {
/*  953 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  957 */     long l1 = count(paramPlayer, StorageType.STORAGE);
/*  958 */     long l2 = getMaxSellPerPermission((Permissible)paramPlayer);
/*  959 */     if (l1 >= l2) {
/*  960 */       message((CommandSender)paramPlayer, Message.SELL_ITEMS_ERROR, new Object[] { "%max%", Long.valueOf(l2) });
/*  961 */     } else if (paramLong > AuctionConfiguration.maxPrice) {
/*  962 */       message((CommandSender)paramPlayer, Message.SELL_MAX_PRICE, new Object[] { "%max%", toPrice(AuctionConfiguration.maxPrice) });
/*  963 */     } else if (paramLong < AuctionConfiguration.minPrice) {
/*  964 */       message((CommandSender)paramPlayer, Message.SELL_MIN_PRICE, new Object[] { "%min%", toPrice(AuctionConfiguration.minPrice) });
/*  965 */     } else if (!AuctionConfiguration.creativeSell && paramPlayer.getGameMode().equals(GameMode.CREATIVE)) {
/*  966 */       message((CommandSender)paramPlayer, Message.SELL_CREATIVE_ERROR, new Object[0]);
/*  967 */     } else if (this.bannedWorlds.contains(paramPlayer.getWorld().getName())) {
/*  968 */       message((CommandSender)paramPlayer, Message.SELL_CREATIVE_ERROR, new Object[0]);
/*      */     } else {
/*      */       
/*  971 */       Inventory inventory = paramCommand.getInventory();
/*      */       
/*  973 */       ZInventoryManager zInventoryManager = this.plugin.getInventoryManager();
/*  974 */       zInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, new ArrayList(), paramCommand, null, Long.valueOf(paramLong), paramAuctionEconomy });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void search(Player paramPlayer, String paramString) {
/*  981 */     if (!AuctionConfiguration.enablePlugin) {
/*  982 */       message((CommandSender)paramPlayer, Message.PLUGIN_DISABLE, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  986 */     if (this.bannedWorlds.contains(paramPlayer.getWorld().getName())) {
/*  987 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  991 */     if (isBlacklist((OfflinePlayer)paramPlayer)) {
/*  992 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/*  996 */     setPlayerWord((OfflinePlayer)paramPlayer, paramString);
/*      */     
/*  998 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*  999 */       this.plugin.getzMenuHandler().openSearch(paramPlayer);
/*      */     } else {
/* 1001 */       Inventory inventory = this.plugin.getInventories().getInventory(InventoryName.SEARCH);
/* 1002 */       CommandObject commandObject = new CommandObject("search", new ArrayList(), inventory, null, "no description");
/* 1003 */       ZInventoryManager zInventoryManager = this.plugin.getInventoryManager();
/* 1004 */       zInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, new ArrayList(), commandObject, null });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public List<AuctionItem> getSearch(Player paramPlayer, String paramString) {
/* 1010 */     FilterManager filterManager = this.plugin.getFilterManager();
/* 1011 */     Optional optional = filterManager.find(paramString);
/* 1012 */     return (List<AuctionItem>)getSortItems(paramPlayer).stream().filter(paramAuctionItem -> paramAuctionItem.match(paramOptional, paramString)).collect(Collectors.toList());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerWord(OfflinePlayer paramOfflinePlayer, String paramString) {
/* 1017 */     getCache(paramOfflinePlayer).setSearchWord(paramString);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPlayerWord(OfflinePlayer paramOfflinePlayer) {
/* 1022 */     return getCache(paramOfflinePlayer).getSearchWord();
/*      */   }
/*      */ 
/*      */   
/*      */   public void open(Player paramPlayer, Command paramCommand, InventoryName paramInventoryName) {
/* 1027 */     if (!AuctionConfiguration.enablePlugin) {
/* 1028 */       message((CommandSender)paramPlayer, Message.PLUGIN_DISABLE, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/* 1032 */     if (this.bannedWorlds.contains(paramPlayer.getWorld().getName())) {
/* 1033 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/* 1037 */     if (isBlacklist((OfflinePlayer)paramPlayer)) {
/* 1038 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION, new Object[0]);
/*      */       
/*      */       return;
/*      */     } 
/* 1042 */     Inventory inventory = this.plugin.getInventories().getInventory(paramInventoryName);
/* 1043 */     ZInventoryManager zInventoryManager = this.plugin.getInventoryManager();
/*      */     
/* 1045 */     ArrayList<Inventory> arrayList = new ArrayList();
/* 1046 */     arrayList.add(paramCommand.getInventory());
/*      */     
/* 1048 */     zInventoryManager.createInventory(EnumInventory.INVENTORY_DEFAULT, paramPlayer, 1, new Object[] { inventory, arrayList, paramCommand, null });
/*      */   }
/*      */ 
/*      */   
/*      */   public void setCurrentCategory(Player paramPlayer, Category paramCategory) {
/* 1053 */     getCache((OfflinePlayer)paramPlayer).setCategory(paramCategory);
/*      */   }
/*      */ 
/*      */   
/*      */   public Optional<Category> getCurrentCategory(Player paramPlayer) {
/* 1058 */     return Optional.ofNullable(getCache((OfflinePlayer)paramPlayer).getCategory());
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public Optional<Priority> getPriority(Permissible paramPermissible) {
/* 1064 */     if (!AuctionConfiguration.enablePrioritySort) return Optional.empty(); 
/* 1065 */     return this.priorities.stream().filter(paramPriority -> paramPermissible.hasPermission(paramPriority.getPermission())).max(Comparator.comparingInt(Priority::getPriority));
/*      */   }
/*      */ 
/*      */   
/*      */   public Optional<Priority> getPriority(int paramInt) {
/* 1070 */     return Optional.ofNullable(this.priorityMap.getOrDefault(Integer.valueOf(paramInt), null));
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID getRandomUniqueId() {
/*      */     try {
/* 1076 */       UUID uUID = generateUUIDFromTimestamp(System.currentTimeMillis());
/* 1077 */       IStorage iStorage = getStorage();
/* 1078 */       return iStorage.uuidExist(uUID) ? getRandomUniqueId() : uUID;
/* 1079 */     } catch (Exception exception) {
/* 1080 */       return UUID.randomUUID();
/*      */     } 
/*      */   }
/*      */   
/*      */   private UUID generateUUIDFromTimestamp(long paramLong) {
/*      */     try {
/* 1086 */       long l1 = UUID.randomUUID().getMostSignificantBits();
/*      */       
/* 1088 */       long l2 = paramLong << 32L;
/* 1089 */       l2 |= l1 >>> 32L & 0xFFFFL;
/*      */       
/* 1091 */       long l3 = l1 << 32L;
/* 1092 */       l3 |= paramLong & 0xFFFFFFFFL;
/*      */       
/* 1094 */       return new UUID(l2, l3);
/* 1095 */     } catch (Exception exception) {
/* 1096 */       return UUID.randomUUID();
/*      */     } 
/*      */   }
/*      */   
/*      */   private boolean isCacheValid(Category paramCategory) {
/* 1101 */     Long long_ = this.lastUpdate.get(paramCategory.getName());
/* 1102 */     return (long_ != null && System.currentTimeMillis() - long_.longValue() < AuctionConfiguration.cacheDurationCategory);
/*      */   }
/*      */ 
/*      */   
/*      */   public void clearCache() {
/* 1107 */     this.cache.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public String getPriceFormat(long paramLong) {
/* 1112 */     return toPrice(paramLong);
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getBannedWorlds() {
/* 1117 */     return this.bannedWorlds;
/*      */   }
/*      */ 
/*      */   
/*      */   public CacheAuctionItem getCacheAuctionItemList() {
/* 1122 */     return this.cacheAuctionItemList;
/*      */   }
/*      */ 
/*      */   
/*      */   public List<String> getSearchElements() {
/* 1127 */     if (this.iStorage == null) return new ArrayList<>(); 
/* 1128 */     return (List<String>)this.iStorage.getItems(this.plugin, StorageType.STORAGE).stream().map(AuctionItem::getMaterialName).map(String::toLowerCase).distinct().collect(Collectors.toList());
/*      */   }
/*      */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ZAuctionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */