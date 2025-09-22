/*     */ package fr.maxlego08.zauctionhouse.api.utils;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Folder;
/*     */ import java.io.File;
/*     */ import java.lang.reflect.Type;
/*     */ import org.bukkit.plugin.Plugin;
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
/*     */ public class Persist
/*     */ {
/*     */   private final Plugin plugin;
/*     */   private final Logger logger;
/*     */   private final Gson gson;
/*     */   
/*     */   public Persist(Plugin paramPlugin, Logger paramLogger, Gson paramGson) {
/*  30 */     this.plugin = paramPlugin;
/*  31 */     this.logger = paramLogger;
/*  32 */     this.gson = paramGson;
/*     */   }
/*     */   
/*     */   public static String getName(Class<?> paramClass) {
/*  36 */     return paramClass.getSimpleName().toLowerCase();
/*     */   }
/*     */   
/*     */   public static String getName(Object paramObject) {
/*  40 */     return getName(paramObject.getClass());
/*     */   }
/*     */   
/*     */   public static String getName(Type paramType) {
/*  44 */     return getName(paramType.getClass());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public File getFile(String paramString) {
/*  52 */     return new File(this.plugin.getDataFolder(), paramString + ".json");
/*     */   }
/*     */   
/*     */   public File getFile(Class<?> paramClass) {
/*  56 */     return getFile(getName(paramClass));
/*     */   }
/*     */   
/*     */   public File getFile(Object paramObject) {
/*  60 */     return getFile(getName(paramObject));
/*     */   }
/*     */   
/*     */   public File getFile(Type paramType) {
/*  64 */     return getFile(getName(paramType));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T loadOrSaveDefault(T paramT, Class<T> paramClass) {
/*  70 */     return loadOrSaveDefault(paramT, paramClass, getFile(paramClass));
/*     */   }
/*     */   
/*     */   public <T> T loadOrSaveDefault(T paramT, Class<T> paramClass, String paramString) {
/*  74 */     return loadOrSaveDefault(paramT, paramClass, getFile(paramString));
/*     */   }
/*     */   
/*     */   public <T> T loadOrSaveDefault(T paramT, Class<T> paramClass, Folder paramFolder, String paramString) {
/*  78 */     return loadOrSaveDefault(paramT, paramClass, getFile(paramFolder.toFolder() + File.separator + paramString));
/*     */   }
/*     */   
/*     */   public <T> T loadOrSaveDefault(T paramT, Class<T> paramClass, File paramFile) {
/*  82 */     if (!paramFile.exists()) {
/*  83 */       this.logger.log("Creating default: " + paramFile, Logger.LogType.SUCCESS);
/*  84 */       save(paramT, paramFile);
/*  85 */       return paramT;
/*     */     } 
/*     */     
/*  88 */     T t = (T)load((Class)paramClass, paramFile);
/*     */     
/*  90 */     if (t == null) {
/*  91 */       this.logger.log("Using default as I failed to load: " + paramFile, Logger.LogType.WARNING);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  97 */       File file = new File(paramFile.getPath() + "_bad");
/*  98 */       if (file.exists())
/*  99 */         file.delete(); 
/* 100 */       this.logger.log("Backing up copy of bad file to: " + file, Logger.LogType.WARNING);
/*     */       
/* 102 */       paramFile.renameTo(file);
/*     */       
/* 104 */       return paramT;
/*     */     } 
/*     */     
/* 107 */     this.logger.log(paramFile.getAbsolutePath() + " loaded successfully !", Logger.LogType.SUCCESS);
/*     */ 
/*     */ 
/*     */     
/* 111 */     return t;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean save(Object paramObject) {
/* 117 */     return save(paramObject, getFile(paramObject));
/*     */   }
/*     */   
/*     */   public boolean save(Object paramObject, String paramString) {
/* 121 */     return save(paramObject, getFile(paramString));
/*     */   }
/*     */   
/*     */   public boolean save(Object paramObject, Folder paramFolder, String paramString) {
/* 125 */     return save(paramObject, getFile(paramFolder.toFolder() + File.separator + paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean save(Object paramObject, File paramFile) {
/*     */     try {
/* 132 */       boolean bool = DiscUtils.writeCatch(paramFile, this.gson.toJson(paramObject));
/* 133 */       if (AuctionConfiguration.enableLogFileSaveInformations) {
/* 134 */         this.logger.log(paramFile.getAbsolutePath() + " successfully saved !", Logger.LogType.SUCCESS);
/*     */       }
/* 136 */       return bool;
/*     */     }
/* 138 */     catch (Exception exception) {
/*     */       
/* 140 */       this.logger.log("cannot save file " + paramFile.getAbsolutePath(), Logger.LogType.ERROR);
/* 141 */       exception.printStackTrace();
/*     */       
/* 143 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T load(Class<T> paramClass) {
/* 150 */     return load(paramClass, getFile(paramClass));
/*     */   }
/*     */   
/*     */   public <T> T load(Class<T> paramClass, String paramString) {
/* 154 */     return load(paramClass, getFile(paramString));
/*     */   }
/*     */   
/*     */   public <T> T load(Class<T> paramClass, File paramFile) {
/* 158 */     String str = DiscUtils.readCatch(paramFile);
/* 159 */     if (str == null) {
/* 160 */       this.logger.log("Impossible de trouver le contenu du fichier.");
/* 161 */       return null;
/*     */     } 
/*     */     
/*     */     try {
/* 165 */       return (T)this.gson.fromJson(str, paramClass);
/*     */     }
/* 167 */     catch (Exception exception) {
/*     */ 
/*     */       
/* 170 */       exception.printStackTrace();
/*     */ 
/*     */       
/* 173 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T load(Type paramType, String paramString) {
/* 179 */     return load(paramType, getFile(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public <T> T load(Type paramType, File paramFile) {
/* 184 */     String str = DiscUtils.readCatch(paramFile);
/* 185 */     if (str == null) {
/* 186 */       return null;
/*     */     }
/*     */     
/*     */     try {
/* 190 */       return (T)this.gson.fromJson(str, paramType);
/* 191 */     } catch (Exception exception) {
/*     */ 
/*     */       
/* 194 */       this.logger.log(exception.getMessage(), Logger.LogType.ERROR);
/*     */ 
/*     */       
/* 197 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\ap\\utils\Persist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */