/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XAccessFlag;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.classes.ClassHandle;
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
/*    */ public abstract class FlaggedNamedMemberHandle
/*    */   extends NamedMemberHandle
/*    */ {
/*    */   protected ClassHandle returnType;
/*    */   
/*    */   protected FlaggedNamedMemberHandle(ClassHandle paramClassHandle) {
/* 39 */     super(paramClassHandle);
/*    */   }
/*    */   
/*    */   public FlaggedNamedMemberHandle asStatic() {
/* 43 */     this.accessFlags.add(XAccessFlag.STATIC);
/* 44 */     return this;
/*    */   }
/*    */   
/*    */   public FlaggedNamedMemberHandle returns(Class<?> paramClass) {
/* 48 */     this.returnType = (ClassHandle)XReflection.of(paramClass);
/* 49 */     return this;
/*    */   }
/*    */   
/*    */   public FlaggedNamedMemberHandle returns(ClassHandle paramClassHandle) {
/* 53 */     this.returnType = paramClassHandle;
/* 54 */     return this;
/*    */   }
/*    */   
/*    */   public static Class<?>[] getParameters(Object paramObject, ClassHandle[] paramArrayOfClassHandle) {
/* 58 */     Class[] arrayOfClass = new Class[paramArrayOfClassHandle.length];
/* 59 */     byte b = 0;
/* 60 */     for (ClassHandle classHandle : paramArrayOfClassHandle) {
/*    */       try {
/* 62 */         arrayOfClass[b++] = (Class)classHandle.unreflect();
/* 63 */       } catch (Throwable throwable) {
/* 64 */         throw XReflection.throwCheckedException(new ReflectiveOperationException("Unknown parameter " + classHandle + " for " + paramObject, throwable));
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 69 */     return arrayOfClass;
/*    */   }
/*    */   
/*    */   protected Class<?> getReturnType() {
/*    */     try {
/* 74 */       return (Class)this.returnType.unreflect();
/* 75 */     } catch (Throwable throwable) {
/* 76 */       throw XReflection.throwCheckedException(new ReflectiveOperationException("Unknown return type " + this.returnType + " for " + this));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\FlaggedNamedMemberHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */