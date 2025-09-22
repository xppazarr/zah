/*     */ package fr.maxlego08.zauctionhouse.api.utils;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ItemBuilder
/*     */   implements Cloneable
/*     */ {
/*     */   private ItemStack item;
/*     */   private final Material material;
/*     */   private ItemMeta meta;
/*     */   private int data;
/*     */   private int amount;
/*     */   private String name;
/*     */   private List<String> lore;
/*     */   private List<ItemFlag> flags;
/*     */   private int durability;
/*     */   private Map<Enchantment, Integer> enchantments;
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt1, int paramInt2, String paramString, List<String> paramList, List<ItemFlag> paramList1, Map<Enchantment, Integer> paramMap) {
/*  46 */     this.material = paramMaterial;
/*  47 */     this.data = paramInt1;
/*  48 */     this.amount = paramInt2;
/*  49 */     this.name = paramString;
/*  50 */     this.lore = paramList;
/*  51 */     this.flags = paramList1;
/*  52 */     this.enchantments = paramMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial) {
/*  60 */     this(paramMaterial, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt) {
/*  69 */     this(paramMaterial, 0, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt1, int paramInt2) {
/*  79 */     this(paramMaterial, paramInt2, paramInt1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt1, int paramInt2, String paramString) {
/*  90 */     this(paramMaterial, paramInt2, paramInt1, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt, String paramString) {
/* 100 */     this(paramMaterial, 0, paramInt, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, String paramString) {
/* 109 */     this(paramMaterial, 0, 1, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, ItemFlag... paramVarArgs) {
/* 118 */     this(paramMaterial, 0, 1, null, null, Arrays.asList(paramVarArgs), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, String... paramVarArgs) {
/* 127 */     this(paramMaterial, 0, 1, null, Arrays.asList(paramVarArgs), null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addEnchant(Enchantment paramEnchantment, int paramInt) {
/* 138 */     if (this.enchantments == null)
/* 139 */       this.enchantments = new HashMap<>(); 
/* 140 */     this.enchantments.put(paramEnchantment, Integer.valueOf(paramInt));
/* 141 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setFlag(ItemFlag... paramVarArgs) {
/* 151 */     this.flags = Arrays.asList(paramVarArgs);
/* 152 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setFlag(ItemFlag paramItemFlag) {
/* 161 */     if (this.flags == null)
/* 162 */       this.flags = new ArrayList<>(); 
/* 163 */     this.flags.add(paramItemFlag);
/* 164 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLine(String paramString, Object... paramVarArgs) {
/* 174 */     if (this.lore == null)
/* 175 */       this.lore = new ArrayList<>(); 
/* 176 */     this.lore.add(String.format(paramString, paramVarArgs));
/* 177 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLine(String paramString) {
/* 187 */     if (this.lore == null)
/* 188 */       this.lore = new ArrayList<>(); 
/* 189 */     this.lore.add(paramString);
/* 190 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(String... paramVarArgs) {
/* 199 */     this.lore = Arrays.asList(paramVarArgs);
/* 200 */     return this;
/*     */   }
/*     */   
/*     */   public void setLore(List<String> paramList) {
/* 204 */     this.lore = paramList;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setName(String paramString) {
/* 213 */     this.name = paramString;
/* 214 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder durability(int paramInt) {
/* 223 */     this.durability = paramInt;
/* 224 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder glow() {
/* 232 */     addEnchant((this.material != Material.BOW) ? Enchantment.ARROW_INFINITE : Enchantment.LUCK, 10);
/* 233 */     setFlag(ItemFlag.HIDE_ENCHANTS);
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack build() {
/* 239 */     this.item = new ItemStack(this.material, this.amount, (short)this.data);
/*     */     
/* 241 */     if (this.meta == null) {
/* 242 */       this.meta = this.item.getItemMeta();
/*     */     }
/* 244 */     if (this.flags != null) {
/* 245 */       this.flags.forEach(paramItemFlag -> this.meta.addItemFlags(new ItemFlag[] { paramItemFlag }));
/*     */     }
/* 247 */     if (this.name != null) {
/* 248 */       this.meta.setDisplayName(this.name);
/*     */     }
/* 250 */     if (this.lore != null) {
/* 251 */       this.meta.setLore(this.lore);
/*     */     }
/* 253 */     if (this.enchantments != null) {
/* 254 */       this.enchantments.forEach((paramEnchantment, paramInteger) -> this.meta.addEnchant(paramEnchantment, paramInteger.intValue(), true));
/*     */     }
/* 256 */     if (this.durability != 0) {
/* 257 */       this.item.setDurability((short)this.durability);
/*     */     }
/* 259 */     this.item.setItemMeta(this.meta);
/* 260 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder clone() {
/*     */     try {
/* 268 */       return (ItemBuilder)super.clone();
/* 269 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 270 */       cloneNotSupportedException.printStackTrace();
/* 271 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/* 279 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 286 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemMeta getMeta() {
/* 294 */     if (this.meta == null)
/* 295 */       this.meta = (new ItemStack(this.material, this.amount, (short)this.data)).getItemMeta(); 
/* 296 */     return this.meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData() {
/* 303 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/* 310 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 317 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getLore() {
/* 324 */     return this.lore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemFlag> getFlags() {
/* 331 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDurability() {
/* 338 */     return this.durability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantments() {
/* 345 */     return this.enchantments;
/*     */   }
/*     */ 
/*     */   
/*     */   public static ItemStack buildNoMoney() {
/* 350 */     Material material = Material.BARRIER;
/* 351 */     ItemBuilder itemBuilder = new ItemBuilder(material, "§4✘ §cYou don't have enough money to buy this !");
/* 352 */     return itemBuilder.build();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */