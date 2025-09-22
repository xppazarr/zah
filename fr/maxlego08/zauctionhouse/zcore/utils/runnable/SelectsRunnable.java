/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.runnable;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IConnection;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*    */ import fr.maxlego08.zauctionhouse.dto.AuctionItemDTO;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import java.util.List;
/*    */ import java.util.function.Function;
/*    */ import java.util.stream.Collectors;
/*    */ 
/*    */ 
/*    */ public class SelectsRunnable
/*    */   implements Runnable
/*    */ {
/*    */   private final AuctionPlugin plugin;
/*    */   private final IConnection connection;
/*    */   private final IStorage iStorage;
/*    */   private final StorageManager manager;
/* 25 */   private final int tryAmount = 0;
/*    */ 
/*    */   
/*    */   public SelectsRunnable(AuctionPlugin paramAuctionPlugin, IConnection paramIConnection, IStorage paramIStorage, StorageManager paramStorageManager) {
/* 29 */     this.connection = paramIConnection;
/* 30 */     this.iStorage = paramIStorage;
/* 31 */     this.manager = paramStorageManager;
/* 32 */     this.plugin = paramAuctionPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 37 */     this.manager.setReady(false);
/*    */     
/* 39 */     RequestHelper requestHelper = this.connection.getRequestHelper();
/* 40 */     for (StorageType storageType : StorageType.values()) {
/*    */ 
/*    */ 
/*    */       
/* 44 */       List list = (List)requestHelper.select("%prefix%items", AuctionItemDTO.class, paramSchema -> paramSchema.where("storage_type", paramStorageType.name())).stream().map(fr.maxlego08.zauctionhouse.ZAuctionItem::new).collect(Collectors.toList());
/*    */       
/* 46 */       for (AuctionItem auctionItem : list) {
/* 47 */         if (storageType != StorageType.STORAGE) {
/* 48 */           auctionItem.setCanBuy(false); continue;
/*    */         } 
/* 50 */         auctionItem.setCategories(this.plugin.getCategoryManager());
/*    */       } 
/*    */ 
/*    */       
/* 54 */       this.iStorage.saveItems(this.plugin, list, storageType);
/*    */     } 
/*    */     
/* 57 */     Logger.info("Loading of items successfully completed.", Logger.LogType.INFO);
/* 58 */     this.manager.setReady(true);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\runnable\SelectsRunnable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */