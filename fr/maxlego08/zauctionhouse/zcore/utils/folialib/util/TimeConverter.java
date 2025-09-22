/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.util;
/*    */ 
/*    */ import java.util.concurrent.TimeUnit;
/*    */ 
/*    */ public class TimeConverter
/*    */ {
/*    */   public static long toTicks(long paramLong, TimeUnit paramTimeUnit) {
/*  8 */     return paramTimeUnit.toMillis(paramLong) / 50L;
/*    */   }
/*    */   
/*    */   public static long toMillis(long paramLong) {
/* 12 */     return paramLong * 50L;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\foliali\\util\TimeConverter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */