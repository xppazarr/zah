/*     */ package fr.maxlego08.zauctionhouse.blacklist;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
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
/*     */ public class BlacklistManager
/*     */   extends ZUtils
/*     */   implements IBlacklistManager
/*     */ {
/*  27 */   private final List<ItemChecker> blacklists = new ArrayList<>();
/*     */ 
/*     */   
/*     */   public List<ItemChecker> getBlacklist() {
/*  31 */     return Collections.unmodifiableList(this.blacklists);
/*     */   }
/*     */ 
/*     */   
/*     */   public void registerBlacklist(ItemChecker paramItemChecker) {
/*  36 */     this.blacklists.add(paramItemChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterBlacklist(ItemChecker paramItemChecker) {
/*  41 */     this.blacklists.remove(paramItemChecker);
/*     */   }
/*     */ 
/*     */   
/*     */   public Optional<ItemChecker> isBlacklist(ItemStack paramItemStack) {
/*  46 */     return this.blacklists.stream().filter(paramItemChecker -> paramItemChecker.checkItemStack(paramItemStack)).findFirst();
/*     */   }
/*     */ 
/*     */   
/*     */   public void unregisterAll() {
/*  51 */     this.blacklists.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void loadConfigurations(ZAuctionPlugin paramZAuctionPlugin) {
/*  57 */     this.blacklists.removeIf(paramItemChecker -> paramItemChecker.getName().startsWith("zauctionhouse:"));
/*     */     
/*  59 */     File file = new File(paramZAuctionPlugin.getDataFolder(), "blacklist.yml");
/*  60 */     if (!file.exists()) {
/*  61 */       paramZAuctionPlugin.saveResource("blacklist.yml", false);
/*  62 */       file = new File(paramZAuctionPlugin.getDataFolder(), "blacklist.yml");
/*     */     } 
/*     */     
/*  65 */     YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
/*  66 */     List list = yamlConfiguration.getList("blacklists");
/*     */     
/*  68 */     if (list != null) {
/*  69 */       list.forEach(paramMap -> {
/*     */             String str = (String)paramMap.get("type");
/*     */             
/*     */             switch (str.toLowerCase()) {
/*     */               case "zauctionhouse:material_similar":
/*     */                 try {
/*     */                   Material material = Material.valueOf(((String)paramMap.get("key")).toUpperCase());
/*     */                   this.blacklists.add(new BlacklistMaterial(material));
/*  77 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material was not found for zauctionhouse:material_similar", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:contains_lore":
/*     */                 try {
/*     */                   this.blacklists.add(new BlacklistLore((String)paramMap.get("key")));
/*  84 */                 } catch (Exception exception) {
/*     */                   Logger.info("Lore was not found for zauctionhouse:contains_lore", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               case "zauctionhouse:similar_model_id":
/*     */                 try {
/*     */                   this.blacklists.add(new BlacklistModelId((String)paramMap.get("key"), ((Integer)paramMap.get("modelId")).intValue()));
/*  91 */                 } catch (Exception exception) {
/*     */                   Logger.info("Material or ModelId was not found for zauctionhouse:similar_model_id", Logger.LogType.ERROR);
/*     */                 } 
/*     */                 return;
/*     */               
/*     */               case "zauctionhouse:names_contains":
/*     */                 try {
/*     */                   this.blacklists.add(new BlacklistName((String)paramMap.get("key")));
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
/* 112 */     Logger.info("Loaded " + this.blacklists.size() + " blacklist items", Logger.LogType.INFO);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\blacklist\BlacklistManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */