/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class DeleteRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final UUID uuid;
/*    */   private final StorageType storage;
/*    */   private final Runnable runnable;
/*    */   
/*    */   public DeleteRunnable(IConnection paramIConnection, UUID paramUUID, StorageType paramStorageType, Runnable paramRunnable) {
/* 19 */     this.connection = paramIConnection;
/* 20 */     this.uuid = paramUUID;
/* 21 */     this.storage = paramStorageType;
/* 22 */     this.runnable = paramRunnable;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/*    */     
/* 30 */     requestHelper.delete("%prefix%items", paramSchema -> {
/*    */           paramSchema.where("id", this.uuid.toString());
/*    */           
/*    */           paramSchema.where("storage_type", this.storage.name());
/*    */         });
/* 35 */     if (this.runnable != null) this.runnable.run(); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\DeleteRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */