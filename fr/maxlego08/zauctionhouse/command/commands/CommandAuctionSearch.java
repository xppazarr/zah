/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandAuctionSearch extends VCommand {
/*    */   public CommandAuctionSearch(ZAuctionPlugin paramZAuctionPlugin) {
/* 14 */     super(paramZAuctionPlugin);
/* 15 */     setDescription(Message.DESCRIPTION_SEARCH);
/* 16 */     setPermission(Permission.ZAUCTIONHOUSE_SEARCH);
/* 17 */     addSubCommand(new String[] { "search", "rechercher" });
/* 18 */     addRequireArg("word", (paramCommandSender, paramArrayOfString) -> paramZAuctionPlugin.getAuctionManager().getSearchElements());
/* 19 */     setExtendedArgs(true);
/* 20 */     setConsoleCanUse(false);
/*    */   }
/*    */ 
/*    */   
/*    */   public CommandType prePerform(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 25 */     CommandType commandType = super.prePerform(paramZAuctionPlugin, paramCommandSender, paramArrayOfString);
/* 26 */     if (commandType.equals(CommandType.SYNTAX_ERROR)) {
/* 27 */       if (AuctionConfiguration.enableCustomSearchMessage) {
/* 28 */         message(paramCommandSender, Message.COMMAND_SEARCH_CUSTOM_HELPING, new Object[0]);
/* 29 */         return CommandType.DEFAULT;
/*    */       } 
/* 31 */       return CommandType.SYNTAX_ERROR;
/*    */     } 
/*    */     
/* 34 */     return CommandType.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 40 */     StringBuilder stringBuilder = new StringBuilder();
/* 41 */     for (byte b = 1; b != this.args.length; b++) {
/* 42 */       stringBuilder.append(this.args[b]);
/* 43 */       if (b != this.args.length - 1) {
/* 44 */         stringBuilder.append(" ");
/*    */       }
/*    */     } 
/*    */     
/* 48 */     if (stringBuilder.length() == 0) {
/* 49 */       return CommandType.SYNTAX_ERROR;
/*    */     }
/*    */     
/* 52 */     String str = stringBuilder.toString();
/* 53 */     if (AuctionConfiguration.enableSearchTranslatedMaterial) {
/* 54 */       str = paramZAuctionPlugin.getTranslationManager().replaceValue(str.toLowerCase());
/*    */     }
/*    */     
/* 57 */     this.auctionManager.search(this.player, str);
/* 58 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */