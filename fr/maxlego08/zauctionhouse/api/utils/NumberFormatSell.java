/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.regex.Matcher;
/*    */ import java.util.regex.Pattern;
/*    */ 
/*    */ public class NumberFormatSell
/*    */ {
/*    */   private final String format;
/*    */   private final long multiplicator;
/* 11 */   private final Pattern pattern = Pattern.compile("^([\\d,.]+)(.*)$");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NumberFormatSell(String paramString, long paramLong) {
/* 19 */     this.format = paramString;
/* 20 */     this.multiplicator = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public NumberFormatSell(Map<String, Object> paramMap) {
/* 28 */     this((String)paramMap.get("format"), ((Number)paramMap.get("multiplication")).longValue());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getFormat() {
/* 35 */     return this.format;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getMultiplicator() {
/* 42 */     return this.multiplicator;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean match(String paramString) {
/* 47 */     Matcher matcher = this.pattern.matcher(paramString);
/*    */     
/* 49 */     if (matcher.find()) {
/* 50 */       String str = matcher.group(2);
/* 51 */       return str.toLowerCase().startsWith(getFormat().toLowerCase());
/*    */     } 
/*    */     
/* 54 */     return false;
/*    */   }
/*    */ 
/*    */   
/*    */   public String replace(String paramString) {
/* 59 */     Matcher matcher = this.pattern.matcher(paramString);
/*    */     
/* 61 */     if (matcher.find()) {
/* 62 */       String str1 = matcher.group(1);
/* 63 */       String str2 = matcher.group(2);
/*    */       
/* 65 */       str2 = str2.toLowerCase().replaceFirst(getFormat().toLowerCase(), "");
/* 66 */       return str1 + str2;
/*    */     } 
/*    */     
/* 69 */     return paramString;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\NumberFormatSell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */