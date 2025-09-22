/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.cache;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.Profileable;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
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
/*    */ 
/*    */ 
/*    */ @Internal
/*    */ public abstract class CacheableProfileable
/*    */   implements Profileable
/*    */ {
/*    */   protected GameProfile cache;
/*    */   protected Throwable lastError;
/*    */   
/*    */   public final synchronized GameProfile getProfile() {
/* 50 */     if (hasExpired(true)) {
/* 51 */       this.lastError = null;
/* 52 */       this.cache = null;
/*    */     } 
/*    */     
/* 55 */     if (this.lastError != null && !(this.lastError instanceof fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.MojangAPIRetryException)) {
/* 56 */       throw XReflection.throwCheckedException(this.lastError);
/*    */     }
/*    */     
/* 59 */     if (this.cache == null) {
/*    */       try {
/* 61 */         this.cache = getProfile0();
/* 62 */         this.lastError = null;
/* 63 */       } catch (Throwable throwable) {
/* 64 */         this.lastError = throwable;
/* 65 */         throw throwable;
/*    */       } 
/*    */     }
/*    */     
/* 69 */     return this.cache;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public final boolean hasExpired() {
/* 76 */     return hasExpired(false);
/*    */   }
/*    */   
/*    */   protected boolean hasExpired(boolean paramBoolean) {
/* 80 */     return this.lastError instanceof fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.MojangAPIRetryException;
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   protected abstract GameProfile getProfile0();
/*    */   
/*    */   public final String toString() {
/* 88 */     return getClass().getSimpleName() + "[cache=" + this.cache + ", lastError=" + this.lastError + ']';
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\cache\CacheableProfileable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */