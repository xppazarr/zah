/*    */ package fr.maxlego08.zauctionhouse.dto;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.Column;
/*    */ import java.util.UUID;
/*    */ 
/*    */ 
/*    */ public class TransactionDTO
/*    */ {
/*    */   @Column("id")
/*    */   private final int id;
/*    */   @Column("seller")
/*    */   private final UUID seller;
/*    */   @Column("buyer")
/*    */   private final UUID buyer;
/*    */   @Column("itemstack")
/*    */   private final String item;
/*    */   @Column("price")
/*    */   private final long price;
/*    */   @Column("transaction_date")
/*    */   private final long date;
/*    */   @Column("economy")
/*    */   private final String economy;
/*    */   @Column("is_read")
/*    */   private final boolean isRead;
/*    */   @Column("need_money")
/*    */   private final boolean needMoney;
/*    */   
/*    */   public TransactionDTO(int paramInt, UUID paramUUID1, UUID paramUUID2, String paramString1, long paramLong1, long paramLong2, String paramString2, boolean paramBoolean1, boolean paramBoolean2) {
/* 29 */     this.id = paramInt;
/* 30 */     this.seller = paramUUID1;
/* 31 */     this.buyer = paramUUID2;
/* 32 */     this.item = paramString1;
/* 33 */     this.price = paramLong1;
/* 34 */     this.date = paramLong2;
/* 35 */     this.economy = paramString2;
/* 36 */     this.isRead = paramBoolean1;
/* 37 */     this.needMoney = paramBoolean2;
/*    */   }
/*    */   
/*    */   public long getDate() {
/* 41 */     return this.date;
/*    */   }
/*    */   
/*    */   public int getId() {
/* 45 */     return this.id;
/*    */   }
/*    */   
/*    */   public UUID getSeller() {
/* 49 */     return this.seller;
/*    */   }
/*    */   
/*    */   public UUID getBuyer() {
/* 53 */     return this.buyer;
/*    */   }
/*    */   
/*    */   public String getItem() {
/* 57 */     return this.item;
/*    */   }
/*    */   
/*    */   public long getPrice() {
/* 61 */     return this.price;
/*    */   }
/*    */   
/*    */   public String getEconomy() {
/* 65 */     return this.economy;
/*    */   }
/*    */   
/*    */   public boolean isRead() {
/* 69 */     return this.isRead;
/*    */   }
/*    */   
/*    */   public boolean isNeedMoney() {
/* 73 */     return this.needMoney;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dto\TransactionDTO.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */