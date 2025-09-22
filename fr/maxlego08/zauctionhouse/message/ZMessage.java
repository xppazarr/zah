/*    */ package fr.maxlego08.zauctionhouse.message;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.MessageType;
/*    */ import fr.maxlego08.zauctionhouse.api.messages.IMessage;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZMessage
/*    */   extends ZTitleMessage
/*    */   implements IMessage
/*    */ {
/*    */   private final Message baseMessage;
/*    */   private String message;
/*    */   private MessageType type;
/*    */   private List<String> messages;
/*    */   
/*    */   public ZMessage(int paramInt1, int paramInt2, int paramInt3, String paramString1, String paramString2, Message paramMessage, String paramString3, MessageType paramMessageType, List<String> paramList) {
/* 30 */     super(paramInt1, paramInt2, paramInt3, paramString1, paramString2);
/* 31 */     this.baseMessage = paramMessage;
/* 32 */     this.message = paramString3;
/* 33 */     this.type = paramMessageType;
/* 34 */     this.messages = (paramList == null) ? new ArrayList<>() : paramList;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getMessage() {
/* 41 */     return this.message;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public MessageType getType() {
/* 48 */     return this.type;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getMessages() {
/* 55 */     return this.messages;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessage(String paramString) {
/* 63 */     this.message = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setType(MessageType paramMessageType) {
/* 71 */     this.type = paramMessageType;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setMessages(List<String> paramList) {
/* 79 */     this.messages = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public Message getDefaultMessage() {
/* 84 */     return this.baseMessage;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\message\ZMessage.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */