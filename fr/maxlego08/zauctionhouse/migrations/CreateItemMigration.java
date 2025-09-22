/*    */ package fr.maxlego08.zauctionhouse.migrations;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ 
/*    */ public class CreateItemMigration
/*    */   extends Migration
/*    */ {
/*    */   public void up() {
/* 10 */     create("%prefix%items", paramSchema -> {
/*    */           paramSchema.string("id", 36).primary();
/*    */           paramSchema.longText("itemstack");
/*    */           paramSchema.bigInt("price");
/*    */           paramSchema.string("seller", 36);
/*    */           paramSchema.string("buyer", 36).nullable();
/*    */           paramSchema.string("economy", 255);
/*    */           paramSchema.string("auction_type", 32);
/*    */           paramSchema.bigInt("expire_at");
/*    */           paramSchema.string("storage_type", 32);
/*    */           paramSchema.string("sellerName", 36);
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\migrations\CreateItemMigration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */