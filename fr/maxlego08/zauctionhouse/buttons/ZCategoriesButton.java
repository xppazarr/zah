/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.MenuItemStack;
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ZCategoriesButton
/*    */   extends Button {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final String enableText;
/*    */   private final String disableText;
/*    */   private final List<String> categories;
/*    */   
/*    */   public ZCategoriesButton(ZAuctionPlugin paramZAuctionPlugin, String paramString1, String paramString2, List<String> paramList) {
/* 25 */     this.plugin = paramZAuctionPlugin;
/* 26 */     this.enableText = paramString1;
/* 27 */     this.disableText = paramString2;
/* 28 */     this.categories = paramList;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 34 */     MenuItemStack menuItemStack = getItemStack();
/* 35 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 36 */     Category category = playerCache.getCategory();
/*    */     
/* 38 */     Placeholders placeholders = new Placeholders();
/* 39 */     placeholders.register("category", category.getDisplayName());
/*    */     
/* 41 */     this.categories.forEach(paramString -> {
/*    */           Optional optional = this.plugin.getCategoryManager().getByName(paramString);
/*    */           
/*    */           paramPlaceholders.register(paramString, (paramString.equalsIgnoreCase(paramCategory.getName()) ? this.enableText : this.disableText).replace("%category%", optional.map(Category::getDisplayName).orElse(paramString)));
/*    */         });
/* 46 */     return menuItemStack.build(paramPlayer, false, placeholders);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 51 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 53 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 54 */     Category category = playerCache.getCategory();
/* 55 */     if (category == null)
/*    */       return; 
/* 57 */     int i = this.categories.indexOf(category.getName());
/* 58 */     if (i == -1)
/*    */       return; 
/* 60 */     String str = "";
/* 61 */     if (paramInventoryClickEvent.isRightClick()) {
/*    */       
/* 63 */       str = this.categories.get((i - 1 < 0) ? (this.categories.size() - 1) : (i - 1));
/*    */     } else {
/*    */       
/* 66 */       str = this.categories.get((i + 1 >= this.categories.size()) ? 0 : (i + 1));
/*    */     } 
/*    */     
/* 69 */     if (str.equalsIgnoreCase("home")) {
/* 70 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*    */     } else {
/* 72 */       this.plugin.getCategoryManager().getByName(str).ifPresent(paramCategory -> this.plugin.getzMenuHandler().openCategory(paramPlayer, paramCategory));
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZCategoriesButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */