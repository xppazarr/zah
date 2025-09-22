/*    */ package fr.maxlego08.zauctionhouse.dto;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.Column;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class OptionDTO
/*    */ {
/*    */   @Column("player_uuid")
/*    */   private final UUID playerUUID;
/*    */   @Column("disable_sell_confirmation")
/*    */   private final boolean disableSellConfirmation;
/*    */   @Column("sorting")
/*    */   private final String sorting;
/*    */   
/*    */   public OptionDTO(UUID paramUUID, boolean paramBoolean, String paramString) {
/* 20 */     this.playerUUID = paramUUID;
/* 21 */     this.disableSellConfirmation = paramBoolean;
/* 22 */     this.sorting = paramString;
/*    */   }
/*    */   
/*    */   public UUID getPlayerUUID() {
/* 26 */     return this.playerUUID;
/*    */   }
/*    */   
/*    */   public boolean isDisableSellConfirmation() {
/* 30 */     return this.disableSellConfirmation;
/*    */   }
/*    */   
/*    */   public String getSorting() {
/* 34 */     return this.sorting;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dto\OptionDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */