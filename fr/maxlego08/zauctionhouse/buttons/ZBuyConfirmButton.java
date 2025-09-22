/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.Inventory;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.BuyConfirmButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import java.util.List;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZBuyConfirmButton extends BuyConfirmButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ZBuyConfirmButton(Plugin paramPlugin) {
/* 26 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 32 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 33 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/*    */     
/* 35 */     Placeholders placeholders = new Placeholders();
/* 36 */     placeholders.register("price", auctionItem.priceFormat());
/* 37 */     placeholders.register("seller", auctionItem.getSellerName());
/* 38 */     placeholders.register("time", auctionItem.timeFormat());
/*    */     
/* 40 */     long l = auctionItem.getEconomy().getMoney((OfflinePlayer)paramPlayer) - auctionItem.getPrice();
/* 41 */     String str = auctionItem.getEconomy().format(((ZAuctionItem)auctionItem).toPriceFormat(l), l);
/* 42 */     placeholders.register("money-after-purchase", str);
/*    */     
/* 44 */     return getItemStack().build(paramPlayer, false, placeholders);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 49 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 51 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 52 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/*    */     
/* 54 */     if (!auctionItem.canBuy() || auctionItem.getStorageType() != StorageType.STORAGE) {
/* 55 */       if (AuctionConfiguration.displayErrorBuyMessage)
/* 56 */         this.plugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR.getIMessage(), new Object[0]); 
/* 57 */       this.plugin.getzMenuHandler().openAuction(paramPlayer);
/*    */       
/*    */       return;
/*    */     } 
/* 61 */     auctionItem.setAlreadyBuying(false);
/* 62 */     this.plugin.getAuctionManager().buy(auctionItem, paramPlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onBackClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, List<Inventory> paramList, Inventory paramInventory, int paramInt) {
/* 68 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 73 */     this.plugin.getzMenuHandler().clearAlreadyBuying(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZBuyConfirmButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */