/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums.EntityTaskResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.ImplementationTestsUtil;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.TimeConverter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedBukkitTask;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedLegacyBukkitTask;
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
/*     */ public class LegacySpigotImplementation
/*     */   implements PlatformScheduler
/*     */ {
/*     */   private final JavaPlugin plugin;
/*     */   @NotNull
/*     */   private final BukkitScheduler scheduler;
/*     */   
/*     */   public LegacySpigotImplementation(FoliaLib paramFoliaLib) {
/*  39 */     this.plugin = paramFoliaLib.getPlugin();
/*  40 */     this.scheduler = this.plugin.getServer().getScheduler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation) {
/*  45 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation, int paramInt) {
/*  50 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Block paramBlock) {
/*  55 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2) {
/*  60 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  65 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Entity paramEntity) {
/*  70 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalTickThread() {
/*  75 */     return this.plugin.getServer().isPrimaryThread();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runNextTick(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  80 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  81 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/*  83 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTask((Plugin)this.plugin, () -> {
/*     */             paramConsumer.accept(paramArrayOfWrappedTask[0]);
/*     */             
/*     */             paramCompletableFuture.complete(null);
/*     */           }));
/*  88 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAsync(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  93 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*  94 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/*  96 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskAsynchronously((Plugin)this.plugin, () -> {
/*     */             paramConsumer.accept(paramArrayOfWrappedTask[0]);
/*     */             
/*     */             paramCompletableFuture.complete(null);
/*     */           }));
/* 101 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong) {
/* 106 */     return wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, paramRunnable, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 111 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/* 112 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 114 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, () -> { paramConsumer.accept(paramArrayOfWrappedTask[0]); paramCompletableFuture.complete(null); }paramLong));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 119 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 124 */     return runLater(paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 129 */     return runLater(paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong) {
/* 134 */     return wrapTask(this.scheduler.runTaskLaterAsynchronously((Plugin)this.plugin, paramRunnable, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 139 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/* 140 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 142 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskLaterAsynchronously((Plugin)this.plugin, () -> { paramConsumer.accept(paramArrayOfWrappedTask[0]); paramCompletableFuture.complete(null); }paramLong));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 147 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 152 */     return runLaterAsync(paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 157 */     return runLaterAsync(paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 162 */     return wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, paramRunnable, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 167 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 169 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, () -> paramConsumer.accept(paramArrayOfWrappedTask[0]), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 176 */     return runTimer(paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 181 */     runTimer(paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 186 */     return wrapTask(this.scheduler.runTaskTimerAsynchronously((Plugin)this.plugin, paramRunnable, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 191 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 193 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskTimerAsynchronously((Plugin)this.plugin, () -> paramConsumer.accept(paramArrayOfWrappedTask[0]), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 200 */     return runTimerAsync(paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 205 */     runTimerAsync(paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocation(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 210 */     return runNextTick(paramConsumer);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong) {
/* 215 */     return wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, paramRunnable, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 220 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/* 221 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 223 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, () -> { paramConsumer.accept(paramArrayOfWrappedTask[0]); paramCompletableFuture.complete(null); }paramLong));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 228 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 233 */     return runAtLocationLater(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 238 */     return runAtLocationLater(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 243 */     return wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, paramRunnable, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 248 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 250 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, () -> paramConsumer.accept(paramArrayOfWrappedTask[0]), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 257 */     return runAtLocationTimer(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 262 */     runAtLocationTimer(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntity(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 267 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/* 268 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 270 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTask((Plugin)this.plugin, () -> {
/*     */             paramConsumer.accept(paramArrayOfWrappedTask[0]);
/*     */             
/*     */             paramCompletableFuture.complete(EntityTaskResult.SUCCESS);
/*     */           }));
/* 275 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntityWithFallback(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable) {
/* 280 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/* 281 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 283 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTask((Plugin)this.plugin, () -> {
/*     */             if (isValid(paramEntity)) {
/*     */               paramConsumer.accept(paramArrayOfWrappedTask[0]);
/*     */               
/*     */               paramCompletableFuture.complete(EntityTaskResult.SUCCESS);
/*     */             } else {
/*     */               paramRunnable.run();
/*     */               paramCompletableFuture.complete(EntityTaskResult.ENTITY_RETIRED);
/*     */             } 
/*     */           }));
/* 293 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong) {
/* 298 */     return runAtEntityLater(paramEntity, paramRunnable, (Runnable)null, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable1, @Nullable Runnable paramRunnable2, long paramLong) {
/* 303 */     if (!isValid(paramEntity)) {
/* 304 */       if (paramRunnable2 != null) paramRunnable2.run(); 
/* 305 */       return null;
/*     */     } 
/* 307 */     return wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, paramRunnable1, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 312 */     return runAtEntityLater(paramEntity, paramConsumer, (Runnable)null, paramLong);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong) {
/* 317 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 319 */     if (!isValid(paramEntity) && 
/* 320 */       paramRunnable != null) {
/* 321 */       paramRunnable.run();
/* 322 */       completableFuture.complete(null);
/*     */     } 
/*     */     
/* 325 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 327 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskLater((Plugin)this.plugin, () -> { paramConsumer.accept(paramArrayOfWrappedTask[0]); paramCompletableFuture.complete(null); }paramLong));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 332 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 337 */     return runAtEntityLater(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 342 */     return runAtEntityLater(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 347 */     return runAtEntityTimer(paramEntity, paramRunnable, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable1, Runnable paramRunnable2, long paramLong1, long paramLong2) {
/* 352 */     if (!isValid(paramEntity)) {
/* 353 */       if (paramRunnable2 != null) paramRunnable2.run(); 
/* 354 */       return null;
/*     */     } 
/* 356 */     return wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, paramRunnable1, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 361 */     runAtEntityTimer(paramEntity, paramConsumer, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 366 */     if (!isValid(paramEntity) && 
/* 367 */       paramRunnable != null) paramRunnable.run();
/*     */ 
/*     */     
/* 370 */     WrappedTask[] arrayOfWrappedTask = new WrappedTask[1];
/*     */     
/* 372 */     arrayOfWrappedTask[0] = wrapTask(this.scheduler.runTaskTimer((Plugin)this.plugin, () -> paramConsumer.accept(paramArrayOfWrappedTask[0]), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 379 */     return runAtEntityTimer(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 384 */     runAtEntityTimer(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelTask(WrappedTask paramWrappedTask) {
/* 389 */     paramWrappedTask.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 394 */     this.scheduler.cancelTasks((Plugin)this.plugin);
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllTasks() {
/* 399 */     return (List<WrappedTask>)this.scheduler.getPendingTasks().stream()
/* 400 */       .filter(paramBukkitTask -> paramBukkitTask.getOwner().equals(this.plugin))
/* 401 */       .map(this::wrapTask)
/* 402 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllServerTasks() {
/* 407 */     return (List<WrappedTask>)this.scheduler.getPendingTasks().stream()
/* 408 */       .map(this::wrapTask)
/* 409 */       .collect(Collectors.toList());
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer(String paramString) {
/* 414 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayer(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayerExact(String paramString) {
/* 419 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayerExact(paramString));
/*     */   }
/*     */ 
/*     */   
/*     */   public Player getPlayer(UUID paramUUID) {
/* 424 */     return getPlayerFromMainThread(() -> this.plugin.getServer().getPlayer(paramUUID));
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation) {
/* 429 */     return teleportAsync(paramEntity, paramLocation, PlayerTeleportEvent.TeleportCause.PLUGIN);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation, PlayerTeleportEvent.TeleportCause paramTeleportCause) {
/* 434 */     CompletableFuture<Boolean> completableFuture = new CompletableFuture();
/*     */     
/* 436 */     runAtEntity(paramEntity, paramWrappedTask -> {
/*     */           if (isValid(paramEntity)) {
/*     */             paramEntity.teleport(paramLocation);
/*     */             
/*     */             paramCompletableFuture.complete(Boolean.valueOf(true));
/*     */           } else {
/*     */             paramCompletableFuture.complete(Boolean.valueOf(false));
/*     */           } 
/*     */         });
/* 445 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask wrapTask(Object paramObject) {
/* 450 */     if (!(paramObject instanceof BukkitTask)) {
/* 451 */       String str = (paramObject == null) ? null : paramObject.getClass().getName();
/* 452 */       throw new IllegalArgumentException("The nativeTask provided must be a BukkitTask. Got: " + str + " instead.");
/*     */     } 
/*     */     
/* 455 */     return ImplementationTestsUtil.isCancelledSupported() ? 
/* 456 */       (WrappedTask)new WrappedBukkitTask((BukkitTask)paramObject) : 
/* 457 */       (WrappedTask)new WrappedLegacyBukkitTask((BukkitTask)paramObject);
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
/*     */   private Player getPlayerFromMainThread(Supplier<Player> paramSupplier) {
/* 469 */     if (this.plugin.getServer().isPrimaryThread()) {
/* 470 */       return paramSupplier.get();
/*     */     }
/*     */ 
/*     */     
/*     */     try {
/* 475 */       Objects.requireNonNull(paramSupplier); return this.scheduler.callSyncMethod((Plugin)this.plugin, paramSupplier::get).get();
/* 476 */     } catch (InterruptedException|java.util.concurrent.ExecutionException interruptedException) {
/* 477 */       interruptedException.printStackTrace();
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 482 */       return null;
/*     */     } 
/*     */   }
/*     */   private boolean isValid(Entity paramEntity) {
/* 486 */     if (paramEntity.isValid()) {
/* 487 */       return (!(paramEntity instanceof Player) || ((Player)paramEntity).isOnline());
/*     */     }
/* 489 */     return (paramEntity instanceof org.bukkit.entity.Projectile && !paramEntity.isDead());
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\LegacySpigotImplementation.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */