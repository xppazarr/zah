/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.objects;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
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
/*    */ public final class ReflectedObjectHandle
/*    */   implements ReflectiveHandle<ReflectedObject>
/*    */ {
/*    */   private final ReflectiveOperation jvmGetter;
/*    */   
/*    */   public ReflectedObjectHandle(ReflectiveOperation paramReflectiveOperation) {
/* 31 */     this.jvmGetter = paramReflectiveOperation;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReflectiveHandle<ReflectedObject> copy() {
/* 40 */     return new ReflectedObjectHandle(this.jvmGetter);
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ReflectedObject reflect() {
/* 45 */     return this.jvmGetter.get();
/*    */   }
/*    */   
/*    */   @NotNull
/*    */   public ReflectiveHandle<ReflectedObject> jvm() {
/* 50 */     return this;
/*    */   }
/*    */   
/*    */   @FunctionalInterface
/*    */   public static interface ReflectiveOperation {
/*    */     ReflectedObject get() throws ReflectiveOperationException;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\jvm\objects\ReflectedObjectHandle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */