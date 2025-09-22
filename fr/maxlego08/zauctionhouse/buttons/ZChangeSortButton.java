/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.ChangeSortButton;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZChangeSortButton
/*    */   extends ChangeSortButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ZChangeSortButton(Plugin paramPlugin) {
/* 16 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 21 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/* 22 */     this.plugin.getAuctionManager().nextSort(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZChangeSortButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */