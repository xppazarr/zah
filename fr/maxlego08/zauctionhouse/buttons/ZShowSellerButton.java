/*    */ package fr.maxlego08.zauctionhouse.buttons;
/*    */ 
/*    */ import fr.maxlego08.menu.api.button.Button;
/*    */ import fr.maxlego08.menu.api.utils.Placeholders;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.PlayerSkin;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.NmsVersion;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.inventory.meta.SkullMeta;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ import org.bukkit.profile.PlayerProfile;
/*    */ 
/*    */ public class ZShowSellerButton
/*    */   extends Button {
/*    */   public ZShowSellerButton(Plugin paramPlugin) {
/* 22 */     this.plugin = (ZAuctionPlugin)paramPlugin;
/*    */   }
/*    */   
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 28 */     PlayerCache playerCache = this.plugin.getAuctionManager().getCache((OfflinePlayer)paramPlayer);
/* 29 */     AuctionItem auctionItem = playerCache.getAuctionItem();
/* 30 */     if (auctionItem == null) return super.getCustomItemStack(paramPlayer);
/*    */     
/* 32 */     String str = auctionItem.getSellerName();
/* 33 */     Placeholders placeholders = new Placeholders();
/* 34 */     placeholders.register("sellerName", str);
/*    */     
/* 36 */     ItemStack itemStack = getItemStack().build(paramPlayer, isUseCache(), placeholders);
/*    */ 
/*    */     
/* 39 */     if (NmsVersion.nmsVersion.isNewHeadApi()) {
/* 40 */       OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(str);
/* 41 */       if (offlinePlayer != null) {
/* 42 */         SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
/* 43 */         skullMeta.setOwnerProfile((PlayerProfile)offlinePlayer.getPlayerProfile());
/* 44 */         itemStack.setItemMeta((ItemMeta)skullMeta);
/*    */       } 
/*    */     } else {
/* 47 */       String str1 = PlayerSkin.getTexture(str);
/* 48 */       if (str1 == null) {
/* 49 */         SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
/* 50 */         skullMeta.setOwner(str);
/* 51 */         itemStack.setItemMeta((ItemMeta)skullMeta);
/*    */       } else {
/* 53 */         this.plugin.getzMenuHandler().applyTexture(itemStack, str1);
/*    */       } 
/*    */     } 
/*    */     
/* 57 */     return itemStack;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\buttons\ZShowSellerButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */