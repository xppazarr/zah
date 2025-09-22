/*     */ package fr.maxlego08.zauctionhouse.buttons;
/*     */ 
/*     */ import fr.maxlego08.menu.api.MenuItemStack;
/*     */ import fr.maxlego08.menu.api.button.Button;
/*     */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*     */ import fr.maxlego08.menu.api.utils.Placeholders;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ public class ZEconomyButton
/*     */   extends Button {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private final String economyName;
/*     */   
/*     */   public ZEconomyButton(ZAuctionPlugin paramZAuctionPlugin, String paramString) {
/*  34 */     this.plugin = paramZAuctionPlugin;
/*  35 */     this.economyName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/*  41 */     paramPlayer.closeInventory();
/*     */     
/*  43 */     Optional<AuctionEconomy> optional = this.plugin.getEconomyManager().getEconomy(this.economyName);
/*  44 */     if (optional.isPresent()) {
/*     */ 
/*     */       
/*  47 */       AuctionEconomy auctionEconomy = optional.get();
/*     */       
/*  49 */       ZAuctionManager zAuctionManager = (ZAuctionManager)this.plugin.getAuctionManager();
/*  50 */       PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/*  51 */       long l1 = playerCache.getSellPrice();
/*  52 */       int i = playerCache.getSellAmount();
/*     */       
/*  54 */       ItemStack itemStack = paramPlayer.getItemInHand().clone();
/*     */       
/*  56 */       if (itemStack == null || itemStack.getAmount() == 0) {
/*  57 */         zAuctionManager.message((CommandSender)paramPlayer, Message.NO_HOLDING_ITEM, new Object[0]);
/*     */         
/*     */         return;
/*     */       } 
/*  61 */       Optional optional1 = zAuctionManager.getPriority((Permissible)paramPlayer);
/*  62 */       int j = ((Integer)optional1.map(Priority::getPriority).orElse(Integer.valueOf(0))).intValue();
/*     */       
/*  64 */       long l2 = (AuctionConfiguration.sellTime != -1L) ? (System.currentTimeMillis() + zAuctionManager.getExpirationPerPermission((Permissible)paramPlayer) * 1000L) : AuctionConfiguration.sellTime;
/*     */       
/*  66 */       i = Math.max(i, 1);
/*  67 */       i = Math.min(i, itemStack.getAmount());
/*  68 */       i = Math.min(i, 64);
/*     */       
/*  70 */       itemStack.setAmount(i);
/*     */       
/*  72 */       UUID uUID = zAuctionManager.getRandomUniqueId();
/*  73 */       ZAuctionItem zAuctionItem = new ZAuctionItem(uUID, itemStack, AuctionType.DEFAULT, l1, auctionEconomy.getName(), paramPlayer.getUniqueId(), l2, StorageType.STORAGE, paramPlayer.getName(), j, this.plugin.getStorage().getServerName());
/*     */       
/*  75 */       this.plugin.getAuctionManager().sellItem((AuctionItem)zAuctionItem, itemStack, paramPlayer, l1, auctionEconomy, i, AuctionType.DEFAULT);
/*     */     } else {
/*     */       
/*  78 */       Logger.info("Impossible to find the economy " + this.economyName, Logger.LogType.ERROR);
/*     */     } 
/*     */     
/*  81 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCustomItemStack(Player paramPlayer) {
/*  86 */     MenuItemStack menuItemStack = getItemStack();
/*  87 */     Optional<AuctionEconomy> optional = this.plugin.getEconomyManager().getEconomy(this.economyName);
/*  88 */     Placeholders placeholders = new Placeholders();
/*  89 */     if (optional.isPresent()) {
/*     */       
/*  91 */       PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/*  92 */       long l = playerCache.getSellPrice();
/*  93 */       int i = playerCache.getSellAmount();
/*     */       
/*  95 */       AuctionEconomy auctionEconomy = optional.get();
/*  96 */       placeholders.register("price", auctionEconomy.format(this.plugin.getAuctionManager().getPriceFormat(l), i));
/*     */     } else {
/*  98 */       placeholders.register("price", "Economy " + this.economyName + " not found !");
/*     */     } 
/* 100 */     return menuItemStack.build(paramPlayer, false, placeholders);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZEconomyButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */