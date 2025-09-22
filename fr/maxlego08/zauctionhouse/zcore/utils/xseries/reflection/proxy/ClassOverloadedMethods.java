/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.proxy;
/*    */ 
/*    */ import java.util.Map;
/*    */ import java.util.function.Supplier;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ @Internal
/*    */ public final class ClassOverloadedMethods<T>
/*    */ {
/*    */   private final Map<String, OverloadedMethod<T>> mapped;
/*    */   
/*    */   public ClassOverloadedMethods(Map<String, OverloadedMethod<T>> paramMap) {
/* 35 */     this.mapped = paramMap;
/*    */   }
/*    */   
/*    */   public Map<String, OverloadedMethod<T>> mappings() {
/* 39 */     return this.mapped;
/*    */   }
/*    */   
/*    */   public T get(String paramString, Supplier<String> paramSupplier) {
/* 43 */     return get(paramString, paramSupplier, false);
/*    */   }
/*    */   
/*    */   public T get(String paramString, Supplier<String> paramSupplier, boolean paramBoolean) {
/* 47 */     OverloadedMethod<Object> overloadedMethod = (OverloadedMethod)this.mapped.get(paramString);
/* 48 */     if (overloadedMethod == null) {
/* 49 */       if (paramBoolean) return null; 
/* 50 */       throw new IllegalArgumentException("Failed to find any method named '" + paramString + "' with descriptor '" + (String)paramSupplier.get() + "' " + this);
/*    */     } 
/*    */     
/* 53 */     T t = (T)overloadedMethod.get(paramSupplier);
/* 54 */     if (t == null) {
/* 55 */       if (paramBoolean) return null; 
/* 56 */       throw new IllegalArgumentException("Failed to find overloaded method named '" + paramString + " with descriptor: '" + (String)paramSupplier
/* 57 */           .get() + "' " + this);
/*    */     } 
/*    */     
/* 60 */     return t;
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 65 */     return getClass().getSimpleName() + '(' + this.mapped + ')';
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\proxy\ClassOverloadedMethods.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */