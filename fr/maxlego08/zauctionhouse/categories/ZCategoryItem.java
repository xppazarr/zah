/*     */ package fr.maxlego08.zauctionhouse.categories;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.category.CategoryItem;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.ChatColor;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ 
/*     */ public class ZCategoryItem
/*     */   extends ZUtils
/*     */   implements CategoryItem {
/*     */   private final Material material;
/*     */   private final int data;
/*     */   private final String name;
/*     */   private final String loreKey;
/*     */   private final int customModelData;
/*     */   private final boolean removeColor;
/*     */   
/*     */   public ZCategoryItem(Material paramMaterial, int paramInt1, String paramString1, String paramString2, int paramInt2, boolean paramBoolean) {
/*  26 */     this.material = paramMaterial;
/*  27 */     this.data = paramInt1;
/*  28 */     this.name = paramString1;
/*  29 */     this.loreKey = paramString2;
/*  30 */     this.customModelData = paramInt2;
/*  31 */     this.removeColor = paramBoolean;
/*     */   }
/*     */   
/*     */   public ZCategoryItem(Material paramMaterial) {
/*  35 */     this.material = paramMaterial;
/*  36 */     this.removeColor = false;
/*  37 */     this.data = 0;
/*  38 */     this.name = null;
/*  39 */     this.loreKey = null;
/*  40 */     this.customModelData = 0;
/*     */   }
/*     */   
/*     */   public ZCategoryItem(Map<?, ?> paramMap) {
/*     */     Material material;
/*  45 */     String str = paramMap.containsKey("material") ? (String)paramMap.getOrDefault("material", null) : null;
/*     */     try {
/*  47 */       material = (str == null) ? null : Material.valueOf(str.toUpperCase());
/*  48 */     } catch (Exception exception) {
/*  49 */       material = Material.PAPER;
/*     */     } 
/*  51 */     this.material = material;
/*  52 */     this.data = paramMap.containsKey("data") ? ((Integer)paramMap.get("data")).intValue() : 0;
/*  53 */     this.removeColor = (paramMap.containsKey("removeColor") && ((Boolean)paramMap.get("removeColor")).booleanValue());
/*  54 */     this.name = (String)paramMap.getOrDefault("name", null);
/*  55 */     this.loreKey = (String)paramMap.getOrDefault("loreKey", null);
/*  56 */     this.customModelData = paramMap.containsKey("modelId") ? ((Number)paramMap.getOrDefault("modelId", null)).intValue() : 0;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isInCategory(ItemStack paramItemStack) {
/*  62 */     if (paramItemStack == null) return false;
/*     */ 
/*     */     
/*  65 */     if (this.material != null && 
/*  66 */       paramItemStack.getType() != this.material) {
/*  67 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  71 */     if (this.data != 0 && 
/*  72 */       paramItemStack.getData().getData() != (short)this.data) {
/*  73 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  77 */     if (this.name != null) {
/*     */       
/*  79 */       if (!paramItemStack.hasItemMeta()) return false; 
/*  80 */       ItemMeta itemMeta = paramItemStack.getItemMeta();
/*     */       
/*  82 */       if (!itemMeta.hasDisplayName()) return false;
/*     */       
/*  84 */       String str = this.removeColor ? stripColors(itemMeta.getDisplayName()) : itemMeta.getDisplayName();
/*     */       
/*  86 */       if (!str.contains(this.name)) {
/*  87 */         return false;
/*     */       }
/*     */     } 
/*     */     
/*  91 */     if (this.loreKey != null) {
/*     */       
/*  93 */       if (!paramItemStack.hasItemMeta()) return false; 
/*  94 */       ItemMeta itemMeta = paramItemStack.getItemMeta();
/*     */       
/*  96 */       if (!itemMeta.hasLore()) return false;
/*     */       
/*  98 */       List list = itemMeta.getLore();
/*  99 */       if (list != null && list.stream().noneMatch(paramString -> (this.removeColor ? stripColors(paramString) : paramString).contains(this.loreKey))) {
/* 100 */         return false;
/*     */       }
/*     */     } 
/*     */     
/* 104 */     if (this.customModelData != 0 && NmsVersion.nmsVersion.isHexVersion()) {
/*     */       
/* 106 */       if (!paramItemStack.hasItemMeta()) return false; 
/* 107 */       ItemMeta itemMeta = paramItemStack.getItemMeta();
/*     */       
/* 109 */       if (!itemMeta.hasCustomModelData()) return false;
/*     */ 
/*     */       
/* 112 */       return (itemMeta.getCustomModelData() == this.customModelData);
/*     */     } 
/*     */     
/* 115 */     return true;
/*     */   }
/*     */   
/*     */   public Material getMaterial() {
/* 119 */     return this.material;
/*     */   }
/*     */   
/*     */   public String getName() {
/* 123 */     return this.name;
/*     */   }
/*     */   
/*     */   public String getLoreKey() {
/* 127 */     return this.loreKey;
/*     */   }
/*     */   
/*     */   public int getCustomModelData() {
/* 131 */     return this.customModelData;
/*     */   }
/*     */   
/*     */   public Map<String, Object> toMap() {
/* 135 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 136 */     if (this.material != null) hashMap.put("material", this.material.name()); 
/* 137 */     if (this.name != null) hashMap.put("name", this.name); 
/* 138 */     if (this.loreKey != null) hashMap.put("loreKey", this.loreKey); 
/* 139 */     if (this.customModelData != 0) hashMap.put("modelId", Integer.valueOf(this.customModelData)); 
/* 140 */     return (Map)hashMap;
/*     */   }
/*     */   
/*     */   public String stripColors(String paramString) {
/* 144 */     return ChatColor.stripColor(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack createItemStack() {
/* 150 */     ItemStack itemStack = new ItemStack((this.material == null) ? Material.PAPER : this.material, 1);
/*     */ 
/*     */     
/* 153 */     ItemMeta itemMeta = itemStack.getItemMeta();
/*     */ 
/*     */     
/* 156 */     if (this.name != null) {
/* 157 */       itemMeta.setDisplayName(ChatColor.translateAlternateColorCodes('&', this.name));
/*     */     }
/*     */ 
/*     */     
/* 161 */     if (this.loreKey != null) {
/* 162 */       List<String> list = Collections.singletonList(ChatColor.translateAlternateColorCodes('&', this.loreKey));
/* 163 */       itemMeta.setLore(list);
/*     */     } 
/*     */ 
/*     */     
/* 167 */     if (this.customModelData != 0) {
/* 168 */       itemMeta.setCustomModelData(Integer.valueOf(this.customModelData));
/*     */     }
/*     */ 
/*     */     
/* 172 */     itemStack.setItemMeta(itemMeta);
/*     */ 
/*     */     
/* 175 */     return itemStack;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\categories\ZCategoryItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */