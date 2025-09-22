/*    */ package fr.maxlego08.zauctionhouse.api.event.events;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.event.CancelledAuctionEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AuctionGiveItemStackEvent
/*    */   extends CancelledAuctionEvent
/*    */ {
/*    */   private final Player player;
/*    */   private ItemStack itemStack;
/*    */   private boolean hasFullInventory;
/*    */   
/*    */   public AuctionGiveItemStackEvent(Player paramPlayer, ItemStack paramItemStack, boolean paramBoolean) {
/* 21 */     this.player = paramPlayer;
/* 22 */     this.itemStack = paramItemStack;
/* 23 */     this.hasFullInventory = paramBoolean;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public Player getPlayer() {
/* 30 */     return this.player;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 37 */     return this.itemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public boolean hasFullInventory() {
/* 44 */     return this.hasFullInventory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setItemStack(ItemStack paramItemStack) {
/* 52 */     this.itemStack = paramItemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setHasFullInventory(boolean paramBoolean) {
/* 60 */     this.hasFullInventory = paramBoolean;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\events\AuctionGiveItemStackEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */