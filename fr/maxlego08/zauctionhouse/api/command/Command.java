package fr.maxlego08.zauctionhouse.api.command;

import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
import java.util.List;

public interface Command {
  String getCommand();
  
  String getPermission();
  
  String getDescription();
  
  List<String> getAliases();
  
  Inventory getInventory();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\command\Command.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */