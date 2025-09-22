/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.builder;
/*     */ 
/*     */ import com.google.common.collect.Multimap;
/*     */ import com.mojang.authlib.GameProfile;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.XMaterial;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.PlayerProfiles;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.ProfileContainer;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.ProfileInputType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.xseries.profiles.objects.Profileable;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.block.BlockState;
/*     */ import org.bukkit.block.Skull;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.inventory.meta.ItemMeta;
/*     */ import org.bukkit.inventory.meta.SkullMeta;
/*     */ import org.jetbrains.annotations.Contract;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class XSkull
/*     */ {
/*     */   @NotNull
/*     */   @Contract(value = "-> new", pure = true)
/*     */   public static ProfileInstruction<ItemStack> createItem() {
/*  90 */     return of(XMaterial.PLAYER_HEAD.parseItem());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static ProfileInstruction<ItemStack> of(@NotNull ItemStack paramItemStack) {
/* 102 */     return new ProfileInstruction<>((ProfileContainer<ItemStack>)new ProfileContainer.ItemStackProfileContainer(paramItemStack));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static ProfileInstruction<ItemMeta> of(@NotNull ItemMeta paramItemMeta) {
/* 114 */     return new ProfileInstruction<>((ProfileContainer<ItemMeta>)new ProfileContainer.ItemMetaProfileContainer((SkullMeta)paramItemMeta));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static ProfileInstruction<Block> of(@NotNull Block paramBlock) {
/* 126 */     return new ProfileInstruction<>((ProfileContainer<Block>)new ProfileContainer.BlockProfileContainer(paramBlock));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   @Contract(value = "_ -> new", pure = true)
/*     */   public static ProfileInstruction<Skull> of(@NotNull BlockState paramBlockState) {
/* 138 */     return new ProfileInstruction<>((ProfileContainer<Skull>)new ProfileContainer.BlockStateProfileContainer((Skull)paramBlockState));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 146 */   private static final GameProfile DEFAULT_PROFILE = PlayerProfiles.signXSeries(ProfileInputType.BASE64.getProfile("eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzEwNTkxZTY5MDllNmEyODFiMzcxODM2ZTQ2MmQ2N2EyYzc4ZmEwOTUyZTkxMGYzMmI0MWEyNmM0OGMxNzU3YyJ9fX0="));
/*     */ 
/*     */ 
/*     */ 
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
/*     */   @Contract(value = "-> new", pure = true)
/*     */   protected static Profileable getDefaultProfile() {
/* 162 */     GameProfile gameProfile = PlayerProfiles.createGameProfile(DEFAULT_PROFILE.getId(), DEFAULT_PROFILE.getName());
/* 163 */     gameProfile.getProperties().putAll((Multimap)DEFAULT_PROFILE.getProperties());
/* 164 */     return Profileable.of(gameProfile);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\profiles\builder\XSkull.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */