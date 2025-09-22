/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.builder;
/*     */ 
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.enchantments.Enchantment;
/*     */ import org.bukkit.inventory.ItemFlag;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.PotionMeta;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ import org.bukkit.potion.PotionEffectType;
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
/*     */ public class PotionBuilder
/*     */   extends ItemBuilder
/*     */ {
/*     */   public PotionBuilder(int paramInt1, int paramInt2, String paramString, List<String> paramList, List<ItemFlag> paramList1, Map<Enchantment, Integer> paramMap) {
/*  31 */     super(Material.POTION, paramInt1, paramInt2, paramString, paramList, paramList1, paramMap);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(int paramInt1, int paramInt2, String paramString) {
/*  41 */     super(Material.POTION, paramInt1, paramInt2, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(int paramInt1, int paramInt2) {
/*  50 */     super(Material.POTION, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(int paramInt, String paramString) {
/*  59 */     super(Material.POTION, paramInt, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(int paramInt) {
/*  67 */     super(Material.POTION, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(ItemFlag... paramVarArgs) {
/*  75 */     super(Material.POTION, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(String... paramVarArgs) {
/*  83 */     super(Material.POTION, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder(String paramString) {
/*  91 */     super(Material.POTION, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder() {
/*  98 */     super(Material.POTION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionMeta getMeta() {
/* 105 */     return (PotionMeta)super.getMeta();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder effect(PotionEffectType paramPotionEffectType) {
/* 115 */     getMeta().setMainEffect(paramPotionEffectType);
/* 116 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PotionBuilder effect(PotionEffectType paramPotionEffectType, int paramInt1, int paramInt2) {
/* 127 */     getMeta().addCustomEffect(new PotionEffect(paramPotionEffectType, paramInt1, paramInt2), true);
/* 128 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\builder\PotionBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */