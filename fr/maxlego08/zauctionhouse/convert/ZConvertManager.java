/*     */ package fr.maxlego08.zauctionhouse.convert;
/*     */ import com.google.common.io.Files;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.ConvertManager;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.ConvertType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.Storage;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.Transaction;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Persist;
/*     */ import fr.maxlego08.zauctionhouse.convert.zauctionhousev2.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.convert.zauctionhousev2.ZAuctionHouseV2Storage;
/*     */ import fr.maxlego08.zauctionhouse.storage.storages.JSonStorage;
/*     */ import fr.maxlego08.zauctionhouse.storage.storages.SqlStorage;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ItemStackUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.Base64ItemStack;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.RequestHelper;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.Schema;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.configuration.InvalidConfigurationException;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public class ZConvertManager extends ZUtils implements ConvertManager {
/*  51 */   private final String database = "auctions.db";
/*     */ 
/*     */   
/*     */   private final ZAuctionPlugin plugin;
/*     */   
/*     */   private boolean isConverting = false;
/*     */ 
/*     */   
/*     */   public ZConvertManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  60 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ 
/*     */   
/*     */   public void convertFile(CommandSender paramCommandSender, ConvertType paramConvertType, long paramLong) {
/*     */     PlayerAuctionsConvertManager playerAuctionsConvertManager;
/*  66 */     if (this.isConverting) {
/*  67 */       message(paramCommandSender, "§cA conversion is already in progress, you cannot do another conversion at the moment.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/*  71 */     switch (paramConvertType) {
/*     */       case AUCTION:
/*  73 */         playerAuctionsConvertManager = new PlayerAuctionsConvertManager(this.plugin, this);
/*  74 */         playerAuctionsConvertManager.convertItemsFromPlayerAuctions(paramCommandSender);
/*     */         break;
/*     */       case SEARCH:
/*     */       case CATEGORY:
/*  78 */         convertItemsFromAuctionHouseDB(paramCommandSender, paramLong);
/*     */         break;
/*     */       case ITEMS:
/*  81 */         convertItemsFromCrazyAuction(paramCommandSender);
/*     */         break;
/*     */       case EXPIRE:
/*  84 */         convertZAuctionHouseV2(paramCommandSender);
/*     */         break;
/*     */       case BUYING:
/*  87 */         convertJsonToDatabase(paramCommandSender);
/*     */         break;
/*     */       case ADMIN_REMOVE:
/*  90 */         convertDatabaseToJson(paramCommandSender);
/*     */         break;
/*     */       case BUY_CONFIRM:
/*  93 */         convertToZMenu(paramCommandSender);
/*     */         break;
/*     */       case REMOVE_CONFIRM:
/*  96 */         convertToBase64Minecraft121(paramCommandSender);
/*     */         break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertToBase64Minecraft121(CommandSender paramCommandSender) {
/* 106 */     if (NmsVersion.nmsVersion.getVersion() >= NmsVersion.V_1_21.getVersion()) {
/* 107 */       message(paramCommandSender, "§cYou do not need to execute this command.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 111 */     if (AuctionConfiguration.enableNewBase64ItemStackMethod) {
/* 112 */       message(paramCommandSender, "§cYour items are already compatible for the 1.21", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 116 */     if (AuctionConfiguration.storage == Storage.JSON) {
/*     */       
/* 118 */       AuctionConfiguration.enableNewBase64ItemStackMethod = true;
/* 119 */       this.plugin.getConfiguration().saveConfigFile();
/* 120 */       this.plugin.getStorage().save((AuctionPlugin)this.plugin);
/*     */       
/* 122 */       message(paramCommandSender, "§aUpdating your items and transactions. Enabling §fenableNewBase64ItemStackMethod §ain config.yml", new Object[0]);
/*     */     }
/* 124 */     else if (!AuctionConfiguration.storage.isNotDatabase()) {
/*     */       
/* 126 */       SqlStorage sqlStorage = (SqlStorage)this.plugin.getStorage();
/* 127 */       RequestHelper requestHelper = sqlStorage.getRequestHelper();
/*     */       
/* 129 */       ZPlugin.service.execute(() -> {
/*     */             message(paramCommandSender, "§fUpdating items...", new Object[0]);
/*     */ 
/*     */             
/*     */             updateItems(paramSqlStorage.getItems(), paramRequestHelper);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating items... §aDONE", new Object[0]);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating buying items...", new Object[0]);
/*     */ 
/*     */             
/*     */             updateItems(paramSqlStorage.getBuyingItems(), paramRequestHelper);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating buying items... §aDONE", new Object[0]);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating expired items...", new Object[0]);
/*     */ 
/*     */             
/*     */             updateItems(paramSqlStorage.getExpiredItems(), paramRequestHelper);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating expired items... §ADONE", new Object[0]);
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating transactions...", new Object[0]);
/*     */ 
/*     */             
/*     */             paramSqlStorage.getTransactions().forEach(());
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§fUpdating transactions... §ADONE", new Object[0]);
/*     */ 
/*     */             
/*     */             AuctionConfiguration.enableNewBase64ItemStackMethod = true;
/*     */ 
/*     */             
/*     */             this.plugin.getConfiguration().saveConfigFile();
/*     */ 
/*     */             
/*     */             message(paramCommandSender, "§aUpdating your items and transactions. Enabling §fenableNewBase64ItemStackMethod §ain config.yml", new Object[0]);
/*     */           });
/*     */     } else {
/* 175 */       message(paramCommandSender, "§cIf you are with a custom storage, you have to manage the migration yourself, if you need help do not hesitate to ask me or decompile the plugin.", new Object[0]);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void updateItems(List<AuctionItem> paramList, RequestHelper paramRequestHelper) {
/*     */     try {
/* 181 */       paramList.forEach(paramAuctionItem -> {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             try {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */               
/*     */               paramRequestHelper.update("%prefix%items", ());
/* 200 */             } catch (Exception exception) {
/*     */               exception.printStackTrace();
/*     */             } 
/*     */           });
/* 204 */     } catch (Exception exception) {
/* 205 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void convertToZMenu(CommandSender paramCommandSender) {
/* 211 */     this.isConverting = true;
/* 212 */     long l = System.currentTimeMillis();
/* 213 */     message(paramCommandSender, "§7Beginning of the conversion..", new Object[0]);
/*     */     
/* 215 */     this.plugin.getFiles().forEach(paramString -> {
/*     */           String str1 = paramString.toLowerCase();
/*     */           
/*     */           File file1 = new File(this.plugin.getDataFolder(), "old_inventories");
/*     */           
/*     */           if (!file1.exists()) {
/*     */             file1.mkdir();
/*     */           }
/*     */           
/*     */           File file2 = new File(this.plugin.getDataFolder(), "inventories/" + str1 + ".yml");
/*     */           File file3 = new File(this.plugin.getDataFolder(), "old_inventories/" + str1 + ".yml");
/*     */           try {
/*     */             Files.copy(file2, file3);
/* 228 */           } catch (IOException iOException) {
/*     */             iOException.printStackTrace();
/*     */           } 
/*     */           YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file2);
/*     */           String str2 = yamlConfiguration.getString("type");
/*     */           if (str2 == null) {
/*     */             return;
/*     */           }
/*     */           InventoryType inventoryType = InventoryType.valueOf(str2);
/*     */           ArrayList<Integer> arrayList1 = new ArrayList();
/*     */           String str3 = null;
/*     */           ArrayList<Integer> arrayList2 = new ArrayList();
/*     */           String str4 = null;
/*     */           for (String str5 : yamlConfiguration.getConfigurationSection("items.").getKeys(false)) {
/*     */             String str6 = "items." + str5 + ".";
/*     */             String str7 = yamlConfiguration.getString(str6 + "type", "none").toLowerCase();
/*     */             switch (str7) {
/*     */               case "none":
/*     */               case "none_slot":
/*     */               case "sell_inventory_cancel":
/*     */                 yamlConfiguration.set(str6 + "type", null);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "inventory":
/*     */                 yamlConfiguration.set(str6 + "plugin", "zAuctionHouseV3");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "change_sort":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_CHANGE_SORT");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "show_slot":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SHOW_ITEMS");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "categories":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_CATEGORIES");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "admin_remove":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_ADMIN_REMOVE");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "claim":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_CLAIM");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "back":
/*     */                 if (inventoryType == InventoryType.BUY_CONFIRM || inventoryType == InventoryType.REMOVE_CONFIRM) {
/*     */                   if (str4 == null) {
/*     */                     str4 = str6;
/*     */                     arrayList2.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                     break;
/*     */                   } 
/*     */                   arrayList2.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                   yamlConfiguration.set("items." + str5, null);
/*     */                 } 
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "buy_item":
/*     */                 if (str3 == null) {
/*     */                   str3 = str6;
/*     */                   yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_BUY_CONFIRM");
/*     */                   arrayList1.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                   break;
/*     */                 } 
/*     */                 arrayList1.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                 yamlConfiguration.set("items." + str5, null);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "remove_item":
/*     */                 if (str3 == null) {
/*     */                   str3 = str6;
/*     */                   yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_REMOVE_CONFIRM");
/*     */                   arrayList1.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                   break;
/*     */                 } 
/*     */                 arrayList1.add(Integer.valueOf(yamlConfiguration.getInt(str6 + "slot")));
/*     */                 yamlConfiguration.set("items." + str5, null);
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "sell_inventory_slot":
/*     */                 yamlConfiguration.set(str6 + "type", (inventoryType == InventoryType.SELL_SHOW) ? "ZAUCTIONHOUSE_SHOW_ITEMS" : "ZAUCTIONHOUSE_SELL_INVENTORY_SLOT");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "sell_inventory_accept":
/*     */                 yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SELL_INVENTORY_ACCEPT");
/*     */                 break;
/*     */ 
/*     */               
/*     */               case "auction_item":
/*     */                 switch (inventoryType) {
/*     */                   case AUCTION:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_AUCTION");
/*     */                     break;
/*     */ 
/*     */                   
/*     */                   case SEARCH:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SEARCH");
/*     */                     break;
/*     */                   
/*     */                   case CATEGORY:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_CATEGORY");
/*     */                     break;
/*     */                   
/*     */                   case ITEMS:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_ITEMS");
/*     */                     break;
/*     */                   
/*     */                   case EXPIRE:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_EXPIRE");
/*     */                     break;
/*     */                   
/*     */                   case BUYING:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_BUYING");
/*     */                     break;
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               
/*     */               case "show_item":
/*     */                 switch (inventoryType) {
/*     */                   case ADMIN_REMOVE:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SHOW_ADMIN");
/*     */                     break;
/*     */                   
/*     */                   case BUY_CONFIRM:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SHOW_BUY");
/*     */                     break;
/*     */                   
/*     */                   case REMOVE_CONFIRM:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_SHOW_REMOVE");
/*     */                     break;
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */               
/*     */               case "remove_all":
/*     */                 switch (inventoryType) {
/*     */                   case ITEMS:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_REMOVE_ALL_ITEMS");
/*     */                     break;
/*     */                   
/*     */                   case EXPIRE:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_REMOVE_ALL_EXPIRE");
/*     */                     break;
/*     */                   
/*     */                   case BUYING:
/*     */                     yamlConfiguration.set(str6 + "type", "ZAUCTIONHOUSE_REMOVE_ALL_BUYING");
/*     */                     break;
/*     */                 } 
/*     */                 
/*     */                 break;
/*     */             } 
/*     */             
/*     */             List<Integer> list = yamlConfiguration.getIntegerList(str6 + "slots");
/*     */             if (!list.isEmpty()) {
/*     */               yamlConfiguration.set(str6 + "slots", updateInRange(list));
/*     */             }
/*     */           } 
/*     */           if (str3 != null) {
/*     */             yamlConfiguration.set(str3 + "slot", null);
/*     */             yamlConfiguration.set(str3 + "slots", updateInRange(arrayList1));
/*     */             str3 = null;
/*     */           } 
/*     */           if (str4 != null) {
/*     */             yamlConfiguration.set(str4 + "slot", null);
/*     */             yamlConfiguration.set(str4 + "slots", updateInRange(arrayList2));
/*     */             str4 = null;
/*     */           } 
/*     */           yamlConfiguration.set("type", null);
/*     */           try {
/*     */             File file = new File(this.plugin.getDataFolder(), "inventories/" + str1 + ".yml");
/*     */             yamlConfiguration.save(file);
/* 413 */           } catch (IOException iOException) {
/*     */             iOException.printStackTrace();
/*     */           } 
/*     */         });
/*     */     
/* 418 */     Logger.info("Converted to " + (System.currentTimeMillis() - l) + "ms");
/* 419 */     paramCommandSender.sendMessage("");
/* 420 */     paramCommandSender.sendMessage("§7You have just converted items from §fzAuctionHouseV3 Config§7 to §dzAuctionHouseV3 x zMenu Config§7.");
/* 421 */     paramCommandSender.sendMessage("");
/* 422 */     paramCommandSender.sendMessage("§fYou just converted your configuration to work with zMenu. Your new configuration is in the folder §7zAuctionHouseV3/inventories");
/* 423 */     paramCommandSender.sendMessage("§fYour old configuration has been copied to the folder §7zAuctionHouseV3/old_inventories");
/* 424 */     paramCommandSender.sendMessage("");
/* 425 */     paramCommandSender.sendMessage("§fEnable the zMenu option in the config.yml file, then run §a/ah reload§f. Then a final §nrestart§r of your server.");
/* 426 */     paramCommandSender.sendMessage("");
/* 427 */     this.isConverting = false;
/*     */   }
/*     */   
/*     */   private List<Object> updateInRange(List<Integer> paramList) {
/* 431 */     ArrayList arrayList = new ArrayList();
/* 432 */     ArrayList<Integer> arrayList1 = new ArrayList();
/*     */     
/* 434 */     for (byte b = 0; b < paramList.size(); b++) {
/* 435 */       int i = ((Integer)paramList.get(b)).intValue();
/* 436 */       if (b == 0) {
/* 437 */         arrayList1.add(Integer.valueOf(i));
/* 438 */       } else if (i == ((Integer)paramList.get(b - 1)).intValue() + 1) {
/* 439 */         arrayList1.add(Integer.valueOf(i));
/*     */       } else {
/* 441 */         arrayList.add(new ArrayList<>(arrayList1));
/* 442 */         arrayList1.clear();
/* 443 */         arrayList1.add(Integer.valueOf(i));
/*     */       } 
/*     */     } 
/*     */     
/* 447 */     if (!arrayList1.isEmpty()) {
/* 448 */       arrayList.add(new ArrayList<>(arrayList1));
/*     */     }
/*     */     
/* 451 */     ArrayList<Object> arrayList2 = new ArrayList();
/*     */     
/* 453 */     arrayList.forEach(paramList2 -> {
/*     */           if (paramList2.size() > 1) {
/*     */             int i = ((Integer)paramList2.get(0)).intValue();
/*     */             int j = ((Integer)paramList2.get(paramList2.size() - 1)).intValue();
/*     */             paramList1.add(i + "-" + j);
/*     */           } else {
/*     */             Objects.requireNonNull(paramList1);
/*     */             paramList2.forEach(paramList1::add);
/*     */           } 
/*     */         });
/* 463 */     return arrayList2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertZAuctionHouseV2(CommandSender paramCommandSender) {
/* 473 */     this.isConverting = true;
/* 474 */     long l = System.currentTimeMillis();
/* 475 */     message(paramCommandSender, "§7Beginning of the conversion..", new Object[0]);
/*     */     
/* 477 */     IStorage iStorage = this.plugin.getStorage();
/* 478 */     File file = new File(this.plugin.getDataFolder(), "convert/items.json");
/*     */     
/* 480 */     if (!file.exists()) {
/* 481 */       message(paramCommandSender, "§cImpossible de trouver le fichier §fitems.json§c.", new Object[0]);
/* 482 */       message(paramCommandSender, "§7Vous devez mettre le fichier §fitems.json§7 dans le dossier §dzAuctionHouseV3/convert§7.", new Object[0]);
/* 483 */       this.isConverting = false;
/*     */       
/*     */       return;
/*     */     } 
/* 487 */     ZAuctionHouseV2Storage zAuctionHouseV2Storage = new ZAuctionHouseV2Storage();
/* 488 */     zAuctionHouseV2Storage.load(this.plugin.getPersist());
/*     */     
/* 490 */     zAuctionHouseV2Storage.getItems().forEach(paramAuctionItem -> {
/*     */           AuctionItem auctionItem = paramAuctionItem.getModernAuction(StorageType.STORAGE, this.plugin);
/*     */           
/*     */           paramIStorage.saveItem((AuctionPlugin)this.plugin, auctionItem, StorageType.STORAGE);
/*     */         });
/* 495 */     zAuctionHouseV2Storage.getExpiredItems().forEach(paramAuctionItem -> {
/*     */           AuctionItem auctionItem = paramAuctionItem.getModernAuction(StorageType.EXPIRE, this.plugin);
/*     */           
/*     */           paramIStorage.saveItem((AuctionPlugin)this.plugin, auctionItem, StorageType.EXPIRE);
/*     */         });
/* 500 */     zAuctionHouseV2Storage.getBuyingItems().forEach(paramAuctionItem -> {
/*     */           AuctionItem auctionItem = paramAuctionItem.getModernAuction(StorageType.BUY, this.plugin);
/*     */           
/*     */           paramIStorage.saveItem((AuctionPlugin)this.plugin, auctionItem, StorageType.BUY);
/*     */         });
/* 505 */     this.plugin.getLogger().info("Converted to " + (System.currentTimeMillis() - l) + "ms");
/* 506 */     message(paramCommandSender, "§7You have just converted items from §fzAuctionHouseV2 §7 to §dzAuctionHouseV3§7.", new Object[0]);
/* 507 */     this.isConverting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertItemsFromAuctionHouseDB(CommandSender paramCommandSender, long paramLong) {
/* 517 */     if (paramCommandSender instanceof org.bukkit.entity.Player) {
/* 518 */       paramCommandSender.sendMessage("§cOnly console can use this command !");
/*     */       
/*     */       return;
/*     */     } 
/* 522 */     this.isConverting = true;
/*     */     
/* 524 */     message(paramCommandSender, "§aStart of conversion of items from AuctionHouse with DB !", new Object[0]);
/*     */     
/* 526 */     File file = new File(this.plugin.getDataFolder(), "convert/auctions.db");
/*     */     
/* 528 */     if (!file.exists()) {
/* 529 */       message(paramCommandSender, "§cUnable to find the file §fauctions.db§c.", new Object[0]);
/* 530 */       message(paramCommandSender, "§7You must put the file §fauctions.db§7 in the folter §dzAuctionHouseV3/convert§7.", new Object[0]);
/* 531 */       this.isConverting = false;
/*     */       
/*     */       return;
/*     */     } 
/* 535 */     runAsync(() -> {
/*     */           try {
/*     */             Connection connection = DriverManager.getConnection("jdbc:sqlite:" + paramFile.getAbsolutePath());
/*     */             
/*     */             String str = "SELECT * FROM listings";
/*     */             
/*     */             PreparedStatement preparedStatement = connection.prepareStatement(str);
/*     */             
/*     */             ResultSet resultSet = preparedStatement.executeQuery();
/*     */             
/*     */             IStorage iStorage = this.plugin.getStorage();
/*     */             
/*     */             byte b = 0;
/*     */             
/*     */             while (resultSet.next()) {
/*     */               long l = resultSet.getLong("id");
/*     */               if (System.currentTimeMillis() > l + paramLong * 1000L) {
/*     */                 continue;
/*     */               }
/*     */               String str1 = resultSet.getString("seller_uuid");
/*     */               String str2 = resultSet.getString("buyer_uuid");
/*     */               float f = resultSet.getFloat("price");
/*     */               byte[] arrayOfByte = resultSet.getBytes("item");
/*     */               String str3 = new String(arrayOfByte, StandardCharsets.UTF_8);
/*     */               YamlConfiguration yamlConfiguration = new YamlConfiguration();
/*     */               ItemStack itemStack = null;
/*     */               try {
/*     */                 yamlConfiguration.loadFromString(str3);
/*     */                 itemStack = yamlConfiguration.getItemStack("item", null);
/* 564 */               } catch (InvalidConfigurationException invalidConfigurationException) {
/*     */                 invalidConfigurationException.printStackTrace();
/*     */               } 
/*     */               
/*     */               if (itemStack != null) {
/*     */                 UUID uUID1 = UUID.fromString(str1);
/*     */                 
/*     */                 UUID uUID2 = (str2.length() != 0) ? UUID.fromString(str2) : null;
/*     */                 
/*     */                 UUID uUID3 = UUID.randomUUID();
/*     */                 
/*     */                 AuctionType auctionType = AuctionType.DEFAULT;
/*     */                 
/*     */                 long l1 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/*     */                 
/*     */                 StorageType storageType = (str2.length() == 0) ? StorageType.EXPIRE : StorageType.BUY;
/*     */                 
/*     */                 OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uUID1);
/*     */                 
/*     */                 ZAuctionItem zAuctionItem = new ZAuctionItem(uUID3, itemStack, auctionType, (long)f, "VAULT", uUID1, l1, uUID2, storageType, offlinePlayer.getName(), 0, this.plugin.getStorage().getServerName());
/*     */                 
/*     */                 iStorage.saveItem((AuctionPlugin)this.plugin, (AuctionItem)zAuctionItem, storageType);
/*     */                 
/*     */                 b++;
/*     */                 
/*     */                 message(paramCommandSender, "§aAdding an item, please wait", new Object[0]);
/*     */                 
/*     */                 continue;
/*     */               } 
/*     */               Logger.info("Error when loading an item ! " + l);
/*     */             } 
/*     */             message(paramCommandSender, "§aLoaded §6" + b + " §aitems.", new Object[0]);
/* 596 */           } catch (SQLException sQLException) {
/*     */             sQLException.printStackTrace();
/*     */             
/*     */             message(paramCommandSender, "§cError: " + sQLException.getMessage(), new Object[0]);
/*     */             
/*     */             this.isConverting = false;
/*     */           } 
/*     */         });
/* 604 */     this.isConverting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertItemsFromCrazyAuction(CommandSender paramCommandSender) {
/* 611 */     this.isConverting = true;
/*     */     
/* 613 */     message(paramCommandSender, "§aStart of conversion of items from CrazyAuction!", new Object[0]);
/*     */     
/* 615 */     long l = System.currentTimeMillis();
/*     */     
/* 617 */     String str = ZPlugin.z().getDataFolder().getPath();
/* 618 */     File file = new File(str + File.separator + "convert/Data.yml");
/*     */     
/* 620 */     if (!file.exists()) {
/* 621 */       message(paramCommandSender, "§cImpossible de trouver le fichier §fData.yml§c.", new Object[0]);
/* 622 */       message(paramCommandSender, "§7Vous devez mettre le fichier §fData.yml§7 dans le dossier §dzAuctionHouseV3/convert§7.", new Object[0]);
/* 623 */       this.isConverting = false;
/*     */       
/*     */       return;
/*     */     } 
/* 627 */     IStorage iStorage = this.plugin.getStorage();
/* 628 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/*     */     
/* 630 */     byte b = 0;
/*     */     
/* 632 */     for (String str1 : yamlConfiguration.getConfigurationSection("Items.").getKeys(false)) {
/*     */       
/* 634 */       String str2 = yamlConfiguration.getString("Items." + str1 + ".Seller");
/* 635 */       UUID uUID1 = Bukkit.getOfflinePlayer(str2).getUniqueId();
/* 636 */       long l1 = yamlConfiguration.getLong("Items." + str1 + ".Price");
/* 637 */       UUID uUID2 = UUID.randomUUID();
/* 638 */       ItemStack itemStack = (ItemStack)yamlConfiguration.get("Items." + str1 + ".Item");
/* 639 */       long l2 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/* 640 */       ZAuctionItem zAuctionItem = new ZAuctionItem(uUID2, itemStack, AuctionType.BID, l1, "VAULT", uUID1, l2, StorageType.STORAGE, str2, 0, this.plugin.getStorage().getServerName());
/* 641 */       iStorage.saveItem((AuctionPlugin)this.plugin, (AuctionItem)zAuctionItem, StorageType.STORAGE);
/*     */       
/* 643 */       b++;
/*     */     } 
/*     */     
/* 646 */     message(paramCommandSender, "§6" + b + " §eitems loaded from §e'§aItems§e' §e!", new Object[0]);
/* 647 */     b = 0;
/*     */     
/* 649 */     for (String str1 : yamlConfiguration.getConfigurationSection("OutOfTime/Cancelled.").getKeys(false)) {
/*     */       
/* 651 */       String str2 = yamlConfiguration.getString("OutOfTime/Cancelled." + str1 + ".Seller");
/* 652 */       UUID uUID1 = Bukkit.getOfflinePlayer(str2).getUniqueId();
/* 653 */       UUID uUID2 = UUID.randomUUID();
/* 654 */       long l1 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/* 655 */       ItemStack itemStack = (ItemStack)yamlConfiguration.get("OutOfTime/Cancelled." + str1 + ".Item");
/*     */       
/* 657 */       ZAuctionItem zAuctionItem = new ZAuctionItem(uUID2, itemStack, AuctionType.DEFAULT, 0L, "VAULT", uUID1, l1, StorageType.EXPIRE, str2, 0, this.plugin.getStorage().getServerName());
/* 658 */       iStorage.saveItem((AuctionPlugin)this.plugin, (AuctionItem)zAuctionItem, StorageType.EXPIRE);
/*     */       
/* 660 */       b++;
/*     */     } 
/*     */     
/* 663 */     message(paramCommandSender, "§6" + b + " §eitems loaded from §e'§aOutOfTime/Cancelled§e' §e!", new Object[0]);
/* 664 */     message(paramCommandSender, "§aConversion made in §2" + Math.abs(l - System.currentTimeMillis()) + " §ams", new Object[0]);
/* 665 */     this.isConverting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertJsonToDatabase(CommandSender paramCommandSender) {
/* 675 */     if (AuctionConfiguration.storage.isNotDatabase()) {
/* 676 */       message(paramCommandSender, "§cYou have to put the storage in a database to be able to convert the items in the json file.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 680 */     IStorage iStorage = this.plugin.getStorage();
/*     */     
/* 682 */     File file = new File(this.plugin.getDataFolder(), "items.json");
/*     */     
/* 684 */     if (!file.exists()) {
/* 685 */       message(paramCommandSender, "§cUnable to find the items.json file.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 689 */     this.isConverting = true;
/* 690 */     message(paramCommandSender, "§aStart of conversion of items from json to database.", new Object[0]);
/*     */     
/* 692 */     Persist persist = this.plugin.getPersist();
/*     */     
/* 694 */     JSonStorage jSonStorage = new JSonStorage();
/* 695 */     jSonStorage = (JSonStorage)persist.loadOrSaveDefault(jSonStorage, JSonStorage.class, "items");
/* 696 */     jSonStorage.load(null, (AuctionPlugin)this.plugin);
/*     */     
/* 698 */     jSonStorage.getItems().forEach(paramAuctionItem -> paramIStorage.saveItem((AuctionPlugin)this.plugin, paramAuctionItem, paramAuctionItem.getStorageType()));
/* 699 */     jSonStorage.getBuyingItems().forEach(paramAuctionItem -> paramIStorage.saveItem((AuctionPlugin)this.plugin, paramAuctionItem, paramAuctionItem.getStorageType()));
/* 700 */     jSonStorage.getExpiredItems().forEach(paramAuctionItem -> paramIStorage.saveItem((AuctionPlugin)this.plugin, paramAuctionItem, paramAuctionItem.getStorageType()));
/* 701 */     jSonStorage.getTransactions().forEach(paramTransaction -> paramIStorage.storeTransaction(paramTransaction, null));
/*     */     
/* 703 */     message(paramCommandSender, "§aAll items have just been converted.", new Object[0]);
/* 704 */     this.isConverting = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void convertDatabaseToJson(CommandSender paramCommandSender) {
/* 715 */     if (AuctionConfiguration.storage.isNotDatabase()) {
/* 716 */       message(paramCommandSender, "§cYou have to put the storage in a database to be able to convert the items in the json file.", new Object[0]);
/*     */       
/*     */       return;
/*     */     } 
/* 720 */     if (AuctionConfiguration.storage.isNotDatabase()) {
/* 721 */       message(paramCommandSender, "§cYou must have a mysql database to do this.", new Object[0]);
/* 722 */       this.isConverting = false;
/*     */       
/*     */       return;
/*     */     } 
/* 726 */     SqlStorage sqlStorage = (SqlStorage)this.plugin.getStorage();
/* 727 */     JSonStorage jSonStorage = new JSonStorage();
/*     */     
/* 729 */     jSonStorage.setBuyingItems(sqlStorage.getBuyingItems());
/* 730 */     jSonStorage.setExpiredItems(sqlStorage.getExpiredItems());
/* 731 */     jSonStorage.setItems(sqlStorage.getBuyingItems());
/* 732 */     jSonStorage.setTransactions(sqlStorage.getTransactions());
/*     */     
/* 734 */     Persist persist = this.plugin.getPersist();
/* 735 */     persist.save(jSonStorage, "items");
/*     */     
/* 737 */     message(paramCommandSender, "§aAll items have just been converted.", new Object[0]);
/* 738 */     this.isConverting = false;
/*     */   }
/*     */   
/*     */   public void setConverting(boolean paramBoolean) {
/* 742 */     this.isConverting = paramBoolean;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\convert\ZConvertManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */