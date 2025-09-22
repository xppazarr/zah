/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheAuctionItem
/*    */ {
/*    */   private final List<AuctionItem> auctionItems;
/*    */   private long expiredAt;
/*    */   
/*    */   public CacheAuctionItem(List<AuctionItem> paramList, long paramLong) {
/* 14 */     this.auctionItems = paramList;
/* 15 */     this.expiredAt = System.currentTimeMillis() + 1000L * paramLong;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<AuctionItem> getAuctionItems() {
/* 22 */     return this.auctionItems;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getExpiredAt() {
/* 29 */     return this.expiredAt;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 33 */     return (this.expiredAt > System.currentTimeMillis());
/*    */   }
/*    */   
/*    */   public void invalid() {
/* 37 */     this.expiredAt = 0L;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\CacheAuctionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */