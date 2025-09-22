/*    */ package fr.maxlego08.zauctionhouse.listener;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*    */ import fr.maxlego08.zauctionhouse.api.AuctionManager;
/*    */ import fr.maxlego08.zauctionhouse.api.enums.Message;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*    */ import net.citizensnpcs.api.event.NPCRightClickEvent;
/*    */ import org.bukkit.command.CommandSender;
/*    */ import org.bukkit.entity.Player;
/*    */ import org.bukkit.event.EventHandler;
/*    */ import org.bukkit.event.Listener;
/*    */ 
/*    */ public class CitizenListener
/*    */   extends ZUtils
/*    */   implements Listener {
/*    */   private final ZAuctionPlugin plugin;
/*    */   private final AuctionManager manager;
/*    */   
/*    */   public CitizenListener(ZAuctionPlugin paramZAuctionPlugin, AuctionManager paramAuctionManager) {
/* 20 */     this.plugin = paramZAuctionPlugin;
/* 21 */     this.manager = paramAuctionManager;
/*    */   }
/*    */ 
/*    */   
/*    */   @EventHandler
/*    */   public void onClic(NPCRightClickEvent paramNPCRightClickEvent) {
/* 27 */     Player player = paramNPCRightClickEvent.getClicker();
/*    */     
/* 29 */     if (!this.plugin.isReady()) {
/* 30 */       message((CommandSender)player, Message.PLUGIN_NOT_READY, new Object[0]);
/*    */       
/*    */       return;
/*    */     } 
/* 34 */     String str = paramNPCRightClickEvent.getNPC().getName();
/* 35 */     this.manager.onNPCRequest(player, str);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\listener\CitizenListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */