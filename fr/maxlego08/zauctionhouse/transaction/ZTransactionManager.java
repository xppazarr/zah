/*     */ package fr.maxlego08.zauctionhouse.transaction;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.HistoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionClaimEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionTransactionEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.StorageManager;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZTransactionManager
/*     */   extends ZUtils
/*     */   implements TransactionManager
/*     */ {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private IStorage iStorage;
/*     */   
/*     */   public ZTransactionManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  37 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */   
/*     */   public IStorage getStorage() {
/*  41 */     return (this.iStorage == null) ? (this.iStorage = this.plugin.getStorage()) : this.iStorage;
/*     */   }
/*     */ 
/*     */   
/*     */   public void storeTransaction(AuctionItem paramAuctionItem, Consumer<Transaction> paramConsumer, long paramLong) {
/*  46 */     runNextTick(() -> {
/*     */           IStorage iStorage = getStorage();
/*     */           Transaction transaction = paramAuctionItem.buildTransaction(paramLong);
/*     */           AuctionTransactionEvent auctionTransactionEvent = new AuctionTransactionEvent(transaction);
/*     */           auctionTransactionEvent.call();
/*     */           if (auctionTransactionEvent.isCancelled()) {
/*     */             return;
/*     */           }
/*     */           iStorage.storeTransaction(transaction, paramConsumer);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Transaction> getTransactions(OfflinePlayer paramOfflinePlayer, HistoryType paramHistoryType) {
/*  61 */     IStorage iStorage = getStorage();
/*  62 */     List list = iStorage.getTransactions();
/*  63 */     UUID uUID = paramOfflinePlayer.getUniqueId();
/*  64 */     return (List<Transaction>)list.stream().filter(paramTransaction -> {
/*     */           switch (paramHistoryType) {
/*     */             case BOTH:
/*  67 */               return (paramTransaction.getSeller().equals(paramUUID) || paramTransaction.getBuyer().equals(paramUUID));
/*     */             
/*     */             case PURCHASE:
/*     */               return paramTransaction.getBuyer().equals(paramUUID);
/*     */             case SALE:
/*     */               return paramTransaction.getSeller().equals(paramUUID);
/*     */           } 
/*     */           return false;
/*  75 */         }).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needMoney(OfflinePlayer paramOfflinePlayer) {
/*  80 */     return getTransactions(paramOfflinePlayer, HistoryType.SALE).stream().anyMatch(Transaction::needMoney);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Transaction> getMoneyTransactions(OfflinePlayer paramOfflinePlayer) {
/*  85 */     return (List<Transaction>)getTransactions(paramOfflinePlayer, HistoryType.SALE).stream().filter(Transaction::needMoney).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<Transaction> getMoneyTransactions(OfflinePlayer paramOfflinePlayer, String paramString) {
/*  90 */     return (List<Transaction>)getTransactions(paramOfflinePlayer, HistoryType.SALE).stream().filter(paramTransaction -> (paramTransaction.needMoney() && paramTransaction.getEconomy().equalsIgnoreCase(paramString))).collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public long countUnRead(OfflinePlayer paramOfflinePlayer) {
/*  95 */     return getTransactions(paramOfflinePlayer, HistoryType.SALE).stream().filter(paramTransaction -> !paramTransaction.isRead()).count();
/*     */   }
/*     */ 
/*     */   
/*     */   public void updateUnRead(OfflinePlayer paramOfflinePlayer) {
/* 100 */     IStorage iStorage = getStorage();
/* 101 */     List list = (List)getTransactions(paramOfflinePlayer, HistoryType.SALE).stream().filter(paramTransaction -> !paramTransaction.isRead()).collect(Collectors.toList());
/* 102 */     list.forEach(Transaction::read);
/* 103 */     iStorage.updateTransaction(list);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void claimMoney(Player paramPlayer) {
/* 109 */     if (AuctionConfiguration.storage.equals(Storage.JSON)) {
/* 110 */       claimMoney(paramPlayer, getMoneyTransactions((OfflinePlayer)paramPlayer));
/*     */     } else {
/*     */       
/* 113 */       IStorage iStorage = getStorage();
/*     */       
/* 115 */       if (iStorage.isCooldown(paramPlayer.getUniqueId())) {
/* 116 */         message((CommandSender)paramPlayer, Message.CLAIM_MONEY_COOLDOWN, new Object[0]);
/*     */ 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 122 */       message((CommandSender)paramPlayer, Message.CLAIM_MONEY_WAIT, new Object[0]);
/* 123 */       iStorage.fetchClaimMoney(paramPlayer, paramList -> claimMoney(paramPlayer, paramList));
/*     */     } 
/*     */   }
/*     */   
/*     */   private void claimMoney(Player paramPlayer, List<Transaction> paramList) {
/* 128 */     if (paramList.isEmpty()) {
/*     */       
/* 130 */       message((CommandSender)paramPlayer, Message.CLAIM_MONEY_ERROR, new Object[0]);
/*     */     }
/*     */     else {
/*     */       
/* 134 */       runNextTick(() -> {
/*     */             paramList.forEach(());
/*     */             this.iStorage.updateTransaction(paramList);
/*     */             this.plugin.getPlaceholderAPI().clearCache(paramPlayer.getUniqueId());
/*     */             message((CommandSender)paramPlayer, Message.CLAIM_MONEY_SUCCESS, new Object[0]);
/*     */           });
/*     */     } 
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
/*     */   public long getMoney(Player paramPlayer, String paramString) {
/* 204 */     List<Transaction> list = getMoneyTransactions((OfflinePlayer)paramPlayer, paramString);
/* 205 */     return list.stream().mapToLong(Transaction::getPrice).sum();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getMoneyFromDatabase(Player paramPlayer, String paramString) {
/* 210 */     IStorage iStorage = this.plugin.getStorage();
/* 211 */     return iStorage.fetchClaimMoneySync(paramPlayer.getUniqueId(), paramString);
/*     */   }
/*     */ 
/*     */   
/*     */   public void purge(CommandSender paramCommandSender, long paramLong) {
/* 216 */     StorageManager storageManager = this.plugin.getStorageManager();
/* 217 */     storageManager.setReady(false);
/*     */     
/* 219 */     paramLong = Math.max(paramLong, 1L);
/* 220 */     message(paramCommandSender, Message.PURGE_START, new Object[0]);
/*     */     
/* 222 */     IStorage iStorage = storageManager.getIStorage();
/* 223 */     iStorage.purgeTransactions(paramLong * 86400L, () -> {
/*     */           paramStorageManager.setReady(true);
/*     */           message(paramCommandSender, Message.PURGE_END, new Object[0]);
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void sendUnreadWithSelect(Player paramPlayer) {
/* 233 */     IStorage iStorage = getStorage();
/*     */     
/* 235 */     if (iStorage.isCooldown(paramPlayer.getUniqueId())) {
/*     */       return;
/*     */     }
/*     */     
/* 239 */     iStorage.fetchUnreadMoney(paramPlayer, paramList -> {
/*     */           if (paramList.isEmpty())
/*     */             return; 
/*     */           long l = paramList.stream().filter(()).count();
/*     */           if (l == 0L)
/*     */             return; 
/*     */           message((CommandSender)paramPlayer, Message.CONNECT_TRANSACTIONS, new Object[] { "%item%", Long.valueOf(l) });
/*     */           List list = (List)paramList.stream().filter(()).collect(Collectors.toList());
/*     */           list.forEach(Transaction::read);
/*     */           paramIStorage.updateTransaction(list);
/*     */         });
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\transaction\ZTransactionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */