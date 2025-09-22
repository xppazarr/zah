/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.zmenu.ZMenuHandler;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZUpdateButton
/*    */   extends Button
/*    */ {
/*    */   private final ZMenuHandler zMenuHandler;
/*    */   
/*    */   public ZUpdateButton(Plugin paramPlugin) {
/* 18 */     this.zMenuHandler = ((ZAuctionPlugin)paramPlugin).getzMenuHandler();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 23 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/* 24 */     this.zMenuHandler.update(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZUpdateButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */