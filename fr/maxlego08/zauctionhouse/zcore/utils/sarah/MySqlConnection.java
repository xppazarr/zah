/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*    */ 
/*    */ import java.sql.Connection;
/*    */ import java.sql.DriverManager;
/*    */ import java.util.Properties;
/*    */ 
/*    */ public class MySqlConnection
/*    */   extends DatabaseConnection {
/*    */   public MySqlConnection(DatabaseConfiguration paramDatabaseConfiguration) {
/* 10 */     super(paramDatabaseConfiguration);
/*    */   }
/*    */ 
/*    */   
/*    */   public Connection connectToDatabase() {
/* 15 */     Properties properties = new Properties();
/* 16 */     properties.setProperty("useSSL", "false");
/* 17 */     properties.setProperty("user", this.databaseConfiguration.getUser());
/* 18 */     properties.setProperty("password", this.databaseConfiguration.getPassword());
/* 19 */     return DriverManager.getConnection("jdbc:mysql://" + this.databaseConfiguration.getHost() + ":" + this.databaseConfiguration.getPort() + "/" + this.databaseConfiguration.getDatabase() + "?allowMultiQueries=true", properties);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\MySqlConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */