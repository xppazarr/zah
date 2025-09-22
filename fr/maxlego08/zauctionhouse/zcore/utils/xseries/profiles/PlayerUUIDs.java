/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles;
/*     */ 
/*     */ import com.google.common.base.Strings;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.MojangAPI;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.StandardCharsets;
/*     */ import java.util.HashMap;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.regex.Matcher;
/*     */ import java.util.regex.Pattern;
/*     */ import org.bukkit.Bukkit;
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
/*     */ @Internal
/*     */ public final class PlayerUUIDs
/*     */ {
/*  47 */   public static final UUID IDENTITY_UUID = new UUID(0L, 0L);
/*     */   
/*  49 */   private static final Pattern UUID_NO_DASHES = Pattern.compile("([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{12})");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  56 */   public static final Map<UUID, UUID> OFFLINE_TO_ONLINE = new HashMap<>(); public static final Map<UUID, UUID> ONLINE_TO_OFFLINE = new HashMap<>();
/*  57 */   public static final Map<String, UUID> USERNAME_TO_ONLINE = new HashMap<>();
/*     */   
/*     */   public static UUID UUIDFromDashlessString(String paramString) {
/*  60 */     Matcher matcher = UUID_NO_DASHES.matcher(paramString);
/*     */     try {
/*  62 */       return UUID.fromString(matcher.replaceFirst("$1-$2-$3-$4-$5"));
/*  63 */     } catch (IllegalArgumentException illegalArgumentException) {
/*  64 */       throw new IllegalArgumentException("Cannot convert from dashless UUID: " + paramString, illegalArgumentException);
/*     */     } 
/*     */   }
/*     */   
/*     */   public static String toUndashedUUID(UUID paramUUID) {
/*  69 */     return paramUUID.toString().replace("-", "");
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public static UUID getOfflineUUID(@NotNull String paramString) {
/*  75 */     return UUID.nameUUIDFromBytes(("OfflinePlayer:" + paramString).getBytes(StandardCharsets.UTF_8));
/*     */   }
/*     */   
/*     */   public static boolean isOnlineMode() {
/*  79 */     return Bukkit.getOnlineMode();
/*     */   }
/*     */   
/*     */   @Nullable
/*     */   public static UUID getRealUUIDOfPlayer(@NotNull String paramString) {
/*  84 */     if (Strings.isNullOrEmpty(paramString)) {
/*  85 */       throw new IllegalArgumentException("Username is null or empty: " + paramString);
/*     */     }
/*  87 */     UUID uUID1 = getOfflineUUID(paramString);
/*  88 */     UUID uUID2 = USERNAME_TO_ONLINE.get(paramString);
/*  89 */     boolean bool = (uUID2 != null) ? true : false;
/*  90 */     if (uUID2 == null) {
/*     */       try {
/*  92 */         uUID2 = MojangAPI.requestUsernameToUUID(paramString);
/*  93 */         if (uUID2 == null)
/*  94 */         { ProfileLogger.debug("Caching null for {} ({}) because it doesn't exist.", new Object[] { paramString, uUID1 });
/*  95 */           uUID2 = IDENTITY_UUID; }
/*  96 */         else { ONLINE_TO_OFFLINE.put(uUID2, uUID1); }
/*  97 */          OFFLINE_TO_ONLINE.put(uUID1, uUID2);
/*  98 */         USERNAME_TO_ONLINE.put(paramString, uUID2);
/*  99 */       } catch (IOException iOException) {
/* 100 */         throw new IllegalStateException("Error while getting real UUID of player: " + paramString, iOException);
/*     */       } 
/*     */     }
/*     */     
/* 104 */     if (uUID2 == IDENTITY_UUID) {
/* 105 */       ProfileLogger.debug("Providing null UUID for {} because it doesn't exist.", new Object[] { paramString });
/* 106 */       uUID2 = null;
/*     */     } else {
/* 108 */       ProfileLogger.debug((bool ? "Cached " : "") + "Real UUID for {} ({}) is {}", new Object[] { paramString, uUID1, uUID2 });
/*     */     } 
/*     */     
/* 111 */     return uUID2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public static UUID getRealUUIDOfPlayer(@NotNull String paramString, @NotNull UUID paramUUID) {
/* 119 */     Objects.requireNonNull(paramUUID);
/* 120 */     if (Strings.isNullOrEmpty(paramString)) {
/* 121 */       throw new IllegalArgumentException("Username is null or empty: " + paramString);
/*     */     }
/* 123 */     if (isOnlineMode()) return paramUUID;
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     UUID uUID1 = OFFLINE_TO_ONLINE.get(paramUUID);
/* 129 */     boolean bool = (uUID1 != null) ? true : false;
/* 130 */     if (uUID1 == null) {
/*     */       try {
/* 132 */         uUID1 = MojangAPI.requestUsernameToUUID(paramString);
/* 133 */         if (uUID1 == null)
/* 134 */         { ProfileLogger.debug("Caching null for {} ({}) because it doesn't exist.", new Object[] { paramString, paramUUID });
/* 135 */           uUID1 = IDENTITY_UUID; }
/* 136 */         else { ONLINE_TO_OFFLINE.put(uUID1, paramUUID); }
/* 137 */          OFFLINE_TO_ONLINE.put(paramUUID, uUID1);
/* 138 */         USERNAME_TO_ONLINE.put(paramString, uUID1);
/* 139 */       } catch (IOException iOException) {
/* 140 */         throw new IllegalStateException("Error while getting real UUID of player: " + paramString + " (" + paramUUID + ')', iOException);
/*     */       } 
/*     */     }
/*     */     
/* 144 */     if (uUID1 == IDENTITY_UUID) {
/* 145 */       ProfileLogger.debug("Providing null UUID for {} ({}) because it doesn't exist.", new Object[] { paramString, paramUUID });
/* 146 */       uUID1 = null;
/*     */     } else {
/* 148 */       ProfileLogger.debug((bool ? "Cached " : "") + "Real UUID for {} ({}) is {}", new Object[] { paramString, paramUUID, uUID1 });
/*     */     } 
/*     */     
/* 151 */     UUID uUID2 = getOfflineUUID(paramString);
/* 152 */     if (!paramUUID.equals(uUID2) && !paramUUID.equals(uUID1)) {
/* 153 */       throw new IllegalArgumentException("The provided UUID (" + paramUUID + ") for '" + paramString + "' doesn't match the offline UUID (" + uUID2 + ") or the real UUID (" + uUID1 + ')');
/*     */     }
/*     */     
/* 156 */     return uUID1;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\PlayerUUIDs.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */