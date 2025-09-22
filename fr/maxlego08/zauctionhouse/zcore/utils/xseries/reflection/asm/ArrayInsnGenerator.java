/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.asm;
/*    */ 
/*    */ import org.objectweb.asm.Type;
/*    */ import org.objectweb.asm.commons.GeneratorAdapter;
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
/*    */ 
/*    */ 
/*    */ final class ArrayInsnGenerator
/*    */ {
/*    */   private final GeneratorAdapter mv;
/*    */   private final int length;
/* 39 */   private int index = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   private final int storeInsn;
/*    */ 
/*    */ 
/*    */   
/*    */   public ArrayInsnGenerator(GeneratorAdapter paramGeneratorAdapter, Class<?> paramClass, int paramInt) {
/* 48 */     if (paramClass.getComponentType() != null) {
/* 49 */       throw new IllegalArgumentException("The raw array element type must be given, not the array type itself: " + paramClass);
/*    */     }
/*    */     
/* 52 */     this.mv = paramGeneratorAdapter;
/* 53 */     this.length = paramInt;
/* 54 */     Type type = Type.getType(paramClass);
/* 55 */     this.storeInsn = (paramClass == Object.class) ? -1 : type.getOpcode(79);
/*    */     
/* 57 */     paramGeneratorAdapter.push(paramInt);
/* 58 */     paramGeneratorAdapter.newArray(type);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private boolean isDynamicStoreInsn() {
/* 65 */     return (this.storeInsn == -1);
/*    */   }
/*    */   
/*    */   public void add(Runnable paramRunnable) {
/* 69 */     if (isDynamicStoreInsn()) {
/* 70 */       throw new IllegalStateException("Must provide the type of stored object since this is a dynamic type array");
/*    */     }
/* 72 */     add(paramRunnable, this.storeInsn);
/*    */   }
/*    */   
/*    */   public void add(Type paramType, Runnable paramRunnable) {
/* 76 */     add(paramRunnable, paramType.getOpcode(79));
/*    */   }
/*    */   
/*    */   private void add(Runnable paramRunnable, int paramInt) {
/* 80 */     if (this.index >= this.length) {
/* 81 */       throw new IllegalStateException("Array is already full, at index " + this.index);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 90 */     this.mv.visitInsn(89);
/* 91 */     this.mv.push(this.index++);
/*    */     
/* 93 */     paramRunnable.run();
/* 94 */     this.mv.visitInsn(paramInt);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\reflection\asm\ArrayInsnGenerator.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */