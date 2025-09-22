package fr.maxlego08.zauctionhouse.api.filter;

import fr.maxlego08.zauctionhouse.api.enums.FilterType;
import java.util.Optional;

public interface FilterManager {
  Optional<Filter> getFiltre(String paramString);
  
  Optional<Filter> find(String paramString);
  
  void registerFilter(Filter paramFilter);
  
  String stripeString(Filter paramFilter, FilterType paramFilterType, String paramString);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\filter\FilterManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */