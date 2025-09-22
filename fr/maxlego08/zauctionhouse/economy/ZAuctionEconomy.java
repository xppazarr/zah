/*    */ package fr.maxlego08.zauctionhouse.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ public class ZAuctionEconomy
/*    */   extends ZUtils
/*    */   implements AuctionEconomy
/*    */ {
/*    */   private final CurrencyProvider currencyProvider;
/*    */   private final String name;
/*    */   private final String currency;
/*    */   private final String denyMessage;
/*    */   private final String format;
/*    */   private final String permission;
/*    */   private final String depositReason;
/*    */   private final String withdrawReason;
/*    */   private final String withdrawTaxReason;
/*    */   
/*    */   public ZAuctionEconomy(CurrencyProvider paramCurrencyProvider, String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6, String paramString7, String paramString8) {
/* 25 */     this.currencyProvider = paramCurrencyProvider;
/* 26 */     this.name = paramString1;
/* 27 */     this.currency = paramString2;
/* 28 */     this.denyMessage = paramString3;
/* 29 */     this.format = paramString4;
/* 30 */     this.permission = paramString5;
/* 31 */     this.depositReason = paramString6;
/* 32 */     this.withdrawReason = paramString7;
/* 33 */     this.withdrawTaxReason = paramString8;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getCurrency() {
/* 38 */     return this.currency;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 43 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDenyMessage() {
/* 48 */     return this.denyMessage;
/*    */   }
/*    */ 
/*    */   
/*    */   public long getMoney(OfflinePlayer paramOfflinePlayer) {
/* 53 */     return this.currencyProvider.getBalance(paramOfflinePlayer).longValue();
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasMoney(OfflinePlayer paramOfflinePlayer, long paramLong) {
/* 58 */     return (getMoney(paramOfflinePlayer) >= paramLong);
/*    */   }
/*    */ 
/*    */   
/*    */   public void depositMoney(OfflinePlayer paramOfflinePlayer, long paramLong, String paramString) {
/* 63 */     this.currencyProvider.deposit(paramOfflinePlayer, BigDecimal.valueOf(paramLong), paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdrawMoney(OfflinePlayer paramOfflinePlayer, long paramLong, String paramString) {
/* 68 */     this.currencyProvider.withdraw(paramOfflinePlayer, BigDecimal.valueOf(paramLong), paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getFormat() {
/* 73 */     return this.format;
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   public String getPermission() {
/* 79 */     return this.permission;
/*    */   }
/*    */   
/*    */   public CurrencyProvider getCurrencyProvider() {
/* 83 */     return this.currencyProvider;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDepositReason() {
/* 88 */     return this.depositReason;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getWithdrawReason() {
/* 93 */     return this.withdrawReason;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getWithdrawTaxReason() {
/* 98 */     return this.withdrawTaxReason;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\economy\ZAuctionEconomy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */