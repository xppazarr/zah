/*    */ package fr.maxlego08.zauctionhouse.filter.filters;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class MaterialFilter
/*    */   extends Filter
/*    */ {
/*    */   public MaterialFilter() {
/* 12 */     super("material");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString) {
/* 18 */     for (ItemStack itemStack : getItems(paramAuctionItem)) {
/*    */       
/* 20 */       String str = itemStack.getType().name();
/*    */       
/* 22 */       switch (paramFilterType) {
/*    */         case CONTAINS:
/* 24 */           return str.toLowerCase().contains(paramString.toLowerCase());
/*    */         case EQUALS:
/* 26 */           return str.equals(paramString);
/*    */         case EQUALSIGNORECASE:
/* 28 */           return str.equalsIgnoreCase(paramString);
/*    */       } 
/*    */ 
/*    */     
/*    */     } 
/* 33 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\filters\MaterialFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */