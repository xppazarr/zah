package fr.maxlego08.zauctionhouse.api.category;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.storage.Savable;
import java.util.List;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

public interface CategoryManager extends Savable {
  Optional<Category> getByName(String paramString);
  
  List<Category> getByItemStack(ItemStack paramItemStack);
  
  List<AuctionItem> getMiscellaneous(List<AuctionItem> paramList);
  
  ItemStack getRandomItemStacks();
  
  List<Category> getCategories(AuctionItem paramAuctionItem);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\category\CategoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */