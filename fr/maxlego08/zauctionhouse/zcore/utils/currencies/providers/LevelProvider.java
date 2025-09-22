/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ 
/*    */ public class LevelProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 12 */     if (paramOfflinePlayer.isOnline()) {
/* 13 */       int i = paramOfflinePlayer.getPlayer().getLevel();
/* 14 */       paramOfflinePlayer.getPlayer().setLevel(i + paramBigDecimal.intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 20 */     if (paramOfflinePlayer.isOnline()) {
/* 21 */       int i = paramOfflinePlayer.getPlayer().getLevel();
/* 22 */       paramOfflinePlayer.getPlayer().setLevel(i - paramBigDecimal.intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 28 */     return BigDecimal.valueOf(paramOfflinePlayer.isOnline() ? paramOfflinePlayer.getPlayer().getLevel() : 0L);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\LevelProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */