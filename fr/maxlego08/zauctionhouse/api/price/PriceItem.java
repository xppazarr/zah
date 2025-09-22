package fr.maxlego08.zauctionhouse.api.price;

import org.bukkit.inventory.ItemStack;

public interface PriceItem {
  long getMinPrice();
  
  long getMaxPrice();
  
  boolean isSimilar(ItemStack paramItemStack);
  
  String getName();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\price\PriceItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */