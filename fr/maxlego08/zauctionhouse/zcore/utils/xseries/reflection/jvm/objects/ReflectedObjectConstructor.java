/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ import java.lang.reflect.Constructor;
/*    */ import java.lang.reflect.Member;
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
/*    */ final class ReflectedObjectConstructor
/*    */   extends AbstractMemberReflectedObject
/*    */ {
/*    */   private final Constructor<?> delegate;
/*    */   
/*    */   ReflectedObjectConstructor(Constructor<?> paramConstructor) {
/* 31 */     this.delegate = paramConstructor;
/*    */   }
/*    */   
/*    */   public ReflectedObject.Type type() {
/* 35 */     return ReflectedObject.Type.CONSTRUCTOR;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String name() {
/* 43 */     return "<init>";
/*    */   }
/*    */ 
/*    */   
/*    */   public Constructor<?> unreflect() {
/* 48 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Member member() {
/* 53 */     return this.delegate;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\ReflectedObjectConstructor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */