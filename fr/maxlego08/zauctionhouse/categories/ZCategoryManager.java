/*     */ package fr.maxlego08.zauctionhouse.categories;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.category.CategoryItem;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Function;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.ConfigurationSection;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZCategoryManager extends YamlUtils implements CategoryManager {
/*  26 */   private final Map<String, Category> categories = new HashMap<>();
/*     */   
/*     */   public ZCategoryManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  29 */     super(paramZAuctionPlugin);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void save(Persist paramPersist) {
/*  35 */     if (paramPersist != null) {
/*     */       return;
/*     */     }
/*     */     
/*  39 */     File file = new File(this.plugin.getDataFolder(), "categories.yml");
/*  40 */     if (!file.exists()) {
/*     */       try {
/*  42 */         file.createNewFile();
/*     */         return;
/*  44 */       } catch (IOException iOException) {
/*  45 */         iOException.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  49 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*  50 */     this.categories.values().forEach(paramCategory -> {
/*     */           paramYamlConfiguration.set("categories." + paramCategory.getName() + ".name", paramCategory.getDisplayName());
/*     */ 
/*     */ 
/*     */           
/*     */           if (paramCategory.getCategoryItems() != null) {
/*     */             String str = "categories." + paramCategory.getName() + ".materials";
/*     */ 
/*     */             
/*     */             ArrayList arrayList = new ArrayList();
/*     */ 
/*     */             
/*     */             paramCategory.getCategoryItems().forEach(());
/*     */ 
/*     */             
/*     */             paramYamlConfiguration.set(str, arrayList);
/*     */           } 
/*     */         });
/*     */ 
/*     */     
/*     */     try {
/*  71 */       yamlConfiguration.save(file);
/*  72 */     } catch (IOException iOException) {
/*  73 */       iOException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void load(Persist paramPersist) {
/*  81 */     if (paramPersist != null) {
/*     */       return;
/*     */     }
/*     */     
/*  85 */     File file = new File(this.plugin.getDataFolder(), "categories.yml");
/*  86 */     if (!file.exists()) {
/*     */       try {
/*  88 */         file.createNewFile();
/*  89 */         createDefault();
/*  90 */         Logger.info("Create default categories files.");
/*     */         return;
/*  92 */       } catch (IOException iOException) {
/*  93 */         iOException.printStackTrace();
/*     */       } 
/*     */     }
/*     */     
/*  97 */     YamlConfiguration yamlConfiguration = getConfig(file);
/*  98 */     if (yamlConfiguration == null) {
/*     */       try {
/* 100 */         file.createNewFile();
/* 101 */         createDefault();
/* 102 */         Logger.info("Create default categories files.");
/*     */         return;
/* 104 */       } catch (IOException iOException) {
/* 105 */         iOException.printStackTrace();
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 110 */     ConfigurationSection configurationSection = yamlConfiguration.getConfigurationSection("categories");
/* 111 */     if (configurationSection == null) {
/*     */       try {
/* 113 */         file.createNewFile();
/* 114 */         createDefault();
/* 115 */         Logger.info("Create default categories files.");
/*     */         return;
/* 117 */       } catch (IOException iOException) {
/* 118 */         iOException.printStackTrace();
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 123 */     for (String str : yamlConfiguration.getConfigurationSection("categories").getKeys(false)) {
/*     */ 
/*     */       
/*     */       try {
/* 127 */         String str1 = yamlConfiguration.getString("categories." + str + ".name");
/*     */         
/* 129 */         if (str1 == null || str1.isEmpty()) {
/* 130 */           Logger.info("An error occurred while loading the category " + str, Logger.LogType.ERROR);
/* 131 */           Logger.info("Impossible to find the name of the category " + str, Logger.LogType.ERROR);
/*     */           
/*     */           continue;
/*     */         } 
/* 135 */         List list = yamlConfiguration.getList("categories." + str + ".materials", new ArrayList());
/*     */         
/* 137 */         if (list.isEmpty()) {
/*     */           
/* 139 */           ZCategory zCategory1 = new ZCategory(str1, str, null);
/* 140 */           this.categories.put(str, zCategory1);
/* 141 */           if (!str.equals("misc")) {
/* 142 */             Logger.info("Attention, the category " + str + " is empty", Logger.LogType.WARNING);
/*     */           }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/*     */           continue;
/*     */         } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 159 */         List<CategoryItem> list1 = (List)list.stream().map(paramObject -> { try { if (paramObject instanceof Map) { Map<?, ?> map = (Map)paramObject; return new ZCategoryItem(map); }  } catch (Exception exception) { if (AuctionConfiguration.enableDebugMode) exception.printStackTrace();  }  return null; }).filter(Objects::nonNull).collect(Collectors.toList());
/*     */         
/* 161 */         ZCategory zCategory = new ZCategory(str1, str, list1);
/*     */         
/* 163 */         Logger.info("Loading the " + str1 + " category with " + list1.size() + " materials", Logger.LogType.INFO);
/* 164 */         this.categories.put(str, zCategory);
/*     */       
/*     */       }
/* 167 */       catch (Exception exception) {
/* 168 */         Logger.info("An error occurred while loading the category " + str, Logger.LogType.ERROR);
/* 169 */         Logger.info("You must have an error with your configuration file. Please check that your file is correct before coming to discord.", Logger.LogType.ERROR);
/* 170 */         exception.printStackTrace();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createDefault() {
/* 181 */     List<CategoryItem> list = (List)Arrays.<Material>stream(Material.values()).filter(Material::isBlock).map(ZCategoryItem::new).collect(Collectors.toList());
/*     */ 
/*     */     
/* 184 */     ArrayList<ZCategoryItem> arrayList = new ArrayList();
/* 185 */     arrayList.add(new ZCategoryItem(null, 0, "test name", null, 0, false));
/* 186 */     arrayList.add(new ZCategoryItem(null, 0, null, "test lore", 0, false));
/* 187 */     arrayList.add(new ZCategoryItem(Material.STONE, 0, "test stone", null, 0, false));
/* 188 */     arrayList.add(new ZCategoryItem(Material.PAPER, 0, null, null, 2500, false));
/*     */     
/* 190 */     this.categories.put("examples", new ZCategory("Examples", "examples", (List)arrayList));
/*     */     
/* 192 */     this.categories.put("blocks", new ZCategory("Blocks", "blocks", list));
/*     */     
/* 194 */     registerTools();
/* 195 */     registerWeapons();
/* 196 */     registerPotion();
/*     */     
/* 198 */     this.categories.put("misc", new ZCategory("Misc", "misc", null));
/*     */     
/* 200 */     save((Persist)null);
/*     */   }
/*     */   
/*     */   private void registerTools() {
/* 204 */     ArrayList<ZCategoryItem> arrayList = new ArrayList();
/* 205 */     if (NmsVersion.nmsVersion.isNewMaterial()) {
/*     */       
/* 207 */       arrayList.add(new ZCategoryItem(Material.STONE_AXE));
/* 208 */       arrayList.add(new ZCategoryItem(Material.STONE_SHOVEL));
/* 209 */       arrayList.add(new ZCategoryItem(Material.STONE_HOE));
/* 210 */       arrayList.add(new ZCategoryItem(Material.STONE_PICKAXE));
/*     */       
/* 212 */       arrayList.add(new ZCategoryItem(Material.IRON_AXE));
/* 213 */       arrayList.add(new ZCategoryItem(Material.IRON_SHOVEL));
/* 214 */       arrayList.add(new ZCategoryItem(Material.IRON_HOE));
/* 215 */       arrayList.add(new ZCategoryItem(Material.IRON_PICKAXE));
/*     */       
/* 217 */       arrayList.add(new ZCategoryItem(Material.GOLDEN_AXE));
/* 218 */       arrayList.add(new ZCategoryItem(Material.GOLDEN_SHOVEL));
/* 219 */       arrayList.add(new ZCategoryItem(Material.GOLDEN_HOE));
/* 220 */       arrayList.add(new ZCategoryItem(Material.GOLDEN_PICKAXE));
/*     */       
/* 222 */       arrayList.add(new ZCategoryItem(Material.DIAMOND_AXE));
/* 223 */       arrayList.add(new ZCategoryItem(Material.DIAMOND_SHOVEL));
/* 224 */       arrayList.add(new ZCategoryItem(Material.DIAMOND_HOE));
/* 225 */       arrayList.add(new ZCategoryItem(Material.DIAMOND_PICKAXE));
/*     */       
/*     */       try {
/* 228 */         arrayList.add(new ZCategoryItem(Material.NETHERITE_AXE));
/* 229 */         arrayList.add(new ZCategoryItem(Material.NETHERITE_SHOVEL));
/* 230 */         arrayList.add(new ZCategoryItem(Material.NETHERITE_HOE));
/* 231 */         arrayList.add(new ZCategoryItem(Material.NETHERITE_PICKAXE));
/* 232 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 235 */       ZCategory zCategory = new ZCategory("Tools", "tools", (List)arrayList);
/* 236 */       this.categories.put("tools", zCategory);
/*     */     }
/*     */     else {
/*     */       
/* 240 */       arrayList.add(new ZCategoryItem(getMaterial(256)));
/* 241 */       arrayList.add(new ZCategoryItem(getMaterial(257)));
/* 242 */       arrayList.add(new ZCategoryItem(getMaterial(258)));
/* 243 */       arrayList.add(new ZCategoryItem(getMaterial(269)));
/* 244 */       arrayList.add(new ZCategoryItem(getMaterial(270)));
/* 245 */       arrayList.add(new ZCategoryItem(getMaterial(271)));
/* 246 */       arrayList.add(new ZCategoryItem(getMaterial(273)));
/* 247 */       arrayList.add(new ZCategoryItem(getMaterial(274)));
/* 248 */       arrayList.add(new ZCategoryItem(getMaterial(275)));
/* 249 */       arrayList.add(new ZCategoryItem(getMaterial(277)));
/* 250 */       arrayList.add(new ZCategoryItem(getMaterial(278)));
/* 251 */       arrayList.add(new ZCategoryItem(getMaterial(279)));
/* 252 */       arrayList.add(new ZCategoryItem(getMaterial(284)));
/* 253 */       arrayList.add(new ZCategoryItem(getMaterial(285)));
/* 254 */       arrayList.add(new ZCategoryItem(getMaterial(286)));
/* 255 */       arrayList.add(new ZCategoryItem(getMaterial(290)));
/* 256 */       arrayList.add(new ZCategoryItem(getMaterial(291)));
/* 257 */       arrayList.add(new ZCategoryItem(getMaterial(292)));
/* 258 */       arrayList.add(new ZCategoryItem(getMaterial(293)));
/* 259 */       arrayList.add(new ZCategoryItem(getMaterial(294)));
/* 260 */       arrayList.add(new ZCategoryItem(getMaterial(346)));
/* 261 */       arrayList.add(new ZCategoryItem(getMaterial(398)));
/* 262 */       arrayList.add(new ZCategoryItem(getMaterial(420)));
/* 263 */       ZCategory zCategory = new ZCategory("Tools", "tools", (List)arrayList);
/* 264 */       this.categories.put("tools", zCategory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void registerPotion() {
/* 271 */     ArrayList<ZCategoryItem> arrayList = new ArrayList();
/* 272 */     if (NmsVersion.nmsVersion.isNewMaterial()) {
/*     */       
/* 274 */       arrayList.add(new ZCategoryItem(Material.BLAZE_ROD));
/* 275 */       arrayList.add(new ZCategoryItem(Material.GHAST_TEAR));
/* 276 */       arrayList.add(new ZCategoryItem(Material.NETHER_STAR));
/* 277 */       arrayList.add(new ZCategoryItem(Material.GLASS_BOTTLE));
/* 278 */       arrayList.add(new ZCategoryItem(Material.POTION));
/* 279 */       arrayList.add(new ZCategoryItem(Material.LINGERING_POTION));
/* 280 */       arrayList.add(new ZCategoryItem(Material.SPLASH_POTION));
/* 281 */       arrayList.add(new ZCategoryItem(Material.SPIDER_EYE));
/* 282 */       arrayList.add(new ZCategoryItem(Material.FERMENTED_SPIDER_EYE));
/* 283 */       arrayList.add(new ZCategoryItem(Material.BLAZE_POWDER));
/* 284 */       arrayList.add(new ZCategoryItem(Material.MAGMA_CREAM));
/* 285 */       arrayList.add(new ZCategoryItem(Material.BREWING_STAND));
/* 286 */       arrayList.add(new ZCategoryItem(Material.CAULDRON));
/* 287 */       arrayList.add(new ZCategoryItem(Material.GLISTERING_MELON_SLICE));
/*     */       
/* 289 */       arrayList.add(new ZCategoryItem(Material.POTION));
/* 290 */       arrayList.add(new ZCategoryItem(Material.SPLASH_POTION));
/* 291 */       arrayList.add(new ZCategoryItem(Material.LINGERING_POTION));
/*     */       
/* 293 */       ZCategory zCategory = new ZCategory("Potions", "potions", (List)arrayList);
/* 294 */       this.categories.put("potions", zCategory);
/*     */     } else {
/*     */       
/* 297 */       arrayList.add(new ZCategoryItem(getMaterial(369)));
/* 298 */       arrayList.add(new ZCategoryItem(getMaterial(370)));
/* 299 */       for (char c = 'Ŵ'; c != 'ſ'; c++) {
/* 300 */         arrayList.add(new ZCategoryItem(getMaterial(c)));
/*     */       }
/*     */       
/* 303 */       ZCategory zCategory = new ZCategory("Potions", "potions", (List)arrayList);
/* 304 */       this.categories.put("potions", zCategory);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void registerWeapons() {
/* 310 */     ArrayList<ZCategoryItem> arrayList = new ArrayList();
/* 311 */     if (NmsVersion.nmsVersion.isNewMaterial()) {
/*     */       
/* 313 */       arrayList.add(new ZCategoryItem(Material.FLINT_AND_STEEL));
/* 314 */       arrayList.add(new ZCategoryItem(Material.BOW));
/* 315 */       arrayList.add(new ZCategoryItem(Material.ARROW));
/* 316 */       arrayList.add(new ZCategoryItem(Material.IRON_SWORD));
/* 317 */       arrayList.add(new ZCategoryItem(Material.DIAMOND_SWORD));
/* 318 */       arrayList.add(new ZCategoryItem(Material.GOLDEN_SWORD));
/* 319 */       arrayList.add(new ZCategoryItem(Material.STONE_SWORD));
/* 320 */       arrayList.add(new ZCategoryItem(Material.WOODEN_SWORD));
/*     */       
/*     */       try {
/* 323 */         arrayList.add(new ZCategoryItem(Material.NETHERITE_SWORD));
/* 324 */         arrayList.add(new ZCategoryItem(Material.IRON_HORSE_ARMOR));
/* 325 */         arrayList.add(new ZCategoryItem(Material.GOLDEN_HORSE_ARMOR));
/* 326 */         arrayList.add(new ZCategoryItem(Material.DIAMOND_HORSE_ARMOR));
/* 327 */         arrayList.add(new ZCategoryItem(Material.LEATHER_HORSE_ARMOR));
/* 328 */       } catch (Exception exception) {}
/*     */ 
/*     */       
/* 331 */       ZCategory zCategory = new ZCategory("Weapons", "weapons", (List)arrayList);
/* 332 */       this.categories.put("weapons", zCategory);
/*     */     }
/*     */     else {
/*     */       
/* 336 */       arrayList.add(new ZCategoryItem(getMaterial(259)));
/* 337 */       arrayList.add(new ZCategoryItem(getMaterial(261)));
/* 338 */       arrayList.add(new ZCategoryItem(getMaterial(262)));
/* 339 */       arrayList.add(new ZCategoryItem(getMaterial(267)));
/* 340 */       arrayList.add(new ZCategoryItem(getMaterial(268)));
/* 341 */       arrayList.add(new ZCategoryItem(getMaterial(272)));
/* 342 */       arrayList.add(new ZCategoryItem(getMaterial(276)));
/* 343 */       arrayList.add(new ZCategoryItem(getMaterial(283)));
/* 344 */       arrayList.add(new ZCategoryItem(getMaterial(417)));
/* 345 */       arrayList.add(new ZCategoryItem(getMaterial(418)));
/* 346 */       arrayList.add(new ZCategoryItem(getMaterial(419)));
/* 347 */       for (char c = 'Ī'; c != 'ľ'; c++) {
/* 348 */         arrayList.add(new ZCategoryItem(getMaterial(c)));
/*     */       }
/* 350 */       ZCategory zCategory = new ZCategory("Weapons", "weapons", (List)arrayList);
/* 351 */       this.categories.put("weapons", zCategory);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<Category> getByName(String paramString) {
/* 358 */     return this.categories.entrySet().stream().filter(paramEntry -> ((String)paramEntry.getKey()).equalsIgnoreCase(paramString)).map(Map.Entry::getValue).findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Category> getByItemStack(ItemStack paramItemStack) {
/* 363 */     return (List<Category>)this.categories.values().stream().filter(paramCategory -> (paramCategory.getCategoryItems() != null && paramCategory.getCategoryItems().stream().anyMatch(()))).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<AuctionItem> getMiscellaneous(List<AuctionItem> paramList) {
/* 368 */     return (List<AuctionItem>)paramList.stream().filter(paramAuctionItem -> !getByItemStack(paramAuctionItem.getItemStack()).isEmpty()).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getRandomItemStacks() {
/* 374 */     if (this.categories.isEmpty()) {
/* 375 */       return new ItemStack(Material.IRON_INGOT, getNumberBetween(1, 64));
/*     */     }
/*     */     
/* 378 */     Category category = (Category)randomElement(new ArrayList(this.categories.values()));
/* 379 */     if (category.getCategoryItems() == null || category.getCategoryItems().isEmpty()) {
/* 380 */       return new ItemStack(Material.GOLD_INGOT, getNumberBetween(1, 64));
/*     */     }
/* 382 */     return ((CategoryItem)randomElement(category.getCategoryItems())).createItemStack();
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Category> getCategories(AuctionItem paramAuctionItem) {
/* 387 */     List<Category> list = getByItemStack(paramAuctionItem.getItemStack());
/* 388 */     if (!list.isEmpty())
/* 389 */       return list; 
/* 390 */     return Collections.singletonList(getMiscCategory());
/*     */   }
/*     */   
/*     */   private Category getMiscCategory() {
/* 394 */     return this.categories.values().stream().filter(Category::isMiscellaneous).findFirst().orElse(null);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\categories\ZCategoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */