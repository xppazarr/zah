/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.SellBuyButton;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ import java.util.Optional;
/*    */ import java.util.UUID;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.Inventory;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.permissions.Permissible;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZSellBuyButton extends SellBuyButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ZSellBuyButton(Plugin paramPlugin) {
/* 37 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 43 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*    */     
/* 45 */     ArrayList<ItemStack> arrayList = new ArrayList();
/*    */     
/* 47 */     Inventory inventory = paramInventoryEngine.getSpigotInventory();
/* 48 */     AuctionManager auctionManager = this.plugin.getAuctionManager();
/* 49 */     Optional<Button> optional = paramInventoryEngine.getButtons().stream().filter(paramButton -> paramButton instanceof fr.maxlego08.zauctionhouse.api.buttons.SellSlotButton).findFirst();
/*    */     
/* 51 */     if (!optional.isPresent()) {
/* 52 */       paramPlayer.sendMessage("§cError with your inventory, impossible to find SellSlotButton");
/*    */       
/*    */       return;
/*    */     } 
/* 56 */     Button button = optional.get();
/* 57 */     IBlacklistManager iBlacklistManager = this.plugin.getBlacklistManager();
/*    */     
/* 59 */     for (Iterator<Integer> iterator = button.getSlots().iterator(); iterator.hasNext(); ) { int j = ((Integer)iterator.next()).intValue();
/*    */       
/* 61 */       ItemStack itemStack = inventory.getItem(j);
/* 62 */       if (itemStack != null) {
/*    */         
/* 64 */         Material material = itemStack.getType();
/* 65 */         if (AuctionConfiguration.disableSellBreakItem && !material.isBlock() && itemStack.getDurability() > 0)
/*    */           continue; 
/* 67 */         if (iBlacklistManager.isBlacklist(itemStack).isPresent()) {
/*    */           continue;
/*    */         }
/*    */         
/* 71 */         arrayList.add(itemStack.clone());
/*    */       } 
/* 73 */       inventory.setItem(j, new ItemStack(Material.AIR)); }
/*    */ 
/*    */     
/* 76 */     if (arrayList.isEmpty()) {
/* 77 */       this.plugin.getzMenuHandler().message((CommandSender)paramPlayer, Message.SELL_INVENTORY_ERROR.getIMessage(), new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 81 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 82 */     long l1 = playerCache.getSellPrice();
/* 83 */     AuctionEconomy auctionEconomy = playerCache.getEconomy();
/*    */     
/* 85 */     Optional optional1 = auctionManager.getPriority((Permissible)paramPlayer);
/* 86 */     int i = ((Integer)optional1.map(Priority::getPriority).orElse(Integer.valueOf(0))).intValue();
/*    */     
/* 88 */     UUID uUID = UUID.randomUUID();
/* 89 */     long l2 = (AuctionConfiguration.sellTime != -1L) ? (System.currentTimeMillis() + auctionManager.getExpirationPerPermission((Permissible)paramPlayer) * 1000L) : AuctionConfiguration.sellTime;
/*    */     
/* 91 */     ZAuctionItem zAuctionItem = new ZAuctionItem(uUID, arrayList, AuctionType.INVENTORY, l1, auctionEconomy.getName(), paramPlayer.getUniqueId(), l2, StorageType.STORAGE, null, i, this.plugin.getStorage().getServerName());
/*    */     
/* 93 */     auctionManager.sellItem((AuctionItem)zAuctionItem, null, paramPlayer, l1, auctionEconomy, arrayList.size(), AuctionType.INVENTORY);
/*    */     
/* 95 */     paramPlayer.closeInventory();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZSellBuyButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */