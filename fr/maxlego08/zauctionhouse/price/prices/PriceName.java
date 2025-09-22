/*    */ package fr.maxlego08.zauctionhouse.price.prices;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.price.ZPriceItem;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PriceName
/*    */   extends ZPriceItem
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   public PriceName(String paramString, long paramLong1, long paramLong2) {
/* 18 */     super(paramLong1, paramLong2);
/* 19 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 24 */     return "zauctionhouse:names_contains";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSimilar(ItemStack paramItemStack) {
/* 29 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 30 */     return (itemMeta.hasDisplayName() && itemMeta.getDisplayName().contains(this.name));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\prices\PriceName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */