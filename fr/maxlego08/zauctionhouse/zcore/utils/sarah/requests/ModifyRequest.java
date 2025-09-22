/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.SchemaBuilder;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Executor;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.SchemaType;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class ModifyRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public ModifyRequest(Schema paramSchema) {
/* 18 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 24 */     String str = this.schema.getTableName() + "_tmp";
/* 25 */     Schema schema = SchemaBuilder.copy(str, SchemaType.CREATE, this.schema);
/*    */     
/*    */     try {
/* 28 */       schema.execute(paramDatabaseConnection, paramLogger);
/* 29 */     } catch (SQLException sQLException) {
/* 30 */       sQLException.printStackTrace();
/* 31 */       return -1;
/*    */     } 
/*    */     
/* 34 */     InsertAllRequest insertAllRequest = new InsertAllRequest(this.schema, str);
/* 35 */     insertAllRequest.execute(paramDatabaseConnection, paramDatabaseConfiguration, paramLogger);
/*    */     
/* 37 */     DropTableRequest dropTableRequest = new DropTableRequest(this.schema);
/* 38 */     dropTableRequest.execute(paramDatabaseConnection, paramDatabaseConfiguration, paramLogger);
/*    */     
/* 40 */     RenameExecutor renameExecutor = new RenameExecutor(SchemaBuilder.rename(str, this.schema.getTableName()));
/* 41 */     renameExecutor.execute(paramDatabaseConnection, paramDatabaseConfiguration, paramLogger);
/*    */     
/* 43 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\ModifyRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */