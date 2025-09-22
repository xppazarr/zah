/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.yggdrasil.ProfileActionType;
/*     */ import com.mojang.authlib.yggdrasil.ProfileResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Internal
/*     */ abstract class MojangProfileCache
/*     */ {
/*     */   abstract void cache(PlayerProfile paramPlayerProfile);
/*     */   
/*     */   @Nullable
/*     */   abstract Optional<GameProfile> get(UUID paramUUID, GameProfile paramGameProfile);
/*     */   
/*     */   protected static final class ProfileResultCache
/*     */     extends MojangProfileCache
/*     */   {
/*     */     private final LoadingCache<UUID, Optional<ProfileResult>> insecureProfiles;
/*     */     
/*     */     ProfileResultCache(LoadingCache<?, ?> param1LoadingCache) {
/*  58 */       this.insecureProfiles = (LoadingCache)param1LoadingCache;
/*     */     }
/*     */ 
/*     */     
/*     */     void cache(PlayerProfile param1PlayerProfile) {
/*  63 */       if (param1PlayerProfile.exists()) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*  71 */         ProfileResult profileResult = new ProfileResult(param1PlayerProfile.fetchedGameProfile, (Set)param1PlayerProfile.profileActions.stream().map(param1String -> { try { return ProfileActionType.valueOf(param1String); } catch (IllegalArgumentException illegalArgumentException) { return null; }  }).filter(Objects::nonNull).collect(Collectors.toSet()));
/*  72 */         this.insecureProfiles.put(param1PlayerProfile.realUUID, Optional.of(profileResult));
/*     */       } else {
/*  74 */         this.insecureProfiles.put(param1PlayerProfile.realUUID, Optional.empty());
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     Optional<GameProfile> get(UUID param1UUID, GameProfile param1GameProfile) {
/*  81 */       Optional optional = (Optional)this.insecureProfiles.getIfPresent(param1UUID);
/*  82 */       return (optional == null) ? null : optional.map(ProfileResult::profile);
/*     */     }
/*     */   }
/*     */   
/*     */   protected static final class GameProfileCache
/*     */     extends MojangProfileCache
/*     */   {
/*     */     private final LoadingCache<GameProfile, GameProfile> insecureProfiles;
/*     */     
/*     */     GameProfileCache(LoadingCache<?, ?> param1LoadingCache) {
/*  92 */       this.insecureProfiles = (LoadingCache)param1LoadingCache;
/*     */     }
/*     */ 
/*     */     
/*     */     void cache(PlayerProfile param1PlayerProfile) {
/*  97 */       if (param1PlayerProfile.exists()) {
/*  98 */         this.insecureProfiles.put(param1PlayerProfile.requestedGameProfile, param1PlayerProfile.fetchedGameProfile);
/*     */       } else {
/* 100 */         this.insecureProfiles.put(param1PlayerProfile.requestedGameProfile, PlayerProfiles.NIL);
/*     */       } 
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     @Nullable
/*     */     Optional<GameProfile> get(UUID param1UUID, GameProfile param1GameProfile) {
/* 109 */       String str = param1GameProfile.getName();
/* 110 */       if (Strings.isNullOrEmpty(str) || str.equals("XSeries")) {
/* 111 */         return null;
/*     */       }
/* 113 */       GameProfile gameProfile = (GameProfile)this.insecureProfiles.getIfPresent(new GameProfile(param1UUID, param1GameProfile.getName()));
/* 114 */       if (gameProfile == PlayerProfiles.NIL) return Optional.empty(); 
/* 115 */       return (gameProfile == null) ? null : Optional.<GameProfile>of(gameProfile);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\mojang\MojangProfileCache.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */