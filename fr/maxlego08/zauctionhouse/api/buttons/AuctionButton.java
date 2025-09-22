package fr.maxlego08.zauctionhouse.api.buttons;

import fr.maxlego08.menu.api.button.PaginateButton;
import fr.maxlego08.zauctionhouse.api.enums.InventoryType;

public abstract class AuctionButton extends PaginateButton {
  public abstract InventoryType getInventoryType();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\buttons\AuctionButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */