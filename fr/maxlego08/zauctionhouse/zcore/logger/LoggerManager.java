/*     */ package fr.maxlego08.zauctionhouse.zcore.logger;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.io.BufferedWriter;
/*     */ import java.io.File;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.text.SimpleDateFormat;
/*     */ import java.util.Date;
/*     */ import java.util.Locale;
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
/*     */ public class LoggerManager
/*     */   extends ZUtils
/*     */ {
/*     */   private static volatile LoggerManager instance;
/*     */   private File logFile;
/*     */   
/*     */   public static LoggerManager getInstance() {
/*  36 */     if (instance == null) {
/*  37 */       synchronized (LoggerManager.class) {
/*  38 */         if (instance == null) {
/*  39 */           instance = new LoggerManager();
/*     */         }
/*     */       } 
/*     */     }
/*  43 */     return instance;
/*     */   }
/*     */   
/*     */   public void load(ZAuctionPlugin paramZAuctionPlugin) {
/*  47 */     if (AuctionConfiguration.useLogInFile) {
/*  48 */       File file = new File(paramZAuctionPlugin.getDataFolder() + "/logs");
/*  49 */       if (!file.exists()) file.mkdirs(); 
/*  50 */       this.logFile = new File(paramZAuctionPlugin.getDataFolder() + "/logs/" + getDateNow() + ".log");
/*  51 */       if (!this.logFile.exists()) {
/*     */         try {
/*  53 */           this.logFile.createNewFile();
/*  54 */           paramZAuctionPlugin.getLogger().info(this.logFile.getAbsolutePath() + " has been successfully created !");
/*  55 */         } catch (IOException iOException) {
/*  56 */           iOException.printStackTrace();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void log(String paramString, Object... paramVarArgs) {
/*  64 */     paramString = String.format(paramString, paramVarArgs);
/*     */     
/*  66 */     if (AuctionConfiguration.useLog) {
/*     */       
/*  68 */       if (AuctionConfiguration.useLogInFile) {
/*     */         
/*  70 */         if (this.logFile == null)
/*     */           return; 
/*  72 */         String str = paramString;
/*  73 */         runAsync(() -> {
/*     */               try {
/*     */                 FileWriter fileWriter = new FileWriter(this.logFile, true);
/*     */                 
/*     */                 BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
/*     */                 
/*     */                 bufferedWriter.newLine();
/*     */                 bufferedWriter.write("[" + getHour() + "] > " + paramString);
/*     */                 bufferedWriter.close();
/*     */                 fileWriter.close();
/*  83 */               } catch (IOException iOException) {
/*     */                 iOException.printStackTrace();
/*     */               } 
/*     */             });
/*     */       } 
/*  88 */       Logger.info(paramString, Logger.LogType.INFO);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDateNow() {
/*  94 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
/*  95 */     Date date = new Date();
/*  96 */     return simpleDateFormat.format(date);
/*     */   }
/*     */   
/*     */   private String getHour() {
/* 100 */     SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss", Locale.FRANCE);
/* 101 */     Date date = new Date();
/* 102 */     return simpleDateFormat.format(date);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcore\logger\LoggerManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */