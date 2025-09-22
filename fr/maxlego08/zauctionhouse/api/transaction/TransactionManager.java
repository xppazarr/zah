package fr.maxlego08.zauctionhouse.api.transaction;

import fr.maxlego08.zauctionhouse.api.AuctionItem;
import fr.maxlego08.zauctionhouse.api.enums.HistoryType;
import java.util.List;
import java.util.function.Consumer;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface TransactionManager {
  void storeTransaction(AuctionItem paramAuctionItem, Consumer<Transaction> paramConsumer, long paramLong);
  
  List<Transaction> getTransactions(OfflinePlayer paramOfflinePlayer, HistoryType paramHistoryType);
  
  List<Transaction> getMoneyTransactions(OfflinePlayer paramOfflinePlayer);
  
  boolean needMoney(OfflinePlayer paramOfflinePlayer);
  
  long countUnRead(OfflinePlayer paramOfflinePlayer);
  
  void updateUnRead(OfflinePlayer paramOfflinePlayer);
  
  void claimMoney(Player paramPlayer);
  
  long getMoney(Player paramPlayer, String paramString);
  
  long getMoneyFromDatabase(Player paramPlayer, String paramString);
  
  List<Transaction> getMoneyTransactions(OfflinePlayer paramOfflinePlayer, String paramString);
  
  void purge(CommandSender paramCommandSender, long paramLong);
  
  void sendUnreadWithSelect(Player paramPlayer);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\transaction\TransactionManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */