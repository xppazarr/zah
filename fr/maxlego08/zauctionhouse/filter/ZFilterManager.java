/*    */ package fr.maxlego08.zauctionhouse.filter;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.FilterType;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.Filter;
/*    */ import fr.maxlego08.zauctionhouse.api.filter.FilterManager;
/*    */ import fr.maxlego08.zauctionhouse.filter.filters.NameFilter;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import java.util.Optional;
/*    */ 
/*    */ 
/*    */ public class ZFilterManager
/*    */   implements FilterManager
/*    */ {
/* 15 */   private final Map<String, Filter> filters = new HashMap<>();
/*    */   
/*    */   public ZFilterManager() {
/* 18 */     this.filters.put("name", new NameFilter());
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Filter> getFiltre(String paramString) {
/* 23 */     return Optional.ofNullable(this.filters.getOrDefault(paramString, null));
/*    */   }
/*    */ 
/*    */   
/*    */   public void registerFilter(Filter paramFilter) {
/* 28 */     this.filters.put(paramFilter.getKey(), paramFilter);
/*    */   }
/*    */ 
/*    */   
/*    */   public String stripeString(Filter paramFilter, FilterType paramFilterType, String paramString) {
/* 33 */     return paramString.substring(paramFilter.getKey().length() + paramFilterType.getType().length());
/*    */   }
/*    */ 
/*    */   
/*    */   public Optional<Filter> find(String paramString) {
/* 38 */     if (paramString == null) return Optional.empty();
/*    */     
/* 40 */     for (Map.Entry<String, Filter> entry : this.filters.entrySet()) {
/* 41 */       String str = (String)entry.getKey();
/* 42 */       Filter filter = (Filter)entry.getValue();
/* 43 */       if (paramString.startsWith(str))
/* 44 */         return Optional.of(filter); 
/*    */     } 
/* 46 */     return Optional.empty();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\filter\ZFilterManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */