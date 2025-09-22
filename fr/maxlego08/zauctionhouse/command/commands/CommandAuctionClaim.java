/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionClaim
/*    */   extends VCommand {
/*    */   public CommandAuctionClaim(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setDescription(Message.DESCRIPTION_CLAIM);
/* 15 */     setPermission(Permission.ZAUCTIONHOUSE_CLAIM);
/* 16 */     addSubCommand("claim");
/* 17 */     setConsoleCanUse(false);
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 22 */     TransactionManager transactionManager = paramZAuctionPlugin.getTransactionManager();
/* 23 */     transactionManager.claimMoney(this.player);
/* 24 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionClaim.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */