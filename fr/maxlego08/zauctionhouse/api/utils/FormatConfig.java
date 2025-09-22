/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class FormatConfig
/*    */ {
/*    */   private final String format;
/*    */   private final String display;
/*    */   private final long maxAmount;
/*    */   
/*    */   public FormatConfig(String paramString1, String paramString2, long paramLong) {
/* 20 */     this.format = paramString1;
/* 21 */     this.display = paramString2;
/* 22 */     this.maxAmount = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormat() {
/* 31 */     return this.format;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDisplay() {
/* 40 */     return this.display;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMaxAmount() {
/* 49 */     return this.maxAmount;
/*    */   }
/*    */   
/*    */   public static class Result {
/*    */     private final String result;
/*    */     private final String displayText;
/*    */     
/*    */     public Result(String param1String1, String param1String2) {
/* 57 */       this.result = param1String1;
/* 58 */       this.displayText = param1String2;
/*    */     }
/*    */     
/*    */     public String getResult() {
/* 62 */       return this.result;
/*    */     }
/*    */     
/*    */     public String getDisplayText() {
/* 66 */       return this.displayText;
/*    */     }
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\FormatConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */