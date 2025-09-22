/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.ClaimButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*    */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ public class ZClaimButton
/*    */   extends ZPlaceholderButton
/*    */   implements ClaimButton
/*    */ {
/*    */   private final TransactionManager transactionManager;
/*    */   
/*    */   public ZClaimButton(TransactionManager paramTransactionManager, ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, boolean paramBoolean2, SoundOption paramSoundOption) {
/* 20 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramBoolean2, paramSoundOption);
/*    */     
/* 22 */     this.transactionManager = paramTransactionManager;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean hasPermission() {
/* 27 */     return true;
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean checkPermission(Player paramPlayer) {
/* 32 */     return this.transactionManager.needMoney((OfflinePlayer)paramPlayer);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZClaimButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */