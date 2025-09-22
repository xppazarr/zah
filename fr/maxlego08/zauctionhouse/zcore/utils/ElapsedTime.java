/*    */ package fr.maxlego08.zauctionhouse.zcore.utils;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ElapsedTime
/*    */   extends ZUtils
/*    */ {
/*    */   private final String name;
/*    */   private long start;
/*    */   private long end;
/*    */   
/*    */   public ElapsedTime(String paramString) {
/* 16 */     this.name = paramString;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void start() {
/* 23 */     this.start = System.nanoTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void end() {
/* 30 */     this.end = System.nanoTime();
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getStart() {
/* 37 */     return this.start;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public long getEnd() {
/* 44 */     return this.end;
/*    */   }
/*    */   
/*    */   public long getElapsedTime() {
/* 48 */     return this.end - this.start;
/*    */   }
/*    */   
/*    */   public void endDisplay() {
/* 52 */     end();
/* 53 */     if (AuctionConfiguration.enableDebugModeTime)
/* 54 */       System.out.println("[ElapsedTime] " + this.name + " -> " + format(getElapsedTime(), ' ')); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\ElapsedTime.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */