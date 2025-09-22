package fr.maxlego08.zauctionhouse.api.sound;

import fr.maxlego08.zauctionhouse.zcore.utils.xseries.XSound;
import org.bukkit.entity.Entity;

public interface SoundOption {
  XSound getSound();
  
  float getPitch();
  
  float getVolume();
  
  void play(Entity paramEntity);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\sound\SoundOption.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */