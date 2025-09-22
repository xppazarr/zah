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
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ public class UpdateRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public UpdateRequest(Schema paramSchema) {
/* 22 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 28 */     StringBuilder stringBuilder = new StringBuilder("UPDATE " + this.schema.getTableName());
/*    */     
/* 30 */     if (!this.schema.getJoinConditions().isEmpty()) {
/* 31 */       for (JoinCondition joinCondition : this.schema.getJoinConditions()) {
/* 32 */         stringBuilder.append(" ").append(joinCondition.getJoinClause());
/*    */       }
/*    */     }
/*    */     
/* 36 */     stringBuilder.append(" SET ");
/*    */     
/* 38 */     ArrayList<Object> arrayList = new ArrayList();
/*    */     
/* 40 */     for (byte b = 0; b < this.schema.getColumns().size(); b++) {
/* 41 */       ColumnDefinition columnDefinition = this.schema.getColumns().get(b);
/* 42 */       stringBuilder.append((b > 0) ? ", " : "").append(columnDefinition.getSafeName()).append(" = ?");
/* 43 */       arrayList.add(columnDefinition.getObject());
/*    */     } 
/*    */     
/* 46 */     this.schema.whereConditions(stringBuilder);
/* 47 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder.toString());
/*    */     
/* 49 */     if (paramDatabaseConfiguration.isDebug()) {
/* 50 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 53 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 54 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str); 
/* 55 */         try { int i; for (i = 0; i < arrayList.size(); i++) {
/* 56 */             preparedStatement.setObject(i + 1, arrayList.get(i));
/*    */           }
/* 58 */           this.schema.applyWhereConditions(preparedStatement, arrayList.size() + 1);
/* 59 */           preparedStatement.executeUpdate();
/* 60 */           i = preparedStatement.getUpdateCount();
/* 61 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 62 */     { sQLException.printStackTrace();
/* 63 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\UpdateRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */