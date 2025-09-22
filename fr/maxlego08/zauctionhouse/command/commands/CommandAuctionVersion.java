/*    */ package fr.maxlego08.zauctionhouse.command.commands;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.command.VCommand;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.commands.CommandType;
/*    */ 
/*    */ public class CommandAuctionVersion
/*    */   extends VCommand {
/*    */   public CommandAuctionVersion(ZAuctionPlugin paramZAuctionPlugin) {
/* 12 */     super(paramZAuctionPlugin);
/* 13 */     setDescription(Message.DESCRIPTION_VERSION);
/* 14 */     addSubCommand(new String[] { "version", "v", "ver" });
/* 15 */     setPermission(AuctionConfiguration.versionPermission);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   protected CommandType perform(ZAuctionPlugin paramZAuctionPlugin) {
/* 21 */     message(this.sender, "§aVersion du plugin§7: §2" + paramZAuctionPlugin.getDescription().getVersion(), new Object[0]);
/* 22 */     message(this.sender, "§aAuteur§7: §2Maxlego08", new Object[0]);
/* 23 */     message(this.sender, "§aDiscord§7: §2http://discord.groupez.dev/", new Object[0]);
/* 24 */     message(this.sender, "§aBuy it for §d12§§7: §2https://groupez.dev/resources/1", new Object[0]);
/* 25 */     message(this.sender, "§aSponsor§7: §chttps://serveur-minecraft-vote.fr/?ref=345", new Object[0]);
/*    */     
/* 27 */     return CommandType.SUCCESS;
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\command\commands\CommandAuctionVersion.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */