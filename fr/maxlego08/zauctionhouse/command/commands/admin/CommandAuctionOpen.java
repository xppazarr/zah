/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ 
/*    */ public class CommandAuctionOpen
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionOpen(ZAuctionPlugin paramZAuctionPlugin) {
/* 19 */     super(paramZAuctionPlugin);
/* 20 */     setDescription(Message.DESCRIPTION_OPEN);
/* 21 */     addSubCommand(new String[] { "open", "ouvrir" });
/* 22 */     setPermission(Permission.ZAUCTIONHOUSE_OPEN);
/* 23 */     addRequireArg("player");
/*    */   }
/*    */ 
/*    */   
/*    */   public CommandType prePerform(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 28 */     CommandType commandType = super.prePerform(paramZAuctionPlugin, paramCommandSender, paramArrayOfString);
/* 29 */     if (commandType.equals(CommandType.SYNTAX_ERROR)) {
/* 30 */       if (AuctionConfiguration.enableCustomOpenMessage) {
/* 31 */         message(paramCommandSender, Message.COMMAND_OPEN_CUSTOM_HELPING, new Object[0]);
/* 32 */         return CommandType.DEFAULT;
/*    */       } 
/* 34 */       return CommandType.SYNTAX_ERROR;
/*    */     } 
/*    */     
/* 37 */     return CommandType.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 43 */     Player player = argAsPlayer(0, null);
/* 44 */     if (player == null) {
/* 45 */       return CommandType.SYNTAX_ERROR;
/*    */     }
/*    */     
/* 48 */     if (!AuctionConfiguration.enablePlugin) {
/* 49 */       message(this.sender, Message.PLUGIN_DISABLE, new Object[0]);
/* 50 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 53 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 54 */       paramZAuctionPlugin.getzMenuHandler().openAuction(player);
/* 55 */       return CommandType.SUCCESS;
/*    */     } 
/*    */     
/* 58 */     CommandObject commandObject = new CommandObject(getFirst(), getSubCommands(), this.inventoryManager.getInventory(InventoryName.AUCTION), getPermission(), getDescription());
/* 59 */     this.auctionManager.open(player, (Command)commandObject);
/*    */     
/* 61 */     message(this.sender, Message.FORCE_OPEN, new Object[] { "%player%", player.getName() });
/*    */     
/* 63 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionOpen.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */