/*    */ package fr.maxlego08.zauctionhouse.filter.filters;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ import org.bukkit.ChatColor;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class LoreFilter
/*    */   extends Filter
/*    */ {
/*    */   public LoreFilter() {
/* 14 */     super("lore");
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString) {
/* 19 */     for (ItemStack itemStack : getItems(paramAuctionItem)) {
/*    */       
/* 21 */       ItemMeta itemMeta = itemStack.getItemMeta();
/* 22 */       if (!itemMeta.hasLore()) {
/*    */         continue;
/*    */       }
/* 25 */       for (String str1 : itemMeta.getLore()) {
/* 26 */         String str2 = ChatColor.stripColor(str1);
/*    */         
/* 28 */         switch (paramFilterType) {
/*    */           case CONTAINS:
/* 30 */             return str2.toLowerCase().contains(paramString.toLowerCase());
/*    */           case EQUALS:
/* 32 */             return str2.equals(paramString);
/*    */           case EQUALSIGNORECASE:
/* 34 */             return str2.equalsIgnoreCase(paramString);
/*    */         } 
/*    */ 
/*    */       
/*    */       } 
/*    */     } 
/* 40 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\filters\LoreFilter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */