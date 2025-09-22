/*    */ package fr.maxlego08.zauctionhouse.price.prices;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.price.ZPriceItem;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PriceLore
/*    */   extends ZPriceItem
/*    */ {
/*    */   private final String lore;
/*    */   
/*    */   public PriceLore(String paramString, long paramLong1, long paramLong2) {
/* 16 */     super(paramLong1, paramLong2);
/* 17 */     this.lore = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 22 */     return "zauctionhouse:contains_lore";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSimilar(ItemStack paramItemStack) {
/* 27 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 28 */     return (itemMeta.hasLore() && itemMeta.getLore().stream().anyMatch(paramString -> (paramString != null && paramString.contains(this.lore))));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\prices\PriceLore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */