/*     */ package fr.maxlego08.zauctionhouse.command;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*     */ import java.lang.reflect.Constructor;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.Command;
/*     */ import org.bukkit.command.CommandExecutor;
/*     */ import org.bukkit.command.CommandMap;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.command.PluginCommand;
/*     */ import org.bukkit.command.TabCompleter;
/*     */ import org.bukkit.permissions.Permissible;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ 
/*     */ public class CommandManager
/*     */   extends ZUtils implements CommandExecutor, TabCompleter {
/*     */   private static CommandMap commandMap;
/*     */   private static Constructor<? extends PluginCommand> constructor;
/*     */   private final ZAuctionPlugin plugin;
/*     */   
/*     */   static {
/*     */     try {
/*  31 */       Field field = Bukkit.getServer().getClass().getDeclaredField("commandMap");
/*  32 */       field.setAccessible(true);
/*  33 */       commandMap = (CommandMap)field.get(Bukkit.getServer());
/*  34 */       constructor = PluginCommand.class.getDeclaredConstructor(new Class[] { String.class, Plugin.class });
/*  35 */       constructor.setAccessible(true);
/*  36 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final List<VCommand> commands = new ArrayList<>();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CommandManager(ZAuctionPlugin paramZAuctionPlugin) {
/*  49 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void validCommands() {
/*  56 */     this.plugin.getLog().log("Loading " + getUniqueCommand() + " commands", Logger.LogType.SUCCESS);
/*  57 */     commandChecking();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCommand registerCommand(VCommand paramVCommand) {
/*  65 */     this.commands.add(paramVCommand);
/*  66 */     return paramVCommand;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public VCommand registerCommand(String paramString, VCommand paramVCommand) {
/*  77 */     this.commands.add(paramVCommand.addSubCommand(paramString));
/*  78 */     this.plugin.getCommand(paramString).setExecutor(this);
/*  79 */     this.plugin.getCommand(paramString).setTabCompleter(this);
/*  80 */     return paramVCommand;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean onCommand(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
/*  85 */     for (VCommand vCommand : this.commands) {
/*  86 */       if (vCommand.getSubCommands().contains(paramCommand.getName().toLowerCase())) {
/*  87 */         if ((paramArrayOfString.length == 0 || vCommand.isIgnoreParent()) && vCommand.getParent() == null) {
/*  88 */           CommandType commandType = processRequirements(vCommand, paramCommandSender, paramArrayOfString);
/*  89 */           if (!commandType.equals(CommandType.CONTINUE))
/*  90 */             return true; 
/*     */         }  continue;
/*  92 */       }  if (paramArrayOfString.length >= 1 && vCommand.getParent() != null && 
/*  93 */         canExecute(paramArrayOfString, paramCommand.getName().toLowerCase(), vCommand)) {
/*  94 */         CommandType commandType = processRequirements(vCommand, paramCommandSender, paramArrayOfString);
/*  95 */         if (!commandType.equals(CommandType.CONTINUE))
/*  96 */           return true; 
/*     */       } 
/*     */     } 
/*  99 */     message(paramCommandSender, Message.COMMAND_NO_ARG, new Object[0]);
/* 100 */     return true;
/*     */   }
/*     */   
/*     */   private boolean canExecute(String[] paramArrayOfString, String paramString, VCommand paramVCommand) {
/* 104 */     for (int i = paramArrayOfString.length - 1; i > -1; i--) {
/* 105 */       if (paramVCommand.getSubCommands().contains(paramArrayOfString[i].toLowerCase())) {
/* 106 */         if (paramVCommand.isIgnoreArgs() && (paramVCommand.getParent() == null || canExecute(paramArrayOfString, paramString, paramVCommand.getParent(), i - 1)))
/* 107 */           return true; 
/* 108 */         if (i < paramArrayOfString.length - 1)
/* 109 */           return false; 
/* 110 */         return canExecute(paramArrayOfString, paramString, paramVCommand.getParent(), i - 1);
/*     */       } 
/*     */     } 
/* 113 */     return false;
/*     */   }
/*     */   
/*     */   private boolean canExecute(String[] paramArrayOfString, String paramString, VCommand paramVCommand, int paramInt) {
/* 117 */     if (paramVCommand == null) return false; 
/* 118 */     if (paramInt < 0 && paramVCommand.getSubCommands().contains(paramString.toLowerCase()))
/* 119 */       return true; 
/* 120 */     if (paramInt < 0)
/* 121 */       return false; 
/* 122 */     if (paramVCommand.getSubCommands().contains(paramArrayOfString[paramInt].toLowerCase())) {
/* 123 */       return canExecute(paramArrayOfString, paramString, paramVCommand.getParent(), paramInt - 1);
/*     */     }
/* 125 */     return false;
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
/*     */   
/*     */   private CommandType processRequirements(VCommand paramVCommand, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 140 */     if (!(paramCommandSender instanceof org.bukkit.entity.Player) && !paramVCommand.isConsoleCanUse()) {
/* 141 */       message(paramCommandSender, Message.COMMAND_NO_CONSOLE, new Object[0]);
/* 142 */       return CommandType.DEFAULT;
/*     */     } 
/*     */     
/* 145 */     if (paramVCommand.getPermission() == null || hasPermission((Permissible)paramCommandSender, paramVCommand.getPermission())) {
/*     */       
/* 147 */       if (paramVCommand.runAsync) {
/* 148 */         runAsync(() -> {
/*     */               CommandType commandType = paramVCommand.prePerform(this.plugin, paramCommandSender, paramArrayOfString);
/*     */               if (commandType == CommandType.SYNTAX_ERROR) {
/*     */                 message(paramCommandSender, Message.COMMAND_SYNTAX_ERROR, new Object[] { "%command%", paramVCommand.getSyntax() });
/*     */               }
/*     */             });
/* 154 */         return CommandType.DEFAULT;
/*     */       } 
/*     */       
/* 157 */       CommandType commandType = paramVCommand.prePerform(this.plugin, paramCommandSender, paramArrayOfString);
/* 158 */       if (commandType == CommandType.SYNTAX_ERROR) {
/* 159 */         message(paramCommandSender, Message.COMMAND_SYNTAX_ERROR, new Object[] { "%command%", paramVCommand.getSyntax() });
/*     */       }
/* 161 */       return commandType;
/*     */     } 
/* 163 */     message(paramCommandSender, Message.COMMAND_NO_PERMISSION, new Object[0]);
/* 164 */     return CommandType.DEFAULT;
/*     */   }
/*     */   
/*     */   public List<VCommand> getCommands() {
/* 168 */     return this.commands;
/*     */   }
/*     */   
/*     */   private int getUniqueCommand() {
/* 172 */     return (int)this.commands.stream().filter(paramVCommand -> (paramVCommand.getParent() == null)).count();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isValid(VCommand paramVCommand, String paramString) {
/* 181 */     return (paramVCommand.getParent() != null) ? isValid(paramVCommand.getParent(), paramString) : 
/* 182 */       paramVCommand.getSubCommands().contains(paramString.toLowerCase());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void commandChecking() {
/* 190 */     this.commands.forEach(paramVCommand -> {
/*     */           if (paramVCommand.sameSubCommands()) {
/*     */             Logger.info(paramVCommand + " command to an argument similar to its parent command !", Logger.LogType.ERROR);
/*     */             this.plugin.getPluginLoader().disablePlugin((Plugin)this.plugin);
/*     */           } 
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> onTabComplete(CommandSender paramCommandSender, Command paramCommand, String paramString, String[] paramArrayOfString) {
/* 202 */     for (VCommand vCommand : this.commands) {
/*     */       
/* 204 */       if (vCommand.getSubCommands().contains(paramCommand.getName().toLowerCase())) {
/* 205 */         if (paramArrayOfString.length == 1 && vCommand.getParent() == null)
/* 206 */           return proccessTab(paramCommandSender, vCommand, paramArrayOfString); 
/*     */         continue;
/*     */       } 
/* 209 */       String[] arrayOfString = Arrays.<String>copyOf(paramArrayOfString, paramArrayOfString.length - 1);
/* 210 */       if (arrayOfString.length >= 1 && vCommand.getParent() != null && 
/* 211 */         canExecute(arrayOfString, paramCommand.getName().toLowerCase(), vCommand)) {
/* 212 */         return proccessTab(paramCommandSender, vCommand, paramArrayOfString);
/*     */       }
/*     */     } 
/*     */ 
/*     */     
/* 217 */     return null;
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
/*     */   private List<String> proccessTab(CommandSender paramCommandSender, VCommand paramVCommand, String[] paramArrayOfString) {
/* 230 */     CommandType commandType = paramVCommand.getTabCompleter();
/* 231 */     if (commandType.equals(CommandType.DEFAULT)) {
/*     */       
/* 233 */       String str = paramArrayOfString[paramArrayOfString.length - 1];
/*     */       
/* 235 */       ArrayList<String> arrayList = new ArrayList();
/* 236 */       for (VCommand vCommand : this.commands) {
/* 237 */         if (vCommand.getParent() != null && vCommand.getParent() == paramVCommand) {
/* 238 */           String str1 = vCommand.getSubCommands().get(0);
/* 239 */           if ((vCommand.getPermission() == null || paramCommandSender.hasPermission(vCommand.getPermission())) && (
/* 240 */             str.length() == 0 || str1.startsWith(str))) {
/* 241 */             arrayList.add(str1);
/*     */           }
/*     */         } 
/*     */       } 
/*     */       
/* 246 */       return (arrayList.size() == 0) ? null : arrayList;
/*     */     } 
/* 248 */     if (commandType.equals(CommandType.SUCCESS)) {
/* 249 */       return paramVCommand.toTab(this.plugin, paramCommandSender, paramArrayOfString);
/*     */     }
/*     */     
/* 252 */     return null;
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
/*     */   public void registerCommand(Plugin paramPlugin, String paramString, VCommand paramVCommand, List<String> paramList) {
/*     */     try {
/* 266 */       PluginCommand pluginCommand = constructor.newInstance(new Object[] { paramString, this.plugin });
/* 267 */       pluginCommand.setExecutor(this);
/* 268 */       pluginCommand.setTabCompleter(this);
/* 269 */       pluginCommand.setAliases(paramList);
/*     */       
/* 271 */       this.commands.add(paramVCommand.addSubCommand(paramString));
/* 272 */       paramVCommand.addSubCommand(paramList);
/*     */       
/* 274 */       if (!commandMap.register(pluginCommand.getName(), paramPlugin.getDescription().getName(), (Command)pluginCommand)) {
/* 275 */         Logger.info("Unable to add the command " + paramVCommand.getSyntax());
/*     */       }
/* 277 */     } catch (Exception exception) {
/* 278 */       exception.printStackTrace();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\CommandManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */