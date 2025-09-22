package fr.maxlego08.zauctionhouse.api.button.buttons;

import fr.maxlego08.zauctionhouse.api.button.Button;
import org.bukkit.entity.Player;

public interface PermissibleButton extends Button {
  Button getElseButton();
  
  String getPermission();
  
  String getMessage();
  
  boolean hasPermission();
  
  boolean hasElseButton();
  
  boolean hasMessage();
  
  boolean checkPermission(Player paramPlayer);
  
  boolean needToGlow();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\button\buttons\PermissibleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */