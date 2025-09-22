/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions;
/*    */ 
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidProfileException
/*    */   extends ProfileException
/*    */ {
/*    */   private final String value;
/*    */   
/*    */   public InvalidProfileException(String paramString1, String paramString2) {
/* 41 */     super(paramString2);
/* 42 */     this.value = paramString1;
/*    */   }
/*    */   
/*    */   public InvalidProfileException(String paramString1, String paramString2, Throwable paramThrowable) {
/* 46 */     super(paramString2, paramThrowable);
/* 47 */     this.value = paramString1;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public String getValue() {
/* 55 */     return this.value;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\exceptions\InvalidProfileException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */