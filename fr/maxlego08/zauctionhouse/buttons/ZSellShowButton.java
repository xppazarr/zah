/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.Inventory;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.SellShowButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.block.Container;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.BlockStateMeta;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZSellShowButton
/*    */   extends SellShowButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ZSellShowButton(Plugin paramPlugin) {
/* 28 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onRender(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/*    */     List<ItemStack> list;
/* 34 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 35 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/* 36 */     if (auctionItem == null) {
/*    */       return;
/*    */     }
/*    */     
/* 40 */     if (auctionItem.getType() == AuctionType.INVENTORY) {
/* 41 */       list = auctionItem.getItemStacks();
/*    */     } else {
/*    */       
/* 44 */       ItemStack itemStack = auctionItem.getItemStack();
/* 45 */       list = new ArrayList();
/* 46 */       if (itemStack.getItemMeta() instanceof BlockStateMeta) {
/* 47 */         BlockStateMeta blockStateMeta = (BlockStateMeta)itemStack.getItemMeta();
/* 48 */         if (blockStateMeta.getBlockState() instanceof Container) {
/* 49 */           Container container = (Container)blockStateMeta.getBlockState();
/* 50 */           list = (List)Arrays.<ItemStack>stream(container.getInventory().getContents()).filter(Objects::nonNull).collect(Collectors.toList());
/*    */         } 
/*    */       } 
/*    */     } 
/*    */ 
/*    */     
/* 56 */     for (byte b = 0; b != Math.min(list.size(), this.slots.size()); b++) {
/* 57 */       int i = ((Integer)this.slots.get(b)).intValue();
/* 58 */       ItemStack itemStack = list.get(b);
/* 59 */       paramInventoryEngine.addItem(i, itemStack);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasSpecialRender() {
/* 66 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onBackClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, List<Inventory> paramList, Inventory paramInventory, int paramInt) {
/* 71 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 76 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZSellShowButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */