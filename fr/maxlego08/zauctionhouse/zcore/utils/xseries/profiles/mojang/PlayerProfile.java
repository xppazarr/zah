/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang;
/*    */ 
/*    */ import com.mojang.authlib.GameProfile;
/*    */ import java.util.List;
/*    */ import java.util.UUID;
/*    */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*    */ final class PlayerProfile
/*    */ {
/*    */   public final UUID realUUID;
/*    */   public final GameProfile requestedGameProfile;
/*    */   public final GameProfile fetchedGameProfile;
/*    */   public final List<String> profileActions;
/*    */   
/*    */   PlayerProfile(UUID paramUUID, GameProfile paramGameProfile1, GameProfile paramGameProfile2, List<String> paramList) {
/* 39 */     this.realUUID = paramUUID;
/* 40 */     this.requestedGameProfile = paramGameProfile1;
/* 41 */     this.fetchedGameProfile = paramGameProfile2;
/* 42 */     this.profileActions = paramList;
/*    */   }
/*    */   
/*    */   boolean exists() {
/* 46 */     return (this.fetchedGameProfile != null);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\mojang\PlayerProfile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */