package fr.maxlego08.zauctionhouse.api.category;

import org.bukkit.inventory.ItemStack;

public interface CategoryItem {
  boolean isInCategory(ItemStack paramItemStack);
  
  ItemStack createItemStack();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\category\CategoryItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */