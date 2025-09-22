/*     */ package fr.maxlego08.zauctionhouse.zmenu;
/*     */ 
/*     */ import fr.maxlego08.menu.api.ButtonManager;
/*     */ import fr.maxlego08.menu.api.Inventory;
/*     */ import fr.maxlego08.menu.api.InventoryManager;
/*     */ import fr.maxlego08.menu.api.exceptions.InventoryException;
/*     */ import fr.maxlego08.menu.api.loader.ButtonLoader;
/*     */ import fr.maxlego08.menu.api.loader.NoneLoader;
/*     */ import fr.maxlego08.menu.api.pattern.PatternManager;
/*     */ import fr.maxlego08.menu.api.utils.MetaUpdater;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.MessageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionAdminRemoveEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionOpenEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPostBuyEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionRemoveEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionRetrieveEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionSellEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.messages.IMessage;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZBuyConfirmButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZChangeSortButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZEconomyItemButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZSearchButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZSellBuyButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZSellShowButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZSellSlotButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZShowSellerButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.ZUpdateButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.multiple.ZEBButton;
/*     */ import fr.maxlego08.zauctionhouse.buttons.multiple.ZEBIButton;
/*     */ import fr.maxlego08.zauctionhouse.loader.AdminRemoveButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.AuctionButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.CategoryButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.CategoryHomeLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.CategoryListLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.ChangeSortingLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.ClaimLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.EconomyButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.RemoveAllButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.RemoveConfirmButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.loader.ShowButtonLoader;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.traqueur.zauctionhouse.redis.sync.ZSyncManager;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Stream;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ZMenuHandler
/*     */   extends ZUtils implements Listener {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private final PatternManager patternManager;
/*     */   private final ButtonManager buttonManager;
/*     */   private final InventoryManager inventoryManager;
/*     */   private final boolean isMiniMessage;
/*     */   private MiniMessageHelper miniMessageHelper;
/*     */   
/*     */   public ZMenuHandler(ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean) {
/*  87 */     this.plugin = paramZAuctionPlugin;
/*  88 */     this.isMiniMessage = paramBoolean;
/*  89 */     this.buttonManager = (ButtonManager)paramZAuctionPlugin.getProvider(ButtonManager.class);
/*  90 */     this.inventoryManager = (InventoryManager)paramZAuctionPlugin.getProvider(InventoryManager.class);
/*  91 */     this.patternManager = (PatternManager)paramZAuctionPlugin.getProvider(PatternManager.class);
/*  92 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*     */     
/*  94 */     if (paramBoolean) {
/*  95 */       this.miniMessageHelper = new MiniMessageHelper(paramZAuctionPlugin);
/*     */     }
/*     */   }
/*     */   
/*     */   public void loadButtons() {
/* 100 */     this.buttonManager.unregisters((Plugin)this.plugin);
/*     */     
/* 102 */     this.buttonManager.register((ButtonLoader)new EconomyButtonLoader(this.plugin));
/*     */     
/* 104 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.AUCTION, "auction"));
/* 105 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.EXPIRE, "expire"));
/* 106 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.ITEMS, "items"));
/* 107 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.BUYING, "buying"));
/* 108 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.SEARCH, "search"));
/* 109 */     this.buttonManager.register((ButtonLoader)new AuctionButtonLoader(this.plugin, InventoryType.CATEGORY, "category"));
/*     */     
/* 111 */     this.buttonManager.register((ButtonLoader)new CategoryButtonLoader(this.plugin));
/*     */     
/* 113 */     this.buttonManager.register((ButtonLoader)new RemoveAllButtonLoader(this.plugin, InventoryType.ITEMS, "items"));
/* 114 */     this.buttonManager.register((ButtonLoader)new RemoveAllButtonLoader(this.plugin, InventoryType.BUYING, "buying"));
/* 115 */     this.buttonManager.register((ButtonLoader)new RemoveAllButtonLoader(this.plugin, InventoryType.EXPIRE, "expire"));
/*     */     
/* 117 */     this.buttonManager.register((ButtonLoader)new ShowButtonLoader(this.plugin, InventoryType.AUCTION, "remove"));
/* 118 */     this.buttonManager.register((ButtonLoader)new ShowButtonLoader(this.plugin, InventoryType.BUY_CONFIRM, "buy"));
/* 119 */     this.buttonManager.register((ButtonLoader)new ShowButtonLoader(this.plugin, InventoryType.ADMIN_REMOVE, "admin"));
/*     */     
/* 121 */     this.buttonManager.register((ButtonLoader)new ClaimLoader(this.plugin));
/* 122 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZSellBuyButton.class, "zauctionhouse_sell_inventory_accept"));
/* 123 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZSellSlotButton.class, "zauctionhouse_sell_inventory_slot"));
/* 124 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZChangeSortButton.class, "zauctionhouse_change_sort"));
/* 125 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZSellShowButton.class, "zauctionhouse_show_items"));
/* 126 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZBuyConfirmButton.class, "zauctionhouse_buy_confirm"));
/* 127 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZUpdateButton.class, "zauctionhouse_update"));
/* 128 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZEconomyItemButton.class, "zauctionhouse_economy_item"));
/* 129 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZShowSellerButton.class, "zauctionhouse_show_seller"));
/* 130 */     this.buttonManager.register((ButtonLoader)new AdminRemoveButtonLoader(this.plugin));
/*     */     
/* 132 */     this.buttonManager.register((ButtonLoader)new RemoveConfirmButtonLoader(this.plugin, false));
/*     */     
/* 134 */     this.buttonManager.register((ButtonLoader)new RemoveConfirmButtonLoader(this.plugin, true));
/* 135 */     this.buttonManager.register((ButtonLoader)new CategoryListLoader(this.plugin));
/* 136 */     this.buttonManager.register((ButtonLoader)new ChangeSortingLoader(this.plugin));
/* 137 */     this.buttonManager.register((ButtonLoader)new CategoryHomeLoader(this.plugin));
/*     */ 
/*     */     
/* 140 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZEBButton.class, "ZAUCTIONHOUSE_EXPIRE_AND_BUYING"));
/* 141 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZEBIButton.class, "ZAUCTIONHOUSE_EXPIRE_AND_BUYING_AND_ITEMS"));
/* 142 */     this.buttonManager.register((ButtonLoader)new NoneLoader((Plugin)this.plugin, ZSearchButton.class, "ZAUCTIONHOUSE_SEARCH_INPUT"));
/*     */   }
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
/*     */   public void message(CommandSender paramCommandSender, IMessage paramIMessage, Object... paramVarArgs) {
/* 156 */     MetaUpdater metaUpdater = this.inventoryManager.getMeta();
/*     */     
/* 158 */     if (paramCommandSender instanceof org.bukkit.command.ConsoleCommandSender) {
/* 159 */       if (!paramIMessage.getMessages().isEmpty())
/* 160 */       { paramIMessage.getMessages().forEach(paramString -> message(paramCommandSender, getMessage(paramString, paramArrayOfObject), new Object[0])); }
/* 161 */       else { message(paramCommandSender, Message.PREFIX.msg() + getMessage(paramIMessage, paramVarArgs), new Object[0]); }
/*     */     
/*     */     } else {
/* 164 */       String str1, str2; int i, j, k; Player player = (Player)paramCommandSender;
/* 165 */       switch (paramIMessage.getType()) {
/*     */         case ACTION:
/* 167 */           actionMessage(player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TCHAT_AND_ACTION:
/* 170 */           actionMessage(player, paramIMessage, paramVarArgs);
/* 171 */           sendTchatMessage(metaUpdater, player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TCHAT:
/*     */         case WITHOUT_PREFIX:
/* 175 */           sendTchatMessage(metaUpdater, player, paramIMessage, paramVarArgs);
/*     */           break;
/*     */         case TITLE:
/* 178 */           str1 = paramIMessage.getTitle();
/* 179 */           str2 = paramIMessage.getSubTitle();
/* 180 */           i = paramIMessage.getStart();
/* 181 */           j = paramIMessage.getTime();
/* 182 */           k = paramIMessage.getEnd();
/* 183 */           title(player, papi(getMessage(str1, paramVarArgs), player), papi(getMessage(str2, paramVarArgs), player), i, j, k);
/*     */           break;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendTchatMessage(MetaUpdater paramMetaUpdater, Player paramPlayer, IMessage paramIMessage, Object... paramVarArgs) {
/* 193 */     if (!AuctionConfiguration.enableMiniMessageInMessage) {
/*     */       
/* 195 */       sendTchatMessage(paramPlayer, paramIMessage, paramVarArgs);
/*     */     }
/* 197 */     else if (paramIMessage.getMessages().size() > 0) {
/* 198 */       paramIMessage.getMessages().forEach(paramString -> paramMetaUpdater.sendMessage((CommandSender)paramPlayer, papi(Message.PREFIX.msg() + getMessage(paramString, paramArrayOfObject), paramPlayer)));
/*     */     } else {
/* 200 */       paramMetaUpdater.sendMessage((CommandSender)paramPlayer, papi(((paramIMessage.getType() == MessageType.WITHOUT_PREFIX) ? "" : Message.PREFIX.msg()) + getMessage(paramIMessage, paramVarArgs), paramPlayer));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadInventories() {
/* 207 */     File file = new File(this.plugin.getDataFolder(), "inventories");
/* 208 */     if (!file.exists()) {
/* 209 */       file.mkdir();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 214 */     loadPatterns();
/*     */     
/* 216 */     this.inventoryManager.deleteInventories((Plugin)this.plugin);
/* 217 */     files(file, paramFile -> {
/*     */           try {
/*     */             this.inventoryManager.loadInventory((Plugin)this.plugin, paramFile);
/* 220 */           } catch (InventoryException inventoryException) {
/*     */             inventoryException.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */   private void files(File paramFile, Consumer<File> paramConsumer) {
/*     */     
/* 227 */     try { Stream<Path> stream = Files.walk(Paths.get(paramFile.getPath(), new String[0]), new java.nio.file.FileVisitOption[0]); 
/* 228 */       try { stream.skip(1L).map(Path::toFile).filter(File::isFile).filter(paramFile -> paramFile.getName().endsWith(".yml")).forEach(paramConsumer);
/* 229 */         if (stream != null) stream.close();  } catch (Throwable throwable) { if (stream != null) try { stream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException)
/* 230 */     { iOException.printStackTrace(); }
/*     */   
/*     */   }
/*     */   
/*     */   public void clearAlreadyBuying(Player paramPlayer) {
/* 235 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 236 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/* 237 */     if (auctionItem != null && auctionItem.isAlreadyBuying()) {
/* 238 */       auctionItem.setAlreadyBuying(false);
/* 239 */       if (this.plugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null) {
/* 240 */         ((ZSyncManager)this.plugin.getProvider(ZSyncManager.class)).publishGiveUpInteractAuction(auctionItem);
/*     */       }
/* 242 */       playerCache.setAuctionItem(null);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void openAuction(Player paramPlayer) {
/* 248 */     if (!AuctionConfiguration.enablePlugin) {
/* 249 */       message((CommandSender)paramPlayer, Message.PLUGIN_DISABLE.getIMessage(), new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 253 */     if (this.plugin.getAuctionManager().getBannedWorld().contains(paramPlayer.getWorld().getName())) {
/* 254 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED.getIMessage(), new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 258 */     if (this.plugin.getAuctionManager().isBlacklist((OfflinePlayer)paramPlayer)) {
/* 259 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION.getIMessage(), new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 263 */     AuctionOpenEvent auctionOpenEvent = new AuctionOpenEvent(paramPlayer);
/* 264 */     auctionOpenEvent.call();
/*     */     
/* 266 */     if (auctionOpenEvent.isCancelled())
/*     */       return; 
/* 268 */     this.inventoryManager.openInventory(paramPlayer, (Plugin)this.plugin, InventoryName.AUCTION.getName());
/*     */   }
/*     */   
/*     */   private void openInventory(Player paramPlayer, AuctionItem paramAuctionItem, InventoryName paramInventoryName) {
/* 272 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 273 */     playerCache.setAuctionItem(paramAuctionItem);
/*     */     
/* 275 */     ArrayList arrayList = new ArrayList();
/* 276 */     Objects.requireNonNull(arrayList); this.inventoryManager.getCurrentPlayerInventory(paramPlayer).ifPresent(arrayList::add);
/* 277 */     Optional<Inventory> optional = this.inventoryManager.getInventory((Plugin)this.plugin, paramInventoryName.getName());
/*     */     
/* 279 */     if (optional.isPresent()) {
/* 280 */       this.inventoryManager.openInventory(paramPlayer, optional.get(), 1, arrayList);
/*     */     } else {
/* 282 */       this.plugin.getLogger().info("TODO ERROR MESSAGE " + paramInventoryName);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openSellShow(Player paramPlayer, AuctionItem paramAuctionItem, int paramInt) {
/* 287 */     openInventory(paramPlayer, paramAuctionItem, InventoryName.SELL_SHOW);
/*     */   }
/*     */   
/*     */   public void openAdminRemove(Player paramPlayer, AuctionItem paramAuctionItem) {
/* 291 */     if (this.plugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null && 
/* 292 */       !((ZSyncManager)this.plugin.getProvider(ZSyncManager.class)).publishInteractAuction(paramPlayer, paramAuctionItem)) {
/*     */       return;
/*     */     }
/* 295 */     openInventory(paramPlayer, paramAuctionItem, InventoryName.ADMIN_REMOVE);
/*     */   }
/*     */   
/*     */   public void openRemoveConfirm(Player paramPlayer, AuctionItem paramAuctionItem) {
/* 299 */     if (this.plugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null && 
/* 300 */       !((ZSyncManager)this.plugin.getProvider(ZSyncManager.class)).publishInteractAuction(paramPlayer, paramAuctionItem)) {
/*     */       return;
/*     */     }
/*     */     
/* 304 */     openInventory(paramPlayer, paramAuctionItem, InventoryName.REMOVE_CONFIRM);
/*     */   }
/*     */   
/*     */   public void openBuyConfirm(Player paramPlayer, AuctionItem paramAuctionItem) {
/* 308 */     if (this.plugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null && 
/* 309 */       !((ZSyncManager)this.plugin.getProvider(ZSyncManager.class)).publishInteractAuction(paramPlayer, paramAuctionItem)) {
/*     */       return;
/*     */     }
/*     */     
/* 313 */     boolean bool = (paramAuctionItem.getType() == AuctionType.INVENTORY || (paramAuctionItem.getType() == AuctionType.DEFAULT && paramAuctionItem.getItemStack().getType().name().endsWith("SHULKER_BOX"))) ? true : false;
/* 314 */     openInventory(paramPlayer, paramAuctionItem, bool ? InventoryName.BUY_CONFIRM_INVENTORY : InventoryName.BUY_CONFIRM);
/*     */   }
/*     */   
/*     */   public void update(Player paramPlayer) {
/* 318 */     this.inventoryManager.updateInventory(paramPlayer);
/*     */   }
/*     */   
/*     */   public void openSearch(Player paramPlayer) {
/* 322 */     this.inventoryManager.openInventory(paramPlayer, (Plugin)this.plugin, InventoryName.SEARCH.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public void openCategory(Player paramPlayer, Category paramCategory) {
/* 327 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 328 */     playerCache.setCategory(paramCategory);
/* 329 */     this.plugin.getAuctionManager().setCurrentCategory(paramPlayer, paramCategory);
/*     */     
/* 331 */     ArrayList arrayList = new ArrayList();
/* 332 */     Objects.requireNonNull(arrayList); this.inventoryManager.getCurrentPlayerInventory(paramPlayer).ifPresent(arrayList::add);
/* 333 */     Optional<Inventory> optional = this.inventoryManager.getInventory((Plugin)this.plugin, InventoryName.CATEGORY.getName());
/*     */     
/* 335 */     if (optional.isPresent()) {
/* 336 */       this.inventoryManager.openInventory(paramPlayer, optional.get(), 1, arrayList);
/*     */     } else {
/* 338 */       paramPlayer.sendMessage("§c" + InventoryName.CATEGORY.getName() + ".yml inventory was not found !");
/* 339 */       Logger.info(InventoryName.CATEGORY.getName() + ".yml inventory was not found !", Logger.LogType.ERROR);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void openSellInventory(Player paramPlayer) {
/* 344 */     this.inventoryManager.openInventory(paramPlayer, (Plugin)this.plugin, InventoryName.SELL.getName());
/*     */   }
/*     */   
/*     */   private void registerDefaultPatterns() {
/* 348 */     ArrayList<String> arrayList = new ArrayList();
/* 349 */     arrayList.add("patterns/auction.yml");
/* 350 */     arrayList.add("patterns/back.yml");
/* 351 */     arrayList.add("patterns/decoration.yml");
/* 352 */     arrayList.add("patterns/pagination.yml");
/*     */     
/* 354 */     arrayList.forEach(paramString -> {
/*     */           if (!(new File(this.plugin.getDataFolder(), paramString)).exists()) {
/*     */             if (NmsVersion.nmsVersion.isNewMaterial()) {
/*     */               this.plugin.saveResource(paramString.replace("patterns/", "patterns/1_13/"), paramString, false);
/*     */             } else {
/*     */               this.plugin.saveResource(paramString, false);
/*     */             } 
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadPatterns() {
/* 367 */     File file = new File(this.plugin.getDataFolder(), "patterns");
/* 368 */     if (!file.exists()) {
/* 369 */       file.mkdir();
/* 370 */       registerDefaultPatterns();
/*     */     } 
/*     */     
/* 373 */     files(file, paramFile -> {
/*     */           try {
/*     */             this.patternManager.loadPattern(paramFile);
/* 376 */           } catch (InventoryException inventoryException) {
/*     */             inventoryException.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private void updatePlayers() {
/* 383 */     if (AuctionConfiguration.enableAutoUpdate) {
/* 384 */       Bukkit.getOnlinePlayers().forEach(paramPlayer -> schedule(paramPlayer.getLocation(), 1L, ()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPurchase(AuctionPostBuyEvent paramAuctionPostBuyEvent) {
/* 394 */     updatePlayers();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onSell(AuctionSellEvent paramAuctionSellEvent) {
/* 399 */     updatePlayers();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRetrieve(AuctionRetrieveEvent paramAuctionRetrieveEvent) {
/* 404 */     updatePlayers();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRemove(AuctionRemoveEvent paramAuctionRemoveEvent) {
/* 409 */     updatePlayers();
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onRemoveAdmin(AuctionAdminRemoveEvent paramAuctionAdminRemoveEvent) {
/* 414 */     updatePlayers();
/*     */   }
/*     */ 
/*     */   
/*     */   public void openSellEconomyChoose(Player paramPlayer, long paramLong, int paramInt) {
/* 419 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 420 */     playerCache.setSellPrice(paramLong);
/* 421 */     playerCache.setSellAmount(paramInt);
/*     */     
/* 423 */     this.inventoryManager.openInventory(paramPlayer, (Plugin)this.plugin, InventoryName.ECONOMY.getName());
/*     */   }
/*     */   
/*     */   public void openInventory(Player paramPlayer, String paramString) {
/* 427 */     this.inventoryManager.openInventory(paramPlayer, (Plugin)this.plugin, paramString);
/*     */   }
/*     */   
/*     */   public boolean isMiniMessage() {
/* 431 */     return this.isMiniMessage;
/*     */   }
/*     */   
/*     */   public void createItem(ItemStack paramItemStack, Player paramPlayer, Message paramMessage, ZAuctionItem paramZAuctionItem) {
/* 435 */     this.miniMessageHelper.createItem(paramItemStack, paramPlayer, paramMessage, (AuctionItem)paramZAuctionItem);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack loadItemStack(YamlConfiguration paramYamlConfiguration, String paramString, ItemStack paramItemStack) {
/* 440 */     if (!this.isMiniMessage) return paramItemStack;
/*     */     
/* 442 */     Material material = Material.valueOf(paramYamlConfiguration.getString(paramString + "material"));
/* 443 */     String str = paramYamlConfiguration.getString(paramString + "name");
/* 444 */     List<String> list = paramYamlConfiguration.getStringList(paramString + "lore");
/*     */     
/* 446 */     return this.miniMessageHelper.createItem(material, str, list);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zmenu\ZMenuHandler.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */