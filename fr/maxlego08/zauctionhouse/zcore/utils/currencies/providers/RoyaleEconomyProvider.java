/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import me.qKing12.RoyaleEconomy.API.Currency;
/*    */ import me.qKing12.RoyaleEconomy.API.MultiCurrencyHandler;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class RoyaleEconomyProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private Currency currency;
/*    */   private final String currencyId;
/*    */   
/*    */   public RoyaleEconomyProvider(String paramString) {
/* 16 */     this.currencyId = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   private void initialize() {
/* 21 */     if (this.currency != null) {
/*    */       return;
/*    */     }
/* 24 */     if (MultiCurrencyHandler.getCurrencies() == null) {
/* 25 */       throw new NullPointerException("RoyaleEconomy multi-currency not enabled.");
/*    */     }
/* 27 */     this.currency = MultiCurrencyHandler.findCurrencyById(this.currencyId);
/* 28 */     if (this.currency == null) {
/* 29 */       throw new NullPointerException("RoyaleEconomy currency " + this.currencyId + " not found");
/*    */     }
/*    */   }
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 34 */     initialize();
/* 35 */     this.currency.addAmount(paramOfflinePlayer.getUniqueId().toString(), paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 40 */     initialize();
/* 41 */     this.currency.removeAmount(paramOfflinePlayer.getUniqueId().toString(), paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 46 */     initialize();
/* 47 */     return BigDecimal.valueOf(this.currency.getAmount(paramOfflinePlayer.getUniqueId().toString()));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\RoyaleEconomyProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */