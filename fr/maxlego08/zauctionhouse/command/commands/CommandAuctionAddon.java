/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.EnumInventory;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionAddon
/*    */   extends VCommand {
/*    */   public CommandAuctionAddon(ZAuctionPlugin paramZAuctionPlugin) {
/* 13 */     super(paramZAuctionPlugin);
/* 14 */     setDescription(Message.DESCRIPTION_CLAIM);
/* 15 */     addSubCommand("addon");
/* 16 */     setConsoleCanUse(false);
/* 17 */     setPermission(AuctionConfiguration.addonPermission);
/*    */   }
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 22 */     createInventory(paramZAuctionPlugin, this.player, EnumInventory.INVENTORY_ADDON);
/* 23 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionAddon.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */