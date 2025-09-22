/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.builder;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.ProfileChangeException;
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
/*    */ public final class ProfileFallback<T>
/*    */ {
/*    */   private final ProfileInstruction<T> instruction;
/*    */   private T object;
/*    */   private final ProfileChangeException error;
/*    */   
/*    */   public ProfileFallback(ProfileInstruction<T> paramProfileInstruction, T paramT, ProfileChangeException paramProfileChangeException) {
/* 39 */     this.instruction = paramProfileInstruction;
/* 40 */     this.object = paramT;
/* 41 */     this.error = paramProfileChangeException;
/*    */   }
/*    */   
/*    */   public T getObject() {
/* 45 */     return this.object;
/*    */   }
/*    */   
/*    */   public ProfileInstruction<T> getInstruction() {
/* 49 */     return this.instruction;
/*    */   }
/*    */   
/*    */   public void setObject(T paramT) {
/* 53 */     this.object = paramT;
/*    */   }
/*    */   
/*    */   public ProfileChangeException getError() {
/* 57 */     return this.error;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\builder\ProfileFallback.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */