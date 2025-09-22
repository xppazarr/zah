/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.SellSlotButton;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.PlayerInventory;
/*    */ 
/*    */ public class ZSellSlotButton
/*    */   extends SellSlotButton
/*    */ {
/*    */   public void onInventoryOpen(Player paramPlayer, InventoryEngine paramInventoryEngine, Placeholders paramPlaceholders) {
/* 15 */     super.onInventoryOpen(paramPlayer, paramInventoryEngine, paramPlaceholders);
/* 16 */     paramInventoryEngine.setDisableClick(false);
/* 17 */     paramInventoryEngine.setDisablePlayerInventoryClick(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender(Player paramPlayer, InventoryEngine paramInventoryEngine) {}
/*    */ 
/*    */   
/*    */   public boolean hasSpecialRender() {
/* 26 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 31 */     super.onInventoryClose(paramPlayer, paramInventoryEngine);
/* 32 */     Inventory inventory = paramInventoryEngine.getSpigotInventory();
/* 33 */     this.slots.forEach(paramInteger -> {
/*    */           ItemStack itemStack = paramInventory.getItem(paramInteger.intValue());
/*    */           if (itemStack != null) {
/*    */             give(paramPlayer, itemStack);
/*    */           }
/*    */         });
/*    */   }
/*    */   
/*    */   protected boolean hasInventoryFull(Player paramPlayer) {
/* 42 */     byte b1 = 0;
/* 43 */     PlayerInventory playerInventory = paramPlayer.getInventory();
/* 44 */     for (byte b2 = 0; b2 != 36; b2++) {
/* 45 */       ItemStack itemStack = playerInventory.getContents()[b2];
/* 46 */       if (itemStack == null) b1++; 
/*    */     } 
/* 48 */     return (b1 == 0);
/*    */   }
/*    */   
/*    */   protected void give(Player paramPlayer, ItemStack paramItemStack) {
/* 52 */     if (hasInventoryFull(paramPlayer)) { paramPlayer.getWorld().dropItem(paramPlayer.getLocation(), paramItemStack); }
/* 53 */     else { paramPlayer.getInventory().addItem(new ItemStack[] { paramItemStack }); }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZSellSlotButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */