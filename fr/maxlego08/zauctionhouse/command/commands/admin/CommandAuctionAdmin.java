/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.command.commands.admin.blacklist.CommandAuctionBlacklist;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionAdmin
/*    */   extends VCommand {
/*    */   public CommandAuctionAdmin(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setPermission(Permission.ZAUCTIONHOUSE_ADMIN);
/* 15 */     setDescription(Message.DESCRIPTION_ADMIN);
/* 16 */     addSubCommand("admin");
/* 17 */     addSubCommand((VCommand)new CommandAuctionBlacklist(paramZAuctionPlugin));
/* 18 */     addSubCommand(new CommandAuctionConvert(paramZAuctionPlugin));
/* 19 */     addSubCommand(new CommandAuctionPurge(paramZAuctionPlugin));
/* 20 */     addSubCommand(new CommandAuctionReload(paramZAuctionPlugin));
/* 21 */     addSubCommand(new CommandAuctionTransaction(paramZAuctionPlugin));
/* 22 */     addSubCommand(new CommandAuctionGiveEconomyItem(paramZAuctionPlugin));
/* 23 */     addSubCommand(new CommandAuctionOpen(paramZAuctionPlugin));
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 28 */     syntaxMessage(this.sender);
/* 29 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionAdmin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */