/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers;
/*    */ 
/*    */ import fr.maxlego08.menu.api.InventoryManager;
/*    */ import fr.maxlego08.menu.api.MenuItemStack;
/*    */ import java.io.File;
/*    */ import java.math.BigDecimal;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.configuration.file.YamlConfiguration;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ZMenuItemProvider
/*    */   extends ItemProvider
/*    */ {
/*    */   private final MenuItemStack menuItemStack;
/*    */   
/*    */   public ZMenuItemProvider(Plugin paramPlugin, File paramFile, String paramString) {
/* 19 */     super(paramPlugin, null);
/* 20 */     InventoryManager inventoryManager = (InventoryManager)paramPlugin.getServer().getServicesManager().getRegistration(InventoryManager.class).getProvider();
/* 21 */     this.menuItemStack = inventoryManager.loadItemStack(YamlConfiguration.loadConfiguration(paramFile), paramString, paramFile);
/*    */   }
/*    */ 
/*    */   
/*    */   public BigDecimal getBalance(OfflinePlayer paramOfflinePlayer) {
/* 26 */     if (paramOfflinePlayer.isOnline()) {
/* 27 */       Player player = paramOfflinePlayer.getPlayer();
/* 28 */       return BigDecimal.valueOf(getAmount(player, this.menuItemStack.build(player)));
/* 29 */     }  return BigDecimal.ZERO;
/*    */   }
/*    */ 
/*    */   
/*    */   public void deposit(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 34 */     if (paramOfflinePlayer.isOnline()) {
/* 35 */       Player player = paramOfflinePlayer.getPlayer();
/* 36 */       giveItem(player, paramBigDecimal.intValue(), this.menuItemStack.build(player));
/*    */     } else {
/* 38 */       this.plugin.getLogger().severe("Deposit items to " + paramOfflinePlayer.getName() + " but is offline");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void withdraw(OfflinePlayer paramOfflinePlayer, BigDecimal paramBigDecimal, String paramString) {
/* 44 */     if (paramOfflinePlayer.isOnline()) {
/* 45 */       Player player = paramOfflinePlayer.getPlayer();
/* 46 */       removeItems(player, this.menuItemStack.build(player), paramBigDecimal.intValue());
/*    */     } else {
/* 48 */       this.plugin.getLogger().severe("Withdraw items from " + paramOfflinePlayer.getName() + " but is offline");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack(Player paramPlayer) {
/* 54 */     return this.menuItemStack.build(paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\currencies\providers\ZMenuItemProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */