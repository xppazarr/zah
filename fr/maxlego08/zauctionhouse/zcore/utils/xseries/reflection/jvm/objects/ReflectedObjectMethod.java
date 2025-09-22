/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ import java.lang.reflect.Member;
/*    */ import java.lang.reflect.Method;
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
/*    */ final class ReflectedObjectMethod
/*    */   extends AbstractMemberReflectedObject
/*    */ {
/*    */   private final Method delegate;
/*    */   
/*    */   ReflectedObjectMethod(Method paramMethod) {
/* 31 */     this.delegate = paramMethod;
/*    */   }
/*    */   
/*    */   public ReflectedObject.Type type() {
/* 35 */     return ReflectedObject.Type.METHOD;
/*    */   }
/*    */ 
/*    */   
/*    */   public Method unreflect() {
/* 40 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Member member() {
/* 45 */     return this.delegate;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\ReflectedObjectMethod.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */