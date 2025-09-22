/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin.blacklist;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class CommandAuctionBlacklistAdd
/*    */   extends VCommand {
/*    */   public CommandAuctionBlacklistAdd(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setConsoleCanUse(false);
/* 15 */     setPermission(Permission.ZAUCTIONHOUSE_BLACKLIST_ADD);
/* 16 */     setDescription(Message.DESCRIPTION_BLACKLIST_ADD);
/*    */     
/* 18 */     addSubCommand("add");
/* 19 */     addRequireArg("player");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 25 */     OfflinePlayer offlinePlayer = argAsOfflinePlayer(0);
/* 26 */     this.auctionManager.blacklistPlayer(offlinePlayer);
/* 27 */     message(this.sender, Message.BLACKLIST_ADD, new Object[] { "%player%", offlinePlayer.getName() });
/*    */     
/* 29 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\blacklist\CommandAuctionBlacklistAdd.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */