package fr.maxlego08.zauctionhouse.api.storage;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
import fr.maxlego08.zauctionhouse.api.PlayerCache;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;
import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public interface IStorage {
  void load(StorageManager paramStorageManager, AuctionPlugin paramAuctionPlugin);
  
  void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  void saveItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType, Consumer<AuctionItem> paramConsumer);
  
  void removeItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  void saveItems(AuctionPlugin paramAuctionPlugin, List<AuctionItem> paramList, StorageType paramStorageType);
  
  void update(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType);
  
  List<AuctionItem> getItems(AuctionPlugin paramAuctionPlugin, StorageType paramStorageType);
  
  void save(AuctionPlugin paramAuctionPlugin);
  
  void storeTransaction(Transaction paramTransaction, Consumer<Transaction> paramConsumer);
  
  void saveTransactions(List<Transaction> paramList);
  
  void updateItem(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  List<Transaction> getTransactions();
  
  void updateTransaction(List<Transaction> paramList);
  
  void purgeTransactions(long paramLong, Runnable paramRunnable);
  
  void fetchClaimMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer);
  
  void fetchUnreadMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer);
  
  boolean isCooldown(UUID paramUUID);
  
  boolean uuidExist(UUID paramUUID);
  
  String getServerName();
  
  long fetchClaimMoneySync(UUID paramUUID, String paramString);
  
  void checkIsItemIsValid(Player paramPlayer, AuctionItem paramAuctionItem, Consumer<Boolean> paramConsumer);
  
  void storeRedisItem(StorageType paramStorageType, AuctionItem paramAuctionItem);
  
  void updateRedisItem(AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  void upsertPlayerOptions(UUID paramUUID);
  
  PlayerCache getCache(OfflinePlayer paramOfflinePlayer);
  
  void removePlayerCache(UUID paramUUID);
  
  void loadPlayerCache(UUID paramUUID);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\IStorage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */