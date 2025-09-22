/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles;
/*     */ 
/*     */ import com.google.common.cache.LoadingCache;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.minecraft.MinecraftSessionService;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.ReflectiveNamespace;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.XReflection;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.FieldMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.FlaggedNamedMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.jvm.MethodMemberHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftClassHandle;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.reflection.minecraft.MinecraftMapping;
/*     */ import java.lang.invoke.MethodHandle;
/*     */ import java.net.Proxy;
/*     */ import java.util.Map;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.Callable;
/*     */ import org.jetbrains.annotations.ApiStatus.Internal;
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
/*     */ public final class ProfilesCore
/*     */ {
/*     */   public static final Object USER_CACHE;
/*     */   public static final Object MINECRAFT_SESSION_SERVICE;
/*     */   public static final Proxy PROXY;
/*     */   public static final LoadingCache<Object, Object> YggdrasilMinecraftSessionService_insecureProfiles;
/*     */   public static final Map<String, Object> UserCache_profilesByName;
/*     */   public static final Map<UUID, Object> UserCache_profilesByUUID;
/*     */   public static final MethodHandle MinecraftSessionService_fillProfileProperties;
/*     */   public static final MethodHandle GameProfileCache_get$profileByName$;
/*     */   public static final MethodHandle GameProfileCache_get$profileByUUID$;
/*     */   public static final MethodHandle CACHE_PROFILE;
/*     */   public static final MethodHandle CraftMetaSkull_profile$getter;
/*     */   public static final MethodHandle CraftMetaSkull_profile$setter;
/*     */   public static final MethodHandle CraftSkull_profile$setter;
/*     */   public static final MethodHandle CraftSkull_profile$getter;
/*     */   public static final MethodHandle Property_getValue;
/*     */   public static final MethodHandle UserCache_getNextOperation;
/*     */   public static final MethodHandle UserCacheEntry_getProfile;
/*     */   public static final MethodHandle UserCacheEntry_setLastAccess;
/*     */   public static final MethodHandle ResolvableProfile$constructor;
/*     */   public static final MethodHandle ResolvableProfile_gameProfile;
/*     */   public static final boolean ResolvableProfile$bukkitSupports;
/*  70 */   public static final boolean NULLABILITY_RECORD_UPDATE = XReflection.supports(1, 20, 2);
/*     */   
/*     */   static {
/*  73 */     Object object3 = null;
/*     */     
/*  75 */     MethodHandle methodHandle1 = null;
/*     */     
/*  77 */     MethodHandle methodHandle7 = null, methodHandle8 = null;
/*  78 */     boolean bool = false;
/*     */ 
/*     */     
/*  81 */     ReflectiveNamespace reflectiveNamespace = XReflection.namespaced().imports(new Class[] { GameProfile.class, MinecraftSessionService.class, LoadingCache.class });
/*     */ 
/*     */ 
/*     */     
/*  85 */     MinecraftClassHandle minecraftClassHandle1 = reflectiveNamespace.ofMinecraft("package nms.server.players; public class GameProfileCache").map(MinecraftMapping.SPIGOT, "UserCache");
/*     */     
/*     */     try {
/*  88 */       MinecraftClassHandle minecraftClassHandle4 = reflectiveNamespace.ofMinecraft("package cb.inventory; class CraftMetaSkull extends CraftMetaItem implements SkullMeta");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  95 */       MinecraftClassHandle minecraftClassHandle5 = reflectiveNamespace.ofMinecraft("package nms.world.item.component; public class ResolvableProfile");
/*     */       
/*  97 */       if (minecraftClassHandle5.exists()) {
/*  98 */         methodHandle7 = minecraftClassHandle5.constructor("public ResolvableProfile(GameProfile gameProfile)").reflect();
/*     */ 
/*     */         
/* 101 */         methodHandle8 = minecraftClassHandle5.method("public GameProfile gameProfile()").map(MinecraftMapping.OBFUSCATED, "f").reflect();
/*     */         
/* 103 */         bool = minecraftClassHandle4.field("private ResolvableProfile profile").exists();
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 110 */       methodHandle6 = (MethodHandle)XReflection.any((ReflectiveHandle[])new FieldMemberHandle[] { minecraftClassHandle4.field("private ResolvableProfile profile"), minecraftClassHandle4.field("private GameProfile       profile") }).modify(FieldMemberHandle::getter).reflect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 120 */       methodHandle5 = (MethodHandle)XReflection.any((ReflectiveHandle[])new FlaggedNamedMemberHandle[] { (FlaggedNamedMemberHandle)minecraftClassHandle4.method("private void setProfile(ResolvableProfile profile)"), (FlaggedNamedMemberHandle)minecraftClassHandle4.method("private void setProfile(GameProfile       profile)"), (FlaggedNamedMemberHandle)minecraftClassHandle4.field("private                 GameProfile       profile ").setter() }).reflect();
/*     */ 
/*     */       
/* 123 */       MinecraftClassHandle minecraftClassHandle6 = reflectiveNamespace.ofMinecraft("package nms.server; public abstract class MinecraftServer");
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 128 */       Object object = minecraftClassHandle6.method("public static MinecraftServer getServer()").reflect().invoke();
/*     */ 
/*     */ 
/*     */       
/* 132 */       object2 = minecraftClassHandle6.method("public MinecraftSessionService getSessionService()").named(new String[] { "aq", "ay", "getMinecraftSessionService", "az", "ao", "am", "aD", "ar", "ap" }).reflect().invoke(object);
/*     */ 
/*     */ 
/*     */       
/* 136 */       FieldMemberHandle fieldMemberHandle1 = reflectiveNamespace.ofMinecraft("package com.mojang.authlib.yggdrasil;public class YggdrasilMinecraftSessionService implements MinecraftSessionService").field().getter();
/* 137 */       if (NULLABILITY_RECORD_UPDATE) {
/* 138 */         fieldMemberHandle1.signature("private final LoadingCache<UUID, Optional<ProfileResult>> insecureProfiles");
/*     */       } else {
/* 140 */         fieldMemberHandle1.signature("private final LoadingCache<GameProfile, GameProfile> insecureProfiles");
/*     */       } 
/* 142 */       MethodHandle methodHandle = (MethodHandle)fieldMemberHandle1.reflectOrNull();
/* 143 */       if (methodHandle != null) {
/* 144 */         object3 = methodHandle.invoke(object2);
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 151 */       object1 = minecraftClassHandle6.method("public GameProfileCache getProfileCache()").named(new String[] { "at", "ar", "ao", "ap", "au" }).map(MinecraftMapping.OBFUSCATED, "getUserCache").reflect().invoke(object);
/*     */       
/* 153 */       if (!NULLABILITY_RECORD_UPDATE)
/*     */       {
/*     */         
/* 156 */         methodHandle1 = reflectiveNamespace.of(MinecraftSessionService.class).method("public GameProfile fillProfileProperties(GameProfile profile, boolean flag)").reflect();
/*     */       }
/*     */ 
/*     */ 
/*     */       
/* 161 */       UserCache_getNextOperation = (MethodHandle)minecraftClassHandle1.method("private long getNextOperation()").map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(21, "e").v(16, "d").orElse("d")).reflectOrNull();
/*     */       
/* 163 */       MethodMemberHandle methodMemberHandle1 = minecraftClassHandle1.method().named(new String[] { "getProfile", "a" });
/* 164 */       MethodMemberHandle methodMemberHandle2 = minecraftClassHandle1.method().named(new String[] { "getProfile", "a" });
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 169 */       methodHandle2 = (MethodHandle)XReflection.anyOf(new Callable[] { () -> paramMethodMemberHandle.signature("public          GameProfile  get(String username)"), () -> paramMethodMemberHandle.signature("public Optional<GameProfile> get(String username)") }).reflect();
/*     */ 
/*     */ 
/*     */       
/* 173 */       methodHandle3 = (MethodHandle)XReflection.anyOf(new Callable[] { () -> paramMethodMemberHandle.signature("public          GameProfile  get(UUID id)"), () -> paramMethodMemberHandle.signature("public Optional<GameProfile> get(UUID id)") }).reflect();
/*     */ 
/*     */ 
/*     */       
/* 177 */       methodHandle4 = minecraftClassHandle1.method("public void add(GameProfile profile)").map(MinecraftMapping.OBFUSCATED, "a").reflect();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/* 188 */         proxy = minecraftClassHandle6.field("protected final java.net.Proxy proxy").getter().map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(20, 5, "h").v(20, 3, "i").v(19, "j").v(18, 2, "n").v(18, "o").v(17, "m").v(14, "proxy").v(13, "c").orElse("e")).reflect().invoke(object);
/* 189 */       } catch (Throwable throwable) {
/* 190 */         ProfileLogger.LOGGER.error("Failed to initialize server proxy settings", throwable);
/* 191 */         proxy = null;
/*     */       } 
/* 193 */     } catch (Throwable throwable) {
/* 194 */       throw XReflection.throwCheckedException(throwable);
/*     */     } 
/*     */     
/* 197 */     MinecraftClassHandle minecraftClassHandle2 = reflectiveNamespace.ofMinecraft("package cb.block; public class CraftSkull extends CraftBlockEntityState implements Skull");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 204 */     FieldMemberHandle fieldMemberHandle = (FieldMemberHandle)XReflection.any((ReflectiveHandle[])new FieldMemberHandle[] { minecraftClassHandle2.field("private ResolvableProfile profile"), minecraftClassHandle2.field("private GameProfile profile") }).getHandle();
/*     */ 
/*     */     
/* 207 */     Property_getValue = NULLABILITY_RECORD_UPDATE ? null : (MethodHandle)reflectiveNamespace.of(Property.class).method("public String getValue()").unreflect();
/*     */     
/* 209 */     PROXY = proxy;
/* 210 */     USER_CACHE = object1;
/* 211 */     CACHE_PROFILE = methodHandle4;
/* 212 */     MINECRAFT_SESSION_SERVICE = object2;
/*     */     
/* 214 */     YggdrasilMinecraftSessionService_insecureProfiles = (LoadingCache<Object, Object>)object3;
/* 215 */     MinecraftSessionService_fillProfileProperties = methodHandle1;
/*     */     
/* 217 */     GameProfileCache_get$profileByName$ = methodHandle2;
/* 218 */     GameProfileCache_get$profileByUUID$ = methodHandle3;
/*     */     
/* 220 */     CraftMetaSkull_profile$setter = methodHandle5;
/* 221 */     CraftMetaSkull_profile$getter = methodHandle6;
/*     */     
/* 223 */     CraftSkull_profile$setter = (MethodHandle)fieldMemberHandle.setter().unreflect();
/* 224 */     CraftSkull_profile$getter = (MethodHandle)fieldMemberHandle.getter().unreflect();
/*     */     
/* 226 */     ResolvableProfile$constructor = methodHandle7;
/* 227 */     ResolvableProfile_gameProfile = methodHandle8;
/* 228 */     ResolvableProfile$bukkitSupports = bool;
/*     */ 
/*     */ 
/*     */     
/* 232 */     MinecraftClassHandle minecraftClassHandle3 = minecraftClassHandle1.inner("private static class GameProfileInfo").map(MinecraftMapping.SPIGOT, "UserCacheEntry");
/*     */ 
/*     */ 
/*     */     
/* 236 */     UserCacheEntry_getProfile = (MethodHandle)minecraftClassHandle3.method("public GameProfile getProfile()").map(MinecraftMapping.OBFUSCATED, "a").makeAccessible().unreflect();
/*     */     
/* 238 */     UserCacheEntry_setLastAccess = (MethodHandle)minecraftClassHandle3.method("public void setLastAccess(long i)").map(MinecraftMapping.OBFUSCATED, "a").reflectOrNull();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 244 */       UserCache_profilesByName = minecraftClassHandle1.field("private final Map<String, UserCache.UserCacheEntry> profilesByName").getter().map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(17, "e").v(16, 2, "c").v(9, "d").orElse("c")).reflect().invoke(object1);
/*     */ 
/*     */ 
/*     */       
/* 248 */       UserCache_profilesByUUID = minecraftClassHandle1.field("private final Map<UUID, UserCache.UserCacheEntry> profilesByUUID").getter().map(MinecraftMapping.OBFUSCATED, (String)XReflection.v(17, "f").v(16, 2, "d").v(9, "e").orElse("d")).reflect().invoke(object1);
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 253 */     catch (Throwable throwable) {
/* 254 */       throw new IllegalStateException("Failed to initialize ProfilesCore", throwable);
/*     */     } 
/*     */   }
/*     */   
/*     */   static {
/*     */     Object object1, object2;
/*     */     Proxy proxy;
/*     */     MethodHandle methodHandle2, methodHandle3, methodHandle4, methodHandle5, methodHandle6;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\ProfilesCore.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */