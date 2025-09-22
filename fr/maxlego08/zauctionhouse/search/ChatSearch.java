/*    */ package fr.maxlego08.zauctionhouse.search;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.SearchInput;
/*    */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*    */ import org.bukkit.event.player.PlayerQuitEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ public class ChatSearch extends ZUtils implements SearchInput, Listener {
/* 21 */   private final List<Player> searchPlayers = new ArrayList<>(); private final ZAuctionPlugin plugin;
/*    */   
/*    */   public ChatSearch(ZAuctionPlugin paramZAuctionPlugin) {
/* 24 */     this.plugin = paramZAuctionPlugin;
/* 25 */     paramZAuctionPlugin.getServer().getPluginManager().registerEvents(this, (Plugin)paramZAuctionPlugin);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void startSearch(Player paramPlayer) {
/* 31 */     this.searchPlayers.add(paramPlayer);
/* 32 */     paramPlayer.closeInventory();
/*    */     
/* 34 */     message((CommandSender)paramPlayer, Message.SEARCH_START.getIMessage(), new Object[0]);
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onTalk(AsyncPlayerChatEvent paramAsyncPlayerChatEvent) {
/* 39 */     if (!this.searchPlayers.contains(paramAsyncPlayerChatEvent.getPlayer()))
/*    */       return; 
/* 41 */     this.searchPlayers.remove(paramAsyncPlayerChatEvent.getPlayer());
/* 42 */     String str1 = paramAsyncPlayerChatEvent.getMessage();
/* 43 */     paramAsyncPlayerChatEvent.setCancelled(true);
/*    */     
/* 45 */     if (AuctionConfiguration.enableSearchTranslatedMaterial) {
/* 46 */       str1 = this.plugin.getTranslationManager().replaceValue(str1.toLowerCase());
/*    */     }
/*    */     
/* 49 */     String str2 = str1;
/* 50 */     ZPlugin.serverImplementation.runNextTick(paramWrappedTask -> this.plugin.getAuctionManager().search(paramAsyncPlayerChatEvent.getPlayer(), paramString));
/*    */   }
/*    */   
/*    */   @EventHandler
/*    */   public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
/* 55 */     this.searchPlayers.remove(paramPlayerQuitEvent.getPlayer());
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\search\ChatSearch.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */