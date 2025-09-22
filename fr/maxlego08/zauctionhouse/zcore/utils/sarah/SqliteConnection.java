/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*    */ 
/*    */ import java.io.File;
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ 
/*    */ public class SqliteConnection
/*    */   extends DatabaseConnection {
/*    */   private final File folder;
/* 10 */   private String fileName = "database.db";
/*    */   
/*    */   public SqliteConnection(DatabaseConfiguration paramDatabaseConfiguration, File paramFile) {
/* 13 */     super(paramDatabaseConfiguration);
/* 14 */     this.folder = paramFile;
/*    */   }
/*    */ 
/*    */   
/*    */   public Connection connectToDatabase() {
/* 19 */     if (!this.folder.exists()) {
/* 20 */       this.folder.mkdirs();
/*    */     }
/*    */     
/* 23 */     File file = new File(this.folder, this.fileName);
/* 24 */     if (!file.exists()) {
/* 25 */       file.createNewFile();
/*    */     }
/*    */     
/*    */     try {
/* 29 */       Class.forName("org.sqlite.JDBC");
/* 30 */     } catch (ClassNotFoundException classNotFoundException) {}
/*    */ 
/*    */     
/* 33 */     return DriverManager.getConnection("jdbc:sqlite:" + file.getAbsolutePath());
/*    */   }
/*    */   
/*    */   public File getFolder() {
/* 37 */     return this.folder;
/*    */   }
/*    */   
/*    */   public String getFileName() {
/* 41 */     return this.fileName;
/*    */   }
/*    */   
/*    */   public void setFileName(String paramString) {
/* 45 */     this.fileName = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public Connection getConnection() {
/*    */     try {
/* 51 */       return connectToDatabase();
/* 52 */     } catch (Exception exception) {
/* 53 */       connect();
/* 54 */       return this.connection;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\SqliteConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */