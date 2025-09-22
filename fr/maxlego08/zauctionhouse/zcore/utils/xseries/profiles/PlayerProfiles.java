/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.google.gson.Gson;
/*     */ import com.google.gson.GsonBuilder;
/*     */ import com.google.gson.JsonElement;
/*     */ import com.google.gson.JsonObject;
/*     */ import com.google.gson.JsonParser;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.transformer.ProfileTransformer;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.Base64;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ public final class PlayerProfiles
/*     */ {
/*     */   public static final String XSERIES_SIG = "XSeries";
/*  61 */   private static final Property XSERIES_GAMEPROFILE_SIGNATURE = new Property("XSeries", "13.0.0");
/*     */   
/*     */   private static final String TEXTURES_PROPERTY = "textures";
/*  64 */   public static final GameProfile NIL = createGameProfile(PlayerUUIDs.IDENTITY_UUID, "XSeries");
/*  65 */   private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TEXTURES_NBT_PROPERTY_PREFIX = "{\"textures\":{\"SKIN\":{\"url\":\"";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static final String TEXTURES_BASE_URL = "http://textures.minecraft.net/texture/";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Optional<Property> getTextureProperty(GameProfile paramGameProfile) {
/*  91 */     return Optional.ofNullable((Property)Iterables.getFirst(paramGameProfile.getProperties().get("textures"), null));
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
/*     */   @Nullable
/*     */   public static String getTextureValue(@NotNull GameProfile paramGameProfile) {
/* 104 */     Objects.requireNonNull(paramGameProfile, "Game profile cannot be null");
/* 105 */     return getTextureProperty(paramGameProfile).<String>map(PlayerProfiles::getPropertyValue).orElse(null);
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static String getOriginalValue(@Nullable GameProfile paramGameProfile) {
/* 110 */     if (paramGameProfile == null) return null;
/*     */     
/* 112 */     String str = ProfileTransformer.IncludeOriginalValue.getOriginalValue(paramGameProfile);
/* 113 */     if (str != null) return str;
/*     */     
/* 115 */     return getTextureValue(paramGameProfile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static String getPropertyValue(@NotNull Property paramProperty) {
/* 125 */     if (ProfilesCore.NULLABILITY_RECORD_UPDATE) return paramProperty.value(); 
/*     */     try {
/* 127 */       return ProfilesCore.Property_getValue.invoke(paramProperty);
/* 128 */     } catch (Throwable throwable) {
/* 129 */       throw new IllegalArgumentException("Unable to get a property value: " + paramProperty, throwable);
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
/*     */   public static boolean hasTextures(GameProfile paramGameProfile) {
/* 141 */     return getTextureProperty(paramGameProfile).isPresent();
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
/*     */   public static GameProfile profileFromHashAndBase64(String paramString1, String paramString2) {
/* 155 */     UUID uUID = UUID.nameUUIDFromBytes(paramString1.getBytes(StandardCharsets.UTF_8));
/* 156 */     GameProfile gameProfile = createNamelessGameProfile(uUID);
/* 157 */     setTexturesProperty(gameProfile, paramString2);
/* 158 */     return gameProfile;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void removeTimestamp(GameProfile paramGameProfile) {
/* 165 */     JsonObject jsonObject = Optional.<String>ofNullable(getTextureValue(paramGameProfile)).map(PlayerProfiles::decodeBase64).map(paramString -> (new JsonParser()).parse(paramString).getAsJsonObject()).orElse(null);
/*     */     
/* 167 */     if (jsonObject == null || !jsonObject.has("timestamp"))
/* 168 */       return;  jsonObject.remove("timestamp");
/*     */ 
/*     */     
/* 171 */     setTexturesProperty(paramGameProfile, encodeBase64(GSON.toJson((JsonElement)jsonObject)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static GameProfile unwrapProfile(@Nullable Object paramObject) {
/* 179 */     if (paramObject == null) return null; 
/* 180 */     if (!(paramObject instanceof GameProfile) && ProfilesCore.ResolvableProfile_gameProfile != null)
/*     */     {
/* 182 */       paramObject = ProfilesCore.ResolvableProfile_gameProfile.invoke(paramObject);
/*     */     }
/* 184 */     return (GameProfile)paramObject;
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static Object wrapProfile(@Nullable GameProfile paramGameProfile) {
/* 189 */     if (paramGameProfile == null) return null; 
/* 190 */     if (ProfilesCore.ResolvableProfile$bukkitSupports) {
/* 191 */       return ProfilesCore.ResolvableProfile$constructor.invoke(paramGameProfile);
/*     */     }
/* 193 */     return paramGameProfile;
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
/*     */   public static GameProfile sanitizeProfile(GameProfile paramGameProfile) {
/* 211 */     if (PlayerUUIDs.isOnlineMode()) return paramGameProfile; 
/* 212 */     UUID uUID = PlayerUUIDs.getOfflineUUID(paramGameProfile.getName());
/* 213 */     PlayerUUIDs.ONLINE_TO_OFFLINE.put(paramGameProfile.getId(), uUID);
/*     */     
/* 215 */     GameProfile gameProfile = createGameProfile(uUID, paramGameProfile.getName());
/* 216 */     gameProfile.getProperties().putAll((Multimap)paramGameProfile.getProperties());
/* 217 */     return gameProfile;
/*     */   }
/*     */   
/*     */   public static GameProfile clone(GameProfile paramGameProfile) {
/* 221 */     GameProfile gameProfile = new GameProfile(paramGameProfile.getId(), paramGameProfile.getName());
/* 222 */     gameProfile.getProperties().putAll((Multimap)paramGameProfile.getProperties());
/* 223 */     return gameProfile;
/*     */   }
/*     */   
/*     */   public static void setTexturesProperty(GameProfile paramGameProfile, String paramString) {
/* 227 */     Property property = new Property("textures", paramString);
/* 228 */     PropertyMap propertyMap = paramGameProfile.getProperties();
/* 229 */     propertyMap.asMap().remove("textures");
/* 230 */     propertyMap.put("textures", property);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static String encodeBase64(String paramString) {
/* 240 */     return Base64.getEncoder().encodeToString(paramString.getBytes(StandardCharsets.UTF_8));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static String decodeBase64(String paramString) {
/* 251 */     Objects.requireNonNull(paramString, "Cannot decode null string");
/*     */     try {
/* 253 */       byte[] arrayOfByte = Base64.getDecoder().decode(paramString);
/* 254 */       return new String(arrayOfByte, StandardCharsets.UTF_8);
/* 255 */     } catch (IllegalArgumentException illegalArgumentException) {
/* 256 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public static GameProfile createGameProfile(UUID paramUUID, String paramString) {
/* 261 */     return signXSeries(new GameProfile(paramUUID, paramString));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static GameProfile signXSeries(GameProfile paramGameProfile) {
/* 271 */     PropertyMap propertyMap = paramGameProfile.getProperties();
/*     */ 
/*     */ 
/*     */     
/* 275 */     propertyMap.put("XSeries", XSERIES_GAMEPROFILE_SIGNATURE);
/* 276 */     return paramGameProfile;
/*     */   }
/*     */   
/*     */   public static GameProfile createNamelessGameProfile(UUID paramUUID) {
/* 280 */     return createGameProfile(paramUUID, "XSeries");
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\PlayerProfiles.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */