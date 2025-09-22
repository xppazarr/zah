/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.CategoryButton;
/*    */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ZCategoryButton
/*    */   extends CategoryButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final String category;
/*    */   
/*    */   public ZCategoryButton(ZAuctionPlugin paramZAuctionPlugin, String paramString) {
/* 17 */     this.plugin = paramZAuctionPlugin;
/* 18 */     this.category = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 23 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/* 24 */     this.plugin.getCategoryManager().getByName(this.category).ifPresent(paramCategory -> this.plugin.getzMenuHandler().openCategory(paramPlayer, paramCategory));
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getCategory() {
/* 31 */     return this.category;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZCategoryButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */