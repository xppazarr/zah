/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.black_ixx.playerpoints.PlayerPoints;
/*    */ import org.black_ixx.playerpoints.PlayerPointsAPI;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ 
/*    */ public class PlayerPointsProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private PlayerPointsAPI playerPointsAPI;
/*    */   
/*    */   private PlayerPointsAPI getAPI() {
/* 16 */     if (this.playerPointsAPI == null) {
/* 17 */       PlayerPoints playerPoints = (PlayerPoints)JavaPlugin.getPlugin(PlayerPoints.class);
/* 18 */       this.playerPointsAPI = playerPoints.getAPI();
/*    */     } 
/*    */     
/* 21 */     return this.playerPointsAPI;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 26 */     getAPI().give(paramOfflinePlayer.getUniqueId(), paramBigDecimal.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 31 */     getAPI().take(paramOfflinePlayer.getUniqueId(), paramBigDecimal.intValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 36 */     return BigDecimal.valueOf(getAPI().look(paramOfflinePlayer.getUniqueId()));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\PlayerPointsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */