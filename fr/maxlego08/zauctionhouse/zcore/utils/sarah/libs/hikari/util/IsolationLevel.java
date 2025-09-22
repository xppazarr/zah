/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util;
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
/*    */ public enum IsolationLevel
/*    */ {
/* 21 */   TRANSACTION_NONE(0),
/* 22 */   TRANSACTION_READ_UNCOMMITTED(1),
/* 23 */   TRANSACTION_READ_COMMITTED(2),
/* 24 */   TRANSACTION_REPEATABLE_READ(4),
/* 25 */   TRANSACTION_SERIALIZABLE(8),
/* 26 */   TRANSACTION_SQL_SERVER_SNAPSHOT_ISOLATION_LEVEL(4096);
/*    */   
/*    */   private final int levelId;
/*    */   
/*    */   IsolationLevel(int paramInt1) {
/* 31 */     this.levelId = paramInt1;
/*    */   }
/*    */   
/*    */   public int getLevelId() {
/* 35 */     return this.levelId;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikar\\util\IsolationLevel.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */