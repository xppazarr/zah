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
/*    */ public class InsertAllRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   private final String toTableName;
/*    */   
/*    */   public InsertAllRequest(Schema paramSchema, String paramString) {
/* 20 */     this.schema = paramSchema;
/* 21 */     this.toTableName = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 27 */     StringBuilder stringBuilder1 = new StringBuilder("INSERT INTO " + this.toTableName + " (");
/* 28 */     StringBuilder stringBuilder2 = new StringBuilder();
/*    */     
/* 30 */     int i = this.schema.getColumns().size();
/* 31 */     for (byte b = 0; b < i; b++) {
/*    */       
/* 33 */       ColumnDefinition columnDefinition = this.schema.getColumns().get(b);
/*    */       
/* 35 */       stringBuilder2.append(columnDefinition.getSafeName());
/* 36 */       if (b < i - 1) {
/* 37 */         stringBuilder2.append(",");
/*    */       }
/*    */     } 
/*    */     
/* 41 */     stringBuilder1.append(stringBuilder2).append(") ");
/*    */     
/* 43 */     stringBuilder1.append("SELECT ").append(stringBuilder2);
/* 44 */     stringBuilder1.append(" FROM ");
/* 45 */     stringBuilder1.append(this.schema.getTableName());
/*    */     
/* 47 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder1.toString());
/*    */     
/* 49 */     if (paramDatabaseConfiguration.isDebug()) {
/* 50 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 53 */     try { Connection connection = paramDatabaseConnection.getConnection(); try { PreparedStatement preparedStatement = connection.prepareStatement(str); 
/* 54 */         try { preparedStatement.executeUpdate();
/* 55 */           if (preparedStatement != null) preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (connection != null) connection.close();  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 56 */     { sQLException.printStackTrace();
/* 57 */       return -1; }
/*    */ 
/*    */     
/* 60 */     return 0;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\InsertAllRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */