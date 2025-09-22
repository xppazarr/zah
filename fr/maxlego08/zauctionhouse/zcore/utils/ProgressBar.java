/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ProgressBar
/*    */ {
/*    */   private final int lenght;
/*    */   private final char symbol;
/*    */   private final String completedColor;
/*    */   private final String notCompletedColor;
/*    */   
/*    */   public ProgressBar(int paramInt, char paramChar, String paramString1, String paramString2) {
/* 18 */     this.lenght = paramInt;
/* 19 */     this.symbol = paramChar;
/* 20 */     this.completedColor = paramString1;
/* 21 */     this.notCompletedColor = paramString2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public int getLenght() {
/* 28 */     return this.lenght;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public char getSymbol() {
/* 35 */     return this.symbol;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCompletedColor() {
/* 42 */     return this.completedColor;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getNotCompletedColor() {
/* 49 */     return this.notCompletedColor;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\ProgressBar.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */