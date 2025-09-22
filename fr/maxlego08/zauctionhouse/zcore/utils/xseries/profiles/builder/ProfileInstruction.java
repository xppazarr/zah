/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.builder;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.ProfileLogger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.InvalidProfileException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.ProfileChangeException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.ProfileException;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.PlayerProfileFetcherThread;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.mojang.ProfileRequestConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.DelegateProfileable;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.ProfileContainer;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.Profileable;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.function.Consumer;
/*     */ import org.jetbrains.annotations.ApiStatus.Experimental;
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
/*     */ public final class ProfileInstruction<T>
/*     */   implements DelegateProfileable
/*     */ {
/*     */   private final ProfileContainer<T> profileContainer;
/*     */   private Profileable profileable;
/*  70 */   private final List<Profileable> fallbacks = new ArrayList<>();
/*     */   
/*     */   private Consumer<ProfileFallback<T>> onFallback;
/*     */   private ProfileRequestConfiguration profileRequestConfiguration;
/*     */   private boolean lenient = false;
/*     */   
/*     */   protected ProfileInstruction(ProfileContainer<T> paramProfileContainer) {
/*  77 */     this.profileContainer = paramProfileContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(mutates = "this")
/*     */   public T removeProfile() {
/*  86 */     this.profileContainer.setProfile(null);
/*  87 */     return (T)this.profileContainer.getObject();
/*     */   }
/*     */   
/*     */   @Experimental
/*     */   @NotNull
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ProfileInstruction<T> profileRequestConfiguration(ProfileRequestConfiguration paramProfileRequestConfiguration) {
/*  94 */     this.profileRequestConfiguration = paramProfileRequestConfiguration;
/*  95 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "-> this", mutates = "this")
/*     */   public ProfileInstruction<T> lenient() {
/* 105 */     this.lenient = true;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @Nullable
/*     */   public GameProfile getProfile() {
/* 116 */     return this.profileContainer.getProfile();
/*     */   }
/*     */ 
/*     */   
/*     */   @Contract(pure = true)
/*     */   public Profileable getDelegateProfile() {
/* 122 */     return (Profileable)this.profileContainer;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ProfileInstruction<T> profile(@NotNull Profileable paramProfileable) {
/* 132 */     this.profileable = Objects.<Profileable>requireNonNull(paramProfileable, "Profileable is null");
/* 133 */     return this;
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
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ProfileInstruction<T> fallback(@NotNull Profileable... paramVarArgs) {
/* 146 */     Objects.requireNonNull(paramVarArgs, "fallbacks array is null");
/* 147 */     this.fallbacks.addAll(Arrays.asList(paramVarArgs));
/* 148 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ProfileInstruction<T> onFallback(@Nullable Consumer<ProfileFallback<T>> paramConsumer) {
/* 160 */     this.onFallback = paramConsumer;
/* 161 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> this", mutates = "this")
/*     */   public ProfileInstruction<T> onFallback(@NotNull Runnable paramRunnable) {
/* 170 */     Objects.requireNonNull(paramRunnable, "onFallback runnable is null");
/* 171 */     this.onFallback = (paramProfileFallback -> paramRunnable.run());
/* 172 */     return this;
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
/*     */   @NotNull
/*     */   public T apply() {
/* 193 */     Objects.requireNonNull(this.profileable, "No profile was set");
/* 194 */     ProfileChangeException profileChangeException = null;
/*     */     
/* 196 */     ArrayList<Profileable> arrayList = new ArrayList(2 + this.fallbacks.size());
/* 197 */     arrayList.add(this.profileable);
/* 198 */     arrayList.addAll(this.fallbacks);
/* 199 */     if (this.lenient) arrayList.add(XSkull.getDefaultProfile());
/*     */     
/* 201 */     boolean bool1 = false;
/* 202 */     boolean bool2 = false;
/* 203 */     for (Profileable profileable : arrayList) {
/*     */       try {
/* 205 */         GameProfile gameProfile = profileable.getDisposableProfile();
/* 206 */         if (gameProfile != null) {
/* 207 */           this.profileContainer.setProfile(gameProfile);
/* 208 */           bool1 = true;
/*     */           break;
/*     */         } 
/* 211 */         if (profileChangeException == null) {
/* 212 */           profileChangeException = new ProfileChangeException("Could not set the profile for " + this.profileContainer);
/*     */         }
/* 214 */         profileChangeException.addSuppressed((Throwable)new InvalidProfileException(profileable.toString(), "Profile doesn't have a value: " + profileable));
/* 215 */         bool2 = true;
/*     */       }
/* 217 */       catch (ProfileException profileException) {
/* 218 */         if (profileChangeException == null) {
/* 219 */           profileChangeException = new ProfileChangeException("Could not set the profile for " + this.profileContainer);
/*     */         }
/* 221 */         profileChangeException.addSuppressed((Throwable)profileException);
/* 222 */         bool2 = true;
/*     */       } 
/*     */     } 
/*     */     
/* 226 */     if (profileChangeException != null) {
/* 227 */       if (bool1 || this.lenient) { ProfileLogger.debug("apply() silenced exception {}", new Object[] { profileChangeException }); }
/* 228 */       else { throw profileChangeException; }
/*     */     
/*     */     }
/* 231 */     Object object = this.profileContainer.getObject();
/* 232 */     if (bool2 && this.onFallback != null) {
/* 233 */       ProfileFallback<T> profileFallback = new ProfileFallback(this, object, profileChangeException);
/* 234 */       this.onFallback.accept(profileFallback);
/* 235 */       object = profileFallback.getObject();
/*     */     } 
/* 237 */     return (T)object;
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
/*     */   @NotNull
/*     */   public CompletableFuture<T> applyAsync() {
/* 276 */     return CompletableFuture.supplyAsync(this::apply, PlayerProfileFetcherThread.EXECUTOR);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\builder\ProfileInstruction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */