/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.BackButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*    */ import fr.maxlego08.zauctionhouse.api.inventory.Inventory;
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
/*    */ public class ZBackButton
/*    */   extends ZInventoryButton
/*    */   implements BackButton
/*    */ {
/*    */   public ZBackButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, String paramString5, Inventory paramInventory, ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean2, SoundOption paramSoundOption) {
/* 33 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramString5, paramInventory, paramZAuctionPlugin, paramBoolean2, paramSoundOption);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void setBackInventory(Inventory paramInventory) {
/* 40 */     this.inventoryInterface = paramInventory;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 50 */     return "ZBackButton [inventory=" + this.inventory + ", inventoryInterface=" + this.inventoryInterface + "]";
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZBackButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */