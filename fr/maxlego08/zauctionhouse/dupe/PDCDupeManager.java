/*    */ package fr.maxlego08.zauctionhouse.dupe;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.dupe.DupeManager;
/*    */ import org.bukkit.NamespacedKey;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ import org.bukkit.persistence.PersistentDataContainer;
/*    */ import org.bukkit.persistence.PersistentDataType;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class PDCDupeManager
/*    */   implements DupeManager
/*    */ {
/*    */   private final NamespacedKey namespacedKey;
/*    */   
/*    */   public PDCDupeManager(Plugin paramPlugin) {
/* 17 */     this.namespacedKey = new NamespacedKey(paramPlugin, "ZAUCTIONHOUSE-ITEM");
/*    */   }
/*    */ 
/*    */   
/*    */   public ItemStack protectItem(ItemStack paramItemStack) {
/* 22 */     if (paramItemStack == null) return null; 
/* 23 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 24 */     if (itemMeta == null) return paramItemStack; 
/* 25 */     PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
/* 26 */     persistentDataContainer.set(this.namespacedKey, PersistentDataType.INTEGER, Integer.valueOf(1));
/* 27 */     paramItemStack.setItemMeta(itemMeta);
/* 28 */     return paramItemStack;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDupeItem(ItemStack paramItemStack) {
/* 33 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 34 */     if (itemMeta == null) return false; 
/* 35 */     PersistentDataContainer persistentDataContainer = itemMeta.getPersistentDataContainer();
/* 36 */     return persistentDataContainer.has(this.namespacedKey, PersistentDataType.INTEGER);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dupe\PDCDupeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */