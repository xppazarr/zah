/*    */ package fr.maxlego08.zauctionhouse.price;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.price.PriceItem;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class ZPriceItem
/*    */   implements PriceItem
/*    */ {
/*    */   private final long maxPrice;
/*    */   private final long minPrice;
/*    */   
/*    */   public ZPriceItem(long paramLong1, long paramLong2) {
/* 16 */     this.maxPrice = paramLong1;
/* 17 */     this.minPrice = paramLong2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMaxPrice() {
/* 24 */     return this.maxPrice;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMinPrice() {
/* 31 */     return this.minPrice;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\price\ZPriceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */