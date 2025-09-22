/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.transformer;
/*     */ 
/*     */ import com.google.common.collect.Iterables;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import com.mojang.authlib.properties.Property;
/*     */ import com.mojang.authlib.properties.PropertyMap;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.Profileable;
/*     */ import java.util.Collection;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.atomic.AtomicLong;
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
/*     */ public interface ProfileTransformer
/*     */ {
/*     */   @NotNull
/*     */   GameProfile transform(@NotNull Profileable paramProfileable, @NotNull GameProfile paramGameProfile);
/*     */   
/*     */   @Internal
/*     */   boolean canBeCached();
/*     */   
/*     */   @NotNull
/*     */   static ProfileTransformer stackable() {
/*  92 */     return RemoveMetadata.INSTANCE;
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
/*     */   @NotNull
/*     */   static ProfileTransformer nonStackable() {
/* 105 */     return MakeNotStackable.INSTANCE;
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
/*     */   static ProfileTransformer removeMetadata() {
/* 119 */     return RemoveMetadata.INSTANCE;
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
/*     */   static ProfileTransformer includeOriginalValue() {
/* 133 */     return IncludeOriginalValue.INSTANCE;
/*     */   }
/*     */   
/*     */   public static final class IncludeOriginalValue implements ProfileTransformer {
/* 137 */     private static final IncludeOriginalValue INSTANCE = new IncludeOriginalValue();
/*     */     public static final String PROPERTY_NAME = "OriginalValue";
/*     */     
/*     */     @Nullable
/*     */     public static String getOriginalValue(@NotNull GameProfile param1GameProfile) {
/* 142 */       PropertyMap propertyMap = param1GameProfile.getProperties();
/* 143 */       Collection collection = propertyMap.get("OriginalValue");
/* 144 */       if (collection.isEmpty()) return null;
/*     */       
/* 146 */       Property property = (Property)Iterables.getFirst(collection, null);
/* 147 */       return PlayerProfiles.getPropertyValue(property);
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile transform(Profileable param1Profileable, GameProfile param1GameProfile) {
/* 152 */       String str = param1Profileable.getProfileValue();
/* 153 */       param1GameProfile.getProperties().put("OriginalValue", new Property("OriginalValue", str));
/* 154 */       return param1GameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canBeCached() {
/* 159 */       return true;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class MakeNotStackable implements ProfileTransformer {
/* 164 */     private static final MakeNotStackable INSTANCE = new MakeNotStackable();
/*     */     private static final String PROPERTY_NAME = "XSeriesSeed";
/* 166 */     private static final AtomicLong NEXT_ID = new AtomicLong();
/*     */ 
/*     */     
/*     */     public GameProfile transform(Profileable param1Profileable, GameProfile param1GameProfile) {
/* 170 */       String str = System.currentTimeMillis() + "-" + NEXT_ID.getAndIncrement();
/* 171 */       param1GameProfile.getProperties().put("XSeriesSeed", new Property("XSeriesSeed", str));
/* 172 */       return param1GameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canBeCached() {
/* 177 */       return false;
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class RemoveMetadata implements ProfileTransformer {
/* 182 */     private static final RemoveMetadata INSTANCE = new RemoveMetadata();
/*     */ 
/*     */     
/*     */     public GameProfile transform(Profileable param1Profileable, GameProfile param1GameProfile) {
/* 186 */       PlayerProfiles.removeTimestamp(param1GameProfile);
/*     */       
/* 188 */       Map map = param1GameProfile.getProperties().asMap();
/* 189 */       map.remove("XSeries");
/* 190 */       map.remove("OriginalValue");
/* 191 */       return param1GameProfile;
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean canBeCached() {
/* 196 */       return true;
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\transformer\ProfileTransformer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */