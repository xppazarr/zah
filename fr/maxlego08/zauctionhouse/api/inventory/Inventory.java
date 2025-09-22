package fr.maxlego08.zauctionhouse.api.inventory;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.button.Button;
import fr.maxlego08.zauctionhouse.api.button.buttons.PlaceholderButton;
import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
import java.util.List;
import org.bukkit.entity.Player;

public interface Inventory {
  int size();
  
  InventoryType getType();
  
  String getName();
  
  String getName(String paramString1, String paramString2);
  
  <T extends Button> List<T> getButtons(Class<T> paramClass);
  
  List<Button> getButtons();
  
  void open(Player paramPlayer);
  
  List<PlaceholderButton> sortButtons(int paramInt);
  
  int getMaxPage();
  
  int getMaxPage(List<AuctionItem> paramList);
  
  String getFileName();
  
  int getAuctionPageSize();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\inventory\Inventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */