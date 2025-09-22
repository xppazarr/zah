/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import java.lang.reflect.AnnotatedElement;
/*    */ import java.lang.reflect.Field;
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
/*    */ final class ReflectedObjectField
/*    */   extends AbstractMemberReflectedObject
/*    */ {
/*    */   private final Field delegate;
/*    */   
/*    */   ReflectedObjectField(Field paramField) {
/* 31 */     this.delegate = paramField;
/*    */   }
/*    */   
/*    */   public ReflectedObject.Type type() {
/* 35 */     return ReflectedObject.Type.FIELD;
/*    */   }
/*    */ 
/*    */   
/*    */   public Field unreflect() {
/* 40 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   protected Member member() {
/* 45 */     return this.delegate;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\ReflectedObjectField.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */