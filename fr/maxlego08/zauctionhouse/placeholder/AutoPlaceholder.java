/*    */ package fr.maxlego08.zauctionhouse.placeholder;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ 
/*    */ public class AutoPlaceholder
/*    */ {
/*    */   private final String startWith;
/*    */   private final ReturnBiConsumer<Player, String, String> biConsumer;
/*    */   private final ReturnConsumer<Player, String> consumer;
/*    */   
/*    */   public AutoPlaceholder(String paramString, ReturnBiConsumer<Player, String, String> paramReturnBiConsumer) {
/* 13 */     this.startWith = paramString;
/* 14 */     this.biConsumer = paramReturnBiConsumer;
/* 15 */     this.consumer = null;
/*    */   }
/*    */   
/*    */   public AutoPlaceholder(String paramString, ReturnConsumer<Player, String> paramReturnConsumer) {
/* 19 */     this.startWith = paramString;
/* 20 */     this.biConsumer = null;
/* 21 */     this.consumer = paramReturnConsumer;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getStartWith() {
/* 28 */     return this.startWith;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ReturnBiConsumer<Player, String, String> getBiConsumer() {
/* 35 */     return this.biConsumer;
/*    */   }
/*    */   
/*    */   public ReturnConsumer<Player, String> getConsumer() {
/* 39 */     return this.consumer;
/*    */   }
/*    */   
/*    */   public String accept(Player paramPlayer, String paramString) {
/* 43 */     if (this.consumer != null) return this.consumer.accept(paramPlayer); 
/* 44 */     if (this.biConsumer != null) return this.biConsumer.accept(paramPlayer, paramString); 
/* 45 */     return "Error with consumer !";
/*    */   }
/*    */   
/*    */   public boolean startsWith(String paramString) {
/* 49 */     return (this.consumer != null) ? this.startWith.equalsIgnoreCase(paramString) : paramString.startsWith(this.startWith);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\placeholder\AutoPlaceholder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */