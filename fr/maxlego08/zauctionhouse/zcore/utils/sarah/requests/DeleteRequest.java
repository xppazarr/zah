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
/*    */ public class DeleteRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schemaBuilder;
/*    */   
/*    */   public DeleteRequest(Schema paramSchema) {
/* 18 */     this.schemaBuilder = paramSchema;
/*    */   }
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 23 */     StringBuilder stringBuilder = (new StringBuilder("DELETE FROM ")).append(this.schemaBuilder.getTableName());
/* 24 */     this.schemaBuilder.whereConditions(stringBuilder);
/*    */     
/* 26 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/* 27 */     if (paramDatabaseConfiguration.isDebug()) {
/* 28 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 31 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 32 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str); 
/* 33 */         try { this.schemaBuilder.applyWhereConditions(preparedStatement, 1);
/* 34 */           int i = preparedStatement.executeUpdate();
/* 35 */           int j = i;
/* 36 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return j; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 37 */     { sQLException.printStackTrace();
/* 38 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\DeleteRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */