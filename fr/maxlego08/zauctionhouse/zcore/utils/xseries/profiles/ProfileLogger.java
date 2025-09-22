/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles;
/*    */ 
/*    */ import org.apache.logging.log4j.LogManager;
/*    */ import org.apache.logging.log4j.Logger;
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
/*    */ @Internal
/*    */ public final class ProfileLogger
/*    */ {
/* 31 */   public static final Logger LOGGER = LogManager.getLogger("XSkull");
/*    */   
/*    */   public static void debug(String paramString, Object... paramVarArgs) {
/* 34 */     LOGGER.debug(paramString, paramVarArgs);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\ProfileLogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */