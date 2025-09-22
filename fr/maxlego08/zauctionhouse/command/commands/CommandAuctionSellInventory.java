/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*    */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.EconomyManager;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.Price;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.SellInventory;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import java.util.List;
/*    */ import java.util.Optional;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.OfflinePlayer;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ public class CommandAuctionSellInventory extends VCommand {
/*    */   public CommandAuctionSellInventory(ZAuctionPlugin paramZAuctionPlugin) {
/* 24 */     super(paramZAuctionPlugin);
/* 25 */     setDescription(Message.DESCRIPTION_SELL_INVENTORY);
/* 26 */     setPermission(Permission.ZAUCTIONHOUSE_SELL_INVENTORY);
/* 27 */     addSubCommand(new String[] { "sellinventory", "si", "vendreinventaire", "vi" });
/*    */     
/* 29 */     addRequireArg(Message.COMMAND_SELL_ARGUMENT_PRICE.getMessage(), (paramCommandSender, paramArrayOfString) -> (List)AuctionConfiguration.sellAutoCompletionPrice.stream().map(String::valueOf).collect(Collectors.toList()));
/* 30 */     addOptionalArg(Message.COMMAND_SELL_ARGUMENT_ECONOMY.getMessage(), (paramCommandSender, paramArrayOfString) -> (List)paramZAuctionPlugin.getEconomyManager().getEconomies().stream().map(AuctionEconomy::getName).collect(Collectors.toList()));
/*    */     
/* 32 */     setConsoleCanUse(false);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 38 */     if (!AuctionConfiguration.enablePlugin) {
/* 39 */       message(this.sender, Message.PLUGIN_DISABLE, new Object[0]);
/* 40 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 43 */     EconomyManager economyManager = paramZAuctionPlugin.getEconomyManager();
/* 44 */     String str = argAsString(1, AuctionConfiguration.defaultEconomy);
/* 45 */     Optional<AuctionEconomy> optional = economyManager.getEconomy(str);
/* 46 */     if (!optional.isPresent()) {
/* 47 */       message(this.sender, Message.ECONOMY_NOT_FOUND, new Object[] { "%name%", str });
/* 48 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 51 */     AuctionEconomy auctionEconomy = optional.get();
/* 52 */     Price price = getPriceAndEconomy(null, auctionEconomy);
/* 53 */     if (price.getType() != CommandType.SUCCESS) {
/* 54 */       return price.getType();
/*    */     }
/*    */     
/* 57 */     if (auctionEconomy.getPermission() != null && !this.player.hasPermission(auctionEconomy.getPermission())) {
/* 58 */       message((CommandSender)this.player, Message.ECONOMY_NO_PERMISSION, new Object[0]);
/* 59 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 62 */     long l = price.getPrice();
/*    */     
/* 64 */     SellInventory sellInventory = new SellInventory(l, auctionEconomy);
/* 65 */     paramZAuctionPlugin.getPlaceholderAPI().setSellInventory(this.player, sellInventory);
/*    */     
/* 67 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*    */       
/* 69 */       PlayerCache playerCache = paramZAuctionPlugin.getAuctionManager().getCache((OfflinePlayer)this.player);
/* 70 */       playerCache.setEconomy(auctionEconomy);
/* 71 */       playerCache.setSellPrice(l);
/* 72 */       paramZAuctionPlugin.getzMenuHandler().openSellInventory(this.player);
/*    */     }
/*    */     else {
/*    */       
/* 76 */       CommandObject commandObject = new CommandObject(getFirst(), getSubCommands(), this.inventoryManager.getInventory(InventoryName.SELL), getPermission(), getDescription());
/* 77 */       this.auctionManager.createSellInventory(this.player, l, auctionEconomy, (Command)commandObject);
/*    */     } 
/*    */     
/* 80 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionSellInventory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */