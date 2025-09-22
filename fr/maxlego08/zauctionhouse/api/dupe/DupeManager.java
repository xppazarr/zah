package fr.maxlego08.zauctionhouse.api.dupe;

import org.bukkit.inventory.ItemStack;

public interface DupeManager {
  public static final String KEY = "ZAUCTIONHOUSE-ITEM";
  
  ItemStack protectItem(ItemStack paramItemStack);
  
  boolean isDupeItem(ItemStack paramItemStack);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\dupe\DupeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */