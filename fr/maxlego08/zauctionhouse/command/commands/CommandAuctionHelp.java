/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionHelp
/*    */   extends VCommand {
/*    */   public CommandAuctionHelp(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     addSubCommand(new String[] { "help", "aide", "?" });
/* 15 */     setPermission(Permission.ZAUCTIONHOUSE_HELP);
/* 16 */     setDescription(Message.DESCRIPTION_HELP);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 22 */     if (AuctionConfiguration.enableCustomHelpMessage) {
/* 23 */       Message message = Message.COMMAND_HELP_CUSTOM;
/* 24 */       if (message.isMessage()) { message.getMessages().forEach(paramString -> messageWO(this.sender, paramString, new Object[0])); }
/* 25 */       else { messageWO(this.sender, message.getMessage(), new Object[0]); }
/* 26 */        return CommandType.SUCCESS;
/*    */     } 
/*    */     
/* 29 */     this.parent.syntaxMessage(this.sender);
/* 30 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionHelp.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */