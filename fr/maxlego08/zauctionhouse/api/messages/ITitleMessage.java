package fr.maxlego08.zauctionhouse.api.messages;

public interface ITitleMessage {
  int getEnd();
  
  int getStart();
  
  String getSubTitle();
  
  String getTitle();
  
  int getTime();
  
  void setEnd(int paramInt);
  
  void setStart(int paramInt);
  
  void setSubTitle(String paramString);
  
  void setTitle(String paramString);
  
  void setTime(int paramInt);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\messages\ITitleMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */