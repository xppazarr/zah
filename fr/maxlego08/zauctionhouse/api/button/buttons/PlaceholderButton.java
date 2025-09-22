package fr.maxlego08.zauctionhouse.api.button.buttons;

import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;

public interface PlaceholderButton extends PermissibleButton {
  String getPlaceHolder();
  
  PlaceholderAction getAction();
  
  boolean hasPlaceHolder();
  
  String getValue();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\button\buttons\PlaceholderButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */