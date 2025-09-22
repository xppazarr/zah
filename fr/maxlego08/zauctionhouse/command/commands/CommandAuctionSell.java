/*     */ package fr.maxlego08.zauctionhouse.command.commands;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.PlayerCache;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.EconomyManager;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.event.events.AuctionPreSellEvent;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Priority;
/*     */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.Price;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.UUID;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.OfflinePlayer;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ public class CommandAuctionSell extends VCommand {
/*     */   public CommandAuctionSell(ZAuctionPlugin paramZAuctionPlugin) {
/*  30 */     super(paramZAuctionPlugin);
/*  31 */     setDescription(Message.DESCRIPTION_SELL);
/*  32 */     setPermission(Permission.ZAUCTIONHOUSE_SELL);
/*  33 */     AuctionConfiguration.sellCommandAliases.forEach(this::addSubCommand);
/*     */     
/*  35 */     addRequireArg(Message.COMMAND_SELL_ARGUMENT_PRICE.getMessage(), (paramCommandSender, paramArrayOfString) -> (List)AuctionConfiguration.sellAutoCompletionPrice.stream().map(String::valueOf).collect(Collectors.toList()));
/*  36 */     addOptionalArg(Message.COMMAND_SELL_ARGUMENT_AMOUNT.getMessage(), (paramCommandSender, paramArrayOfString) -> {
/*     */           byte b; if (paramCommandSender instanceof Player) {
/*     */             Player player = (Player)paramCommandSender; ItemStack itemStack = player.getItemInHand(); if (itemStack != null) {
/*     */               b = itemStack.getMaxStackSize();
/*     */             } else {
/*     */               b = 64;
/*     */             } 
/*     */           } else {
/*     */             b = 64;
/*     */           }  return (List)AuctionConfiguration.sellAutoCompletionAmount.stream().filter(()).map(String::valueOf).collect(Collectors.toList());
/*     */         });
/*  47 */     addOptionalArg(Message.COMMAND_SELL_ARGUMENT_ECONOMY.getMessage(), (paramCommandSender, paramArrayOfString) -> (List)paramZAuctionPlugin.getEconomyManager().getEconomies().stream().map(AuctionEconomy::getName).collect(Collectors.toList()));
/*     */     
/*  49 */     setConsoleCanUse(false);
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandType prePerform(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/*  54 */     CommandType commandType = super.prePerform(paramZAuctionPlugin, paramCommandSender, paramArrayOfString);
/*  55 */     if (commandType.equals(CommandType.SYNTAX_ERROR)) {
/*  56 */       if (AuctionConfiguration.enableCustomSellMessage) {
/*  57 */         message(paramCommandSender, Message.COMMAND_SELL_CUSTOM_HELPING, new Object[0]);
/*  58 */         return CommandType.DEFAULT;
/*     */       } 
/*  60 */       return CommandType.SYNTAX_ERROR;
/*     */     } 
/*     */     
/*  63 */     return CommandType.SUCCESS;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/*  69 */     if (!AuctionConfiguration.enablePlugin) {
/*  70 */       message(this.sender, Message.PLUGIN_DISABLE, new Object[0]);
/*  71 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/*  74 */     Player player = this.player;
/*     */     
/*  76 */     ItemStack itemStack1 = getPlayer().getItemInHand().clone();
/*     */     
/*  78 */     if (itemStack1 == null || itemStack1.getAmount() == 0) {
/*  79 */       message(this.sender, Message.NO_HOLDING_ITEM, new Object[0]);
/*  80 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/*  83 */     int i = argAsInteger(1, itemStack1.getAmount());
/*  84 */     i = (i > itemStack1.getAmount()) ? itemStack1.getAmount() : ((i <= 0) ? 1 : i);
/*     */     
/*  86 */     EconomyManager economyManager = paramZAuctionPlugin.getEconomyManager();
/*  87 */     String str = argAsString(2, AuctionConfiguration.defaultEconomy);
/*  88 */     Optional<AuctionEconomy> optional = economyManager.getEconomy(str);
/*  89 */     if (!optional.isPresent()) {
/*  90 */       message(this.sender, Message.ECONOMY_NOT_FOUND, new Object[] { "%name%", str });
/*  91 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/*  94 */     AuctionEconomy auctionEconomy1 = optional.get();
/*  95 */     Price price = getPriceAndEconomy(itemStack1, auctionEconomy1);
/*  96 */     paramZAuctionPlugin.debug("Auction price: " + price.getPrice() + " -> Command result: " + price.getType().name() + " -> Economy: " + auctionEconomy1.getName());
/*     */     
/*  98 */     if (price.getType() != CommandType.SUCCESS) {
/*  99 */       return price.getType();
/*     */     }
/*     */     
/* 102 */     if (auctionEconomy1.getPermission() != null && !player.hasPermission(auctionEconomy1.getPermission())) {
/* 103 */       message((CommandSender)player, Message.ECONOMY_NO_PERMISSION, new Object[0]);
/* 104 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/* 107 */     long l1 = price.getPrice();
/*     */     
/* 109 */     i = (i < 0) ? 1 : Math.min(i, 64);
/* 110 */     itemStack1.setAmount(i);
/*     */     
/* 112 */     UUID uUID = this.auctionManager.getRandomUniqueId();
/* 113 */     long l2 = (AuctionConfiguration.sellTime != -1L) ? (System.currentTimeMillis() + this.auctionManager.getExpirationPerPermission((Permissible)this.player) * 1000L) : AuctionConfiguration.sellTime;
/*     */     
/* 115 */     AuctionPreSellEvent auctionPreSellEvent = new AuctionPreSellEvent(this.player, i, uUID, l2, auctionEconomy1, l1, itemStack1);
/* 116 */     auctionPreSellEvent.call();
/*     */     
/* 118 */     if (auctionPreSellEvent.isCancelled()) {
/* 119 */       return CommandType.DEFAULT;
/*     */     }
/*     */     
/* 122 */     long l3 = auctionPreSellEvent.getPrice();
/* 123 */     AuctionEconomy auctionEconomy2 = auctionPreSellEvent.getEconomy();
/* 124 */     ItemStack itemStack2 = auctionPreSellEvent.getItemStack();
/* 125 */     long l4 = auctionPreSellEvent.getExpiredAt();
/* 126 */     int j = i;
/*     */     
/* 128 */     Optional optional1 = this.auctionManager.getPriority((Permissible)this.player);
/* 129 */     int k = ((Integer)optional1.map(Priority::getPriority).orElse(Integer.valueOf(0))).intValue();
/*     */     
/* 131 */     ItemStack itemStack3 = player.getItemInHand().clone();
/*     */     
/* 133 */     if (itemStack3.getAmount() == 0) {
/* 134 */       message((CommandSender)player, Message.NO_HOLDING_ITEM, new Object[0]);
/* 135 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/* 138 */     PlayerCache playerCache = paramZAuctionPlugin.getStorage().getCache((OfflinePlayer)player);
/* 139 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY && AuctionConfiguration.enableSellEconomyInventory && !playerCache.isDisableSellConfirmation()) {
/* 140 */       paramZAuctionPlugin.getzMenuHandler().openSellEconomyChoose(player, l3, j);
/* 141 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/* 144 */     ZAuctionItem zAuctionItem = new ZAuctionItem(uUID, itemStack2, AuctionType.DEFAULT, l3, auctionEconomy2.getName(), player.getUniqueId(), l4, StorageType.STORAGE, player.getName(), k, paramZAuctionPlugin.getStorage().getServerName());
/* 145 */     this.auctionManager.sellItem((AuctionItem)zAuctionItem, itemStack2, player, l3, auctionEconomy2, j, AuctionType.DEFAULT);
/*     */     
/* 147 */     return CommandType.SUCCESS;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionSell.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */