package fr.maxlego08.zauctionhouse.api.economy;

import java.util.Collection;
import java.util.Optional;

public interface EconomyManager {
  Collection<AuctionEconomy> getEconomies();
  
  boolean registerEconomy(AuctionEconomy paramAuctionEconomy);
  
  boolean removeEconomy(AuctionEconomy paramAuctionEconomy);
  
  Optional<AuctionEconomy> getEconomy(String paramString);
  
  void loadEconomies();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\economy\EconomyManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */