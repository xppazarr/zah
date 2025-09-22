/*    */ package fr.maxlego08.zauctionhouse.filter.filters;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ 
/*    */ public class TypeFilter
/*    */   extends Filter {
/*    */   public TypeFilter() {
/* 10 */     super("type");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString) {
/* 16 */     String str = paramAuctionItem.getType().name();
/*    */     
/* 18 */     switch (paramFilterType) {
/*    */       case CONTAINS:
/* 20 */         return str.toLowerCase().contains(paramString.toLowerCase());
/*    */       case EQUALS:
/* 22 */         return str.equals(paramString);
/*    */       case EQUALSIGNORECASE:
/* 24 */         return str.equalsIgnoreCase(paramString);
/*    */     } 
/*    */ 
/*    */     
/* 28 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\filters\TypeFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */