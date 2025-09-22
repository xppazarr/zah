/*     */ package fr.maxlego08.zauctionhouse;
/*     */ import fr.maxlego08.menu.api.configuration.Config;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.ConvertManager;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.IWhitelistManager;
/*     */ import fr.maxlego08.zauctionhouse.api.category.CategoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.dupe.DupeManager;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.EconomyManager;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*     */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*     */ import fr.maxlego08.zauctionhouse.api.filter.FilterManager;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.InventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.VInventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.price.PriceManager;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.CustomStorageProvider;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxManager;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.translation.TranslationManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.BlacklistPlayers;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.SearchInput;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.BlacklistManager;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.WhitelistManager;
/*     */ import fr.maxlego08.zauctionhouse.categories.ZCategoryManager;
/*     */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*     */ import fr.maxlego08.zauctionhouse.command.commands.CommandAuction;
/*     */ import fr.maxlego08.zauctionhouse.convert.ZConvertManager;
/*     */ import fr.maxlego08.zauctionhouse.dupe.DupeListener;
/*     */ import fr.maxlego08.zauctionhouse.dupe.NMSDupeManager;
/*     */ import fr.maxlego08.zauctionhouse.dupe.PDCDupeManager;
/*     */ import fr.maxlego08.zauctionhouse.economy.ZEconomyManager;
/*     */ import fr.maxlego08.zauctionhouse.filter.ZFilterManager;
/*     */ import fr.maxlego08.zauctionhouse.filter.filters.LoreFilter;
/*     */ import fr.maxlego08.zauctionhouse.filter.filters.MaterialFilter;
/*     */ import fr.maxlego08.zauctionhouse.filter.filters.NameFilter;
/*     */ import fr.maxlego08.zauctionhouse.filter.filters.SellerFilter;
/*     */ import fr.maxlego08.zauctionhouse.filter.filters.TypeFilter;
/*     */ import fr.maxlego08.zauctionhouse.inventory.InventoryLoader;
/*     */ import fr.maxlego08.zauctionhouse.inventory.VInventory;
/*     */ import fr.maxlego08.zauctionhouse.inventory.ZInventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.inventory.inventories.InventoryAddon;
/*     */ import fr.maxlego08.zauctionhouse.inventory.inventories.InventoryConfig;
/*     */ import fr.maxlego08.zauctionhouse.inventory.inventories.InventoryDefault;
/*     */ import fr.maxlego08.zauctionhouse.listener.CitizenListener;
/*     */ import fr.maxlego08.zauctionhouse.listener.HeadListener;
/*     */ import fr.maxlego08.zauctionhouse.placeholder.LocalPlaceholder;
/*     */ import fr.maxlego08.zauctionhouse.price.ZPriceManager;
/*     */ import fr.maxlego08.zauctionhouse.save.MessageLoader;
/*     */ import fr.maxlego08.zauctionhouse.search.ChatSearch;
/*     */ import fr.maxlego08.zauctionhouse.search.ProtocolSearch;
/*     */ import fr.maxlego08.zauctionhouse.storage.ZAHImplementation;
/*     */ import fr.maxlego08.zauctionhouse.storage.ZStorageManager;
/*     */ import fr.maxlego08.zauctionhouse.storage.customs.ZEssentialsProvider;
/*     */ import fr.maxlego08.zauctionhouse.tax.ZTaxManager;
/*     */ import fr.maxlego08.zauctionhouse.transaction.ZTransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.translations.ZTranslationManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.plugins.Metrics;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.plugins.Plugins;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.plugins.VersionChecker;
/*     */ import fr.maxlego08.zauctionhouse.zmenu.ZMenuHandler;
/*     */ import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.ServicePriority;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.slf4j.Logger;
/*     */ 
/*     */ public class ZAuctionPlugin extends ZPlugin implements AuctionPlugin {
/*  83 */   private final AuctionConfiguration configuration = new AuctionConfiguration();
/*  84 */   private final IBlacklistManager blacklistManager = (IBlacklistManager)new BlacklistManager();
/*  85 */   private final IWhitelistManager whitelistManager = (IWhitelistManager)new WhitelistManager();
/*  86 */   private final InventoryManager inventories = (InventoryManager)new InventoryLoader(this);
/*  87 */   private final AuctionManager auctionManager = new ZAuctionManager(this);
/*  88 */   private final CategoryManager categoryManager = (CategoryManager)new ZCategoryManager(this);
/*  89 */   private final TransactionManager transactionManager = (TransactionManager)new ZTransactionManager(this);
/*  90 */   private final ConvertManager convertManager = (ConvertManager)new ZConvertManager(this);
/*  91 */   private final FilterManager filterManager = (FilterManager)new ZFilterManager();
/*  92 */   private final MessageLoader messageLoader = new MessageLoader(this);
/*  93 */   private final TaxManager taxManager = (TaxManager)new ZTaxManager(this);
/*  94 */   private final PriceManager priceManager = (PriceManager)new ZPriceManager(this);
/*  95 */   private final EconomyManager economyManager = (EconomyManager)new ZEconomyManager(this);
/*  96 */   private final ZPlaceholderAPI placeholderAPI = new ZPlaceholderAPI(this);
/*  97 */   private final BlacklistPlayers blacklistPlayers = new BlacklistPlayers();
/*  98 */   private final ZTranslationManager translationManager = new ZTranslationManager(this);
/*     */   
/*     */   private SearchInput searchInput;
/*     */   
/*     */   private ZStorageManager storageManager;
/*     */   private DupeManager dupeManager;
/*     */   private ZMenuHandler zMenuHandler;
/*     */   private boolean isActive = false;
/*     */   private CommandAuction commandAuction;
/*     */   private CustomStorageProvider customStorageProvider;
/*     */   
/*     */   public void onEnable() {
/* 110 */     LocalPlaceholder localPlaceholder = LocalPlaceholder.getInstance();
/* 111 */     localPlaceholder.setPrefix("zauctionhouse");
/*     */     
/* 113 */     this.isActive = false;
/*     */ 
/*     */ 
/*     */     
/* 117 */     for (InventoryName inventoryName : InventoryName.values()) {
/* 118 */       registerFile(inventoryName);
/*     */     }
/* 120 */     this.dupeManager = NmsVersion.nmsVersion.isPdcVersion() ? (DupeManager)new PDCDupeManager((Plugin)this) : (DupeManager)new NMSDupeManager();
/*     */     
/* 122 */     preEnable();
/*     */ 
/*     */     
/* 125 */     getServer().getServicesManager().register(InventoryManager.class, this.inventories, (Plugin)this, ServicePriority.High);
/* 126 */     getServer().getServicesManager().register(CategoryManager.class, this.categoryManager, (Plugin)this, ServicePriority.High);
/* 127 */     getServer().getServicesManager().register(TransactionManager.class, this.transactionManager, (Plugin)this, ServicePriority.High);
/* 128 */     getServer().getServicesManager().register(ConvertManager.class, this.convertManager, (Plugin)this, ServicePriority.High);
/* 129 */     getServer().getServicesManager().register(FilterManager.class, this.filterManager, (Plugin)this, ServicePriority.High);
/* 130 */     getServer().getServicesManager().register(IBlacklistManager.class, this.blacklistManager, (Plugin)this, ServicePriority.High);
/* 131 */     getServer().getServicesManager().register(IWhitelistManager.class, this.whitelistManager, (Plugin)this, ServicePriority.High);
/* 132 */     getServer().getServicesManager().register(AuctionManager.class, this.auctionManager, (Plugin)this, ServicePriority.High);
/* 133 */     getServer().getServicesManager().register(PriceManager.class, this.priceManager, (Plugin)this, ServicePriority.High);
/* 134 */     getServer().getServicesManager().register(EconomyManager.class, this.economyManager, (Plugin)this, ServicePriority.High);
/* 135 */     getServer().getServicesManager().register(TranslationManager.class, this.translationManager, (Plugin)this, ServicePriority.High);
/*     */ 
/*     */ 
/*     */     
/* 139 */     this.filterManager.registerFilter((Filter)new SellerFilter());
/* 140 */     this.filterManager.registerFilter((Filter)new MaterialFilter());
/* 141 */     this.filterManager.registerFilter((Filter)new NameFilter());
/* 142 */     this.filterManager.registerFilter((Filter)new LoreFilter());
/* 143 */     this.filterManager.registerFilter((Filter)new TypeFilter());
/*     */     
/* 145 */     addSave((Savable)this.messageLoader);
/* 146 */     addSave((Savable)this.categoryManager);
/* 147 */     addSave((Savable)this.priceManager);
/*     */     
/* 149 */     loadFiles();
/* 150 */     this.blacklistPlayers.load(getPersist());
/* 151 */     this.configuration.load();
/*     */     
/* 153 */     this.taxManager.load(getPersist());
/*     */ 
/*     */ 
/*     */     
/* 157 */     this.auctionManager.loadConfiguration();
/* 158 */     this.categoryManager.load(null);
/* 159 */     this.translationManager.loadTranslations();
/*     */ 
/*     */ 
/*     */     
/* 163 */     registerInventory(EnumInventory.INVENTORY_DEFAULT, (VInventory)new InventoryDefault());
/* 164 */     registerInventory(EnumInventory.INVENTORY_CONFIG, (VInventory)new InventoryConfig());
/* 165 */     registerInventory(EnumInventory.INVENTORY_ADDON, (VInventory)new InventoryAddon());
/*     */     
/* 167 */     Plugin plugin = getPlugin(Plugins.HEADDATABASE);
/*     */     
/* 169 */     if (plugin != null && plugin.isEnabled() && AuctionConfiguration.enableHeadDatabaseVerification) {
/* 170 */       addListener((Listener)new HeadListener(enablePlugin()));
/*     */     } else {
/* 172 */       enablePlugin().run();
/*     */     } 
/*     */     
/* 175 */     plugin = getPlugin(Plugins.CITIZENS);
/* 176 */     if (plugin != null && plugin.isEnabled()) {
/* 177 */       addListener((Listener)new CitizenListener(this, this.auctionManager));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 182 */     addListener(new AuctionListener(this, this.transactionManager));
/*     */     
/* 184 */     new Metrics((Plugin)this, 5326);
/*     */     
/* 186 */     this.placeholderAPI.registerPlaceholders();
/*     */     
/* 188 */     postEnable();
/*     */ 
/*     */ 
/*     */     
/* 192 */     this.storageManager = new ZStorageManager(this, AuctionConfiguration.storage);
/*     */     
/* 194 */     getServer().getServicesManager().register(StorageManager.class, this.storageManager, (Plugin)this, ServicePriority.High);
/*     */     
/* 196 */     this.storageManager.load(getPersist());
/* 197 */     LoggerManager.getInstance().load(this);
/*     */     
/* 199 */     plugin = getPlugin(Plugins.HEADDATABASE);
/* 200 */     if (plugin == null || !plugin.isEnabled()) {
/* 201 */       this.isActive = true;
/*     */     }
/*     */     
/* 204 */     if (isEnable(Plugins.ZTRANSLATOR)) {
/* 205 */       getLog().log("zTranslator found. We will use for translations.");
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 210 */     this.commandAuction = new CommandAuction(this);
/* 211 */     registerCommand(AuctionConfiguration.mainCommand, (VCommand)this.commandAuction, AuctionConfiguration.subCommands);
/*     */     
/* 213 */     if (AuctionConfiguration.dupeConfig != null && AuctionConfiguration.dupeConfig.isEnable()) {
/* 214 */       addListener((Listener)new DupeListener(this.dupeManager, this));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/* 219 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*     */       
/* 221 */       if (!isEnable(Plugins.ZMENU)) {
/* 222 */         Logger.info("");
/* 223 */         Logger.info("! Attention !", Logger.LogType.ERROR);
/* 224 */         Logger.info("LOOK installation Guide on spigot: https://www.spigotmc.org/resources/63010/", Logger.LogType.ERROR);
/* 225 */         Logger.info("You must install the zMenu plugin to run zAuctionHouse!", Logger.LogType.WARNING);
/* 226 */         Logger.info("Download here: https://www.spigotmc.org/resources/zmenu.110402/", Logger.LogType.WARNING);
/* 227 */         Logger.info("");
/* 228 */         Bukkit.broadcastMessage("");
/* 229 */         Bukkit.broadcastMessage("§4! Attention !");
/* 230 */         Bukkit.broadcastMessage("§cLOOK installation Guide on spigot: https://www.spigotmc.org/resources/63010/");
/* 231 */         Bukkit.broadcastMessage("§6You must install the zMenu plugin to run zAuctionHouse!");
/* 232 */         Bukkit.broadcastMessage("§eDownload here: https://www.spigotmc.org/resources/zmenu.110402/");
/* 233 */         Bukkit.broadcastMessage("");
/* 234 */         this.isActive = false;
/*     */         
/*     */         return;
/*     */       } 
/* 238 */       this.zMenuHandler = new ZMenuHandler(this, isMiniMessage());
/* 239 */       this.zMenuHandler.loadButtons();
/* 240 */       this.zMenuHandler.loadInventories();
/* 241 */       this.messageLoader.load(getPersist());
/* 242 */       this.isActive = true;
/*     */     } 
/*     */     
/* 245 */     this.economyManager.loadEconomies();
/*     */     
/* 247 */     if (isEnable(Plugins.ZSCHEDULERS)) {
/* 248 */       getLog().log("Register zScheduler implementation", Logger.LogType.INFO);
/* 249 */       ZAHImplementation zAHImplementation = new ZAHImplementation(this);
/* 250 */       zAHImplementation.register();
/*     */     } 
/*     */     
/* 253 */     if (isEnable(Plugins.PROTOCOLLIBS) && AuctionConfiguration.enableSearchWithProtocolLib) {
/* 254 */       getLog().log("Register ProtocolLib implementation", Logger.LogType.INFO);
/* 255 */       this.searchInput = (SearchInput)new ProtocolSearch(this);
/*     */     } else {
/* 257 */       this.searchInput = (SearchInput)new ChatSearch(this);
/*     */     } 
/*     */     
/* 260 */     if (AuctionConfiguration.enableVersionChecker) {
/* 261 */       VersionChecker versionChecker = new VersionChecker(this, 1);
/* 262 */       versionChecker.useLastVersion();
/*     */     } 
/*     */     
/* 265 */     if (Bukkit.getPluginManager().getPlugin("zAuctionHouseRedis") != null) {
/*     */       
/* 267 */       int i = Integer.parseInt(getDescription().getVersion().replace(".", ""));
/* 268 */       int j = Integer.parseInt(Bukkit.getPluginManager().getPlugin("zAuctionHouseRedis").getDescription().getVersion().replace(".", ""));
/*     */       
/* 270 */       if (j < 200 && i > 3227) {
/* 271 */         getLog().log("The version of zAuctionHouseRedis is not supported you must use 2.0.0 or higher.", Logger.LogType.ERROR);
/* 272 */         getServer().getPluginManager().disablePlugin(Bukkit.getPluginManager().getPlugin("zAuctionHouseRedis"));
/*     */         
/*     */         return;
/*     */       } 
/*     */     } 
/* 277 */     if (isEnable(Plugins.ZESSENTIALS) && AuctionConfiguration.enableZEssentialsCustomProvider) {
/* 278 */       getLog().log("Register zEssentials implementation", Logger.LogType.INFO);
/* 279 */       this.customStorageProvider = (CustomStorageProvider)new ZEssentialsProvider();
/*     */     } 
/*     */     
/* 282 */     logEnable();
/*     */   }
/*     */   
/*     */   private boolean isMiniMessage() {
/*     */     try {
/* 287 */       Class.forName("net.kyori.adventure.text.minimessage.MiniMessage");
/* 288 */       Class.forName("net.kyori.adventure.text.minimessage.tag.resolver.TagResolver");
/* 289 */       return (Config.enableMiniMessageFormat && NmsVersion.nmsVersion.getVersion() >= NmsVersion.V_1_18.getVersion());
/* 290 */     } catch (Exception exception) {
/* 291 */       return false;
/*     */     } 
/*     */   }
/*     */   @NotNull
/*     */   public ComponentLogger getComponentLogger() {
/* 296 */     return super.getComponentLogger();
/*     */   }
/*     */ 
/*     */   
/*     */   public Logger getSLF4JLogger() {
/* 301 */     return super.getSLF4JLogger();
/*     */   }
/*     */ 
/*     */   
/*     */   public Logger getLog4JLogger() {
/* 306 */     return super.getLog4JLogger();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onDisable() {
/* 312 */     preDisable();
/*     */     
/* 314 */     this.inventoryManager.close();
/* 315 */     getSavers().forEach(paramSavable -> paramSavable.save(getPersist()));
/* 316 */     this.storageManager.save(this.persist);
/*     */     
/* 318 */     postDisable();
/*     */   }
/*     */   
/*     */   public Runnable enablePlugin() {
/* 322 */     if (!AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 323 */       this.isActive = false;
/*     */     }
/* 325 */     return () -> {
/*     */         try {
/*     */           if (!AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*     */             this.inventories.loadInventories();
/*     */             
/*     */             this.messageLoader.load(this.persist);
/*     */             this.isActive = true;
/*     */           } 
/* 333 */         } catch (Exception exception) {
/*     */           exception.printStackTrace();
/*     */         } 
/*     */       };
/*     */   }
/*     */   
/*     */   public InventoryManager getInventories() {
/* 340 */     return this.inventories;
/*     */   }
/*     */ 
/*     */   
/*     */   public ZInventoryManager getInventoryManager() {
/* 345 */     return this.inventoryManager;
/*     */   }
/*     */   
/*     */   public AuctionManager getAuctionManager() {
/* 349 */     return this.auctionManager;
/*     */   }
/*     */   
/*     */   public boolean isReady() {
/* 353 */     return (this.storageManager.isReady() && isActive());
/*     */   }
/*     */   
/*     */   public IStorage getStorage() {
/* 357 */     return this.storageManager.getIStorage();
/*     */   }
/*     */   
/*     */   public void disable() {
/* 361 */     this.storageManager.setReady(false);
/*     */   }
/*     */   
/*     */   public void enable() {
/* 365 */     this.storageManager.setReady(true);
/*     */   }
/*     */   
/*     */   public MessageLoader getMessageLoader() {
/* 369 */     return this.messageLoader;
/*     */   }
/*     */ 
/*     */   
/*     */   public CategoryManager getCategoryManager() {
/* 374 */     return this.categoryManager;
/*     */   }
/*     */   
/*     */   public TransactionManager getTransactionManager() {
/* 378 */     return this.transactionManager;
/*     */   }
/*     */   
/*     */   public ConvertManager getConvertManager() {
/* 382 */     return this.convertManager;
/*     */   }
/*     */   
/*     */   public boolean isActive() {
/* 386 */     return this.isActive;
/*     */   }
/*     */   
/*     */   public FilterManager getFilterManager() {
/* 390 */     return this.filterManager;
/*     */   }
/*     */   
/*     */   public TaxManager getTaxManager() {
/* 394 */     return this.taxManager;
/*     */   }
/*     */   
/*     */   public PriceManager getPriceManager() {
/* 398 */     return this.priceManager;
/*     */   }
/*     */   
/*     */   public StorageManager getStorageManager() {
/* 402 */     return (StorageManager)this.storageManager;
/*     */   }
/*     */   
/*     */   public IBlacklistManager getBlacklistManager() {
/* 406 */     return this.blacklistManager;
/*     */   }
/*     */   
/*     */   public ZMenuHandler getzMenuHandler() {
/* 410 */     if (this.zMenuHandler == null) {
/* 411 */       this.zMenuHandler = new ZMenuHandler(this, isMiniMessage());
/*     */     }
/* 413 */     return this.zMenuHandler;
/*     */   }
/*     */   
/*     */   public EconomyManager getEconomyManager() {
/* 417 */     return this.economyManager;
/*     */   }
/*     */   
/*     */   public DupeManager getDupeManager() {
/* 421 */     return this.dupeManager;
/*     */   }
/*     */   
/*     */   public IWhitelistManager getWhitelistManager() {
/* 425 */     return this.whitelistManager;
/*     */   }
/*     */   
/*     */   public ZPlaceholderAPI getPlaceholderAPI() {
/* 429 */     return this.placeholderAPI;
/*     */   }
/*     */   
/*     */   public CommandAuction getCommandAuction() {
/* 433 */     return this.commandAuction;
/*     */   }
/*     */   
/*     */   public void debug(String paramString) {
/* 437 */     if (AuctionConfiguration.enableDebugMode) {
/* 438 */       getLogger().info(paramString);
/*     */     }
/*     */   }
/*     */   
/*     */   public AuctionConfiguration getConfiguration() {
/* 443 */     return this.configuration;
/*     */   }
/*     */ 
/*     */   
/*     */   public TranslationManager getTranslationManager() {
/* 448 */     return (TranslationManager)this.translationManager;
/*     */   }
/*     */ 
/*     */   
/*     */   public String translateItemStack(ItemStack paramItemStack) {
/* 453 */     return this.translationManager.translateItemStack(paramItemStack);
/*     */   }
/*     */   
/*     */   public SearchInput getSearchInput() {
/* 457 */     return this.searchInput;
/*     */   }
/*     */ 
/*     */   
/*     */   public CustomStorageProvider getCustomStorageProvider() {
/* 462 */     return this.customStorageProvider;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCustomStorageProvider(CustomStorageProvider paramCustomStorageProvider) {
/* 467 */     this.customStorageProvider = paramCustomStorageProvider;
/*     */   }
/*     */   
/*     */   public void updateInventory(Player paramPlayer) {
/* 471 */     serverImplementation.runLater(() -> { if (this.zMenuHandler == null) return;  this.zMenuHandler.update(paramPlayer); }1L);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ZAuctionPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */