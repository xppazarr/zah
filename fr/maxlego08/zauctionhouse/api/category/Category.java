package fr.maxlego08.zauctionhouse.api.category;

import java.util.List;

public interface Category {
  String getDisplayName();
  
  String getName();
  
  List<CategoryItem> getCategoryItems();
  
  boolean isMiscellaneous();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\category\Category.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */