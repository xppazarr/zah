/*     */ package fr.maxlego08.zauctionhouse.zcore;
/*     */ 
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Savable;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.command.CommandManager;
/*     */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*     */ import fr.maxlego08.zauctionhouse.inventory.VInventory;
/*     */ import fr.maxlego08.zauctionhouse.inventory.ZInventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.listener.AdapterListener;
/*     */ import fr.maxlego08.zauctionhouse.listener.ListenerAdapter;
/*     */ import fr.maxlego08.zauctionhouse.placeholder.LocalPlaceholder;
/*     */ import fr.maxlego08.zauctionhouse.placeholder.Placeholder;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl.PlatformScheduler;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.gson.AuctionAdapter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.gson.LocationAdapter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.gson.PotionEffectAdapter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.gson.TransactionAdapter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.plugins.Plugins;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.io.OutputStream;
/*     */ import java.nio.file.Files;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.logging.Level;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.RegisteredServiceProvider;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.potion.PotionEffect;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ZPlugin
/*     */   extends JavaPlugin
/*     */ {
/*  53 */   public static final ExecutorService service = Executors.newFixedThreadPool(5);
/*     */   public static Material[] byId;
/*     */   public static PlatformScheduler serverImplementation;
/*     */   private static ZPlugin plugin;
/*     */   private static FoliaLib foliaLib;
/*  58 */   private final Logger log = new Logger(getDescription().getFullName());
/*  59 */   private final List<Savable> savers = new ArrayList<>();
/*  60 */   private final List<ListenerAdapter> listenerAdapters = new ArrayList<>();
/*  61 */   private final List<String> files = new ArrayList<>();
/*     */   protected CommandManager commandManager;
/*     */   protected ZInventoryManager inventoryManager;
/*     */   protected Persist persist;
/*     */   private Gson gson;
/*     */   private long enableTime;
/*     */   
/*     */   public ZPlugin() {
/*  69 */     plugin = this;
/*     */   }
/*     */   
/*     */   public static ZPlugin z() {
/*  73 */     return plugin;
/*     */   }
/*     */ 
/*     */   
/*     */   protected void preEnable() {
/*  78 */     LocalPlaceholder.getInstance().setPlugin((ZAuctionPlugin)this);
/*  79 */     Placeholder.getPlaceholder();
/*     */     
/*  81 */     foliaLib = new FoliaLib(this);
/*  82 */     serverImplementation = foliaLib.getScheduler();
/*     */     
/*  84 */     this.enableTime = System.currentTimeMillis();
/*     */     
/*  86 */     this.log.log("=== ENABLE START ===");
/*  87 */     this.log.log("Plugin Version V<&>c" + getDescription().getVersion(), Logger.LogType.INFO);
/*     */     
/*  89 */     getDataFolder().mkdirs();
/*     */     
/*  91 */     this.gson = getGsonBuilder().create();
/*  92 */     this.persist = new Persist((Plugin)this, this.log, this.gson);
/*     */     
/*  94 */     this.commandManager = new CommandManager((ZAuctionPlugin)this);
/*  95 */     this.inventoryManager = new ZInventoryManager((ZAuctionPlugin)this);
/*     */ 
/*     */     
/*  98 */     addListener((Listener)new AdapterListener((ZAuctionPlugin)this));
/*  99 */     addListener((ListenerAdapter)this.inventoryManager);
/*     */     
/* 101 */     boolean bool1 = NmsVersion.nmsVersion.isNewMaterial();
/* 102 */     boolean bool2 = (new File(getDataFolder(), "config.yml")).exists();
/*     */     
/* 104 */     if (!bool2) {
/* 105 */       saveDefaultConfig();
/*     */     }
/*     */     
/* 108 */     File file = new File(getDataFolder(), "convert");
/* 109 */     if (!file.exists()) file.mkdir();
/*     */     
/* 111 */     for (String str : this.files) {
/* 112 */       if (bool1) {
/* 113 */         if (!(new File(getDataFolder() + "/inventories/" + str + ".yml")).exists())
/* 114 */           saveResource("inventories/1_13/" + str + ".yml", "inventories/" + str + ".yml", false); 
/*     */         continue;
/*     */       } 
/* 117 */       if (!(new File(getDataFolder() + "/inventories/" + str + ".yml")).exists()) {
/* 118 */         saveResource("inventories/" + str + ".yml", false);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 123 */     if (!(new File(getDataFolder(), "taxs.yml")).exists()) {
/* 124 */       saveResource("taxs.yml", false);
/*     */     }
/*     */     
/* 127 */     if (!(new File(getDataFolder(), "prices.yml")).exists()) {
/* 128 */       saveResource("prices.yml", false);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void postEnable() {
/* 134 */     if (this.inventoryManager != null) {
/* 135 */       this.inventoryManager.sendLog();
/*     */     }
/*     */     
/* 138 */     if (this.commandManager != null) {
/* 139 */       this.commandManager.validCommands();
/*     */     }
/*     */   }
/*     */   
/*     */   protected void logEnable() {
/* 144 */     this.log.log("=== ENABLE DONE <&>7(<&>6" + Math.abs(this.enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");
/*     */   }
/*     */   
/*     */   protected void preDisable() {
/* 148 */     this.enableTime = System.currentTimeMillis();
/* 149 */     this.log.log("=== DISABLE START ===");
/*     */   }
/*     */   
/*     */   protected void postDisable() {
/* 153 */     this.log.log("=== DISABLE DONE <&>7(<&>6" + Math.abs(this.enableTime - System.currentTimeMillis()) + "ms<&>7) <&>e===");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public GsonBuilder getGsonBuilder() {
/* 163 */     return (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().serializeNulls().excludeFieldsWithModifiers(new int[] { 128, 64 }).registerTypeAdapter(AuctionItem.class, new AuctionAdapter()).registerTypeAdapter(Transaction.class, new TransactionAdapter()).registerTypeAdapter(PotionEffect.class, new PotionEffectAdapter(this)).registerTypeAdapter(Location.class, new LocationAdapter(this));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(Listener paramListener) {
/* 172 */     if (paramListener instanceof Savable) addSave((Savable)paramListener); 
/* 173 */     Bukkit.getPluginManager().registerEvents(paramListener, (Plugin)this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addListener(ListenerAdapter paramListenerAdapter) {
/* 182 */     if (paramListenerAdapter instanceof Savable) addSave((Savable)paramListenerAdapter); 
/* 183 */     this.listenerAdapters.add(paramListenerAdapter);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addSave(Savable paramSavable) {
/* 192 */     this.savers.add(paramSavable);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Logger getLog() {
/* 201 */     return this.log;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Gson getGson() {
/* 210 */     return this.gson;
/*     */   }
/*     */   
/*     */   public Persist getPersist() {
/* 214 */     return this.persist;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<Savable> getSavers() {
/* 223 */     return this.savers;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T getProvider(Class<T> paramClass) {
/* 231 */     RegisteredServiceProvider registeredServiceProvider = getServer().getServicesManager().getRegistration(paramClass);
/* 232 */     if (registeredServiceProvider == null) {
/* 233 */       this.log.log("Unable to retrieve the provider " + paramClass, Logger.LogType.WARNING);
/* 234 */       return null;
/*     */     } 
/* 236 */     return (registeredServiceProvider.getProvider() != null) ? (T)registeredServiceProvider.getProvider() : null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<ListenerAdapter> getListenerAdapters() {
/* 243 */     return this.listenerAdapters;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandManager getCommandManager() {
/* 250 */     return this.commandManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZInventoryManager getZInventoryManager() {
/* 257 */     return this.inventoryManager;
/*     */   }
/*     */   
/*     */   public boolean isEnable(Plugins paramPlugins) {
/* 261 */     Plugin plugin = getPlugin(paramPlugins);
/* 262 */     return (plugin != null && plugin.isEnabled());
/*     */   }
/*     */   
/*     */   protected Plugin getPlugin(Plugins paramPlugins) {
/* 266 */     return Bukkit.getPluginManager().getPlugin(paramPlugins.getName());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerCommand(String paramString, VCommand paramVCommand, List<String> paramList) {
/* 277 */     this.commandManager.registerCommand((Plugin)this, paramString, paramVCommand, paramList);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void registerInventory(EnumInventory paramEnumInventory, VInventory paramVInventory) {
/* 287 */     this.inventoryManager.registerInventory(paramEnumInventory, paramVInventory);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadFiles() {
/* 294 */     this.savers.forEach(paramSavable -> paramSavable.load(this.persist));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveFiles() {
/* 301 */     this.savers.forEach(paramSavable -> paramSavable.save(this.persist));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void reloadFiles() {
/* 308 */     this.savers.forEach(paramSavable -> paramSavable.load(this.persist));
/*     */   }
/*     */   
/*     */   protected void registerFile(InventoryName paramInventoryName) {
/* 312 */     this.files.add(paramInventoryName.getName());
/*     */   }
/*     */   
/*     */   public void saveResource(String paramString1, String paramString2, boolean paramBoolean) {
/* 316 */     if (paramString1 != null && !paramString1.equals("")) {
/* 317 */       paramString1 = paramString1.replace('\\', '/');
/* 318 */       InputStream inputStream = getResource(paramString1);
/* 319 */       if (inputStream == null) {
/* 320 */         throw new IllegalArgumentException("The embedded resource '" + paramString1 + "' cannot be found in " + getFile());
/*     */       }
/* 322 */       File file1 = new File(getDataFolder(), paramString2);
/* 323 */       int i = paramString2.lastIndexOf('/');
/* 324 */       File file2 = new File(getDataFolder(), paramString2.substring(0, (i >= 0) ? i : 0));
/* 325 */       if (!file2.exists()) {
/* 326 */         file2.mkdirs();
/*     */       }
/*     */       
/*     */       try {
/* 330 */         if (file1.exists() && !paramBoolean) {
/* 331 */           getLogger().log(Level.WARNING, "Could not save " + file1.getName() + " to " + file1 + " because " + file1.getName() + " already exists.");
/*     */         } else {
/* 333 */           OutputStream outputStream = Files.newOutputStream(file1.toPath(), new java.nio.file.OpenOption[0]);
/* 334 */           byte[] arrayOfByte = new byte[1024];
/*     */           
/*     */           int j;
/* 337 */           while ((j = inputStream.read(arrayOfByte)) > 0) {
/* 338 */             outputStream.write(arrayOfByte, 0, j);
/*     */           }
/*     */           
/* 341 */           outputStream.close();
/* 342 */           inputStream.close();
/*     */         } 
/* 344 */       } catch (IOException iOException) {
/* 345 */         getLogger().log(Level.SEVERE, "Could not save " + file1.getName() + " to " + file1, iOException);
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 350 */       throw new IllegalArgumentException("ResourcePath cannot be null or empty");
/*     */     } 
/*     */   }
/*     */   
/*     */   public List<String> getFiles() {
/* 355 */     return this.files;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcore\ZPlugin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */