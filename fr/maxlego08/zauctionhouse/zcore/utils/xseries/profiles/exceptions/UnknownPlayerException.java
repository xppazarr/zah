/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions;
/*    */ 
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
/*    */ public final class UnknownPlayerException
/*    */   extends InvalidProfileException
/*    */ {
/*    */   private final Object unknownObject;
/*    */   
/*    */   public UnknownPlayerException(Object paramObject, String paramString) {
/* 36 */     super(paramObject.toString(), paramString);
/* 37 */     this.unknownObject = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Object getUnknownObject() {
/* 45 */     return this.unknownObject;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\exceptions\UnknownPlayerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */