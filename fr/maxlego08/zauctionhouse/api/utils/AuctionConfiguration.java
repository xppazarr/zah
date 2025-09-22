/*     */ package fr.maxlego08.zauctionhouse.api.utils;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*     */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxType;
/*     */ import fr.maxlego08.zauctionhouse.sound.ZSoundOption;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.XSound;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuctionConfiguration
/*     */ {
/*     */   public static boolean betterPrice = true;
/*     */   public static boolean betterPriceReduction = false;
/*     */   public static boolean categoryCheckOnlyMaterial = true;
/*     */   public static boolean creativeSell = true;
/*     */   public static boolean disableCommands = false;
/*     */   public static boolean disableDefaultSellPriceFormatError = true;
/*     */   public static boolean disablePriceErrorDefault = false;
/*     */   public static boolean disableSellBreakItem = false;
/*     */   public static boolean displayCooldownMessage = true;
/*     */   public static boolean displayErrorBuyMessage = false;
/*     */   public static boolean dontGiveIfPlayerHasFullInventory = false;
/*     */   public static boolean enableAddItemFlagToAuctionItem = false;
/*     */   public static boolean enableAutoUpdate = true;
/*     */   public static boolean enableAsyncInventoryOpen = true;
/*     */   public static boolean enableBackup = true;
/*     */   public static boolean enableBuyAnnonce = false;
/*     */   public static boolean enableBuyErrorSound = false;
/*     */   public static boolean enableBuySuccessSound = false;
/*     */   public static boolean enableCacheItems = false;
/*     */   public static boolean enableClaimMoney = false;
/*     */   public static boolean enableClaimMoneyMessageOnJoin = false;
/*     */   public static boolean enableCommandInventories = false;
/*     */   public static boolean enableCooldownClick = false;
/*     */   public static boolean enableCooldownCommand = true;
/*     */   public static boolean enableCustomHelpMessage = false;
/*     */   public static boolean enableCustomOpenMessage = false;
/*     */   public static boolean enableCustomSearchMessage = false;
/*     */   public static boolean enableCustomSellMessage = false;
/*     */   public static boolean enableDebugMode = false;
/*     */   public static boolean enableDebugModeTime = false;
/*     */   public static boolean enableDefaultTax = false;
/*     */   public static boolean enableHeadDatabaseVerification = true;
/*     */   public static boolean enableItemsTax = false;
/*     */   public static boolean enableLogFileSaveInformations = true;
/*     */   public static boolean enableMinMaxPricePerItems = false;
/*     */   public static boolean enableNewBase64ItemStackMethod = false;
/*     */   public static boolean enableNumberFormatSell;
/*     */   public static boolean enableOpenSyncInventory = false;
/*     */   public static boolean enablePapiInAuctionItemLore = true;
/*     */   public static boolean enablePermissionForSellingTime = false;
/*     */   public static boolean enablePlugin = true;
/*     */   public static boolean enablePrioritySort = false;
/*     */   public static boolean enablePrioritySortLimit = false;
/*     */   public static boolean enableSellAnnonce = false;
/*     */   public static boolean enableSellEconomyInventory = true;
/*     */   public static boolean enableTaxByPassPermission = false;
/*     */   public static boolean enableTransactionMessageOnJoin = false;
/*     */   public static boolean enableVersionChecker = true;
/*     */   public static boolean enableWhitelist = false;
/*     */   public static boolean giveItemAfterPurchase = false;
/*     */   public static boolean giveItemAfterRemove = false;
/*     */   public static boolean giveMoneyOnSellServer = true;
/*     */   public static boolean needPriceForSellInventory = true;
/*     */   public static boolean openInventoryAfterBuying = false;
/*     */   public static boolean openInventoryAfterRemoveConfirm = true;
/*     */   public static boolean useLog = true;
/*     */   public static boolean useLogInFile = false;
/*     */   public static boolean useNoMoneyItem = true;
/*     */   public static boolean useSellCooldown = true;
/*     */   public static boolean USE_ZMENU_INVENTORY = false;
/*     */   public static boolean enableCategoriesCache = false;
/*     */   public static boolean enableSearch = true;
/*     */   public static boolean enableSearchWithProtocolLib = true;
/*     */   public static boolean enableSearchTranslatedMaterial = false;
/*     */   public static boolean enableDownloadAllLangagesFiles = false;
/*     */   public static boolean enableItemStackTranslation = false;
/*     */   public static boolean enableZEssentialsCustomProvider = false;
/*  98 */   public static long cacheDurationCategory = 60000L;
/*  99 */   public static long cacheItemStackMilliSeconds = 1000L;
/* 100 */   public static long cachePlaceholderDurationMillis = 60000L;
/* 101 */   public static long cacheSeconds = 15L;
/* 102 */   public static long commandCooldownMilliSeconds = 5000L;
/* 103 */   public static long cooldownClaimMoneyMessageTicks = 10L;
/* 104 */   public static long cooldownClaimMoneySecond = 300L;
/* 105 */   public static long cooldownCommandSell = 5L;
/* 106 */   public static long cooldownInformationsMessageTicks = 15L;
/* 107 */   public static long cooldownTransactions = 30000L;
/* 108 */   public static long expireTime = 604800L;
/* 109 */   public static long maxPrice = 999999999999999999L;
/* 110 */   public static long minPrice = 10L;
/* 111 */   public static long noMoneyItemTicks = 20L;
/* 112 */   public static long removeTransactionAfterSeconds = 604800L;
/* 113 */   public static long sellTime = 172800L;
/*     */ 
/*     */ 
/*     */   
/* 117 */   public static int autoSaveSecond = 900;
/* 118 */   public static int maxSqlRetryAmount = 5;
/* 119 */   public static int sortCooldownChange = 1;
/* 120 */   public static int transactionPageSize = 5;
/*     */ 
/*     */ 
/*     */   
/* 124 */   public static String addonPermission = null;
/* 125 */   public static String defaultEconomy = "VAULT";
/* 126 */   public static String defaultFormat = "%amount%";
/* 127 */   public static String mainCommand = "zauctionhouse";
/* 128 */   public static String permissionBuyAnnonce = null;
/* 129 */   public static String permissionSellAnnonce = null;
/* 130 */   public static String placeholderLoader = "Loading...";
/* 131 */   public static String tableSqlPrefix = "";
/* 132 */   public static String taxBypassPermission = "zauctionhouse.bypass.tax";
/* 133 */   public static String transactionDateFormat = "YYYY MM dd hh:mm";
/* 134 */   public static String versionPermission = null;
/* 135 */   public static String language = "fr-fr";
/*     */ 
/*     */ 
/*     */   
/* 139 */   public static List<FormatConfig> formatConfigs = new ArrayList<>();
/* 140 */   public static List<ItemFlag> itemFlags = Arrays.asList(ItemFlag.values());
/* 141 */   public static List<NumberFormatSell> numberFormatSells = new ArrayList<>();
/* 142 */   public static List<String> sellCommandAliases = Arrays.asList(new String[] { "sell", "vendre", "s" });
/* 143 */   public static List<String> subCommands = Arrays.asList(new String[] { "zauction", "ah", "hdv", "zah", "zhdv" });
/* 144 */   public static List<Integer> sellAutoCompletionAmount = Arrays.asList(new Integer[] { Integer.valueOf(1), Integer.valueOf(4), Integer.valueOf(8), Integer.valueOf(16), Integer.valueOf(32), Integer.valueOf(64) });
/* 145 */   public static List<Integer> sellAutoCompletionPrice = Arrays.asList(new Integer[] { Integer.valueOf(100), Integer.valueOf(200), Integer.valueOf(300), Integer.valueOf(400), Integer.valueOf(500), Integer.valueOf(600), Integer.valueOf(700), Integer.valueOf(800), Integer.valueOf(900), Integer.valueOf(1000) });
/* 146 */   public static String[] searchLines = new String[] { "", "--------", "please enter", "your search" };
/*     */ 
/*     */ 
/*     */   
/* 150 */   public static ClickType showClick = ClickType.RIGHT;
/*     */   public static DiscordWebhookConfig discordWebhook;
/*     */   public static DupeConfig dupeConfig;
/* 153 */   public static Sorting defaultSort = Sorting.DECREASING_DATE;
/*     */   public static SoundOption soundBuyError;
/*     */   public static SoundOption soundBuySuccess;
/* 156 */   public static Storage storage = Storage.JSON;
/* 157 */   public static TaxType taxType = TaxType.SELL;
/* 158 */   public static double taxDefaultPercent = 10.0D;
/* 159 */   public static char betterPriceEspace = ' ';
/*     */   public static boolean enableMiniMessageInMessage = false;
/*     */   
/*     */   static {
/* 163 */     numberFormatSells.add(new NumberFormatSell("k", 1000L));
/* 164 */     numberFormatSells.add(new NumberFormatSell("m", 1000000L));
/* 165 */     numberFormatSells.add(new NumberFormatSell("B", 1000000000L));
/* 166 */     numberFormatSells.add(new NumberFormatSell("T", 1000000000000L));
/*     */   }
/*     */   
/*     */   public void saveConfigFile() {
/* 170 */     ZPlugin zPlugin = ZPlugin.z();
/* 171 */     File file = new File(zPlugin.getDataFolder(), "config.yml");
/*     */     
/* 173 */     zPlugin.reloadConfig();
/* 174 */     YamlConfiguration yamlConfiguration = (YamlConfiguration)zPlugin.getConfig();
/*     */     
/* 176 */     yamlConfiguration.set("enableDebug", Boolean.valueOf(enableDebugMode));
/* 177 */     yamlConfiguration.set("enableDebugTime", Boolean.valueOf(enableDebugModeTime));
/* 178 */     yamlConfiguration.set("enableExpirationPermission", Boolean.valueOf(enablePermissionForSellingTime));
/* 179 */     yamlConfiguration.set("enablePlugin", Boolean.valueOf(enablePlugin));
/* 180 */     yamlConfiguration.set("enableCreativeSell", Boolean.valueOf(creativeSell));
/* 181 */     yamlConfiguration.set("sellCooldown.enable", Boolean.valueOf(useSellCooldown));
/* 182 */     yamlConfiguration.set("noMoneyItem.enable", Boolean.valueOf(useNoMoneyItem));
/* 183 */     yamlConfiguration.set("log.enable", Boolean.valueOf(useLog));
/* 184 */     yamlConfiguration.set("log.isFile", Boolean.valueOf(useLogInFile));
/* 185 */     yamlConfiguration.set("enableCustomMessage.help", Boolean.valueOf(enableCustomHelpMessage));
/* 186 */     yamlConfiguration.set("enableCustomMessage.sell", Boolean.valueOf(enableCustomSellMessage));
/* 187 */     yamlConfiguration.set("enableCustomMessage.search", Boolean.valueOf(enableCustomSearchMessage));
/* 188 */     yamlConfiguration.set("enableCustomMessage.open", Boolean.valueOf(enableCustomOpenMessage));
/* 189 */     yamlConfiguration.set("enablePrioritySort", Boolean.valueOf(enablePrioritySort));
/* 190 */     yamlConfiguration.set("enablePrioritySortLimit", Boolean.valueOf(enablePrioritySortLimit));
/* 191 */     yamlConfiguration.set("tax.globalTax", Boolean.valueOf(enableDefaultTax));
/* 192 */     yamlConfiguration.set("tax.perItemTax", Boolean.valueOf(enableItemsTax));
/* 193 */     yamlConfiguration.set("tax.enableByPass", Boolean.valueOf(enableTaxByPassPermission));
/* 194 */     yamlConfiguration.set("optimisation.enablePapiInLore", Boolean.valueOf(enablePapiInAuctionItemLore));
/* 195 */     yamlConfiguration.set("optimisation.cache.enable", Boolean.valueOf(enableCacheItems));
/* 196 */     yamlConfiguration.set("optimisation.enableLogAutoSaveInformation", Boolean.valueOf(enableLogFileSaveInformations));
/* 197 */     yamlConfiguration.set("optimisation.openSyncInventory", Boolean.valueOf(enableOpenSyncInventory));
/* 198 */     yamlConfiguration.set("optimisation.openSyncInventory", Boolean.valueOf(disableCommands));
/* 199 */     yamlConfiguration.set("itemFlags.enable", Boolean.valueOf(enableAddItemFlagToAuctionItem));
/* 200 */     yamlConfiguration.set("price.betterPrice", Boolean.valueOf(betterPrice));
/* 201 */     yamlConfiguration.set("price.betterPriceReduction", Boolean.valueOf(betterPriceReduction));
/* 202 */     yamlConfiguration.set("price.enableMinMaxPricePerItems", Boolean.valueOf(enableMinMaxPricePerItems));
/* 203 */     yamlConfiguration.set("enableNewBase64ItemStackMethod", Boolean.valueOf(enableNewBase64ItemStackMethod));
/* 204 */     yamlConfiguration.set("enableSellAnnonce", Boolean.valueOf(enableSellAnnonce));
/* 205 */     yamlConfiguration.set("displayErrorBuyMessage", Boolean.valueOf(displayErrorBuyMessage));
/* 206 */     yamlConfiguration.set("enableBuyAnnonce", Boolean.valueOf(enableBuyAnnonce));
/* 207 */     yamlConfiguration.set("enableClaimMoney", Boolean.valueOf(enableClaimMoney));
/* 208 */     yamlConfiguration.set("enableClaimMoneyMessageOnJoin", Boolean.valueOf(enableClaimMoneyMessageOnJoin));
/* 209 */     yamlConfiguration.set("enableTransactionMessageOnJoin", Boolean.valueOf(enableTransactionMessageOnJoin));
/* 210 */     yamlConfiguration.set("disableSellBreakItem", Boolean.valueOf(disableSellBreakItem));
/* 211 */     yamlConfiguration.set("giveItemAfterPurchase", Boolean.valueOf(giveItemAfterPurchase));
/* 212 */     yamlConfiguration.set("giveItemAfterRemove", Boolean.valueOf(giveItemAfterRemove));
/* 213 */     yamlConfiguration.set("dontGiveIfPlayerHasFullInventory", Boolean.valueOf(dontGiveIfPlayerHasFullInventory));
/* 214 */     yamlConfiguration.set("openInventoryAfterRemoveConfirm", Boolean.valueOf(openInventoryAfterRemoveConfirm));
/* 215 */     yamlConfiguration.set("openInventoryAfterRemoveConfirm", Boolean.valueOf(openInventoryAfterRemoveConfirm));
/* 216 */     yamlConfiguration.set("needPriceForSellInventory", Boolean.valueOf(needPriceForSellInventory));
/* 217 */     yamlConfiguration.set("disablePriceErrorDefault", Boolean.valueOf(disablePriceErrorDefault));
/* 218 */     yamlConfiguration.set("enableCommandInventories", Boolean.valueOf(enableCommandInventories));
/* 219 */     yamlConfiguration.set("enableVersionChecker", Boolean.valueOf(enableVersionChecker));
/* 220 */     yamlConfiguration.set("disableDefaultSellPriceFormatError", Boolean.valueOf(disableDefaultSellPriceFormatError));
/* 221 */     yamlConfiguration.set("categoryCheckOnlyMaterial", Boolean.valueOf(categoryCheckOnlyMaterial));
/* 222 */     yamlConfiguration.set("displayCooldownMessage", Boolean.valueOf(displayCooldownMessage));
/* 223 */     yamlConfiguration.set("enableCooldownClick", Boolean.valueOf(enableCooldownClick));
/* 224 */     yamlConfiguration.set("enableCooldownCommand", Boolean.valueOf(enableCooldownCommand));
/* 225 */     yamlConfiguration.set("enableBackup", Boolean.valueOf(enableBackup));
/* 226 */     yamlConfiguration.set("enableAutoUpdate", Boolean.valueOf(enableAutoUpdate));
/* 227 */     yamlConfiguration.set("enableSellEconomyInventory", Boolean.valueOf(enableSellEconomyInventory));
/* 228 */     yamlConfiguration.set("giveMoneyOnSellServer", Boolean.valueOf(giveMoneyOnSellServer));
/* 229 */     yamlConfiguration.set("enableAsyncInventoryOpen", Boolean.valueOf(enableAsyncInventoryOpen));
/* 230 */     yamlConfiguration.set("enableCategoriesCache", Boolean.valueOf(enableCategoriesCache));
/* 231 */     if (dupeConfig != null) {
/* 232 */       yamlConfiguration.set("antiDupe.enable", Boolean.valueOf(dupeConfig.isEnable()));
/* 233 */       yamlConfiguration.set("antiDupe.consoleLog.enable", Boolean.valueOf(dupeConfig.isEnableLog()));
/* 234 */       yamlConfiguration.set("antiDupe.discordWebhook.enable", Boolean.valueOf(dupeConfig.isEnableWebhook()));
/*     */     } 
/*     */     
/*     */     try {
/* 238 */       yamlConfiguration.save(file);
/* 239 */     } catch (IOException iOException) {
/* 240 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void load() {
/* 246 */     ZPlugin zPlugin = ZPlugin.z();
/* 247 */     zPlugin.reloadConfig();
/* 248 */     YamlConfiguration yamlConfiguration = (YamlConfiguration)zPlugin.getConfig();
/*     */     
/* 250 */     enableDebugMode = yamlConfiguration.getBoolean("enableDebug", false);
/* 251 */     enableDebugModeTime = yamlConfiguration.getBoolean("enableDebugTime", false);
/* 252 */     enableAsyncInventoryOpen = yamlConfiguration.getBoolean("enableAsyncInventoryOpen", false);
/* 253 */     enablePermissionForSellingTime = yamlConfiguration.getBoolean("enableExpirationPermission", false);
/* 254 */     enablePlugin = yamlConfiguration.getBoolean("enablePlugin", true);
/* 255 */     creativeSell = yamlConfiguration.getBoolean("enableCreateSell", true);
/* 256 */     useSellCooldown = yamlConfiguration.getBoolean("sellCooldown.enable", true);
/* 257 */     useNoMoneyItem = yamlConfiguration.getBoolean("noMoneyItem.enable", true);
/* 258 */     useLog = yamlConfiguration.getBoolean("log.enable", true);
/* 259 */     useLogInFile = yamlConfiguration.getBoolean("log.inFile", false);
/* 260 */     enableCustomHelpMessage = yamlConfiguration.getBoolean("enableCustomMessage.help", false);
/* 261 */     enableCustomSellMessage = yamlConfiguration.getBoolean("enableCustomMessage.sell", false);
/* 262 */     enableCustomSearchMessage = yamlConfiguration.getBoolean("enableCustomMessage.search", false);
/* 263 */     enableCustomOpenMessage = yamlConfiguration.getBoolean("enableCustomMessage.open", false);
/* 264 */     enablePrioritySort = yamlConfiguration.getBoolean("enablePrioritySort", false);
/* 265 */     enablePrioritySortLimit = yamlConfiguration.getBoolean("enablePrioritySortLimit", false);
/* 266 */     enablePapiInAuctionItemLore = yamlConfiguration.getBoolean("optimisation.enablePapiInLore", true);
/* 267 */     enableCacheItems = yamlConfiguration.getBoolean("optimisation.cache.enable", false);
/* 268 */     enableLogFileSaveInformations = yamlConfiguration.getBoolean("optimisation.enableLogAutoSaveInformation", false);
/* 269 */     enableOpenSyncInventory = yamlConfiguration.getBoolean("optimisation.openSyncInventory", false);
/* 270 */     disableCommands = yamlConfiguration.getBoolean("disableCommands", false);
/* 271 */     enableAddItemFlagToAuctionItem = yamlConfiguration.getBoolean("itemFlags.enable", false);
/* 272 */     betterPrice = yamlConfiguration.getBoolean("price.betterPrice", true);
/* 273 */     betterPriceReduction = yamlConfiguration.getBoolean("price.betterPriceReduction", false);
/* 274 */     enableMinMaxPricePerItems = yamlConfiguration.getBoolean("price.enableMinMaxPricePerItems", false);
/* 275 */     enableNewBase64ItemStackMethod = yamlConfiguration.getBoolean("enableNewBase64ItemStackMethod", false);
/* 276 */     enableSellAnnonce = yamlConfiguration.getBoolean("enableSellAnnonce", false);
/* 277 */     displayErrorBuyMessage = yamlConfiguration.getBoolean("displayErrorBuyMessage", false);
/* 278 */     enableBuyAnnonce = yamlConfiguration.getBoolean("enableBuyAnnonce", false);
/* 279 */     enableClaimMoney = yamlConfiguration.getBoolean("enableClaimMoney", false);
/* 280 */     enableClaimMoneyMessageOnJoin = yamlConfiguration.getBoolean("enableClaimMoneyMessageOnJoin", false);
/* 281 */     enableTransactionMessageOnJoin = yamlConfiguration.getBoolean("enableTransactionMessageOnJoin", false);
/* 282 */     enableBuySuccessSound = yamlConfiguration.getBoolean("enableBuySuccessSound", false);
/* 283 */     enableBuyErrorSound = yamlConfiguration.getBoolean("enableBuyErrorSound", false);
/* 284 */     disableSellBreakItem = yamlConfiguration.getBoolean("disableSellBreakItem", false);
/* 285 */     giveItemAfterPurchase = yamlConfiguration.getBoolean("giveItemAfterPurchase", false);
/* 286 */     giveItemAfterRemove = yamlConfiguration.getBoolean("giveItemAfterRemove", false);
/* 287 */     dontGiveIfPlayerHasFullInventory = yamlConfiguration.getBoolean("dontGiveIfPlayerHasFullInventory", false);
/* 288 */     openInventoryAfterRemoveConfirm = yamlConfiguration.getBoolean("openInventoryAfterRemoveConfirm", true);
/* 289 */     openInventoryAfterBuying = yamlConfiguration.getBoolean("openInventoryAfterBuying", false);
/* 290 */     needPriceForSellInventory = yamlConfiguration.getBoolean("needPriceForSellInventory", true);
/* 291 */     disablePriceErrorDefault = yamlConfiguration.getBoolean("disablePriceErrorDefault", true);
/* 292 */     enableCommandInventories = yamlConfiguration.getBoolean("enableCommandInventories", false);
/* 293 */     enableVersionChecker = yamlConfiguration.getBoolean("enableVersionChecker", true);
/* 294 */     disableDefaultSellPriceFormatError = yamlConfiguration.getBoolean("disableDefaultSellPriceFormatError", false);
/* 295 */     categoryCheckOnlyMaterial = yamlConfiguration.getBoolean("categoryCheckOnlyMaterial", true);
/* 296 */     displayCooldownMessage = yamlConfiguration.getBoolean("displayCooldownMessage", true);
/* 297 */     enableCooldownClick = yamlConfiguration.getBoolean("enableCooldownClick", false);
/* 298 */     enableCooldownCommand = yamlConfiguration.getBoolean("enableCooldownCommand", true);
/* 299 */     USE_ZMENU_INVENTORY = yamlConfiguration.getBoolean("useZMenuEngine", false);
/* 300 */     enableBackup = yamlConfiguration.getBoolean("enableBackup", true);
/* 301 */     enableNumberFormatSell = yamlConfiguration.getBoolean("enableNumberFormatSell", true);
/* 302 */     enableAutoUpdate = yamlConfiguration.getBoolean("enableAutoUpdate", false);
/* 303 */     enableSellEconomyInventory = yamlConfiguration.getBoolean("enableSellEconomyInventory", false);
/* 304 */     enableHeadDatabaseVerification = yamlConfiguration.getBoolean("enableHeadDatabaseVerification", true);
/* 305 */     giveMoneyOnSellServer = yamlConfiguration.getBoolean("giveMoneyOnSellServer", false);
/* 306 */     enableWhitelist = yamlConfiguration.getBoolean("enableWhitelist", false);
/* 307 */     enableCategoriesCache = yamlConfiguration.getBoolean("enableCategoriesCache", false);
/* 308 */     enableMiniMessageInMessage = yamlConfiguration.getBoolean("enableMiniMessageInMessage", false);
/* 309 */     enableZEssentialsCustomProvider = yamlConfiguration.getBoolean("enableZEssentialsCustomProvider", false);
/*     */     
/* 311 */     enableSearch = yamlConfiguration.getBoolean("search.enable", true);
/* 312 */     enableSearchWithProtocolLib = yamlConfiguration.getBoolean("search.enable-search-with-protocol-lib", true);
/* 313 */     enableSearchTranslatedMaterial = yamlConfiguration.getBoolean("search.enable-translated-material", false);
/* 314 */     enableDownloadAllLangagesFiles = yamlConfiguration.getBoolean("download-all-langages-files", true);
/* 315 */     enableItemStackTranslation = yamlConfiguration.getBoolean("enable-itemstack-translation", false);
/*     */     
/* 317 */     permissionSellAnnonce = yamlConfiguration.getString("sellAnnoncePermission", null);
/* 318 */     permissionBuyAnnonce = yamlConfiguration.getString("buyAnnoncePermission", null);
/*     */     
/* 320 */     cooldownCommandSell = yamlConfiguration.getLong("sellCooldown.seconds", 5L);
/* 321 */     noMoneyItemTicks = yamlConfiguration.getLong("sellCooldown.ticks", 20L);
/* 322 */     maxPrice = yamlConfiguration.getLong("price.max", 999999999999999999L);
/* 323 */     minPrice = yamlConfiguration.getLong("price.min", 10L);
/* 324 */     sellTime = yamlConfiguration.getLong("time.sell", 172800L);
/* 325 */     expireTime = yamlConfiguration.getLong("time.expire", 604800L);
/* 326 */     removeTransactionAfterSeconds = yamlConfiguration.getLong("time.removeTransactionAfter", 604800L);
/*     */     
/* 328 */     storage = Storage.valueOf(yamlConfiguration.getString("storage", "JSON").toUpperCase());
/* 329 */     defaultSort = Sorting.valueOf(yamlConfiguration.getString("defaultSort", "DECREASING_DATE").toUpperCase());
/* 330 */     showClick = ClickType.valueOf(yamlConfiguration.getString("showClick", "RIGHT").toUpperCase());
/*     */     
/* 332 */     defaultEconomy = yamlConfiguration.getString("defaultEconomy", "VAULT");
/* 333 */     mainCommand = yamlConfiguration.getString("command.main", "zauctionhouse");
/* 334 */     subCommands = yamlConfiguration.getStringList("command.aliases");
/* 335 */     sellCommandAliases = yamlConfiguration.getStringList("command.sellCommandAliases");
/* 336 */     sellAutoCompletionAmount = yamlConfiguration.getIntegerList("sell-auto-completion.amount");
/* 337 */     sellAutoCompletionPrice = yamlConfiguration.getIntegerList("sell-auto-completion.price");
/* 338 */     searchLines = (String[])yamlConfiguration.getStringList("search.sign-lines").toArray((Object[])new String[0]);
/*     */     
/* 340 */     numberFormatSells = (List<NumberFormatSell>)yamlConfiguration.getList("numberFormatSellMultiplication").stream().map(NumberFormatSell::new).collect(Collectors.toList());
/*     */ 
/*     */     
/* 343 */     enableDefaultTax = yamlConfiguration.getBoolean("tax.globalTax", false);
/* 344 */     enableItemsTax = yamlConfiguration.getBoolean("tax.perItemTax", false);
/* 345 */     taxType = TaxType.valueOf(yamlConfiguration.getString("tax.type", "SELL").toUpperCase());
/* 346 */     taxDefaultPercent = yamlConfiguration.getDouble("tax.percent", 10.0D);
/* 347 */     enableTaxByPassPermission = yamlConfiguration.getBoolean("tax.enableByPass", false);
/* 348 */     taxBypassPermission = yamlConfiguration.getString("tax.byPassPermission", "zauctionhouse.bypass.tax");
/*     */     
/* 350 */     discordWebhook = new DiscordWebhookConfig(yamlConfiguration);
/*     */     
/* 352 */     addonPermission = yamlConfiguration.getString("permission.addon", null);
/* 353 */     versionPermission = yamlConfiguration.getString("permission.version", null);
/*     */     
/* 355 */     transactionDateFormat = yamlConfiguration.getString("transactionDateFormat", null);
/*     */     
/* 357 */     cacheItemStackMilliSeconds = yamlConfiguration.getLong("optimisation.cacheItemStack", 1000L);
/* 358 */     cacheSeconds = yamlConfiguration.getLong("optimisation.cache.seconds", 15L);
/* 359 */     sortCooldownChange = yamlConfiguration.getInt("optimisation.sortCooldown", 1);
/* 360 */     autoSaveSecond = yamlConfiguration.getInt("optimisation.autoSave", 900);
/* 361 */     tableSqlPrefix = yamlConfiguration.getString("sql.prefix", "");
/* 362 */     maxSqlRetryAmount = yamlConfiguration.getInt("sql.retry", 5);
/* 363 */     cacheDurationCategory = yamlConfiguration.getLong("cacheDurationCategory", 120000L);
/* 364 */     cachePlaceholderDurationMillis = yamlConfiguration.getLong("placeholder.cache", 60000L);
/* 365 */     placeholderLoader = yamlConfiguration.getString("placeholder.loading", "Loading...");
/* 366 */     language = yamlConfiguration.getString("language", "fr-fr");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 375 */       itemFlags = (List<ItemFlag>)yamlConfiguration.getStringList("itemFlags.flags").stream().map(paramString -> { try { return ItemFlag.valueOf(paramString); } catch (Exception exception) { return null; }  }).filter(Objects::nonNull).collect(Collectors.toList());
/* 376 */     } catch (Exception exception) {}
/*     */ 
/*     */ 
/*     */     
/* 380 */     betterPriceEspace = yamlConfiguration.getString("price.betterPriceEspace", " ").charAt(0);
/*     */     
/* 382 */     cooldownClaimMoneyMessageTicks = yamlConfiguration.getLong("cooldownClaimMoneyMessageTicks", 10L);
/* 383 */     cooldownInformationsMessageTicks = yamlConfiguration.getLong("cooldownInformationsMessageTicks", 15L);
/* 384 */     cooldownClaimMoneySecond = yamlConfiguration.getLong("cooldownClaimMoneySecond", 300L);
/* 385 */     transactionPageSize = yamlConfiguration.getInt("transactionPageSize", 5);
/* 386 */     cooldownTransactions = yamlConfiguration.getLong("cooldownTransactions", 30000L);
/* 387 */     if (cooldownTransactions < 1000L) {
/* 388 */       cooldownTransactions = 1000L;
/* 389 */       Logger.info("Attention, cooldownTransactions cannot be less than 1000ms, this can create a bug if the player spam the command /ah claim, we advise you to increase this value. It just went to 1000ms", Logger.LogType.ERROR);
/*     */     } 
/* 391 */     commandCooldownMilliSeconds = yamlConfiguration.getLong("commandCooldownMilliSeconds", 5000L);
/*     */     
/* 393 */     dupeConfig = new DupeConfig(yamlConfiguration);
/*     */     
/* 395 */     loadNumberFormat(yamlConfiguration);
/* 396 */     soundBuySuccess = loadSound(yamlConfiguration, "buySuccessSound.");
/* 397 */     soundBuyError = loadSound(yamlConfiguration, "buyErrorSound.");
/*     */     
/* 399 */     Logger.info("Loaded config.yml");
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadNumberFormat(YamlConfiguration paramYamlConfiguration) {
/* 404 */     defaultFormat = paramYamlConfiguration.getString("price.display", "%amount%");
/* 405 */     formatConfigs = new ArrayList<>();
/*     */     
/* 407 */     List list = paramYamlConfiguration.getMapList("price.formats");
/* 408 */     list.forEach(paramMap -> {
/*     */           String str1 = (String)paramMap.get("format");
/*     */           String str2 = (String)paramMap.getOrDefault("display", null);
/*     */           long l = ((Number)paramMap.get("maxAmount")).longValue();
/*     */           formatConfigs.add(new FormatConfig(str1, (str2 == null) ? "%amount%" : str2, l));
/*     */         });
/*     */   }
/*     */   
/*     */   private SoundOption loadSound(YamlConfiguration paramYamlConfiguration, String paramString) {
/* 417 */     ZSoundOption zSoundOption = null;
/* 418 */     String str = paramYamlConfiguration.getString(paramString + "sound", null);
/* 419 */     if (str != null) {
/* 420 */       Optional<XSound> optional = XSound.matchXSound(str);
/* 421 */       XSound xSound = optional.orElse(null);
/* 422 */       if (optional.isPresent()) {
/* 423 */         float f1 = Float.parseFloat(paramYamlConfiguration.getString(paramString + "pitch", "1.0f"));
/* 424 */         float f2 = Float.parseFloat(paramYamlConfiguration.getString(paramString + "volume", "1.0f"));
/* 425 */         zSoundOption = new ZSoundOption(xSound, f1, f2);
/*     */       } 
/*     */     } 
/* 428 */     return (SoundOption)zSoundOption;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\AuctionConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */