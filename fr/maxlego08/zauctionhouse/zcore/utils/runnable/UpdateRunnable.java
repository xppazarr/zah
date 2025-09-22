/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class UpdateRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final AuctionItem auctionItem;
/*    */   private final StorageType type;
/*    */   
/*    */   public UpdateRunnable(IConnection paramIConnection, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 19 */     this.connection = paramIConnection;
/* 20 */     this.auctionItem = paramAuctionItem;
/* 21 */     this.type = paramStorageType;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 27 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 28 */     requestHelper.update("%prefix%items", paramSchema -> {
/*    */           paramSchema.where("id", this.auctionItem.getUniqueId().toString());
/*    */           UUID uUID = this.auctionItem.getBuyerUniqueId();
/*    */           if (uUID == null) {
/*    */             paramSchema.object("buyer", null);
/*    */           } else {
/*    */             paramSchema.uuid("buyer", this.auctionItem.getBuyerUniqueId());
/*    */           } 
/*    */           paramSchema.bigInt("expire_at", this.auctionItem.getExpireAt());
/*    */           paramSchema.string("storage_type", this.type.name());
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\UpdateRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */