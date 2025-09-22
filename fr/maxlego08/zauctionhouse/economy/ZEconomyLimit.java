/*    */ package fr.maxlego08.zauctionhouse.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.economy.EconomyLimit;
/*    */ 
/*    */ public class ZEconomyLimit
/*    */   implements EconomyLimit
/*    */ {
/*    */   private final String economyName;
/*    */   private final long max;
/*    */   private final long min;
/*    */   
/*    */   public ZEconomyLimit(String paramString, long paramLong1, long paramLong2) {
/* 13 */     this.economyName = paramString;
/* 14 */     this.max = paramLong1;
/* 15 */     this.min = paramLong2;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getEconomyName() {
/* 23 */     return this.economyName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMax() {
/* 31 */     return this.max;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMin() {
/* 39 */     return this.min;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\economy\ZEconomyLimit.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */