/*    */ package fr.maxlego08.zauctionhouse.tax;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.tax.Tax;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZTax
/*    */   implements Tax
/*    */ {
/*    */   private final ItemStack itemStack;
/*    */   private final double percent;
/*    */   
/*    */   public ZTax(ItemStack paramItemStack, double paramDouble) {
/* 18 */     this.itemStack = paramItemStack;
/* 19 */     this.percent = paramDouble;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 26 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getPercent() {
/* 33 */     return this.percent;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\tax\ZTax.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */