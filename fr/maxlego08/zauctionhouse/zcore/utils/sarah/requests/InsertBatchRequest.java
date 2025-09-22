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
/*    */ import java.util.List;
/*    */ 
/*    */ 
/*    */ public class InsertBatchRequest
/*    */   implements Executor
/*    */ {
/*    */   private final List<Schema> schemas;
/*    */   
/*    */   public InsertBatchRequest(List<Schema> paramList) {
/* 23 */     this.schemas = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 28 */     if (this.schemas.isEmpty()) {
/* 29 */       return 0;
/*    */     }
/*    */     
/* 32 */     Schema schema = this.schemas.get(0);
/* 33 */     StringBuilder stringBuilder1 = new StringBuilder("INSERT INTO " + schema.getTableName() + " (");
/* 34 */     StringBuilder stringBuilder2 = new StringBuilder("VALUES ");
/*    */     
/* 36 */     ArrayList<Object> arrayList = new ArrayList();
/* 37 */     ArrayList<String> arrayList1 = new ArrayList();
/* 38 */     ArrayList<String> arrayList2 = new ArrayList();
/*    */     
/* 40 */     for (ColumnDefinition columnDefinition : schema.getColumns()) {
/* 41 */       arrayList2.add(columnDefinition.getSafeName());
/*    */     }
/*    */     
/* 44 */     stringBuilder1.append(String.join(", ", (Iterable)arrayList2)).append(") ");
/*    */     
/* 46 */     for (Schema schema1 : this.schemas) {
/* 47 */       ArrayList<String> arrayList3 = new ArrayList();
/* 48 */       for (ColumnDefinition columnDefinition : schema1.getColumns()) {
/* 49 */         arrayList3.add("?");
/* 50 */         arrayList.add(columnDefinition.getObject());
/*    */       } 
/* 52 */       arrayList1.add("(" + String.join(", ", (Iterable)arrayList3) + ")");
/*    */     } 
/*    */     
/* 55 */     stringBuilder2.append(String.join(", ", (Iterable)arrayList1));
/* 56 */     stringBuilder1.append(stringBuilder2);
/*    */     
/* 58 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder1.toString());
/* 59 */     if (paramDatabaseConfiguration.isDebug()) {
/* 60 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 63 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 64 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str, 1);
/*    */         
/* 66 */         try { byte b = 1;
/* 67 */           for (Object object : arrayList) {
/* 68 */             preparedStatement.setObject(b++, object);
/*    */           }
/*    */           
/* 71 */           int i = preparedStatement.executeUpdate();
/*    */           
/* 73 */           ResultSet resultSet = preparedStatement.getGeneratedKeys(); 
/* 74 */           try { if (resultSet.next())
/* 75 */             { int k = resultSet.getInt(1);
/*    */               
/* 77 */               if (resultSet != null) resultSet.close();
/*    */               
/* 79 */               if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return k; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  int j = i; if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return j; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 80 */     { sQLException.printStackTrace();
/* 81 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\InsertBatchRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */