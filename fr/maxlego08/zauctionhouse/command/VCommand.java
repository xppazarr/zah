/*     */ package fr.maxlego08.zauctionhouse.command;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*     */ import fr.maxlego08.zauctionhouse.api.economy.AuctionEconomy;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*     */ import fr.maxlego08.zauctionhouse.api.inventory.InventoryManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.NumberFormatSell;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.PapiUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.Price;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.Arguments;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CollectionBiConsumer;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.Tab;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.plugins.Plugins;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.HashMap;
/*     */ import java.util.List;
/*     */ import java.util.Map;
/*     */ import java.util.Optional;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class VCommand
/*     */   extends Arguments
/*     */ {
/*     */   protected final ZAuctionPlugin plugin;
/*  35 */   private final List<String> subCommands = new ArrayList<>();
/*  36 */   private final List<String> requireArgs = new ArrayList<>();
/*  37 */   private final List<String> optionalArgs = new ArrayList<>();
/*     */   
/*     */   protected VCommand parent;
/*     */   
/*     */   protected boolean bypassReady = false;
/*     */   
/*  43 */   protected List<VCommand> subVCommands = new ArrayList<>();
/*     */   
/*     */   protected InventoryManager inventoryManager;
/*     */   
/*     */   protected AuctionManager auctionManager;
/*     */   
/*     */   protected boolean runAsync = false;
/*     */   protected CommandSender sender;
/*     */   protected Player player;
/*  52 */   protected Map<Integer, CollectionBiConsumer> tabCompletions = new HashMap<>();
/*     */ 
/*     */   
/*     */   private String permission;
/*     */ 
/*     */   
/*     */   private boolean consoleCanUse = true;
/*     */ 
/*     */   
/*     */   private boolean ignoreParent = false;
/*     */ 
/*     */   
/*     */   private boolean ignoreArgs = false;
/*     */ 
/*     */   
/*     */   private boolean extendedArgs = false;
/*     */ 
/*     */   
/*  70 */   private CommandType tabCompleter = CommandType.DEFAULT;
/*     */   
/*     */   private String syntax;
/*     */   
/*     */   private String description;
/*     */   
/*     */   private int argsMinLength;
/*     */   
/*     */   private int argsMaxLength;
/*     */   
/*     */   public VCommand(ZAuctionPlugin paramZAuctionPlugin) {
/*  81 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Optional<CollectionBiConsumer> getCompletionAt(int paramInt) {
/*  89 */     return Optional.ofNullable(this.tabCompletions.getOrDefault(Integer.valueOf(paramInt), null));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getPermission() {
/*  98 */     return this.permission;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setPermission(String paramString) {
/* 105 */     this.permission = paramString;
/* 106 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setPermission(Permission paramPermission) {
/* 113 */     this.permission = paramPermission.getPermission();
/* 114 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCommand getParent() {
/* 121 */     return this.parent;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setParent(VCommand paramVCommand) {
/* 128 */     this.parent = paramVCommand;
/* 129 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> getSubCommands() {
/* 136 */     return this.subCommands;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isConsoleCanUse() {
/* 143 */     return this.consoleCanUse;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setConsoleCanUse(boolean paramBoolean) {
/* 150 */     this.consoleCanUse = paramBoolean;
/* 151 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isIgnoreParent() {
/* 158 */     return this.ignoreParent;
/*     */   }
/*     */   
/*     */   public void setIgnoreParent(boolean paramBoolean) {
/* 162 */     this.ignoreParent = paramBoolean;
/*     */   }
/*     */   
/*     */   public CommandSender getSender() {
/* 166 */     return this.sender;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArgsMinLength() {
/* 173 */     return this.argsMinLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getArgsMaxLength() {
/* 180 */     return this.argsMaxLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer() {
/* 187 */     return this.player;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSyntax() {
/* 200 */     if (this.syntax == null) {
/* 201 */       this.syntax = generateDefaultSyntax("");
/*     */     }
/* 203 */     return this.syntax;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setSyntax(String paramString) {
/* 210 */     this.syntax = paramString;
/* 211 */     return this;
/*     */   }
/*     */   
/*     */   public boolean isIgnoreArgs() {
/* 215 */     return this.ignoreArgs;
/*     */   }
/*     */   
/*     */   public void setIgnoreArgs(boolean paramBoolean) {
/* 219 */     this.ignoreArgs = paramBoolean;
/*     */   }
/*     */   
/*     */   public String getDescription() {
/* 223 */     return (this.description == null) ? "no description" : this.description;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setDescription(String paramString) {
/* 233 */     this.description = paramString;
/* 234 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected VCommand setDescription(Message paramMessage) {
/* 244 */     this.description = paramMessage.getMessage();
/* 245 */     return this;
/*     */   }
/*     */   
/*     */   public CommandType getTabCompleter() {
/* 249 */     return this.tabCompleter;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setTabCompletor() {
/* 256 */     this.tabCompleter = CommandType.SUCCESS;
/*     */   }
/*     */   
/*     */   public void setExtendedArgs(boolean paramBoolean) {
/* 260 */     this.extendedArgs = paramBoolean;
/*     */   }
/*     */   
/*     */   protected VCommand onlyPlayers() {
/* 264 */     this.consoleCanUse = false;
/* 265 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRequireArg(String paramString) {
/* 272 */     this.requireArgs.add(paramString);
/* 273 */     this.ignoreParent = (this.parent == null);
/* 274 */     this.ignoreArgs = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addRequireArg(String paramString, CollectionBiConsumer paramCollectionBiConsumer) {
/* 281 */     addRequireArg(paramString);
/* 282 */     int i = this.requireArgs.size();
/* 283 */     addCompletion(i - 1, paramCollectionBiConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOptionalArg(String paramString, CollectionBiConsumer paramCollectionBiConsumer) {
/* 292 */     addOptionalArg(paramString);
/* 293 */     int i = this.requireArgs.size() + this.optionalArgs.size();
/* 294 */     addCompletion(i - 1, paramCollectionBiConsumer);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void addOptionalArg(String paramString) {
/* 303 */     this.optionalArgs.add(paramString);
/* 304 */     this.ignoreParent = (this.parent == null);
/* 305 */     this.ignoreArgs = true;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getFirst() {
/* 312 */     return this.subCommands.get(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCommand addSubCommand(String paramString) {
/* 326 */     this.subCommands.add(paramString);
/* 327 */     return this;
/*     */   }
/*     */   
/*     */   public VCommand addSubCommand(VCommand paramVCommand) {
/* 331 */     paramVCommand.setParent(this);
/* 332 */     this.plugin.getCommandManager().registerCommand(paramVCommand);
/* 333 */     this.subVCommands.add(paramVCommand);
/* 334 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCommand addSubCommand(String... paramVarArgs) {
/* 344 */     this.subCommands.addAll(Arrays.asList(paramVarArgs));
/* 345 */     return this;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addCompletion(int paramInt, CollectionBiConsumer paramCollectionBiConsumer) {
/* 355 */     this.tabCompletions.put(Integer.valueOf(paramInt), paramCollectionBiConsumer);
/* 356 */     setTabCompletor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String generateDefaultSyntax(String paramString) {
/* 368 */     String str = this.subCommands.get(0);
/*     */     
/* 370 */     boolean bool = paramString.equals("");
/*     */     
/* 372 */     if (this.requireArgs.size() != 0 && bool) {
/* 373 */       for (String str1 : this.requireArgs) {
/* 374 */         str1 = "<" + str1 + ">";
/* 375 */         paramString = paramString + " " + str1;
/*     */       } 
/*     */     }
/* 378 */     if (this.optionalArgs.size() != 0 && bool) {
/* 379 */       for (String str1 : this.optionalArgs) {
/* 380 */         str1 = "[<" + str1 + ">]";
/* 381 */         paramString = paramString + " " + str1;
/*     */       } 
/*     */     }
/*     */     
/* 385 */     str = str + paramString;
/*     */     
/* 387 */     if (this.parent == null) {
/* 388 */       return "/" + str;
/*     */     }
/*     */     
/* 391 */     return this.parent.generateDefaultSyntax(" " + str);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int parentCount(int paramInt) {
/* 401 */     return (this.parent == null) ? paramInt : this.parent.parentCount(paramInt + 1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandType prePerform(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 414 */     if (!paramZAuctionPlugin.isReady() && !this.bypassReady) {
/*     */       
/* 416 */       if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/*     */         
/* 418 */         messageWO(paramCommandSender, "", new Object[0]);
/* 419 */         messageWO(paramCommandSender, "§cYou have not installed zMenu. §7The default configuration needs the zMenu plugin.", new Object[0]);
/* 420 */         messageWO(paramCommandSender, "§fhttps://www.spigotmc.org/resources/zmenu.110402/", new Object[0]);
/* 421 */         messageWO(paramCommandSender, "", new Object[0]);
/* 422 */         messageWO(paramCommandSender, "§7§oDon’t want to use zMenu? (it’s sad but it’s your choice)", new Object[0]);
/* 423 */         messageWO(paramCommandSender, "§8https://zauctionhouse.groupez.dev/informations/why-i-need-zmenu#how-not-to-use-zmenu", new Object[0]);
/* 424 */         messageWO(paramCommandSender, "", new Object[0]);
/*     */         
/* 426 */         return CommandType.SUCCESS;
/*     */       } 
/*     */       
/* 429 */       if (!paramZAuctionPlugin.isActive() && paramZAuctionPlugin.isEnable(Plugins.HEADDATABASE)) {
/* 430 */         message(paramCommandSender, Message.PLUGIN_NOT_READY, new Object[0]);
/*     */       } else {
/* 432 */         message(paramCommandSender, Message.PLUGIN_NOT_READY_HDB, new Object[0]);
/*     */       } 
/* 434 */       return CommandType.SUCCESS;
/*     */     } 
/*     */     
/* 437 */     this.inventoryManager = paramZAuctionPlugin.getInventories();
/* 438 */     this.auctionManager = paramZAuctionPlugin.getAuctionManager();
/*     */ 
/*     */     
/* 441 */     this.parentCount = parentCount(0);
/* 442 */     this.argsMaxLength = this.requireArgs.size() + this.optionalArgs.size() + this.parentCount;
/* 443 */     this.argsMinLength = this.requireArgs.size() + this.parentCount;
/*     */ 
/*     */     
/* 446 */     if (this.syntax == null) {
/* 447 */       this.syntax = generateDefaultSyntax("");
/*     */     }
/*     */     
/* 450 */     this.args = paramArrayOfString;
/*     */     
/* 452 */     String str = argAsString(0);
/*     */     
/* 454 */     if (str != null) {
/* 455 */       for (VCommand vCommand : this.subVCommands) {
/* 456 */         if (vCommand.getSubCommands().contains(str.toLowerCase())) return CommandType.CONTINUE;
/*     */       
/*     */       } 
/*     */     }
/* 460 */     if ((this.argsMinLength != 0 && paramArrayOfString.length < this.argsMinLength) || (this.argsMaxLength != 0 && paramArrayOfString.length > this.argsMaxLength && !this.extendedArgs)) {
/* 461 */       return CommandType.SYNTAX_ERROR;
/*     */     }
/*     */     
/* 464 */     if (Arrays.<String>stream(paramArrayOfString).anyMatch(PapiUtils::isPlaceholder)) {
/* 465 */       return CommandType.SYNTAX_ERROR;
/*     */     }
/*     */     
/* 468 */     this.sender = paramCommandSender;
/* 469 */     if (this.sender instanceof Player) {
/* 470 */       this.player = (Player)paramCommandSender;
/*     */     }
/*     */     
/*     */     try {
/* 474 */       return perform(paramZAuctionPlugin);
/* 475 */     } catch (Exception exception) {
/* 476 */       if (AuctionConfiguration.enableDebugMode) {
/* 477 */         exception.printStackTrace();
/*     */       }
/* 479 */       return CommandType.SYNTAX_ERROR;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean sameSubCommands() {
/* 489 */     if (this.parent == null) {
/* 490 */       return false;
/*     */     }
/* 492 */     for (String str : this.subCommands) {
/* 493 */       if (this.parent.getSubCommands().contains(str)) return true; 
/*     */     } 
/* 495 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 505 */     return "VCommand [permission=" + this.permission + ", subCommands=" + this.subCommands + ", consoleCanUse=" + this.consoleCanUse + ", description=" + this.description + "]";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> toTab(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 518 */     this.parentCount = parentCount(0);
/*     */     
/* 520 */     int i = paramArrayOfString.length - this.parentCount - 1;
/* 521 */     Optional<CollectionBiConsumer> optional = getCompletionAt(i);
/*     */     
/* 523 */     if (optional.isPresent()) {
/*     */       
/* 525 */       CollectionBiConsumer collectionBiConsumer = optional.get();
/* 526 */       String str = paramArrayOfString[paramArrayOfString.length - 1];
/* 527 */       return generateList(collectionBiConsumer.accept(paramCommandSender, paramArrayOfString), str);
/*     */     } 
/*     */ 
/*     */     
/* 531 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> generateList(String paramString, String... paramVarArgs) {
/* 542 */     return generateList(Arrays.asList(paramVarArgs), paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> generateList(Tab paramTab, String paramString, String... paramVarArgs) {
/* 553 */     return generateList(Arrays.asList(paramVarArgs), paramString, paramTab);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> generateList(List<String> paramList, String paramString) {
/* 564 */     return generateList(paramList, paramString, Tab.CONTAINS);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected List<String> generateList(List<String> paramList, String paramString, Tab paramTab) {
/* 576 */     ArrayList<String> arrayList = new ArrayList();
/* 577 */     for (String str : paramList) {
/* 578 */       if (paramString.length() == 0 || (paramTab.equals(Tab.START) ? str.toLowerCase().startsWith(paramString.toLowerCase()) : str.toLowerCase().contains(paramString.toLowerCase()))) {
/* 579 */         arrayList.add(str);
/*     */       }
/*     */     } 
/* 582 */     return (arrayList.size() == 0) ? null : arrayList;
/*     */   }
/*     */   
/*     */   public void addSubCommand(List<String> paramList) {
/* 586 */     this.subCommands.addAll(paramList);
/*     */   }
/*     */   
/*     */   public void syntaxMessage(CommandSender paramCommandSender) {
/* 590 */     this.subVCommands.forEach(paramVCommand -> {
/*     */           if (paramVCommand.getPermission() == null || hasPermission((Permissible)paramCommandSender, paramVCommand.getPermission())) {
/*     */             messageWO(paramCommandSender, Message.COMMAND_SYNTAXE_HELP, new Object[] { "%syntaxe%", paramVCommand.getSyntax(), "%description%", paramVCommand.getDescription() });
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */   
/*     */   protected Price getPriceAndEconomy(ItemStack paramItemStack, AuctionEconomy paramAuctionEconomy) {
/* 599 */     String str = argAsString(0);
/*     */     
/* 601 */     long l1 = this.plugin.getPriceManager().getMinPrice(paramItemStack, paramAuctionEconomy);
/* 602 */     long l2 = 1L;
/*     */     
/*     */     try {
/* 605 */       l1 = Long.parseLong(str);
/* 606 */     } catch (NumberFormatException numberFormatException) {
/*     */       
/* 608 */       String str1 = str;
/* 609 */       Optional<NumberFormatSell> optional = AuctionConfiguration.numberFormatSells.stream().filter(paramNumberFormatSell -> paramNumberFormatSell.match(paramString)).findFirst();
/*     */       
/* 611 */       this.plugin.debug("Trying to find a number format sell for: " + str + " - " + optional);
/*     */       
/* 613 */       if (optional.isPresent() && AuctionConfiguration.enableNumberFormatSell) {
/* 614 */         NumberFormatSell numberFormatSell = optional.get();
/*     */         
/* 616 */         this.plugin.debug("Found number format sell: " + numberFormatSell.getFormat());
/*     */         
/* 618 */         str = numberFormatSell.replace(str);
/* 619 */         l2 = numberFormatSell.getMultiplicator();
/*     */         try {
/* 621 */           l1 = (long)(Double.parseDouble(str.replace(",", ".")) * l2);
/* 622 */         } catch (Exception exception) {}
/*     */       } 
/*     */ 
/*     */       
/* 626 */       this.plugin.debug("Before: " + str + " - " + l2);
/*     */       
/* 628 */       if (l1 < this.plugin.getPriceManager().getMinPrice(paramItemStack, paramAuctionEconomy))
/*     */       {
/* 630 */         if (AuctionConfiguration.disablePriceErrorDefault) {
/* 631 */           if (AuctionConfiguration.enableCustomSellMessage) {
/* 632 */             message(this.sender, Message.COMMAND_SELL_CUSTOM_HELPING, new Object[0]);
/* 633 */             return new Price(CommandType.DEFAULT);
/*     */           } 
/* 635 */           return new Price(CommandType.SYNTAX_ERROR);
/*     */         } 
/*     */       }
/*     */ 
/*     */       
/* 640 */       if (l1 == AuctionConfiguration.minPrice && AuctionConfiguration.disableDefaultSellPriceFormatError) {
/* 641 */         return new Price(CommandType.SYNTAX_ERROR);
/*     */       }
/*     */     } 
/*     */     
/* 645 */     return new Price(l1, CommandType.SUCCESS);
/*     */   }
/*     */   
/*     */   protected abstract CommandType perform(ZAuctionPlugin paramZAuctionPlugin);
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\VCommand.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */