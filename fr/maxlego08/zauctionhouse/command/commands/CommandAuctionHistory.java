/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.HistoryType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandAuctionHistory
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionHistory(ZAuctionPlugin paramZAuctionPlugin) {
/* 19 */     super(paramZAuctionPlugin);
/* 20 */     setDescription(Message.DESCRIPTION_HISTORY);
/* 21 */     setPermission(Permission.ZAUCTIONHOUSE_HISTORY);
/* 22 */     addSubCommand("history");
/* 23 */     addOptionalArg("page");
/* 24 */     addOptionalArg("purchase/sale/both");
/* 25 */     setConsoleCanUse(false);
/* 26 */     setTabCompletor();
/* 27 */     this.runAsync = true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 32 */     int i = argAsInteger(0, 1);
/* 33 */     HistoryType historyType = HistoryType.form(argAsString(1, null));
/* 34 */     this.auctionManager.showHistory(this.sender, (OfflinePlayer)this.player, i, historyType);
/* 35 */     return CommandType.SUCCESS;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> toTab(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 40 */     if (paramArrayOfString.length == 3) {
/* 41 */       String str = paramArrayOfString[2];
/* 42 */       return generateList((List)Arrays.<HistoryType>stream(HistoryType.values()).map(paramHistoryType -> (paramHistoryType == HistoryType.BOTH) ? Message.BOTH.getMessage() : ((paramHistoryType == HistoryType.SALE) ? Message.SALE.getMessage() : Message.PURCHASE.getMessage()))
/*    */ 
/*    */ 
/*    */           
/* 46 */           .collect(Collectors.toList()), str);
/*    */     } 
/* 48 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionHistory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */