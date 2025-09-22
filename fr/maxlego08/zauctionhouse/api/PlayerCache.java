/*     */ package fr.maxlego08.zauctionhouse.api;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Sorting;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ 
/*     */ public class PlayerCache
/*     */ {
/*     */   private AuctionItem auctionItem;
/*     */   private boolean lastUpdate;
/*     */   private Category category;
/*     */   private long sellPrice;
/*     */   private int sellAmount;
/*     */   private AuctionEconomy economy;
/*     */   private String searchWord;
/*  17 */   private Sorting sorting = AuctionConfiguration.defaultSort;
/*     */ 
/*     */   
/*     */   private boolean disableSellConfirmation;
/*     */ 
/*     */   
/*     */   public long getSellPrice() {
/*  24 */     return this.sellPrice;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSellPrice(long paramLong) {
/*  31 */     this.sellPrice = paramLong;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuctionEconomy getEconomy() {
/*  38 */     return this.economy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEconomy(AuctionEconomy paramAuctionEconomy) {
/*  45 */     this.economy = paramAuctionEconomy;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AuctionItem getAuctionItem() {
/*  52 */     return this.auctionItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAuctionItem(AuctionItem paramAuctionItem) {
/*  59 */     this.auctionItem = paramAuctionItem;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isLastUpdate() {
/*  66 */     return this.lastUpdate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setLastUpdate(boolean paramBoolean) {
/*  73 */     this.lastUpdate = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Category getCategory() {
/*  80 */     return this.category;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCategory(Category paramCategory) {
/*  87 */     this.category = paramCategory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSellAmount() {
/*  94 */     return this.sellAmount;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSellAmount(int paramInt) {
/* 101 */     this.sellAmount = paramInt;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSearchWord() {
/* 109 */     return this.searchWord;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSearchWord(String paramString) {
/* 116 */     this.searchWord = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Sorting getSorting() {
/* 123 */     return this.sorting;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSorting(Sorting paramSorting) {
/* 130 */     this.sorting = paramSorting;
/*     */   }
/*     */   
/*     */   public boolean isDisableSellConfirmation() {
/* 134 */     return this.disableSellConfirmation;
/*     */   }
/*     */   
/*     */   public void setDisableSellConfirmation(boolean paramBoolean) {
/* 138 */     this.disableSellConfirmation = paramBoolean;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\PlayerCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */