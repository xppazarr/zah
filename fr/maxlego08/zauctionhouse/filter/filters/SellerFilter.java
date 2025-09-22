/*    */ package fr.maxlego08.zauctionhouse.filter.filters;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class SellerFilter
/*    */   extends Filter
/*    */ {
/*    */   public SellerFilter() {
/* 12 */     super("seller");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString) {
/* 18 */     OfflinePlayer offlinePlayer = paramAuctionItem.getSeller();
/* 19 */     String str = offlinePlayer.getName();
/*    */     
/* 21 */     switch (paramFilterType) {
/*    */       case CONTAINS:
/* 23 */         return str.toLowerCase().contains(paramString.toLowerCase());
/*    */       case EQUALS:
/* 25 */         return str.equals(paramString);
/*    */       case EQUALSIGNORECASE:
/* 27 */         return str.equalsIgnoreCase(paramString);
/*    */     } 
/*    */ 
/*    */     
/* 31 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\filters\SellerFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */