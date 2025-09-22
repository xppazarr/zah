/*     */ package fr.maxlego08.zauctionhouse.buttons.multiple;
/*     */ 
/*     */ import fr.maxlego08.menu.api.button.PaginateButton;
/*     */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*     */ import fr.maxlego08.menu.api.utils.Placeholders;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.buttons.AuctionHelperButton;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ElapsedTime;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.entity.Player;
/*     */ 
/*     */ public abstract class MultipleButton
/*     */   extends PaginateButton {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private final List<InventoryType> inventoryTypes;
/*  24 */   private final Map<Player, List<ItemCache>> items = new HashMap<>();
/*     */   private final AuctionManager auctionManager;
/*     */   
/*     */   public MultipleButton(ZAuctionPlugin paramZAuctionPlugin, List<InventoryType> paramList) {
/*  28 */     this.plugin = paramZAuctionPlugin;
/*  29 */     this.inventoryTypes = paramList;
/*  30 */     this.auctionManager = paramZAuctionPlugin.getAuctionManager();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void onRender(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/*  36 */     ElapsedTime elapsedTime = new ElapsedTime("Render auctions items for " + (String)this.inventoryTypes.stream().map(Enum::name).collect(Collectors.joining(", ")));
/*  37 */     elapsedTime.start();
/*     */     
/*  39 */     List<ItemCache> list = getAuctionItems(paramPlayer);
/*  40 */     paginate(list, paramInventoryEngine, (paramInteger, paramItemCache) -> {
/*     */           AuctionItem auctionItem = paramItemCache.getAuctionItem();
/*     */           
/*     */           InventoryType inventoryType = paramItemCache.getInventoryType();
/*     */           
/*     */           AuctionHelperButton.renderButton(this.plugin, auctionItem, paramInventoryEngine, paramPlayer, paramInteger.intValue(), inventoryType);
/*     */         });
/*     */     
/*  48 */     this.items.remove(paramPlayer);
/*  49 */     elapsedTime.endDisplay();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasSpecialRender() {
/*  54 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission() {
/*  59 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPermission(Player paramPlayer, InventoryEngine paramInventoryEngine, Placeholders paramPlaceholders) {
/*  64 */     return !getAuctionItems(paramPlayer).isEmpty();
/*     */   }
/*     */ 
/*     */   
/*     */   private List<ItemCache> getAuctionItems(Player paramPlayer) {
/*  69 */     if (this.items.containsKey(paramPlayer)) {
/*  70 */       return this.items.getOrDefault(paramPlayer, new ArrayList<>());
/*     */     }
/*     */     
/*  73 */     ArrayList<ItemCache> arrayList = new ArrayList();
/*  74 */     for (InventoryType inventoryType : this.inventoryTypes) {
/*  75 */       switch (inventoryType) {
/*     */         case ITEMS:
/*  77 */           arrayList.addAll((Collection)this.auctionManager.getItems(paramPlayer).stream().map(paramAuctionItem -> new ItemCache(paramAuctionItem, InventoryType.ITEMS)).collect(Collectors.toList()));
/*     */         
/*     */         case EXPIRE:
/*  80 */           arrayList.addAll((Collection)this.auctionManager.getExpire(paramPlayer).stream().map(paramAuctionItem -> new ItemCache(paramAuctionItem, InventoryType.EXPIRE)).collect(Collectors.toList()));
/*     */         
/*     */         case BUYING:
/*  83 */           arrayList.addAll((Collection)this.auctionManager.getBuying(paramPlayer).stream().map(paramAuctionItem -> new ItemCache(paramAuctionItem, InventoryType.BUYING)).collect(Collectors.toList()));
/*     */       } 
/*     */     
/*     */     } 
/*  87 */     arrayList.sort((paramItemCache1, paramItemCache2) -> (int)(paramItemCache1.getAuctionItem().getExpireAt() - paramItemCache2.getAuctionItem().getExpireAt()));
/*     */     
/*  89 */     this.items.put(paramPlayer, arrayList);
/*  90 */     return arrayList;
/*     */   }
/*     */ 
/*     */   
/*     */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/*  95 */     this.items.remove(paramPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPaginationSize(Player paramPlayer) {
/* 100 */     return getAuctionItems(paramPlayer).size();
/*     */   }
/*     */   
/*     */   public static class ItemCache
/*     */   {
/*     */     private final AuctionItem auctionItem;
/*     */     private final InventoryType inventoryType;
/*     */     
/*     */     public ItemCache(AuctionItem param1AuctionItem, InventoryType param1InventoryType) {
/* 109 */       this.auctionItem = param1AuctionItem;
/* 110 */       this.inventoryType = param1InventoryType;
/*     */     }
/*     */     
/*     */     public AuctionItem getAuctionItem() {
/* 114 */       return this.auctionItem;
/*     */     }
/*     */     
/*     */     public InventoryType getInventoryType() {
/* 118 */       return this.inventoryType;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\multiple\MultipleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */