/*     */ package fr.maxlego08.zauctionhouse.economy;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.EconomyManager;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionLoadEconomyEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.Currencies;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.loader.ItemStackLoader;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZEconomyManager
/*     */   implements EconomyManager {
/*     */   private final ZAuctionPlugin plugin;
/*  26 */   private final List<AuctionEconomy> shopEconomies = new ArrayList<>();
/*     */   
/*     */   public ZEconomyManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  29 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<AuctionEconomy> getEconomies() {
/*  34 */     return Collections.unmodifiableCollection(this.shopEconomies);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean registerEconomy(AuctionEconomy paramAuctionEconomy) {
/*  39 */     Objects.requireNonNull(this.shopEconomies); getEconomy(paramAuctionEconomy.getName()).ifPresent(this.shopEconomies::remove);
/*  40 */     this.shopEconomies.add(paramAuctionEconomy);
/*  41 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean removeEconomy(AuctionEconomy paramAuctionEconomy) {
/*  46 */     return this.shopEconomies.remove(paramAuctionEconomy);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<AuctionEconomy> getEconomy(String paramString) {
/*  51 */     return this.shopEconomies.stream().filter(paramAuctionEconomy -> paramAuctionEconomy.getName().equalsIgnoreCase(paramString)).findFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadEconomies() {
/*  57 */     File file = new File(this.plugin.getDataFolder(), "economies.yml");
/*  58 */     if (!file.exists()) {
/*  59 */       this.plugin.saveResource("economies.yml", true);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  64 */     AuctionLoadEconomyEvent auctionLoadEconomyEvent = new AuctionLoadEconomyEvent((AuctionPlugin)this.plugin, this);
/*  65 */     auctionLoadEconomyEvent.call();
/*     */     
/*  67 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/*  68 */     for (String str1 : yamlConfiguration.getConfigurationSection("economies.").getKeys(false)) {
/*  69 */       String str12, str2 = "economies." + str1 + ".";
/*     */       
/*  71 */       if (!yamlConfiguration.getBoolean(str2 + "isEnable"))
/*     */         continue; 
/*  73 */       String str3 = yamlConfiguration.getString(str2 + "name", "VAULT");
/*  74 */       String str4 = yamlConfiguration.getString(str2 + "type", "VAULT");
/*  75 */       String str5 = yamlConfiguration.getString(str2 + "currency", null);
/*  76 */       String str6 = yamlConfiguration.getString(str2 + "format", null);
/*  77 */       String str7 = yamlConfiguration.getString(str2 + "permission", null);
/*     */       
/*  79 */       String str8 = yamlConfiguration.getString(str2 + "deposit-reason", "Sale of x%amount% %item%");
/*  80 */       String str9 = yamlConfiguration.getString(str2 + "withdraw-reason", "Purchase of x%amount% %item%");
/*  81 */       String str10 = yamlConfiguration.getString(str2 + "withdraw-tax-reason", "Tax on sale of x%amount% %item%");
/*  82 */       Currencies currencies = Currencies.valueOf(str4.toUpperCase());
/*     */       
/*  84 */       if (str5 == null) {
/*  85 */         Logger.info("§ccurrency of " + str3 + " is wrong ! Your configuration is not right !", Logger.LogType.ERROR);
/*     */         
/*     */         continue;
/*     */       } 
/*  89 */       if (str6 == null) {
/*  90 */         Logger.info("§cformat of " + str3 + " is wrong ! Your configuration is not right !", Logger.LogType.ERROR);
/*     */         
/*     */         continue;
/*     */       } 
/*  94 */       String str11 = yamlConfiguration.getString(str2 + "denyMessage");
/*     */       
/*  96 */       CurrencyProvider currencyProvider = null;
/*  97 */       switch (currencies) {
/*     */         case ZMENUITEMS:
/*     */         case ITEM:
/* 100 */           if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 101 */             currencyProvider = Currencies.ZMENUITEMS.createProvider(new Object[] { this.plugin, file, str2 + "item." }); break;
/*     */           } 
/*     */           try {
/* 104 */             ItemStack itemStack = (new ItemStackLoader()).load(yamlConfiguration, str2 + "item.", new Object[0]);
/* 105 */             currencyProvider = Currencies.ITEM.createProvider(new Object[] { this.plugin, itemStack });
/* 106 */           } catch (Exception exception) {
/* 107 */             exception.printStackTrace();
/*     */           } 
/*     */           break;
/*     */         
/*     */         case ECOBITS:
/*     */         case ZESSENTIALS:
/*     */         case COINSENGINE:
/*     */         case REDISECONOMY:
/* 115 */           str12 = yamlConfiguration.getString(str2 + "currencyName");
/* 116 */           currencyProvider = currencies.createProvider(new Object[] { str12 });
/*     */           break;
/*     */         default:
/* 119 */           currencyProvider = currencies.createProvider(new Object[0]);
/*     */           break;
/*     */       } 
/*     */       
/* 123 */       if (currencyProvider == null) {
/* 124 */         Logger.info("Impossible to find currency provider for " + currencies, Logger.LogType.ERROR);
/*     */         
/*     */         continue;
/*     */       } 
/* 128 */       Logger.info("Register " + currencies + " economy with name " + str3);
/* 129 */       registerEconomy(new ZAuctionEconomy(currencyProvider, str3, str5, str11, str6, str7, str8, str9, str10));
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\economy\ZEconomyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */