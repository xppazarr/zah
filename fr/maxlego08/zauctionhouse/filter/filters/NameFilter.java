/*    */ package fr.maxlego08.zauctionhouse.filter.filters;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class NameFilter
/*    */   extends Filter
/*    */ {
/*    */   public NameFilter() {
/* 14 */     super("name");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString) {
/* 19 */     for (ItemStack itemStack : getItems(paramAuctionItem)) {
/*    */       
/* 21 */       ItemMeta itemMeta = itemStack.getItemMeta();
/* 22 */       if (!itemMeta.hasDisplayName()) {
/*    */         continue;
/*    */       }
/* 25 */       String str = ChatColor.stripColor(itemMeta.getDisplayName());
/*    */       
/* 27 */       switch (paramFilterType) {
/*    */         case CONTAINS:
/* 29 */           return str.toLowerCase().contains(paramString.toLowerCase());
/*    */         case EQUALS:
/* 31 */           return str.equals(paramString);
/*    */         case EQUALSIGNORECASE:
/* 33 */           return str.equalsIgnoreCase(paramString);
/*    */       } 
/*    */ 
/*    */     
/*    */     } 
/* 38 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\filters\NameFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */