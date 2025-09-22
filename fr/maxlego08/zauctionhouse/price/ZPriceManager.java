/*     */ package fr.maxlego08.zauctionhouse.price;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.EconomyLimit;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.price.PriceItem;
/*     */ import fr.maxlego08.zauctionhouse.api.price.PriceManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.economy.ZEconomyLimit;
/*     */ import fr.maxlego08.zauctionhouse.price.prices.PriceLore;
/*     */ import fr.maxlego08.zauctionhouse.price.prices.PriceMaterial;
/*     */ import fr.maxlego08.zauctionhouse.price.prices.PriceModelId;
/*     */ import fr.maxlego08.zauctionhouse.price.prices.PriceName;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.yaml.YamlUtils;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZPriceManager
/*     */   extends YamlUtils
/*     */   implements PriceManager
/*     */ {
/*  33 */   private final List<PriceItem> prices = new ArrayList<>();
/*  34 */   private final List<EconomyLimit> economyLimits = new ArrayList<>();
/*     */   
/*     */   public ZPriceManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  37 */     super(paramZAuctionPlugin);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Persist paramPersist) {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(Persist paramPersist) {
/*  48 */     this.prices.removeIf(paramPriceItem -> paramPriceItem.getName().startsWith("zauctionhouse:"));
/*     */     
/*  50 */     File file = new File(this.plugin.getDataFolder(), "prices.yml");
/*     */     
/*  52 */     if (!file.exists()) {
/*  53 */       this.plugin.saveResource("prices.yml", false);
/*  54 */       file = new File(this.plugin.getDataFolder(), "prices.yml");
/*     */     } 
/*     */     
/*  57 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/*  58 */     List list1 = yamlConfiguration.getList("prices");
/*     */     
/*  60 */     if (list1 != null) {
/*  61 */       list1.forEach(paramMap -> {
/*     */             String str = (String)paramMap.get("type");
/*     */             
/*     */             long l1 = ((Number)paramMap.get("minPrice")).longValue();
/*     */             
/*     */             long l2 = ((Number)paramMap.get("maxPrice")).longValue();
/*     */             
/*     */             switch (str) {
/*     */               case "zauctionhouse:material_similar":
/*     */                 try {
/*     */                   Material material = Material.valueOf(((String)paramMap.get("key")).toUpperCase());
/*     */                   this.prices.add(new PriceMaterial(material, l2, l1));
/*  73 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material was not found for zauctionhouse:material_similar", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:contains_lore":
/*     */                 try {
/*     */                   this.prices.add(new PriceLore((String)paramMap.get("key"), l2, l1));
/*  80 */                 } catch (Exception exception) {
/*     */                   Logger.info("Lore was not found for zauctionhouse:contains_lore", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               
/*     */               case "zauctionhouse:similar_model_id":
/*     */                 try {
/*     */                   this.prices.add(new PriceModelId((String)paramMap.get("key"), ((Integer)paramMap.get("modelId")).intValue(), l2, l1));
/*  88 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material or ModelId was not found for zauctionhouse:similar_model_id", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               
/*     */               case "zauctionhouse:names_contains":
/*     */                 try {
/*     */                   this.prices.add(new PriceName((String)paramMap.get("key"), l2, l1));
/*  96 */                 } catch (Exception exception) {
/*     */                   Logger.info("Name was not found for zauctionhouse:names_contains", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             Logger.info(str + " type was not found !", Logger.LogType.ERROR);
/*     */           });
/*     */     }
/* 109 */     Logger.info("Loading of " + this.prices.size() + " price items");
/*     */     
/* 111 */     FileConfiguration fileConfiguration = this.plugin.getConfig();
/* 112 */     List list2 = fileConfiguration.getMapList("price.limits");
/* 113 */     for (Map map : list2) {
/* 114 */       String str = (String)map.get("economy");
/* 115 */       long l1 = ((Number)map.get("max")).longValue();
/* 116 */       long l2 = ((Number)map.get("min")).longValue();
/*     */       
/* 118 */       ZEconomyLimit zEconomyLimit = new ZEconomyLimit(str, l1, l2);
/* 119 */       this.economyLimits.add(zEconomyLimit);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<PriceItem> getPrice(ItemStack paramItemStack) {
/* 125 */     return this.prices.stream().filter(paramPriceItem -> (paramItemStack != null && paramPriceItem.isSimilar(paramItemStack))).findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<EconomyLimit> getEconomyLimit(AuctionEconomy paramAuctionEconomy) {
/* 130 */     return this.economyLimits.stream()
/* 131 */       .filter(paramEconomyLimit -> paramEconomyLimit.getEconomyName().equalsIgnoreCase(paramAuctionEconomy.getName())).findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMinPrice(ItemStack paramItemStack, AuctionEconomy paramAuctionEconomy) {
/* 136 */     if (!AuctionConfiguration.enableMinMaxPricePerItems) {
/*     */       
/* 138 */       Optional<EconomyLimit> optional1 = getEconomyLimit(paramAuctionEconomy);
/* 139 */       if (optional1.isPresent()) {
/* 140 */         return ((EconomyLimit)optional1.get()).getMin();
/*     */       }
/*     */       
/* 143 */       return AuctionConfiguration.minPrice;
/*     */     } 
/*     */     
/* 146 */     Optional<PriceItem> optional = getPrice(paramItemStack);
/* 147 */     return optional.isPresent() ? (((PriceItem)optional.get()).getMinPrice() * paramItemStack.getAmount()) : AuctionConfiguration.minPrice;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMinPrice(AuctionItem paramAuctionItem, AuctionEconomy paramAuctionEconomy) {
/* 152 */     if (!AuctionConfiguration.enableMinMaxPricePerItems) {
/*     */       
/* 154 */       Optional<EconomyLimit> optional = getEconomyLimit(paramAuctionEconomy);
/* 155 */       if (optional.isPresent()) {
/* 156 */         return ((EconomyLimit)optional.get()).getMin();
/*     */       }
/*     */       
/* 159 */       return AuctionConfiguration.minPrice;
/*     */     } 
/*     */     
/* 162 */     if (paramAuctionItem.getType() == AuctionType.DEFAULT) {
/* 163 */       ItemStack itemStack = paramAuctionItem.getItemStack();
/* 164 */       Optional<PriceItem> optional = getPrice(itemStack);
/* 165 */       return ((Long)optional.<Long>map(paramPriceItem -> Long.valueOf(paramPriceItem.getMinPrice() * paramItemStack.getAmount())).orElseGet(() -> Long.valueOf(AuctionConfiguration.minPrice))).longValue();
/*     */     } 
/*     */     
/* 168 */     long l = 0L;
/*     */     
/* 170 */     for (ItemStack itemStack : paramAuctionItem.getItemStacks()) {
/* 171 */       Optional<PriceItem> optional = getPrice(itemStack);
/*     */       
/* 173 */       if (!optional.isPresent()) {
/* 174 */         return AuctionConfiguration.minPrice;
/*     */       }
/*     */       
/* 177 */       long l1 = ((Long)optional.<Long>map(paramPriceItem -> Long.valueOf(paramPriceItem.getMinPrice() * paramItemStack.getAmount())).orElseGet(() -> Long.valueOf(AuctionConfiguration.minPrice))).longValue();
/* 178 */       l += l1;
/*     */     } 
/*     */     
/* 181 */     return l;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMaxPrice(AuctionItem paramAuctionItem, AuctionEconomy paramAuctionEconomy) {
/* 186 */     if (!AuctionConfiguration.enableMinMaxPricePerItems) {
/*     */       
/* 188 */       Optional<EconomyLimit> optional = getEconomyLimit(paramAuctionEconomy);
/* 189 */       return ((Long)optional.<Long>map(EconomyLimit::getMax).orElseGet(() -> Long.valueOf(AuctionConfiguration.maxPrice))).longValue();
/*     */     } 
/*     */ 
/*     */     
/* 193 */     if (paramAuctionItem.getType() == AuctionType.DEFAULT) {
/* 194 */       ItemStack itemStack = paramAuctionItem.getItemStack();
/* 195 */       Optional<PriceItem> optional = getPrice(itemStack);
/* 196 */       return ((Long)optional.<Long>map(paramPriceItem -> Long.valueOf(paramPriceItem.getMaxPrice() * paramItemStack.getAmount())).orElseGet(() -> Long.valueOf(AuctionConfiguration.maxPrice))).longValue();
/*     */     } 
/*     */     
/* 199 */     long l = 0L;
/*     */     
/* 201 */     for (ItemStack itemStack : paramAuctionItem.getItemStacks()) {
/* 202 */       Optional<PriceItem> optional = getPrice(itemStack);
/*     */ 
/*     */       
/* 205 */       if (!optional.isPresent()) {
/* 206 */         return AuctionConfiguration.maxPrice;
/*     */       }
/*     */       
/* 209 */       long l1 = ((Long)optional.<Long>map(paramPriceItem -> Long.valueOf(paramPriceItem.getMaxPrice() * paramItemStack.getAmount())).orElseGet(() -> Long.valueOf(AuctionConfiguration.maxPrice))).longValue();
/* 210 */       l += l1;
/*     */     } 
/*     */     
/* 213 */     return l;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\ZPriceManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */