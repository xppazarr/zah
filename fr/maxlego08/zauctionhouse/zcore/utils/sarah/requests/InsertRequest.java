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
/*    */ import java.sql.ResultSet;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class InsertRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public InsertRequest(Schema paramSchema) {
/* 23 */     this.schema = paramSchema;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 29 */     StringBuilder stringBuilder1 = new StringBuilder("INSERT INTO " + this.schema.getTableName() + " (");
/* 30 */     StringBuilder stringBuilder2 = new StringBuilder("VALUES (");
/*    */     
/* 32 */     ArrayList<Object> arrayList = new ArrayList();
/*    */     
/* 34 */     for (byte b = 0; b < this.schema.getColumns().size(); b++) {
/* 35 */       ColumnDefinition columnDefinition = this.schema.getColumns().get(b);
/* 36 */       stringBuilder1.append((b > 0) ? ", " : "").append(columnDefinition.getSafeName());
/* 37 */       stringBuilder2.append((b > 0) ? ", " : "").append("?");
/* 38 */       arrayList.add(columnDefinition.getObject());
/*    */     } 
/*    */     
/* 41 */     stringBuilder1.append(") ");
/* 42 */     stringBuilder2.append(")");
/* 43 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder1 + stringBuilder2.toString());
/*    */     
/* 45 */     if (paramDatabaseConfiguration.isDebug()) {
/* 46 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 49 */     try { Connection connection = paramDatabaseConnection.getConnection(); try { PreparedStatement preparedStatement = connection.prepareStatement(str, 1);
/*    */         
/* 51 */         try { for (byte b1 = 0; b1 < arrayList.size(); b1++) {
/* 52 */             preparedStatement.setObject(b1 + 1, arrayList.get(b1));
/*    */           }
/* 54 */           preparedStatement.executeUpdate();
/*    */           
/* 56 */           try { ResultSet resultSet = preparedStatement.getGeneratedKeys(); 
/* 57 */             try { if (resultSet.next())
/* 58 */               { int i = resultSet.getInt(1);
/*    */ 
/*    */ 
/*    */                 
/* 62 */                 if (resultSet != null) resultSet.close();
/*    */ 
/*    */                 
/* 65 */                 if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; }  boolean bool = false; if (resultSet != null) resultSet.close();  if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return bool; } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Exception exception) { byte b2 = -1; if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return b2; }  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 66 */     { sQLException.printStackTrace();
/* 67 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\InsertRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */