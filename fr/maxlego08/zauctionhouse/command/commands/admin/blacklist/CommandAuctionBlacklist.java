/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin.blacklist;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.BlacklistPlayers;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ 
/*    */ 
/*    */ public class CommandAuctionBlacklist
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionBlacklist(ZAuctionPlugin paramZAuctionPlugin) {
/* 19 */     super(paramZAuctionPlugin);
/* 20 */     setConsoleCanUse(false);
/* 21 */     setPermission(Permission.ZAUCTIONHOUSE_BLACKLIST);
/* 22 */     setDescription(Message.DESCRIPTION_BLACKLIST_LIST);
/* 23 */     addSubCommand("blacklist");
/* 24 */     addSubCommand("bl");
/* 25 */     addSubCommand(new CommandAuctionBlacklistAdd(paramZAuctionPlugin));
/* 26 */     addSubCommand(new CommandAuctionBlacklistRemove(paramZAuctionPlugin));
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 32 */     List list = BlacklistPlayers.blacklistPlayers;
/* 33 */     if (list.isEmpty()) {
/*    */       
/* 35 */       message(this.sender, Message.BLACKLIST_EMPTY, new Object[0]);
/*    */     } else {
/* 37 */       message(this.sender, Message.BLACKLIST_LIST, new Object[] { "%players%", toList((List)list.stream().map(Bukkit::getOfflinePlayer).map(OfflinePlayer::getName).collect(Collectors.toList()), "§7", "§f") });
/*    */     } 
/*    */     
/* 40 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\blacklist\CommandAuctionBlacklist.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */