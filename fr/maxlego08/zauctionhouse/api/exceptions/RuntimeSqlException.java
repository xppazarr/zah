/*    */ package fr.maxlego08.zauctionhouse.api.exceptions;
/*    */ 
/*    */ 
/*    */ public class RuntimeSqlException
/*    */   extends RuntimeException
/*    */ {
/*    */   private static final long serialVersionUID = 5224696788505678598L;
/*    */   
/*    */   public RuntimeSqlException() {}
/*    */   
/*    */   public RuntimeSqlException(String paramString) {
/* 12 */     super(paramString);
/*    */   }
/*    */   
/*    */   public RuntimeSqlException(String paramString, Throwable paramThrowable) {
/* 16 */     super(paramString, paramThrowable);
/*    */   }
/*    */   
/*    */   public RuntimeSqlException(Throwable paramThrowable) {
/* 20 */     super(paramThrowable);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\exceptions\RuntimeSqlException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */