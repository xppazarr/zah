/*     */ package fr.maxlego08.zauctionhouse.buttons;
/*     */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*     */ import fr.maxlego08.menu.api.engine.ItemButton;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class AuctionHelperButton {
/*     */   public static Consumer<InventoryClickEvent> auctionClick(ZAuctionPlugin paramZAuctionPlugin, AuctionItem paramAuctionItem, Player paramPlayer, InventoryEngine paramInventoryEngine, ItemStack paramItemStack, int paramInt, InventoryType paramInventoryType) {
/*  26 */     return paramInventoryClickEvent -> {
/*     */         AuctionType auctionType = paramAuctionItem.getType();
/*     */ 
/*     */         
/*     */         PlayerCache playerCache = paramZAuctionPlugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/*     */ 
/*     */         
/*     */         if (paramAuctionItem.getStorageType() != StorageType.STORAGE) {
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (paramAuctionItem.isExpired()) {
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (paramAuctionItem.isAlreadyBuying()) {
/*     */           if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */             paramZAuctionPlugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR.getIMessage(), new Object[0]);
/*     */           }
/*     */           
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (!paramAuctionItem.canBuy()) {
/*     */           if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */             paramZAuctionPlugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR.getIMessage(), new Object[0]);
/*     */           }
/*     */           
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else {
/*     */           if (auctionType.equals(AuctionType.INVENTORY) && paramInventoryClickEvent.getClick().equals(AuctionConfiguration.showClick) && !paramInventoryType.equals(InventoryType.SELL_SHOW)) {
/*     */             paramAuctionItem.setAlreadyBuying(true);
/*     */             
/*     */             paramZAuctionPlugin.getzMenuHandler().openSellShow(paramPlayer, paramAuctionItem, paramInventoryEngine.getPage());
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           if (paramInventoryClickEvent.getClick().equals(AuctionConfiguration.showClick)) {
/*     */             Material material = paramItemStack.getType();
/*     */             
/*     */             if (material.name().endsWith("SHULKER_BOX") || material.name().equals("BARREL")) {
/*     */               paramAuctionItem.setAlreadyBuying(true);
/*     */               
/*     */               paramZAuctionPlugin.getzMenuHandler().openSellShow(paramPlayer, paramAuctionItem, paramInventoryEngine.getPage());
/*     */               
/*     */               return;
/*     */             } 
/*     */           } 
/*     */           
/*  70 */           boolean bool = ((paramInventoryClickEvent.getClick().equals(ClickType.MIDDLE) || paramInventoryClickEvent.getClick().equals(ClickType.DROP)) && paramPlayer.hasPermission(Permission.ZAUCTIONHOUSE_ADMIN_REMOVE.getPermission())) ? true : false;
/*     */           AuctionEconomy auctionEconomy = paramAuctionItem.getEconomy();
/*     */           if (!bool && !paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId()) && !auctionEconomy.hasMoney((OfflinePlayer)paramPlayer, paramAuctionItem.getPrice())) {
/*     */             if (AuctionConfiguration.enableBuyErrorSound && AuctionConfiguration.soundBuyError != null) {
/*     */               AuctionConfiguration.soundBuyError.play((Entity)paramPlayer);
/*     */             }
/*     */             if (AuctionConfiguration.useNoMoneyItem) {
/*     */               if (playerCache.isLastUpdate()) {
/*     */                 return;
/*     */               }
/*     */               playerCache.setLastUpdate(true);
/*     */               paramInventoryEngine.getSpigotInventory().setItem(paramInt, Message.NO_MONEY_BUTTON.getItemStack());
/*     */               paramZAuctionPlugin.getzMenuHandler().schedule(paramPlayer.getLocation(), AuctionConfiguration.noMoneyItemTicks, ());
/*     */               return;
/*     */             } 
/*     */             paramZAuctionPlugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_MONEY.getIMessage(), new Object[0]);
/*     */             return;
/*     */           } 
/*     */           paramAuctionItem.setAlreadyBuying(true);
/*     */           if (bool) {
/*     */             paramZAuctionPlugin.getzMenuHandler().openAdminRemove(paramPlayer, paramAuctionItem);
/*     */           } else if (paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId())) {
/*     */             paramZAuctionPlugin.getzMenuHandler().openRemoveConfirm(paramPlayer, paramAuctionItem);
/*     */           } else {
/*     */             paramZAuctionPlugin.getzMenuHandler().openBuyConfirm(paramPlayer, paramAuctionItem);
/*     */           } 
/*     */         } 
/*     */       };
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void renderButton(ZAuctionPlugin paramZAuctionPlugin, AuctionItem paramAuctionItem, InventoryEngine paramInventoryEngine, Player paramPlayer, int paramInt, InventoryType paramInventoryType) {
/* 116 */     AuctionType auctionType = paramAuctionItem.getType();
/*     */     
/* 118 */     Message message = auctionType.getInventoryMessage(paramInventoryType);
/* 119 */     if (paramAuctionItem.getType() == AuctionType.DEFAULT && paramAuctionItem.getItemStack() != null && paramAuctionItem.getItemStack().getType().name().endsWith("SHULKER_BOX")) {
/* 120 */       message = Message.ITEM_LORE_INVENTORY;
/*     */     }
/* 122 */     ItemStack itemStack = paramAuctionItem.createItem(paramPlayer, message);
/* 123 */     ItemButton itemButton = paramInventoryEngine.addItem(paramInt, itemStack);
/*     */ 
/*     */     
/* 126 */     if (paramInventoryType.isAuction()) {
/*     */       
/* 128 */       Consumer<InventoryClickEvent> consumer = auctionClick(paramZAuctionPlugin, paramAuctionItem, paramPlayer, paramInventoryEngine, itemStack, paramInt, paramInventoryType);
/* 129 */       itemButton.setClick(consumer);
/*     */     
/*     */     }
/*     */     else {
/*     */       
/* 134 */       StorageType storageType = paramInventoryType.equals(InventoryType.BUYING) ? StorageType.BUY : (paramInventoryType.equals(InventoryType.ITEMS) ? StorageType.STORAGE : StorageType.EXPIRE);
/* 135 */       itemButton.setClick(paramInventoryClickEvent -> {
/*     */             if (paramAuctionItem.isAlreadyBuying()) {
/*     */               if (AuctionConfiguration.displayErrorBuyMessage)
/*     */                 paramZAuctionPlugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR.getIMessage(), new Object[0]); 
/*     */               paramZAuctionPlugin.getzMenuHandler().update(paramPlayer);
/*     */             } else if (!paramAuctionItem.canBuy() && paramStorageType.equals(StorageType.STORAGE)) {
/*     */               if (AuctionConfiguration.displayErrorBuyMessage)
/*     */                 paramZAuctionPlugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR.getIMessage(), new Object[0]); 
/*     */               paramZAuctionPlugin.getzMenuHandler().update(paramPlayer);
/*     */             } else {
/*     */               paramZAuctionPlugin.getAuctionManager().removeItem(paramPlayer, paramAuctionItem, paramStorageType);
/*     */             } 
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\AuctionHelperButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */