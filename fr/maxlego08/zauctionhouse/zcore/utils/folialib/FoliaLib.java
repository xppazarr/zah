/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums.ImplementationType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl.PlatformScheduler;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.InvalidTickDelayNotifier;
/*     */ import java.util.logging.Logger;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class FoliaLib
/*     */ {
/*     */   private final JavaPlugin plugin;
/*     */   private final ImplementationType implementationType;
/*     */   private final PlatformScheduler scheduler;
/*     */   
/*     */   public FoliaLib(JavaPlugin paramJavaPlugin) {
/*  19 */     this.plugin = paramJavaPlugin;
/*     */ 
/*     */     
/*  22 */     ImplementationType implementationType = ImplementationType.UNKNOWN; ImplementationType[] arrayOfImplementationType; int i;
/*     */     byte b;
/*  24 */     for (arrayOfImplementationType = ImplementationType.values(), i = arrayOfImplementationType.length, b = 0; b < i; ) { ImplementationType implementationType1 = arrayOfImplementationType[b];
/*     */       
/*  26 */       if (!implementationType1.selfCheck()) {
/*     */         b++; continue;
/*  28 */       }  implementationType = implementationType1; }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  33 */     this.implementationType = implementationType;
/*  34 */     this.scheduler = createServerImpl(this.implementationType.getImplementationClassName());
/*     */ 
/*     */     
/*  37 */     if (this.scheduler == null) {
/*  38 */       throw new IllegalStateException("Failed to create server implementation. Please report this to the FoliaLib GitHub issues page. Forks of server software may not all be supported. If you are using an unofficial fork, please report this to the fork's developers first.");
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  47 */     String str = "com,tcoded,folialib,".replace(",", ".");
/*  48 */     if (getClass().getName().startsWith(str)) {
/*  49 */       Logger logger = this.plugin.getLogger();
/*  50 */       logger.severe("****************************************************************");
/*  51 */       logger.severe("FoliaLib is not be relocated correctly! This will cause conflicts");
/*  52 */       logger.severe("with other plugins using FoliaLib. Please contact the developers");
/*  53 */       logger.severe(String.format("of '%s' and inform them of this issue immediately!", new Object[] { this.plugin.getDescription().getName() }));
/*  54 */       logger.severe("****************************************************************");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ImplementationType getImplType() {
/*  62 */     return this.implementationType;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public PlatformScheduler getImpl() {
/*  71 */     return getScheduler();
/*     */   }
/*     */   
/*     */   public PlatformScheduler getScheduler() {
/*  75 */     return this.scheduler;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFolia() {
/*  80 */     return (this.implementationType == ImplementationType.FOLIA);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPaper() {
/*  85 */     return (this.implementationType == ImplementationType.PAPER || this.implementationType == ImplementationType.LEGACY_PAPER);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isSpigot() {
/*  90 */     return (this.implementationType == ImplementationType.SPIGOT || this.implementationType == ImplementationType.LEGACY_SPIGOT);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isUnsupported() {
/*  95 */     return (this.implementationType == ImplementationType.UNKNOWN);
/*     */   }
/*     */   
/*     */   public JavaPlugin getPlugin() {
/*  99 */     return this.plugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void disableInvalidTickValueWarning() {
/* 106 */     InvalidTickDelayNotifier.disableNotifications = true;
/*     */   }
/*     */ 
/*     */   
/*     */   public void enableInvalidTickValueDebug() {
/* 111 */     InvalidTickDelayNotifier.debugMode = true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private PlatformScheduler createServerImpl(String paramString) {
/* 117 */     String str = getClass().getPackage().getName() + ".impl.";
/*     */     
/*     */     try {
/* 120 */       return Class.forName(str + paramString)
/* 121 */         .getConstructor(new Class[] { getClass()
/* 122 */           }).newInstance(new Object[] { this });
/* 123 */     } catch (InstantiationException|ClassNotFoundException|NoSuchMethodException|java.lang.reflect.InvocationTargetException|IllegalAccessException instantiationException) {
/*     */       
/* 125 */       instantiationException.printStackTrace();
/*     */ 
/*     */       
/* 128 */       return null;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\FoliaLib.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */