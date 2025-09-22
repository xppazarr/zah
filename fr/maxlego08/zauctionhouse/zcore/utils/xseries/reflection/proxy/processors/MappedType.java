/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.processors;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy.ReflectiveProxyObject;
/*    */ import java.util.Arrays;
/*    */ import java.util.IdentityHashMap;
/*    */ import java.util.Map;
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
/*    */ public final class MappedType
/*    */ {
/* 32 */   public static final Map<Class<? extends ReflectiveProxyObject>, Class<?>> LOOK_AHEAD = new IdentityHashMap<>();
/*    */   public final Class<?> synthetic;
/*    */   public final Class<?> real;
/*    */   
/*    */   public MappedType(Class<?> paramClass1, Class<?> paramClass2) {
/* 37 */     this.synthetic = paramClass1;
/* 38 */     this.real = paramClass2;
/*    */   }
/*    */   
/*    */   public boolean isDifferent() {
/* 42 */     return (this.synthetic != this.real);
/*    */   }
/*    */   
/*    */   public static Class<?>[] getRealTypes(MappedType[] paramArrayOfMappedType) {
/* 46 */     return (Class[])Arrays.<MappedType>stream(paramArrayOfMappedType).map(paramMappedType -> paramMappedType.real).toArray(paramInt -> new Class[paramInt]);
/*    */   }
/*    */   
/*    */   public static Class<?> getMappedTypeOrCreate(Class<? extends ReflectiveProxyObject> paramClass) {
/* 50 */     Class<?> clazz = LOOK_AHEAD.get(paramClass);
/* 51 */     if (clazz == null) {
/* 52 */       ReflectiveAnnotationProcessor reflectiveAnnotationProcessor = new ReflectiveAnnotationProcessor(paramClass);
/* 53 */       reflectiveAnnotationProcessor.processTargetClass();
/* 54 */       clazz = reflectiveAnnotationProcessor.getTargetClass();
/* 55 */       if (clazz == null) {
/* 56 */         throw new IllegalStateException("Look ahead type " + paramClass + " could not be processed.");
/*    */       }
/*    */     } 
/*    */     
/* 60 */     return clazz;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return "MappedType[synthetic: " + this.synthetic + ", real: " + this.real + ']';
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\processors\MappedType.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */