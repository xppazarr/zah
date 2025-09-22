/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.transaction.TransactionManager;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionPurge
/*    */   extends VCommand {
/*    */   public CommandAuctionPurge(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setDescription(Message.DESCRIPTION_PURGE);
/* 15 */     addSubCommand("purge");
/* 16 */     setPermission(Permission.ZAUCTIONHOUSE_PURGE);
/* 17 */     addRequireArg("days");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 23 */     long l = argAsLong(0);
/* 24 */     TransactionManager transactionManager = paramZAuctionPlugin.getTransactionManager();
/* 25 */     transactionManager.purge(this.sender, l);
/*    */     
/* 27 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionPurge.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */