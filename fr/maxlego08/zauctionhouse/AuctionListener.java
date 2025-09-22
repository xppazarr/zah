/*    */ package fr.maxlego08.zauctionhouse;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.listener.ListenerAdapter;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ 
/*    */ public class AuctionListener extends ListenerAdapter {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final TransactionManager transactionManager;
/*    */   
/*    */   public AuctionListener(ZAuctionPlugin paramZAuctionPlugin, TransactionManager paramTransactionManager) {
/* 19 */     this.plugin = paramZAuctionPlugin;
/* 20 */     this.transactionManager = paramTransactionManager;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected void onConnect(PlayerJoinEvent paramPlayerJoinEvent, Player paramPlayer) {
/* 26 */     if (AuctionConfiguration.enableClaimMoneyMessageOnJoin) {
/* 27 */       schedule(paramPlayer.getLocation(), AuctionConfiguration.cooldownClaimMoneyMessageTicks, () -> {
/*    */             if (this.transactionManager.needMoney((OfflinePlayer)paramPlayer)) {
/*    */               message((CommandSender)paramPlayer, Message.CONNECT_CLAIM, new Object[0]);
/*    */             }
/*    */           });
/*    */     }
/*    */     
/* 34 */     if (AuctionConfiguration.enableTransactionMessageOnJoin) {
/* 35 */       schedule(paramPlayer.getLocation(), AuctionConfiguration.cooldownInformationsMessageTicks, () -> {
/*    */             if (AuctionConfiguration.storage == Storage.JSON) {
/*    */               long l = this.transactionManager.countUnRead((OfflinePlayer)paramPlayer);
/*    */               
/*    */               if (l > 0L) {
/*    */                 message((CommandSender)paramPlayer, Message.CONNECT_TRANSACTIONS, new Object[] { "%item%", Long.valueOf(l) });
/*    */                 this.transactionManager.updateUnRead((OfflinePlayer)paramPlayer);
/*    */               } 
/*    */             } else {
/*    */               this.transactionManager.sendUnreadWithSelect(paramPlayer);
/*    */             } 
/*    */           });
/*    */     }
/* 48 */     schedule(paramPlayer.getLocation(), 10L, () -> {
/*    */           if (paramPlayerJoinEvent.getPlayer().getName().startsWith("Maxlego08") || paramPlayerJoinEvent.getPlayer().getName().startsWith("SMV")) {
/*    */             paramPlayerJoinEvent.getPlayer().sendMessage(Message.PREFIX_END.getMessage() + " §aLe serveur utilise §2" + this.plugin.getDescription().getFullName() + " §a!");
/*    */             
/*    */             String str = "276655";
/*    */             paramPlayerJoinEvent.getPlayer().sendMessage(Message.PREFIX_END.getMessage() + " §aUtilisateur spigot §2" + str + " §a!");
/*    */           } 
/*    */         });
/* 56 */     this.plugin.getStorage().loadPlayerCache(paramPlayer.getUniqueId());
/*    */   }
/*    */ 
/*    */   
/*    */   protected void onQuit(PlayerQuitEvent paramPlayerQuitEvent, Player paramPlayer) {
/* 61 */     this.plugin.getPlaceholderAPI().onQuit(paramPlayer);
/* 62 */     this.plugin.getStorage().removePlayerCache(paramPlayer.getUniqueId());
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\AuctionListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */