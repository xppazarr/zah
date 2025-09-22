/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.Inventory;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.RemoveConfirmButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import java.util.List;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ZRemoveConfirmButton
/*    */   extends RemoveConfirmButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final boolean isAdmin;
/*    */   
/*    */   public ZRemoveConfirmButton(ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean) {
/* 25 */     this.plugin = paramZAuctionPlugin;
/* 26 */     this.isAdmin = paramBoolean;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 32 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 33 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/*    */     
/* 35 */     auctionItem.setAlreadyBuying(false);
/*    */     
/* 37 */     if (!auctionItem.canBuy() || auctionItem.isAlreadyBuying() || auctionItem.getStorageType() != StorageType.STORAGE) {
/* 38 */       if (AuctionConfiguration.displayErrorBuyMessage)
/* 39 */         this.plugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_REMOVE_ERROR.getIMessage(), new Object[0]); 
/* 40 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*    */       
/*    */       return;
/*    */     } 
/* 44 */     if (AuctionConfiguration.giveItemAfterRemove && AuctionConfiguration.dontGiveIfPlayerHasFullInventory && !auctionItem.hasFreeSpace(paramPlayer)) {
/* 45 */       this.plugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_REMOVE_FULL.getIMessage(), new Object[0]);
/* 46 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*    */       
/*    */       return;
/*    */     } 
/* 50 */     this.plugin.getAuctionManager().remove(auctionItem, paramPlayer, this.isAdmin);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBackClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, List<Inventory> paramList, Inventory paramInventory, int paramInt) {
/* 55 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 60 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZRemoveConfirmButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */