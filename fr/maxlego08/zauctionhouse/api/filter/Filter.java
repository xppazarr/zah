/*    */ package fr.maxlego08.zauctionhouse.api.filter;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class Filter
/*    */ {
/*    */   private final String key;
/*    */   
/*    */   public Filter(String paramString) {
/* 21 */     this.key = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean perform(AuctionItem paramAuctionItem, String paramString) {
/* 31 */     FilterType filterType = findFilterType(paramString);
/* 32 */     if (filterType == null)
/* 33 */       return false; 
/* 34 */     paramString = stripe(filterType, paramString);
/* 35 */     return perform(paramAuctionItem, filterType, paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected abstract boolean perform(AuctionItem paramAuctionItem, FilterType paramFilterType, String paramString);
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected List<ItemStack> getItems(AuctionItem paramAuctionItem) {
/* 53 */     if (paramAuctionItem.getType().equals(AuctionType.INVENTORY))
/* 54 */       return paramAuctionItem.getItemStacks(); 
/* 55 */     return Arrays.asList(new ItemStack[] { paramAuctionItem.getItemStack() });
/*    */   }
/*    */   
/*    */   public String getKey() {
/* 59 */     return this.key;
/*    */   }
/*    */   
/*    */   public String stripe(FilterType paramFilterType, String paramString) {
/* 63 */     return paramString.substring(getKey().length() + paramFilterType.getType().length());
/*    */   }
/*    */   
/*    */   protected FilterType findFilterType(String paramString) {
/* 67 */     paramString = String.valueOf(paramString.substring(getKey().length()));
/* 68 */     for (FilterType filterType : FilterType.values()) {
/* 69 */       if (paramString.startsWith(filterType.getType()))
/* 70 */         return filterType; 
/* 71 */     }  return null;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\filter\Filter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */