/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.InventoryButton;
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
/*    */ public class ZInventoryButton
/*    */   extends ZPlaceholderButton
/*    */   implements InventoryButton
/*    */ {
/*    */   protected String inventory;
/*    */   protected Inventory inventoryInterface;
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ZInventoryButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, String paramString5, Inventory paramInventory, ZAuctionPlugin paramZAuctionPlugin, boolean paramBoolean2, SoundOption paramSoundOption) {
/* 37 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramBoolean2, paramSoundOption);
/*    */     
/* 39 */     this.inventory = paramString5;
/* 40 */     this.inventoryInterface = paramInventory;
/* 41 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */   
/*    */   public Inventory getInventory() {
/* 46 */     if (getType() == ButtonType.BACK)
/* 47 */       return this.inventoryInterface; 
/* 48 */     return (this.inventoryInterface == null) ? (this.inventoryInterface = this.plugin.getInventories().getInventory(this.inventory, true)) : 
/* 49 */       this.inventoryInterface;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String toString() {
/* 59 */     return "ZInventoryButton [inventory=" + this.inventory + ", inventoryInterface=" + this.inventoryInterface + "]";
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZInventoryButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */