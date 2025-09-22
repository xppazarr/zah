/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Price
/*    */ {
/*    */   private long price;
/*    */   private CommandType type;
/*    */   
/*    */   public Price(CommandType paramCommandType) {
/* 22 */     this.type = paramCommandType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Price(long paramLong, CommandType paramCommandType) {
/* 33 */     this.price = paramLong;
/* 34 */     this.type = paramCommandType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Price() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getPrice() {
/* 48 */     return this.price;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setPrice(long paramLong) {
/* 55 */     this.price = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public CommandType getType() {
/* 62 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setType(CommandType paramCommandType) {
/* 69 */     this.type = paramCommandType;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\Price.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */