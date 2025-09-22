/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.essentials.api.EssentialsPlugin;
/*    */ import fr.maxlego08.essentials.api.economy.Economy;
/*    */ import fr.maxlego08.essentials.api.economy.EconomyManager;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import java.util.Optional;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class ZEssentialsProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private EconomyManager economyManager;
/*    */   private Economy economy;
/*    */   private final String economyName;
/*    */   
/*    */   public ZEssentialsProvider(String paramString) {
/* 20 */     this.economyName = paramString;
/*    */   }
/*    */   
/*    */   private void initialize() {
/* 24 */     if (this.economyManager == null || this.economy == null) {
/* 25 */       EssentialsPlugin essentialsPlugin = (EssentialsPlugin)Bukkit.getPluginManager().getPlugin("zEssentials");
/* 26 */       this.economyManager = essentialsPlugin.getEconomyManager();
/* 27 */       Optional<Economy> optional = this.economyManager.getEconomy(this.economyName);
/* 28 */       if (optional.isPresent()) {
/* 29 */         this.economy = optional.get();
/*    */       } else {
/* 31 */         throw new NullPointerException("ZEssentials economy " + this.economyName + " not found");
/*    */       } 
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 38 */     initialize();
/* 39 */     this.economyManager.deposit(paramOfflinePlayer.getUniqueId(), this.economy, paramBigDecimal, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 44 */     initialize();
/* 45 */     this.economyManager.withdraw(paramOfflinePlayer.getUniqueId(), this.economy, paramBigDecimal, paramString);
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 50 */     initialize();
/* 51 */     return this.economyManager.getBalance(paramOfflinePlayer, this.economy);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ZEssentialsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */