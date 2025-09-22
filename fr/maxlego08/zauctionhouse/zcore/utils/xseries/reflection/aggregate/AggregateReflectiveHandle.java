/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.aggregate;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects.ReflectedObject;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.function.Consumer;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AggregateReflectiveHandle<T, H extends ReflectiveHandle<T>>
/*     */   implements ReflectiveHandle<T>
/*     */ {
/*     */   private final List<Callable<H>> handles;
/*     */   private Consumer<H> handleModifier;
/*     */   
/*     */   @Internal
/*     */   public AggregateReflectiveHandle(Collection<Callable<H>> paramCollection) {
/*  66 */     this.handles = new ArrayList<>(paramCollection.size());
/*  67 */     this.handles.addAll(paramCollection);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AggregateReflectiveHandle<T, H> or(@NotNull H paramH) {
/*  74 */     return or(() -> paramReflectiveHandle);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AggregateReflectiveHandle<T, H> or(@NotNull Callable<H> paramCallable) {
/*  81 */     this.handles.add(paramCallable);
/*  82 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AggregateReflectiveHandle<T, H> modify(@Nullable Consumer<H> paramConsumer) {
/*  89 */     this.handleModifier = paramConsumer;
/*  90 */     return this;
/*     */   }
/*     */   
/*     */   public H getHandle() {
/*  94 */     ClassNotFoundException classNotFoundException = null;
/*     */     
/*  96 */     for (Callable<ReflectiveHandle> callable : this.handles) {
/*     */       
/*     */       try {
/*  99 */         ReflectiveHandle reflectiveHandle = callable.call();
/* 100 */         if (this.handleModifier != null) this.handleModifier.accept((H)reflectiveHandle); 
/* 101 */         if (!reflectiveHandle.exists()) reflectiveHandle.reflect(); 
/* 102 */         return (H)reflectiveHandle;
/* 103 */       } catch (Throwable throwable) {
/* 104 */         if (classNotFoundException == null)
/* 105 */           classNotFoundException = new ClassNotFoundException("None of the aggregate handles were successful"); 
/* 106 */         classNotFoundException.addSuppressed(throwable);
/*     */       } 
/*     */     } 
/*     */     
/* 110 */     throw XReflection.throwCheckedException(XReflection.relativizeSuppressedExceptions(classNotFoundException));
/*     */   }
/*     */ 
/*     */   
/*     */   public AggregateReflectiveHandle<T, H> copy() {
/* 115 */     AggregateReflectiveHandle<T, H> aggregateReflectiveHandle = new AggregateReflectiveHandle(new ArrayList<>(this.handles));
/* 116 */     aggregateReflectiveHandle.handleModifier = this.handleModifier;
/* 117 */     return aggregateReflectiveHandle;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public ReflectiveHandle<ReflectedObject> jvm() {
/* 122 */     return getHandle().jvm();
/*     */   }
/*     */ 
/*     */   
/*     */   public T reflect() {
/* 127 */     ClassNotFoundException classNotFoundException = null;
/*     */     
/* 129 */     for (Callable<ReflectiveHandle> callable : this.handles) {
/*     */       ReflectiveHandle reflectiveHandle;
/*     */       
/*     */       try {
/* 133 */         reflectiveHandle = callable.call();
/* 134 */         if (this.handleModifier != null) this.handleModifier.accept((H)reflectiveHandle); 
/* 135 */       } catch (Throwable throwable) {
/* 136 */         if (classNotFoundException == null)
/* 137 */           classNotFoundException = new ClassNotFoundException("None of the aggregate handles were successful"); 
/* 138 */         classNotFoundException.addSuppressed(throwable);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*     */       try {
/* 144 */         return (T)reflectiveHandle.reflect();
/* 145 */       } catch (Throwable throwable) {
/* 146 */         if (classNotFoundException == null)
/* 147 */           classNotFoundException = new ClassNotFoundException("None of the aggregate handles were successful"); 
/* 148 */         classNotFoundException.addSuppressed(throwable);
/*     */       } 
/*     */     } 
/*     */     
/* 152 */     throw (ClassNotFoundException)XReflection.relativizeSuppressedExceptions(classNotFoundException);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\aggregate\AggregateReflectiveHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */