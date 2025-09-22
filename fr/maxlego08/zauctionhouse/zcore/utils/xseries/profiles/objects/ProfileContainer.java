/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects;
/*     */ 
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.ProfilesCore;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.exceptions.InvalidProfileContainerException;
/*     */ import java.util.Objects;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
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
/*     */ @Internal
/*     */ public abstract class ProfileContainer<T>
/*     */   implements Profileable
/*     */ {
/*     */   @NotNull
/*     */   public abstract void setProfile(@Nullable GameProfile paramGameProfile);
/*     */   
/*     */   @NotNull
/*     */   public abstract T getObject();
/*     */   
/*     */   public final String toString() {
/*  56 */     return getClass().getSimpleName() + '[' + getObject() + ']';
/*     */   }
/*     */   
/*     */   public static final class ItemStackProfileContainer
/*     */     extends ProfileContainer<ItemStack> implements DelegateProfileable {
/*     */     public ItemStackProfileContainer(ItemStack param1ItemStack) {
/*  62 */       this.itemStack = Objects.<ItemStack>requireNonNull(param1ItemStack, "ItemStack is null");
/*     */     } private final ItemStack itemStack;
/*     */     private ProfileContainer.ItemMetaProfileContainer getMetaContainer(ItemMeta param1ItemMeta) {
/*  65 */       if (!(param1ItemMeta instanceof SkullMeta))
/*  66 */         throw new InvalidProfileContainerException(this.itemStack, "Item can't contain texture: " + this.itemStack); 
/*  67 */       return new ProfileContainer.ItemMetaProfileContainer((SkullMeta)param1ItemMeta);
/*     */     }
/*     */ 
/*     */     
/*     */     public void setProfile(GameProfile param1GameProfile) {
/*  72 */       ItemMeta itemMeta = this.itemStack.getItemMeta();
/*  73 */       getMetaContainer(itemMeta).setProfile(param1GameProfile);
/*  74 */       this.itemStack.setItemMeta(itemMeta);
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemStack getObject() {
/*  79 */       return this.itemStack;
/*     */     }
/*     */ 
/*     */     
/*     */     public Profileable getDelegateProfile() {
/*  84 */       return getMetaContainer(this.itemStack.getItemMeta());
/*     */     } }
/*     */   
/*     */   public static final class ItemMetaProfileContainer extends ProfileContainer<ItemMeta> {
/*     */     private final ItemMeta meta;
/*     */     
/*     */     public ItemMetaProfileContainer(SkullMeta param1SkullMeta) {
/*  91 */       this.meta = (ItemMeta)Objects.requireNonNull(param1SkullMeta, "ItemMeta is null");
/*     */     }
/*     */     
/*     */     public void setProfile(GameProfile param1GameProfile) {
/*     */       try {
/*  96 */         ProfilesCore.CraftMetaSkull_profile$setter.invoke(this.meta, PlayerProfiles.wrapProfile(param1GameProfile));
/*  97 */       } catch (Throwable throwable) {
/*  98 */         throw new IllegalStateException("Unable to set profile " + param1GameProfile + " to " + this.meta, throwable);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public ItemMeta getObject() {
/* 104 */       return this.meta;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getProfile() {
/*     */       try {
/* 110 */         return PlayerProfiles.unwrapProfile(ProfilesCore.CraftMetaSkull_profile$getter.invoke((SkullMeta)this.meta));
/* 111 */       } catch (Throwable throwable) {
/* 112 */         throw new IllegalStateException("Failed to get profile from item meta: " + this.meta, throwable);
/*     */       } 
/*     */     }
/*     */   }
/*     */   
/*     */   public static final class BlockProfileContainer extends ProfileContainer<Block> implements DelegateProfileable { private final Block block;
/*     */     
/*     */     public BlockProfileContainer(Block param1Block) {
/* 120 */       this.block = Objects.<Block>requireNonNull(param1Block, "Block is null");
/*     */     }
/*     */     private Skull getBlockState() {
/* 123 */       BlockState blockState = this.block.getState();
/* 124 */       if (!(blockState instanceof Skull))
/* 125 */         throw new InvalidProfileContainerException(this.block, "Block can't contain texture: " + this.block); 
/* 126 */       return (Skull)blockState;
/*     */     }
/*     */ 
/*     */     
/*     */     public void setProfile(GameProfile param1GameProfile) {
/* 131 */       Skull skull = getBlockState();
/* 132 */       (new ProfileContainer.BlockStateProfileContainer(skull)).setProfile(param1GameProfile);
/* 133 */       skull.update(true);
/*     */     }
/*     */ 
/*     */     
/*     */     public Block getObject() {
/* 138 */       return this.block;
/*     */     }
/*     */ 
/*     */     
/*     */     public Profileable getDelegateProfile() {
/* 143 */       return new ProfileContainer.BlockStateProfileContainer(getBlockState());
/*     */     } }
/*     */   
/*     */   public static final class BlockStateProfileContainer extends ProfileContainer<Skull> {
/*     */     private final Skull state;
/*     */     
/*     */     public BlockStateProfileContainer(Skull param1Skull) {
/* 150 */       this.state = Objects.<Skull>requireNonNull(param1Skull, "Skull BlockState is null");
/*     */     }
/*     */     
/*     */     public void setProfile(GameProfile param1GameProfile) {
/*     */       try {
/* 155 */         ProfilesCore.CraftSkull_profile$setter.invoke(this.state, PlayerProfiles.wrapProfile(param1GameProfile));
/* 156 */       } catch (Throwable throwable) {
/* 157 */         throw new IllegalStateException("Unable to set profile " + param1GameProfile + " to " + this.state, throwable);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public Skull getObject() {
/* 163 */       return this.state;
/*     */     }
/*     */ 
/*     */     
/*     */     public GameProfile getProfile() {
/*     */       try {
/* 169 */         return PlayerProfiles.unwrapProfile(ProfilesCore.CraftSkull_profile$getter.invoke(this.state));
/* 170 */       } catch (Throwable throwable) {
/* 171 */         throw new IllegalStateException("Unable to get profile fr om blockstate: " + this.state, throwable);
/*     */       } 
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\objects\ProfileContainer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */