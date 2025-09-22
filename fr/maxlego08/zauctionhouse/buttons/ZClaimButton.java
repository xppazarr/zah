/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.engine.InventoryEngine;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.ClaimButton;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*    */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*    */ import java.util.HashMap;
/*    */ import java.util.Map;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.inventory.InventoryClickEvent;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ZClaimButton
/*    */   extends ClaimButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final boolean enableAutoRefresh;
/* 20 */   private final Map<Player, InventoryEngine> inventoryDefaultMap = new HashMap<>();
/*    */   
/*    */   public ZClaimButton(ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean) {
/* 23 */     this.plugin = paramZAuctionPlugin;
/* 24 */     this.enableAutoRefresh = paramBoolean;
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryOpen(Player paramPlayer, InventoryEngine paramInventoryEngine, Placeholders paramPlaceholders) {
/* 29 */     super.onInventoryOpen(paramPlayer, paramInventoryEngine, paramPlaceholders);
/* 30 */     this.inventoryDefaultMap.put(paramPlayer, paramInventoryEngine);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onInventoryClose(Player paramPlayer, InventoryEngine paramInventoryEngine) {
/* 35 */     super.onInventoryClose(paramPlayer, paramInventoryEngine);
/* 36 */     this.inventoryDefaultMap.remove(paramPlayer);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 42 */     if (this.enableAutoRefresh && getItemStack().getLore().stream().anyMatch(paramString -> paramString.contains("%zauctionhouse_claim_"))) {
/* 43 */       ZPlugin.serverImplementation.runLater(() -> { if (this.inventoryDefaultMap.containsKey(paramPlayer) && paramPlayer.isOnline()) { InventoryEngine inventoryEngine = this.inventoryDefaultMap.remove(paramPlayer); if (inventoryEngine == null) return;  for (Integer integer : getSlots()) inventoryEngine.getSpigotInventory().setItem(integer.intValue(), super.getCustomItemStack(paramPlayer));  }  }20L);
/*    */     }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 55 */     return super.getCustomItemStack(paramPlayer);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onClick(Player paramPlayer, InventoryClickEvent paramInventoryClickEvent, InventoryEngine paramInventoryEngine, int paramInt, Placeholders paramPlaceholders) {
/* 60 */     super.onClick(paramPlayer, paramInventoryClickEvent, paramInventoryEngine, paramInt, paramPlaceholders);
/* 61 */     TransactionManager transactionManager = this.plugin.getTransactionManager();
/* 62 */     transactionManager.claimMoney(paramPlayer);
/* 63 */     ZPlugin.serverImplementation.runNextTick(paramWrappedTask -> this.plugin.getzMenuHandler().update(paramPlayer));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZClaimButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */