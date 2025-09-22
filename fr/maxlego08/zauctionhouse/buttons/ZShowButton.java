/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.buttons.ShowButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.ItemBuilder;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ZShowButton
/*    */   extends ShowButton {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final InventoryType inventoryType;
/*    */   
/*    */   public ZShowButton(ZAuctionPlugin paramZAuctionPlugin, InventoryType paramInventoryType) {
/* 22 */     this.plugin = paramZAuctionPlugin;
/* 23 */     this.inventoryType = paramInventoryType;
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 28 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 29 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/*    */     
/* 31 */     if (auctionItem == null) {
/* 32 */       return (new ItemBuilder(Material.BARRIER, "§cYou cant use the button here !")).build();
/*    */     }
/*    */     
/* 35 */     AuctionType auctionType = auctionItem.getType();
/* 36 */     Message message = auctionType.getInventoryMessage(this.inventoryType);
/* 37 */     if (auctionItem.getType() == AuctionType.DEFAULT && auctionItem.getItemStack().getType().name().endsWith("SHULKER_BOX")) {
/* 38 */       message = Message.ITEM_LORE_INVENTORY;
/*    */     }
/* 40 */     return auctionItem.createItem(paramPlayer, message);
/*    */   }
/*    */ 
/*    */   
/*    */   public InventoryType getInventoryType() {
/* 45 */     return this.inventoryType;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZShowButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */