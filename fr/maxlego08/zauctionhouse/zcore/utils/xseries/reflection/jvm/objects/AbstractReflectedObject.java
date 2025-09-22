/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.annotation.Annotation;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AbstractReflectedObject
/*    */   implements ReflectedObject
/*    */ {
/*    */   public final <A extends Annotation> A getAnnotation(Class<A> paramClass) {
/* 34 */     return unreflect().getAnnotation(paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean isAnnotationPresent(Class<? extends Annotation> paramClass) {
/* 39 */     return unreflect().isAnnotationPresent(paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public final <A extends Annotation> A[] getAnnotationsByType(Class<A> paramClass) {
/* 44 */     return unreflect().getAnnotationsByType(paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public final Annotation[] getAnnotations() {
/* 49 */     return unreflect().getAnnotations();
/*    */   }
/*    */ 
/*    */   
/*    */   public final <A extends Annotation> A getDeclaredAnnotation(Class<A> paramClass) {
/* 54 */     return unreflect().getDeclaredAnnotation(paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public final <A extends Annotation> A[] getDeclaredAnnotationsByType(Class<A> paramClass) {
/* 59 */     return unreflect().getDeclaredAnnotationsByType(paramClass);
/*    */   }
/*    */ 
/*    */   
/*    */   public final Annotation[] getDeclaredAnnotations() {
/* 64 */     return unreflect().getDeclaredAnnotations();
/*    */   }
/*    */ 
/*    */   
/*    */   public final boolean equals(Object paramObject) {
/* 69 */     if (this == paramObject) return true; 
/* 70 */     if (paramObject == null) return false;
/*    */     
/* 72 */     if (paramObject instanceof ReflectedObject) return unreflect().equals(((ReflectedObject)paramObject).unreflect()); 
/* 73 */     return unreflect().equals(paramObject);
/*    */   }
/*    */ 
/*    */   
/*    */   public final int hashCode() {
/* 78 */     return unreflect().hashCode();
/*    */   }
/*    */ 
/*    */   
/*    */   public final String toString() {
/* 83 */     return getClass().getSimpleName() + '(' + unreflect() + ')';
/*    */   }
/*    */   
/*    */   public abstract AnnotatedElement unreflect();
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\AbstractReflectedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */