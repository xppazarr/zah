/*     */ package fr.maxlego08.zauctionhouse.api.utils;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.DiscordWebhook;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.io.IOException;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class DiscordWebhookConfig
/*     */   extends ZUtils
/*     */ {
/*     */   private final String url;
/*     */   private boolean isEnable;
/*     */   private final String content;
/*     */   private DiscordWebhook.EmbedObject embed;
/*     */   
/*     */   public DiscordWebhookConfig(String paramString1, boolean paramBoolean, String paramString2, DiscordWebhook.EmbedObject paramEmbedObject) {
/*  24 */     this.url = paramString1;
/*  25 */     this.isEnable = paramBoolean;
/*  26 */     this.content = paramString2;
/*  27 */     this.embed = paramEmbedObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public DiscordWebhookConfig(YamlConfiguration paramYamlConfiguration) {
/*  32 */     this.url = paramYamlConfiguration.getString("discordWebhook.url", "https://discord.com/api/webhooks/xxxxx/xxxxxxxxxxxx");
/*  33 */     this.isEnable = paramYamlConfiguration.getBoolean("discordWebhook.isEnable", false);
/*  34 */     this.content = paramYamlConfiguration.getString("discordWebhook.content", null);
/*     */     try {
/*  36 */       this.embed = new DiscordWebhook.EmbedObject(paramYamlConfiguration);
/*  37 */     } catch (Exception exception) {
/*  38 */       exception.printStackTrace();
/*     */     } 
/*     */     
/*  41 */     if (this.isEnable) {
/*  42 */       ZPlugin.service.execute(() -> {
/*     */             if (checkWebhookExists(this.url)) {
/*     */               Logger.info("URL of your Discord Webhook is valid!", Logger.LogType.SUCCESS);
/*     */             } else {
/*     */               this.isEnable = false;
/*     */               Logger.info("Your discord webhook URL is not found, disable the discord webhook configuration.", Logger.LogType.ERROR);
/*     */             } 
/*     */           });
/*     */     }
/*     */   }
/*     */   
/*     */   private boolean checkWebhookExists(String paramString) {
/*     */     try {
/*  55 */       URL uRL = new URL(paramString);
/*  56 */       HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
/*  57 */       httpURLConnection.setRequestMethod("GET");
/*  58 */       httpURLConnection.setConnectTimeout(5000);
/*  59 */       httpURLConnection.setReadTimeout(5000);
/*     */       
/*  61 */       int i = httpURLConnection.getResponseCode();
/*     */       
/*  63 */       return (i == 200);
/*  64 */     } catch (IOException iOException) {
/*  65 */       iOException.printStackTrace();
/*  66 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getUrl() {
/*  74 */     return this.url;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isEnable() {
/*  81 */     return this.isEnable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getContent() {
/*  88 */     return this.content;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DiscordWebhook.EmbedObject getEmbed() {
/*  95 */     return this.embed;
/*     */   }
/*     */ 
/*     */   
/*     */   public void send(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem) {
/* 100 */     if (this.isEnable) {
/*     */       
/* 102 */       DiscordWebhook discordWebhook = new DiscordWebhook(paramAuctionPlugin, this.url);
/*     */       
/* 104 */       if (this.content != null) {
/* 105 */         discordWebhook.setContent(this.content);
/*     */       }
/* 107 */       if (this.embed != null) {
/* 108 */         discordWebhook.addEmbed(this.embed);
/*     */       }
/*     */       
/* 111 */       runAsync(() -> {
/*     */             try {
/*     */               paramDiscordWebhook.execute(paramAuctionItem);
/* 114 */             } catch (IOException iOException) {
/*     */               iOException.printStackTrace();
/*     */             } 
/*     */           });
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
/*     */   public String toString() {
/* 130 */     return "DiscordWebhookConfig [url=" + this.url + ", isEnable=" + this.isEnable + ", content=" + this.content + ", embed=" + this.embed + "]";
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\DiscordWebhookConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */