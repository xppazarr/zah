/*    */ package fr.maxlego08.zauctionhouse.convert.zauctionhousev2;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class ZAuctionHouseV2Storage
/*    */   implements Savable
/*    */ {
/* 11 */   private static List<AuctionItem> items = new ArrayList<>();
/* 12 */   private static List<AuctionItem> buyingItems = new ArrayList<>();
/* 13 */   private static List<AuctionItem> expiredItems = new ArrayList<>();
/*    */ 
/*    */ 
/*    */   
/*    */   public void save(Persist paramPersist) {}
/*    */ 
/*    */ 
/*    */   
/*    */   public void load(Persist paramPersist) {
/* 22 */     paramPersist.loadOrSaveDefault(this, ZAuctionHouseV2Storage.class, "convert/items");
/*    */   }
/*    */   
/*    */   public List<AuctionItem> getBuyingItems() {
/* 26 */     return buyingItems;
/*    */   }
/*    */   
/*    */   public List<AuctionItem> getItems() {
/* 30 */     return items;
/*    */   }
/*    */   
/*    */   public List<AuctionItem> getExpiredItems() {
/* 34 */     return expiredItems;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\convert\zauctionhousev2\ZAuctionHouseV2Storage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */