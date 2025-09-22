/*     */ package fr.maxlego08.zauctionhouse.inventory;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.builder.ItemBuilder;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.InventoryResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.ZButton;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ public abstract class VInventory
/*     */   extends ZUtils
/*     */   implements Cloneable
/*     */ {
/*     */   protected int id;
/*     */   protected ZAuctionPlugin plugin;
/*  29 */   protected Map<Integer, ZButton> items = new HashMap<>();
/*     */   protected Player player;
/*     */   protected int page;
/*     */   protected Object[] args;
/*     */   protected Inventory inventory;
/*     */   protected String guiName;
/*     */   protected boolean disableClick = true;
/*     */   protected boolean openAsync = false;
/*  37 */   protected int invnetorySize = 54;
/*     */   
/*     */   protected AuctionManager auctionManager;
/*  40 */   protected List<AuctionItem> auctionItems = null;
/*     */   
/*     */   public int getId() {
/*  43 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VInventory setId(int paramInt) {
/*  53 */     this.id = paramInt;
/*  54 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createInventory(String paramString) {
/*  64 */     createInventory(paramString, 54);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void createInventory(String paramString, int paramInt) {
/*  75 */     this.guiName = paramString;
/*  76 */     this.invnetorySize = paramInt;
/*  77 */     this.inventory = Bukkit.createInventory(null, paramInt, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void createDefaultInventory() {
/*  84 */     if (this.inventory == null) {
/*  85 */       this.invnetorySize = 54;
/*  86 */       this.inventory = Bukkit.createInventory(null, 54, "§cDefault Inventory");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZButton addItem(int paramInt, Material paramMaterial, String paramString) {
/*  99 */     return addItem(paramInt, (new ItemBuilder(paramMaterial, paramString)).build());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZButton addItem(int paramInt, ItemBuilder paramItemBuilder) {
/* 110 */     return addItem(paramInt, paramItemBuilder.build());
/*     */   }
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
/*     */   public ZButton addItem(int paramInt, ItemStack paramItemStack) {
/* 123 */     createDefaultInventory();
/*     */ 
/*     */     
/* 126 */     ItemStack itemStack = (AuctionConfiguration.dupeConfig != null && AuctionConfiguration.dupeConfig.isEnable()) ? this.plugin.getDupeManager().protectItem(paramItemStack) : paramItemStack;
/*     */     
/* 128 */     ZButton zButton = new ZButton(itemStack);
/*     */     
/* 130 */     if (paramInt >= this.invnetorySize) {
/* 131 */       Logger.info(" Attention, the slot " + paramInt + " is greater than the size of the inventory (which is " + this.invnetorySize + ")", Logger.LogType.ERROR);
/* 132 */       return zButton;
/*     */     } 
/*     */     
/* 135 */     this.items.put(Integer.valueOf(paramInt), zButton);
/*     */     
/* 137 */     if (AuctionConfiguration.enableOpenSyncInventory) {
/* 138 */       this.inventory.setItem(paramInt, itemStack);
/*     */     } else {
/* 140 */       runAsync(() -> this.inventory.setItem(paramInt, paramItemStack));
/*     */     } 
/*     */     
/* 143 */     return zButton;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeItem(int paramInt) {
/* 152 */     this.items.remove(Integer.valueOf(paramInt));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void clearItem() {
/* 159 */     this.items.clear();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Map<Integer, ZButton> getItems() {
/* 168 */     return this.items;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isDisableClick() {
/* 178 */     return this.disableClick;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setDisableClick(boolean paramBoolean) {
/* 187 */     this.disableClick = paramBoolean;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/* 196 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getPage() {
/* 205 */     return this.page;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Object[] getObjets() {
/* 212 */     return this.args;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Inventory getInventory() {
/* 219 */     return this.inventory;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getGuiName() {
/* 226 */     return this.guiName;
/*     */   }
/*     */ 
/*     */   
/*     */   protected InventoryResult preOpenInventory(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt, Object... paramVarArgs) {
/* 231 */     this.page = paramInt;
/* 232 */     this.args = paramVarArgs;
/* 233 */     this.player = paramPlayer;
/* 234 */     this.plugin = paramZAuctionPlugin;
/*     */     
/* 236 */     this.auctionManager = paramZAuctionPlugin.getAuctionManager();
/*     */     
/* 238 */     return openInventory(paramZAuctionPlugin, paramPlayer, paramInt, paramVarArgs);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onClose(InventoryCloseEvent paramInventoryCloseEvent, ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void onDrag(InventoryDragEvent paramInventoryDragEvent, ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VInventory clone() {
/*     */     try {
/* 262 */       return (VInventory)getClass().newInstance();
/* 263 */     } catch (InstantiationException instantiationException) {
/*     */       
/* 265 */       instantiationException.printStackTrace();
/* 266 */     } catch (IllegalAccessException illegalAccessException) {
/*     */       
/* 268 */       illegalAccessException.printStackTrace();
/*     */     } 
/* 270 */     return null;
/*     */   }
/*     */   
/*     */   public abstract InventoryResult openInventory(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt, Object... paramVarArgs);
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\inventory\VInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */