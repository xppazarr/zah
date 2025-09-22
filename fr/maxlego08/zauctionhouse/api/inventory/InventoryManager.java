package fr.maxlego08.zauctionhouse.api.inventory;

import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
import java.util.Collection;
import java.util.Optional;

public interface InventoryManager {
  Inventory getInventory(InventoryName paramInventoryName);
  
  Inventory getInventory(String paramString, boolean paramBoolean);
  
  Inventory getInventory(InventoryType paramInventoryType);
  
  void loadInventories() throws Exception;
  
  Inventory loadInventory(String paramString) throws Exception;
  
  void saveInventories();
  
  void delete();
  
  Optional<Inventory> getInventoryByName(String paramString);
  
  Collection<Inventory> getInventories();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\inventory\InventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */