package fr.maxlego08.zauctionhouse.api.button.buttons;

import java.util.List;
import org.bukkit.entity.Player;

public interface PerformButton extends PermissibleButton {
  List<String> getCommands();
  
  List<String> getConsoleCommands();
  
  void execute(Player paramPlayer);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\button\buttons\PerformButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */