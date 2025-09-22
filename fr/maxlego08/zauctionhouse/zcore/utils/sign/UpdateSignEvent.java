/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sign;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public final class UpdateSignEvent
/*    */ {
/*    */   private final SignGUI gui;
/*    */   private final Player player;
/*    */   private final String[] lines;
/*    */   
/*    */   public UpdateSignEvent(SignGUI paramSignGUI, Player paramPlayer, String[] paramArrayOfString) {
/* 12 */     this.gui = paramSignGUI;
/* 13 */     this.player = paramPlayer;
/* 14 */     this.lines = paramArrayOfString;
/*    */   }
/*    */   
/*    */   public SignGUI getGui() {
/* 18 */     return this.gui;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 22 */     return this.player;
/*    */   }
/*    */   
/*    */   public String[] getLines() {
/* 26 */     return this.lines;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sign\UpdateSignEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */