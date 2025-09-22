/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionReload
/*    */   extends VCommand {
/*    */   public CommandAuctionReload(ZAuctionPlugin paramZAuctionPlugin) {
/* 12 */     super(paramZAuctionPlugin);
/* 13 */     setPermission(Permission.ZAUCTIONHOUSE_RELOAD);
/* 14 */     addSubCommand(new String[] { "reload", "rl" });
/* 15 */     setDescription(Message.DESCRIPTION_RELOAD);
/* 16 */     this.bypassReady = true;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 22 */     paramZAuctionPlugin.disable();
/*    */     
/* 24 */     paramZAuctionPlugin.getInventoryManager().close();
/* 25 */     paramZAuctionPlugin.getPriceManager().load(paramZAuctionPlugin.getPersist());
/* 26 */     paramZAuctionPlugin.getEconomyManager().loadEconomies();
/*    */     
/* 28 */     this.auctionManager.reload();
/* 29 */     paramZAuctionPlugin.enable();
/* 30 */     message(this.sender, Message.COMMAND_RELOAD, new Object[0]);
/*    */     
/* 32 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionReload.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */