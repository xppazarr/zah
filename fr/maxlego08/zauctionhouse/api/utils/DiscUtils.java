/*     */ package fr.maxlego08.zauctionhouse.api.utils;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.net.URL;
/*     */ import java.nio.channels.Channels;
/*     */ import java.nio.channels.ReadableByteChannel;
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
/*     */ public class DiscUtils
/*     */ {
/*     */   private static final String UTF8 = "UTF-8";
/*     */   
/*     */   public static byte[] readBytes(File paramFile) {
/*  27 */     int i = (int)paramFile.length();
/*  28 */     byte[] arrayOfByte = new byte[i];
/*  29 */     FileInputStream fileInputStream = new FileInputStream(paramFile);
/*  30 */     int j = 0;
/*  31 */     while (j < i) {
/*  32 */       j += fileInputStream.read(arrayOfByte, j, i - j);
/*     */     }
/*  34 */     fileInputStream.close();
/*  35 */     return arrayOfByte;
/*     */   }
/*     */   
/*     */   public static void writeBytes(File paramFile, byte[] paramArrayOfbyte) {
/*  39 */     FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/*  40 */     fileOutputStream.write(paramArrayOfbyte);
/*  41 */     fileOutputStream.close();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void write(File paramFile, String paramString) {
/*  49 */     writeBytes(paramFile, utf8(paramString));
/*     */   }
/*     */   
/*     */   public static String read(File paramFile) {
/*  53 */     return utf8(readBytes(paramFile));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean writeCatch(File paramFile, String paramString) {
/*     */     try {
/*  62 */       write(paramFile, paramString);
/*  63 */       return true;
/*  64 */     } catch (Exception exception) {
/*  65 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String readCatch(File paramFile) {
/*     */     try {
/*  71 */       return read(paramFile);
/*  72 */     } catch (IOException iOException) {
/*  73 */       iOException.printStackTrace();
/*  74 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean downloadUrl(String paramString, File paramFile) {
/*     */     try {
/*  84 */       URL uRL = new URL(paramString);
/*  85 */       ReadableByteChannel readableByteChannel = Channels.newChannel(uRL.openStream());
/*     */       
/*  87 */       FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
/*  88 */       fileOutputStream.getChannel().transferFrom(readableByteChannel, 0L, 16777216L);
/*  89 */       return true;
/*  90 */     } catch (Exception exception) {
/*  91 */       exception.printStackTrace();
/*  92 */       return false;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static boolean downloadUrl(String paramString1, String paramString2) {
/*  97 */     return downloadUrl(paramString1, new File(paramString2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static boolean deleteRecursive(File paramFile) {
/* 105 */     if (!paramFile.exists())
/* 106 */       throw new FileNotFoundException(paramFile.getAbsolutePath()); 
/* 107 */     boolean bool = true;
/* 108 */     if (paramFile.isDirectory()) {
/* 109 */       for (File file : paramFile.listFiles()) {
/* 110 */         bool = (bool && deleteRecursive(file)) ? true : false;
/*     */       }
/*     */     }
/* 113 */     return (bool && paramFile.delete());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] utf8(String paramString) {
/*     */     try {
/* 122 */       return paramString.getBytes("UTF-8");
/* 123 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 124 */       unsupportedEncodingException.printStackTrace();
/* 125 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String utf8(byte[] paramArrayOfbyte) {
/*     */     try {
/* 131 */       return new String(paramArrayOfbyte, "UTF-8");
/* 132 */     } catch (UnsupportedEncodingException unsupportedEncodingException) {
/* 133 */       unsupportedEncodingException.printStackTrace();
/* 134 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\DiscUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */