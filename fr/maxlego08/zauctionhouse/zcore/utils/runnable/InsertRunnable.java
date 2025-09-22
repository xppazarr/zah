/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.Base64ItemStack;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.function.Consumer;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ 
/*    */ public class InsertRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final IConnection connection;
/*    */   private final AuctionItem auctionItem;
/*    */   private final StorageType type;
/*    */   private final String serverName;
/*    */   private final Consumer<AuctionItem> consumer;
/*    */   
/*    */   public InsertRunnable(IConnection paramIConnection, AuctionItem paramAuctionItem, StorageType paramStorageType, String paramString, Consumer<AuctionItem> paramConsumer) {
/* 25 */     this.connection = paramIConnection;
/* 26 */     this.auctionItem = paramAuctionItem;
/* 27 */     this.type = paramStorageType;
/* 28 */     this.serverName = paramString;
/* 29 */     this.consumer = paramConsumer;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 34 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 35 */     requestHelper.insert("%prefix%items", paramSchema -> {
/*    */           paramSchema.uuid("id", this.auctionItem.getUniqueId());
/*    */           
/*    */           if (this.auctionItem.getType().equals(AuctionType.INVENTORY)) {
/*    */             paramSchema.string("itemstack", this.auctionItem.getItemStacks().stream().map(Base64ItemStack::encode).collect(Collectors.joining(";")));
/*    */           } else {
/*    */             paramSchema.string("itemstack", ItemStackUtils.serializeItemStack(this.auctionItem.getItemStack()));
/*    */           } 
/*    */           paramSchema.bigInt("price", this.auctionItem.getPrice());
/*    */           paramSchema.string("seller", this.auctionItem.getSellerUniqueId().toString());
/*    */           paramSchema.string("buyer", (this.auctionItem.getBuyerUniqueId() == null) ? null : this.auctionItem.getBuyerUniqueId().toString());
/*    */           paramSchema.string("economy", this.auctionItem.getEconomyName());
/*    */           paramSchema.string("auction_type", this.auctionItem.getType().name());
/*    */           paramSchema.bigInt("expire_at", this.auctionItem.getExpireAt());
/*    */           paramSchema.string("storage_type", this.type.name());
/*    */           paramSchema.string("sellerName", this.auctionItem.getSellerName());
/*    */           paramSchema.string("server_name", this.serverName);
/*    */           paramSchema.bigInt("priority", this.auctionItem.getPriority());
/*    */         });
/* 54 */     if (this.consumer != null)
/* 55 */       this.consumer.accept(this.auctionItem); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\InsertRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */