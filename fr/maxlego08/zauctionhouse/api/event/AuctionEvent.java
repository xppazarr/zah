/*    */ package fr.maxlego08.zauctionhouse.api.event;
/*    */ 
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.event.Event;
/*    */ import org.bukkit.event.HandlerList;
/*    */ 
/*    */ public class AuctionEvent
/*    */   extends Event {
/*  9 */   private static final HandlerList handlers = new HandlerList();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionEvent() {}
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public AuctionEvent(boolean paramBoolean) {
/* 23 */     super(paramBoolean);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public HandlerList getHandlers() {
/* 31 */     return handlers;
/*    */   }
/*    */   
/*    */   public static HandlerList getHandlerList() {
/* 35 */     return handlers;
/*    */   }
/*    */   
/*    */   public void call() {
/* 39 */     Bukkit.getPluginManager().callEvent(this);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\event\AuctionEvent.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */