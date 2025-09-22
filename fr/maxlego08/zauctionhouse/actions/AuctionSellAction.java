/*     */ package fr.maxlego08.zauctionhouse.actions;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionSellEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.price.PriceManager;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxManager;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.builder.CooldownBuilder;
/*     */ import fr.traqueur.zauctionhouse.redis.sync.ZSyncManager;
/*     */ import org.bukkit.GameMode;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ public class AuctionSellAction extends ZUtils {
/*     */   public void sellItem(ZAuctionPlugin paramZAuctionPlugin, AuctionManager paramAuctionManager, AuctionItem paramAuctionItem, ItemStack paramItemStack, Player paramPlayer, long paramLong, AuctionEconomy paramAuctionEconomy, int paramInt, AuctionType paramAuctionType) {
/*  32 */     if (paramAuctionEconomy == null) {
/*  33 */       message((CommandSender)paramPlayer, "§cThe economy is null, please contact an administrator.", new Object[0]);
/*  34 */       Logger.info("The economy is null, please contact an administrator.", Logger.LogType.ERROR);
/*  35 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */       
/*     */       return;
/*     */     } 
/*  39 */     long l1 = paramAuctionManager.getMaxPricePerEconomyPermission(paramAuctionEconomy, (Permissible)paramPlayer);
/*  40 */     if (l1 > 0L && paramLong > l1) {
/*  41 */       message((CommandSender)paramPlayer, Message.SELL_MAX_PRICE, new Object[] { "%max%", toPrice(l1) });
/*  42 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  47 */     if (isCooldown(paramPlayer, "sell_command")) {
/*  48 */       message((CommandSender)paramPlayer, Message.SELL_COOLDOWN, new Object[] { "%time%", timerFormat(paramPlayer, "sell_command") });
/*  49 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */       
/*     */       return;
/*     */     } 
/*     */     
/*  54 */     TaxManager taxManager = paramZAuctionPlugin.getTaxManager();
/*  55 */     double d = taxManager.getTax(paramPlayer, paramLong, paramItemStack, TaxType.SELL);
/*  56 */     if (d > 0.0D)
/*     */     {
/*  58 */       if (!paramAuctionEconomy.hasMoney((OfflinePlayer)paramPlayer, (long)d)) {
/*  59 */         message((CommandSender)paramPlayer, Message.SELL_ITEM_TAX_DEFAULT, new Object[] { "%tax%", paramAuctionEconomy.format(String.valueOf(d), (long)d) });
/*  60 */         paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/*     */     
/*  66 */     long l2 = paramAuctionManager.count(paramPlayer, StorageType.STORAGE);
/*     */     
/*  68 */     long l3 = paramAuctionManager.getMaxSellPerPermission((Permissible)paramPlayer);
/*  69 */     Material material = (paramItemStack == null) ? null : paramItemStack.getType();
/*     */ 
/*     */     
/*  72 */     PriceManager priceManager = paramZAuctionPlugin.getPriceManager();
/*  73 */     long l4 = priceManager.getMinPrice(paramAuctionItem, paramAuctionEconomy);
/*  74 */     l1 = priceManager.getMaxPrice(paramAuctionItem, paramAuctionEconomy);
/*     */ 
/*     */     
/*  77 */     if (l2 >= l3) {
/*  78 */       message((CommandSender)paramPlayer, Message.SELL_ITEMS_ERROR, new Object[] { "%max%", Long.valueOf(l3) });
/*  79 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*  80 */     } else if (paramLong > l1) {
/*     */       
/*  82 */       message((CommandSender)paramPlayer, Message.SELL_MAX_PRICE, new Object[] { "%max%", toPrice(l1) });
/*  83 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*  84 */     } else if (paramLong < l4) {
/*     */       
/*  86 */       message((CommandSender)paramPlayer, Message.SELL_MIN_PRICE, new Object[] { "%min%", toPrice(l4) });
/*  87 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*  88 */     } else if (!AuctionConfiguration.creativeSell && paramPlayer.getGameMode().equals(GameMode.CREATIVE)) {
/*     */       
/*  90 */       message((CommandSender)paramPlayer, Message.SELL_CREATIVE_ERROR, new Object[0]);
/*  91 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*  92 */     } else if (paramAuctionManager.getBannedWorlds().contains(paramPlayer.getWorld().getName())) {
/*     */       
/*  94 */       message((CommandSender)paramPlayer, Message.WORLD_IS_BANNED, new Object[0]);
/*  95 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*  96 */     } else if (paramAuctionManager.isBlacklist((OfflinePlayer)paramPlayer)) {
/*     */       
/*  98 */       message((CommandSender)paramPlayer, Message.BLACKLIST_INFORMATION, new Object[0]);
/*  99 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/* 100 */     } else if (paramAuctionManager.auctionHasBlacklistItems(paramAuctionItem)) {
/*     */       
/* 102 */       message((CommandSender)paramPlayer, Message.SELL_ITEM_ERROR, new Object[0]);
/* 103 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/* 104 */     } else if (AuctionConfiguration.enableWhitelist && !paramAuctionManager.auctionHasWhitelistItems(paramAuctionItem)) {
/*     */       
/* 106 */       message((CommandSender)paramPlayer, Message.SELL_ITEM_WHITELIST, new Object[0]);
/* 107 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/* 108 */     } else if (paramItemStack != null && AuctionConfiguration.disableSellBreakItem && !material.isBlock() && paramItemStack.getDurability() > 0) {
/*     */       
/* 110 */       message((CommandSender)paramPlayer, Message.SELL_ITEM_BREAK, new Object[0]);
/* 111 */       paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */     } else {
/*     */       
/* 114 */       if (AuctionConfiguration.taxType.equals(TaxType.CAPITALISM)) {
/* 115 */         paramLong = (long)(paramLong + taxManager.getTax(paramPlayer, paramLong, paramItemStack, TaxType.CAPITALISM));
/* 116 */         paramAuctionItem.setPrice(paramLong);
/*     */       } 
/*     */ 
/*     */       
/* 120 */       Message message = paramAuctionType.getSellMessage();
/* 121 */       AuctionSellEvent auctionSellEvent = new AuctionSellEvent(paramAuctionItem, paramLong, paramPlayer, paramAuctionEconomy, message.getIMessage());
/* 122 */       auctionSellEvent.call();
/*     */ 
/*     */       
/* 125 */       if (auctionSellEvent.isCancelled()) {
/* 126 */         paramAuctionItem.giveIf(paramPlayer, paramAuctionItem -> paramAuctionItem.getType().equals(AuctionType.INVENTORY));
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 131 */       if (paramAuctionType.removeItemInHand()) removeItemInHand(paramPlayer, paramInt);
/*     */       
/* 133 */       String str1 = paramZAuctionPlugin.translateItemStack(paramItemStack);
/*     */       
/* 135 */       if (d > 0.0D) paramAuctionEconomy.withdrawMoney((OfflinePlayer)paramPlayer, (long)d, getMessage(paramAuctionEconomy.getWithdrawTaxReason(), new Object[] { "%amount%", Integer.valueOf(paramInt), "%item%", str1, "%price%", paramAuctionItem.priceFormat(), "%tax%", Double.valueOf(d) }));
/*     */ 
/*     */       
/* 138 */       message((CommandSender)paramPlayer, auctionSellEvent.getMessage(), new Object[] { "%amount%", Integer.valueOf(paramInt), "%item%", str1, "%price%", paramAuctionItem.priceFormat(), "%tax%", Double.valueOf(d) });
/*     */ 
/*     */       
/* 141 */       IStorage iStorage = paramAuctionManager.getStorage();
/* 142 */       iStorage.saveItem((AuctionPlugin)paramZAuctionPlugin, paramAuctionItem, StorageType.STORAGE, paramAuctionItem -> runNextTick(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 150 */       if (AuctionConfiguration.enableSellAnnonce && (AuctionConfiguration.permissionSellAnnonce == null || paramPlayer.hasPermission(AuctionConfiguration.permissionSellAnnonce))) {
/* 151 */         broadcast(Message.BROADCAST_SELL_ANNONCE, new Object[] { "%player%", paramPlayer.getName(), "%amount%", Integer.valueOf(paramInt), "%item%", paramZAuctionPlugin.translateItemStack(paramItemStack), "%price%", paramAuctionItem.priceFormat(), "%tax%", Double.valueOf(d) });
/*     */       }
/*     */ 
/*     */       
/* 155 */       if (AuctionConfiguration.useSellCooldown) {
/* 156 */         CooldownBuilder.addCooldown("sell_command", paramPlayer, AuctionConfiguration.cooldownCommandSell);
/*     */       }
/*     */ 
/*     */       
/* 160 */       String str2 = "%s added x%s %s in auction for %s.";
/* 161 */       LoggerManager.getInstance().log(str2, new Object[] { paramPlayer.getName(), Integer.valueOf(paramInt), paramAuctionType.equals(AuctionType.INVENTORY) ? "items" : paramZAuctionPlugin.translateItemStack(paramItemStack), Long.valueOf(paramLong), paramAuctionItem.priceFormat() });
/*     */ 
/*     */       
/* 164 */       if (AuctionConfiguration.discordWebhook != null) {
/* 165 */         AuctionConfiguration.discordWebhook.send((AuctionPlugin)paramZAuctionPlugin, paramAuctionItem);
/*     */       }
/*     */ 
/*     */       
/* 169 */       paramAuctionManager.clearCache();
/* 170 */       paramZAuctionPlugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\actions\AuctionSellAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */