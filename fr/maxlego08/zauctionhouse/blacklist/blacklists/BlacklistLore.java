/*    */ package fr.maxlego08.zauctionhouse.blacklist.blacklists;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.blacklist.ItemChecker;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ 
/*    */ public class BlacklistLore
/*    */   implements ItemChecker
/*    */ {
/*    */   private final String lore;
/*    */   
/*    */   public BlacklistLore(String paramString) {
/* 14 */     this.lore = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 19 */     return "zauctionhouse:contains_lore";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkItemStack(ItemStack paramItemStack) {
/* 24 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 25 */     return (itemMeta.hasLore() && itemMeta.getLore().stream().anyMatch(paramString -> (paramString != null && paramString.contains(this.lore))));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\blacklist\blacklists\BlacklistLore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */