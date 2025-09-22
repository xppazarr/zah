package fr.maxlego08.zauctionhouse.api.storage;

import java.io.IOException;

public interface StorageManager {
  void setReady(boolean paramBoolean);
  
  void setCustomStorage(IStorage paramIStorage);
  
  IStorage getIStorage();
  
  Storage getStorage();
  
  void createBackup() throws IOException;
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\StorageManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */