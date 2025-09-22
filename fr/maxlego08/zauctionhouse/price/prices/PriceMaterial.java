/*    */ package fr.maxlego08.zauctionhouse.price.prices;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.price.ZPriceItem;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PriceMaterial
/*    */   extends ZPriceItem
/*    */ {
/*    */   private final Material material;
/*    */   
/*    */   public PriceMaterial(Material paramMaterial, long paramLong1, long paramLong2) {
/* 16 */     super(paramLong1, paramLong2);
/* 17 */     this.material = paramMaterial;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 22 */     return "zauctionhouse:material_similar";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSimilar(ItemStack paramItemStack) {
/* 27 */     return (paramItemStack.getType() == this.material);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\prices\PriceMaterial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */