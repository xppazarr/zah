/*     */ package fr.maxlego08.zauctionhouse.actions;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPostBuyEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPreBuyEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxManager;
/*     */ import fr.maxlego08.zauctionhouse.api.tax.TaxType;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*     */ import fr.maxlego08.zauctionhouse.zcore.logger.LoggerManager;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.traqueur.zauctionhouse.redis.sync.ZSyncManager;
/*     */ import java.util.Collections;
/*     */ import java.util.Objects;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class AuctionPurchaseAction extends ZUtils {
/*     */   public void buy(ZAuctionPlugin paramZAuctionPlugin, AuctionManager paramAuctionManager, AuctionItem paramAuctionItem, Player paramPlayer) {
/*  32 */     if (!paramAuctionItem.canBuy() || paramAuctionItem.isAlreadyBuying() || paramAuctionItem.isExpired()) {
/*  33 */       paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */       
/*     */       return;
/*     */     } 
/*  37 */     IStorage iStorage = paramAuctionManager.getStorage();
/*  38 */     AuctionEconomy auctionEconomy = paramAuctionItem.getEconomy();
/*  39 */     long l1 = paramAuctionItem.getPrice();
/*     */     
/*  41 */     boolean bool1 = auctionEconomy.hasMoney((OfflinePlayer)paramPlayer, l1);
/*     */     
/*  43 */     Message message1 = Message.ITEM_PURCHASED;
/*  44 */     Message message2 = Message.BUYER_PURCHASED;
/*     */     
/*  46 */     AuctionPreBuyEvent auctionPreBuyEvent = new AuctionPreBuyEvent(auctionEconomy, paramPlayer, paramAuctionItem, bool1, l1, message2.getIMessage(), message1.getIMessage());
/*  47 */     auctionPreBuyEvent.call();
/*     */     
/*  49 */     if (auctionPreBuyEvent.isCancelled()) {
/*     */       return;
/*     */     }
/*     */     
/*  53 */     bool1 = auctionPreBuyEvent.isHasEnoughtMoney();
/*  54 */     l1 = auctionPreBuyEvent.getPrice();
/*     */     
/*  56 */     boolean bool2 = bool1;
/*  57 */     long l2 = l1;
/*  58 */     iStorage.checkIsItemIsValid(paramPlayer, paramAuctionItem, paramBoolean1 -> {
/*     */           if (!paramBoolean1.booleanValue())
/*     */             return; 
/*     */           long l1 = paramLong;
/*     */           if (!paramBoolean) {
/*     */             message((CommandSender)paramPlayer, Message.NO_ENOUGHT_MONEY, new Object[0]);
/*     */             return;
/*     */           } 
/*     */           if (AuctionConfiguration.giveItemAfterPurchase && AuctionConfiguration.dontGiveIfPlayerHasFullInventory && !paramAuctionItem.hasFreeSpace(paramPlayer)) {
/*     */             message((CommandSender)paramPlayer, Message.NO_ENOUGHT_PLACE, new Object[0]);
/*     */             return;
/*     */           } 
/*     */           TaxManager taxManager = paramZAuctionPlugin.getTaxManager();
/*     */           double d = taxManager.getTax(paramPlayer, l1, paramAuctionItem.getItemStack(), TaxType.PURCHASE);
/*     */           OfflinePlayer offlinePlayer = paramAuctionItem.getSeller();
/*     */           String str1 = paramAuctionItem.getSellerName();
/*     */           ItemStack itemStack = paramAuctionItem.getItemStack();
/*     */           int i = itemStack.getAmount();
/*     */           String str2 = paramZAuctionPlugin.translateItemStack(itemStack);
/*     */           paramAuctionEconomy.withdrawMoney((OfflinePlayer)paramPlayer, l1, getMessage(paramAuctionEconomy.getWithdrawReason(), new Object[] { 
/*     */                   "%amount%", Integer.valueOf(i), "%price%", paramAuctionItem.priceFormat(), "%item%", str2, "%seller%", str1, "%buyer%", paramPlayer.getName(), 
/*     */                   "%tax%", Double.valueOf(d) }));
/*     */           if (AuctionConfiguration.taxType == TaxType.CAPITALISM)
/*     */             l1 = (long)taxManager.reverseTax(l1, paramAuctionItem.getItemStack()); 
/*     */           long l2 = l1 - (long)d;
/*     */           if (!AuctionConfiguration.enableClaimMoney)
/*     */             if (!AuctionConfiguration.giveMoneyOnSellServer || paramAuctionItem.getServer().equals(paramAuctionManager.getStorage().getServerName()))
/*     */               paramAuctionEconomy.depositMoney(offlinePlayer, l2, getMessage(paramAuctionEconomy.getDepositReason(), new Object[] { 
/*     */                       "%amount%", Integer.valueOf(i), "%price%", paramAuctionItem.priceFormat(), "%item%", str2, "%seller%", str1, "%buyer%", paramPlayer.getName(), 
/*     */                       "%tax%", Double.valueOf(d) }));  
/*     */           message((CommandSender)paramPlayer, paramAuctionPreBuyEvent.getSellerMessage(), new Object[] { 
/*     */                 "%amount%", Integer.valueOf(i), "%price%", paramAuctionItem.priceFormat(), "%item%", str2, "%seller%", str1, "%buyer%", paramPlayer.getName(), 
/*     */                 "%tax%", Double.valueOf(d) });
/*     */           if (offlinePlayer.isOnline())
/*     */             message((CommandSender)offlinePlayer.getPlayer(), paramAuctionPreBuyEvent.getBuyerMessage(), new Object[] { 
/*     */                   "%amount%", Integer.valueOf(i), "%price%", paramAuctionItem.priceFormat(), "%item%", paramZAuctionPlugin.translateItemStack(itemStack), "%seller%", str1, "%buyer%", paramPlayer.getName(), 
/*     */                   "%tax%", Double.valueOf(d), "%realPrice%", Long.valueOf(l1) }); 
/*     */           Objects.requireNonNull(paramPlayer);
/*     */           runNextTick(paramPlayer::closeInventory);
/*     */           paramAuctionItem.setBuyer(paramPlayer.getUniqueId());
/*     */           paramAuctionItem.setExpireAt((AuctionConfiguration.expireTime == -1L) ? -1L : (System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime));
/*     */           paramAuctionItem.setAlreadyBuying(false);
/*     */           paramAuctionItem.setCanBuy(false);
/*     */           paramAuctionItem.setPrice(l2);
/*     */           if (AuctionConfiguration.enableCacheItems && paramAuctionManager.getCacheAuctionItemList() != null)
/*     */             paramAuctionManager.getCacheAuctionItemList().invalid(); 
/*     */           boolean bool = false;
/*     */           if (AuctionConfiguration.giveItemAfterPurchase && paramAuctionItem.hasFreeSpace(paramPlayer)) {
/*     */             paramAuctionManager.giveAuctionItem(paramAuctionItem, paramPlayer);
/*     */           } else {
/*     */             paramIStorage.updateItem((AuctionPlugin)paramZAuctionPlugin, paramAuctionItem, StorageType.BUY);
/*     */             bool = true;
/*     */           } 
/*     */           if (paramZAuctionPlugin.getServer().getPluginManager().getPlugin("zAuctionHouseRedis") != null)
/*     */             ((ZSyncManager)paramZAuctionPlugin.getProvider(ZSyncManager.class)).publishPreBuyAuction(paramAuctionItem, bool, d, paramAuctionPreBuyEvent); 
/*     */           if (AuctionConfiguration.enableBuyAnnonce && (AuctionConfiguration.permissionBuyAnnonce == null || paramPlayer.hasPermission(AuctionConfiguration.permissionBuyAnnonce)))
/*     */             broadcast(Message.BROADCAST_BUY_ANNONCE, new Object[] { 
/*     */                   "%player%", paramPlayer.getName(), "%amount%", Integer.valueOf(i), "%item%", paramZAuctionPlugin.translateItemStack(itemStack), "%price%", paramAuctionItem.priceFormat(), "%tax%", Double.valueOf(d), 
/*     */                   "%seller%", str1 }); 
/*     */           if (AuctionConfiguration.enableBuySuccessSound && AuctionConfiguration.soundBuySuccess != null)
/*     */             AuctionConfiguration.soundBuySuccess.play((Entity)paramPlayer); 
/*     */           String str3 = "%s buy x%s %s to %s for %s.";
/*     */           LoggerManager.getInstance().log(str3, new Object[] { paramPlayer.getName(), Integer.valueOf(paramAuctionItem.getItemStack().getAmount()), paramZAuctionPlugin.translateItemStack(itemStack), str1, paramAuctionItem.priceFormat(), "%tax%", Double.valueOf(d) });
/*     */           TransactionManager transactionManager = paramZAuctionPlugin.getTransactionManager();
/*     */           transactionManager.storeTransaction(paramAuctionItem, (), l2);
/*     */           if (AuctionConfiguration.openInventoryAfterBuying)
/*     */             runNextTick(()); 
/*     */           paramAuctionManager.clearCache();
/*     */           paramZAuctionPlugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*     */           paramZAuctionPlugin.getPlaceholderAPI().clearCache(offlinePlayer.getUniqueId());
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\actions\AuctionPurchaseAction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */