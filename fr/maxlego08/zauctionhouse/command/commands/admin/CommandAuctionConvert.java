/*    */ package fr.maxlego08.zauctionhouse.command.commands.admin;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.ConvertManager;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.ConvertType;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ import java.util.Arrays;
/*    */ import java.util.List;
/*    */ import java.util.stream.Collectors;
/*    */ import org.bukkit.command.CommandSender;
/*    */ 
/*    */ 
/*    */ public class CommandAuctionConvert
/*    */   extends VCommand
/*    */ {
/*    */   public CommandAuctionConvert(ZAuctionPlugin paramZAuctionPlugin) {
/* 20 */     super(paramZAuctionPlugin);
/* 21 */     setDescription(Message.DESCRIPTION_CLAIM);
/* 22 */     setPermission(Permission.ZAUCTIONHOUSE_CONVERT);
/* 23 */     addSubCommand("convert");
/* 24 */     addRequireArg("type");
/* 25 */     addOptionalArg("day");
/* 26 */     setTabCompletor();
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 32 */     String str = argAsString(0, null);
/* 33 */     long l = argAsLong(1, 365L);
/* 34 */     if (str == null) {
/*    */       
/* 36 */       message(this.sender, "§7List of convertible plugins§8:", new Object[0]);
/* 37 */       for (ConvertType convertType1 : ConvertType.values()) {
/* 38 */         message(this.sender, "§f%name% §7%link%", new Object[] { "%name%", name(convertType1.name()), "%link%", convertType1.getPluginLink() });
/*    */       } 
/* 40 */       return CommandType.SUCCESS;
/*    */     } 
/*    */     
/* 43 */     ConvertType convertType = ConvertType.valueOf(str.toUpperCase());
/* 44 */     ConvertManager convertManager = paramZAuctionPlugin.getConvertManager();
/* 45 */     convertManager.convertFile(this.sender, convertType, l * 86400L);
/*    */     
/* 47 */     return CommandType.SUCCESS;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public List<String> toTab(ZAuctionPlugin paramZAuctionPlugin, CommandSender paramCommandSender, String[] paramArrayOfString) {
/* 53 */     if (paramArrayOfString.length == 2) {
/*    */ 
/*    */       
/* 56 */       List list = (List)Arrays.<ConvertType>stream(ConvertType.values()).map(paramConvertType -> paramConvertType.name().toLowerCase()).collect(Collectors.toList());
/* 57 */       String str = paramArrayOfString[1];
/* 58 */       return generateList(list, str);
/*    */     } 
/*    */     
/* 61 */     return null;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\admin\CommandAuctionConvert.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */