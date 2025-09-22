/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.AuctionButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*    */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*    */ import java.util.List;
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
/*    */ public class ZAuctionButton
/*    */   extends ZPlaceholderButton
/*    */   implements AuctionButton
/*    */ {
/*    */   private final List<Integer> slots;
/*    */   
/*    */   public ZAuctionButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, List<Integer> paramList, boolean paramBoolean2, SoundOption paramSoundOption) {
/* 33 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramBoolean2, paramSoundOption);
/*    */     
/* 35 */     this.slots = paramList;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<Integer> getSlots() {
/* 40 */     return this.slots;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZAuctionButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */