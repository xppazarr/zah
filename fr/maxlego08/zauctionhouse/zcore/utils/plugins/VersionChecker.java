/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.plugins;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*    */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.util.Scanner;
/*    */ import java.util.concurrent.atomic.AtomicBoolean;
/*    */ import java.util.function.Consumer;
/*    */ import org.bukkit.Bukkit;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ import org.bukkit.event.player.PlayerJoinEvent;
/*    */ import org.bukkit.plugin.Plugin;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class VersionChecker
/*    */   implements Listener
/*    */ {
/* 25 */   private final String URL_API = "https://groupez.dev/api/v1/resource/version/%s";
/* 26 */   private final String URL_RESOURCE = "https://groupez.dev/resources/%s";
/*    */ 
/*    */   
/*    */   private final ZAuctionPlugin plugin;
/*    */ 
/*    */   
/*    */   private final int pluginID;
/*    */ 
/*    */   
/*    */   private boolean useLastVersion = false;
/*    */ 
/*    */   
/*    */   public VersionChecker(ZAuctionPlugin paramZAuctionPlugin, int paramInt) {
/* 39 */     this.plugin = paramZAuctionPlugin;
/* 40 */     this.pluginID = paramInt;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void useLastVersion() {
/* 48 */     Bukkit.getPluginManager().registerEvents(this, (Plugin)this.plugin);
/*    */ 
/*    */     
/* 51 */     String str = this.plugin.getDescription().getVersion();
/* 52 */     AtomicBoolean atomicBoolean = new AtomicBoolean();
/* 53 */     getVersion(paramString2 -> {
/*    */           long l1 = Long.parseLong(paramString2.replace(".", ""));
/*    */           long l2 = Long.parseLong(paramString1.replace(".", ""));
/*    */           paramAtomicBoolean.set((l2 >= l1));
/*    */           this.useLastVersion = paramAtomicBoolean.get();
/*    */           if (paramAtomicBoolean.get()) {
/*    */             Logger.info("No update available.");
/*    */           } else {
/*    */             Logger.info("New update available. Your version: " + paramString1 + ", latest version: " + paramString2);
/*    */             Logger.info("Download plugin here: " + String.format("https://groupez.dev/resources/%s", new Object[] { Integer.valueOf(this.pluginID) }));
/*    */           } 
/*    */         });
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onConnect(PlayerJoinEvent paramPlayerJoinEvent) {
/* 70 */     Player player = paramPlayerJoinEvent.getPlayer();
/* 71 */     if (!this.useLastVersion && paramPlayerJoinEvent.getPlayer().hasPermission("zplugin.notifs")) {
/* 72 */       ZPlugin.serverImplementation.runAtLocationLater(player.getLocation(), () -> { paramPlayer.sendMessage("§8(§bzAuctionHouse§8) §cYou do not use the latest version of the plugin! Thank you for taking the latest version to avoid any risk of problem!"); paramPlayer.sendMessage("§8(§bzAuctionHouse§8) §fDownload plugin here: §a" + String.format("https://groupez.dev/resources/%s", new Object[] { Integer.valueOf(this.pluginID) })); }20L);
/*    */     }
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
/*    */   public void getVersion(Consumer<String> paramConsumer) {
/* 85 */     ZPlugin.serverImplementation.runAsync(paramWrappedTask -> {
/*    */           String str = String.format("https://groupez.dev/api/v1/resource/version/%s", new Object[] { Integer.valueOf(this.pluginID) });
/*    */           try {
/*    */             URL uRL = new URL(str);
/*    */             URLConnection uRLConnection = uRL.openConnection();
/*    */             uRLConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10.4; en-US; rv:1.9.2.2) Gecko/20100316 Firefox/3.6.2");
/*    */             Scanner scanner = new Scanner(uRLConnection.getInputStream());
/*    */             if (scanner.hasNext())
/*    */               paramConsumer.accept(scanner.next()); 
/*    */             scanner.close();
/* 95 */           } catch (IOException iOException) {
/*    */             this.plugin.getLogger().info("Cannot look for updates: " + iOException.getMessage());
/*    */           } 
/*    */         });
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\plugins\VersionChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */