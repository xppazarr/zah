/*    */ package fr.maxlego08.zauctionhouse.blacklist.blacklists;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.blacklist.ItemChecker;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.inventory.meta.ItemMeta;
/*    */ 
/*    */ public class BlacklistName
/*    */   implements ItemChecker
/*    */ {
/*    */   private final String name;
/*    */   
/*    */   public BlacklistName(String paramString) {
/* 13 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return "zauctionhouse:names_contains";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkItemStack(ItemStack paramItemStack) {
/* 23 */     ItemMeta itemMeta = paramItemStack.getItemMeta();
/* 24 */     return (itemMeta.hasDisplayName() && itemMeta.getDisplayName().contains(this.name));
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\blacklist\blacklists\BlacklistName.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */