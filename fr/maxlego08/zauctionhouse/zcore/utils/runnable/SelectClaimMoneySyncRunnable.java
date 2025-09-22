/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.dto.TransactionDTO;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class SelectClaimMoneySyncRunnable
/*    */   extends ZUtils
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final UUID uniqueId;
/*    */   private final String economyName;
/*    */   
/*    */   public SelectClaimMoneySyncRunnable(IConnection paramIConnection, UUID paramUUID, String paramString) {
/* 19 */     this.connection = paramIConnection;
/* 20 */     this.uniqueId = paramUUID;
/* 21 */     this.economyName = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public long fetchMoney() {
/* 26 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 27 */     List list = requestHelper.select("%prefix%transactions", TransactionDTO.class, paramSchema -> {
/*    */           paramSchema.where("seller", this.uniqueId);
/*    */           
/*    */           paramSchema.where("need_money", Boolean.valueOf(true));
/*    */           paramSchema.where("economy", this.economyName);
/*    */         });
/* 33 */     return list.stream().mapToLong(TransactionDTO::getPrice).sum();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\SelectClaimMoneySyncRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */