/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.builder;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
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
/*     */   extends ZUtils
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
/*  50 */     this.material = paramMaterial;
/*  51 */     this.data = paramInt1;
/*  52 */     this.amount = paramInt2;
/*  53 */     this.name = paramString;
/*  54 */     this.lore = paramList;
/*  55 */     this.flags = paramList1;
/*  56 */     this.enchantments = paramMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial) {
/*  64 */     this(paramMaterial, 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt) {
/*  73 */     this(paramMaterial, paramInt, 0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt1, int paramInt2) {
/*  83 */     this(paramMaterial, paramInt1, paramInt2, null);
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
/*  94 */     this(paramMaterial, paramInt2, paramInt1, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, int paramInt, String paramString) {
/* 104 */     this(paramMaterial, 0, paramInt, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, String paramString) {
/* 113 */     this(paramMaterial, 0, 1, paramString, null, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, ItemFlag... paramVarArgs) {
/* 122 */     this(paramMaterial, 0, 1, null, null, Arrays.asList(paramVarArgs), null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder(Material paramMaterial, String... paramVarArgs) {
/* 131 */     this(paramMaterial, 0, 1, null, Arrays.asList(paramVarArgs), null, null);
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
/* 142 */     if (this.enchantments == null)
/* 143 */       this.enchantments = new HashMap<>(); 
/* 144 */     this.enchantments.put(paramEnchantment, Integer.valueOf(paramInt));
/* 145 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setFlag(ItemFlag... paramVarArgs) {
/* 155 */     this.flags = Arrays.asList(paramVarArgs);
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setFlag(ItemFlag paramItemFlag) {
/* 165 */     if (this.flags == null)
/* 166 */       this.flags = new ArrayList<>(); 
/* 167 */     this.flags.add(paramItemFlag);
/* 168 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLine(String paramString, Object... paramVarArgs) {
/* 178 */     if (this.lore == null)
/* 179 */       this.lore = new ArrayList<>(); 
/* 180 */     this.lore.add(String.format(paramString, paramVarArgs));
/* 181 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder addLine(String paramString) {
/* 191 */     if (this.lore == null)
/* 192 */       this.lore = new ArrayList<>(); 
/* 193 */     this.lore.add(paramString);
/* 194 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder setLore(String... paramVarArgs) {
/* 203 */     this.lore = Arrays.asList(paramVarArgs);
/* 204 */     return this;
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder owner(Player paramPlayer) {
/* 243 */     return owner(paramPlayer.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemBuilder owner(String paramString) {
/* 248 */     if (this.material == getMaterial(144) || this.material == getMaterial(397)) {
/* 249 */       SkullMeta skullMeta = (SkullMeta)this.meta;
/* 250 */       skullMeta.setOwner(paramString);
/* 251 */       if (this.meta == null)
/* 252 */         build(); 
/* 253 */       this.meta = (ItemMeta)skullMeta;
/*     */     } 
/* 255 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack build() {
/* 260 */     this.item = new ItemStack(this.material, this.amount, (short)this.data);
/*     */     
/* 262 */     if (this.meta == null) {
/* 263 */       this.meta = this.item.getItemMeta();
/*     */     }
/* 265 */     if (this.flags != null) {
/* 266 */       this.flags.forEach(paramItemFlag -> this.meta.addItemFlags(new ItemFlag[] { paramItemFlag }));
/*     */     }
/* 268 */     if (this.name != null) {
/* 269 */       this.meta.setDisplayName(this.name);
/*     */     }
/* 271 */     if (this.lore != null) {
/* 272 */       this.meta.setLore(this.lore);
/*     */     }
/* 274 */     if (this.enchantments != null) {
/* 275 */       this.enchantments.forEach((paramEnchantment, paramInteger) -> this.meta.addEnchant(paramEnchantment, paramInteger.intValue(), true));
/*     */     }
/* 277 */     if (this.durability != 0) {
/* 278 */       this.item.setDurability((short)this.durability);
/*     */     }
/* 280 */     this.item.setItemMeta(this.meta);
/* 281 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemBuilder clone() {
/*     */     try {
/* 289 */       return (ItemBuilder)super.clone();
/* 290 */     } catch (CloneNotSupportedException cloneNotSupportedException) {
/* 291 */       cloneNotSupportedException.printStackTrace();
/* 292 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItem() {
/* 300 */     return this.item;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Material getMaterial() {
/* 307 */     return this.material;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemMeta getMeta() {
/* 315 */     if (this.meta == null)
/* 316 */       this.meta = (new ItemStack(this.material, this.amount, (short)this.data)).getItemMeta(); 
/* 317 */     return this.meta;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getData() {
/* 324 */     return this.data;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/* 331 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getName() {
/* 338 */     return this.name;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getLore() {
/* 345 */     return this.lore;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ItemFlag> getFlags() {
/* 352 */     return this.flags;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getDurability() {
/* 359 */     return this.durability;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Enchantment, Integer> getEnchantments() {
/* 366 */     return this.enchantments;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\builder\ItemBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */