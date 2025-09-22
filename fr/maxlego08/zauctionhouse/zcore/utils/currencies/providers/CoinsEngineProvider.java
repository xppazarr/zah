/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import su.nightexpress.coinsengine.api.CoinsEngineAPI;
/*    */ 
/*    */ public class CoinsEngineProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private final String currencyName;
/*    */   
/*    */   public CoinsEngineProvider(String paramString) {
/* 14 */     this.currencyName = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 19 */     CoinsEngineAPI.addBalance(paramOfflinePlayer.getUniqueId(), this.currencyName, paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 24 */     CoinsEngineAPI.removeBalance(paramOfflinePlayer.getUniqueId(), this.currencyName, paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 29 */     return BigDecimal.valueOf(CoinsEngineAPI.getBalance(paramOfflinePlayer.getUniqueId(), this.currencyName));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\CoinsEngineProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */