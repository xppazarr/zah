/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.util;
/*    */ 
/*    */ import java.util.logging.Logger;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InvalidTickDelayNotifier
/*    */ {
/*    */   public static boolean disableNotifications = false;
/*    */   public static boolean debugMode = false;
/*    */   private static boolean notified = false;
/*    */   
/*    */   public static void notifyOnce(@NotNull Logger paramLogger, long paramLong) {
/* 16 */     if (notified || disableNotifications)
/* 17 */       return;  notified = true;
/*    */ 
/*    */     
/* 20 */     paramLogger.warning(
/* 21 */         String.format("A tick based delay or timer was scheduled with a time span of %d ticks. ", new Object[] { Long.valueOf(paramLong) }) + "The server is already processing the current tick and, as such, won't process new tasks in less than 1 tick. FoliaLib will automatically change time spans of <= 0 ticks to 1 tick going forward. This warning is purely informative and won't impact the operation of the plugin. Plugin developers can disable this warning or enable debug mode to location the source of this warning.");
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 28 */     if (debugMode)
/* 29 */       (new Exception("Debugging information to track down the location of the invalid tick input value"))
/* 30 */         .printStackTrace(); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\foliali\\util\InvalidTickDelayNotifier.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */