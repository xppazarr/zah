/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.JoinCondition;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Executor;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.List;
/*    */ 
/*    */ public class UpdateBatchRequest
/*    */   implements Executor
/*    */ {
/*    */   private final List<Schema> schemas;
/*    */   
/*    */   public UpdateBatchRequest(List<Schema> paramList) {
/* 21 */     this.schemas = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 26 */     if (this.schemas.isEmpty()) return 0;
/*    */     
/* 28 */     Schema schema = this.schemas.get(0);
/* 29 */     StringBuilder stringBuilder = new StringBuilder("UPDATE " + schema.getTableName());
/*    */     
/* 31 */     if (!schema.getJoinConditions().isEmpty()) {
/* 32 */       for (JoinCondition joinCondition : schema.getJoinConditions()) {
/* 33 */         stringBuilder.append(" ").append(joinCondition.getJoinClause());
/*    */       }
/*    */     }
/*    */     
/* 37 */     stringBuilder.append(" SET ");
/*    */     
/* 39 */     List<ColumnDefinition> list = schema.getColumns();
/* 40 */     for (byte b = 0; b < list.size(); b++) {
/* 41 */       ColumnDefinition columnDefinition = list.get(b);
/* 42 */       stringBuilder.append((b > 0) ? ", " : "").append(columnDefinition.getSafeName()).append(" = ?");
/*    */     } 
/*    */     
/* 45 */     schema.whereConditions(stringBuilder);
/* 46 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/*    */     
/* 48 */     if (paramDatabaseConfiguration.isDebug()) {
/* 49 */       paramLogger.info("Executing SQL Batch: " + str);
/*    */     }
/*    */     
/* 52 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 53 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str);
/*    */         
/* 55 */         try { for (Schema schema1 : this.schemas) {
/* 56 */             List<ColumnDefinition> list1 = schema1.getColumns();
/* 57 */             for (byte b1 = 0; b1 < list1.size(); b1++) {
/* 58 */               preparedStatement.setObject(b1 + 1, ((ColumnDefinition)list1.get(b1)).getObject());
/*    */             }
/* 60 */             schema1.applyWhereConditions(preparedStatement, list1.size() + 1);
/* 61 */             preparedStatement.addBatch();
/*    */           } 
/*    */           
/* 64 */           int[] arrayOfInt = preparedStatement.executeBatch();
/* 65 */           int i = 0;
/* 66 */           for (int k : arrayOfInt) {
/* 67 */             i += k;
/*    */           }
/* 69 */           int j = i;
/*    */           
/* 71 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return j; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 72 */     { sQLException.printStackTrace();
/* 73 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\UpdateBatchRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */