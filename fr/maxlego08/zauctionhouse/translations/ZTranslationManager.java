/*     */ package fr.maxlego08.zauctionhouse.translations;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.annotations.SerializedName;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.translation.TranslationManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.io.BufferedInputStream;
/*     */ import java.io.File;
/*     */ import java.io.FileOutputStream;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStreamReader;
/*     */ import java.net.HttpURLConnection;
/*     */ import java.net.URL;
/*     */ import java.nio.file.Files;
/*     */ import java.nio.file.Path;
/*     */ import java.nio.file.Paths;
/*     */ import java.nio.file.attribute.FileAttribute;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZTranslationManager implements TranslationManager {
/*     */   private static final String JSON_URL = "https://raw.githubusercontent.com/Maxlego08/minecraft-assets/1.21/assets/minecraft/lang/_list.json";
/*     */   private static final String BASE_URL = "https://raw.githubusercontent.com/Maxlego08/minecraft-assets/1.21/assets/minecraft/lang/";
/*     */   private final ZAuctionPlugin plugin;
/*  33 */   private final Map<String, String> translationToKeys = new HashMap<>();
/*  34 */   private final Map<String, String> keyToMaterials = new HashMap<>();
/*  35 */   private final Map<String, String> keyToTranslations = new HashMap<>();
/*  36 */   private Map<Material, String> materialToKeys = new HashMap<>();
/*     */   
/*     */   public ZTranslationManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  39 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */   
/*     */   public static List<String> findMatchingKeys(Map<String, String> paramMap, String paramString) {
/*  43 */     ArrayList<String> arrayList = new ArrayList();
/*  44 */     for (String str : paramMap.keySet()) {
/*  45 */       if (str.contains(paramString)) {
/*  46 */         arrayList.add(str);
/*     */       }
/*     */     } 
/*  49 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void loadTranslations() {
/*  54 */     if (!NmsVersion.nmsVersion.isNewMaterial())
/*     */       return; 
/*  56 */     File file = new File(this.plugin.getDataFolder(), "langs");
/*  57 */     if (file.exists()) {
/*     */       try {
/*  59 */         loadTranslation();
/*  60 */       } catch (Exception exception) {
/*  61 */         exception.printStackTrace();
/*     */       } 
/*     */       
/*     */       return;
/*     */     } 
/*  66 */     file.mkdirs();
/*     */     
/*  68 */     if (!AuctionConfiguration.enableDownloadAllLangagesFiles)
/*     */       return; 
/*  70 */     ZPlugin.service.execute(() -> {
/*     */           try {
/*     */             List<String> list = fetchFileList();
/*     */ 
/*     */             
/*     */             downloadFiles(list, paramFile);
/*  76 */           } catch (IOException iOException) {
/*     */             iOException.printStackTrace();
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   private void loadTranslation() {
/*  84 */     if (!AuctionConfiguration.enableSearchTranslatedMaterial && !AuctionConfiguration.enableItemStackTranslation) {
/*     */       return;
/*     */     }
/*     */     
/*  88 */     if (AuctionConfiguration.language == null)
/*     */       return; 
/*  90 */     File file = new File(this.plugin.getDataFolder(), "langs/" + AuctionConfiguration.language + ".json");
/*  91 */     if (!file.exists()) {
/*  92 */       this.plugin.getLogger().severe("File langs/" + AuctionConfiguration.language + ".json doesnt exist ! ");
/*     */       
/*     */       return;
/*     */     } 
/*  96 */     this.translationToKeys.clear();
/*  97 */     this.keyToMaterials.clear();
/*  98 */     this.keyToTranslations.clear();
/*     */     
/* 100 */     this.materialToKeys = getTranslationMaterials();
/* 101 */     this.materialToKeys.forEach((paramMaterial, paramString) -> this.keyToMaterials.put(paramString, paramMaterial.name()));
/*     */     
/* 103 */     Map map = (Map)this.plugin.getPersist().load(Map.class, file);
/* 104 */     map.forEach((paramObject1, paramObject2) -> {
/*     */           if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*     */             String str1 = (String)paramObject1;
/*     */             String str2 = (String)paramObject2;
/*     */             if (str1.startsWith("block") || str1.startsWith("item")) {
/*     */               this.translationToKeys.put(str2.toLowerCase(), str1);
/*     */               this.keyToTranslations.put(str1, str2);
/*     */             } 
/*     */           } 
/*     */         });
/*     */   }
/*     */   
/*     */   private Map<Material, String> getTranslationMaterials() {
/* 117 */     String str = "langs/materials-translations-key.json";
/* 118 */     File file = new File(this.plugin.getDataFolder(), str);
/* 119 */     if (!file.exists()) {
/* 120 */       this.plugin.saveResource(str, false);
/*     */     }
/*     */     
/* 123 */     HashMap<Object, Object> hashMap = new HashMap<>();
/* 124 */     Map map = (Map)this.plugin.getPersist().load(Map.class, file);
/* 125 */     map.forEach((paramObject1, paramObject2) -> {
/*     */           if (paramObject1 instanceof String && paramObject2 instanceof String) {
/*     */             try {
/*     */               paramMap.put(Material.valueOf((String)paramObject1), (String)paramObject2);
/* 129 */             } catch (Exception exception) {}
/*     */           }
/*     */         });
/*     */     
/* 133 */     return (Map)hashMap;
/*     */   }
/*     */   
/*     */   private List<String> fetchFileList() {
/* 137 */     URL uRL = new URL("https://raw.githubusercontent.com/Maxlego08/minecraft-assets/1.21/assets/minecraft/lang/_list.json");
/* 138 */     HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
/* 139 */     httpURLConnection.setRequestMethod("GET");
/*     */     
/* 141 */     InputStreamReader inputStreamReader = new InputStreamReader(new BufferedInputStream(httpURLConnection.getInputStream())); 
/* 142 */     try { Gson gson = new Gson();
/* 143 */       JsonData jsonData = (JsonData)gson.fromJson(inputStreamReader, JsonData.class);
/* 144 */       List<String> list = jsonData.getFiles();
/* 145 */       inputStreamReader.close(); return list; }
/*     */     catch (Throwable throwable) { try { inputStreamReader.close(); }
/*     */       catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 149 */      } private void downloadFiles(List<String> paramList, File paramFile) { Path path = paramFile.toPath();
/* 150 */     if (!Files.exists(path, new java.nio.file.LinkOption[0])) {
/*     */       try {
/* 152 */         Files.createDirectories(path, (FileAttribute<?>[])new FileAttribute[0]);
/* 153 */       } catch (IOException iOException) {
/* 154 */         iOException.printStackTrace();
/*     */         
/*     */         return;
/*     */       } 
/*     */     }
/* 159 */     for (Iterator<String> iterator = paramList.iterator(); iterator.hasNext(); ) { String str1 = iterator.next();
/* 160 */       String str2 = "https://raw.githubusercontent.com/Maxlego08/minecraft-assets/1.21/assets/minecraft/lang/" + str1;
/* 161 */       ZPlugin.service.execute(() -> {
/*     */             try {
/*     */               downloadFile(paramString1, Paths.get(paramFile.getAbsolutePath(), new String[] { paramString2.replace("_", "-") }).toString());
/* 164 */             } catch (IOException iOException) {
/*     */               this.plugin.getLogger().severe("Failed to download " + paramString2 + ": " + iOException.getMessage());
/*     */             } 
/*     */           }); }
/*     */      }
/*     */ 
/*     */   
/*     */   private void downloadFile(String paramString1, String paramString2) {
/* 172 */     URL uRL = new URL(paramString1);
/* 173 */     HttpURLConnection httpURLConnection = (HttpURLConnection)uRL.openConnection();
/* 174 */     httpURLConnection.setRequestMethod("GET");
/*     */     
/* 176 */     BufferedInputStream bufferedInputStream = new BufferedInputStream(httpURLConnection.getInputStream()); try { FileOutputStream fileOutputStream = new FileOutputStream(paramString2); 
/* 177 */       try { byte[] arrayOfByte = new byte[1024];
/*     */         int i;
/* 179 */         while ((i = bufferedInputStream.read(arrayOfByte, 0, 1024)) != -1) {
/* 180 */           fileOutputStream.write(arrayOfByte, 0, i);
/*     */         }
/* 182 */         fileOutputStream.close(); } catch (Throwable throwable) { try { fileOutputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }  throw throwable; }  bufferedInputStream.close(); } catch (Throwable throwable) { try { bufferedInputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }
/*     */        throw throwable; }
/* 184 */      if (AuctionConfiguration.enableDebugMode) {
/* 185 */       this.plugin.getLogger().info("Download file " + paramString1 + " into " + paramString2);
/*     */     }
/*     */     
/* 188 */     if (AuctionConfiguration.language != null && paramString2.contains(AuctionConfiguration.language)) {
/* 189 */       loadTranslation();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String replaceValue(String paramString) {
/* 196 */     if (this.translationToKeys.containsKey(paramString)) {
/* 197 */       String str = this.translationToKeys.get(paramString);
/* 198 */       if (str != null && this.keyToMaterials.containsKey(str)) {
/* 199 */         return this.keyToMaterials.get(str);
/*     */       }
/*     */     } 
/*     */     
/* 203 */     return paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public String translateItemStack(ItemStack paramItemStack) {
/* 209 */     if (paramItemStack == null) return "ItemStack is NULL";
/*     */     
/* 211 */     if (paramItemStack.hasItemMeta() && paramItemStack.getItemMeta().hasDisplayName()) {
/* 212 */       return paramItemStack.getItemMeta().getDisplayName().replace("%", "");
/*     */     }
/*     */     
/* 215 */     Material material = paramItemStack.getType();
/* 216 */     if (AuctionConfiguration.enableItemStackTranslation && 
/* 217 */       this.materialToKeys.containsKey(material)) {
/* 218 */       String str1 = this.materialToKeys.get(material);
/* 219 */       if (str1 != null && this.keyToTranslations.containsKey(str1)) {
/* 220 */         return this.keyToTranslations.get(str1);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 225 */     String str = material.name().replace("_", " ").toLowerCase();
/* 226 */     return str.substring(0, 1).toUpperCase() + str.substring(1);
/*     */   }
/*     */   
/*     */   static class JsonData {
/*     */     @SerializedName("files")
/*     */     private List<String> files;
/*     */     
/*     */     public List<String> getFiles() {
/* 234 */       return this.files;
/*     */     }
/*     */     
/*     */     public void setFiles(List<String> param1List) {
/* 238 */       this.files = param1List;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\translations\ZTranslationManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */