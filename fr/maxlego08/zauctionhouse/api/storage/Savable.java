package fr.maxlego08.zauctionhouse.api.storage;

import fr.maxlego08.zauctionhouse.api.utils.Persist;

public interface Savable {
  void save(Persist paramPersist);
  
  void load(Persist paramPersist);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\Savable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */