/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.AdminRemoveButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*    */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ZAdminRemoveButton
/*    */   extends ZPlaceholderButton
/*    */   implements AdminRemoveButton
/*    */ {
/*    */   private final boolean isSilent;
/*    */   private final boolean isForceRemove;
/*    */   
/*    */   public ZAdminRemoveButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, boolean paramBoolean2, SoundOption paramSoundOption, boolean paramBoolean3, boolean paramBoolean4) {
/* 35 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramBoolean2, paramSoundOption);
/*    */     
/* 37 */     this.isSilent = paramBoolean3;
/* 38 */     this.isForceRemove = paramBoolean4;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isSilent() {
/* 43 */     return this.isSilent;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isForceRemove() {
/* 48 */     return this.isForceRemove;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZAdminRemoveButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */