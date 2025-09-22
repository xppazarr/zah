/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
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
/*    */ 
/*    */ @Internal
/*    */ class CachedReflectiveHandle<T>
/*    */   implements ReflectiveHandle<T>
/*    */ {
/*    */   private final ReflectiveHandle<T> delegate;
/*    */   private T cache;
/*    */   private CachedReflectiveHandle<ReflectedObject> jvm;
/*    */   
/*    */   CachedReflectiveHandle(ReflectiveHandle<T> paramReflectiveHandle) {
/* 43 */     this.delegate = paramReflectiveHandle;
/*    */   }
/*    */   
/*    */   public ReflectiveHandle<T> getDelegate() {
/* 47 */     return this.delegate;
/*    */   }
/*    */ 
/*    */   
/*    */   public ReflectiveHandle<T> copy() {
/* 52 */     return this.delegate.copy();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public T reflect() {
/* 57 */     return (this.cache == null) ? (this.cache = this.delegate.reflect()) : this.cache;
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ReflectiveHandle<ReflectedObject> jvm() {
/* 62 */     return (this.jvm == null) ? (this.jvm = new CachedReflectiveHandle((ReflectiveHandle)this.delegate.jvm())) : this.jvm;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\CachedReflectiveHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */