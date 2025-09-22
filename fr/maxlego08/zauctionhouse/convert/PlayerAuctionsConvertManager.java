/*     */ package fr.maxlego08.zauctionhouse.convert;
/*     */ 
/*     */ import com.olziedev.playerauctions.api.PlayerAuctionsAPI;
/*     */ import com.olziedev.playerauctions.api.auction.Auction;
/*     */ import com.olziedev.playerauctions.api.player.APlayer;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionItem;
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.AuctionType;
/*     */ import fr.maxlego08.zauctionhouse.api.enums.StorageType;
/*     */ import fr.maxlego08.zauctionhouse.api.storage.IStorage;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import java.util.List;
/*     */ import java.util.UUID;
/*     */ import org.bukkit.Bukkit;
/*     */ import org.bukkit.command.CommandSender;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class PlayerAuctionsConvertManager
/*     */   extends ZUtils
/*     */ {
/*     */   private final ZAuctionPlugin plugin;
/*     */   private final ZConvertManager zConvertManager;
/*     */   
/*     */   public PlayerAuctionsConvertManager(ZAuctionPlugin paramZAuctionPlugin, ZConvertManager paramZConvertManager) {
/*  33 */     this.plugin = paramZAuctionPlugin;
/*  34 */     this.zConvertManager = paramZConvertManager;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void convertItemsFromPlayerAuctions(CommandSender paramCommandSender) {
/*  44 */     if (Bukkit.getPluginManager().getPlugin("PlayerAuctions") == null) {
/*  45 */       paramCommandSender.sendMessage("§cPlayerAuctions needs to be installed for the conversion to works. You can remove it after.");
/*     */       
/*     */       return;
/*     */     } 
/*  49 */     if (paramCommandSender instanceof org.bukkit.entity.Player) {
/*  50 */       paramCommandSender.sendMessage("§cOnly console can use this command !");
/*     */       
/*     */       return;
/*     */     } 
/*  54 */     this.zConvertManager.setConverting(true);
/*     */     
/*  56 */     message(paramCommandSender, "§aStart of conversion of items from PlayerAuctions !", new Object[0]);
/*     */     
/*  58 */     PlayerAuctionsAPI playerAuctionsAPI = PlayerAuctionsAPI.getInstance();
/*  59 */     IStorage iStorage = this.plugin.getStorage();
/*     */     
/*  61 */     List list = playerAuctionsAPI.getPlayerAuctions();
/*  62 */     byte b = 0;
/*  63 */     for (Auction auction : list) {
/*  64 */       StorageType storageType; long l1 = auction.getID();
/*  65 */       UUID uUID1 = auction.getUUID();
/*  66 */       long l2 = (long)Math.ceil(auction.getPrice());
/*  67 */       ItemStack itemStack = auction.getItem();
/*  68 */       long l3 = auction.getItemAmount();
/*  69 */       long l4 = auction.getAuctionDate();
/*  70 */       APlayer aPlayer = playerAuctionsAPI.getAuctionPlayer(uUID1);
/*  71 */       String str = aPlayer.getName();
/*     */       
/*  73 */       if (auction.isBidding()) {
/*     */         
/*  75 */         message(paramCommandSender, "§cWon't convert auction " + l1 + " because bidding is not currently supported by zAuctionHouseV3.", new Object[0]);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  80 */       if (l3 > 64L) {
/*     */         
/*  82 */         message(paramCommandSender, "§eAuction " + l1 + " have an amount above 64 which isn't supported by zAuctionHouseV3, the amount will be truncated to 64.", new Object[0]);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  87 */       if (itemStack == null) {
/*     */         
/*  89 */         message(paramCommandSender, "§cWon't convert auction " + l1 + " because item cannot be resolved.", new Object[0]);
/*     */         
/*     */         continue;
/*     */       } 
/*     */       
/*  94 */       long l5 = l4 + 1000L * AuctionConfiguration.sellTime;
/*  95 */       boolean bool = (l5 < System.currentTimeMillis()) ? true : false;
/*     */       
/*  97 */       UUID uUID2 = UUID.randomUUID();
/*     */       
/*  99 */       if (bool) {
/* 100 */         l5 = System.currentTimeMillis() + 1000L * AuctionConfiguration.expireTime;
/* 101 */         storageType = StorageType.EXPIRE;
/*     */       } else {
/* 103 */         storageType = StorageType.STORAGE;
/*     */       } 
/*     */       
/* 106 */       ZAuctionItem zAuctionItem = new ZAuctionItem(uUID2, itemStack, AuctionType.DEFAULT, l2, "VAULT", uUID1, l5, StorageType.EXPIRE, str, 0, this.plugin.getStorage().getServerName());
/* 107 */       iStorage.saveItem((AuctionPlugin)this.plugin, (AuctionItem)zAuctionItem, storageType);
/*     */       
/* 109 */       b++;
/* 110 */       message(paramCommandSender, "§aAdding an item, please wait", new Object[0]);
/*     */     } 
/*     */     
/* 113 */     message(paramCommandSender, "§aLoaded §6" + b + " §aitems.", new Object[0]);
/*     */     
/* 115 */     this.zConvertManager.setConverting(false);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\convert\PlayerAuctionsConvertManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */