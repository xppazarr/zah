/*    */ package fr.maxlego08.zauctionhouse.dto;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.Column;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionItemDTO
/*    */ {
/*    */   @Column("id")
/*    */   private final UUID id;
/*    */   @Column("itemstack")
/*    */   private final String itemStack;
/*    */   @Column("price")
/*    */   private final long price;
/*    */   @Column("seller")
/*    */   private final UUID seller;
/*    */   @Column("buyer")
/*    */   private final UUID buyer;
/*    */   @Column("economy")
/*    */   private final String economy;
/*    */   @Column("auction_type")
/*    */   private final String auctionType;
/*    */   @Column("expire_at")
/*    */   private final long expireAt;
/*    */   @Column("storage_type")
/*    */   private final String storageType;
/*    */   @Column("sellerName")
/*    */   private final String sellerName;
/*    */   @Column("server_name")
/*    */   private final String serverName;
/*    */   @Column("priority")
/*    */   private final int priority;
/*    */   
/*    */   public AuctionItemDTO(UUID paramUUID1, String paramString1, long paramLong1, UUID paramUUID2, UUID paramUUID3, String paramString2, String paramString3, long paramLong2, String paramString4, String paramString5, String paramString6, int paramInt) {
/* 36 */     this.id = paramUUID1;
/* 37 */     this.itemStack = paramString1;
/* 38 */     this.price = paramLong1;
/* 39 */     this.seller = paramUUID2;
/* 40 */     this.buyer = paramUUID3;
/* 41 */     this.economy = paramString2;
/* 42 */     this.auctionType = paramString3;
/* 43 */     this.expireAt = paramLong2;
/* 44 */     this.storageType = paramString4;
/* 45 */     this.sellerName = paramString5;
/* 46 */     this.serverName = paramString6;
/* 47 */     this.priority = paramInt;
/*    */   }
/*    */   
/*    */   public UUID getId() {
/* 51 */     return this.id;
/*    */   }
/*    */   
/*    */   public String getItemStack() {
/* 55 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public long getPrice() {
/* 59 */     return this.price;
/*    */   }
/*    */   
/*    */   public UUID getSeller() {
/* 63 */     return this.seller;
/*    */   }
/*    */   
/*    */   public UUID getBuyer() {
/* 67 */     return this.buyer;
/*    */   }
/*    */   
/*    */   public String getEconomy() {
/* 71 */     return this.economy;
/*    */   }
/*    */   
/*    */   public String getAuctionType() {
/* 75 */     return this.auctionType;
/*    */   }
/*    */   
/*    */   public long getExpireAt() {
/* 79 */     return this.expireAt;
/*    */   }
/*    */   
/*    */   public String getStorageType() {
/* 83 */     return this.storageType;
/*    */   }
/*    */   
/*    */   public String getSellerName() {
/* 87 */     return this.sellerName;
/*    */   }
/*    */   
/*    */   public String getServerName() {
/* 91 */     return this.serverName;
/*    */   }
/*    */   
/*    */   public int getPriority() {
/* 95 */     return this.priority;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dto\AuctionItemDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */