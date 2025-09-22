/*     */ package fr.maxlego08.zauctionhouse.blacklist;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.IWhitelistManager;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.ItemChecker;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.blacklists.BlacklistLore;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.blacklists.BlacklistMaterial;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.blacklists.BlacklistModelId;
/*     */ import fr.maxlego08.zauctionhouse.blacklist.blacklists.BlacklistName;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.configuration.file.YamlConfiguration;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class WhitelistManager
/*     */   extends ZUtils
/*     */   implements IWhitelistManager
/*     */ {
/*  27 */   private final List<ItemChecker> itemCheckers = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public List<ItemChecker> getWhitelist() {
/*  31 */     return Collections.unmodifiableList(this.itemCheckers);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerWhitelist(ItemChecker paramItemChecker) {
/*  36 */     this.itemCheckers.add(paramItemChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterWhitelist(ItemChecker paramItemChecker) {
/*  41 */     this.itemCheckers.remove(paramItemChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ItemChecker> isWhitelist(ItemStack paramItemStack) {
/*  46 */     return this.itemCheckers.stream().filter(paramItemChecker -> paramItemChecker.checkItemStack(paramItemStack)).findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterAll() {
/*  51 */     this.itemCheckers.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfigurations(ZAuctionPlugin paramZAuctionPlugin) {
/*  58 */     this.itemCheckers.removeIf(paramItemChecker -> paramItemChecker.getName().startsWith("zauctionhouse:"));
/*     */     
/*  60 */     File file = new File(paramZAuctionPlugin.getDataFolder(), "whitelist.yml");
/*  61 */     if (!file.exists()) {
/*  62 */       paramZAuctionPlugin.saveResource("whitelist.yml", false);
/*  63 */       file = new File(paramZAuctionPlugin.getDataFolder(), "whitelist.yml");
/*     */     } 
/*     */     
/*  66 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/*  67 */     List list = yamlConfiguration.getList("whitelist");
/*     */     
/*  69 */     if (list != null) {
/*  70 */       list.forEach(paramMap -> {
/*     */             String str = (String)paramMap.get("type");
/*     */             
/*     */             switch (str) {
/*     */               case "zauctionhouse:material_similar":
/*     */                 try {
/*     */                   Material material = Material.valueOf(((String)paramMap.get("key")).toUpperCase());
/*     */                   this.itemCheckers.add(new BlacklistMaterial(material));
/*  78 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material was not found for zauctionhouse:material_similar", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:contains_lore":
/*     */                 try {
/*     */                   this.itemCheckers.add(new BlacklistLore((String)paramMap.get("key")));
/*  85 */                 } catch (Exception exception) {
/*     */                   Logger.info("Lore was not found for zauctionhouse:contains_lore", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:similar_model_id":
/*     */                 try {
/*     */                   this.itemCheckers.add(new BlacklistModelId((String)paramMap.get("key"), ((Integer)paramMap.get("modelId")).intValue()));
/*  92 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material or ModelId was not found for zauctionhouse:similar_model_id", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:names_contains":
/*     */                 try {
/*     */                   this.itemCheckers.add(new BlacklistName((String)paramMap.get("key")));
/*  99 */                 } catch (Exception exception) {
/*     */                   Logger.info("Name was not found for zauctionhouse:names_contains", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */             } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */             
/*     */             Logger.info(str + " type was not found !", Logger.LogType.ERROR);
/*     */           });
/*     */     }
/* 112 */     Logger.info("Loaded " + this.itemCheckers.size() + " whitelist items", Logger.LogType.INFO);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\blacklist\WhitelistManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */