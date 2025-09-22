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
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class AlterRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public AlterRequest(Schema paramSchema) {
/* 21 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 27 */     StringBuilder stringBuilder = new StringBuilder("ALTER TABLE ");
/* 28 */     stringBuilder.append(this.schema.getTableName()).append(" ");
/*    */     
/* 30 */     ArrayList<String> arrayList = new ArrayList();
/* 31 */     for (ColumnDefinition columnDefinition : this.schema.getColumns()) {
/* 32 */       arrayList.add("ADD COLUMN " + columnDefinition.build(paramDatabaseConfiguration));
/*    */     }
/* 34 */     stringBuilder.append(String.join(", ", (Iterable)arrayList));
/*    */     
/* 36 */     if (!this.schema.getPrimaryKeys().isEmpty()) {
/* 37 */       stringBuilder.append(", PRIMARY KEY (").append(String.join(", ", this.schema.getPrimaryKeys())).append(")");
/*    */     }
/*    */     
/* 40 */     for (String str1 : this.schema.getForeignKeys()) {
/* 41 */       stringBuilder.append(", ADD ").append(str1);
/*    */     }
/*    */     
/* 44 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/* 45 */     if (paramDatabaseConfiguration.isDebug()) {
/* 46 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 49 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 50 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str); 
/* 51 */         try { preparedStatement.execute();
/* 52 */           int i = preparedStatement.getUpdateCount();
/* 53 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 54 */     { sQLException.printStackTrace();
/* 55 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\AlterRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */