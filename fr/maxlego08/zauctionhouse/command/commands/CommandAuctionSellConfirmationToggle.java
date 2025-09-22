/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class CommandAuctionSellConfirmationToggle extends VCommand {
/*    */   public CommandAuctionSellConfirmationToggle(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setDescription(Message.DESCRIPTION_SELL_CONFIRMATION_TOGGLE);
/* 15 */     setPermission(Permission.ZAUCTIONHOUSE_SELL_CONFIRMATION_TOGGLE);
/* 16 */     addSubCommand("sell-confirmation-toggle");
/* 17 */     setConsoleCanUse(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 23 */     PlayerCache playerCache = paramZAuctionPlugin.getAuctionManager().getCache((OfflinePlayer)this.player);
/* 24 */     paramZAuctionPlugin.getAuctionManager().setDisableSellConfirmation(this.player, !playerCache.isDisableSellConfirmation());
/*    */     
/* 26 */     message(this.sender, playerCache.isDisableSellConfirmation() ? Message.SELL_CONFIRMATION_TOGGLE_ENABLED : Message.SELL_CONFIRMATION_TOGGLE_DISABLED, new Object[0]);
/*    */     
/* 28 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionSellConfirmationToggle.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */