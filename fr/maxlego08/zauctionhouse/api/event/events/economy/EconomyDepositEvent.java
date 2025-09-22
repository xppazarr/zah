/*    */ package fr.maxlego08.zauctionhouse.api.event.events.economy;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ 
/*    */ @Deprecated
/*    */ public class EconomyDepositEvent
/*    */   extends AuctionEvent
/*    */ {
/*    */   private final OfflinePlayer player;
/*    */   private final double money;
/*    */   
/*    */   public EconomyDepositEvent(OfflinePlayer paramOfflinePlayer, double paramDouble) {
/* 15 */     this.player = paramOfflinePlayer;
/* 16 */     this.money = paramDouble;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public OfflinePlayer getPlayer() {
/* 23 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public double getMoney() {
/* 30 */     return this.money;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\economy\EconomyDepositEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */