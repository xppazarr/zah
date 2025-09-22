/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import me.elementalgaming.ElementalGems.GemAPI;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class ElementalGemsProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 12 */     GemAPI.addGems(paramOfflinePlayer.getUniqueId(), paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 17 */     GemAPI.removeGems(paramOfflinePlayer.getUniqueId(), paramBigDecimal.doubleValue());
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 22 */     return BigDecimal.valueOf(GemAPI.getGems(paramOfflinePlayer.getUniqueId()));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ElementalGemsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */