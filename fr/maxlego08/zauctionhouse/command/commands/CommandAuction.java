/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.command.commands.admin.CommandAuctionAdmin;
/*    */ import fr.maxlego08.zauctionhouse.command.commands.admin.CommandAuctionReload;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import org.bukkit.Bukkit;
/*    */ 
/*    */ public class CommandAuction
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuction(ZAuctionPlugin paramZAuctionPlugin) {
/* 20 */     super(paramZAuctionPlugin);
/*    */     
/* 22 */     setPermission(Permission.ZAUCTIONHOUSE_USE);
/* 23 */     setDescription(Message.DESCRIPTION_AUCTION);
/*    */     
/* 25 */     addSubCommand((VCommand)new CommandAuctionReload(paramZAuctionPlugin));
/* 26 */     addSubCommand(new CommandAuctionAddon(paramZAuctionPlugin));
/* 27 */     addSubCommand(new CommandAuctionVersion(paramZAuctionPlugin));
/*    */     
/* 29 */     addSubCommand(new CommandAuctionSell(paramZAuctionPlugin));
/* 30 */     addSubCommand(new CommandAuctionSellInventory(paramZAuctionPlugin));
/*    */     
/* 32 */     addSubCommand(new CommandAuctionHelp(paramZAuctionPlugin));
/* 33 */     addSubCommand(new CommandAuctionHistory(paramZAuctionPlugin));
/*    */     
/* 35 */     if (AuctionConfiguration.enableClaimMoney) {
/* 36 */       addSubCommand(new CommandAuctionClaim(paramZAuctionPlugin));
/*    */     }
/*    */     
/* 39 */     if (AuctionConfiguration.enableSearch) {
/* 40 */       addSubCommand(new CommandAuctionSearch(paramZAuctionPlugin));
/*    */     }
/*    */     
/* 43 */     if (AuctionConfiguration.enableCommandInventories) {
/*    */       
/* 45 */       addSubCommand(new CommandAuctionSub(paramZAuctionPlugin, "items", InventoryName.ITEMS));
/* 46 */       addSubCommand(new CommandAuctionSub(paramZAuctionPlugin, "categories", InventoryName.CATEGORIES));
/* 47 */       addSubCommand(new CommandAuctionSub(paramZAuctionPlugin, "expire", InventoryName.EXPIRE));
/* 48 */       addSubCommand(new CommandAuctionSub(paramZAuctionPlugin, "buying", InventoryName.BUYING));
/*    */     } 
/*    */ 
/*    */     
/* 52 */     if (Bukkit.getPluginManager().getPlugin("zAuctionHouseStats") == null) {
/* 53 */       addSubCommand(new CommandAuctionPrice(paramZAuctionPlugin));
/*    */     }
/*    */     
/* 56 */     addSubCommand((VCommand)new CommandAuctionAdmin(paramZAuctionPlugin));
/* 57 */     addSubCommand(new CommandAuctionSellConfirmationToggle(paramZAuctionPlugin));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 63 */     if (!AuctionConfiguration.enablePlugin) {
/* 64 */       message(this.sender, Message.PLUGIN_DISABLE, new Object[0]);
/* 65 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 68 */     if (AuctionConfiguration.disableCommands) {
/* 69 */       message(this.sender, Message.COMMAND_IS_DISABLE, new Object[0]);
/* 70 */       return CommandType.SUCCESS;
/*    */     } 
/*    */     
/* 73 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 74 */       paramZAuctionPlugin.getzMenuHandler().openAuction(this.player);
/* 75 */       return CommandType.SUCCESS;
/*    */     } 
/*    */     
/* 78 */     CommandObject commandObject = new CommandObject(getFirst(), getSubCommands(), this.inventoryManager.getInventory(InventoryName.AUCTION), getPermission(), getDescription());
/* 79 */     this.auctionManager.open(this.player, (Command)commandObject);
/*    */     
/* 81 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */