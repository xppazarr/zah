/*    */ package fr.maxlego08.zauctionhouse.api.economy;
/*    */ 
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.jetbrains.annotations.Nullable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public interface AuctionEconomy
/*    */ {
/*    */   long getMoney(OfflinePlayer paramOfflinePlayer);
/*    */   
/*    */   boolean hasMoney(OfflinePlayer paramOfflinePlayer, long paramLong);
/*    */   
/*    */   void depositMoney(OfflinePlayer paramOfflinePlayer, long paramLong, String paramString);
/*    */   
/*    */   void withdrawMoney(OfflinePlayer paramOfflinePlayer, long paramLong, String paramString);
/*    */   
/*    */   String getCurrency();
/*    */   
/*    */   String getFormat();
/*    */   
/*    */   String getDenyMessage();
/*    */   
/*    */   String getName();
/*    */   
/*    */   @Nullable
/*    */   String getPermission();
/*    */   
/*    */   default String format(String priceAsString, long amount) {
/* 89 */     return getCurrency().replace("%price%", priceAsString).replace("%s%", (amount > 1L) ? "s" : "");
/*    */   }
/*    */   
/*    */   String getDepositReason();
/*    */   
/*    */   String getWithdrawReason();
/*    */   
/*    */   String getWithdrawTaxReason();
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\economy\AuctionEconomy.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */