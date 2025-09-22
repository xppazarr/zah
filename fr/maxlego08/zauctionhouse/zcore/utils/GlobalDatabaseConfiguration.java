/*     */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ public class GlobalDatabaseConfiguration
/*     */ {
/*     */   private final FileConfiguration pluginConfiguration;
/*     */   private final FileConfiguration globalConfiguration;
/*     */   
/*     */   public GlobalDatabaseConfiguration(FileConfiguration paramFileConfiguration) {
/*  14 */     this.pluginConfiguration = paramFileConfiguration;
/*  15 */     File file = new File("database-configuration.yml");
/*  16 */     if (file.exists()) {
/*  17 */       this.globalConfiguration = (FileConfiguration)YamlConfiguration.loadConfiguration(file);
/*     */     } else {
/*  19 */       this.globalConfiguration = null;
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
/*     */   public String getHost() {
/*  32 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getString("sql.host") : this.globalConfiguration.getString("database-configuration.host", this.pluginConfiguration.getString("sql.host", "192.168.10.10"));
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
/*     */   public int getPort() {
/*  44 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getInt("sql.port") : this.globalConfiguration.getInt("sql.port", this.pluginConfiguration.getInt("sql.port", 3306));
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
/*     */   public String getDatabase() {
/*  56 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getString("sql.database") : this.globalConfiguration.getString("database-configuration.database", this.pluginConfiguration.getString("sql.database", "homestead"));
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
/*     */   public String getUser() {
/*  68 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getString("sql.user") : this.globalConfiguration.getString("database-configuration.user", this.pluginConfiguration.getString("sql.user", "homestead"));
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
/*     */   public String getPassword() {
/*  80 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getString("sql.password") : this.globalConfiguration.getString("database-configuration.password", this.pluginConfiguration.getString("sql.password", "secret"));
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
/*     */   public String getTablePrefix() {
/*  92 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getString("sql.prefix") : this.globalConfiguration.getString("database-configuration.prefix", this.pluginConfiguration.getString("sql.prefix", "groupez_"));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDebug() {
/* 103 */     return (this.globalConfiguration == null) ? this.pluginConfiguration.getBoolean("sql.debug") : this.globalConfiguration.getBoolean("sql.debug", this.pluginConfiguration.getBoolean("sql.debug", false));
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\GlobalDatabaseConfiguration.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */