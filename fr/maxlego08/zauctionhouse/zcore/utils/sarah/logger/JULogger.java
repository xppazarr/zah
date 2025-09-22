/*   */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger;
/*   */ import java.util.logging.Logger;
/*   */ 
/*   */ public class JULogger {
/*   */   public static Logger from(final Logger logger) {
/* 6 */     return new Logger()
/*   */       {
/*   */         public void info(String param1String) {
/* 9 */           logger.info(param1String);
/*   */         }
/*   */       };
/*   */   }
/*   */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\logger\JULogger.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */