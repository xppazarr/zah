/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ import java.util.Objects;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DatabaseConfiguration
/*     */ {
/*     */   private final String tablePrefix;
/*     */   private final String user;
/*     */   private final String password;
/*     */   private final int port;
/*     */   private final String host;
/*     */   private final String database;
/*     */   private final boolean debug;
/*     */   private final DatabaseType databaseType;
/*     */   
/*     */   public DatabaseConfiguration(String paramString1, String paramString2, String paramString3, int paramInt, String paramString4, String paramString5, boolean paramBoolean, DatabaseType paramDatabaseType) {
/*  24 */     this.tablePrefix = paramString1;
/*  25 */     this.user = paramString2;
/*  26 */     this.password = paramString3;
/*  27 */     this.port = paramInt;
/*  28 */     this.host = paramString4;
/*  29 */     this.database = paramString5;
/*  30 */     this.debug = paramBoolean;
/*  31 */     this.databaseType = paramDatabaseType;
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration create(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, DatabaseType paramDatabaseType) {
/*  35 */     return new DatabaseConfiguration(null, paramString1, paramString2, paramInt, paramString3, paramString4, false, paramDatabaseType);
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration create(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4) {
/*  39 */     return new DatabaseConfiguration(null, paramString1, paramString2, paramInt, paramString3, paramString4, false, DatabaseType.MYSQL);
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration create(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, boolean paramBoolean) {
/*  43 */     return new DatabaseConfiguration(null, paramString1, paramString2, paramInt, paramString3, paramString4, paramBoolean, DatabaseType.MYSQL);
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration create(String paramString1, String paramString2, String paramString3, String paramString4, DatabaseType paramDatabaseType) {
/*  47 */     return new DatabaseConfiguration(null, paramString1, paramString2, 3306, paramString3, paramString4, false, paramDatabaseType);
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration create(String paramString1, String paramString2, int paramInt, String paramString3, String paramString4, boolean paramBoolean, DatabaseType paramDatabaseType) {
/*  51 */     return new DatabaseConfiguration(null, paramString1, paramString2, paramInt, paramString3, paramString4, paramBoolean, paramDatabaseType);
/*     */   }
/*     */   
/*     */   public static DatabaseConfiguration sqlite(boolean paramBoolean) {
/*  55 */     return new DatabaseConfiguration(null, null, null, 0, null, null, paramBoolean, DatabaseType.SQLITE);
/*     */   }
/*     */   
/*     */   public String replacePrefix(String paramString) {
/*  59 */     return (this.tablePrefix == null) ? paramString : paramString.replaceAll("%prefix%", this.tablePrefix);
/*     */   }
/*     */   
/*     */   public String getTablePrefix() {
/*  63 */     return this.tablePrefix;
/*     */   }
/*     */   
/*     */   public String getUser() {
/*  67 */     return this.user;
/*     */   }
/*     */   
/*     */   public String getPassword() {
/*  71 */     return this.password;
/*     */   }
/*     */   
/*     */   public int getPort() {
/*  75 */     return this.port;
/*     */   }
/*     */   
/*     */   public String getHost() {
/*  79 */     return this.host;
/*     */   }
/*     */   
/*     */   public String getDatabase() {
/*  83 */     return this.database;
/*     */   }
/*     */   
/*     */   public boolean isDebug() {
/*  87 */     return this.debug;
/*     */   }
/*     */   
/*     */   public DatabaseType getDatabaseType() {
/*  91 */     return this.databaseType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object paramObject) {
/*  96 */     if (this == paramObject) return true; 
/*  97 */     if (paramObject == null || getClass() != paramObject.getClass()) return false; 
/*  98 */     DatabaseConfiguration databaseConfiguration = (DatabaseConfiguration)paramObject;
/*  99 */     return (this.port == databaseConfiguration.port && this.debug == databaseConfiguration.debug && 
/*     */       
/* 101 */       Objects.equals(this.tablePrefix, databaseConfiguration.tablePrefix) && 
/* 102 */       Objects.equals(this.user, databaseConfiguration.user) && 
/* 103 */       Objects.equals(this.password, databaseConfiguration.password) && 
/* 104 */       Objects.equals(this.host, databaseConfiguration.host) && 
/* 105 */       Objects.equals(this.database, databaseConfiguration.database) && this.databaseType == databaseConfiguration.databaseType);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 111 */     return Objects.hash(new Object[] { this.tablePrefix, this.user, this.password, Integer.valueOf(this.port), this.host, this.database, Boolean.valueOf(this.debug), this.databaseType });
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 116 */     return "DatabaseConfiguration{tablePrefix='" + this.tablePrefix + '\'' + ", user='" + this.user + '\'' + ", password='" + this.password + '\'' + ", port=" + this.port + ", host='" + this.host + '\'' + ", database='" + this.database + '\'' + ", debug=" + this.debug + ", databaseType=" + this.databaseType + '}';
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\DatabaseConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */