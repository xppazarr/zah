/*    */ package fr.maxlego08.zauctionhouse.search;
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.SearchInput;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sign.SignGUI;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sign.UpdateSignEvent;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ProtocolSearch implements SearchInput {
/*    */   private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ProtocolSearch(ZAuctionPlugin paramZAuctionPlugin) {
/* 14 */     this.plugin = paramZAuctionPlugin;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void startSearch(Player paramPlayer) {
/* 29 */     SignGUI signGUI = new SignGUI(paramUpdateSignEvent -> { String str = paramUpdateSignEvent.getLines()[0]; if (AuctionConfiguration.enableSearchTranslatedMaterial) str = this.plugin.getTranslationManager().replaceValue(str.toLowerCase());  this.plugin.getAuctionManager().search(paramPlayer, str); }AuctionConfiguration.searchLines, paramPlayer.getUniqueId(), (Plugin)this.plugin);
/* 30 */     signGUI.open();
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\search\ProtocolSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */