/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Obsolete;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface ReflectiveHandle<T>
/*     */ {
/*     */   @Experimental
/*     */   @Contract(value = "-> new", pure = true)
/*     */   ReflectiveHandle<T> copy();
/*     */   
/*     */   @Contract(pure = true)
/*     */   default boolean exists() {
/*     */     try {
/*  57 */       reflect();
/*  58 */       return true;
/*  59 */     } catch (ReflectiveOperationException ignored) {
/*  60 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   @Nullable
/*     */   @Obsolete
/*     */   default ReflectiveOperationException catchError() {
/*     */     try {
/*  74 */       reflect();
/*  75 */       return null;
/*  76 */     } catch (ReflectiveOperationException ex) {
/*  77 */       return ex;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   default T unreflect() {
/*     */     try {
/*  93 */       return reflect();
/*  94 */     } catch (ReflectiveOperationException e) {
/*  95 */       throw XReflection.throwCheckedException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   @Contract(pure = true)
/*     */   default T reflectOrNull() {
/*     */     try {
/* 109 */       return reflect();
/* 110 */     } catch (ReflectiveOperationException ignored) {
/* 111 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   T reflect() throws ReflectiveOperationException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Experimental
/*     */   @Contract(pure = true)
/*     */   ReflectiveHandle<ReflectedObject> jvm();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Experimental
/*     */   @Contract(value = "-> new", pure = true)
/*     */   default ReflectiveHandle<T> cached() {
/* 162 */     return new CachedReflectiveHandle<>(copy());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Experimental
/*     */   @Contract(pure = true)
/*     */   default ReflectiveHandle<T> unwrap() {
/* 176 */     if (this instanceof CachedReflectiveHandle) return ((CachedReflectiveHandle<T>)this).getDelegate(); 
/* 177 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\ReflectiveHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */