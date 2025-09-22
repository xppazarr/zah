/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.MenuItemStack;
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.DisplaySorting;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ZChangeSortingButton
/*    */   extends Button
/*    */ {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final String enableText;
/*    */   private final String disableText;
/*    */   private final List<DisplaySorting> displaySortings;
/*    */   
/*    */   public ZChangeSortingButton(ZAuctionPlugin paramZAuctionPlugin, String paramString1, String paramString2, List<DisplaySorting> paramList) {
/* 25 */     this.plugin = paramZAuctionPlugin;
/* 26 */     this.enableText = paramString1;
/* 27 */     this.disableText = paramString2;
/* 28 */     this.displaySortings = paramList;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 34 */     MenuItemStack menuItemStack = getItemStack();
/* 35 */     Placeholders placeholders = new Placeholders();
/*    */     
/* 37 */     this.displaySortings.forEach(paramDisplaySorting -> {
/*    */           Sorting sorting1 = this.plugin.getAuctionManager().getSort(paramPlayer);
/*    */           
/*    */           Sorting sorting2 = paramDisplaySorting.getSorting();
/*    */           paramPlaceholders.register(sorting2.name(), ((sorting2 == sorting1) ? this.enableText : this.disableText).replace("%sorting%", paramDisplaySorting.getDisplayString()));
/*    */         });
/* 43 */     return menuItemStack.build(paramPlayer, false, placeholders);
/*    */   }
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/*    */     DisplaySorting displaySorting2;
/* 48 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 50 */     Sorting sorting = this.plugin.getAuctionManager().getSort(paramPlayer);
/*    */     
/* 52 */     Optional<DisplaySorting> optional = this.displaySortings.stream().filter(paramDisplaySorting -> (paramDisplaySorting.getSorting() == paramSorting)).findFirst();
/*    */     
/* 54 */     if (!optional.isPresent())
/* 55 */       return;  DisplaySorting displaySorting1 = optional.get();
/*    */     
/* 57 */     int i = this.displaySortings.indexOf(displaySorting1);
/* 58 */     if (i == -1) {
/*    */       return;
/*    */     }
/* 61 */     if (paramInventoryClickEvent.isRightClick()) {
/*    */       
/* 63 */       displaySorting2 = this.displaySortings.get((i - 1 < 0) ? (this.displaySortings.size() - 1) : (i - 1));
/*    */     } else {
/*    */       
/* 66 */       displaySorting2 = this.displaySortings.get((i + 1 >= this.displaySortings.size()) ? 0 : (i + 1));
/*    */     } 
/*    */     
/* 69 */     this.plugin.getAuctionManager().setSort(paramPlayer, displaySorting2.getSorting());
/* 70 */     this.plugin.getzMenuHandler().update(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZChangeSortingButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */