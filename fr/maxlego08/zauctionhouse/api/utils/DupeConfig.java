/*    */ package fr.maxlego08.zauctionhouse.api.utils;
/*    */ 
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ 
/*    */ public class DupeConfig
/*    */ {
/*    */   private final boolean enable;
/*    */   private final boolean enableWebhook;
/*    */   private final boolean enableLog;
/*    */   private final String discordWebhookUrl;
/*    */   private final String discordWebhookContent;
/*    */   private final String logContent;
/*    */   
/*    */   public DupeConfig(YamlConfiguration paramYamlConfiguration) {
/* 15 */     this.enable = paramYamlConfiguration.getBoolean("antiDupe.enable", true);
/* 16 */     this.enableWebhook = paramYamlConfiguration.getBoolean("antiDupe.discordWebhook.enable", true);
/* 17 */     this.enableLog = paramYamlConfiguration.getBoolean("antiDupe.consoleLog.enable", true);
/* 18 */     this.discordWebhookUrl = paramYamlConfiguration.getString("antiDupe.discordWebhook.url", "");
/* 19 */     this.discordWebhookContent = paramYamlConfiguration.getString("antiDupe.discordWebhook.content", "");
/* 20 */     this.logContent = paramYamlConfiguration.getString("antiDupe.consoleLog.content", "");
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 30 */     return "DupeConfig [enable=" + this.enable + ", enableWebhook=" + this.enableWebhook + ", enableLog=" + this.enableLog + ", discordWebhookUrl=" + this.discordWebhookUrl + ", discordWebhookContent=" + this.discordWebhookContent + ", logContent=" + this.logContent + "]";
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnable() {
/* 39 */     return this.enable;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnableWebhook() {
/* 46 */     return this.enableWebhook;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean isEnableLog() {
/* 53 */     return this.enableLog;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDiscordWebhookUrl() {
/* 60 */     return this.discordWebhookUrl;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getDiscordWebhookContent() {
/* 67 */     return this.discordWebhookContent;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getLogContent() {
/* 74 */     return this.logContent;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\DupeConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */