/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.RemoveAllButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ZRemoveAllButton
/*    */   extends RemoveAllButton
/*    */ {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final InventoryType inventoryType;
/*    */   
/*    */   public ZRemoveAllButton(ZAuctionPlugin paramZAuctionPlugin, InventoryType paramInventoryType) {
/* 19 */     this.plugin = paramZAuctionPlugin;
/* 20 */     this.inventoryType = paramInventoryType;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 25 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 27 */     StorageType storageType = this.inventoryType.equals(InventoryType.BUYING) ? StorageType.BUY : (this.inventoryType.equals(InventoryType.ITEMS) ? StorageType.STORAGE : StorageType.EXPIRE);
/*    */     
/* 29 */     this.plugin.getAuctionManager().removeAll(paramPlayer, storageType);
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryType getInventoryType() {
/* 34 */     return this.inventoryType;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZRemoveAllButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */