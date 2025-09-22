package fr.maxlego08.zauctionhouse.zcore.utils.map;

import java.util.Map;
import java.util.Optional;

public interface OptionalMap<K, V> extends Map<K, V> {
  Optional<V> getOptional(K paramK);
  
  boolean isPresent(K paramK);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\map\OptionalMap.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */