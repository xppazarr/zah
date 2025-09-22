package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;

import fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums.EntityTaskResult;
import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Deprecated
public interface ServerImplementation {
  boolean isOwnedByCurrentRegion(@NotNull Location paramLocation);
  
  boolean isOwnedByCurrentRegion(@NotNull Location paramLocation, int paramInt);
  
  boolean isOwnedByCurrentRegion(@NotNull Block paramBlock);
  
  boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2);
  
  boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2, int paramInt3);
  
  boolean isOwnedByCurrentRegion(@NotNull Entity paramEntity);
  
  boolean isGlobalTickThread();
  
  @NotNull
  CompletableFuture<Void> runNextTick(@NotNull Consumer<WrappedTask> paramConsumer);
  
  @NotNull
  CompletableFuture<Void> runAsync(@NotNull Consumer<WrappedTask> paramConsumer);
  
  WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong);
  
  @NotNull
  CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong);
  
  WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit);
  
  WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong);
  
  @NotNull
  CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong);
  
  WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit);
  
  WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2);
  
  void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2);
  
  WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2);
  
  void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2);
  
  WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<Void> runAtLocation(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer);
  
  WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong);
  
  @NotNull
  CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong);
  
  WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit);
  
  WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2);
  
  void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2);
  
  WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<EntityTaskResult> runAtEntity(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer);
  
  @NotNull
  CompletableFuture<EntityTaskResult> runAtEntityWithFallback(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, @Nullable Runnable paramRunnable);
  
  WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong);
  
  WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable1, @Nullable Runnable paramRunnable2, long paramLong);
  
  @NotNull
  CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong);
  
  @NotNull
  CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong);
  
  WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit);
  
  @NotNull
  CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit);
  
  WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2);
  
  WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable1, Runnable paramRunnable2, long paramLong1, long paramLong2);
  
  void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2);
  
  void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong1, long paramLong2);
  
  WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit);
  
  void cancelTask(WrappedTask paramWrappedTask);
  
  void cancelAllTasks();
  
  List<WrappedTask> getAllTasks();
  
  List<WrappedTask> getAllServerTasks();
  
  Player getPlayer(String paramString);
  
  Player getPlayerExact(String paramString);
  
  Player getPlayer(UUID paramUUID);
  
  CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation);
  
  CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation, PlayerTeleportEvent.TeleportCause paramTeleportCause);
  
  WrappedTask wrapTask(Object paramObject);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\ServerImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */