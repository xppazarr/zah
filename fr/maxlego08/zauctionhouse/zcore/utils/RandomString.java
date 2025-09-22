/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import java.util.Locale;
/*    */ import java.util.Objects;
/*    */ import java.util.Random;
/*    */ 
/*    */ 
/*    */ public class RandomString
/*    */ {
/*    */   public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
/*    */   
/*    */   public String nextString() {
/* 14 */     for (byte b = 0; b < this.buf.length; b++)
/* 15 */       this.buf[b] = this.symbols[this.random.nextInt(this.symbols.length)]; 
/* 16 */     return new String(this.buf);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public static final String lower = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase(Locale.ROOT);
/*    */   
/*    */   public static final String digits = "0123456789";
/*    */   
/* 25 */   public static final String alphanum = "ABCDEFGHIJKLMNOPQRSTUVWXYZ" + lower + "0123456789";
/*    */   
/*    */   private final Random random;
/*    */   
/*    */   private final char[] symbols;
/*    */   
/*    */   private final char[] buf;
/*    */   
/*    */   public RandomString(int paramInt, Random paramRandom, String paramString) {
/* 34 */     if (paramInt < 1) throw new IllegalArgumentException(); 
/* 35 */     if (paramString.length() < 2) throw new IllegalArgumentException(); 
/* 36 */     this.random = Objects.<Random>requireNonNull(paramRandom);
/* 37 */     this.symbols = paramString.toCharArray();
/* 38 */     this.buf = new char[paramInt];
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RandomString(int paramInt, Random paramRandom) {
/* 45 */     this(paramInt, paramRandom, alphanum);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RandomString(int paramInt) {
/* 52 */     this(paramInt, new SecureRandom());
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public RandomString() {
/* 59 */     this(21);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\RandomString.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */