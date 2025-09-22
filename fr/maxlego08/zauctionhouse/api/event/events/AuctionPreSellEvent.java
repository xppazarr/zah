/*     */ package fr.maxlego08.zauctionhouse.api.event.events;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
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
/*     */ public class AuctionPreSellEvent
/*     */   extends CancelledAuctionEvent
/*     */ {
/*     */   private final Player player;
/*     */   private final int amount;
/*     */   private final UUID auctionItemUniqueId;
/*     */   private long expiredAt;
/*     */   private AuctionEconomy economy;
/*     */   private long price;
/*     */   private ItemStack itemStack;
/*     */   
/*     */   public AuctionPreSellEvent(Player paramPlayer, int paramInt, UUID paramUUID, long paramLong1, AuctionEconomy paramAuctionEconomy, long paramLong2, ItemStack paramItemStack) {
/*  33 */     this.player = paramPlayer;
/*  34 */     this.amount = paramInt;
/*  35 */     this.auctionItemUniqueId = paramUUID;
/*  36 */     this.expiredAt = paramLong1;
/*  37 */     this.economy = paramAuctionEconomy;
/*  38 */     this.price = paramLong2;
/*  39 */     this.itemStack = paramItemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/*  46 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmount() {
/*  53 */     return this.amount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public UUID getAuctionItemUniqueId() {
/*  60 */     return this.auctionItemUniqueId;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getExpiredAt() {
/*  67 */     return this.expiredAt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuctionEconomy getEconomy() {
/*  74 */     return this.economy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long getPrice() {
/*  81 */     return this.price;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/*  88 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setExpiredAt(long paramLong) {
/*  96 */     this.expiredAt = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEconomy(AuctionEconomy paramAuctionEconomy) {
/* 104 */     this.economy = paramAuctionEconomy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setPrice(long paramLong) {
/* 112 */     this.price = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setItemStack(ItemStack paramItemStack) {
/* 120 */     this.itemStack = paramItemStack;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionPreSellEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */