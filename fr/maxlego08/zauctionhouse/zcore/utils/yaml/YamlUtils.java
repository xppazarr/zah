/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.yaml;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import java.io.File;
/*    */ import java.util.ArrayList;
/*    */ import java.util.HashMap;
/*    */ import java.util.List;
/*    */ import java.util.Map;
/*    */ import org.bukkit.configuration.file.FileConfiguration;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ 
/*    */ public abstract class YamlUtils
/*    */   extends ZUtils
/*    */ {
/*    */   protected final transient ZAuctionPlugin plugin;
/* 19 */   protected List<String> bannedWorlds = new ArrayList<>();
/* 20 */   protected Map<String, Long> permissionSells = new HashMap<>();
/* 21 */   protected Map<String, Long> permissionExpiration = new HashMap<>();
/* 22 */   protected Map<String, Map<String, Long>> permissionEconomies = new HashMap<>();
/*    */ 
/*    */   
/*    */   public YamlUtils(ZAuctionPlugin paramZAuctionPlugin) {
/* 26 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected FileConfiguration getConfig() {
/* 33 */     return this.plugin.getConfig();
/*    */   }
/*    */   
/*    */   protected YamlConfiguration getConfig(File paramFile) {
/* 37 */     if (paramFile == null) return null; 
/* 38 */     return YamlConfiguration.loadConfiguration(paramFile);
/*    */   }
/*    */   
/*    */   protected YamlConfiguration getConfig(String paramString) {
/* 42 */     File file = new File(this.plugin.getDataFolder() + "/" + paramString);
/* 43 */     if (!file.exists()) return null; 
/* 44 */     return getConfig(file);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void info(String paramString) {
/* 53 */     Logger.info(paramString);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void success(String paramString) {
/* 62 */     Logger.info(paramString, Logger.LogType.SUCCESS);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void error(String paramString) {
/* 71 */     Logger.info(paramString, Logger.LogType.ERROR);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected void warn(String paramString) {
/* 80 */     Logger.info(paramString, Logger.LogType.WARNING);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\yaml\YamlUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */