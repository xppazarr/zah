package fr.maxlego08.zauctionhouse.api.button;

import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Button {
  ItemStack getItemStack();
  
  ItemStack getCustomItemStack(Player paramPlayer);
  
  ButtonType getType();
  
  int getSlot();
  
  void setTmpSlot(int paramInt);
  
  int getTmpSlot();
  
  boolean isClickable();
  
  <T extends Button> T toButton(Class<T> paramClass);
  
  boolean isPermament();
  
  SoundOption getSound();
  
  void playSound(Entity paramEntity);
  
  boolean isDisableEvent();
  
  boolean closeInventory();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\button\Button.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */