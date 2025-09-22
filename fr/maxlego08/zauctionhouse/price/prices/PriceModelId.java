/*    */ package fr.maxlego08.zauctionhouse.price.prices;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.price.ZPriceItem;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class PriceModelId
/*    */   extends ZPriceItem {
/*    */   private final String key;
/*    */   private final int modelId;
/*    */   
/*    */   public PriceModelId(String paramString, int paramInt, long paramLong1, long paramLong2) {
/* 14 */     super(paramLong1, paramLong2);
/* 15 */     this.key = paramString;
/* 16 */     this.modelId = paramInt;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 21 */     return "zauctionhouse:similar_model_id";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isSimilar(ItemStack paramItemStack) {
/* 27 */     if (!NmsVersion.nmsVersion.isHexVersion()) {
/* 28 */       return false;
/*    */     }
/*    */     
/* 31 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/*    */     
/* 33 */     return (paramItemStack.getType().name().equalsIgnoreCase(this.key) && itemMeta.hasCustomModelData() && itemMeta.getCustomModelData() == this.modelId);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\prices\PriceModelId.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */