package fr.maxlego08.zauctionhouse.listener;

import fr.maxlego08.zauctionhouse.zcore.utils.ZUtils;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerGameModeChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public abstract class ListenerAdapter extends ZUtils {
  protected void onConnect(PlayerJoinEvent paramPlayerJoinEvent, Player paramPlayer) {}
  
  protected void onQuit(PlayerQuitEvent paramPlayerQuitEvent, Player paramPlayer) {}
  
  protected void onMove(PlayerMoveEvent paramPlayerMoveEvent, Player paramPlayer) {}
  
  protected void onInventoryClick(InventoryClickEvent paramInventoryClickEvent, Player paramPlayer) {}
  
  protected void onInventoryClose(InventoryCloseEvent paramInventoryCloseEvent, Player paramPlayer) {}
  
  protected void onInventoryDrag(InventoryDragEvent paramInventoryDragEvent, Player paramPlayer) {}
  
  protected void onBlockBreak(BlockBreakEvent paramBlockBreakEvent, Player paramPlayer) {}
  
  protected void onBlockPlace(BlockPlaceEvent paramBlockPlaceEvent, Player paramPlayer) {}
  
  protected void onEntityDeath(EntityDeathEvent paramEntityDeathEvent, Entity paramEntity) {}
  
  protected void onInteract(PlayerInteractEvent paramPlayerInteractEvent, Player paramPlayer) {}
  
  protected void onPlayerTalk(AsyncPlayerChatEvent paramAsyncPlayerChatEvent, String paramString) {}
  
  protected void onCraftItem(CraftItemEvent paramCraftItemEvent) {}
  
  protected void onCommand(PlayerCommandPreprocessEvent paramPlayerCommandPreprocessEvent, Player paramPlayer, String paramString) {}
  
  protected void onGamemodeChange(PlayerGameModeChangeEvent paramPlayerGameModeChangeEvent, Player paramPlayer) {}
  
  protected void onDrop(PlayerDropItemEvent paramPlayerDropItemEvent, Player paramPlayer) {}
  
  protected void onPickUp(PlayerPickupItemEvent paramPlayerPickupItemEvent, Player paramPlayer) {}
  
  protected void onMobSpawn(CreatureSpawnEvent paramCreatureSpawnEvent) {}
  
  protected void onDamageByEntity(EntityDamageByEntityEvent paramEntityDamageByEntityEvent, EntityDamageEvent.DamageCause paramDamageCause, double paramDouble, LivingEntity paramLivingEntity1, LivingEntity paramLivingEntity2) {}
  
  protected void onPlayerDamagaByPlayer(EntityDamageByEntityEvent paramEntityDamageByEntityEvent, EntityDamageEvent.DamageCause paramDamageCause, double paramDouble, Player paramPlayer1, Player paramPlayer2) {}
  
  protected void onPlayerDamagaByArrow(EntityDamageByEntityEvent paramEntityDamageByEntityEvent, EntityDamageEvent.DamageCause paramDamageCause, double paramDouble, Projectile paramProjectile, Player paramPlayer) {}
  
  protected void onItemisOnGround(PlayerDropItemEvent paramPlayerDropItemEvent, Player paramPlayer, Item paramItem, Location paramLocation) {}
  
  protected void onItemMove(PlayerDropItemEvent paramPlayerDropItemEvent, Player paramPlayer, Item paramItem, Location paramLocation, Block paramBlock) {}
  
  protected void onPlayerWalk(PlayerMoveEvent paramPlayerMoveEvent, Player paramPlayer, int paramInt) {}
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\listener\ListenerAdapter.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */