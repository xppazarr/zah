/*    */ package fr.maxlego08.zauctionhouse.categories;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*    */ import fr.maxlego08.zauctionhouse.api.category.CategoryItem;
/*    */ import java.util.List;
/*    */ import java.util.Objects;
/*    */ 
/*    */ 
/*    */ public class ZCategory
/*    */   implements Category
/*    */ {
/*    */   private final String displayName;
/*    */   private final String name;
/*    */   private final List<CategoryItem> categoryItems;
/*    */   
/*    */   public ZCategory(String paramString1, String paramString2, List<CategoryItem> paramList) {
/* 17 */     this.displayName = paramString1;
/* 18 */     this.name = paramString2;
/* 19 */     this.categoryItems = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 24 */     return this.name;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<CategoryItem> getCategoryItems() {
/* 29 */     return this.categoryItems;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isMiscellaneous() {
/* 34 */     return (this.categoryItems == null);
/*    */   }
/*    */ 
/*    */   
/*    */   public String getDisplayName() {
/* 39 */     return this.displayName;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 49 */     return "ZCategory [displayName=" + this.displayName + ", name=" + this.name + ", materials=" + this.categoryItems + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 59 */     if (this == paramObject) return true; 
/* 60 */     if (paramObject == null || getClass() != paramObject.getClass()) return false; 
/* 61 */     ZCategory zCategory = (ZCategory)paramObject;
/* 62 */     return Objects.equals(this.name, zCategory.name);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\categories\ZCategory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */