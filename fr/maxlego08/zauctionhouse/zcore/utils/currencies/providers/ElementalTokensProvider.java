/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import me.elementalgaming.ElementalTokens.TokenAPI;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class ElementalTokensProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 12 */     TokenAPI.addTokens(paramOfflinePlayer.getUniqueId(), paramBigDecimal.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 17 */     TokenAPI.removeTokens(paramOfflinePlayer.getUniqueId(), paramBigDecimal.longValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 22 */     return BigDecimal.valueOf(TokenAPI.getTokens(paramOfflinePlayer.getUniqueId()));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ElementalTokensProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */