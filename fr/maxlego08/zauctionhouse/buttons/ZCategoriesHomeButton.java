/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*    */ import java.util.List;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ 
/*    */ public class ZCategoriesHomeButton
/*    */   extends Button {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final List<String> categories;
/*    */   
/*    */   public ZCategoriesHomeButton(ZAuctionPlugin paramZAuctionPlugin, List<String> paramList) {
/* 18 */     this.plugin = paramZAuctionPlugin;
/* 19 */     this.categories = paramList;
/*    */   }
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/*    */     String str;
/* 24 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 26 */     byte b = 0;
/*    */ 
/*    */     
/* 29 */     if (paramInventoryClickEvent.isRightClick()) {
/*    */       
/* 31 */       str = this.categories.get(this.categories.size() - 1);
/*    */     } else {
/*    */       
/* 34 */       str = this.categories.get((b + 1 >= this.categories.size()) ? 0 : (b + 1));
/*    */     } 
/*    */     
/* 37 */     if (str.equalsIgnoreCase("home")) {
/* 38 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*    */     } else {
/* 40 */       this.plugin.getCategoryManager().getByName(str).ifPresent(paramCategory -> this.plugin.getzMenuHandler().openCategory(paramPlayer, paramCategory));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZCategoriesHomeButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */