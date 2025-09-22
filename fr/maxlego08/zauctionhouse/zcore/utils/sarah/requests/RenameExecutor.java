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
/*    */ public class RenameExecutor
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public RenameExecutor(Schema paramSchema) {
/* 18 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 24 */     StringBuilder stringBuilder = new StringBuilder("ALTER TABLE ");
/* 25 */     stringBuilder.append(this.schema.getTableName());
/* 26 */     stringBuilder.append(" RENAME TO ");
/* 27 */     stringBuilder.append(this.schema.getNewTableName());
/*    */     
/* 29 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/* 30 */     if (paramDatabaseConfiguration.isDebug()) {
/* 31 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 34 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 35 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str); 
/* 36 */         try { preparedStatement.execute();
/* 37 */           int i = preparedStatement.getUpdateCount();
/* 38 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 39 */     { sQLException.printStackTrace();
/* 40 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\RenameExecutor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */