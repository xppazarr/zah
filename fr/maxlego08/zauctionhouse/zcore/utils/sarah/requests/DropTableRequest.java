/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Executor;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ 
/*    */ public class DropTableRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public DropTableRequest(Schema paramSchema) {
/* 18 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 23 */     String str1 = this.schema.getTableName();
/* 24 */     if (str1 == null || str1.trim().isEmpty()) {
/* 25 */       paramLogger.info("Invalid table name.");
/* 26 */       return -1;
/*    */     } 
/*    */     
/* 29 */     String str2 = paramDatabaseConfiguration.replacePrefix("DROP TABLE IF EXISTS " + str1);
/* 30 */     if (paramDatabaseConfiguration.isDebug()) {
/* 31 */       paramLogger.info("Executing SQL: " + str2);
/*    */     }
/*    */     
/* 34 */     try { Connection connection = paramDatabaseConnection.getConnection(); try { PreparedStatement preparedStatement = connection.prepareStatement(str2);
/*    */         
/* 36 */         try { preparedStatement.execute();
/* 37 */           boolean bool = false;
/* 38 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return bool; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 39 */     { paramLogger.info("Error while executing SQL query: " + sQLException.getMessage());
/* 40 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\DropTableRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */