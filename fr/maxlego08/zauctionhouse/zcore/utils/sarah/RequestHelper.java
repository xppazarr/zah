/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.InsertBatchRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.UpdateBatchRequest;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.requests.UpsertBatchRequest;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.function.Consumer;
/*     */ 
/*     */ 
/*     */ public class RequestHelper
/*     */ {
/*     */   private final DatabaseConnection connection;
/*     */   private final Logger logger;
/*     */   
/*     */   public RequestHelper(DatabaseConnection paramDatabaseConnection, Logger paramLogger) {
/*  22 */     this.connection = paramDatabaseConnection;
/*  23 */     this.logger = paramLogger;
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
/*     */ 
/*     */   
/*     */   public <T> void upsert(String paramString, Class<T> paramClass, T paramT) {
/*  37 */     upsert(paramString, ConsumerConstructor.createConsumerFromTemplate(paramClass, paramT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void upsert(String paramString, Consumer<Schema> paramConsumer) {
/*     */     try {
/*  49 */       SchemaBuilder.upsert(paramString, paramConsumer).execute(this.connection, this.logger);
/*  50 */     } catch (SQLException sQLException) {
/*  51 */       sQLException.printStackTrace();
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
/*     */ 
/*     */   
/*     */   public <T> void update(String paramString, Class<T> paramClass, T paramT) {
/*  66 */     update(paramString, ConsumerConstructor.createConsumerFromTemplate(paramClass, paramT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void update(String paramString, Consumer<Schema> paramConsumer) {
/*     */     try {
/*  78 */       SchemaBuilder.update(paramString, paramConsumer).execute(this.connection, this.logger);
/*  79 */     } catch (SQLException sQLException) {
/*  80 */       sQLException.printStackTrace();
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
/*     */ 
/*     */   
/*     */   public <T> void insert(String paramString, Class<T> paramClass, T paramT) {
/*  95 */     insert(paramString, ConsumerConstructor.createConsumerFromTemplate(paramClass, paramT));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(String paramString, Consumer<Schema> paramConsumer) {
/* 106 */     insert(paramString, paramConsumer, paramInteger -> {
/*     */         
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
/*     */   
/*     */   public void insert(String paramString, Consumer<Schema> paramConsumer, Consumer<Integer> paramConsumer1) {
/* 120 */     insert(paramString, paramConsumer, paramConsumer1, () -> {
/*     */         
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void insert(String paramString, Consumer<Schema> paramConsumer, Consumer<Integer> paramConsumer1, Runnable paramRunnable) {
/*     */     try {
/* 137 */       paramConsumer1.accept(Integer.valueOf(SchemaBuilder.insert(paramString, paramConsumer).execute(this.connection, this.logger)));
/* 138 */     } catch (SQLException sQLException) {
/* 139 */       sQLException.printStackTrace();
/* 140 */       paramRunnable.run();
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
/*     */   
/*     */   public long count(String paramString, Consumer<Schema> paramConsumer) {
/* 154 */     Schema schema = SchemaBuilder.selectCount(paramString);
/* 155 */     paramConsumer.accept(schema);
/*     */     try {
/* 157 */       return schema.executeSelectCount(this.connection, this.logger);
/* 158 */     } catch (SQLException sQLException) {
/* 159 */       sQLException.printStackTrace();
/*     */       
/* 161 */       return 0L;
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
/*     */   
/*     */   public <T> List<T> select(String paramString, Class<T> paramClass, Consumer<Schema> paramConsumer) {
/* 175 */     Schema schema = SchemaBuilder.select(paramString);
/* 176 */     paramConsumer.accept(schema);
/*     */     try {
/* 178 */       return schema.executeSelect(paramClass, this.connection, this.logger);
/* 179 */     } catch (Exception exception) {
/* 180 */       exception.printStackTrace();
/*     */       
/* 182 */       return new ArrayList<>();
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
/*     */   
/*     */   public List<Map<String, Object>> select(String paramString, Consumer<Schema> paramConsumer) {
/* 196 */     Schema schema = SchemaBuilder.select(paramString);
/* 197 */     paramConsumer.accept(schema);
/*     */     try {
/* 199 */       return schema.executeSelect(this.connection, this.logger);
/* 200 */     } catch (Exception exception) {
/* 201 */       exception.printStackTrace();
/*     */       
/* 203 */       return new ArrayList<>();
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
/*     */   public <T> List<T> selectAll(String paramString, Class<T> paramClass) {
/* 215 */     Schema schema = SchemaBuilder.select(paramString);
/*     */     try {
/* 217 */       return schema.executeSelect(paramClass, this.connection, this.logger);
/* 218 */     } catch (Exception exception) {
/* 219 */       exception.printStackTrace();
/*     */       
/* 221 */       return new ArrayList<>();
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
/*     */   public void delete(String paramString, Consumer<Schema> paramConsumer) {
/* 233 */     Schema schema = SchemaBuilder.delete(paramString);
/* 234 */     paramConsumer.accept(schema);
/*     */     try {
/* 236 */       schema.execute(this.connection, this.logger);
/* 237 */     } catch (SQLException sQLException) {
/* 238 */       sQLException.printStackTrace();
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
/*     */   
/*     */   public void upsertMultiple(List<Schema> paramList) {
/* 252 */     UpsertBatchRequest upsertBatchRequest = new UpsertBatchRequest(paramList);
/* 253 */     upsertBatchRequest.execute(this.connection, this.connection.getDatabaseConfiguration(), this.logger);
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
/*     */   
/*     */   public void insertMultiple(List<Schema> paramList) {
/* 266 */     InsertBatchRequest insertBatchRequest = new InsertBatchRequest(paramList);
/* 267 */     insertBatchRequest.execute(this.connection, this.connection.getDatabaseConfiguration(), this.logger);
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
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateMultiple(List<Schema> paramList) {
/* 282 */     if (this.connection.getDatabaseConfiguration().getDatabaseType() == DatabaseType.SQLITE) {
/* 283 */       for (Schema schema : paramList) {
/*     */         try {
/* 285 */           schema.execute(this.connection, this.logger);
/* 286 */         } catch (SQLException sQLException) {
/* 287 */           sQLException.printStackTrace();
/*     */         } 
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/* 293 */     UpdateBatchRequest updateBatchRequest = new UpdateBatchRequest(paramList);
/* 294 */     updateBatchRequest.execute(this.connection, this.connection.getDatabaseConfiguration(), this.logger);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseConnection getConnection() {
/* 303 */     return this.connection;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\RequestHelper.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */