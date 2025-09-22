/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.nms;
/*    */ 
/*    */ import java.io.ByteArrayInputStream;
/*    */ import java.io.ByteArrayOutputStream;
/*    */ import java.io.IOException;
/*    */ import java.util.Base64;
/*    */ import java.util.zip.GZIPInputStream;
/*    */ import java.util.zip.GZIPOutputStream;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.util.io.BukkitObjectInputStream;
/*    */ import org.bukkit.util.io.BukkitObjectOutputStream;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class Base64ItemStack
/*    */ {
/*    */   public static String encode(ItemStack paramItemStack) {
/* 19 */     Base64.Encoder encoder = Base64.getEncoder();
/*    */     try {
/* 21 */       ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
/* 22 */       GZIPOutputStream gZIPOutputStream = new GZIPOutputStream(byteArrayOutputStream);
/* 23 */       BukkitObjectOutputStream bukkitObjectOutputStream = new BukkitObjectOutputStream(gZIPOutputStream);
/* 24 */       bukkitObjectOutputStream.writeObject(paramItemStack);
/* 25 */       bukkitObjectOutputStream.close();
/* 26 */       return encoder.encodeToString(byteArrayOutputStream.toByteArray());
/* 27 */     } catch (IOException iOException) {
/* 28 */       iOException.printStackTrace();
/* 29 */       return null;
/*    */     } 
/*    */   }
/*    */   
/*    */   public static ItemStack decode(String paramString) {
/* 34 */     Base64.Decoder decoder = Base64.getDecoder();
/*    */     try {
/* 36 */       byte[] arrayOfByte = decoder.decode(paramString);
/* 37 */       ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayOfByte);
/* 38 */       GZIPInputStream gZIPInputStream = new GZIPInputStream(byteArrayInputStream);
/* 39 */       BukkitObjectInputStream bukkitObjectInputStream = new BukkitObjectInputStream(gZIPInputStream);
/* 40 */       ItemStack itemStack = (ItemStack)bukkitObjectInputStream.readObject();
/* 41 */       bukkitObjectInputStream.close();
/* 42 */       return itemStack;
/* 43 */     } catch (IOException|ClassNotFoundException iOException) {
/* 44 */       iOException.printStackTrace();
/* 45 */       return null;
/*    */     } 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\nms\Base64ItemStack.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */