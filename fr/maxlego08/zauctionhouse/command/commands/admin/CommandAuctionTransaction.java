/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
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
/*    */ public class CommandAuctionTransaction
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionTransaction(ZAuctionPlugin paramZAuctionPlugin) {
/* 19 */     super(paramZAuctionPlugin);
/* 20 */     setDescription(Message.DESCRIPTION_TRANSACTION);
/* 21 */     setPermission(Permission.ZAUCTIONHOUSE_TRANSACTION);
/* 22 */     addSubCommand("transaction");
/* 23 */     addRequireArg("player");
/* 24 */     addOptionalArg("page");
/* 25 */     addOptionalArg("purchase/sale/both");
/* 26 */     setTabCompletor();
/* 27 */     this.runAsync = true;
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 32 */     OfflinePlayer offlinePlayer = argAsOfflinePlayer(0);
/* 33 */     int i = argAsInteger(1, 1);
/* 34 */     HistoryType historyType = HistoryType.form(argAsString(2, null));
/* 35 */     this.auctionManager.showHistory(this.sender, offlinePlayer, i, historyType);
/* 36 */     return CommandType.SUCCESS;
/*    */   }
/*    */ 
/*    */   
/*    */   public List<String> toTab(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 41 */     if (paramArrayOfString.length == 4) {
/* 42 */       String str = paramArrayOfString[3];
/* 43 */       return generateList((List)Arrays.<HistoryType>stream(HistoryType.values()).map(paramHistoryType -> (paramHistoryType == HistoryType.BOTH) ? Message.BOTH.getMessage() : ((paramHistoryType == HistoryType.SALE) ? Message.SALE.getMessage() : Message.PURCHASE.getMessage()))
/*    */ 
/*    */ 
/*    */           
/* 47 */           .collect(Collectors.toList()), str);
/*    */     } 
/* 49 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionTransaction.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */