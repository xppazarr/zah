/*     */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*     */ 
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.URL;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerSkin
/*     */ {
/*  26 */   private static final Map<String, String> textures = new HashMap<>();
/*  27 */   private static final ExecutorService pool = Executors.newCachedThreadPool();
/*     */   
/*     */   public static String getTexture(Player paramPlayer) {
/*  30 */     if (textures.containsKey(paramPlayer.getName())) {
/*  31 */       return textures.get(paramPlayer.getName());
/*     */     }
/*  33 */     String[] arrayOfString = getFromPlayer(paramPlayer);
/*     */     try {
/*  35 */       String str = arrayOfString[0];
/*  36 */       textures.put(paramPlayer.getName(), str);
/*  37 */       return str;
/*  38 */     } catch (Exception exception) {
/*     */       
/*  40 */       return null;
/*     */     } 
/*     */   }
/*     */   public static String getTexture(String paramString) {
/*  44 */     if (textures.containsKey(paramString)) {
/*  45 */       return textures.get(paramString);
/*     */     }
/*     */     
/*  48 */     Player player = Bukkit.getPlayer(paramString);
/*  49 */     if (player != null) {
/*  50 */       return getTexture(player);
/*     */     }
/*     */     
/*  53 */     pool.execute(() -> {
/*     */           String[] arrayOfString = getFromName(paramString);
/*     */           try {
/*     */             String str = arrayOfString[0];
/*     */             textures.put(paramString, str);
/*  58 */           } catch (Exception exception) {}
/*     */         });
/*     */     
/*  61 */     return null;
/*     */   }
/*     */   
/*     */   public static String[] getFromPlayer(Player paramPlayer) {
/*  65 */     GameProfile gameProfile = getProfile(paramPlayer);
/*  66 */     Property property = gameProfile.getProperties().get("textures").iterator().next();
/*  67 */     String str1 = property.getValue();
/*  68 */     String str2 = property.getSignature();
/*     */     
/*  70 */     return new String[] { str1, str2 };
/*     */   }
/*     */ 
/*     */   
/*     */   public static String[] getFromName(String paramString) {
/*     */     try {
/*  76 */       URL uRL1 = new URL("https://api.mojang.com/users/profiles/minecraft/" + paramString);
/*  77 */       InputStreamReader inputStreamReader1 = new InputStreamReader(uRL1.openStream());
/*  78 */       String str1 = (new JsonParser()).parse(inputStreamReader1).getAsJsonObject().get("id").getAsString();
/*     */       
/*  80 */       URL uRL2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + str1 + "?unsigned=false");
/*  81 */       InputStreamReader inputStreamReader2 = new InputStreamReader(uRL2.openStream());
/*  82 */       JsonObject jsonObject = (new JsonParser()).parse(inputStreamReader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
/*  83 */       String str2 = jsonObject.get("value").getAsString();
/*  84 */       String str3 = jsonObject.get("signature").getAsString();
/*     */       
/*  86 */       return new String[] { str2, str3 };
/*  87 */     } catch (IOException iOException) {
/*  88 */       System.err.println("Could not get skin data from session servers!");
/*  89 */       iOException.printStackTrace();
/*  90 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static GameProfile getProfile(Player paramPlayer) {
/*     */     try {
/*  97 */       Object object = paramPlayer.getClass().getMethod("getHandle", new Class[0]).invoke(paramPlayer, new Object[0]);
/*  98 */       return (GameProfile)object.getClass().getMethod(getMethodName(), new Class[0]).invoke(object, new Object[0]);
/*  99 */     } catch (IllegalAccessException|IllegalArgumentException|java.lang.reflect.InvocationTargetException|NoSuchMethodException|SecurityException illegalAccessException) {
/*     */       
/* 101 */       illegalAccessException.printStackTrace();
/*     */ 
/*     */       
/* 104 */       return null;
/*     */     } 
/*     */   }
/*     */   public static String getMethodName() {
/* 108 */     double d = NmsVersion.nmsVersion.getVersion();
/* 109 */     if (d >= NmsVersion.V_1_19.getVersion() && d <= NmsVersion.V_1_19_2.getVersion())
/* 110 */       return "fz"; 
/* 111 */     if (d >= NmsVersion.V_1_18.getVersion() && d <= NmsVersion.V_1_18_2.getVersion()) {
/* 112 */       return "fp";
/*     */     }
/* 114 */     return "getProfile";
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\PlayerSkin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */