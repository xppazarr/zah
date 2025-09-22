/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class ExperienceProvider
/*    */   implements CurrencyProvider
/*    */ {
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 13 */     if (paramOfflinePlayer.isOnline()) {
/* 14 */       Player player = paramOfflinePlayer.getPlayer();
/* 15 */       BigDecimal bigDecimal = BigDecimal.valueOf(getTotalExperience(player));
/* 16 */       setTotalExperience(player, bigDecimal.add(paramBigDecimal).intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 22 */     if (paramOfflinePlayer.isOnline()) {
/* 23 */       Player player = paramOfflinePlayer.getPlayer();
/* 24 */       BigDecimal bigDecimal1 = BigDecimal.valueOf(getTotalExperience(player));
/* 25 */       BigDecimal bigDecimal2 = bigDecimal1.subtract(paramBigDecimal);
/* 26 */       setTotalExperience(player, bigDecimal2.max(BigDecimal.ZERO).intValue());
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 32 */     if (paramOfflinePlayer.isOnline()) {
/* 33 */       Player player = paramOfflinePlayer.getPlayer();
/* 34 */       return (player == null) ? BigDecimal.ZERO : BigDecimal.valueOf(getTotalExperience(player));
/* 35 */     }  return BigDecimal.ZERO;
/*    */   }
/*    */   
/*    */   private void setTotalExperience(Player paramPlayer, int paramInt) {
/* 39 */     if (paramInt < 0) throw new IllegalArgumentException("Experience is negative!"); 
/* 40 */     paramPlayer.setExp(0.0F);
/* 41 */     paramPlayer.setLevel(0);
/* 42 */     paramPlayer.setTotalExperience(0);
/* 43 */     int i = paramInt;
/* 44 */     while (i > 0) {
/* 45 */       int j = getExpAtLevel(paramPlayer);
/* 46 */       i -= j;
/* 47 */       if (i >= 0) {
/* 48 */         paramPlayer.giveExp(j);
/*    */         continue;
/*    */       } 
/* 51 */       i += j;
/* 52 */       paramPlayer.giveExp(i);
/* 53 */       i = 0;
/*    */     } 
/*    */   }
/*    */   
/*    */   private int getExpAtLevel(Player paramPlayer) {
/* 58 */     return getExpAtLevel(paramPlayer.getLevel());
/*    */   }
/*    */   
/*    */   private int getExpAtLevel(int paramInt) {
/* 62 */     if (paramInt <= 15) return 2 * paramInt + 7; 
/* 63 */     if (paramInt <= 30) return 5 * paramInt - 38; 
/* 64 */     return 9 * paramInt - 158;
/*    */   }
/*    */   
/*    */   private int getTotalExperience(Player paramPlayer) {
/* 68 */     int i = Math.round(getExpAtLevel(paramPlayer) * paramPlayer.getExp());
/* 69 */     int j = paramPlayer.getLevel();
/* 70 */     while (j > 0) {
/* 71 */       j--;
/* 72 */       i += getExpAtLevel(j);
/*    */     } 
/* 74 */     if (i < 0) {
/* 75 */       i = Integer.MAX_VALUE;
/*    */     }
/* 77 */     return i;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ExperienceProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */