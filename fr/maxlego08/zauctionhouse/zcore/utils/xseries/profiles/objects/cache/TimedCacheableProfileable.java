/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.cache;
/*    */ 
/*    */ import java.time.Duration;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public abstract class TimedCacheableProfileable
/*    */   extends CacheableProfileable
/*    */ {
/*    */   private long lastUpdate;
/*    */   
/*    */   @NotNull
/*    */   protected Duration expiresAfter() {
/* 46 */     return Duration.ofHours(6L);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean hasExpired(boolean paramBoolean) {
/* 54 */     if (super.hasExpired(paramBoolean)) return true; 
/* 55 */     if (this.cache == null && this.lastError == null) return true;
/*    */     
/* 57 */     Duration duration = expiresAfter();
/* 58 */     if (duration.isZero()) return false;
/*    */     
/* 60 */     long l1 = System.currentTimeMillis();
/* 61 */     if (this.lastUpdate == 0L) {
/* 62 */       if (paramBoolean) this.lastUpdate = l1; 
/* 63 */       return true;
/*    */     } 
/* 65 */     long l2 = l1 - this.lastUpdate;
/* 66 */     if (l2 >= duration.toMillis()) {
/* 67 */       if (paramBoolean) this.lastUpdate = l1; 
/* 68 */       return true;
/*    */     } 
/* 70 */     return false;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\cache\TimedCacheableProfileable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */