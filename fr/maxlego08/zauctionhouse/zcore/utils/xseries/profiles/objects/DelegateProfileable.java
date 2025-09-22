/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.ProfileException;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.transformer.ProfileTransformer;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
/*    */ import org.jetbrains.annotations.NotNull;
/*    */ import org.jetbrains.annotations.Nullable;
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
/*    */ public interface DelegateProfileable
/*    */   extends Profileable
/*    */ {
/*    */   @Internal
/*    */   @NotNull
/*    */   Profileable getDelegateProfile();
/*    */   
/*    */   @Nullable
/*    */   default GameProfile getProfile() {
/* 53 */     return getDelegateProfile().getProfile();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default ProfileException test() {
/* 58 */     return getDelegateProfile().test();
/*    */   }
/*    */   
/*    */   @Nullable
/*    */   default GameProfile getDisposableProfile() {
/* 63 */     return getDelegateProfile().getDisposableProfile();
/*    */   }
/*    */ 
/*    */   
/*    */   @NotNull
/*    */   Profileable transform(@NotNull ProfileTransformer... transformers) {
/* 69 */     return getDelegateProfile().transform(transformers);
/*    */   }
/*    */ 
/*    */   
/*    */   @Nullable
/*    */   default String getProfileValue() {
/* 75 */     return getDelegateProfile().getProfileValue();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\DelegateProfileable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */