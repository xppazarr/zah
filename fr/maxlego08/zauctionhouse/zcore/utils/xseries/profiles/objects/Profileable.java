/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects;
/*     */ 
/*     */ import com.google.common.base.Function;
/*     */ import com.google.common.base.Strings;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerUUIDs;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.InvalidProfileException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.ProfileException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.UnknownPlayerException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.MojangAPI;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.PlayerProfileFetcherThread;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.ProfileRequestConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.cache.TimedCacheableProfileable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.transformer.ProfileTransformer;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.transformer.TransformableProfile;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collection;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Objects;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.CompletionStage;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
/*     */ import org.jetbrains.annotations.Contract;
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
/*     */ public interface Profileable
/*     */ {
/*     */   @Nullable
/*     */   default ProfileException test() {
/*     */     try {
/*  97 */       getProfile();
/*  98 */       return null;
/*  99 */     } catch (ProfileException ex) {
/* 100 */       return ex;
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
/*     */   @Nullable
/*     */   @Internal
/*     */   default GameProfile getDisposableProfile() {
/* 116 */     GameProfile profile = getProfile();
/* 117 */     return (profile == null) ? null : PlayerProfiles.clone(profile);
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
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   Profileable transform(@NotNull ProfileTransformer... transformers) {
/* 134 */     return (Profileable)new TransformableProfile(this, Arrays.asList(transformers));
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
/*     */   @Nullable
/*     */   default String getProfileValue() {
/* 149 */     return PlayerProfiles.getOriginalValue(getProfile());
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Experimental
/*     */   static <C extends Collection<Profileable>> CompletableFuture<C> prepare(@NotNull C profileables) {
/* 155 */     return prepare(profileables, null, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Experimental
/*     */   static <C extends Collection<Profileable>> CompletableFuture<C> prepare(@NotNull C profileables, @Nullable ProfileRequestConfiguration config, @Nullable Function<Throwable, Boolean> errorHandler) {
/* 163 */     Objects.requireNonNull(profileables, "Profile list is null");
/*     */     
/* 165 */     CompletableFuture<Map<UUID, String>> initial = CompletableFuture.completedFuture(new HashMap<>());
/* 166 */     List<String> usernameRequests = new ArrayList<>();
/*     */     
/* 168 */     if (!PlayerUUIDs.isOnlineMode()) {
/* 169 */       for (Profileable profileable : profileables) {
/* 170 */         String username = null;
/* 171 */         if (profileable instanceof UsernameProfileable) {
/* 172 */           username = ((UsernameProfileable)profileable).username;
/* 173 */         } else if (profileable instanceof PlayerProfileable) {
/* 174 */           username = ((PlayerProfileable)profileable).username;
/* 175 */         } else if (profileable instanceof StringProfileable && 
/* 176 */           (((StringProfileable)profileable).determineType()).type == ProfileInputType.USERNAME) {
/* 177 */           username = ((StringProfileable)profileable).string;
/*     */         } 
/*     */ 
/*     */         
/* 181 */         if (username != null) {
/* 182 */           usernameRequests.add(username);
/*     */         }
/*     */       } 
/*     */       
/* 186 */       if (!usernameRequests.isEmpty()) {
/* 187 */         initial = CompletableFuture.supplyAsync(() -> MojangAPI.usernamesToUUIDs(usernameRequests, config), PlayerProfileFetcherThread.EXECUTOR);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 192 */     return XReflection.stacktrace(initial
/* 193 */         .thenCompose(a -> {
/*     */             List<CompletableFuture<GameProfile>> requests = new ArrayList<>(profileables.size());
/*     */ 
/*     */             
/*     */             for (Profileable profileable : profileables) {
/*     */               Objects.requireNonNull(profileable);
/*     */ 
/*     */               
/*     */               CompletableFuture<GameProfile> async = CompletableFuture.supplyAsync(profileable::getProfile, PlayerProfileFetcherThread.EXECUTOR);
/*     */ 
/*     */               
/*     */               if (errorHandler != null) {
/*     */                 async = XReflection.stacktrace(async).exceptionally(());
/*     */               }
/*     */ 
/*     */               
/*     */               requests.add(async);
/*     */             } 
/*     */             
/*     */             return CompletableFuture.allOf((CompletableFuture<?>[])requests.<CompletableFuture>toArray(new CompletableFuture[0]));
/* 213 */           }).thenApply(a -> profileables));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable username(@NotNull String username) {
/* 222 */     return (Profileable)new UsernameProfileable(username);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull UUID uuid) {
/* 231 */     return (Profileable)new UUIDProfileable(uuid);
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
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull GameProfile profile) {
/* 244 */     return (Profileable)new GameProfileProfileable(profile);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull OfflinePlayer offlinePlayer) {
/* 256 */     return (Profileable)new PlayerProfileable(offlinePlayer);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull BlockState blockState) {
/* 262 */     return new ProfileContainer.BlockStateProfileContainer((Skull)blockState);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull Block block) {
/* 268 */     return new ProfileContainer.BlockProfileContainer(block);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull ItemStack item) {
/* 274 */     return new ProfileContainer.ItemStackProfileContainer(item);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull ItemMeta meta) {
/* 280 */     return new ProfileContainer.ItemMetaProfileContainer((SkullMeta)meta);
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
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable detect(@NotNull String input) {
/* 298 */     return (Profileable)new StringProfileable(input, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   static Profileable of(@NotNull ProfileInputType type, @NotNull String input) {
/* 310 */     Objects.requireNonNull(type, () -> "Cannot profile from a null input type: " + input);
/* 311 */     Objects.requireNonNull(input, () -> "Cannot profile from a null input: " + type);
/* 312 */     return (Profileable)new StringProfileable(input, type);
/*     */   }
/*     */   @Nullable
/*     */   @Internal
/*     */   GameProfile getProfile();
/*     */   @Internal
/*     */   public static final class UsernameProfileable extends TimedCacheableProfileable { private final String username;
/*     */     public UsernameProfileable(String param1String) {
/* 320 */       this.username = Objects.<String>requireNonNull(param1String);
/*     */     }
/*     */     private Boolean valid;
/*     */     public String getProfileValue() {
/* 324 */       return this.username;
/*     */     }
/*     */ 
/*     */     
/*     */     protected GameProfile getProfile0() {
/* 329 */       if (this.valid == null) {
/* 330 */         this.valid = Boolean.valueOf(ProfileInputType.USERNAME.pattern.matcher(this.username).matches());
/*     */       }
/* 332 */       if (!this.valid.booleanValue()) throw new InvalidProfileException(this.username, "Invalid username: '" + this.username + '\'');
/*     */       
/* 334 */       Optional<GameProfile> optional = MojangAPI.getMojangCachedProfileFromUsername(this.username);
/* 335 */       if (!optional.isPresent()) {
/* 336 */         throw new UnknownPlayerException(this.username, "Cannot find player named '" + this.username + '\'');
/*     */       }
/* 338 */       GameProfile gameProfile = optional.get();
/* 339 */       if (PlayerProfiles.hasTextures(gameProfile)) return gameProfile; 
/* 340 */       return MojangAPI.getOrFetchProfile(gameProfile);
/*     */     } }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static final class UUIDProfileable extends TimedCacheableProfileable { private final UUID id;
/*     */     
/*     */     public UUIDProfileable(UUID param1UUID) {
/* 348 */       this.id = Objects.<UUID>requireNonNull(param1UUID, "UUID cannot be null");
/*     */     }
/*     */     
/*     */     public String getProfileValue() {
/* 352 */       return this.id.toString();
/*     */     }
/*     */ 
/*     */     
/*     */     protected GameProfile getProfile0() {
/* 357 */       GameProfile gameProfile = MojangAPI.getCachedProfileByUUID(this.id);
/* 358 */       if (PlayerProfiles.hasTextures(gameProfile)) return gameProfile; 
/* 359 */       return MojangAPI.getOrFetchProfile(gameProfile);
/*     */     } }
/*     */   
/*     */   @Internal
/*     */   public static final class GameProfileProfileable extends TimedCacheableProfileable {
/*     */     private final GameProfile profile;
/*     */     
/*     */     public GameProfileProfileable(GameProfile param1GameProfile) {
/* 367 */       this.profile = Objects.<GameProfile>requireNonNull(param1GameProfile);
/*     */     }
/*     */     
/*     */     protected GameProfile getProfile0() {
/* 371 */       if (PlayerProfiles.hasTextures(this.profile)) {
/* 372 */         return this.profile;
/*     */       }
/*     */       
/* 375 */       return (PlayerUUIDs.isOnlineMode() ? 
/* 376 */         new Profileable.UUIDProfileable(this.profile.getId()) : 
/* 377 */         new Profileable.UsernameProfileable(this.profile.getName()))
/* 378 */         .getProfile();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   @Internal
/*     */   public static final class PlayerProfileable
/*     */     extends TimedCacheableProfileable
/*     */   {
/*     */     @Nullable
/*     */     private final String username;
/*     */     @NotNull
/*     */     private final UUID id;
/*     */     
/*     */     public PlayerProfileable(OfflinePlayer param1OfflinePlayer) {
/* 393 */       Objects.requireNonNull(param1OfflinePlayer);
/* 394 */       this.username = param1OfflinePlayer.getName();
/* 395 */       this.id = param1OfflinePlayer.getUniqueId();
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProfileValue() {
/* 400 */       return Strings.isNullOrEmpty(this.username) ? this.id.toString() : this.username;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected GameProfile getProfile0() {
/* 407 */       if (Strings.isNullOrEmpty(this.username)) {
/* 408 */         return (new Profileable.UUIDProfileable(this.id)).getProfile();
/*     */       }
/* 410 */       return (new Profileable.UsernameProfileable(this.username)).getProfile();
/*     */     }
/*     */   }
/*     */   
/*     */   @Internal
/*     */   public static final class StringProfileable extends TimedCacheableProfileable {
/*     */     private final String string;
/*     */     @Nullable
/*     */     private ProfileInputType type;
/*     */     
/*     */     public StringProfileable(String param1String, @Nullable ProfileInputType param1ProfileInputType) {
/* 421 */       this.string = Objects.<String>requireNonNull(param1String, "Input string is null");
/* 422 */       this.type = param1ProfileInputType;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getProfileValue() {
/* 427 */       return this.string;
/*     */     }
/*     */     
/*     */     private StringProfileable determineType() {
/* 431 */       if (this.type == null) this.type = ProfileInputType.typeOf(this.string); 
/* 432 */       return this;
/*     */     }
/*     */ 
/*     */     
/*     */     protected GameProfile getProfile0() {
/* 437 */       determineType();
/* 438 */       if (this.type == null) {
/* 439 */         throw new InvalidProfileException(this.string, "Unknown skull string value: " + this.string);
/*     */       }
/* 441 */       return this.type.getProfile(this.string);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\Profileable.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */