/*     */ package fr.maxlego08.zauctionhouse.buttons;
/*     */ 
/*     */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*     */ import fr.maxlego08.menu.api.engine.Pagination;
/*     */ import fr.maxlego08.menu.api.utils.Placeholders;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.buttons.AuctionButton;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ElapsedTime;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*     */ import java.util.ArrayList;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public class ZAuctionButton
/*     */   extends AuctionButton {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private final AuctionManager auctionManager;
/*     */   private final InventoryType inventoryType;
/*  29 */   private final Map<Player, List<AuctionItem>> items = new HashMap<>();
/*     */   
/*     */   public ZAuctionButton(ZAuctionPlugin paramZAuctionPlugin, InventoryType paramInventoryType) {
/*  32 */     this.plugin = paramZAuctionPlugin;
/*  33 */     this.auctionManager = paramZAuctionPlugin.getAuctionManager();
/*  34 */     this.inventoryType = paramInventoryType;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onRender(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/*  39 */     ElapsedTime elapsedTime = new ElapsedTime("Render auctions items for type " + this.inventoryType);
/*  40 */     elapsedTime.start();
/*     */     
/*  42 */     if (this.slots.isEmpty()) {
/*  43 */       elapsedTime.end();
/*  44 */       Logger.info("Attention, a button doesnt have slots !", Logger.LogType.ERROR);
/*     */       
/*     */       return;
/*     */     } 
/*  48 */     Pagination pagination = new Pagination();
/*  49 */     List<AuctionItem> list = getAuctionItems(paramPlayer);
/*  50 */     List list1 = pagination.paginate(list, this.slots.size(), paramInventoryEngine.getPage());
/*     */     
/*  52 */     Runnable runnable = () -> {
/*     */         for (byte b = 0; b != Math.min(paramList.size(), this.slots.size()); b++) {
/*     */           int i = ((Integer)this.slots.get(b)).intValue();
/*     */           AuctionItem auctionItem = paramList.get(b);
/*     */           AuctionHelperButton.renderButton(this.plugin, auctionItem, paramInventoryEngine, paramPlayer, i, this.inventoryType);
/*     */         } 
/*     */       };
/*  59 */     if (AuctionConfiguration.enableOpenSyncInventory) {
/*  60 */       runnable.run();
/*     */     } else {
/*  62 */       ZPlugin.serverImplementation.runAsync(paramWrappedTask -> paramRunnable.run());
/*     */     } 
/*     */     
/*  65 */     this.items.remove(paramPlayer);
/*  66 */     elapsedTime.endDisplay();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/*  71 */     this.items.remove(paramPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSpecialRender() {
/*  76 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public InventoryType getInventoryType() {
/*  81 */     return this.inventoryType;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission() {
/*  86 */     return true;
/*     */   }
/*     */   
/*     */   private List<AuctionItem> getAuctionItems(Player paramPlayer) {
/*     */     Category category;
/*  91 */     if (this.items.containsKey(paramPlayer)) {
/*  92 */       return this.items.get(paramPlayer);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*  97 */     switch (this.inventoryType)
/*     */     { case EXPIRE:
/*  99 */         list = this.auctionManager.getExpire(paramPlayer);
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 124 */         this.items.put(paramPlayer, list);
/* 125 */         return list;case BUYING: list = this.auctionManager.getBuying(paramPlayer); this.items.put(paramPlayer, list); return list;case ITEMS: list = this.auctionManager.getItems(paramPlayer); this.items.put(paramPlayer, list); return list;case SEARCH: list = this.auctionManager.getSearch(paramPlayer, this.auctionManager.getPlayerWord((OfflinePlayer)paramPlayer)); this.items.put(paramPlayer, list); return list;case CATEGORY: category = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer).getCategory(); if (category == null) { list = new ArrayList<>(); } else { this.auctionManager.setCurrentCategory(paramPlayer, category); list = this.auctionManager.getSortItems(paramPlayer, category); }  this.items.put(paramPlayer, list); return list; }  List<AuctionItem> list = this.auctionManager.getSortItems(paramPlayer); this.items.put(paramPlayer, list); return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPermission(Player paramPlayer, InventoryEngine paramInventoryEngine, Placeholders paramPlaceholders) {
/* 130 */     return !getAuctionItems(paramPlayer).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaginationSize(Player paramPlayer) {
/* 135 */     return getAuctionItems(paramPlayer).size();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZAuctionButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */