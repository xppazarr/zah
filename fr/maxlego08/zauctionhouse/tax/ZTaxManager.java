/*     */ package fr.maxlego08.zauctionhouse.tax;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.loader.Loader;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.Tax;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxManager;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.loader.ItemStackLoader;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.yaml.YamlUtils;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.atomic.AtomicInteger;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZTaxManager
/*     */   extends YamlUtils
/*     */   implements TaxManager {
/*  28 */   private final List<Tax> taxs = new ArrayList<>();
/*     */   
/*     */   public ZTaxManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  31 */     super(paramZAuctionPlugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public void save(Persist paramPersist) {
/*  36 */     if (paramPersist != null) {
/*     */       return;
/*     */     }
/*  39 */     this.taxs.clear();
/*  40 */     this.taxs.add(new ZTax(new ItemStack(Material.STONE), 5.0D));
/*  41 */     this.taxs.add(new ZTax(new ItemStack(Material.IRON_ORE), 10.0D));
/*  42 */     this.taxs.add(new ZTax(new ItemStack(Material.IRON_INGOT), 30.0D));
/*     */     
/*  44 */     File file = new File(this.plugin.getDataFolder(), "taxs.yml");
/*  45 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*  46 */     ItemStackLoader itemStackLoader = new ItemStackLoader();
/*     */     
/*  48 */     AtomicInteger atomicInteger = new AtomicInteger(1);
/*  49 */     this.taxs.forEach(paramTax -> {
/*     */           String str = "taxs." + paramAtomicInteger.getAndIncrement() + ".";
/*     */           
/*     */           paramYamlConfiguration.set(str + "percent", Double.valueOf(paramTax.getPercent()));
/*     */           paramLoader.save(paramTax.getItemStack(), paramYamlConfiguration, str + "item.");
/*     */         });
/*     */     try {
/*  56 */       yamlConfiguration.save(file);
/*  57 */     } catch (IOException iOException) {
/*  58 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(Persist paramPersist) {
/*  65 */     File file = new File(this.plugin.getDataFolder(), "taxs.yml");
/*     */     
/*  67 */     if (!file.exists()) {
/*     */       try {
/*  69 */         file.createNewFile();
/*  70 */         save((Persist)null);
/*  71 */       } catch (IOException iOException) {
/*  72 */         iOException.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  77 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*  78 */     ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("taxs.");
/*  79 */     ItemStackLoader itemStackLoader = new ItemStackLoader();
/*     */     
/*  81 */     for (String str : configurationSection.getKeys(false)) {
/*     */       
/*     */       try {
/*  84 */         String str1 = "taxs." + str + ".";
/*  85 */         double d = yamlConfiguration.getDouble(str1 + "percent", 1.0D);
/*  86 */         ItemStack itemStack = (ItemStack)itemStackLoader.load(yamlConfiguration, str1 + ".item.", new Object[0]);
/*  87 */         this.taxs.add(new ZTax(itemStack, d));
/*  88 */       } catch (Exception exception) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     Logger.info("Loading of " + this.taxs.size() + " tax items");
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<Tax> getTax(ItemStack paramItemStack) {
/* 100 */     return this.taxs.stream().filter(paramTax -> paramTax.getItemStack().isSimilar(paramItemStack)).findFirst();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public double getTax(Player paramPlayer, long paramLong, ItemStack paramItemStack, TaxType paramTaxType) {
/* 106 */     if (AuctionConfiguration.enableTaxByPassPermission && paramPlayer.hasPermission(AuctionConfiguration.taxBypassPermission)) {
/* 107 */       return 0.0D;
/*     */     }
/*     */     
/* 110 */     double d = getTaxPercent(paramItemStack, paramTaxType);
/* 111 */     if (d == 0.0D) {
/* 112 */       return 0.0D;
/*     */     }
/* 114 */     return percentNum(paramLong, d);
/*     */   }
/*     */ 
/*     */   
/*     */   public double reverseTax(long paramLong, ItemStack paramItemStack) {
/* 119 */     double d1 = getTaxPercent(paramItemStack, TaxType.CAPITALISM);
/* 120 */     if (d1 == 0.0D) {
/* 121 */       return paramLong;
/*     */     }
/* 123 */     double d2 = d1 / 100.0D + 1.0D;
/* 124 */     return paramLong / d2 + 1.0D;
/*     */   }
/*     */ 
/*     */   
/*     */   public double getTaxPercent(ItemStack paramItemStack, TaxType paramTaxType) {
/* 129 */     if (AuctionConfiguration.taxType == paramTaxType || AuctionConfiguration.taxType == TaxType.BOTH) {
/* 130 */       if (AuctionConfiguration.enableDefaultTax)
/* 131 */         return AuctionConfiguration.taxDefaultPercent; 
/* 132 */       if (AuctionConfiguration.enableItemsTax) {
/* 133 */         Optional<Tax> optional = getTax(paramItemStack);
/* 134 */         if (optional.isPresent()) {
/* 135 */           Tax tax = optional.get();
/* 136 */           return tax.getPercent();
/*     */         } 
/*     */       } 
/*     */     } 
/* 140 */     return 0.0D;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\tax\ZTaxManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */