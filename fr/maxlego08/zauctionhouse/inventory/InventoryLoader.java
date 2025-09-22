/*     */ package fr.maxlego08.zauctionhouse.inventory;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryFileNotFoundException;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryNotFoundException;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventorySizeException;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.NameAlreadyExistException;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.InventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.loader.ButtonCollections;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.yaml.YamlUtils;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import org.bukkit.configuration.file.FileConfiguration;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class InventoryLoader
/*     */   extends YamlUtils
/*     */   implements InventoryManager
/*     */ {
/*     */   private final ZAuctionPlugin plugin;
/*  32 */   private final Map<InventoryType, Inventory> typeInventories = new HashMap<>();
/*  33 */   private final Map<String, Inventory> inventories = new HashMap<>();
/*     */   
/*     */   public InventoryLoader(ZAuctionPlugin paramZAuctionPlugin) {
/*  36 */     super(paramZAuctionPlugin);
/*  37 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public Inventory getInventory(InventoryName paramInventoryName) {
/*  42 */     return getInventory(paramInventoryName.getName(), true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getInventory(String paramString, boolean paramBoolean) {
/*  52 */     if (paramString == null && paramBoolean)
/*  53 */       throw new InventoryNotFoundException("Unable to find the inventory with name null"); 
/*  54 */     Inventory inventory = this.inventories.getOrDefault(paramString.toLowerCase(), null);
/*  55 */     if (inventory == null && paramBoolean)
/*  56 */       throw new InventoryNotFoundException("Unable to find the inventory " + paramString); 
/*  57 */     return inventory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadInventories() {
/*  63 */     info("Loading inventories in progress...");
/*     */     
/*  65 */     FileConfiguration fileConfiguration = getConfig();
/*     */     
/*  67 */     delete();
/*     */     
/*  69 */     this.plugin.getFiles().forEach(paramString -> {
/*     */           if (InventoryName.isOnlyZMenu(paramString)) {
/*     */             return;
/*     */           }
/*     */           try {
/*     */             loadInventory(paramString);
/*  75 */           } catch (Exception exception) {
/*     */             exception.printStackTrace();
/*     */           } 
/*     */         });
/*     */     
/*  80 */     if (fileConfiguration.contains("customInventories")) {
/*  81 */       List list = fileConfiguration.getStringList("customInventories");
/*  82 */       for (String str : list) {
/*  83 */         loadInventory(str);
/*     */       }
/*     */     } 
/*  86 */     info("Inventories loading complete.");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void saveInventories() {}
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory loadInventory(String paramString) {
/*  98 */     if (paramString == null) {
/*  99 */       throw new NullPointerException("Impossible de trouver le string ! Il est null !");
/*     */     }
/* 101 */     String str1 = paramString.toLowerCase();
/*     */     
/* 103 */     if (this.inventories.containsKey(str1)) {
/* 104 */       throw new NameAlreadyExistException("the name " + str1 + " already exist ! (Simply remove it from the list of categories in the config.yml file.)");
/*     */     }
/*     */     
/* 107 */     YamlConfiguration yamlConfiguration = getConfig("inventories/" + str1 + ".yml");
/*     */     
/* 109 */     if (yamlConfiguration == null) {
/* 110 */       Logger.info("Please check that your file is in lower case!", Logger.LogType.ERROR);
/* 111 */       throw new InventoryFileNotFoundException("Cannot find the file: inventories/" + str1 + ".yml . Do you have the files in the zAuctionHouseV3/inventories/ folder?");
/*     */     } 
/*     */ 
/*     */     
/* 115 */     InventoryType inventoryType = InventoryType.form(yamlConfiguration.getString("type"));
/*     */     
/* 117 */     String str2 = yamlConfiguration.getString("name");
/* 118 */     str2 = (str2 == null) ? "" : str2;
/*     */     
/* 120 */     int i = yamlConfiguration.getInt("size", 54);
/* 121 */     if (i % 9 != 0) {
/* 122 */       throw new InventorySizeException("Size " + i + " is not valid for inventory " + str1);
/*     */     }
/* 124 */     ButtonCollections buttonCollections = new ButtonCollections(this.plugin);
/* 125 */     List<Button> list = (List)buttonCollections.load(yamlConfiguration, str1, new Object[0]);
/*     */     
/* 127 */     InventoryObject inventoryObject = new InventoryObject(str2, inventoryType, i, list, str1);
/* 128 */     this.inventories.put(str1, inventoryObject);
/*     */     
/* 130 */     if (inventoryType != InventoryType.DEFAULT) {
/* 131 */       this.typeInventories.put(inventoryType, inventoryObject);
/*     */     }
/* 133 */     success("Successful loading of the inventory " + str1 + " !");
/* 134 */     return inventoryObject;
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/* 139 */     this.inventories.clear();
/* 140 */     this.typeInventories.clear();
/*     */   }
/*     */ 
/*     */   
/*     */   public Inventory getInventory(InventoryType paramInventoryType) {
/* 145 */     return this.typeInventories.getOrDefault(paramInventoryType, null);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<Inventory> getInventoryByName(String paramString) {
/* 150 */     Inventory inventory = getInventory(paramString, false);
/* 151 */     if (inventory != null)
/* 152 */       return Optional.of(inventory); 
/* 153 */     return this.inventories.values().stream().filter(paramInventory -> paramInventory.getName().toLowerCase().contains(paramString.toLowerCase()))
/* 154 */       .findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public Collection<Inventory> getInventories() {
/* 159 */     return Collections.unmodifiableCollection(this.inventories.values());
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\inventory\InventoryLoader.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */