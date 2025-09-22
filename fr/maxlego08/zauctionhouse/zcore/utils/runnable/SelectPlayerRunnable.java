/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.dto.OptionDTO;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import java.util.function.Consumer;
/*    */ 
/*    */ public class SelectPlayerRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final UUID playerUUID;
/*    */   private final Consumer<PlayerCache> consumer;
/*    */   
/*    */   public SelectPlayerRunnable(IConnection paramIConnection, UUID paramUUID, Consumer<PlayerCache> paramConsumer) {
/* 21 */     this.connection = paramIConnection;
/* 22 */     this.playerUUID = paramUUID;
/* 23 */     this.consumer = paramConsumer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 28 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 29 */     List<OptionDTO> list = requestHelper.select("%prefix%options", OptionDTO.class, paramSchema -> paramSchema.where("player_id", this.playerUUID));
/* 30 */     if (list.isEmpty())
/*    */       return; 
/* 32 */     OptionDTO optionDTO = list.get(0);
/*    */     
/* 34 */     PlayerCache playerCache = new PlayerCache();
/* 35 */     playerCache.setDisableSellConfirmation(optionDTO.isDisableSellConfirmation());
/* 36 */     playerCache.setSorting(Sorting.valueOf(optionDTO.getSorting()));
/*    */     
/* 38 */     this.consumer.accept(playerCache);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\SelectPlayerRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */