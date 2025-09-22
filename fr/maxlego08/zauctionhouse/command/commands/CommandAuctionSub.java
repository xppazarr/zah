/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.command.Command;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.InventoryName;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Permission;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.CommandObject;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionSub
/*    */   extends VCommand {
/*    */   private final InventoryName inventoryName;
/*    */   
/*    */   public CommandAuctionSub(ZAuctionPlugin paramZAuctionPlugin, String paramString, InventoryName paramInventoryName) {
/* 18 */     super(paramZAuctionPlugin);
/* 19 */     addSubCommand(paramString);
/*    */     
/* 21 */     setPermission(Permission.ZAUCTIONHOUSE_USE);
/* 22 */     switch (paramInventoryName) {
/*    */       case ITEMS:
/* 24 */         setDescription(Message.DESCRIPTION_SUB_ITEMS);
/*    */         break;
/*    */       case CATEGORIES:
/* 27 */         setDescription(Message.DESCRIPTION_SUB_CATEGORIES);
/*    */         break;
/*    */       case BUYING:
/* 30 */         setDescription(Message.DESCRIPTION_SUB_BUYING);
/*    */         break;
/*    */       case EXPIRE:
/* 33 */         setDescription(Message.DESCRIPTION_SUB_EXPIRE);
/*    */         break;
/*    */     } 
/*    */ 
/*    */ 
/*    */ 
/*    */     
/* 40 */     this.inventoryName = paramInventoryName;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 46 */     if (AuctionConfiguration.USE_ZMENU_INVENTORY) {
/* 47 */       paramZAuctionPlugin.getzMenuHandler().openInventory(this.player, this.inventoryName.getName());
/* 48 */       return CommandType.SUCCESS;
/*    */     } 
/*    */ 
/*    */ 
/*    */     
/* 53 */     CommandObject commandObject = new CommandObject(this.parent.getFirst(), this.parent.getSubCommands(), this.inventoryManager.getInventory(InventoryName.AUCTION), this.parent.getPermission(), this.parent.getDescription());
/* 54 */     this.auctionManager.open(this.player, (Command)commandObject, this.inventoryName);
/*    */     
/* 56 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionSub.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */