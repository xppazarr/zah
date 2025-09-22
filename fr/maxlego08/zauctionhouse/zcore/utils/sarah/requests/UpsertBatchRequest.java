/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Executor;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*    */ import java.sql.Connection;
/*    */ import java.sql.PreparedStatement;
/*    */ import java.sql.SQLException;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ 
/*    */ public class UpsertBatchRequest
/*    */   implements Executor
/*    */ {
/*    */   private final List<Schema> schemas;
/*    */   
/*    */   public UpsertBatchRequest(List<Schema> paramList) {
/* 22 */     this.schemas = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/* 27 */     if (this.schemas.isEmpty()) {
/* 28 */       return 0;
/*    */     }
/*    */     
/* 31 */     DatabaseType databaseType = paramDatabaseConfiguration.getDatabaseType();
/* 32 */     Schema schema = this.schemas.get(0);
/* 33 */     StringBuilder stringBuilder1 = new StringBuilder("INSERT INTO " + schema.getTableName() + " (");
/* 34 */     StringBuilder stringBuilder2 = new StringBuilder("VALUES ");
/* 35 */     StringBuilder stringBuilder3 = new StringBuilder();
/*    */     
/* 37 */     ArrayList<Object> arrayList = new ArrayList();
/* 38 */     ArrayList<String> arrayList1 = new ArrayList();
/* 39 */     ArrayList<String> arrayList2 = new ArrayList();
/*    */     
/* 41 */     for (ColumnDefinition columnDefinition : schema.getColumns()) {
/* 42 */       arrayList2.add(columnDefinition.getSafeName());
/*    */     }
/*    */     
/* 45 */     stringBuilder1.append(String.join(", ", (Iterable)arrayList2)).append(") ");
/*    */     
/* 47 */     for (Schema schema1 : this.schemas) {
/* 48 */       ArrayList<String> arrayList3 = new ArrayList();
/* 49 */       for (ColumnDefinition columnDefinition : schema1.getColumns()) {
/* 50 */         arrayList3.add("?");
/* 51 */         arrayList.add(columnDefinition.getObject());
/*    */       } 
/* 53 */       arrayList1.add("(" + String.join(", ", (Iterable)arrayList3) + ")");
/*    */     } 
/*    */     
/* 56 */     stringBuilder2.append(String.join(", ", (Iterable)arrayList1));
/*    */     
/* 58 */     if (databaseType == DatabaseType.SQLITE) {
/* 59 */       StringBuilder stringBuilder = new StringBuilder(" ON CONFLICT (");
/* 60 */       List<? extends CharSequence> list = schema.getPrimaryKeys();
/* 61 */       stringBuilder.append(String.join(", ", list)).append(") DO UPDATE SET ");
/*    */       
/* 63 */       for (byte b = 0; b < arrayList2.size(); b++) {
/* 64 */         if (b > 0) stringBuilder3.append(", "); 
/* 65 */         stringBuilder3.append(arrayList2.get(b)).append(" = excluded.").append(arrayList2.get(b));
/*    */       } 
/*    */       
/* 68 */       stringBuilder1.append(stringBuilder2).append(stringBuilder).append(stringBuilder3);
/*    */     } else {
/* 70 */       stringBuilder3.append(" ON DUPLICATE KEY UPDATE ");
/* 71 */       for (byte b = 0; b < arrayList2.size(); b++) {
/* 72 */         if (b > 0) stringBuilder3.append(", "); 
/* 73 */         stringBuilder3.append(arrayList2.get(b)).append(" = VALUES(").append(arrayList2.get(b)).append(")");
/*    */       } 
/*    */       
/* 76 */       stringBuilder1.append(stringBuilder2).append(stringBuilder3);
/*    */     } 
/*    */     
/* 79 */     String str = paramDatabaseConfiguration.replacePrefix(stringBuilder1.toString());
/* 80 */     if (paramDatabaseConfiguration.isDebug()) {
/* 81 */       paramLogger.info("Executing SQL: " + str);
/*    */     }
/*    */     
/* 84 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 85 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str);
/*    */         
/* 87 */         try { byte b = 1;
/* 88 */           for (Object object : arrayList) {
/* 89 */             preparedStatement.setObject(b++, object);
/*    */           }
/*    */           
/* 92 */           int i = preparedStatement.executeUpdate();
/* 93 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 94 */     { sQLException.printStackTrace();
/* 95 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\UpsertBatchRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */