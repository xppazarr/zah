/*    */ package fr.maxlego08.zauctionhouse.api.dupe;
/*    */ 
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class DupeItem
/*    */ {
/*    */   private final ItemStack itemStack;
/*    */   private final Player player;
/*    */   
/*    */   public DupeItem(ItemStack paramItemStack, Player paramPlayer) {
/* 12 */     this.itemStack = paramItemStack;
/* 13 */     this.player = paramPlayer;
/*    */   }
/*    */   
/*    */   public ItemStack getItemStack() {
/* 17 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public Player getPlayer() {
/* 21 */     return this.player;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\dupe\DupeItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */