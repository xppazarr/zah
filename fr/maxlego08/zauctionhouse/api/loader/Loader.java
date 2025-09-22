package fr.maxlego08.zauctionhouse.api.loader;

import org.bukkit.configuration.file.YamlConfiguration;

public interface Loader<T> {
  T load(YamlConfiguration paramYamlConfiguration, String paramString, Object... paramVarArgs) throws Exception;
  
  void save(T paramT, YamlConfiguration paramYamlConfiguration, String paramString);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\loader\Loader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */