/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ public class MigrationManager
/*     */ {
/*  19 */   private static final List<Schema> schemas = new ArrayList<>();
/*  20 */   private static final List<Migration> migrations = new ArrayList<>();
/*  21 */   private static String migrationTableName = "migrations";
/*     */ 
/*     */ 
/*     */   
/*     */   private static DatabaseConfiguration databaseConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String getMigrationTableName() {
/*  31 */     return migrationTableName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setMigrationTableName(String paramString) {
/*  43 */     migrationTableName = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static DatabaseConfiguration getDatabaseConfiguration() {
/*  52 */     return databaseConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setDatabaseConfiguration(DatabaseConfiguration paramDatabaseConfiguration) {
/*  63 */     databaseConfiguration = paramDatabaseConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerSchema(Schema paramSchema) {
/*  72 */     schemas.add(paramSchema);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void execute(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/*  83 */     createMigrationTable(paramDatabaseConnection, paramLogger);
/*     */     
/*  85 */     List<String> list = getMigrations(paramDatabaseConnection, paramLogger);
/*     */     
/*  87 */     migrations.forEach(Migration::up);
/*     */     
/*  89 */     schemas.forEach(paramSchema -> {
/*     */           if (!paramList.contains(paramSchema.getMigration().getClass().getSimpleName())) {
/*     */             int i;
/*     */             try {
/*     */               i = paramSchema.execute(paramDatabaseConnection, paramLogger);
/*  94 */             } catch (SQLException sQLException) {
/*     */               throw new RuntimeException(sQLException);
/*     */             }  if (i != -1)
/*     */               insertMigration(paramDatabaseConnection, paramLogger, paramSchema.getMigration()); 
/*     */           } else {
/*     */             if (!paramSchema.getMigration().isAlter())
/*     */               return;  ArrayList<ColumnDefinition> arrayList = new ArrayList(); String str = paramSchema.getTableName(); str = str.replace("%prefix%", paramDatabaseConnection.getDatabaseConfiguration().getTablePrefix()); if (paramDatabaseConnection.getDatabaseConfiguration().getDatabaseType() == DatabaseType.SQLITE) {
/*     */               try {
/*     */                 Connection connection = paramDatabaseConnection.getConnection(); 
/*     */                 try { PreparedStatement preparedStatement = connection.prepareStatement(String.format("PRAGMA table_info(%s)", new Object[] { str }));
/*     */                   
/*     */                   try { List list = paramSchema.getColumns();
/*     */                     paramLogger.info("Executing SQL: " + String.format("PRAGMA table_info(%s)", new Object[] { str }));
/*     */                     ResultSet resultSet = preparedStatement.executeQuery();
/*     */                     
/*     */                     try { while (resultSet.next()) {
/*     */                         String str1 = resultSet.getString("name");
/*     */                         list.removeIf(());
/*     */                       } 
/*     */                       if (resultSet != null)
/*     */                         resultSet.close();  }
/* 115 */                     catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  arrayList.addAll(list); if (preparedStatement != null)
/*     */                       preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null)
/*     */                       try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (connection != null)
/*     */                     connection.close();  } catch (Throwable throwable) { if (connection != null)
/*     */                     try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */                       throw throwable; }
/*     */               
/* 122 */               } catch (SQLException sQLException) {
/*     */                 sQLException.printStackTrace();
/*     */               } 
/*     */             } else {
/*     */               for (ColumnDefinition columnDefinition : paramSchema.getColumns()) {
/*     */                 long l;
/*     */ 
/*     */                 
/*     */                 Schema schema = SchemaBuilder.selectCount("information_schema.COLUMNS").where("TABLE_NAME", str).where("TABLE_SCHEMA", paramDatabaseConnection.getDatabaseConfiguration().getDatabase()).where("COLUMN_NAME", columnDefinition.getName());
/*     */ 
/*     */                 
/*     */                 try {
/*     */                   l = schema.executeSelectCount(paramDatabaseConnection, paramLogger);
/* 135 */                 } catch (SQLException sQLException) {
/*     */                   throw new RuntimeException(sQLException);
/*     */                 } 
/*     */ 
/*     */                 
/*     */                 if (l == 0L) {
/*     */                   arrayList.add(columnDefinition);
/*     */                 }
/*     */               } 
/*     */             } 
/*     */ 
/*     */             
/*     */             if (arrayList.isEmpty()) {
/*     */               return;
/*     */             }
/*     */             
/*     */             try {
/*     */               int i = SchemaBuilder.alter((Migration)null, str, ()).execute(paramDatabaseConnection, paramLogger);
/*     */               
/*     */               if (i == -1) {
/*     */                 insertMigration(paramDatabaseConnection, paramLogger, paramSchema.getMigration());
/*     */               }
/* 157 */             } catch (SQLException sQLException) {
/*     */               throw new RuntimeException(sQLException);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static List<Migration> getMigrations() {
/* 172 */     return migrations;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void createMigrationTable(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/* 183 */     Schema schema = SchemaBuilder.create((Migration)null, migrationTableName, paramSchema -> {
/*     */           paramSchema.text("migration");
/*     */           paramSchema.createdAt();
/*     */         });
/*     */     try {
/* 188 */       schema.execute(paramDatabaseConnection, paramLogger);
/* 189 */     } catch (SQLException sQLException) {
/* 190 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static List<String> getMigrations(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/* 202 */     Schema schema = SchemaBuilder.select(migrationTableName);
/*     */     try {
/* 204 */       return (List<String>)schema.executeSelect(MigrationTable.class, paramDatabaseConnection, paramLogger).stream().map(MigrationTable::getMigration).collect(Collectors.toList());
/* 205 */     } catch (Exception exception) {
/* 206 */       exception.printStackTrace();
/*     */       
/* 208 */       return new ArrayList<>();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void insertMigration(DatabaseConnection paramDatabaseConnection, Logger paramLogger, Migration paramMigration) {
/*     */     try {
/* 222 */       SchemaBuilder.insert(migrationTableName, paramSchema -> paramSchema.string("migration", paramMigration.getClass().getSimpleName())).execute(paramDatabaseConnection, paramLogger);
/* 223 */     } catch (SQLException sQLException) {
/* 224 */       sQLException.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void registerMigration(Migration paramMigration) {
/* 236 */     migrations.add(paramMigration);
/*     */   }
/*     */   
/*     */   public static class MigrationTable
/*     */   {
/*     */     @Column("migration")
/*     */     private final String migration;
/*     */     
/*     */     public MigrationTable(String param1String) {
/* 245 */       this.migration = param1String;
/*     */     }
/*     */     
/*     */     public String getMigration() {
/* 249 */       return this.migration;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\MigrationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */