/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import com.willfp.ecobits.currencies.Currencies;
/*    */ import com.willfp.ecobits.currencies.Currency;
/*    */ import com.willfp.ecobits.currencies.CurrencyUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class EcoBitProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private Currency currency;
/*    */   private final String currencyName;
/*    */   
/*    */   public EcoBitProvider(String paramString) {
/* 17 */     this.currencyName = paramString;
/*    */   }
/*    */   
/*    */   private void initialize() {
/* 21 */     if (this.currency == null) {
/* 22 */       this.currency = Currencies.getByID(this.currencyName);
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 28 */     initialize();
/* 29 */     CurrencyUtils.adjustBalance(paramOfflinePlayer, this.currency, paramBigDecimal);
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 34 */     initialize();
/* 35 */     CurrencyUtils.adjustBalance(paramOfflinePlayer, this.currency, paramBigDecimal.negate());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 40 */     initialize();
/* 41 */     return CurrencyUtils.getBalance(paramOfflinePlayer, this.currency);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\EcoBitProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */