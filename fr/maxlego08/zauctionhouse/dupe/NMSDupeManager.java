/*    */ package fr.maxlego08.zauctionhouse.dupe;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.dupe.DupeManager;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.nms.ItemStackCompound;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ public class NMSDupeManager
/*    */   implements DupeManager
/*    */ {
/*    */   public ItemStack protectItem(ItemStack paramItemStack) {
/* 12 */     return ItemStackCompound.itemStackCompound.setBoolean(paramItemStack, "ZAUCTIONHOUSE-ITEM", true);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isDupeItem(ItemStack paramItemStack) {
/* 17 */     return ItemStackCompound.itemStackCompound.isKey(paramItemStack, "ZAUCTIONHOUSE-ITEM");
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dupe\NMSDupeManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */