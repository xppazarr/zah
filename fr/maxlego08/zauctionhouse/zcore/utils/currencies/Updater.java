/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.HttpURLConnection;
/*    */ import java.net.URI;
/*    */ import java.net.URL;
/*    */ import java.util.Properties;
/*    */ import java.util.Scanner;
/*    */ import java.util.logging.Logger;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Updater
/*    */ {
/*    */   public static void checkUpdates() {
/* 20 */     if (!isUpToDate()) {
/* 21 */       Logger.getLogger("CurrenciesAPI").warning("The framework is not up to date, the latest version is " + fetchLatestVersion());
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String getVersion() {
/* 30 */     Properties properties = new Properties();
/*    */     try {
/* 32 */       properties.load(Updater.class.getClassLoader().getResourceAsStream("currenciesapi.properties"));
/* 33 */       return properties.getProperty("version");
/* 34 */     } catch (IOException iOException) {
/* 35 */       throw new RuntimeException(iOException);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static boolean isUpToDate() {
/*    */     try {
/* 45 */       String str = fetchLatestVersion();
/* 46 */       return getVersion().equals(str);
/* 47 */     } catch (Exception exception) {
/* 48 */       return false;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static String fetchLatestVersion() {
/*    */     try {
/* 58 */       URL uRL = URI.create("https://api.github.com/repos/Traqueur-dev/CurrenciesAPI/releases/latest").toURL();
/* 59 */       String str = getString(uRL);
/* 60 */       int i = str.indexOf("\"tag_name\"");
/* 61 */       int j = str.indexOf('"', i + 10) + 1;
/* 62 */       int k = str.indexOf('"', j);
/* 63 */       return str.substring(j, k);
/* 64 */     } catch (Exception exception) {
/* 65 */       return null;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private static String getString(URL paramURL) {
/* 74 */     HttpURLConnection httpURLConnection = (HttpURLConnection)paramURL.openConnection();
/* 75 */     httpURLConnection.setRequestMethod("GET");
/*    */     
/* 77 */     StringBuilder stringBuilder = new StringBuilder(); 
/* 78 */     try { Scanner scanner = new Scanner(httpURLConnection.getInputStream()); 
/* 79 */       try { while (scanner.hasNext()) {
/* 80 */           stringBuilder.append(scanner.nextLine());
/*    */         }
/* 82 */         scanner.close(); } catch (Throwable throwable) { try { scanner.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  }
/* 83 */     finally { httpURLConnection.disconnect(); }
/*    */ 
/*    */     
/* 86 */     return stringBuilder.toString();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\Updater.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */