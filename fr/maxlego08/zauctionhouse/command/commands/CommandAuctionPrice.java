/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionPrice
/*    */   extends VCommand {
/*    */   public CommandAuctionPrice(ZAuctionPlugin paramZAuctionPlugin) {
/* 12 */     super(paramZAuctionPlugin);
/* 13 */     addSubCommand("price");
/* 14 */     setPermission(Permission.ZAUCTIONHOUSE_PRICE);
/* 15 */     setDescription(Message.DESCRIPTION_PRICE);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 21 */     message(this.sender, "", new Object[0]);
/* 22 */     message(this.sender, "§fTo access all zAuctionHouse statistics, you must use the zAuctionHouseStats addon.", new Object[0]);
/* 23 */     message(this.sender, "§7This addon adds dozens of placeholders, orders and additional inventory.", new Object[0]);
/* 24 */     message(this.sender, "§7Buy it for 5€ here: https://groupez.dev/resources/319", new Object[0]);
/* 25 */     message(this.sender, "", new Object[0]);
/*    */     
/* 27 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionPrice.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */