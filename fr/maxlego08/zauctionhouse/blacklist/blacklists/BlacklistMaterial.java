/*    */ package fr.maxlego08.zauctionhouse.blacklist.blacklists;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.blacklist.ItemChecker;
/*    */ import org.bukkit.Material;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class BlacklistMaterial
/*    */   implements ItemChecker
/*    */ {
/*    */   private final Material material;
/*    */   
/*    */   public BlacklistMaterial(Material paramMaterial) {
/* 13 */     this.material = paramMaterial;
/*    */   }
/*    */ 
/*    */   
/*    */   public String getName() {
/* 18 */     return "zauctionhouse:material_similar";
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkItemStack(ItemStack paramItemStack) {
/* 23 */     return (paramItemStack.getType() == this.material);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\blacklist\blacklists\BlacklistMaterial.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */