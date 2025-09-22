/*    */ package fr.maxlego08.zauctionhouse.migrations;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.SchemaBuilder;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ 
/*    */ public class UpdateItemAddServerNameMigration
/*    */   extends Migration {
/*    */   public void up() {
/* 10 */     SchemaBuilder.alter(this, "%prefix%items", paramSchema -> paramSchema.string("server_name", 255).nullable());
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\migrations\UpdateItemAddServerNameMigration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */