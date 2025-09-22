/*    */ package fr.maxlego08.zauctionhouse.migrations;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ 
/*    */ public class CreateTransactionMigration
/*    */   extends Migration
/*    */ {
/*    */   public void up() {
/* 10 */     create("%prefix%transactions", paramSchema -> {
/*    */           paramSchema.autoIncrement("id");
/*    */           paramSchema.string("seller", 36);
/*    */           paramSchema.string("buyer", 36);
/*    */           paramSchema.longText("itemstack");
/*    */           paramSchema.bigInt("transaction_date");
/*    */           paramSchema.bigInt("price");
/*    */           paramSchema.string("economy", 32);
/*    */           paramSchema.bool("is_read");
/*    */           paramSchema.bool("need_money");
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\migrations\CreateTransactionMigration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */