/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import dev.unnm3d.rediseconomy.api.RedisEconomyAPI;
/*    */ import dev.unnm3d.rediseconomy.currency.Currency;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class RedisEconomyProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private final String economyName;
/*    */   
/*    */   public RedisEconomyProvider(String paramString) {
/* 16 */     this.economyName = paramString;
/*    */   }
/*    */   
/*    */   private Currency getCurrency() {
/* 20 */     RedisEconomyAPI redisEconomyAPI = RedisEconomyAPI.getAPI();
/* 21 */     if (redisEconomyAPI == null) {
/* 22 */       Bukkit.getLogger().info("RedisEconomyAPI not found!");
/* 23 */       return null;
/*    */     } 
/* 25 */     Currency currency = redisEconomyAPI.getCurrencyByName(this.economyName);
/* 26 */     if (currency == null) {
/* 27 */       Bukkit.getLogger().info("Currency " + this.economyName + " not found!");
/*    */     }
/* 29 */     return currency;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 34 */     Currency currency = getCurrency();
/* 35 */     if (currency != null) {
/* 36 */       currency.depositPlayer(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 42 */     Currency currency = getCurrency();
/* 43 */     if (currency != null) {
/* 44 */       currency.withdrawPlayer(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 50 */     Currency currency = getCurrency();
/* 51 */     return (currency != null) ? BigDecimal.valueOf(currency.getBalance(paramOfflinePlayer)) : BigDecimal.ZERO;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\RedisEconomyProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */