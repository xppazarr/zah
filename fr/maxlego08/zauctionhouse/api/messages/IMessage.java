package fr.maxlego08.zauctionhouse.api.messages;

import fr.maxlego08.zauctionhouse.api.enums.Message;
import fr.maxlego08.zauctionhouse.api.enums.MessageType;
import java.util.List;

public interface IMessage extends ITitleMessage {
  Message getDefaultMessage();
  
  List<String> getMessages();
  
  String getMessage();
  
  void setMessage(String paramString);
  
  void setMessages(List<String> paramList);
  
  MessageType getType();
  
  void setType(MessageType paramMessageType);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\messages\IMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */