/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*    */ 
/*    */ public class DisplaySorting
/*    */ {
/*    */   private final Sorting sorting;
/*    */   private final String displayString;
/*    */   
/*    */   public DisplaySorting(Sorting paramSorting, String paramString) {
/* 11 */     this.sorting = paramSorting;
/* 12 */     this.displayString = paramString;
/*    */   }
/*    */   
/*    */   public Sorting getSorting() {
/* 16 */     return this.sorting;
/*    */   }
/*    */   
/*    */   public String getDisplayString() {
/* 20 */     return this.displayString;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\DisplaySorting.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */