/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums.EntityTaskResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.TimeConverter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedBukkitTask;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*     */ import java.util.List;
/*     */ import java.util.Objects;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.function.Supplier;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.bukkit.scheduler.BukkitScheduler;
/*     */ import org.bukkit.scheduler.BukkitTask;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ import org.jetbrains.annotations.Nullable;
/*     */ 
/*     */ public class SpigotImplementation
/*     */   implements PlatformScheduler
/*     */ {
/*     */   private final JavaPlugin plugin;
/*     */   @NotNull
/*     */   private final BukkitScheduler scheduler;
/*     */   
/*     */   public SpigotImplementation(FoliaLib paramFoliaLib) {
/*  37 */     this.plugin = paramFoliaLib.getPlugin();
/*  38 */     this.scheduler = this.plugin.getServer().getScheduler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation) {
/*  43 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation, int paramInt) {
/*  48 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Block paramBlock) {
/*  53 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2) {
/*  58 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  63 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Entity paramEntity) {
/*  68 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalTickThread() {
/*  73 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runNextTick(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  78 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/*  80 */     this.scheduler.runTask((Plugin)this.plugin, paramBukkitTask -> {
/*     */           paramConsumer.accept(wrapTask(paramBukkitTask));
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/*  85 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAsync(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  90 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/*  92 */     this.scheduler.runTaskAsynchronously((Plugin)this.plugin, paramBukkitTask -> {
/*     */           paramConsumer.accept(wrapTask(paramBukkitTask));
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/*  97 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong) {
/* 102 */     return wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, paramRunnable, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 107 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 109 */     this.scheduler.runTaskLater((Plugin)this.plugin, paramBukkitTask -> { paramConsumer.accept(wrapTask(paramBukkitTask)); paramCompletableFuture.complete(null); }paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 114 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 119 */     return runLater(paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 124 */     return runLater(paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong) {
/* 129 */     return wrapTask(this.scheduler.runTaskLaterAsynchronously((Plugin)this.plugin, paramRunnable, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 134 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 136 */     this.scheduler.runTaskLaterAsynchronously((Plugin)this.plugin, paramBukkitTask -> { paramConsumer.accept(wrapTask(paramBukkitTask)); paramCompletableFuture.complete(null); }paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 146 */     return runLaterAsync(paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 151 */     return runLaterAsync(paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 156 */     return wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, paramRunnable, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 161 */     this.scheduler.runTaskTimer((Plugin)this.plugin, paramBukkitTask -> paramConsumer.accept(wrapTask(paramBukkitTask)), paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 166 */     return runTimer(paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 171 */     runTimer(paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 176 */     return wrapTask(this.scheduler.runTaskTimerAsynchronously((Plugin)this.plugin, paramRunnable, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 181 */     this.scheduler.runTaskTimerAsynchronously((Plugin)this.plugin, paramBukkitTask -> paramConsumer.accept(wrapTask(paramBukkitTask)), paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 186 */     return runTimerAsync(paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 191 */     runTimerAsync(paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocation(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 196 */     return runNextTick(paramConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong) {
/* 201 */     return runLater(paramRunnable, paramLong);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 206 */     return runLater(paramConsumer, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 211 */     return runAtLocationLater(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 216 */     return runAtLocationLater(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 221 */     return runTimer(paramRunnable, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 226 */     runTimer(paramConsumer, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 231 */     return runAtLocationTimer(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 236 */     runAtLocationTimer(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntity(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 241 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/*     */     
/* 243 */     runNextTick(paramWrappedTask -> {
/*     */           paramConsumer.accept(paramWrappedTask);
/*     */           
/*     */           paramCompletableFuture.complete(EntityTaskResult.SUCCESS);
/*     */         });
/* 248 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntityWithFallback(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable) {
/* 253 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/*     */     
/* 255 */     runNextTick(paramWrappedTask -> {
/*     */           if (isValid(paramEntity)) {
/*     */             paramConsumer.accept(paramWrappedTask);
/*     */             
/*     */             paramCompletableFuture.complete(EntityTaskResult.SUCCESS);
/*     */           } else {
/*     */             paramRunnable.run();
/*     */             paramCompletableFuture.complete(EntityTaskResult.ENTITY_RETIRED);
/*     */           } 
/*     */         });
/* 265 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong) {
/* 270 */     return runAtEntityLater(paramEntity, paramRunnable, (Runnable)null, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable1, Runnable paramRunnable2, long paramLong) {
/* 275 */     if (!isValid(paramEntity)) {
/* 276 */       if (paramRunnable2 != null) paramRunnable2.run(); 
/* 277 */       return null;
/*     */     } 
/*     */     
/* 280 */     return runLater(paramRunnable1, paramLong);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 285 */     return runAtEntityLater(paramEntity, paramConsumer, (Runnable)null, paramLong);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, @Nullable Runnable paramRunnable, long paramLong) {
/* 290 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 292 */     if (!isValid(paramEntity)) {
/* 293 */       if (paramRunnable != null) {
/* 294 */         paramRunnable.run();
/* 295 */         completableFuture.complete(null);
/*     */       } 
/*     */     } else {
/*     */       
/* 299 */       runLater(paramWrappedTask -> { paramConsumer.accept(wrapTask(paramWrappedTask)); paramCompletableFuture.complete(null); }paramLong);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 305 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 310 */     return runAtEntityLater(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 315 */     return runAtEntityLater(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 320 */     return runAtEntityTimer(paramEntity, paramRunnable, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable1, @Nullable Runnable paramRunnable2, long paramLong1, long paramLong2) {
/* 325 */     if (!isValid(paramEntity)) {
/* 326 */       if (paramRunnable2 != null) paramRunnable2.run(); 
/* 327 */       return null;
/*     */     } 
/* 329 */     return runTimer(paramRunnable1, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 335 */     runAtEntityTimer(paramEntity, paramConsumer, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 341 */     if (!isValid(paramEntity)) {
/* 342 */       if (paramRunnable != null) paramRunnable.run();
/*     */       
/*     */       return;
/*     */     } 
/* 346 */     runTimer(paramWrappedTask -> { if (!isValid(paramEntity)) { if (paramRunnable != null) paramRunnable.run();  return; }  paramConsumer.accept(paramWrappedTask); }paramLong1, paramLong2);
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
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 358 */     return runAtEntityTimer(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 363 */     runAtEntityTimer(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelTask(WrappedTask paramWrappedTask) {
/* 368 */     paramWrappedTask.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 373 */     this.scheduler.cancelTasks((Plugin)this.plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllTasks() {
/* 378 */     return (List<WrappedTask>)this.scheduler.getPendingTasks().stream()
/* 379 */       .filter(paramBukkitTask -> paramBukkitTask.getOwner().equals(this.plugin))
/* 380 */       .map(this::wrapTask)
/* 381 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllServerTasks() {
/* 386 */     return (List<WrappedTask>)this.scheduler.getPendingTasks().stream()
/* 387 */       .map(this::wrapTask)
/* 388 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer(String paramString) {
/* 393 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayer(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayerExact(String paramString) {
/* 398 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayerExact(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer(UUID paramUUID) {
/* 403 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayer(paramUUID));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Player getPlayerFromMainThread(Supplier<Player> paramSupplier) {
/* 414 */     if (this.plugin.getServer().isPrimaryThread()) {
/* 415 */       return paramSupplier.get();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 420 */       Objects.requireNonNull(paramSupplier); return this.scheduler.callSyncMethod((Plugin)this.plugin, paramSupplier::get).get();
/* 421 */     } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {
/* 422 */       interruptedException.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 427 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation) {
/* 432 */     return teleportAsync(paramEntity, paramLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation, PlayerTeleportEvent.TeleportCause paramTeleportCause) {
/* 437 */     CompletableFuture<Boolean> completableFuture = new CompletableFuture();
/*     */     
/* 439 */     runAtEntity(paramEntity, paramWrappedTask -> {
/*     */           if (isValid(paramEntity)) {
/*     */             paramEntity.teleport(paramLocation);
/*     */             
/*     */             paramCompletableFuture.complete(Boolean.valueOf(true));
/*     */           } else {
/*     */             paramCompletableFuture.complete(Boolean.valueOf(false));
/*     */           } 
/*     */         });
/* 448 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask wrapTask(Object paramObject) {
/* 453 */     if (!(paramObject instanceof BukkitTask)) {
/* 454 */       String str = (paramObject == null) ? null : paramObject.getClass().getName();
/* 455 */       throw new IllegalArgumentException("The nativeTask provided must be a BukkitTask. Got: " + str + " instead.");
/*     */     } 
/*     */     
/* 458 */     return (WrappedTask)new WrappedBukkitTask((BukkitTask)paramObject);
/*     */   }
/*     */   
/*     */   private boolean isValid(Entity paramEntity) {
/* 462 */     if (paramEntity.isValid()) {
/* 463 */       return (!(paramEntity instanceof Player) || ((Player)paramEntity).isOnline());
/*     */     }
/* 465 */     return (paramEntity instanceof org.bukkit.entity.Projectile && !paramEntity.isDead());
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\SpigotImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */