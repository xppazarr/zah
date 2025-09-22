/*     */ package fr.maxlego08.zauctionhouse.api.event.events;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.messages.IMessage;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AuctionPreBuyEvent
/*     */   extends CancelledAuctionEvent
/*     */ {
/*     */   private final AuctionEconomy economy;
/*     */   private final Player player;
/*     */   private final AuctionItem auctionItem;
/*     */   private boolean hasEnoughtMoney;
/*     */   private long price;
/*     */   private IMessage buyerMessage;
/*     */   private IMessage sellerMessage;
/*     */   
/*     */   public AuctionPreBuyEvent(AuctionEconomy paramAuctionEconomy, Player paramPlayer, AuctionItem paramAuctionItem, boolean paramBoolean, long paramLong, IMessage paramIMessage1, IMessage paramIMessage2) {
/*  33 */     this.economy = paramAuctionEconomy;
/*  34 */     this.player = paramPlayer;
/*  35 */     this.auctionItem = paramAuctionItem;
/*  36 */     this.hasEnoughtMoney = paramBoolean;
/*  37 */     this.price = paramLong;
/*  38 */     this.buyerMessage = paramIMessage1;
/*  39 */     this.sellerMessage = paramIMessage2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuctionEconomy getEconomy() {
/*  46 */     return this.economy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  53 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuctionItem getAuctionItem() {
/*  60 */     return this.auctionItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isHasEnoughtMoney() {
/*  67 */     return this.hasEnoughtMoney;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPrice() {
/*  74 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHasEnoughtMoney(boolean paramBoolean) {
/*  82 */     this.hasEnoughtMoney = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrice(long paramLong) {
/*  90 */     this.price = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMessage getBuyerMessage() {
/*  97 */     return this.buyerMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public IMessage getSellerMessage() {
/* 104 */     return this.sellerMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setBuyerMessage(IMessage paramIMessage) {
/* 112 */     this.buyerMessage = paramIMessage;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSellerMessage(IMessage paramIMessage) {
/* 120 */     this.sellerMessage = paramIMessage;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionPreBuyEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */