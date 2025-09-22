package fr.maxlego08.zauctionhouse.api.blacklist;

import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
import java.util.List;
import java.util.Optional;
import org.bukkit.inventory.ItemStack;

public interface IBlacklistManager {
  List<ItemChecker> getBlacklist();
  
  void registerBlacklist(ItemChecker paramItemChecker);
  
  void unregisterBlacklist(ItemChecker paramItemChecker);
  
  void unregisterAll();
  
  Optional<ItemChecker> isBlacklist(ItemStack paramItemStack);
  
  void loadConfigurations(ZAuctionPlugin paramZAuctionPlugin);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\blacklist\IBlacklistManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */