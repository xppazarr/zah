/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import com.bencodez.votingplugin.VotingPluginHooks;
/*    */ import com.bencodez.votingplugin.user.UserManager;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class VotingProvider
/*    */   implements CurrencyProvider
/*    */ {
/* 12 */   private final UserManager userManager = VotingPluginHooks.getInstance().getUserManager();
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 16 */     this.userManager.getVotingPluginUser(paramOfflinePlayer.getUniqueId()).addPoints(paramBigDecimal.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 21 */     this.userManager.getVotingPluginUser(paramOfflinePlayer.getUniqueId()).removePoints(paramBigDecimal.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 26 */     return BigDecimal.valueOf(this.userManager.getVotingPluginUser(paramOfflinePlayer.getUniqueId()).getPoints());
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\VotingProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */