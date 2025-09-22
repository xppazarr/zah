package fr.maxlego08.zauctionhouse.zcore.utils.currencies;

import java.math.BigDecimal;
import org.bukkit.OfflinePlayer;

public interface CurrencyProvider {
  void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString);
  
  void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString);
  
  BigDecimal getBalance(OfflinePlayer paramOfflinePlayer);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\CurrencyProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */