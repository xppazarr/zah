/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class DatabaseConnection
/*     */ {
/*     */   protected final DatabaseConfiguration databaseConfiguration;
/*     */   protected Connection connection;
/*     */   
/*     */   public DatabaseConnection(DatabaseConfiguration paramDatabaseConfiguration) {
/*  16 */     this.databaseConfiguration = paramDatabaseConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseConfiguration getDatabaseConfiguration() {
/*  25 */     return this.databaseConfiguration;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*     */     try {
/*  36 */       Class.forName("com.mysql.cj.jdbc.Driver");
/*  37 */     } catch (Exception exception) {}
/*     */ 
/*     */     
/*  40 */     if (!isConnected(this.connection)) {
/*     */       try {
/*  42 */         Connection connection = connectToDatabase();
/*     */         
/*  44 */         if (isConnected(connection)) {
/*  45 */           connection.close();
/*     */         }
/*  47 */       } catch (Exception exception) {
/*  48 */         exception.printStackTrace();
/*  49 */         return false;
/*     */       } 
/*     */     }
/*     */     
/*  53 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isConnected(Connection paramConnection) {
/*  63 */     if (paramConnection == null) {
/*  64 */       return false;
/*     */     }
/*     */     
/*     */     try {
/*  68 */       return (!paramConnection.isClosed() && paramConnection.isValid(1));
/*  69 */     } catch (Exception exception) {
/*  70 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disconnect() {
/*  78 */     if (isConnected(this.connection)) {
/*     */       try {
/*  80 */         this.connection.close();
/*  81 */       } catch (SQLException sQLException) {
/*  82 */         sQLException.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void connect() {
/*  91 */     if (!isConnected(this.connection)) {
/*     */       try {
/*  93 */         this.connection = connectToDatabase();
/*  94 */       } catch (Exception exception) {
/*  95 */         exception.printStackTrace();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public abstract Connection connectToDatabase();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 109 */     connect();
/* 110 */     return this.connection;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\DatabaseConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */