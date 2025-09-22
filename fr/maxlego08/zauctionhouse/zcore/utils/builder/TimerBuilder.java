/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.builder;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ 
/*    */ public class TimerBuilder
/*    */ {
/*    */   public static String getFormatLongDays(long paramLong) {
/*  8 */     long l1 = paramLong / 1000L;
/*    */     
/* 10 */     long l2 = l1 / 86400L;
/* 11 */     long l3 = l1 % 86400L / 3600L;
/* 12 */     long l4 = l1 % 3600L / 60L;
/* 13 */     long l5 = l1 % 60L;
/*    */     
/* 15 */     String str = Message.TIME_DAY.msg();
/* 16 */     str = str.replace("%second%", ((l5 <= 1L) ? Message.FORMAT_SECOND : Message.FORMAT_SECONDS).msg());
/* 17 */     str = str.replace("%minute%", ((l4 <= 1L) ? Message.FORMAT_MINUTE : Message.FORMAT_MINUTES).msg());
/* 18 */     str = str.replace("%hour%", ((l3 <= 1L) ? Message.FORMAT_HOUR : Message.FORMAT_HOURS).msg());
/* 19 */     str = str.replace("%day%", ((l2 <= 1L) ? Message.FORMAT_DAY : Message.FORMAT_DAYS).msg());
/* 20 */     return format(String.format(str, new Object[] { Long.valueOf(l2), Long.valueOf(l3), Long.valueOf(l4), Long.valueOf(l5) }));
/*    */   }
/*    */   
/*    */   public static String getFormatLongHours(long paramLong) {
/* 24 */     long l1 = paramLong / 1000L;
/*    */     
/* 26 */     long l2 = l1 / 3600L;
/* 27 */     long l3 = l1 % 3600L / 60L;
/* 28 */     long l4 = l1 % 60L;
/*    */     
/* 30 */     String str = Message.TIME_HOUR.msg();
/* 31 */     str = str.replace("%second%", ((l4 <= 1L) ? Message.FORMAT_SECOND : Message.FORMAT_SECONDS).msg());
/* 32 */     str = str.replace("%minute%", ((l3 <= 1L) ? Message.FORMAT_MINUTE : Message.FORMAT_MINUTES).msg());
/* 33 */     str = str.replace("%hour%", ((l2 <= 1L) ? Message.FORMAT_HOUR : Message.FORMAT_HOURS).msg());
/* 34 */     return format(String.format(str, new Object[] { Long.valueOf(l2), Long.valueOf(l3), Long.valueOf(l4) }));
/*    */   }
/*    */ 
/*    */   
/*    */   public static String getFormatLongMinutes(long paramLong) {
/* 39 */     long l1 = paramLong / 1000L;
/*    */     
/* 41 */     long l2 = l1 % 3600L / 60L;
/* 42 */     long l3 = l1 % 60L;
/*    */     
/* 44 */     String str = Message.TIME_MINUTE.msg();
/* 45 */     str = str.replace("%second%", ((l3 <= 1L) ? Message.FORMAT_SECOND : Message.FORMAT_SECONDS).msg());
/* 46 */     str = str.replace("%minute%", ((l2 <= 1L) ? Message.FORMAT_MINUTE : Message.FORMAT_MINUTES).msg());
/* 47 */     return format(String.format(str, new Object[] { Long.valueOf(l2), Long.valueOf(l3) }));
/*    */   }
/*    */   
/*    */   public static String getFormatLongSecondes(long paramLong) {
/* 51 */     long l1 = paramLong / 1000L;
/*    */     
/* 53 */     long l2 = l1 % 60L;
/* 54 */     String str = Message.TIME_SECOND.msg();
/* 55 */     str = str.replace("%second%", ((l2 <= 1L) ? Message.FORMAT_SECOND : Message.FORMAT_SECONDS).msg());
/* 56 */     return format(String.format(str, new Object[] { Long.valueOf(l2) }));
/*    */   }
/*    */   
/*    */   public static String getStringTime(long paramLong) {
/* 60 */     if (paramLong < 60L)
/* 61 */       return getFormatLongSecondes(paramLong * 1000L); 
/* 62 */     if (paramLong >= 60L && paramLong < 3600L)
/* 63 */       return getFormatLongMinutes(paramLong * 1000L); 
/* 64 */     if (paramLong >= 3600L && paramLong < 86400L) {
/* 65 */       return getFormatLongHours(paramLong * 1000L);
/*    */     }
/* 67 */     return getFormatLongDays(paramLong * 1000L);
/*    */   }
/*    */ 
/*    */   
/*    */   public static String format(String paramString) {
/* 72 */     paramString = paramString.replace(" 00 " + Message.FORMAT_SECOND.msg(), "");
/* 73 */     paramString = paramString.replace(" 00 " + Message.FORMAT_HOUR.msg(), "");
/* 74 */     paramString = paramString.replace(" 00 " + Message.FORMAT_DAY.msg(), "");
/* 75 */     paramString = paramString.replace(" 00 " + Message.FORMAT_MINUTE.msg(), "");
/* 76 */     return paramString;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\builder\TimerBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */