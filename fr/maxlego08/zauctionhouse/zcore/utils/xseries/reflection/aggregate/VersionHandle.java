/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ public final class VersionHandle<T>
/*     */ {
/*     */   private int version;
/*     */   private int patch;
/*     */   private T handle;
/*     */   
/*     */   @Internal
/*     */   public VersionHandle(int paramInt, T paramT) {
/*  47 */     this(paramInt, 0, paramT);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public VersionHandle(int paramInt1, int paramInt2, T paramT) {
/*  55 */     if (XReflection.supports(paramInt1, paramInt2)) {
/*  56 */       this.version = paramInt1;
/*  57 */       this.patch = paramInt2;
/*  58 */       this.handle = paramT;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public VersionHandle(int paramInt1, int paramInt2, Callable<T> paramCallable) {
/*  67 */     if (XReflection.supports(paramInt1, paramInt2)) {
/*  68 */       this.version = paramInt1;
/*  69 */       this.patch = paramInt2;
/*     */       
/*     */       try {
/*  72 */         this.handle = paramCallable.call();
/*  73 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public VersionHandle(int paramInt, Callable<T> paramCallable) {
/*  80 */     this(paramInt, 0, paramCallable);
/*     */   }
/*     */   
/*     */   public VersionHandle<T> v(int paramInt, T paramT) {
/*  84 */     return v(paramInt, 0, paramT);
/*     */   }
/*     */   
/*     */   private boolean checkVersion(int paramInt1, int paramInt2) {
/*  88 */     if (paramInt1 == this.version && paramInt2 == this.patch)
/*  89 */       throw new IllegalArgumentException("Cannot have duplicate version handles for version: " + paramInt1 + '.' + paramInt2); 
/*  90 */     return (paramInt1 > this.version && paramInt2 >= this.patch && XReflection.supports(paramInt1, paramInt2));
/*     */   }
/*     */   
/*     */   public VersionHandle<T> v(int paramInt1, int paramInt2, Callable<T> paramCallable) {
/*  94 */     if (!checkVersion(paramInt1, paramInt2)) return this;
/*     */     
/*     */     try {
/*  97 */       this.handle = paramCallable.call();
/*  98 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/* 101 */     this.version = paramInt1;
/* 102 */     this.patch = paramInt2;
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public VersionHandle<T> v(int paramInt1, int paramInt2, T paramT) {
/* 107 */     if (checkVersion(paramInt1, paramInt2)) {
/* 108 */       this.version = paramInt1;
/* 109 */       this.patch = paramInt2;
/* 110 */       this.handle = paramT;
/*     */     } 
/* 112 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T orElse(T paramT) {
/* 121 */     return (this.version == 0) ? paramT : this.handle;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public T orElse(Callable<T> paramCallable) {
/* 130 */     if (this.version == 0) {
/*     */       try {
/* 132 */         return paramCallable.call();
/* 133 */       } catch (Exception exception) {
/* 134 */         throw new IllegalArgumentException("The last handle also failed", exception);
/*     */       } 
/*     */     }
/* 137 */     return this.handle;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\aggregate\VersionHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */