package fr.maxlego08.zauctionhouse.api.storage;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;

public interface CustomStorageProvider {
  boolean store(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\CustomStorageProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */