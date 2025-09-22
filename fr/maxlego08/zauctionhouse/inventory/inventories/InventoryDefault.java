/*     */ package fr.maxlego08.zauctionhouse.inventory.inventories;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.blacklist.IBlacklistManager;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.AdminRemoveButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.AuctionButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.BackButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.CategoryButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.HomeButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.InventoryButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PerformButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PermissibleButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PlaceholderButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.SellSlotButton;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.SlotButton;
/*     */ import fr.maxlego08.zauctionhouse.api.category.Category;
/*     */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryOpenException;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryTypeException;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
/*     */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*     */ import fr.maxlego08.zauctionhouse.inventory.VInventory;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ElapsedTime;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.InventoryResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.Pagination;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.ZButton;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.function.Consumer;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.block.Container;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.ClickType;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.inventory.Inventory;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.BlockStateMeta;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ public class InventoryDefault
/*     */   extends VInventory {
/*     */   private Inventory inventory;
/*     */   private List<Inventory> oldInventories;
/*     */   private Command command;
/*     */   private String categoryName;
/*     */   private Category category;
/*     */   private SellSlotButton sellButton;
/*     */   private boolean lastUpdate;
/*     */   
/*     */   public InventoryResult openInventory(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt, Object... paramVarArgs) {
/*  75 */     ElapsedTime elapsedTime1 = new ElapsedTime("InventoryDefault");
/*  76 */     elapsedTime1.start();
/*     */     
/*  78 */     long l = System.currentTimeMillis();
/*     */     
/*  80 */     if (paramVarArgs.length < 3) {
/*  81 */       throw new InventoryOpenException("Pas assez d'argument pour ouvrir l'inventaire");
/*     */     }
/*     */     
/*  84 */     ElapsedTime elapsedTime2 = new ElapsedTime("Reading the arguments");
/*  85 */     elapsedTime2.start();
/*     */ 
/*     */     
/*  88 */     this.inventory = (Inventory)paramVarArgs[0];
/*  89 */     this.oldInventories = (List<Inventory>)paramVarArgs[1];
/*  90 */     this.command = (Command)paramVarArgs[2];
/*  91 */     this.categoryName = (String)paramVarArgs[3];
/*     */     
/*  93 */     elapsedTime2.endDisplay();
/*     */     
/*  95 */     if (!this.inventory.getType().isDefault()) {
/*  96 */       throw new InventoryTypeException("Cannot open default inventory with type " + this.inventory.getType());
/*     */     }
/*     */     
/*  99 */     ElapsedTime elapsedTime3 = new ElapsedTime("Retrieving the list of items");
/* 100 */     elapsedTime3.start();
/*     */ 
/*     */     
/* 103 */     if (this.inventory.getType().equals(InventoryType.AUCTION)) {
/* 104 */       this.auctionItems = this.auctionManager.getSortItems(paramPlayer);
/* 105 */     } else if (this.inventory.getType().equals(InventoryType.EXPIRE)) {
/* 106 */       this.auctionItems = this.auctionManager.getExpire(paramPlayer);
/* 107 */     } else if (this.inventory.getType().equals(InventoryType.BUYING)) {
/* 108 */       this.auctionItems = this.auctionManager.getBuying(paramPlayer);
/* 109 */     } else if (this.inventory.getType().equals(InventoryType.ITEMS)) {
/* 110 */       this.auctionItems = this.auctionManager.getItems(paramPlayer);
/* 111 */     } else if (this.inventory.getType().equals(InventoryType.SEARCH)) {
/* 112 */       this.auctionItems = this.auctionManager.getSearch(paramPlayer, this.auctionManager.getPlayerWord((OfflinePlayer)paramPlayer));
/* 113 */     } else if (this.inventory.getType().equals(InventoryType.CATEGORY)) {
/*     */       
/* 115 */       Optional<Category> optional = paramZAuctionPlugin.getCategoryManager().getByName(this.categoryName);
/* 116 */       if (optional.isPresent()) {
/* 117 */         this.category = optional.get();
/* 118 */         this.auctionManager.setCurrentCategory(paramPlayer, this.category);
/* 119 */         this.auctionItems = this.auctionManager.getSortItems(paramPlayer, this.category);
/*     */       } else {
/* 121 */         this.auctionItems = new ArrayList();
/* 122 */         Logger.info("Impossible to find the category " + this.categoryName, Logger.LogType.ERROR);
/*     */       }
/*     */     
/* 125 */     } else if (this.inventory.getType().equals(InventoryType.SELL)) {
/* 126 */       this.disableClick = false;
/*     */     } 
/*     */     
/* 129 */     elapsedTime3.endDisplay();
/*     */     
/* 131 */     ElapsedTime elapsedTime4 = new ElapsedTime("Max page retrieval");
/* 132 */     elapsedTime4.start();
/* 133 */     int i = this.inventory.getMaxPage(this.auctionItems);
/* 134 */     elapsedTime4.endDisplay();
/*     */     
/* 136 */     ElapsedTime elapsedTime5 = new ElapsedTime("Pagination of items");
/* 137 */     elapsedTime5.start();
/* 138 */     if (this.inventory.getType().isPagination()) {
/* 139 */       Pagination pagination = new Pagination();
/* 140 */       this.auctionItems = pagination.paginate(this.auctionItems, this.inventory.getAuctionPageSize(), paramInt);
/*     */     } 
/* 142 */     elapsedTime5.endDisplay();
/*     */     
/* 144 */     ElapsedTime elapsedTime6 = new ElapsedTime("Creation of back buttons");
/* 145 */     elapsedTime6.start();
/* 146 */     int j = this.oldInventories.size() - 1;
/* 147 */     if (j >= 0) {
/* 148 */       Inventory inventory = this.oldInventories.get(j);
/*     */       
/* 150 */       this.inventory.getButtons(BackButton.class).forEach(paramBackButton -> paramBackButton.setBackInventory((paramInventory == null) ? this.inventory : paramInventory));
/*     */     } 
/*     */ 
/*     */     
/* 154 */     elapsedTime6.endDisplay();
/*     */     
/* 156 */     ElapsedTime elapsedTime7 = new ElapsedTime("Creation of the home buttons");
/* 157 */     elapsedTime7.start();
/* 158 */     this.inventory.getButtons(HomeButton.class).forEach(paramHomeButton -> paramHomeButton.setBackInventory(this.command.getInventory()));
/* 159 */     elapsedTime7.endDisplay();
/*     */     
/* 161 */     ElapsedTime elapsedTime8 = new ElapsedTime("Button Sorting");
/* 162 */     elapsedTime8.start();
/* 163 */     List<PlaceholderButton> list = this.inventory.sortButtons(paramInt);
/* 164 */     elapsedTime8.endDisplay();
/*     */     
/* 166 */     ElapsedTime elapsedTime9 = new ElapsedTime("Creation of the inventory");
/* 167 */     elapsedTime9.start();
/*     */     
/* 169 */     String str = this.inventory.getName();
/* 170 */     str = str.replace("%page%", String.valueOf(paramInt));
/* 171 */     str = str.replace("%maxPage%", String.valueOf(i));
/*     */     
/* 173 */     if (this.inventory.getType().equals(InventoryType.CATEGORY)) {
/* 174 */       if (this.category == null) {
/* 175 */         Logger.getLogger().log("Attention, your configuration for the categories is incorrect!", Logger.LogType.ERROR);
/*     */       } else {
/* 177 */         str = str.replace("%zauctionhouse_category%", this.category.getDisplayName());
/*     */       } 
/*     */     }
/*     */     
/* 181 */     createInventory(papi(color(str), paramPlayer), this.inventory.size());
/* 182 */     elapsedTime9.endDisplay();
/*     */     
/* 184 */     ElapsedTime elapsedTime10 = new ElapsedTime("Creation of buttons");
/* 185 */     elapsedTime10.start();
/* 186 */     if (AuctionConfiguration.enableOpenSyncInventory) {
/* 187 */       buildButtons(list, paramZAuctionPlugin, i);
/*     */     } else {
/* 189 */       runAsync(() -> buildButtons(paramList, paramZAuctionPlugin, paramInt));
/*     */     } 
/* 191 */     elapsedTime10.endDisplay();
/*     */     
/* 193 */     elapsedTime1.endDisplay();
/*     */ 
/*     */     
/* 196 */     if (AuctionConfiguration.enableDebugMode) {
/* 197 */       String str1 = "(" + this.inventory.getType() + ") ";
/* 198 */       if (this.auctionItems != null) {
/* 199 */         paramZAuctionPlugin.getLogger().info(str1 + "Opening the inventory in: " + (System.currentTimeMillis() - l) + "ms page " + paramInt + ", item number: " + this.auctionItems.size());
/*     */       } else {
/* 201 */         paramZAuctionPlugin.getLogger().info(str1 + "Opening the inventory in: " + (System.currentTimeMillis() - l) + "ms page " + paramInt);
/*     */       } 
/*     */     } 
/* 204 */     return InventoryResult.SUCCESS;
/*     */   }
/*     */   
/*     */   private void buildButtons(List<PlaceholderButton> paramList, ZAuctionPlugin paramZAuctionPlugin, int paramInt) {
/* 208 */     for (PlaceholderButton placeholderButton : paramList)
/* 209 */       buildButton(placeholderButton, paramZAuctionPlugin, paramInt); 
/*     */   }
/*     */   
/*     */   private void buildButton(PlaceholderButton paramPlaceholderButton, ZAuctionPlugin paramZAuctionPlugin, int paramInt) {
/* 213 */     if (paramPlaceholderButton.hasPermission())
/*     */     
/* 215 */     { if (!paramPlaceholderButton.checkPermission(this.player) && paramPlaceholderButton.hasElseButton()) {
/*     */         
/* 217 */         int i = paramPlaceholderButton.getTmpSlot();
/* 218 */         PlaceholderButton placeholderButton = (PlaceholderButton)paramPlaceholderButton.getElseButton().toButton(PlaceholderButton.class);
/* 219 */         ZButton zButton = addItem(i, placeholderButton.getCustomItemStack(this.player));
/*     */         
/* 221 */         if (placeholderButton.isClickable())
/* 222 */           zButton.setClick(clickEvent(paramZAuctionPlugin, this.player, this.page, paramInt, placeholderButton, i)); 
/*     */       } else {
/* 224 */         displayButton(paramPlaceholderButton, paramZAuctionPlugin, paramInt);
/*     */       }  }
/* 226 */     else { displayButton(paramPlaceholderButton, paramZAuctionPlugin, paramInt); }
/*     */   
/*     */   }
/*     */   
/*     */   private void displayButton(PlaceholderButton paramPlaceholderButton, ZAuctionPlugin paramZAuctionPlugin, int paramInt) {
/* 231 */     int i = paramPlaceholderButton.getSlot();
/* 232 */     if (paramPlaceholderButton.getType().isSlots()) {
/* 233 */       ((SlotButton)paramPlaceholderButton.toButton(SlotButton.class)).getSlots().forEach(paramInteger -> addItem(paramInteger.intValue(), paramPlaceholderButton.getCustomItemStack(this.player)).setClick(()));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 241 */     else if (paramPlaceholderButton.getType().equals(ButtonType.SELL_INVENTORY_SLOT)) {
/*     */       
/* 243 */       SellSlotButton sellSlotButton = (SellSlotButton)paramPlaceholderButton.toButton(SellSlotButton.class);
/* 244 */       if (this.inventory.getType().equals(InventoryType.SELL_SHOW))
/* 245 */       { displayInventoryItems(sellSlotButton, paramZAuctionPlugin, this.player); }
/* 246 */       else { this.sellButton = sellSlotButton; }
/*     */     
/* 248 */     } else if (paramPlaceholderButton.getType().equals(ButtonType.SHOW_SLOT)) {
/*     */       
/* 250 */       SlotButton slotButton = (SlotButton)paramPlaceholderButton.toButton(SlotButton.class);
/* 251 */       if (this.inventory.getType().equals(InventoryType.SHOW)) {
/* 252 */         displayInventoryItems(slotButton, paramZAuctionPlugin, this.player);
/*     */       }
/* 254 */     } else if (paramPlaceholderButton.getType().isAuction()) {
/*     */       
/* 256 */       if (this.auctionItems != null) displayAuctionButtons(paramPlaceholderButton, paramZAuctionPlugin, paramInt, this.player);
/*     */     
/* 258 */     } else if (paramPlaceholderButton.getType().isPageChange() && paramPlaceholderButton.hasElseButton()) {
/*     */       
/* 260 */       PlaceholderButton placeholderButton = paramPlaceholderButton;
/* 261 */       switch (paramPlaceholderButton.getType()) {
/*     */         case NEXT:
/* 263 */           if (this.page >= paramInt) placeholderButton = (PlaceholderButton)paramPlaceholderButton.getElseButton().toButton(PlaceholderButton.class); 
/*     */           break;
/*     */         case PREVIOUS:
/* 266 */           if (this.page == 1) placeholderButton = (PlaceholderButton)paramPlaceholderButton.getElseButton().toButton(PlaceholderButton.class);
/*     */           
/*     */           break;
/*     */       } 
/*     */ 
/*     */       
/* 272 */       displayFinalButton(placeholderButton, paramZAuctionPlugin, paramInt, this.player, i);
/*     */     } else {
/* 274 */       displayFinalButton(paramPlaceholderButton, paramZAuctionPlugin, paramInt, this.player, i);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void displayInventoryItems(SellSlotButton paramSellSlotButton, ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer) {
/* 279 */     AuctionItem auctionItem = (AuctionItem)this.args[4];
/* 280 */     List<Integer> list = paramSellSlotButton.getSlots();
/* 281 */     List<ItemStack> list1 = auctionItem.getItemStacks();
/* 282 */     for (byte b = 0; b != Math.min(list1.size(), list.size()); b++) {
/*     */       
/* 284 */       int i = ((Integer)list.get(b)).intValue();
/* 285 */       ItemStack itemStack = list1.get(b);
/* 286 */       addItem(i, itemStack).setClick(paramInventoryClickEvent -> {
/*     */             if (paramSellSlotButton.closeInventory()) {
/*     */               paramPlayer.closeInventory();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void displayInventoryItems(SlotButton paramSlotButton, ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer) {
/* 298 */     AuctionItem auctionItem = (AuctionItem)this.args[4];
/* 299 */     List<Integer> list = paramSlotButton.getSlots();
/*     */     
/* 301 */     ItemStack itemStack = auctionItem.getItemStack();
/* 302 */     ItemStack[] arrayOfItemStack = null;
/* 303 */     if (itemStack.getItemMeta() instanceof BlockStateMeta) {
/* 304 */       BlockStateMeta blockStateMeta = (BlockStateMeta)itemStack.getItemMeta();
/* 305 */       if (blockStateMeta.getBlockState() instanceof Container) {
/* 306 */         Container container = (Container)blockStateMeta.getBlockState();
/* 307 */         arrayOfItemStack = container.getInventory().getContents();
/*     */       } 
/*     */     } 
/*     */     
/* 311 */     if (arrayOfItemStack != null) for (byte b = 0; b != Math.min(arrayOfItemStack.length, list.size()); b++) {
/*     */         
/* 313 */         int i = ((Integer)list.get(b)).intValue();
/* 314 */         ItemStack itemStack1 = arrayOfItemStack[b];
/* 315 */         addItem(i, itemStack1).setClick(paramInventoryClickEvent -> {
/*     */               if (paramSlotButton.closeInventory()) {
/*     */                 paramPlayer.closeInventory();
/*     */               }
/*     */             });
/*     */       } 
/*     */   
/*     */   }
/*     */ 
/*     */   
/*     */   private void displayAuctionButtons(PlaceholderButton paramPlaceholderButton, ZAuctionPlugin paramZAuctionPlugin, int paramInt, Player paramPlayer) {
/* 326 */     List<Integer> list = ((AuctionButton)paramPlaceholderButton.toButton(AuctionButton.class)).getSlots();
/* 327 */     for (byte b = 0; b != Math.min(this.auctionItems.size(), list.size()); b++) {
/* 328 */       int i = ((Integer)list.get(b)).intValue();
/* 329 */       AuctionItem auctionItem = this.auctionItems.get(b);
/*     */       
/* 331 */       AuctionType auctionType = auctionItem.getType();
/* 332 */       Message message = auctionType.getInventoryMessage(this.inventory.getType());
/*     */       
/* 334 */       ItemStack itemStack = auctionItem.createItem(paramPlayer, message);
/* 335 */       if (this.inventory.getType().isAuction() && auctionItem.getType() == AuctionType.DEFAULT && auctionItem.getItemStack().getType().name().endsWith("SHULKER_BOX"))
/* 336 */         message = Message.ITEM_LORE_INVENTORY; 
/* 337 */       ZButton zButton = addItem(i, itemStack);
/*     */       
/* 339 */       InventoryType inventoryType = this.inventory.getType();
/* 340 */       if (inventoryType.isAuction()) {
/* 341 */         Consumer<InventoryClickEvent> consumer = auctionClick(paramZAuctionPlugin, paramPlayer, this.page, paramInt, (PermissibleButton)paramPlaceholderButton, auctionItem, i, itemStack);
/* 342 */         zButton.setClick(consumer);
/*     */       }
/*     */       else {
/*     */         
/* 346 */         StorageType storageType = inventoryType.equals(InventoryType.BUYING) ? StorageType.BUY : (inventoryType.equals(InventoryType.ITEMS) ? StorageType.STORAGE : StorageType.EXPIRE);
/* 347 */         zButton.setClick(paramInventoryClickEvent -> {
/*     */               if (paramAuctionItem.isAlreadyBuying()) {
/*     */                 if (AuctionConfiguration.displayErrorBuyMessage)
/*     */                   message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]); 
/*     */                 paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */               } else if (!paramAuctionItem.canBuy() && paramStorageType.equals(StorageType.STORAGE)) {
/*     */                 if (AuctionConfiguration.displayErrorBuyMessage)
/*     */                   message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]); 
/*     */                 paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */               } else {
/*     */                 this.auctionManager.removeItem(paramPlayer, paramAuctionItem, paramStorageType);
/*     */               } 
/*     */             });
/*     */       } 
/*     */     } 
/*     */   }
/*     */   private void displayFinalButton(PlaceholderButton paramPlaceholderButton, ZAuctionPlugin paramZAuctionPlugin, int paramInt1, Player paramPlayer, int paramInt2) {
/*     */     ItemStack itemStack;
/* 365 */     if (this.inventory.getType().equals(InventoryType.SELL_SHOW)) {
/*     */ 
/*     */       
/* 368 */       AuctionItem auctionItem = (AuctionItem)this.args[4];
/* 369 */       paramZAuctionPlugin.getPlaceholderAPI().setPlayer(paramPlayer, auctionItem);
/*     */     } 
/*     */ 
/*     */     
/* 373 */     if (paramPlaceholderButton.getType().equals(ButtonType.SHOW_ITEM)) {
/*     */       
/* 375 */       AuctionItem auctionItem = (AuctionItem)this.args[4];
/* 376 */       AuctionType auctionType = auctionItem.getType();
/* 377 */       Message message = auctionType.getInventoryMessage(this.inventory.getType());
/* 378 */       itemStack = auctionItem.createItem(paramPlayer, message);
/* 379 */       if (this.inventory.getType().isAuction() && auctionItem.getType() == AuctionType.DEFAULT && auctionItem.getItemStack().getType().name().endsWith("SHULKER_BOX"))
/* 380 */         message = Message.ITEM_LORE_INVENTORY; 
/*     */     } else {
/* 382 */       itemStack = paramPlaceholderButton.getCustomItemStack(paramPlayer);
/*     */     } 
/* 384 */     ZButton zButton = addItem(paramInt2, itemStack);
/*     */     
/* 386 */     if (paramPlaceholderButton.isClickable()) {
/* 387 */       Consumer<InventoryClickEvent> consumer = clickEvent(paramZAuctionPlugin, paramPlayer, this.page, paramInt1, paramPlaceholderButton, paramInt2);
/* 388 */       zButton.setClick(consumer);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private Consumer<InventoryClickEvent> clickEvent(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt1, int paramInt2, PlaceholderButton paramPlaceholderButton, int paramInt3) {
/* 394 */     return paramInventoryClickEvent -> {
/*     */         InventoryButton inventoryButton2;
/*     */         PerformButton performButton;
/*     */         AuctionItem auctionItem1;
/*     */         InventoryButton inventoryButton1;
/*     */         CategoryButton categoryButton;
/*     */         Inventory inventory;
/*     */         Object[] arrayOfObject;
/*     */         int i;
/*     */         StorageType storageType;
/*     */         TransactionManager transactionManager;
/*     */         AuctionItem auctionItem2;
/*     */         ArrayList<ItemStack> arrayList;
/*     */         ItemStack itemStack;
/*     */         AdminRemoveButton adminRemoveButton;
/*     */         IBlacklistManager iBlacklistManager;
/*     */         Consumer<InventoryClickEvent> consumer;
/*     */         Iterator<Integer> iterator;
/*     */         long l1;
/*     */         AuctionEconomy auctionEconomy;
/*     */         AuctionManager auctionManager;
/*     */         Optional<Priority> optional;
/*     */         boolean bool;
/*     */         UUID uUID;
/*     */         long l2;
/*     */         ZAuctionItem zAuctionItem;
/*     */         paramInventoryClickEvent.setCancelled(paramPlaceholderButton.isDisableEvent());
/*     */         PlaceholderButton placeholderButton = paramPlaceholderButton;
/*     */         if (placeholderButton.hasPermission()) {
/*     */           if (!paramPlaceholderButton.checkPermission(paramPlayer)) {
/*     */             if (placeholderButton.hasMessage()) {
/*     */               message((CommandSender)paramPlayer, placeholderButton.getMessage(), new Object[0]);
/*     */             }
/*     */             if (paramPlaceholderButton.hasElseButton()) {
/*     */               placeholderButton = (PlaceholderButton)placeholderButton.getElseButton().toButton(PlaceholderButton.class);
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         placeholderButton.playSound((Entity)paramPlayer);
/*     */         if (placeholderButton.closeInventory()) {
/*     */           paramPlayer.closeInventory();
/*     */         }
/*     */         switch (placeholderButton.getType()) {
/*     */           case NEXT:
/*     */             if (paramInt1 != paramInt2) {
/*     */               createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, paramInt1 + 1, this.args);
/*     */             }
/*     */             break;
/*     */           case PREVIOUS:
/*     */             if (paramInt1 != 1) {
/*     */               createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, paramInt1 - 1, this.args);
/*     */             }
/*     */             break;
/*     */           case INVENTORY:
/*     */             this.oldInventories.add(this.inventory);
/*     */             inventoryButton2 = (InventoryButton)placeholderButton.toButton(InventoryButton.class);
/*     */             inventory = inventoryButton2.getInventory();
/*     */             if (!inventory.getType().isDefault()) {
/*     */               message((CommandSender)paramPlayer, "§cUnable to navigate to the §f" + inventory.getName() + "inventory §c, please contact an administrator to correct the problem.", new Object[0]);
/*     */               Logger.info("Player " + paramPlayer.getName() + " wanted to go to the " + inventory.getName() + " inventory but the inventory type is incorrect.", Logger.LogType.ERROR);
/*     */               return;
/*     */             } 
/*     */             arrayOfObject = Arrays.copyOf(this.args, this.args.length);
/*     */             arrayOfObject[0] = inventory;
/*     */             createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, 1, arrayOfObject);
/*     */             break;
/*     */           case HOME:
/*     */             inventoryButton2 = (InventoryButton)placeholderButton.toButton(InventoryButton.class);
/*     */             createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, 1, new Object[] { inventoryButton2.getInventory(), new ArrayList(), this.command, null });
/*     */             break;
/*     */           case PERFORM_COMMAND:
/*     */             performButton = (PerformButton)placeholderButton.toButton(PerformButton.class);
/*     */             performButton.execute(paramPlayer);
/*     */             break;
/*     */           case REMOVE_ITEM:
/*     */             if (!this.inventory.getType().isRemove()) {
/*     */               return;
/*     */             }
/*     */             auctionItem1 = (AuctionItem)this.args[4];
/*     */             auctionItem1.setAlreadyBuying(false);
/*     */             if (!auctionItem1.canBuy() || auctionItem1.isAlreadyBuying() || auctionItem1.getStorageType() != StorageType.STORAGE) {
/*     */               if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */                 message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]);
/*     */               }
/*     */               this.auctionManager.openDefault(paramPlayer);
/*     */               return;
/*     */             } 
/*     */             if (AuctionConfiguration.giveItemAfterRemove && AuctionConfiguration.dontGiveIfPlayerHasFullInventory && !auctionItem1.hasFreeSpace(paramPlayer)) {
/*     */               message((CommandSender)paramPlayer, Message.ERROR_REMOVE_FULL, new Object[0]);
/*     */               this.auctionManager.openDefault(paramPlayer);
/*     */               return;
/*     */             } 
/*     */             this.auctionManager.remove(auctionItem1, paramPlayer, this.inventory.getType().isRemoveAsAdmin());
/*     */             break;
/*     */           case BUY_ITEM:
/*     */             if (!this.inventory.getType().isBuyConfirm()) {
/*     */               return;
/*     */             }
/*     */             auctionItem1 = (AuctionItem)this.args[4];
/*     */             if (!auctionItem1.canBuy() || auctionItem1.getStorageType() != StorageType.STORAGE) {
/*     */               if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */                 message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]);
/*     */               }
/*     */               this.auctionManager.openDefault(paramPlayer);
/*     */               return;
/*     */             } 
/*     */             auctionItem1.setAlreadyBuying(false);
/*     */             this.auctionManager.buy(auctionItem1, paramPlayer);
/*     */             break;
/*     */           case BACK:
/*     */             inventoryButton1 = (InventoryButton)placeholderButton.toButton(InventoryButton.class);
/*     */             inventory = inventoryButton1.getInventory();
/*     */             this.oldInventories.remove(inventory);
/*     */             if (this.inventory.getType().needToReset()) {
/*     */               AuctionItem auctionItem = (AuctionItem)this.args[4];
/*     */               if (auctionItem.isAlreadyBuying()) {
/*     */                 auctionItem.setAlreadyBuying(false);
/*     */               }
/*     */             } 
/*     */             arrayOfObject = Arrays.copyOf(this.args, this.args.length);
/*     */             arrayOfObject[0] = inventory;
/*     */             i = (this.args.length == 7) ? ((Integer)this.args[5]).intValue() : getPage();
/*     */             createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, i, arrayOfObject);
/*     */             break;
/*     */           case CATEGORIES:
/*     */             this.oldInventories.add(this.inventory);
/*     */             categoryButton = (CategoryButton)paramPlaceholderButton.toButton(CategoryButton.class);
/*     */             inventory = paramZAuctionPlugin.getInventories().getInventory(InventoryType.CATEGORY);
/*     */             if (!inventory.getType().isDefault()) {
/*     */               message((CommandSender)paramPlayer, "§cUnable to navigate to the §f" + inventory.getName() + "inventory §c, please contact an administrator to correct the problem.", new Object[0]);
/*     */               Logger.info("Player " + paramPlayer.getName() + " wanted to go to the " + inventory.getName() + " inventory but the inventory type is incorrect.", Logger.LogType.ERROR);
/*     */               return;
/*     */             } 
/*     */             arrayOfObject = Arrays.copyOf(this.args, this.args.length);
/*     */             arrayOfObject[0] = inventory;
/*     */             arrayOfObject[3] = categoryButton.getCategory();
/*     */             createInventory(paramZAuctionPlugin, paramPlayer, EnumInventory.INVENTORY_DEFAULT, 1, arrayOfObject);
/*     */             break;
/*     */           case REMOVE_ALL:
/*     */             storageType = this.inventory.getType().equals(InventoryType.BUYING) ? StorageType.BUY : (this.inventory.getType().equals(InventoryType.ITEMS) ? StorageType.STORAGE : StorageType.EXPIRE);
/*     */             this.auctionManager.removeAll(paramPlayer, storageType);
/*     */             break;
/*     */           case CHANGE_SORT:
/*     */             this.auctionManager.nextSort(paramPlayer);
/*     */             break;
/*     */           case CLAIM:
/*     */             transactionManager = paramZAuctionPlugin.getTransactionManager();
/*     */             transactionManager.claimMoney(paramPlayer);
/*     */             paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */             break;
/*     */           case SELL_INVENTORY_BUY:
/*     */             if (!this.inventory.getType().equals(InventoryType.SELL_SHOW)) {
/*     */               return;
/*     */             }
/*     */             auctionItem2 = (AuctionItem)this.args[4];
/*     */             itemStack = super.inventory.getItem(paramInt3);
/*     */             consumer = auctionClick(paramZAuctionPlugin, paramPlayer, paramInt1, paramInt2, (PermissibleButton)paramPlaceholderButton, auctionItem2, paramInt3, itemStack);
/*     */             auctionItem2.setAlreadyBuying(false);
/*     */             consumer.accept(paramInventoryClickEvent);
/*     */             break;
/*     */           case ADMIN_REMOVE:
/*     */             if (!this.inventory.getType().equals(InventoryType.ADMIN_REMOVE)) {
/*     */               return;
/*     */             }
/*     */             auctionItem2 = (AuctionItem)this.args[4];
/*     */             auctionItem2.setAlreadyBuying(false);
/*     */             adminRemoveButton = (AdminRemoveButton)paramPlaceholderButton.toButton(AdminRemoveButton.class);
/*     */             this.auctionManager.removeAdmin(paramPlayer, auctionItem2, adminRemoveButton.isSilent(), adminRemoveButton.isForceRemove());
/*     */             break;
/*     */           case SELL_INVENTORY_CANCEL:
/*     */             paramPlayer.closeInventory();
/*     */             break;
/*     */           case SELL_INVENTORY_ACCEPT:
/*     */             arrayList = new ArrayList();
/*     */             iBlacklistManager = paramZAuctionPlugin.getBlacklistManager();
/*     */             iterator = this.sellButton.getSlots().iterator();
/*     */             while (iterator.hasNext()) {
/*     */               int j = ((Integer)iterator.next()).intValue();
/*     */               Inventory inventory1 = super.inventory;
/*     */               ItemStack itemStack1 = inventory1.getItem(j);
/*     */               if (itemStack1 != null) {
/*     */                 Material material = itemStack1.getType();
/*     */                 if (AuctionConfiguration.disableSellBreakItem && !material.isBlock() && itemStack1.getDurability() > 0) {
/*     */                   continue;
/*     */                 }
/*     */                 if (iBlacklistManager.isBlacklist(itemStack1).isPresent()) {
/*     */                   continue;
/*     */                 }
/*     */                 arrayList.add(itemStack1);
/*     */               } 
/*     */             } 
/*     */             if (arrayList.size() == 0) {
/*     */               message((CommandSender)paramPlayer, Message.SELL_INVENTORY_ERROR, new Object[0]);
/*     */               return;
/*     */             } 
/*     */             l1 = ((Long)this.args[4]).longValue();
/*     */             auctionEconomy = (AuctionEconomy)this.args[5];
/*     */             auctionManager = paramZAuctionPlugin.getAuctionManager();
/*     */             optional = this.auctionManager.getPriority((Permissible)this.player);
/*     */             bool = optional.isPresent() ? ((Priority)optional.get()).getPriority() : false;
/*     */             uUID = UUID.randomUUID();
/*     */             l2 = (AuctionConfiguration.sellTime != -1L) ? (System.currentTimeMillis() + this.auctionManager.getExpirationPerPermission((Permissible)this.player) * 1000L) : AuctionConfiguration.sellTime;
/*     */             zAuctionItem = new ZAuctionItem(uUID, arrayList, AuctionType.INVENTORY, l1, auctionEconomy.getName(), paramPlayer.getUniqueId(), l2, StorageType.STORAGE, null, bool, paramZAuctionPlugin.getStorage().getServerName());
/*     */             auctionManager.sellItem((AuctionItem)zAuctionItem, null, paramPlayer, l1, auctionEconomy, arrayList.size(), AuctionType.INVENTORY);
/*     */             this.sellButton = null;
/*     */             paramPlayer.closeInventory();
/*     */             break;
/*     */         } 
/*     */       };
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
/*     */ 
/*     */ 
/*     */   
/*     */   private Consumer<InventoryClickEvent> auctionClick(ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer, int paramInt1, int paramInt2, PermissibleButton paramPermissibleButton, AuctionItem paramAuctionItem, int paramInt3, ItemStack paramItemStack) {
/* 644 */     return paramInventoryClickEvent -> {
/*     */         PermissibleButton permissibleButton = paramPermissibleButton;
/*     */         
/*     */         AuctionType auctionType = paramAuctionItem.getType();
/*     */         
/*     */         if (permissibleButton.hasPermission()) {
/*     */           if (!paramPlayer.hasPermission(permissibleButton.getPermission())) {
/*     */             if (permissibleButton.hasMessage()) {
/*     */               message((CommandSender)paramPlayer, permissibleButton.getMessage(), new Object[0]);
/*     */             }
/*     */             
/*     */             if (paramPermissibleButton.hasElseButton()) {
/*     */               permissibleButton = (PermissibleButton)permissibleButton.getElseButton().toButton(PermissibleButton.class);
/*     */             } else {
/*     */               return;
/*     */             } 
/*     */           } 
/*     */         }
/*     */         
/*     */         if (paramAuctionItem.getStorageType() != StorageType.STORAGE) {
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (paramAuctionItem.isExpired()) {
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (paramAuctionItem.isAlreadyBuying()) {
/*     */           if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */             message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]);
/*     */           }
/*     */           
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else if (!paramAuctionItem.canBuy()) {
/*     */           if (AuctionConfiguration.displayErrorBuyMessage) {
/*     */             message((CommandSender)paramPlayer, Message.ERROR_BUY_ERROR, new Object[0]);
/*     */           }
/*     */           
/*     */           paramZAuctionPlugin.getInventoryManager().update(paramPlayer);
/*     */         } else {
/*     */           if (auctionType.equals(AuctionType.INVENTORY) && paramInventoryClickEvent.getClick().equals(AuctionConfiguration.showClick) && !this.inventory.getType().equals(InventoryType.SELL_SHOW)) {
/*     */             paramAuctionItem.setAlreadyBuying(true);
/*     */             
/*     */             this.oldInventories.add(this.inventory);
/*     */             
/*     */             paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.SELL_SHOW, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */             
/*     */             return;
/*     */           } 
/*     */           
/*     */           Material material = paramItemStack.getType();
/*     */           if (paramInventoryClickEvent.getClick().equals(AuctionConfiguration.showClick)) {
/*     */             if (NmsVersion.nmsVersion.isShulker() && material.name().endsWith("SHULKER_BOX")) {
/*     */               this.oldInventories.add(this.inventory);
/*     */               paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.SHOW, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */               return;
/*     */             } 
/*     */             if (NmsVersion.nmsVersion.isBarrel() && material.equals(Material.BARREL)) {
/*     */               this.oldInventories.add(this.inventory);
/*     */               paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.SHOW, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */               return;
/*     */             } 
/*     */           } 
/* 703 */           boolean bool = ((paramInventoryClickEvent.getClick().equals(ClickType.MIDDLE) || paramInventoryClickEvent.getClick().equals(ClickType.DROP)) && paramPlayer.hasPermission(Permission.ZAUCTIONHOUSE_ADMIN_REMOVE.getPermission())) ? true : false;
/*     */           AuctionEconomy auctionEconomy = paramAuctionItem.getEconomy();
/*     */           if (!auctionEconomy.hasMoney((OfflinePlayer)paramPlayer, paramAuctionItem.getPrice()) && !paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId()) && !bool) {
/*     */             if (AuctionConfiguration.useNoMoneyItem) {
/*     */               if (this.lastUpdate) {
/*     */                 return;
/*     */               }
/*     */               this.lastUpdate = true;
/*     */               super.inventory.setItem(paramInt2, Message.NO_MONEY_BUTTON.getItemStack());
/*     */               schedule(paramPlayer.getLocation(), AuctionConfiguration.noMoneyItemTicks, ());
/*     */               return;
/*     */             } 
/*     */             message((CommandSender)paramPlayer, Message.ERROR_BUY_MONEY, new Object[0]);
/*     */             return;
/*     */           } 
/*     */           paramAuctionItem.setAlreadyBuying(true);
/*     */           this.oldInventories.add(this.inventory);
/*     */           if (bool) {
/*     */             paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.ADMIN_REMOVE, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */           } else if (paramAuctionItem.getSellerUniqueId().equals(paramPlayer.getUniqueId())) {
/*     */             paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.REMOVE_CONFIRM, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */           } else {
/*     */             paramZAuctionPlugin.getAuctionManager().open((AuctionPlugin)paramZAuctionPlugin, InventoryType.BUY_CONFIRM, paramPlayer, paramAuctionItem, paramInt1, this.oldInventories, this.categoryName);
/*     */           } 
/*     */         } 
/*     */       };
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onClose(InventoryCloseEvent paramInventoryCloseEvent, ZAuctionPlugin paramZAuctionPlugin, Player paramPlayer) {
/* 750 */     if (this.inventory.getType().needToReset()) {
/*     */       
/* 752 */       AuctionItem auctionItem = (AuctionItem)this.args[4];
/* 753 */       if (auctionItem.isAlreadyBuying()) auctionItem.setAlreadyBuying(false);
/*     */     
/* 755 */     } else if (this.inventory.getType().equals(InventoryType.SELL) && this.sellButton != null) {
/*     */       
/* 757 */       this.sellButton.getSlots().forEach(paramInteger -> {
/*     */             Inventory inventory = super.inventory;
/*     */             ItemStack itemStack = inventory.getItem(paramInteger.intValue());
/*     */             if (itemStack != null)
/*     */               give(paramPlayer, itemStack); 
/*     */           });
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\inventory\inventories\InventoryDefault.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */