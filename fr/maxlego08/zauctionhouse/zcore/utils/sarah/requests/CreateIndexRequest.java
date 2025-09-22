/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Executor;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class CreateIndexRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public CreateIndexRequest(Schema paramSchema) {
/* 19 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 25 */     StringBuilder stringBuilder = new StringBuilder("CREATE INDEX ");
/* 26 */     String str1 = this.schema.getTableName();
/* 27 */     ColumnDefinition columnDefinition = this.schema.getColumns().get(0);
/* 28 */     String str2 = "idx_" + str1 + "_" + columnDefinition.getName();
/*    */     
/* 30 */     stringBuilder.append(str2);
/* 31 */     stringBuilder.append(" ON ");
/* 32 */     stringBuilder.append(String.format("`%s`", new Object[] { str1 }));
/* 33 */     stringBuilder.append(" (");
/* 34 */     stringBuilder.append(columnDefinition.getSafeName());
/* 35 */     stringBuilder.append(" )");
/*    */     
/* 37 */     String str3 = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/* 38 */     if (paramDatabaseConfiguration.isDebug()) {
/* 39 */       paramLogger.info("Executing SQL: " + str3);
/*    */     }
/*    */     
/* 42 */     try { Connection connection = paramDatabaseConnection.getConnection(); try { PreparedStatement preparedStatement = connection.prepareStatement(str3); 
/* 43 */         try { preparedStatement.execute();
/* 44 */           int i = preparedStatement.getUpdateCount();
/* 45 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 46 */     { sQLException.printStackTrace();
/* 47 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\CreateIndexRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */