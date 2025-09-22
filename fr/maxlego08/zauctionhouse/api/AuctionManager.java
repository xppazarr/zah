package fr.maxlego08.zauctionhouse.api;

import fr.maxlego08.zauctionhouse.api.category.Category;
import fr.maxlego08.zauctionhouse.api.command.Command;
import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
import fr.maxlego08.zauctionhouse.api.enums.HistoryType;
import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
import fr.maxlego08.zauctionhouse.api.enums.Sorting;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;
import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
import fr.maxlego08.zauctionhouse.api.storage.IStorage;
import fr.maxlego08.zauctionhouse.api.utils.CacheAuctionItem;
import fr.maxlego08.zauctionhouse.api.utils.Priority;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.permissions.Permissible;

public interface AuctionManager {
  void open(Player paramPlayer, Command paramCommand);
  
  void openDefault(Player paramPlayer);
  
  void open(AuctionPlugin paramAuctionPlugin, InventoryType paramInventoryType, Player paramPlayer, AuctionItem paramAuctionItem, int paramInt, List<Inventory> paramList, String paramString);
  
  void reload();
  
  void closeInventory();
  
  IStorage getStorage();
  
  void update(StorageType paramStorageType);
  
  void sellItem(AuctionItem paramAuctionItem, ItemStack paramItemStack, Player paramPlayer, long paramLong, AuctionEconomy paramAuctionEconomy, int paramInt, AuctionType paramAuctionType);
  
  List<AuctionItem> getSortItems(Player paramPlayer);
  
  Sorting getSort(Player paramPlayer);
  
  void setSort(Player paramPlayer, Sorting paramSorting);
  
  void setDisableSellConfirmation(Player paramPlayer, boolean paramBoolean);
  
  void loadConfiguration();
  
  long getMaxSellPerPermission(Permissible paramPermissible);
  
  long getExpirationPerPermission(Permissible paramPermissible);
  
  long getMaxPricePerEconomyPermission(AuctionEconomy paramAuctionEconomy, Permissible paramPermissible);
  
  boolean canSellMoreItem(Player paramPlayer);
  
  long count(Player paramPlayer, StorageType paramStorageType);
  
  void remove(AuctionItem paramAuctionItem, Player paramPlayer, boolean paramBoolean);
  
  List<AuctionItem> getExpire(Player paramPlayer);
  
  void removeItem(Player paramPlayer, AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  void buy(AuctionItem paramAuctionItem, Player paramPlayer);
  
  List<AuctionItem> getBuying(Player paramPlayer);
  
  long count(StorageType paramStorageType);
  
  void nextSort(Player paramPlayer);
  
  List<AuctionItem> getItems(Player paramPlayer);
  
  List<AuctionItem> getItems(Category paramCategory);
  
  List<AuctionItem> getSortItems(Player paramPlayer, Category paramCategory);
  
  void removeAll(Player paramPlayer, StorageType paramStorageType);
  
  void onNPCRequest(Player paramPlayer, String paramString);
  
  boolean isBlacklist(OfflinePlayer paramOfflinePlayer);
  
  void blacklistPlayer(OfflinePlayer paramOfflinePlayer);
  
  void unBlacklistPlayer(OfflinePlayer paramOfflinePlayer);
  
  void openConfiguration(Player paramPlayer, String paramString);
  
  void showHistory(CommandSender paramCommandSender, OfflinePlayer paramOfflinePlayer, int paramInt, HistoryType paramHistoryType);
  
  void createSellInventory(Player paramPlayer, long paramLong, AuctionEconomy paramAuctionEconomy, Command paramCommand);
  
  void removeAdmin(Player paramPlayer, AuctionItem paramAuctionItem, boolean paramBoolean1, boolean paramBoolean2);
  
  void search(Player paramPlayer, String paramString);
  
  List<AuctionItem> getSearch(Player paramPlayer, String paramString);
  
  void setPlayerWord(OfflinePlayer paramOfflinePlayer, String paramString);
  
  String getPlayerWord(OfflinePlayer paramOfflinePlayer);
  
  void open(Player paramPlayer, Command paramCommand, InventoryName paramInventoryName);
  
  void setCurrentCategory(Player paramPlayer, Category paramCategory);
  
  Optional<Category> getCurrentCategory(Player paramPlayer);
  
  boolean auctionHasBlacklistItems(AuctionItem paramAuctionItem);
  
  boolean auctionHasWhitelistItems(AuctionItem paramAuctionItem);
  
  Optional<Priority> getPriority(Permissible paramPermissible);
  
  Optional<Priority> getPriority(int paramInt);
  
  UUID getRandomUniqueId();
  
  List<String> getBannedWorld();
  
  void clearCache();
  
  String getPriceFormat(long paramLong);
  
  List<String> getBannedWorlds();
  
  CacheAuctionItem getCacheAuctionItemList();
  
  void giveAuctionItem(AuctionItem paramAuctionItem, Player paramPlayer);
  
  PlayerCache getCache(OfflinePlayer paramOfflinePlayer);
  
  List<String> getSearchElements();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\AuctionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */