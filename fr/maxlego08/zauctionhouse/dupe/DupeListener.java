/*     */ package fr.maxlego08.zauctionhouse.dupe;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.api.AuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.api.dupe.DupeItem;
/*     */ import fr.maxlego08.zauctionhouse.api.dupe.DupeManager;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.AuctionConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.DupeConfig;
/*     */ import fr.maxlego08.zauctionhouse.api.utils.Logger;
/*     */ import fr.maxlego08.zauctionhouse.zcore.ZPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.DiscordWebhook;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*     */ import java.io.IOException;
/*     */ import org.bukkit.Material;
/*     */ import org.bukkit.entity.Item;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.EventPriority;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.player.PlayerDropItemEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.inventory.ItemStack;
/*     */ 
/*     */ 
/*     */ public class DupeListener
/*     */   implements Listener
/*     */ {
/*     */   private final DupeManager dupeManager;
/*     */   private final AuctionPlugin plugin;
/*     */   
/*     */   public DupeListener(DupeManager paramDupeManager, AuctionPlugin paramAuctionPlugin) {
/*  32 */     this.dupeManager = paramDupeManager;
/*  33 */     this.plugin = paramAuctionPlugin;
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onDrop(PlayerDropItemEvent paramPlayerDropItemEvent) {
/*  39 */     if (!AuctionConfiguration.dupeConfig.isEnable())
/*     */       return; 
/*  41 */     if (paramPlayerDropItemEvent.isCancelled()) {
/*     */       return;
/*     */     }
/*  44 */     Item item = paramPlayerDropItemEvent.getItemDrop();
/*  45 */     ItemStack itemStack = item.getItemStack();
/*  46 */     if (this.dupeManager.isDupeItem(itemStack)) {
/*  47 */       item.remove();
/*  48 */       sendInformation(this.plugin, new DupeItem(itemStack, paramPlayerDropItemEvent.getPlayer()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onDrop(PlayerPickupItemEvent paramPlayerPickupItemEvent) {
/*  55 */     if (!AuctionConfiguration.dupeConfig.isEnable())
/*     */       return; 
/*  57 */     if (paramPlayerPickupItemEvent.isCancelled()) {
/*     */       return;
/*     */     }
/*  60 */     Item item = paramPlayerPickupItemEvent.getItem();
/*  61 */     ItemStack itemStack = item.getItemStack();
/*  62 */     if (this.dupeManager.isDupeItem(itemStack)) {
/*  63 */       item.remove();
/*  64 */       paramPlayerPickupItemEvent.setCancelled(true);
/*  65 */       sendInformation(this.plugin, new DupeItem(itemStack, paramPlayerPickupItemEvent.getPlayer()));
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler(priority = EventPriority.HIGHEST)
/*     */   public void onInventoryClick(InventoryClickEvent paramInventoryClickEvent) {
/*  72 */     if (!AuctionConfiguration.dupeConfig.isEnable())
/*     */       return; 
/*  74 */     if (paramInventoryClickEvent.isCancelled()) {
/*     */       return;
/*     */     }
/*  77 */     Player player = (Player)paramInventoryClickEvent.getWhoClicked();
/*  78 */     ItemStack itemStack = paramInventoryClickEvent.getCurrentItem();
/*  79 */     if (itemStack != null && 
/*  80 */       this.dupeManager.isDupeItem(itemStack)) {
/*  81 */       paramInventoryClickEvent.setCurrentItem(new ItemStack(Material.AIR));
/*  82 */       paramInventoryClickEvent.setCancelled(true);
/*  83 */       sendInformation(this.plugin, new DupeItem(itemStack, player));
/*     */     } 
/*     */ 
/*     */     
/*  87 */     itemStack = paramInventoryClickEvent.getCursor();
/*  88 */     if (itemStack != null && 
/*  89 */       this.dupeManager.isDupeItem(itemStack)) {
/*  90 */       paramInventoryClickEvent.setCursor(new ItemStack(Material.AIR));
/*  91 */       paramInventoryClickEvent.setCancelled(true);
/*  92 */       sendInformation(this.plugin, new DupeItem(itemStack, player));
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void sendInformation(AuctionPlugin paramAuctionPlugin, DupeItem paramDupeItem) {
/* 100 */     DupeConfig dupeConfig = AuctionConfiguration.dupeConfig;
/* 101 */     if (dupeConfig == null || !dupeConfig.isEnable()) {
/*     */       return;
/*     */     }
/* 104 */     DiscordWebhook discordWebhook = new DiscordWebhook(paramAuctionPlugin, dupeConfig.getDiscordWebhookUrl());
/* 105 */     if (dupeConfig.isEnableWebhook()) {
/* 106 */       discordWebhook.setContent(dupeConfig.getDiscordWebhookContent());
/* 107 */       ZPlugin.serverImplementation.runAsync(paramWrappedTask -> {
/*     */             try {
/*     */               paramDiscordWebhook.executeDupe(paramDupeItem);
/* 110 */             } catch (IOException iOException) {
/*     */               iOException.printStackTrace();
/*     */             } 
/*     */           });
/*     */     } 
/*     */     
/* 116 */     Logger.info(discordWebhook.replaceString(dupeConfig.getLogContent(), paramDupeItem), Logger.LogType.WARNING);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\dupe\DupeListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */