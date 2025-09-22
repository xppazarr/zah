/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.UUID;
/*    */ 
/*    */ public class UpsertOptionRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final UUID playerUUID;
/*    */   private final PlayerCache playerCache;
/*    */   
/*    */   public UpsertOptionRunnable(IConnection paramIConnection, UUID paramUUID, PlayerCache paramPlayerCache) {
/* 17 */     this.connection = paramIConnection;
/* 18 */     this.playerUUID = paramUUID;
/* 19 */     this.playerCache = paramPlayerCache;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 24 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 25 */     requestHelper.upsert("%prefix%options", paramSchema -> {
/*    */           paramSchema.uuid("player_id", this.playerUUID).primary();
/*    */           paramSchema.bool("disable_sell_confirmation", this.playerCache.isDisableSellConfirmation());
/*    */           paramSchema.string("sorting", this.playerCache.getSorting().name());
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\UpsertOptionRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */