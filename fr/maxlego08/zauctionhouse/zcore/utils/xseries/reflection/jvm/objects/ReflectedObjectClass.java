/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.reflect.AnnotatedElement;
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
/*    */ final class ReflectedObjectClass
/*    */   extends AbstractReflectedObject
/*    */ {
/*    */   private final Class<?> delegate;
/*    */   
/*    */   ReflectedObjectClass(Class<?> paramClass) {
/* 28 */     this.delegate = paramClass;
/*    */   }
/*    */   
/*    */   public ReflectedObject.Type type() {
/* 32 */     return ReflectedObject.Type.CLASS;
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> unreflect() {
/* 37 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   public String name() {
/* 42 */     return this.delegate.getSimpleName();
/*    */   }
/*    */ 
/*    */   
/*    */   public Class<?> getDeclaringClass() {
/* 47 */     return this.delegate.getDeclaringClass();
/*    */   }
/*    */ 
/*    */   
/*    */   public int getModifiers() {
/* 52 */     return this.delegate.getModifiers();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\ReflectedObjectClass.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */