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
/*    */ public final class InvalidProfileContainerException
/*    */   extends ProfileException
/*    */ {
/*    */   private final Object container;
/*    */   
/*    */   public InvalidProfileContainerException(Object paramObject, String paramString) {
/* 34 */     super(paramString);
/* 35 */     this.container = paramObject;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   public Object getContainer() {
/* 43 */     return this.container;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\exceptions\InvalidProfileContainerException.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */