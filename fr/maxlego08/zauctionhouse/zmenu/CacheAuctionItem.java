/*    */ package fr.maxlego08.zauctionhouse.zmenu;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CacheAuctionItem
/*    */ {
/*    */   private final long expiredAt;
/*    */   private final ItemStack itemStack;
/*    */   
/*    */   public CacheAuctionItem(ItemStack paramItemStack) {
/* 15 */     this.expiredAt = System.currentTimeMillis() + AuctionConfiguration.cacheItemStackMilliSeconds;
/* 16 */     this.itemStack = paramItemStack;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getExpiredAt() {
/* 23 */     return this.expiredAt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public ItemStack getItemStack() {
/* 30 */     return this.itemStack;
/*    */   }
/*    */   
/*    */   public boolean isValid() {
/* 34 */     return (this.expiredAt > System.currentTimeMillis());
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zmenu\CacheAuctionItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */