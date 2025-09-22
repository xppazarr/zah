/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin.blacklist;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ public class CommandAuctionBlacklistRemove
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionBlacklistRemove(ZAuctionPlugin paramZAuctionPlugin) {
/* 14 */     super(paramZAuctionPlugin);
/* 15 */     setConsoleCanUse(false);
/* 16 */     setPermission(Permission.ZAUCTIONHOUSE_BLACKLIST_REMOVE);
/* 17 */     setDescription(Message.DESCRIPTION_BLACKLIST_REMOVE);
/*    */     
/* 19 */     addSubCommand("remove");
/*    */     
/* 21 */     addRequireArg("player");
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 27 */     OfflinePlayer offlinePlayer = argAsOfflinePlayer(0);
/* 28 */     this.auctionManager.unBlacklistPlayer(offlinePlayer);
/* 29 */     message(this.sender, Message.BLACKLIST_REMOVE, new Object[] { "%player%", offlinePlayer.getName() });
/*    */     
/* 31 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\blacklist\CommandAuctionBlacklistRemove.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */