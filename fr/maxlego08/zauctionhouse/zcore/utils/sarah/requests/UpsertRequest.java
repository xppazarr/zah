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
/*    */ public class UpsertRequest
/*    */   implements Executor
/*    */ {
/*    */   private final Schema schema;
/*    */   
/*    */   public UpsertRequest(Schema paramSchema) {
/* 22 */     this.schema = paramSchema;
/*    */   }
/*    */   
/*    */   public int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger) {
/*    */     String str1;
/* 27 */     DatabaseType databaseType = paramDatabaseConfiguration.getDatabaseType();
/* 28 */     StringBuilder stringBuilder1 = new StringBuilder("INSERT INTO " + this.schema.getTableName() + " (");
/* 29 */     StringBuilder stringBuilder2 = new StringBuilder("VALUES (");
/* 30 */     StringBuilder stringBuilder3 = new StringBuilder();
/*    */     
/* 32 */     ArrayList<Object> arrayList = new ArrayList();
/*    */     
/* 34 */     for (byte b = 0; b < this.schema.getColumns().size(); b++) {
/* 35 */       ColumnDefinition columnDefinition = this.schema.getColumns().get(b);
/* 36 */       stringBuilder1.append((b > 0) ? ", " : "").append(columnDefinition.getSafeName());
/* 37 */       stringBuilder2.append((b > 0) ? ", " : "").append("?");
/* 38 */       if (b > 0) {
/* 39 */         stringBuilder3.append(", ");
/*    */       }
/* 41 */       if (databaseType == DatabaseType.SQLITE) {
/* 42 */         stringBuilder3.append(columnDefinition.getSafeName()).append(" = excluded.").append(columnDefinition.getSafeName());
/*    */       } else {
/* 44 */         stringBuilder3.append(columnDefinition.getSafeName()).append(" = ?");
/*    */       } 
/* 46 */       arrayList.add(columnDefinition.getObject());
/*    */     } 
/*    */     
/* 49 */     stringBuilder1.append(") ");
/* 50 */     stringBuilder2.append(")");
/*    */ 
/*    */ 
/*    */     
/* 54 */     if (databaseType == DatabaseType.SQLITE) {
/* 55 */       StringBuilder stringBuilder = new StringBuilder(" ON CONFLICT (");
/* 56 */       List<String> list = this.schema.getPrimaryKeys();
/* 57 */       for (byte b1 = 0; b1 < list.size(); b1++) {
/* 58 */         stringBuilder.append((b1 > 0) ? ", " : "").append(list.get(b1));
/*    */       }
/* 60 */       stringBuilder.append(") DO UPDATE SET ");
/* 61 */       str1 = stringBuilder1 + stringBuilder2.toString() + stringBuilder + stringBuilder3;
/*    */     } else {
/* 63 */       stringBuilder3.insert(0, " ON DUPLICATE KEY UPDATE ");
/* 64 */       str1 = stringBuilder1 + stringBuilder2.toString() + stringBuilder3;
/*    */     } 
/*    */     
/* 67 */     String str2 = paramDatabaseConfiguration.replacePrefix(str1);
/* 68 */     if (paramDatabaseConfiguration.isDebug()) {
/* 69 */       paramLogger.info("Executing SQL: " + str2);
/*    */     }
/*    */     
/* 72 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 73 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str2);
/*    */         
/* 75 */         try { byte b1 = 1;
/*    */ 
/*    */           
/* 78 */           for (Object object : arrayList) {
/* 79 */             preparedStatement.setObject(b1++, object);
/*    */           }
/*    */ 
/*    */           
/* 83 */           if (databaseType != DatabaseType.SQLITE) {
/* 84 */             for (Object object : arrayList) {
/* 85 */               preparedStatement.setObject(b1++, object);
/*    */             }
/*    */           }
/* 88 */           preparedStatement.executeUpdate();
/* 89 */           int i = preparedStatement.getUpdateCount();
/* 90 */           if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return i; } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 91 */     { sQLException.printStackTrace();
/*    */       
/* 93 */       return -1; }
/*    */   
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\requests\UpsertRequest.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */