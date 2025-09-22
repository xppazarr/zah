/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.economy.ZAuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.CurrencyProvider;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.currencies.providers.ItemProvider;
/*    */ import java.util.Optional;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class CommandAuctionGiveEconomyItem
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionGiveEconomyItem(ZAuctionPlugin paramZAuctionPlugin) {
/* 20 */     super(paramZAuctionPlugin);
/* 21 */     setDescription(Message.DESCRIPTION_GIVE);
/* 22 */     addSubCommand("giveeconomyitem");
/* 23 */     setConsoleCanUse(false);
/* 24 */     setPermission(Permission.ZAUCTIONHOUSE_GIVE_ECONOMY_ITEM);
/* 25 */     addRequireArg("economy name");
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 30 */     String str = argAsString(0);
/*    */     
/* 32 */     Optional<ZAuctionEconomy> optional = paramZAuctionPlugin.getEconomyManager().getEconomy(str);
/* 33 */     if (!optional.isPresent()) {
/* 34 */       message(this.sender, Message.ECONOMY_GIVE_ERROR_NOTFOUND, new Object[] { "%name%", str });
/* 35 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 38 */     ZAuctionEconomy zAuctionEconomy = optional.get();
/* 39 */     CurrencyProvider currencyProvider = zAuctionEconomy.getCurrencyProvider();
/* 40 */     if (!(currencyProvider instanceof ItemProvider)) {
/* 41 */       message(this.sender, Message.ECONOMY_GIVE_ERROR_TYPE, new Object[] { "%name%", str });
/* 42 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 45 */     ItemProvider itemProvider = (ItemProvider)currencyProvider;
/* 46 */     ItemStack itemStack = itemProvider.getItemStack(this.player);
/* 47 */     give(this.player, itemStack);
/* 48 */     message(this.sender, Message.ECONOMY_GIVE_SUCCESS, new Object[] { "%name%", str });
/*    */     
/* 50 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionGiveEconomyItem.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */