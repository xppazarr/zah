package fr.maxlego08.zauctionhouse.api.transaction;

import java.util.UUID;

public interface Transaction {
  UUID getSeller();
  
  UUID getBuyer();
  
  long getDate();
  
  String getItemStack();
  
  long getPrice();
  
  String getEconomy();
  
  boolean isRead();
  
  void read();
  
  boolean needMoney();
  
  void setNeedMoney(boolean paramBoolean);
  
  int getID();
  
  void setID(int paramInt);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\transaction\Transaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */