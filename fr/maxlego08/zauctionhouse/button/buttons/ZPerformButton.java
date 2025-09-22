/*    */ package fr.maxlego08.zauctionhouse.button.buttons;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.api.button.Button;
/*    */ import fr.maxlego08.zauctionhouse.api.button.buttons.PerformButton;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ButtonType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.PlaceholderAction;
/*    */ import fr.maxlego08.zauctionhouse.api.sound.SoundOption;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
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
/*    */ public class ZPerformButton
/*    */   extends ZPlaceholderButton
/*    */   implements PerformButton
/*    */ {
/*    */   private final List<String> commands;
/*    */   private final List<String> consoleCommands;
/*    */   
/*    */   public ZPerformButton(ButtonType paramButtonType, ItemStack paramItemStack, int paramInt, String paramString1, String paramString2, Button paramButton, boolean paramBoolean1, PlaceholderAction paramPlaceholderAction, String paramString3, String paramString4, List<String> paramList1, List<String> paramList2, boolean paramBoolean2, SoundOption paramSoundOption) {
/* 38 */     super(paramButtonType, paramItemStack, paramInt, paramString1, paramString2, paramButton, paramBoolean1, paramPlaceholderAction, paramString3, paramString4, paramBoolean2, paramSoundOption);
/*    */     
/* 40 */     this.commands = paramList1;
/* 41 */     this.consoleCommands = paramList2;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> getCommands() {
/* 46 */     return this.commands;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void execute(Player paramPlayer) {
/* 52 */     if (!checkPermission(paramPlayer)) {
/*    */       return;
/*    */     }
/* 55 */     papi(new ArrayList<>(this.commands), paramPlayer)
/* 56 */       .forEach(paramString -> paramPlayer.performCommand(paramString.replace("%player%", paramPlayer.getName())));
/*    */     
/* 58 */     papi(new ArrayList<>(this.consoleCommands), paramPlayer).forEach(paramString -> Bukkit.dispatchCommand((CommandSender)Bukkit.getConsoleSender(), paramString.replace("%player%", paramPlayer.getName())));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> getConsoleCommands() {
/* 64 */     return this.consoleCommands;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\button\buttons\ZPerformButton.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */