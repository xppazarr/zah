/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.constraint;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
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
/*    */ public class ReflectiveConstraintException
/*    */   extends RuntimeException
/*    */ {
/*    */   private final ReflectiveConstraint constraint;
/*    */   private final ReflectiveConstraint.Result result;
/*    */   
/*    */   private ReflectiveConstraintException(ReflectiveConstraint paramReflectiveConstraint, ReflectiveConstraint.Result paramResult, String paramString) {
/* 41 */     super(paramString);
/* 42 */     this.constraint = paramReflectiveConstraint;
/* 43 */     this.result = paramResult;
/*    */   }
/*    */   
/*    */   public ReflectiveConstraint getConstraint() {
/* 47 */     return this.constraint;
/*    */   }
/*    */   
/*    */   public ReflectiveConstraint.Result getResult() {
/* 51 */     return this.result;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public static ReflectiveConstraintException create(ReflectiveConstraint paramReflectiveConstraint, ReflectiveConstraint.Result paramResult, ReflectiveHandle<?> paramReflectiveHandle, Object paramObject) {
/*    */     String str;
/* 58 */     switch (paramResult) {
/*    */       case MATCHED:
/* 60 */         throw new IllegalArgumentException("Cannot create an exception if results are successful: " + paramReflectiveConstraint + " -> MATCHED");
/*    */       case INCOMPATIBLE:
/* 62 */         str = "The constraint " + paramReflectiveConstraint + " cannot be applied to " + paramReflectiveHandle;
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
/* 73 */         return new ReflectiveConstraintException(paramReflectiveConstraint, paramResult, str);case NOT_MATCHED: str = "Found " + paramReflectiveHandle + " with JVM " + paramObject + ", however it doesn't match the constraint: " + paramReflectiveConstraint + " - " + (String)XAccessFlag.getModifiers(paramObject).map(XAccessFlag::toString).orElse("[NO MODIFIER]"); return new ReflectiveConstraintException(paramReflectiveConstraint, paramResult, str);
/*    */     } 
/*    */     throw new AssertionError("Unknown reflective constraint result: " + paramResult);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\constraint\ReflectiveConstraintException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */