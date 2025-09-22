/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.xseries.base;
/*     */ 
/*     */ import java.util.Arrays;
/*     */ import java.util.Locale;
/*     */ import java.util.stream.Collectors;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public interface XBase<XForm extends XBase<XForm, BukkitForm>, BukkitForm>
/*     */ {
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   default String friendlyName() {
/*  87 */     return Arrays.<String>stream(name().split("_"))
/*  88 */       .map(t -> t.charAt(0) + t.substring(1).toLowerCase(Locale.ENGLISH))
/*  89 */       .collect(Collectors.joining(" "));
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
/*     */   @Contract(pure = true)
/*     */   default boolean isSupported() {
/* 110 */     return (get() != null);
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
/*     */   @Contract(pure = true)
/*     */   default XForm or(XForm other) {
/* 127 */     return isSupported() ? (XForm)this : other;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   @Contract(pure = true)
/*     */   String name();
/*     */   
/*     */   @Internal
/*     */   @Contract(pure = true)
/*     */   String[] getNames();
/*     */   
/*     */   @Nullable
/*     */   @Contract(pure = true)
/*     */   BukkitForm get();
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\xseries\base\XBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */