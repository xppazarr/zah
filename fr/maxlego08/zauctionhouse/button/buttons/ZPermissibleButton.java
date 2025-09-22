/*     */ package fr.maxlego08.zauctionhouse.button.buttons;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PermissibleButton;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*     */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*     */ import fr.maxlego08.zauctionhouse.button.ZButton;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZPermissibleButton
/*     */   extends ZButton
/*     */   implements PermissibleButton
/*     */ {
/*     */   private final String permission;
/*     */   private final String message;
/*     */   private final Button elseButton;
/*     */   private final boolean glowIfCheck;
/*     */   
/*     */   public ZPermissibleButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, boolean paramBoolean2, SoundOption paramSoundOption) {
/*  28 */     super(paramButtonType, paramItemStack, paramInt, paramBoolean1, paramSoundOption);
/*  29 */     this.permission = paramString1;
/*  30 */     this.elseButton = paramButton;
/*  31 */     this.message = color(paramString2);
/*  32 */     this.glowIfCheck = paramBoolean2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ZPermissibleButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, boolean paramBoolean, SoundOption paramSoundOption) {
/*  43 */     super(paramButtonType, paramItemStack, paramInt, paramBoolean, paramSoundOption);
/*  44 */     this.permission = null;
/*  45 */     this.elseButton = null;
/*  46 */     this.message = null;
/*  47 */     this.glowIfCheck = false;
/*     */   }
/*     */ 
/*     */   
/*     */   public Button getElseButton() {
/*  52 */     return this.elseButton;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPermission() {
/*  57 */     return this.permission;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission() {
/*  62 */     return (this.permission != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasElseButton() {
/*  67 */     return (this.elseButton != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public String getMessage() {
/*  72 */     return this.message;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasMessage() {
/*  77 */     return (this.message != null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  87 */     return "ZPermissibleButton [permission=" + this.permission + ", message=" + this.message + ", elseButton=" + this.elseButton + "] => " + super
/*  88 */       .toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean checkPermission(Player paramPlayer) {
/*  93 */     return (getPermission() == null || paramPlayer.hasPermission(getPermission()));
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean needToGlow() {
/*  98 */     return this.glowIfCheck;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCustomItemStack(Player paramPlayer) {
/* 103 */     ItemStack itemStack = super.getCustomItemStack(paramPlayer);
/* 104 */     if (checkPermission(paramPlayer) && needToGlow())
/* 105 */       glow(itemStack); 
/* 106 */     return itemStack;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZPermissibleButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */