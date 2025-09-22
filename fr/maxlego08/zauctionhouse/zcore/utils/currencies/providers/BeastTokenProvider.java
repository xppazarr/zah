/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import me.mraxetv.beasttokens.api.BeastTokensAPI;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class BeastTokenProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 12 */     if (paramOfflinePlayer.isOnline()) {
/* 13 */       BeastTokensAPI.getTokensManager().addTokens(paramOfflinePlayer.getPlayer(), paramBigDecimal.doubleValue());
/*    */     } else {
/* 15 */       BeastTokensAPI.getTokensManager().addTokens(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 21 */     if (paramOfflinePlayer.isOnline()) {
/* 22 */       BeastTokensAPI.getTokensManager().removeTokens(paramOfflinePlayer.getPlayer(), paramBigDecimal.doubleValue());
/*    */     } else {
/* 24 */       BeastTokensAPI.getTokensManager().removeTokens(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 30 */     return BigDecimal.valueOf(paramOfflinePlayer.isOnline() ? BeastTokensAPI.getTokensManager().getTokens(paramOfflinePlayer.getPlayer()) : BeastTokensAPI.getTokensManager().getTokens(paramOfflinePlayer));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\BeastTokenProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */