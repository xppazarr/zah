package fr.maxlego08.zauctionhouse.api.storage;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
import fr.maxlego08.zauctionhouse.api.PlayerCache;
import fr.maxlego08.zauctionhouse.api.enums.StorageType;
import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
import java.sql.Connection;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import org.bukkit.entity.Player;

public interface IConnection {
  Connection getConnection();
  
  RequestHelper getRequestHelper();
  
  void asyncInsert(AuctionItem paramAuctionItem, StorageType paramStorageType, String paramString, Consumer<AuctionItem> paramConsumer);
  
  void asyncDelete(AuctionItem paramAuctionItem, StorageType paramStorageType, Runnable paramRunnable);
  
  void selectItems(AuctionPlugin paramAuctionPlugin, IStorage paramIStorage, StorageManager paramStorageManager);
  
  void asyncInsert(Transaction paramTransaction, Consumer<Transaction> paramConsumer);
  
  void selectTransactions(AuctionPlugin paramAuctionPlugin, IStorage paramIStorage, StorageManager paramStorageManager);
  
  void asyncUpdate(AuctionItem paramAuctionItem, StorageType paramStorageType);
  
  void asyncUpdate(List<Transaction> paramList);
  
  void getAndRefreshConnection(Runnable paramRunnable);
  
  void asyncDelete(Transaction paramTransaction);
  
  void fetchClaimMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer);
  
  void fetchUnreadMoney(Player paramPlayer, Consumer<List<Transaction>> paramConsumer);
  
  long fetchClaimMoneySync(UUID paramUUID, String paramString);
  
  void asyncSelect(UUID paramUUID, Consumer<PlayerCache> paramConsumer);
  
  void asyncUpsert(UUID paramUUID, PlayerCache paramPlayerCache);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\IConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */