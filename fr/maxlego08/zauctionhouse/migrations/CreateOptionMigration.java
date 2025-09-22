/*   */ package fr.maxlego08.zauctionhouse.migrations;
/*   */ 
/*   */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*   */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*   */ 
/*   */ public class CreateOptionMigration
/*   */   extends Migration {
/*   */   public void up() {
/* 9 */     createOrAlter("%prefix%options", paramSchema -> {
/*   */           paramSchema.uuid("player_id").primary();
/*   */           paramSchema.bool("disable_sell_confirmation");
/*   */           paramSchema.text("sorting");
/*   */         });
/*   */   }
/*   */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\migrations\CreateOptionMigration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */