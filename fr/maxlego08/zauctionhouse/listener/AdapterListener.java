/*     */ package fr.maxlego08.zauctionhouse.listener;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.ZAuctionPlugin;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.LivingEntity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.entity.Projectile;
/*     */ import org.bukkit.event.EventHandler;
/*     */ import org.bukkit.event.Listener;
/*     */ import org.bukkit.event.block.BlockBreakEvent;
/*     */ import org.bukkit.event.block.BlockPlaceEvent;
/*     */ import org.bukkit.event.entity.CreatureSpawnEvent;
/*     */ import org.bukkit.event.entity.EntityDamageByEntityEvent;
/*     */ import org.bukkit.event.entity.EntityDeathEvent;
/*     */ import org.bukkit.event.inventory.CraftItemEvent;
/*     */ import org.bukkit.event.inventory.InventoryClickEvent;
/*     */ import org.bukkit.event.inventory.InventoryCloseEvent;
/*     */ import org.bukkit.event.inventory.InventoryDragEvent;
/*     */ import org.bukkit.event.player.AsyncPlayerChatEvent;
/*     */ import org.bukkit.event.player.PlayerCommandPreprocessEvent;
/*     */ import org.bukkit.event.player.PlayerGameModeChangeEvent;
/*     */ import org.bukkit.event.player.PlayerInteractEvent;
/*     */ import org.bukkit.event.player.PlayerJoinEvent;
/*     */ import org.bukkit.event.player.PlayerPickupItemEvent;
/*     */ import org.bukkit.event.player.PlayerQuitEvent;
/*     */ 
/*     */ public class AdapterListener
/*     */   extends ZUtils
/*     */   implements Listener {
/*     */   private final ZAuctionPlugin plugin;
/*     */   
/*     */   public AdapterListener(ZAuctionPlugin paramZAuctionPlugin) {
/*  34 */     this.plugin = paramZAuctionPlugin;
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onConnect(PlayerJoinEvent paramPlayerJoinEvent) {
/*  39 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onConnect(paramPlayerJoinEvent, paramPlayerJoinEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
/*  44 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onQuit(paramPlayerQuitEvent, paramPlayerQuitEvent.getPlayer()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onInventoryClick(InventoryClickEvent paramInventoryClickEvent) {
/*  60 */     this.plugin.getListenerAdapters()
/*  61 */       .forEach(paramListenerAdapter -> paramListenerAdapter.onInventoryClick(paramInventoryClickEvent, (Player)paramInventoryClickEvent.getWhoClicked()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockBreak(BlockBreakEvent paramBlockBreakEvent) {
/*  66 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onBlockBreak(paramBlockBreakEvent, paramBlockBreakEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onBlockPlace(BlockPlaceEvent paramBlockPlaceEvent) {
/*  71 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onBlockPlace(paramBlockPlaceEvent, paramBlockPlaceEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onEntityDeath(EntityDeathEvent paramEntityDeathEvent) {
/*  76 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onEntityDeath(paramEntityDeathEvent, (Entity)paramEntityDeathEvent.getEntity()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onInteract(PlayerInteractEvent paramPlayerInteractEvent) {
/*  81 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onInteract(paramPlayerInteractEvent, paramPlayerInteractEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onPlayerTalk(AsyncPlayerChatEvent paramAsyncPlayerChatEvent) {
/*  86 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onPlayerTalk(paramAsyncPlayerChatEvent, paramAsyncPlayerChatEvent.getMessage()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCraftItem(CraftItemEvent paramCraftItemEvent) {
/*  91 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onCraftItem(paramCraftItemEvent));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onDrag(InventoryDragEvent paramInventoryDragEvent) {
/*  96 */     this.plugin.getListenerAdapters()
/*  97 */       .forEach(paramListenerAdapter -> paramListenerAdapter.onInventoryDrag(paramInventoryDragEvent, (Player)paramInventoryDragEvent.getWhoClicked()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onClose(InventoryCloseEvent paramInventoryCloseEvent) {
/* 102 */     this.plugin.getListenerAdapters()
/* 103 */       .forEach(paramListenerAdapter -> paramListenerAdapter.onInventoryClose(paramInventoryCloseEvent, (Player)paramInventoryCloseEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onCommand(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent) {
/* 108 */     this.plugin.getListenerAdapters()
/* 109 */       .forEach(paramListenerAdapter -> paramListenerAdapter.onCommand(paramPlayerCommandPreprocessEvent, paramPlayerCommandPreprocessEvent.getPlayer(), paramPlayerCommandPreprocessEvent.getMessage()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onGamemodeChange(PlayerGameModeChangeEvent paramPlayerGameModeChangeEvent) {
/* 114 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onGamemodeChange(paramPlayerGameModeChangeEvent, paramPlayerGameModeChangeEvent.getPlayer()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onPick(PlayerPickupItemEvent paramPlayerPickupItemEvent) {
/* 134 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onPickUp(paramPlayerPickupItemEvent, paramPlayerPickupItemEvent.getPlayer()));
/*     */   }
/*     */   
/*     */   @EventHandler
/*     */   public void onMobSpawn(CreatureSpawnEvent paramCreatureSpawnEvent) {
/* 139 */     this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onMobSpawn(paramCreatureSpawnEvent));
/*     */   }
/*     */ 
/*     */   
/*     */   @EventHandler
/*     */   public void onDamage(EntityDamageByEntityEvent paramEntityDamageByEntityEvent) {
/* 145 */     if (paramEntityDamageByEntityEvent.getEntity() instanceof LivingEntity && paramEntityDamageByEntityEvent.getDamager() instanceof LivingEntity) {
/* 146 */       this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onDamageByEntity(paramEntityDamageByEntityEvent, paramEntityDamageByEntityEvent.getCause(), paramEntityDamageByEntityEvent.getDamage(), (LivingEntity)paramEntityDamageByEntityEvent.getDamager(), (LivingEntity)paramEntityDamageByEntityEvent.getEntity()));
/*     */     }
/*     */ 
/*     */     
/* 150 */     if (paramEntityDamageByEntityEvent.getEntity() instanceof Player && paramEntityDamageByEntityEvent.getDamager() instanceof Player) {
/* 151 */       this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onPlayerDamagaByPlayer(paramEntityDamageByEntityEvent, paramEntityDamageByEntityEvent.getCause(), paramEntityDamageByEntityEvent.getDamage(), (Player)paramEntityDamageByEntityEvent.getDamager(), (Player)paramEntityDamageByEntityEvent.getEntity()));
/*     */     }
/*     */ 
/*     */     
/* 155 */     if (paramEntityDamageByEntityEvent.getEntity() instanceof Player && paramEntityDamageByEntityEvent.getDamager() instanceof Projectile)
/* 156 */       this.plugin.getListenerAdapters().forEach(paramListenerAdapter -> paramListenerAdapter.onPlayerDamagaByArrow(paramEntityDamageByEntityEvent, paramEntityDamageByEntityEvent.getCause(), paramEntityDamageByEntityEvent.getDamage(), (Projectile)paramEntityDamageByEntityEvent.getDamager(), (Player)paramEntityDamageByEntityEvent.getEntity())); 
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\listener\AdapterListener.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */