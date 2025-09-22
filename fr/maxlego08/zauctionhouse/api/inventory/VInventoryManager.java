package fr.maxlego08.zauctionhouse.api.inventory;

import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
import org.bukkit.entity.Player;

public interface VInventoryManager {
  void createInventory(EnumInventory paramEnumInventory, Player paramPlayer, int paramInt, Object... paramVarArgs);
  
  void updateAllPlayer(int paramInt);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\inventory\VInventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */