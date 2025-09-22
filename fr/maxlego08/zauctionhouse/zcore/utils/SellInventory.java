/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SellInventory
/*    */ {
/*    */   private final long price;
/*    */   private final AuctionEconomy economy;
/*    */   
/*    */   public SellInventory(long paramLong, AuctionEconomy paramAuctionEconomy) {
/* 16 */     this.price = paramLong;
/* 17 */     this.economy = paramAuctionEconomy;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getPrice() {
/* 24 */     return this.price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionEconomy getEconomy() {
/* 31 */     return this.economy;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\SellInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */