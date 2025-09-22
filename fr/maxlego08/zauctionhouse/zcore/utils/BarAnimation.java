/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.boss.BarColor;
/*    */ import org.bukkit.boss.BarStyle;
/*    */ import org.bukkit.boss.BossBar;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.plugin.java.JavaPlugin;
/*    */ import org.bukkit.scheduler.BukkitRunnable;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class BarAnimation
/*    */   extends BukkitRunnable
/*    */ {
/*    */   private final BossBar bossBar;
/*    */   private final double totalTime;
/*    */   private double remainingTime;
/*    */   
/*    */   public BarAnimation(List<Player> paramList, String paramString, int paramInt, BarColor paramBarColor, BarStyle paramBarStyle) {
/* 27 */     this.bossBar = Bukkit.createBossBar(paramString, paramBarColor, paramBarStyle, new org.bukkit.boss.BarFlag[0]);
/* 28 */     this.totalTime = paramInt * 20.0D;
/* 29 */     this.remainingTime = this.totalTime;
/*    */     
/* 31 */     for (Player player : paramList) {
/* 32 */       this.bossBar.addPlayer(player);
/*    */     }
/*    */     
/* 35 */     this.bossBar.setVisible(true);
/*    */     
/* 37 */     runTaskTimer((Plugin)JavaPlugin.getProvidingPlugin(getClass()), 0L, 1L);
/*    */   }
/*    */ 
/*    */   
/*    */   public void run() {
/* 42 */     double d = this.remainingTime / this.totalTime;
/* 43 */     this.bossBar.setProgress(d);
/*    */     
/* 45 */     if (this.remainingTime <= 0.0D) {
/* 46 */       this.bossBar.removeAll();
/* 47 */       cancel();
/*    */     } 
/*    */     
/* 50 */     this.remainingTime--;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\BarAnimation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */