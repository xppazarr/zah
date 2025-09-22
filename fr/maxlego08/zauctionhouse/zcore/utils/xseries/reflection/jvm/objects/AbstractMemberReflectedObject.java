/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.reflect.AnnotatedElement;
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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ abstract class AbstractMemberReflectedObject
/*    */   extends AbstractReflectedObject
/*    */ {
/*    */   public String name() {
/* 36 */     return member().getName();
/*    */   }
/*    */ 
/*    */   
/*    */   public final Class<?> getDeclaringClass() {
/* 41 */     return member().getDeclaringClass();
/*    */   }
/*    */ 
/*    */   
/*    */   public final int getModifiers() {
/* 46 */     return member().getModifiers();
/*    */   }
/*    */   
/*    */   public abstract AnnotatedElement unreflect();
/*    */   
/*    */   protected abstract Member member();
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\AbstractMemberReflectedObject.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */