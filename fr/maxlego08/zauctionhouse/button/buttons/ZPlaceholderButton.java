/*     */ package fr.maxlego08.zauctionhouse.button.buttons;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*     */ import fr.maxlego08.zauctionhouse.api.button.buttons.PlaceholderButton;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*     */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class ZPlaceholderButton
/*     */   extends ZPermissibleButton
/*     */   implements PlaceholderButton
/*     */ {
/*     */   private final PlaceholderAction action;
/*     */   private final String placeholder;
/*     */   private final String value;
/*     */   
/*     */   public ZPlaceholderButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, boolean paramBoolean2, SoundOption paramSoundOption) {
/*  34 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramBoolean2, paramSoundOption);
/*  35 */     this.action = paramPlaceholderAction;
/*  36 */     this.placeholder = paramString3;
/*  37 */     this.value = paramString4;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPlaceHolder() {
/*  42 */     return this.placeholder;
/*     */   }
/*     */ 
/*     */   
/*     */   public PlaceholderAction getAction() {
/*  47 */     return this.action;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPlaceHolder() {
/*  52 */     return (this.placeholder != null && this.action != null);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean hasPermission() {
/*  57 */     return (super.hasPermission() || hasPlaceHolder());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean checkPermission(Player paramPlayer) {
/*  63 */     if (!hasPlaceHolder())
/*     */     {
/*  65 */       return super.checkPermission(paramPlayer);
/*     */     }
/*     */ 
/*     */     
/*  69 */     String str = papi(getPlaceHolder(), paramPlayer);
/*     */     
/*  71 */     if (this.action.equals(PlaceholderAction.BOOLEAN))
/*     */ 
/*     */       
/*     */       try { 
/*  75 */         return Boolean.valueOf(str).booleanValue();
/*     */          }
/*     */       
/*  78 */       catch (Exception exception)
/*     */       
/*     */       { 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 124 */         return super.checkPermission(paramPlayer); }   if (this.action.isString()) { switch (this.action) { case EQUALS_STRING: return str.equals(String.valueOf(this.value));
/*     */         case EQUALSIGNORECASE_STRING: return str.equalsIgnoreCase(String.valueOf(this.value)); }  return super.checkPermission(paramPlayer); }  try { double d1 = Double.valueOf(str).doubleValue(); double d2 = Double.valueOf(this.value).doubleValue(); switch (this.action) { case LOWER: return (d1 < d2);
/*     */         case LOWER_OR_EQUAL: return (d1 <= d2);
/*     */         case SUPERIOR: return (d1 > d2);
/*     */         case SUPERIOR_OR_EQUAL: return (d1 >= d2);
/*     */         case EQUALS_STRING: return str.equals(String.valueOf(this.value));
/* 130 */         case EQUALSIGNORECASE_STRING: return str.equalsIgnoreCase(String.valueOf(this.value)); }  return super.checkPermission(paramPlayer); } catch (Exception exception) { if (AuctionConfiguration.enableDebugMode) { exception.printStackTrace(); Logger.info("Impossible de transformer la valeur " + str + " en double pour le placeholder " + this.placeholder); }  return super.checkPermission(paramPlayer); }  } public String getValue() { return this.value; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 140 */     return "ZPlaceholderButton [action=" + this.action + ", placeholder=" + this.placeholder + ", value=" + this.value + ", getElseButton()=" + 
/* 141 */       getElseButton() + ", getPermission()=" + getPermission() + ", hasElseButton()=" + 
/* 142 */       hasElseButton() + ", getMessage()=" + getMessage() + ", hasMessage()=" + hasMessage() + ", getItemStack()=" + 
/* 143 */       getItemStack() + ", getType()=" + getType() + ", getSlot()=" + getSlot() + ", getTmpSlot()=" + 
/* 144 */       getTmpSlot() + ", isClickable()=" + isClickable() + ", isPermament()=" + 
/* 145 */       isPermament() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZPlaceholderButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */