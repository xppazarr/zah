/*     */ package fr.maxlego08.zauctionhouse.inventory;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryAlreadyExistException;
/*     */ import fr.maxlego08.zauctionhouse.api.exceptions.InventoryOpenException;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.VInventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.listener.ListenerAdapter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.InventoryResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.inventory.ZButton;
/*     */ import java.util.HashMap;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.inventory.InventoryType;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class ZInventoryManager extends ListenerAdapter implements VInventoryManager {
/*  29 */   private final Map<Integer, VInventory> inventories = new HashMap<>();
/*  30 */   private final Map<UUID, VInventory> playerInventories = new HashMap<>();
/*     */ 
/*     */   
/*     */   private final ZAuctionPlugin plugin;
/*     */ 
/*     */ 
/*     */   
/*     */   public ZInventoryManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  38 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */   
/*     */   public void sendLog() {
/*  42 */     this.plugin.getLog().log("Loading " + this.inventories.size() + " inventories", Logger.LogType.SUCCESS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerInventory(EnumInventory paramEnumInventory, VInventory paramVInventory) {
/*  53 */     if (!this.inventories.containsKey(Integer.valueOf(paramEnumInventory.getId()))) {
/*  54 */       this.inventories.put(Integer.valueOf(paramEnumInventory.getId()), paramVInventory);
/*     */     } else {
/*  56 */       throw new InventoryAlreadyExistException("Inventory with id " + paramVInventory.getId() + " already exist !");
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
/*     */   public void createInventory(EnumInventory paramEnumInventory, Player paramPlayer, int paramInt, Object... paramVarArgs) {
/*  69 */     createInventory(paramEnumInventory.getId(), paramPlayer, paramInt, paramVarArgs);
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
/*     */   public void createInventory(int paramInt1, Player paramPlayer, int paramInt2, Object... paramVarArgs) {
/*  81 */     Optional<VInventory> optional = getInventory(paramInt1);
/*     */     
/*  83 */     if (!optional.isPresent()) {
/*  84 */       message((CommandSender)paramPlayer, Message.INVENTORY_CLONE_NULL, new Object[] { "%id%", Integer.valueOf(paramInt1) });
/*     */       
/*     */       return;
/*     */     } 
/*  88 */     VInventory vInventory1 = optional.get();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  94 */     VInventory vInventory2 = vInventory1.clone();
/*     */     
/*  96 */     if (vInventory2 == null) {
/*  97 */       message((CommandSender)paramPlayer, Message.INVENTORY_CLONE_NULL, new Object[] { "%id%", Integer.valueOf(paramInt1) });
/*     */       
/*     */       return;
/*     */     } 
/* 101 */     vInventory2.setId(paramInt1);
/*     */     try {
/* 103 */       InventoryResult inventoryResult = vInventory2.preOpenInventory(this.plugin, paramPlayer, paramInt2, paramVarArgs);
/* 104 */       if (inventoryResult.equals(InventoryResult.SUCCESS)) {
/* 105 */         paramPlayer.openInventory(vInventory2.getInventory());
/* 106 */         this.playerInventories.put(paramPlayer.getUniqueId(), vInventory2);
/* 107 */       } else if (inventoryResult.equals(InventoryResult.ERROR)) {
/* 108 */         message((CommandSender)paramPlayer, Message.INVENTORY_OPEN_ERROR, new Object[] { "%id%", Integer.valueOf(paramInt1) });
/*     */       } 
/* 110 */     } catch (InventoryOpenException inventoryOpenException) {
/* 111 */       message((CommandSender)paramPlayer, Message.INVENTORY_OPEN_ERROR, new Object[] { "%id%", Integer.valueOf(paramInt1) });
/* 112 */       inventoryOpenException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void createInventory(VInventory paramVInventory, Player paramPlayer) {
/* 117 */     createInventory(paramVInventory.getId(), paramPlayer, paramVInventory.getPage(), paramVInventory.getObjets());
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onInventoryClick(InventoryClickEvent paramInventoryClickEvent, Player paramPlayer) {
/* 122 */     if (paramInventoryClickEvent.getClickedInventory() == null)
/* 123 */       return;  if (paramInventoryClickEvent.getWhoClicked() instanceof Player) {
/* 124 */       if (!exist(paramPlayer))
/* 125 */         return;  VInventory vInventory = this.playerInventories.get(paramPlayer.getUniqueId());
/* 126 */       if (vInventory.getGuiName() == null || vInventory.getGuiName().length() == 0) {
/* 127 */         Logger.info("An error has occurred with the menu ! " + vInventory.getClass().getName());
/*     */         return;
/*     */       } 
/* 130 */       if (paramInventoryClickEvent.getView() != null && vInventory.getPlayer().equals(paramPlayer) && paramInventoryClickEvent.getView().getTitle().equals(vInventory.getGuiName())) {
/*     */         
/* 132 */         paramInventoryClickEvent.setCancelled(vInventory.isDisableClick());
/*     */         
/* 134 */         if (paramInventoryClickEvent.getClickedInventory().getType().equals(InventoryType.PLAYER)) {
/*     */           return;
/*     */         }
/*     */         
/* 138 */         ZButton zButton = vInventory.getItems().getOrDefault(Integer.valueOf(paramInventoryClickEvent.getSlot()), null);
/* 139 */         if (zButton != null) {
/* 140 */           zButton.onClick(paramInventoryClickEvent);
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onInventoryClose(InventoryCloseEvent paramInventoryCloseEvent, Player paramPlayer) {
/* 148 */     if (!exist(paramPlayer))
/* 149 */       return;  VInventory vInventory = this.playerInventories.get(paramPlayer.getUniqueId());
/* 150 */     remove(paramPlayer);
/* 151 */     vInventory.onClose(paramInventoryCloseEvent, this.plugin, paramPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void onInventoryDrag(InventoryDragEvent paramInventoryDragEvent, Player paramPlayer) {
/* 156 */     if (paramInventoryDragEvent.getWhoClicked() instanceof Player) {
/* 157 */       if (!exist(paramPlayer))
/* 158 */         return;  ((VInventory)this.playerInventories.get(paramPlayer.getUniqueId())).onDrag(paramInventoryDragEvent, this.plugin, paramPlayer);
/*     */     } 
/*     */   }
/*     */   
/*     */   public boolean exist(Player paramPlayer) {
/* 163 */     return this.playerInventories.containsKey(paramPlayer.getUniqueId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void remove(Player paramPlayer) {
/* 172 */     this.playerInventories.remove(paramPlayer.getUniqueId());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Optional<VInventory> getInventory(int paramInt) {
/* 180 */     return Optional.ofNullable(this.inventories.getOrDefault(Integer.valueOf(paramInt), null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAllPlayer(int... paramVarArgs) {
/* 187 */     for (int i : paramVarArgs) {
/* 188 */       updateAllPlayer(i);
/*     */     }
/*     */   }
/*     */   
/*     */   public void closeAllPlayer(int... paramVarArgs) {
/* 193 */     for (int i : paramVarArgs) {
/* 194 */       closeAllPlayer(i);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateAllPlayer(int paramInt) {
/* 202 */     Iterator<VInventory> iterator = ((List)this.playerInventories.values().stream().filter(paramVInventory -> (paramVInventory.getId() == paramInt)).collect(Collectors.toList())).iterator();
/* 203 */     while (iterator.hasNext()) {
/* 204 */       VInventory vInventory = iterator.next();
/* 205 */       Bukkit.getScheduler().runTask((Plugin)this.plugin, () -> createInventory(paramVInventory, paramVInventory.getPlayer()));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void closeAllPlayer(int paramInt) {
/* 213 */     Iterator<VInventory> iterator = ((List)this.playerInventories.values().stream().filter(paramVInventory -> (paramVInventory.getId() == paramInt)).collect(Collectors.toList())).iterator();
/* 214 */     while (iterator.hasNext()) {
/* 215 */       VInventory vInventory = iterator.next();
/* 216 */       vInventory.getPlayer().closeInventory();
/*     */     } 
/*     */   }
/*     */   
/*     */   public void close() {
/* 221 */     Iterator<Map.Entry> iterator = (new HashMap<>(this.playerInventories)).entrySet().iterator();
/* 222 */     while (iterator.hasNext()) {
/*     */       try {
/* 224 */         Map.Entry entry = iterator.next();
/* 225 */         VInventory vInventory = (VInventory)entry.getValue();
/* 226 */         Player player = vInventory.getPlayer();
/* 227 */         player.closeInventory();
/* 228 */       } catch (Exception exception) {}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void update(Player paramPlayer) {
/* 234 */     if (this.playerInventories.containsKey(paramPlayer.getUniqueId())) {
/* 235 */       VInventory vInventory = this.playerInventories.get(paramPlayer.getUniqueId());
/* 236 */       createInventory(vInventory, paramPlayer);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\inventory\ZInventoryManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */