/*     */ package fr.maxlego08.zauctionhouse.transaction;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.dto.TransactionDTO;
/*     */ import java.util.UUID;
/*     */ 
/*     */ public class ZTransaction
/*     */   implements Transaction
/*     */ {
/*     */   private final long transactionDate;
/*     */   private final UUID buyer;
/*     */   private final UUID seller;
/*     */   private final String item;
/*     */   private final long price;
/*     */   private final String economy;
/*     */   private int id;
/*     */   private boolean isRead;
/*     */   private boolean needMoney;
/*     */   
/*     */   public ZTransaction(TransactionDTO paramTransactionDTO) {
/*  21 */     this.transactionDate = paramTransactionDTO.getDate();
/*  22 */     this.buyer = paramTransactionDTO.getBuyer();
/*  23 */     this.seller = paramTransactionDTO.getSeller();
/*  24 */     this.item = paramTransactionDTO.getItem();
/*  25 */     this.needMoney = paramTransactionDTO.isNeedMoney();
/*  26 */     this.price = paramTransactionDTO.getPrice();
/*  27 */     this.economy = paramTransactionDTO.getEconomy();
/*  28 */     this.isRead = paramTransactionDTO.isRead();
/*  29 */     this.id = paramTransactionDTO.getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public ZTransaction(UUID paramUUID1, UUID paramUUID2, String paramString1, long paramLong, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
/*  34 */     this.transactionDate = System.currentTimeMillis();
/*  35 */     this.buyer = paramUUID1;
/*  36 */     this.seller = paramUUID2;
/*  37 */     this.item = paramString1;
/*  38 */     this.needMoney = paramBoolean2;
/*  39 */     this.price = paramLong;
/*  40 */     this.economy = paramString2;
/*  41 */     this.isRead = paramBoolean1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ZTransaction(long paramLong1, UUID paramUUID1, UUID paramUUID2, String paramString1, long paramLong2, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
/*  47 */     this.transactionDate = paramLong1;
/*  48 */     this.buyer = paramUUID1;
/*  49 */     this.seller = paramUUID2;
/*  50 */     this.item = paramString1;
/*  51 */     this.needMoney = paramBoolean2;
/*  52 */     this.price = paramLong2;
/*  53 */     this.economy = paramString2;
/*  54 */     this.isRead = paramBoolean1;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getSeller() {
/*  59 */     return this.seller;
/*     */   }
/*     */ 
/*     */   
/*     */   public UUID getBuyer() {
/*  64 */     return this.buyer;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getDate() {
/*  69 */     return this.transactionDate;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getItemStack() {
/*  74 */     return this.item;
/*     */   }
/*     */ 
/*     */   
/*     */   public long getPrice() {
/*  79 */     return this.price;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getEconomy() {
/*  84 */     return this.economy;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isRead() {
/*  89 */     return this.isRead;
/*     */   }
/*     */ 
/*     */   
/*     */   public void read() {
/*  94 */     this.isRead = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needMoney() {
/*  99 */     return this.needMoney;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedMoney(boolean paramBoolean) {
/* 104 */     this.needMoney = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getID() {
/* 109 */     return this.id;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setID(int paramInt) {
/* 114 */     this.id = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     return "ZTransaction [id=" + this.id + ", transactionDate=" + this.transactionDate + ", buyer=" + this.buyer + ", seller=" + this.seller + ", item=" + this.item + ", price=" + this.price + ", economy=" + this.economy + ", isRead=" + this.isRead + ", needMoney=" + this.needMoney + "]";
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\transaction\ZTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */