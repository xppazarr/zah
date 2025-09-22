/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang;
/*     */ 
/*     */ import com.google.common.cache.Cache;
/*     */ import com.google.common.cache.CacheBuilder;
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.gson.JsonArray;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerUUIDs;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.ProfileLogger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.ProfilesCore;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.MojangAPIException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.UnknownPlayerException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.ProfileInputType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import java.io.IOException;
/*     */ import java.time.Duration;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Locale;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.ApiStatus.Obsolete;
/*     */ import org.jetbrains.annotations.NotNull;
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
/*     */ @Internal
/*     */ public final class MojangAPI
/*     */ {
/*  58 */   private static final MojangProfileCache MOJANG_PROFILE_CACHE = !ProfilesCore.NULLABILITY_RECORD_UPDATE ? 
/*  59 */     new MojangProfileCache.GameProfileCache(ProfilesCore.YggdrasilMinecraftSessionService_insecureProfiles) : 
/*  60 */     new MojangProfileCache.ProfileResultCache(ProfilesCore.YggdrasilMinecraftSessionService_insecureProfiles);
/*     */ 
/*     */ 
/*     */   
/*  64 */   private static final Cache<UUID, Optional<GameProfile>> INSECURE_PROFILES = CacheBuilder.newBuilder()
/*  65 */     .expireAfterWrite(6L, TimeUnit.HOURS).build();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final boolean REQUIRE_SECURE_PROFILES = false;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  75 */   private static final MinecraftClient USERNAME_TO_UUID = new MinecraftClient("GET", "https://api.mojang.com/users/profiles/minecraft/", new RateLimiter(600, 
/*     */ 
/*     */         
/*  78 */         Duration.ofMinutes(10L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  86 */   private static final MinecraftClient USERNAMES_TO_UUIDS = new MinecraftClient("POST", "https://api.minecraftservices.com/minecraft/profile/lookup/bulk/byname", new RateLimiter(600, 
/*     */ 
/*     */         
/*  89 */         Duration.ofMinutes(10L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  95 */   private static final MinecraftClient UUID_TO_PROFILE = new MinecraftClient("GET", "https://sessionserver.mojang.com/session/minecraft/profile/", new RateLimiter(200, 
/*     */ 
/*     */         
/*  98 */         Duration.ofMinutes(1L)));
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static UUID requestUsernameToUUID(@NotNull String paramString) {
/* 106 */     JsonElement jsonElement1 = USERNAME_TO_UUID.session(null).append(paramString).request();
/* 107 */     if (jsonElement1 == null) return null;
/*     */     
/* 109 */     JsonObject jsonObject = jsonElement1.getAsJsonObject();
/* 110 */     JsonElement jsonElement2 = jsonObject.get("id");
/* 111 */     if (jsonElement2 == null) {
/* 112 */       throw new IllegalArgumentException("No 'id' field for UUID request for '" + paramString + "': " + jsonObject);
/*     */     }
/* 114 */     return PlayerUUIDs.UUIDFromDashlessString(jsonElement2.getAsString());
/*     */   }
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
/*     */   @Obsolete
/*     */   private static GameProfile getCachedProfileByUsername(String paramString) {
/*     */     try {
/* 130 */       Object object = ProfilesCore.GameProfileCache_get$profileByName$.invoke(ProfilesCore.USER_CACHE, paramString);
/* 131 */       if (object instanceof Optional) object = ((Optional)object).orElse(null);
/*     */ 
/*     */       
/* 134 */       GameProfile gameProfile = (object == null) ? PlayerProfiles.createGameProfile(PlayerUUIDs.IDENTITY_UUID, paramString) : PlayerProfiles.sanitizeProfile((GameProfile)object);
/* 135 */       ProfileLogger.debug("The cached profile for {} -> {}", new Object[] { paramString, object });
/* 136 */       return gameProfile;
/* 137 */     } catch (Throwable throwable) {
/* 138 */       ProfileLogger.LOGGER.error("Unable to get cached profile by username: {}", paramString, throwable);
/* 139 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static Optional<GameProfile> getMojangCachedProfileFromUsername(String paramString) {
/*     */     try {
/* 145 */       return getMojangCachedProfileFromUsername0(paramString);
/* 146 */     } catch (Throwable throwable) {
/* 147 */       throw XReflection.throwCheckedException(throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   private static Optional<GameProfile> getMojangCachedProfileFromUsername0(String paramString) {
/*     */     Optional<GameProfile> optional;
/* 153 */     String str = paramString.toLowerCase(Locale.ENGLISH);
/* 154 */     Object object = ProfilesCore.UserCache_profilesByName.get(str);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 161 */     if (object != null) {
/*     */ 
/*     */ 
/*     */       
/* 165 */       if (ProfilesCore.UserCacheEntry_setLastAccess != null && ProfilesCore.UserCache_getNextOperation != null) {
/*     */         
/* 167 */         long l = ProfilesCore.UserCache_getNextOperation.invoke(ProfilesCore.USER_CACHE);
/* 168 */         ProfilesCore.UserCacheEntry_setLastAccess.invoke(object, l);
/*     */       } 
/* 170 */       optional = Optional.of(ProfilesCore.UserCacheEntry_getProfile.invoke(object));
/*     */     } else {
/*     */       
/* 173 */       UUID uUID = PlayerUUIDs.getRealUUIDOfPlayer(paramString);
/* 174 */       if (uUID == null) return Optional.empty(); 
/* 175 */       GameProfile gameProfile = PlayerProfiles.createGameProfile(
/* 176 */           PlayerUUIDs.isOnlineMode() ? uUID : PlayerUUIDs.getOfflineUUID(paramString), paramString);
/*     */ 
/*     */       
/* 179 */       optional = Optional.of(gameProfile);
/*     */       
/* 181 */       cacheProfile(gameProfile);
/*     */     } 
/*     */     
/* 184 */     return optional;
/*     */   }
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
/*     */   public static Map<UUID, String> usernamesToUUIDs(@NotNull Collection<String> paramCollection, @Nullable ProfileRequestConfiguration paramProfileRequestConfiguration) {
/* 205 */     if (paramCollection == null || paramCollection.isEmpty()) throw new IllegalArgumentException("Usernames are null or empty"); 
/* 206 */     for (String str : paramCollection) {
/* 207 */       if (str == null || !ProfileInputType.USERNAME.pattern.matcher(str).matches()) {
/* 208 */         throw new IllegalArgumentException("One of the requested usernames is invalid: " + str + " in " + paramCollection);
/*     */       }
/*     */     } 
/*     */     
/* 212 */     HashMap<Object, Object> hashMap = new HashMap<>(paramCollection.size());
/* 213 */     HashSet<String> hashSet = new HashSet<>(paramCollection);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 218 */     Iterator<String> iterator = hashSet.iterator();
/* 219 */     while (iterator.hasNext()) {
/* 220 */       String str = iterator.next();
/* 221 */       UUID uUID = (UUID)PlayerUUIDs.USERNAME_TO_ONLINE.get(str);
/* 222 */       if (uUID != null) {
/* 223 */         iterator.remove();
/* 224 */         hashMap.put(uUID, str);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 229 */     if (hashSet.isEmpty()) return (Map)hashMap; 
/* 230 */     boolean bool = PlayerUUIDs.isOnlineMode();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 235 */     Iterable iterable = Iterables.partition(hashSet, 10);
/* 236 */     for (List list : iterable) {
/*     */       JsonArray jsonArray;
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 243 */         jsonArray = USERNAMES_TO_UUIDS.session(paramProfileRequestConfiguration).body(list).request().getAsJsonArray();
/* 244 */       } catch (IOException iOException) {
/* 245 */         throw new MojangAPIException("Failed to request UUIDs for username batch: " + list, iOException);
/*     */       } 
/*     */       
/* 248 */       for (JsonElement jsonElement : jsonArray) {
/* 249 */         JsonObject jsonObject = jsonElement.getAsJsonObject();
/* 250 */         String str1 = jsonObject.get("name").getAsString();
/* 251 */         UUID uUID1 = PlayerUUIDs.UUIDFromDashlessString(jsonObject.get("id").getAsString());
/* 252 */         UUID uUID2 = PlayerUUIDs.getOfflineUUID(str1);
/*     */         
/* 254 */         PlayerUUIDs.USERNAME_TO_ONLINE.put(str1, uUID1);
/* 255 */         PlayerUUIDs.ONLINE_TO_OFFLINE.put(uUID1, uUID2);
/* 256 */         PlayerUUIDs.OFFLINE_TO_ONLINE.put(uUID2, uUID1);
/* 257 */         if (!ProfilesCore.UserCache_profilesByName.containsKey(str1)) {
/* 258 */           cacheProfile(PlayerProfiles.createGameProfile(bool ? uUID1 : uUID2, str1));
/*     */         }
/*     */         
/* 261 */         String str2 = (String)hashMap.put(uUID1, str1);
/* 262 */         if (str2 != null) {
/* 263 */           throw new IllegalArgumentException("Got duplicate usernames for UUID: " + uUID1 + " (" + str2 + " -> " + str1 + ')');
/*     */         }
/*     */       } 
/*     */     } 
/* 267 */     return (Map)hashMap;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static GameProfile getCachedProfileByUUID(UUID paramUUID) {
/* 279 */     paramUUID = PlayerUUIDs.isOnlineMode() ? paramUUID : (UUID)PlayerUUIDs.ONLINE_TO_OFFLINE.getOrDefault(paramUUID, paramUUID);
/*     */     try {
/* 281 */       Object object = ProfilesCore.GameProfileCache_get$profileByUUID$.invoke(ProfilesCore.USER_CACHE, paramUUID);
/* 282 */       if (object instanceof Optional) object = ((Optional)object).orElse(null); 
/* 283 */       ProfileLogger.debug("The cached profile for {} -> {}", new Object[] { paramUUID, object });
/* 284 */       return (object == null) ? 
/* 285 */         PlayerProfiles.createNamelessGameProfile(paramUUID) : 
/* 286 */         PlayerProfiles.sanitizeProfile((GameProfile)object);
/* 287 */     } catch (Throwable throwable) {
/* 288 */       ProfileLogger.LOGGER.error("Unable to get cached profile by UUID: {}", paramUUID, throwable);
/* 289 */       return PlayerProfiles.createNamelessGameProfile(paramUUID);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static void cacheProfile(GameProfile paramGameProfile) {
/*     */     try {
/* 301 */       ProfilesCore.CACHE_PROFILE.invoke(ProfilesCore.USER_CACHE, paramGameProfile);
/* 302 */       ProfileLogger.debug("Profile is now cached: {}", new Object[] { paramGameProfile });
/* 303 */     } catch (Throwable throwable) {
/* 304 */       ProfileLogger.LOGGER.error("Unable to cache profile {}", paramGameProfile);
/* 305 */       throwable.printStackTrace();
/*     */     } 
/*     */   }
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
/*     */   @NotNull
/*     */   public static GameProfile getOrFetchProfile(@NotNull GameProfile paramGameProfile) {
/*     */     UUID uUID;
/* 321 */     if (paramGameProfile.getName().equals("XSeries")) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 328 */       uUID = paramGameProfile.getId();
/*     */     } else {
/* 330 */       uUID = PlayerUUIDs.getRealUUIDOfPlayer(paramGameProfile.getName(), paramGameProfile.getId());
/* 331 */       if (uUID == null) {
/* 332 */         throw new UnknownPlayerException(paramGameProfile.getName(), "Player with the given properties not found: " + paramGameProfile);
/*     */       }
/*     */     } 
/*     */     
/* 336 */     GameProfile gameProfile1 = handleCache(paramGameProfile, uUID);
/* 337 */     if (gameProfile1 != null) return gameProfile1;
/*     */     
/* 339 */     JsonElement jsonElement = requestProfile(paramGameProfile, uUID);
/* 340 */     JsonObject jsonObject = jsonElement.getAsJsonObject();
/* 341 */     ArrayList<String> arrayList = new ArrayList();
/* 342 */     GameProfile gameProfile2 = createGameProfile(jsonObject, arrayList);
/*     */     
/* 344 */     gameProfile2 = PlayerProfiles.sanitizeProfile(gameProfile2);
/* 345 */     cacheProfile(gameProfile2);
/*     */     
/* 347 */     INSECURE_PROFILES.put(uUID, Optional.of(gameProfile2));
/* 348 */     MOJANG_PROFILE_CACHE.cache(new PlayerProfile(uUID, paramGameProfile, gameProfile2, arrayList));
/*     */     
/* 350 */     return gameProfile2;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   private static GameProfile handleCache(@NotNull GameProfile paramGameProfile, UUID paramUUID) {
/* 355 */     Optional<GameProfile> optional1 = (Optional)INSECURE_PROFILES.getIfPresent(paramUUID);
/*     */     
/* 357 */     if (optional1 != null) {
/* 358 */       ProfileLogger.debug("Found cached profile from UUID ({}): {} -> {}", new Object[] { paramUUID, paramGameProfile, optional1 });
/* 359 */       if (optional1.isPresent()) return optional1.get(); 
/* 360 */       throw new UnknownPlayerException(paramUUID, "Player with the given properties not found: " + paramGameProfile);
/*     */     } 
/*     */     
/* 363 */     Optional<GameProfile> optional2 = MOJANG_PROFILE_CACHE.get(paramUUID, paramGameProfile);
/* 364 */     if (optional2 != null) {
/* 365 */       INSECURE_PROFILES.put(paramUUID, optional2);
/* 366 */       if (optional2.isPresent()) return optional2.get(); 
/* 367 */       throw new UnknownPlayerException(paramUUID, "Player with the given properties not found: " + paramGameProfile);
/*     */     } 
/* 369 */     return null;
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   private static JsonElement requestProfile(@NotNull GameProfile paramGameProfile, UUID paramUUID) {
/*     */     JsonElement jsonElement;
/*     */     try {
/* 377 */       jsonElement = UUID_TO_PROFILE.session(null).append(PlayerUUIDs.toUndashedUUID(paramUUID) + "?unsigned=" + '\001').request();
/* 378 */     } catch (IOException iOException) {
/* 379 */       throw new IllegalStateException("Failed to request profile: " + paramGameProfile + " with real UUID: " + paramUUID, iOException);
/*     */     } 
/* 381 */     if (jsonElement == null) {
/* 382 */       INSECURE_PROFILES.put(paramUUID, Optional.empty());
/* 383 */       MOJANG_PROFILE_CACHE.cache(new PlayerProfile(paramUUID, paramGameProfile, null, null));
/* 384 */       throw new UnknownPlayerException(paramUUID, "Player with the given properties not found: " + paramGameProfile);
/*     */     } 
/* 386 */     return jsonElement;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private static GameProfile createGameProfile(JsonObject paramJsonObject, List<String> paramList) {
/* 391 */     UUID uUID = PlayerUUIDs.UUIDFromDashlessString(paramJsonObject.get("id").getAsString());
/* 392 */     String str = paramJsonObject.get("name").getAsString();
/* 393 */     GameProfile gameProfile = PlayerProfiles.createGameProfile(uUID, str);
/*     */ 
/*     */     
/* 396 */     JsonElement jsonElement1 = paramJsonObject.get("properties");
/* 397 */     if (jsonElement1 != null) {
/* 398 */       JsonArray jsonArray = jsonElement1.getAsJsonArray();
/* 399 */       PropertyMap propertyMap = gameProfile.getProperties();
/* 400 */       for (JsonElement jsonElement3 : jsonArray) {
/* 401 */         Property property; JsonObject jsonObject = jsonElement3.getAsJsonObject();
/* 402 */         String str1 = jsonObject.get("name").getAsString();
/* 403 */         String str2 = jsonObject.get("value").getAsString();
/* 404 */         JsonElement jsonElement4 = jsonObject.get("signature");
/*     */ 
/*     */         
/* 407 */         if (jsonElement4 != null) {
/* 408 */           property = new Property(str1, str2, jsonElement4.getAsString());
/*     */         } else {
/* 410 */           property = new Property(str1, str2);
/*     */         } 
/*     */         
/* 413 */         propertyMap.put(str1, property);
/*     */       } 
/*     */     } 
/*     */ 
/*     */     
/* 418 */     JsonElement jsonElement2 = paramJsonObject.get("profileActions");
/* 419 */     if (jsonElement2 != null) {
/* 420 */       for (JsonElement jsonElement : jsonElement2.getAsJsonArray()) {
/* 421 */         paramList.add(jsonElement.getAsString());
/*     */       }
/*     */     }
/*     */     
/* 425 */     return gameProfile;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\mojang\MojangAPI.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */