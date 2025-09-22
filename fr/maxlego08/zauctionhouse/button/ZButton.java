/*     */ package fr.maxlego08.zauctionhouse.button;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*     */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZButton
/*     */   extends ZUtils
/*     */   implements Button
/*     */ {
/*     */   private final ButtonType type;
/*     */   private final ItemStack itemStack;
/*     */   private final int slot;
/*     */   private final SoundOption sound;
/*     */   private int tmpSlot;
/*     */   private boolean isPermanent;
/*     */   private boolean closeInventory;
/*     */   
/*     */   public ZButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, boolean paramBoolean, SoundOption paramSoundOption) {
/*  29 */     this.type = paramButtonType;
/*  30 */     this.itemStack = paramItemStack;
/*  31 */     this.slot = paramInt;
/*  32 */     this.isPermanent = paramBoolean;
/*  33 */     this.sound = paramSoundOption;
/*     */   }
/*     */   
/*     */   public void setCloseInventory(boolean paramBoolean) {
/*  37 */     this.closeInventory = paramBoolean;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getItemStack() {
/*  42 */     return this.itemStack;
/*     */   }
/*     */ 
/*     */   
/*     */   public ButtonType getType() {
/*  47 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlot() {
/*  52 */     return this.slot;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setTmpSlot(int paramInt) {
/*  57 */     if (isPermament()) {
/*  58 */       this.tmpSlot = this.slot;
/*     */     } else {
/*  60 */       this.tmpSlot = paramInt;
/*     */     } 
/*     */   }
/*     */   
/*     */   public int getTmpSlot() {
/*  65 */     return this.tmpSlot;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  75 */     return "ZButton [type=" + this.type + ", itemStack=" + this.itemStack + ", slot=" + this.slot + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public <T extends Button> T toButton(Class<T> paramClass) {
/*  81 */     return (T)this;
/*     */   }
/*     */ 
/*     */   
/*     */   public ItemStack getCustomItemStack(Player paramPlayer) {
/*  86 */     if (this.itemStack == null)
/*  87 */       return null; 
/*  88 */     ItemStack itemStack = this.itemStack.clone();
/*  89 */     return playerHead(papi(itemStack, paramPlayer), (OfflinePlayer)paramPlayer);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClickable() {
/*  94 */     return this.type.isClickable();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isPermament() {
/*  99 */     return this.isPermanent;
/*     */   }
/*     */ 
/*     */   
/*     */   public SoundOption getSound() {
/* 104 */     return this.sound;
/*     */   }
/*     */ 
/*     */   
/*     */   public void playSound(Entity paramEntity) {
/* 109 */     if (this.sound != null) {
/* 110 */       this.sound.play(paramEntity);
/*     */     }
/*     */   }
/*     */   
/*     */   public boolean isDisableEvent() {
/* 115 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean closeInventory() {
/* 120 */     return this.closeInventory;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\ZButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */