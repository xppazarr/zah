/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import net.milkbowl.vault.economy.Economy;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.plugin.RegisteredServiceProvider;
/*    */ 
/*    */ public class VaultProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   private Economy economy;
/*    */   
/*    */   private Economy getEconomy() {
/* 16 */     if (this.economy == null) {
/* 17 */       RegisteredServiceProvider registeredServiceProvider = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
/* 18 */       if (registeredServiceProvider != null) {
/* 19 */         this.economy = (Economy)registeredServiceProvider.getProvider();
/* 20 */         return this.economy;
/*    */       } 
/* 22 */       throw new NullPointerException("Vault Economy interface not found");
/*    */     } 
/*    */     
/* 25 */     return this.economy;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 30 */     getEconomy().depositPlayer(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 35 */     getEconomy().withdrawPlayer(paramOfflinePlayer, paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 40 */     return BigDecimal.valueOf(getEconomy().getBalance(paramOfflinePlayer));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\VaultProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */