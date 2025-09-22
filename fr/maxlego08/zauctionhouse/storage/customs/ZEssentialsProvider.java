/*    */ package fr.maxlego08.zauctionhouse.storage.customs;
/*    */ 
/*    */ import fr.maxlego08.essentials.api.EssentialsPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.storage.CustomStorageProvider;
/*    */ 
/*    */ 
/*    */ public class ZEssentialsProvider
/*    */   implements CustomStorageProvider
/*    */ {
/*    */   public boolean store(AuctionPlugin paramAuctionPlugin, AuctionItem paramAuctionItem, StorageType paramStorageType) {
/* 14 */     if (paramStorageType == StorageType.EXPIRE) {
/* 15 */       EssentialsPlugin essentialsPlugin = (EssentialsPlugin)paramAuctionPlugin.getServer().getPluginManager().getPlugin("zEssentials");
/* 16 */       if (essentialsPlugin == null) return false; 
/* 17 */       essentialsPlugin.addMailBoxItem(paramAuctionItem.getSellerUniqueId(), paramAuctionItem.getItemStack().clone());
/* 18 */       return true;
/*    */     } 
/*    */     
/* 21 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\storage\customs\ZEssentialsProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */