/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.event.AuctionEvent;
/*    */ 
/*    */ public class AuctionItemExpireEvent
/*    */   extends AuctionEvent
/*    */ {
/*    */   private long expireAt;
/*    */   private final long currentMilliSecond;
/*    */   private final AuctionItem auctionItem;
/*    */   private final StorageType type;
/*    */   
/*    */   public AuctionItemExpireEvent(long paramLong1, long paramLong2, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 16 */     this.expireAt = paramLong1;
/* 17 */     this.currentMilliSecond = paramLong2;
/* 18 */     this.auctionItem = paramAuctionItem;
/* 19 */     this.type = paramStorageType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getExpireAt() {
/* 26 */     return this.expireAt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setExpireAt(long paramLong) {
/* 34 */     this.expireAt = paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getCurrentMilliSecond() {
/* 41 */     return this.currentMilliSecond;
/*    */   }
/*    */   
/*    */   public AuctionItem getAuctionItem() {
/* 45 */     return this.auctionItem;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public StorageType getType() {
/* 52 */     return this.type;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionItemExpireEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */