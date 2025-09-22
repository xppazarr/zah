/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.players.ActionBar;
/*    */ import java.util.Optional;
/*    */ import org.bukkit.inventory.ItemStack;
/*    */ import org.bukkit.permissions.Permissible;
/*    */ 
/*    */ public class CommandAuctionTestPerf extends VCommand {
/*    */   public CommandAuctionTestPerf(ZAuctionPlugin paramZAuctionPlugin) {
/* 22 */     super(paramZAuctionPlugin);
/* 23 */     setDescription(Message.DESCRIPTION_TEST_PERF);
/* 24 */     addSubCommand("testperf");
/* 25 */     setConsoleCanUse(false);
/* 26 */     setPermission(Permission.ZAUCTIONHOUSE_TEST_PERF);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 32 */     String str = AuctionConfiguration.defaultEconomy;
/* 33 */     Optional<AuctionEconomy> optional = this.plugin.getEconomyManager().getEconomy(str);
/*    */ 
/*    */     
/* 36 */     if (!optional.isPresent()) {
/* 37 */       message(this.sender, Message.ECONOMY_NOT_FOUND, new Object[] { "%name%", str });
/* 38 */       return CommandType.DEFAULT;
/*    */     } 
/*    */     
/* 41 */     AuctionEconomy auctionEconomy = optional.get();
/* 42 */     long l = (AuctionConfiguration.sellTime != -1L) ? (System.currentTimeMillis() + this.auctionManager.getExpirationPerPermission((Permissible)this.player) * 1000L) : AuctionConfiguration.sellTime;
/*    */     
/* 44 */     message(this.sender, "§aStart create new items.", new Object[0]);
/* 45 */     for (byte b = 0; b != 'Ϩ'; b++) {
/* 46 */       ActionBar.sendActionBar(this.player, "§aItem §7" + b + "§8/§71000");
/*    */       
/* 48 */       ItemStack itemStack = paramZAuctionPlugin.getCategoryManager().getRandomItemStacks();
/*    */       
/* 50 */       ZAuctionItem zAuctionItem = new ZAuctionItem(this.player.getUniqueId(), itemStack, AuctionType.DEFAULT, getNumberBetween(100, 100000), auctionEconomy.getName(), this.player.getUniqueId(), l, StorageType.STORAGE, this.player.getName(), 0, paramZAuctionPlugin.getStorage().getServerName());
/* 51 */       paramZAuctionPlugin.getStorage().saveItem((AuctionPlugin)paramZAuctionPlugin, (AuctionItem)zAuctionItem, StorageType.STORAGE);
/*    */     } 
/*    */     
/* 54 */     ActionBar.sendActionBar(this.player, "§aItem §71000§8/§71000");
/* 55 */     message(this.sender, "§aYou just created 1000 items.", new Object[0]);
/*    */     
/* 57 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionTestPerf.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */