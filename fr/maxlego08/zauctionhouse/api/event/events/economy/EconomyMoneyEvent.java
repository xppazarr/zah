/*    */ package fr.maxlego08.zauctionhouse.api.event.events.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class EconomyMoneyEvent
/*    */   extends AuctionEvent
/*    */ {
/*    */   private final OfflinePlayer player;
/*    */   private double money;
/*    */   
/*    */   public EconomyMoneyEvent(OfflinePlayer paramOfflinePlayer) {
/* 15 */     this.player = paramOfflinePlayer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OfflinePlayer getPlayer() {
/* 22 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMoney() {
/* 29 */     return this.money;
/*    */   }
/*    */   
/*    */   public void setMoney(double paramDouble) {
/* 33 */     this.money = paramDouble;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\economy\EconomyMoneyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */