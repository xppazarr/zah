/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.JoinCondition;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.SelectCondition;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.WhereCondition;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Migration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.SchemaType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.AlterRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.CreateIndexRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.CreateRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.DeleteRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.DropTableRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.InsertRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.ModifyRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.RenameExecutor;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.UpdateRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.UpsertRequest;
/*     */ import java.io.ByteArrayInputStream;
/*     */ import java.io.ByteArrayOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.ObjectInputStream;
/*     */ import java.io.ObjectOutputStream;
/*     */ import java.io.Serializable;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.math.BigDecimal;
/*     */ import java.sql.Connection;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.text.ParseException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Date;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class SchemaBuilder
/*     */   implements Schema
/*     */ {
/*     */   private final String tableName;
/*     */   private final SchemaType schemaType;
/*  54 */   private final List<ColumnDefinition> columns = new ArrayList<>();
/*  55 */   private final List<String> primaryKeys = new ArrayList<>();
/*  56 */   private final List<String> foreignKeys = new ArrayList<>();
/*  57 */   private final List<WhereCondition> whereConditions = new ArrayList<>();
/*  58 */   private final List<JoinCondition> joinConditions = new ArrayList<>();
/*  59 */   private final List<SelectCondition> selectColumns = new ArrayList<>();
/*     */   private String newTableName;
/*     */   private String orderBy;
/*     */   private Migration migration;
/*     */   private boolean isDistinct;
/*     */   
/*     */   private SchemaBuilder(String paramString, SchemaType paramSchemaType) {
/*  66 */     this.tableName = paramString;
/*  67 */     this.schemaType = paramSchemaType;
/*     */   }
/*     */   
/*     */   public static Schema copy(String paramString, SchemaType paramSchemaType, Schema paramSchema) {
/*  71 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, paramSchemaType);
/*     */     
/*  73 */     schemaBuilder.columns.addAll(paramSchema.getColumns());
/*  74 */     schemaBuilder.primaryKeys.addAll(paramSchema.getPrimaryKeys());
/*  75 */     schemaBuilder.foreignKeys.addAll(paramSchema.getForeignKeys());
/*  76 */     schemaBuilder.whereConditions.addAll(paramSchema.getWhereConditions());
/*  77 */     schemaBuilder.joinConditions.addAll(paramSchema.getJoinConditions());
/*  78 */     schemaBuilder.selectColumns.addAll(paramSchema.getSelectColumns());
/*  79 */     schemaBuilder.orderBy = paramSchema.getOrderBy();
/*  80 */     schemaBuilder.migration = paramSchema.getMigration();
/*  81 */     schemaBuilder.isDistinct = paramSchema.isDistinct();
/*  82 */     schemaBuilder.newTableName = paramSchema.getNewTableName();
/*     */     
/*  84 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema rename(String paramString1, String paramString2) {
/*  88 */     return rename(null, paramString1, paramString2);
/*     */   }
/*     */   
/*     */   public static Schema rename(Migration paramMigration, String paramString1, String paramString2) {
/*  92 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString1, SchemaType.RENAME);
/*  93 */     schemaBuilder.newTableName = paramString2;
/*  94 */     if (paramMigration != null) {
/*  95 */       schemaBuilder.migration = paramMigration;
/*  96 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/*  98 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema create(Migration paramMigration, String paramString, Class<?> paramClass) {
/* 102 */     return create(paramMigration, paramString, ConsumerConstructor.createConsumerFromTemplate(paramClass, null));
/*     */   }
/*     */   
/*     */   public static Schema create(Migration paramMigration, String paramString, Consumer<Schema> paramConsumer) {
/* 106 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.CREATE);
/* 107 */     if (paramMigration != null) {
/* 108 */       schemaBuilder.migration = paramMigration;
/* 109 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/* 111 */     paramConsumer.accept(schemaBuilder);
/* 112 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema createIndex(Migration paramMigration, String paramString1, String paramString2) {
/* 116 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString1, SchemaType.CREATE_INDEX);
/* 117 */     if (paramMigration != null) {
/* 118 */       schemaBuilder.migration = paramMigration;
/* 119 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/* 121 */     schemaBuilder.addColumn(new ColumnDefinition(paramString2, ""));
/* 122 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema modify(Migration paramMigration, String paramString, Consumer<Schema> paramConsumer) {
/* 126 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.MODIFY);
/* 127 */     if (paramMigration != null) {
/* 128 */       schemaBuilder.migration = paramMigration;
/* 129 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/* 131 */     paramConsumer.accept(schemaBuilder);
/* 132 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema drop(Migration paramMigration, String paramString) {
/* 136 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.DROP);
/* 137 */     if (paramMigration != null) {
/* 138 */       schemaBuilder.migration = paramMigration;
/* 139 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/* 141 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema upsert(String paramString, Consumer<Schema> paramConsumer) {
/* 145 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.UPSERT);
/* 146 */     paramConsumer.accept(schemaBuilder);
/* 147 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema alter(Migration paramMigration, String paramString, Class<?> paramClass) {
/* 151 */     return alter(paramMigration, paramString, ConsumerConstructor.createConsumerFromTemplate(paramClass, null));
/*     */   }
/*     */   
/*     */   public static Schema alter(Migration paramMigration, String paramString, Consumer<Schema> paramConsumer) {
/* 155 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.ALTER);
/* 156 */     if (paramMigration != null) {
/* 157 */       schemaBuilder.migration = paramMigration;
/* 158 */       MigrationManager.registerSchema(schemaBuilder);
/*     */     } 
/* 160 */     paramConsumer.accept(schemaBuilder);
/* 161 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema insert(String paramString, Consumer<Schema> paramConsumer) {
/* 165 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.INSERT);
/* 166 */     paramConsumer.accept(schemaBuilder);
/* 167 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema update(String paramString, Consumer<Schema> paramConsumer) {
/* 171 */     SchemaBuilder schemaBuilder = new SchemaBuilder(paramString, SchemaType.UPDATE);
/* 172 */     paramConsumer.accept(schemaBuilder);
/* 173 */     return schemaBuilder;
/*     */   }
/*     */   
/*     */   public static Schema select(String paramString) {
/* 177 */     return new SchemaBuilder(paramString, SchemaType.SELECT);
/*     */   }
/*     */   
/*     */   public static Schema selectCount(String paramString) {
/* 181 */     return new SchemaBuilder(paramString, SchemaType.SELECT);
/*     */   }
/*     */   
/*     */   public static Schema delete(String paramString) {
/* 185 */     return new SchemaBuilder(paramString, SchemaType.DELETE);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema where(String paramString, Object paramObject) {
/* 190 */     return where(null, paramString, "=", paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema where(String paramString, UUID paramUUID) {
/* 195 */     return where(paramString, paramUUID.toString());
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema where(String paramString1, String paramString2, Object paramObject) {
/* 200 */     return where(null, paramString1, paramString2, paramObject);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema where(String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 205 */     this.whereConditions.add(new WhereCondition(paramString1, paramString2, paramString3, paramObject));
/* 206 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereNotNull(String paramString) {
/* 211 */     this.whereConditions.add(new WhereCondition(paramString, WhereCondition.WhereAction.IS_NOT_NULL));
/* 212 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereNull(String paramString) {
/* 217 */     this.whereConditions.add(new WhereCondition(paramString, WhereCondition.WhereAction.IS_NULL));
/* 218 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereIn(String paramString, Object... paramVarArgs) {
/* 223 */     return whereIn((String)null, paramString, paramVarArgs);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereIn(String paramString, List<String> paramList) {
/* 228 */     return whereIn((String)null, paramString, paramList);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereIn(String paramString1, String paramString2, Object... paramVarArgs) {
/* 233 */     this.whereConditions.add(new WhereCondition(paramString1, paramString2, (List)Arrays.<Object>stream(paramVarArgs).map(String::valueOf).collect(Collectors.toList())));
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema whereIn(String paramString1, String paramString2, List<String> paramList) {
/* 239 */     this.whereConditions.add(new WhereCondition(paramString1, paramString2, paramList));
/* 240 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema uuid(String paramString) {
/* 245 */     string(paramString, 36);
/* 246 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema uuid(String paramString, UUID paramUUID) {
/* 251 */     return addColumn((new ColumnDefinition(paramString)).setObject(paramUUID.toString()));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema string(String paramString, int paramInt) {
/* 256 */     return addColumn((new ColumnDefinition(paramString, "VARCHAR")).setLength(paramInt));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema text(String paramString) {
/* 261 */     return addColumn(new ColumnDefinition(paramString, "TEXT"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema longText(String paramString) {
/* 266 */     return addColumn(new ColumnDefinition(paramString, "LONGTEXT"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema decimal(String paramString) {
/* 271 */     return decimal(paramString, 65, 30);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema decimal(String paramString, int paramInt1, int paramInt2) {
/* 276 */     return addColumn((new ColumnDefinition(paramString, "DECIMAL")).setLength(paramInt1).setDecimal(Integer.valueOf(paramInt2)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema string(String paramString1, String paramString2) {
/* 281 */     return addColumn((new ColumnDefinition(paramString1)).setObject(paramString2));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema decimal(String paramString, Number paramNumber) {
/* 286 */     return addColumn((new ColumnDefinition(paramString)).setObject(paramNumber));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema date(String paramString, Date paramDate) {
/* 291 */     return addColumn((new ColumnDefinition(paramString)).setObject(paramDate));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema object(String paramString, Object paramObject) {
/* 296 */     return addColumn((new ColumnDefinition(paramString)).setObject(paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema bigInt(String paramString) {
/* 301 */     return addColumn(new ColumnDefinition(paramString, "BIGINT"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema integer(String paramString) {
/* 306 */     return addColumn(new ColumnDefinition(paramString, "INT"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema bigInt(String paramString, long paramLong) {
/* 311 */     return addColumn((new ColumnDefinition(paramString)).setObject(Long.valueOf(paramLong)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema bool(String paramString) {
/* 316 */     return addColumn(new ColumnDefinition(paramString, "BOOLEAN"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema bool(String paramString, boolean paramBoolean) {
/* 321 */     return addColumn((new ColumnDefinition(paramString)).setObject(Boolean.valueOf(paramBoolean)));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema json(String paramString) {
/* 326 */     return addColumn(new ColumnDefinition(paramString, "JSON"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema blob(String paramString) {
/* 331 */     return addColumn(new ColumnDefinition(paramString, "BLOB"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema blob(String paramString, byte[] paramArrayOfbyte) {
/* 336 */     return addColumn((new ColumnDefinition(paramString, "BLOB")).setObject(paramArrayOfbyte));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema blob(String paramString, Object paramObject) {
/*     */     try {
/* 342 */       byte[] arrayOfByte = serializeObject(paramObject);
/* 343 */       return addColumn((new ColumnDefinition(paramString, "BLOB")).setObject(arrayOfByte));
/* 344 */     } catch (IOException iOException) {
/* 345 */       throw new RuntimeException("An error occurred while serializing object for BLOB column: " + paramString, iOException);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema foreignKey(String paramString) {
/* 351 */     if (this.columns.isEmpty()) throw new IllegalStateException("No column defined to apply foreign key."); 
/* 352 */     ColumnDefinition columnDefinition = this.columns.get(this.columns.size() - 1);
/*     */     
/* 354 */     String str = String.format("FOREIGN KEY (%s) REFERENCES %s(%s) ON DELETE CASCADE", new Object[] { columnDefinition.getSafeName(), paramString, columnDefinition.getSafeName() });
/* 355 */     this.foreignKeys.add(str);
/* 356 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema foreignKey(String paramString1, String paramString2, boolean paramBoolean) {
/* 361 */     if (this.columns.isEmpty()) throw new IllegalStateException("No column defined to apply foreign key."); 
/* 362 */     ColumnDefinition columnDefinition = this.columns.get(this.columns.size() - 1);
/*     */     
/* 364 */     String str = String.format("FOREIGN KEY (%s) REFERENCES %s(%s)%s", new Object[] { columnDefinition.getSafeName(), paramString1, paramString2, paramBoolean ? " ON DELETE CASCADE" : "" });
/* 365 */     this.foreignKeys.add(str);
/* 366 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema createdAt() {
/* 371 */     ColumnDefinition columnDefinition = new ColumnDefinition("created_at", "TIMESTAMP");
/* 372 */     columnDefinition.setDefaultValue("CURRENT_TIMESTAMP");
/* 373 */     this.columns.add(columnDefinition);
/* 374 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema timestamp(String paramString) {
/* 379 */     return addColumn(new ColumnDefinition(paramString, "TIMESTAMP"));
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema autoIncrement(String paramString) {
/* 384 */     return addColumn((new ColumnDefinition(paramString, "INTEGER")).setAutoIncrement(true)).primary();
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema autoIncrementBigInt(String paramString) {
/* 389 */     return addColumn((new ColumnDefinition(paramString, "BIGINT")).setAutoIncrement(true)).primary();
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema updatedAt() {
/* 394 */     ColumnDefinition columnDefinition = new ColumnDefinition("updated_at", "TIMESTAMP");
/*     */     
/* 396 */     DatabaseConfiguration databaseConfiguration = MigrationManager.getDatabaseConfiguration();
/* 397 */     if (databaseConfiguration.getDatabaseType() == DatabaseType.SQLITE) {
/* 398 */       columnDefinition.setDefaultValue("CURRENT_TIMESTAMP");
/*     */     } else {
/* 400 */       columnDefinition.setDefaultValue("CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP");
/*     */     } 
/* 402 */     this.columns.add(columnDefinition);
/* 403 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema nullable() {
/* 408 */     getLastColumn().setNullable(true);
/* 409 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema unique(boolean paramBoolean) {
/* 414 */     getLastColumn().setUnique(paramBoolean);
/* 415 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema unique() {
/* 420 */     return unique(true);
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema defaultValue(Object paramObject) {
/* 425 */     getLastColumn().setDefaultValue(paramObject.toString());
/* 426 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema defaultCurrentTimestamp() {
/* 431 */     return defaultValue("CURRENT_TIMESTAMP");
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema primary() {
/* 436 */     ColumnDefinition columnDefinition = getLastColumn();
/* 437 */     columnDefinition.setPrimaryKey(true);
/* 438 */     this.primaryKeys.add(columnDefinition.getSafeName());
/* 439 */     return this;
/*     */   }
/*     */   
/*     */   public Schema addColumn(ColumnDefinition paramColumnDefinition) {
/* 443 */     this.columns.add(paramColumnDefinition);
/* 444 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema timestamps() {
/* 449 */     createdAt();
/* 450 */     updatedAt();
/* 451 */     return this;
/*     */   }
/*     */   
/*     */   private ColumnDefinition getLastColumn() {
/* 455 */     if (this.columns.isEmpty()) throw new IllegalStateException("No columns defined."); 
/* 456 */     return this.columns.get(this.columns.size() - 1);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getTableName() {
/* 461 */     return this.tableName;
/*     */   }
/*     */ 
/*     */   
/*     */   public void whereConditions(StringBuilder paramStringBuilder) {
/* 466 */     if (!this.whereConditions.isEmpty()) {
/* 467 */       ArrayList<String> arrayList = new ArrayList();
/* 468 */       for (WhereCondition whereCondition : this.whereConditions) {
/* 469 */         arrayList.add(whereCondition.getCondition());
/*     */       }
/* 471 */       paramStringBuilder.append(" WHERE ").append(String.join(" AND ", (Iterable)arrayList));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public long executeSelectCount(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/* 477 */     StringBuilder stringBuilder = new StringBuilder("SELECT COUNT(*) FROM " + this.tableName);
/* 478 */     whereConditions(stringBuilder);
/*     */     
/* 480 */     String str = paramDatabaseConnection.getDatabaseConfiguration().replacePrefix(stringBuilder.toString());
/* 481 */     if (paramDatabaseConnection.getDatabaseConfiguration().isDebug()) {
/* 482 */       paramLogger.info("Executing SQL: " + str);
/*     */     }
/*     */     
/* 485 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 486 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str);
/*     */         
/* 488 */         try { applyWhereConditions(preparedStatement, 1);
/*     */           
/* 490 */           ResultSet resultSet = preparedStatement.executeQuery(); 
/* 491 */           try { if (resultSet.next())
/* 492 */             { long l = resultSet.getInt(1);
/*     */               
/* 494 */               if (resultSet != null) resultSet.close(); 
/* 495 */               if (preparedStatement != null) preparedStatement.close();  if (connection != null) connection.close();  return l; }  if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null) try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (preparedStatement != null) preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (connection != null) connection.close();  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 496 */     { sQLException.printStackTrace();
/* 497 */       throw new SQLException("Failed to execute schema select count: " + sQLException.getMessage(), sQLException); }
/*     */     
/* 499 */     return 0L;
/*     */   }
/*     */   
/*     */   public List<Map<String, Object>> executeSelect(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/*     */     StringBuilder stringBuilder;
/* 504 */     ArrayList<HashMap<Object, Object>> arrayList = new ArrayList();
/*     */     
/* 506 */     String str1 = "*";
/* 507 */     if (!this.selectColumns.isEmpty())
/*     */     {
/*     */       
/* 510 */       str1 = this.selectColumns.stream().map(SelectCondition::getSelectColumn).collect(Collectors.joining(","));
/*     */     }
/*     */ 
/*     */     
/* 514 */     if (this.isDistinct) {
/* 515 */       stringBuilder = new StringBuilder("SELECT DISTINCT " + str1 + " FROM " + this.tableName);
/*     */     } else {
/* 517 */       stringBuilder = new StringBuilder("SELECT " + str1 + " FROM " + this.tableName);
/*     */     } 
/*     */     
/* 520 */     if (!this.joinConditions.isEmpty()) {
/* 521 */       for (JoinCondition joinCondition : this.joinConditions) {
/* 522 */         stringBuilder.append(" ").append(joinCondition.getJoinClause());
/*     */       }
/*     */     }
/*     */     
/* 526 */     whereConditions(stringBuilder);
/*     */     
/* 528 */     if (this.orderBy != null) {
/* 529 */       stringBuilder.append(" ").append(this.orderBy);
/*     */     }
/*     */     
/* 532 */     DatabaseConfiguration databaseConfiguration = paramDatabaseConnection.getDatabaseConfiguration();
/* 533 */     String str2 = databaseConfiguration.replacePrefix(stringBuilder.toString());
/*     */     
/* 535 */     if (databaseConfiguration.isDebug()) {
/* 536 */       paramLogger.info("Executing SQL: " + str2);
/*     */     }
/*     */     
/* 539 */     try { Connection connection = paramDatabaseConnection.getConnection(); 
/* 540 */       try { PreparedStatement preparedStatement = connection.prepareStatement(str2);
/*     */         
/* 542 */         try { applyWhereConditions(preparedStatement, 1);
/*     */           
/* 544 */           ResultSet resultSet = preparedStatement.executeQuery(); 
/* 545 */           try { while (resultSet.next()) {
/* 546 */               HashMap<Object, Object> hashMap = new HashMap<>();
/* 547 */               for (byte b = 1; b <= resultSet.getMetaData().getColumnCount(); b++) {
/* 548 */                 hashMap.put(resultSet.getMetaData().getColumnName(b), resultSet.getObject(b));
/*     */               }
/* 550 */               arrayList.add(hashMap);
/*     */             } 
/* 552 */             if (resultSet != null) resultSet.close();  } catch (Throwable throwable) { if (resultSet != null)
/* 553 */               try { resultSet.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (preparedStatement != null) preparedStatement.close();  } catch (Throwable throwable) { if (preparedStatement != null) try { preparedStatement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  if (connection != null) connection.close();  } catch (Throwable throwable) { if (connection != null) try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/* 554 */     { paramLogger.info("Failed to execute schema select: " + sQLException.getMessage());
/* 555 */       throw new SQLException("Failed to execute schema select: " + sQLException.getMessage(), sQLException); }
/*     */ 
/*     */     
/* 558 */     return (List)arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void applyWhereConditions(PreparedStatement paramPreparedStatement, int paramInt) {
/* 563 */     for (WhereCondition whereCondition : this.whereConditions) {
/* 564 */       if (whereCondition.getWhereAction() == WhereCondition.WhereAction.NORMAL) {
/* 565 */         paramPreparedStatement.setObject(paramInt, whereCondition.getValue());
/* 566 */         paramInt++; continue;
/* 567 */       }  if (whereCondition.getWhereAction() == WhereCondition.WhereAction.IN) {
/* 568 */         for (String str : whereCondition.getValues()) {
/* 569 */           paramPreparedStatement.setObject(paramInt, str);
/* 570 */           paramInt++;
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> List<T> executeSelect(Class<T> paramClass, DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/* 578 */     List<Map<String, Object>> list = executeSelect(paramDatabaseConnection, paramLogger);
/* 579 */     return transformResults(list, paramClass);
/*     */   }
/*     */   
/*     */   private <T> List<T> transformResults(List<Map<String, Object>> paramList, Class<T> paramClass) {
/* 583 */     ArrayList<byte> arrayList = new ArrayList();
/* 584 */     Constructor[] arrayOfConstructor = (Constructor[])paramClass.getDeclaredConstructors();
/* 585 */     Constructor<byte> constructor = arrayOfConstructor[0];
/* 586 */     constructor.setAccessible(true);
/*     */     
/* 588 */     for (Map<String, Object> map : paramList) {
/* 589 */       Object[] arrayOfObject = new Object[constructor.getParameterCount()];
/* 590 */       Field[] arrayOfField = paramClass.getDeclaredFields();
/*     */       byte b;
/* 592 */       for (b = 0; b < arrayOfField.length; b++) {
/* 593 */         Field field = arrayOfField[b];
/* 594 */         if (field.isAnnotationPresent((Class)Column.class)) {
/* 595 */           Column column = field.<Column>getAnnotation(Column.class);
/* 596 */           arrayOfObject[b] = convertToRequiredType(map.get(column.value()), field.getType());
/*     */         } else {
/* 598 */           arrayOfObject[b] = convertToRequiredType(map.get(field.getName()), field.getType());
/*     */         } 
/*     */       } 
/* 601 */       b = constructor.newInstance(arrayOfObject);
/* 602 */       arrayList.add(b);
/*     */     } 
/* 604 */     return (List)arrayList;
/*     */   }
/*     */   
/*     */   protected Object convertToRequiredType(Object paramObject, Class<?> paramClass) {
/* 608 */     if (paramObject == null)
/* 609 */       return null; 
/* 610 */     if (paramClass.isEnum())
/* 611 */       return Enum.valueOf(paramClass, (String)paramObject); 
/* 612 */     if (paramClass == BigDecimal.class)
/* 613 */       return new BigDecimal(paramObject.toString()); 
/* 614 */     if (paramClass == UUID.class)
/* 615 */       return UUID.fromString((String)paramObject); 
/* 616 */     if (paramClass == Boolean.class || paramClass == boolean.class) {
/* 617 */       String str = paramObject.toString();
/* 618 */       return Boolean.valueOf((str.equalsIgnoreCase("true") || str.equalsIgnoreCase("1")));
/* 619 */     }  if (paramClass == Long.class || paramClass == long.class)
/* 620 */       return Long.valueOf(Long.parseLong(paramObject.toString())); 
/* 621 */     if (paramClass == Double.class || paramClass == double.class)
/* 622 */       return Double.valueOf(Double.parseDouble(paramObject.toString())); 
/* 623 */     if (paramClass == Integer.class || paramClass == int.class)
/* 624 */       return Integer.valueOf(Integer.parseInt(paramObject.toString())); 
/* 625 */     if (Serializable.class.isAssignableFrom(paramClass) && paramObject instanceof byte[])
/* 626 */       return deserializeObject((byte[])paramObject, paramClass); 
/* 627 */     if (paramClass == Date.class) {
/* 628 */       if (paramObject instanceof String) {
/* 629 */         SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
/*     */         try {
/* 631 */           return simpleDateFormat.parse((String)paramObject);
/* 632 */         } catch (ParseException parseException) {
/* 633 */           parseException.printStackTrace();
/* 634 */           return null;
/*     */         } 
/*     */       } 
/* 637 */       if (paramObject instanceof Number) {
/* 638 */         return new Date(((Number)paramObject).longValue());
/*     */       }
/*     */       
/* 641 */       if (paramObject instanceof java.sql.Timestamp) {
/* 642 */         return paramObject;
/*     */       }
/* 644 */       return null;
/*     */     } 
/* 646 */     return paramObject;
/*     */   }
/*     */ 
/*     */   
/*     */   protected byte[] serializeObject(Object paramObject) {
/* 651 */     ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(); try { ObjectOutputStream objectOutputStream = new ObjectOutputStream(byteArrayOutputStream); 
/* 652 */       try { objectOutputStream.writeObject(paramObject);
/* 653 */         byte[] arrayOfByte = byteArrayOutputStream.toByteArray();
/* 654 */         objectOutputStream.close(); byteArrayOutputStream.close(); return arrayOfByte; } catch (Throwable throwable) { try { objectOutputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  }
/*     */     catch (Throwable throwable) { try { byteArrayOutputStream.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 658 */      } protected <T> T deserializeObject(byte[] paramArrayOfbyte, Class<T> paramClass) { try { ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(paramArrayOfbyte); try { ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream); 
/* 659 */         try { T t = paramClass.cast(objectInputStream.readObject());
/* 660 */           objectInputStream.close(); byteArrayInputStream.close(); return t; } catch (Throwable throwable) { try { objectInputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (Throwable throwable) { try { byteArrayInputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  } catch (IOException|ClassNotFoundException iOException)
/* 661 */     { throw new Error("An exception occurred during deserialization of a BLOB ", iOException); }
/*     */      }
/*     */ 
/*     */ 
/*     */   
/*     */   public Migration getMigration() {
/* 667 */     return this.migration;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMigration(Migration paramMigration) {
/* 672 */     this.migration = paramMigration;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema leftJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 677 */     this.joinConditions.add(new JoinCondition(JoinCondition.JoinType.LEFT, paramString1, paramString2, paramString3, paramString4, paramString5, null));
/* 678 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema leftJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JoinCondition paramJoinCondition) {
/* 683 */     this.joinConditions.add(new JoinCondition(JoinCondition.JoinType.LEFT, paramString1, paramString2, paramString3, paramString4, paramString5, paramJoinCondition));
/* 684 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema rightJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 689 */     this.joinConditions.add(new JoinCondition(JoinCondition.JoinType.RIGHT, paramString1, paramString2, paramString3, paramString4, paramString5, null));
/* 690 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema innerJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 695 */     this.joinConditions.add(new JoinCondition(JoinCondition.JoinType.INNER, paramString1, paramString2, paramString3, paramString4, paramString5, null));
/* 696 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Schema fullJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5) {
/* 701 */     this.joinConditions.add(new JoinCondition(JoinCondition.JoinType.FULL, paramString1, paramString2, paramString3, paramString4, paramString5, null));
/* 702 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<ColumnDefinition> getColumns() {
/* 707 */     return this.columns;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getPrimaryKeys() {
/* 712 */     return this.primaryKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> getForeignKeys() {
/* 717 */     return this.foreignKeys;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<JoinCondition> getJoinConditions() {
/* 722 */     return this.joinConditions;
/*     */   }
/*     */ 
/*     */   
/*     */   public void orderBy(String paramString) {
/* 727 */     this.orderBy = String.format("ORDER BY %s", new Object[] { paramString });
/*     */   }
/*     */ 
/*     */   
/*     */   public void orderByDesc(String paramString) {
/* 732 */     this.orderBy = String.format("ORDER BY %s DESC", new Object[] { paramString });
/*     */   }
/*     */ 
/*     */   
/*     */   public String getOrderBy() {
/* 737 */     return this.orderBy;
/*     */   }
/*     */ 
/*     */   
/*     */   public void distinct() {
/* 742 */     this.isDistinct = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isDistinct() {
/* 747 */     return this.isDistinct; } public int execute(DatabaseConnection paramDatabaseConnection, Logger paramLogger) { RenameExecutor renameExecutor; ModifyRequest modifyRequest; CreateRequest createRequest; DropTableRequest dropTableRequest; AlterRequest alterRequest;
/*     */     UpsertRequest upsertRequest;
/*     */     UpdateRequest updateRequest;
/*     */     InsertRequest insertRequest;
/*     */     DeleteRequest deleteRequest;
/*     */     CreateIndexRequest createIndexRequest;
/* 753 */     switch (this.schemaType) {
/*     */       case RENAME:
/* 755 */         renameExecutor = new RenameExecutor(this);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 791 */         return renameExecutor.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case MODIFY: modifyRequest = new ModifyRequest(this); return modifyRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case CREATE: createRequest = new CreateRequest(this); return createRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case DROP: dropTableRequest = new DropTableRequest(this); return dropTableRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case ALTER: alterRequest = new AlterRequest(this); return alterRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case UPSERT: upsertRequest = new UpsertRequest(this); return upsertRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case UPDATE: updateRequest = new UpdateRequest(this); return updateRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case INSERT: insertRequest = new InsertRequest(this); return insertRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case DELETE: deleteRequest = new DeleteRequest(this); return deleteRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);case CREATE_INDEX: createIndexRequest = new CreateIndexRequest(this); return createIndexRequest.execute(paramDatabaseConnection, paramDatabaseConnection.getDatabaseConfiguration(), paramLogger);
/*     */       case SELECT:
/*     */       case SELECT_COUNT:
/*     */         throw new IllegalArgumentException("Wrong method !");
/*     */     } 
/* 796 */     throw new Error("Schema type not found !"); } public void addSelect(String paramString) { this.selectColumns.add(new SelectCondition(null, paramString, null, false, null)); }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSelect(String paramString1, String paramString2) {
/* 801 */     this.selectColumns.add(new SelectCondition(paramString1, paramString2, null, false, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelect(String paramString1, String paramString2, String paramString3) {
/* 806 */     this.selectColumns.add(new SelectCondition(null, paramString2, paramString3, false, null));
/*     */   }
/*     */ 
/*     */   
/*     */   public void addSelect(String paramString1, String paramString2, String paramString3, Object paramObject) {
/* 811 */     this.selectColumns.add(new SelectCondition(null, paramString2, paramString3, true, paramObject));
/*     */   }
/*     */ 
/*     */   
/*     */   public SchemaType getSchemaType() {
/* 816 */     return this.schemaType;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WhereCondition> getWhereConditions() {
/* 821 */     return this.whereConditions;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<SelectCondition> getSelectColumns() {
/* 826 */     return this.selectColumns;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getNewTableName() {
/* 831 */     return this.newTableName;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\SchemaBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */