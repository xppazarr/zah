/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.Inventory;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.AdminRemoveButton;
/*    */ import java.util.List;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ZAdminRemoveButton
/*    */   extends AdminRemoveButton
/*    */ {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final boolean isSilent;
/*    */   private final boolean isForceRemove;
/*    */   
/*    */   public ZAdminRemoveButton(ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean1, boolean paramBoolean2) {
/* 23 */     this.plugin = paramZAuctionPlugin;
/* 24 */     this.isSilent = paramBoolean1;
/* 25 */     this.isForceRemove = paramBoolean2;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 30 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/* 31 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 32 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/* 33 */     auctionItem.setAlreadyBuying(false);
/* 34 */     this.plugin.getAuctionManager().removeAdmin(paramPlayer, auctionItem, this.isSilent, this.isForceRemove);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSilent() {
/* 39 */     return this.isSilent;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isForceRemove() {
/* 44 */     return this.isForceRemove;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBackClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, List<Inventory> paramList, Inventory paramInventory, int paramInt) {
/* 49 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 54 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZAdminRemoveButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */