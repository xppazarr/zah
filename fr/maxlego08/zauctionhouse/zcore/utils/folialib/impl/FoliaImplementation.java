/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.folialib.impl;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.FoliaLib;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.enums.EntityTaskResult;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.InvalidTickDelayNotifier;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.util.TimeConverter;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedFoliaTask;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.folialib.wrapper.task.WrappedTask;
/*     */ import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
/*     */ import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
/*     */ import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
/*     */ import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
/*     */ import it.unimi.dsi.fastutil.objects.ObjectIterator;
/*     */ import java.lang.reflect.Field;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ import java.util.Set;
/*     */ import java.util.UUID;
/*     */ import java.util.concurrent.CompletableFuture;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.function.Consumer;
/*     */ import java.util.stream.Collectors;
/*     */ import org.bukkit.Location;
/*     */ import org.bukkit.World;
/*     */ import org.bukkit.block.Block;
/*     */ import org.bukkit.entity.Entity;
/*     */ import org.bukkit.entity.Player;
/*     */ import org.bukkit.event.player.PlayerTeleportEvent;
/*     */ import org.bukkit.plugin.Plugin;
/*     */ import org.bukkit.plugin.java.JavaPlugin;
/*     */ import org.jetbrains.annotations.NotNull;
/*     */ 
/*     */ public class FoliaImplementation
/*     */   implements PlatformScheduler
/*     */ {
/*     */   private final JavaPlugin plugin;
/*     */   private final GlobalRegionScheduler globalRegionScheduler;
/*     */   private final AsyncScheduler asyncScheduler;
/*     */   
/*     */   public FoliaImplementation(FoliaLib paramFoliaLib) {
/*  41 */     this.plugin = paramFoliaLib.getPlugin();
/*  42 */     this.globalRegionScheduler = this.plugin.getServer().getGlobalRegionScheduler();
/*  43 */     this.asyncScheduler = this.plugin.getServer().getAsyncScheduler();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation) {
/*  48 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Location paramLocation, int paramInt) {
/*  53 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramLocation, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Block paramBlock) {
/*  58 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramBlock);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2) {
/*  63 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramWorld, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull World paramWorld, int paramInt1, int paramInt2, int paramInt3) {
/*  68 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramWorld, paramInt1, paramInt2, paramInt3);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOwnedByCurrentRegion(@NotNull Entity paramEntity) {
/*  73 */     return this.plugin.getServer().isOwnedByCurrentRegion(paramEntity);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isGlobalTickThread() {
/*  78 */     return this.plugin.getServer().isGlobalTickThread();
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runNextTick(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  83 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/*  85 */     this.globalRegionScheduler.run((Plugin)this.plugin, paramScheduledTask -> {
/*     */           paramConsumer.accept(wrapTask(paramScheduledTask));
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/*  90 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAsync(@NotNull Consumer<WrappedTask> paramConsumer) {
/*  95 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/*  97 */     this.asyncScheduler.runNow((Plugin)this.plugin, paramScheduledTask -> {
/*     */           paramConsumer.accept(wrapTask(paramScheduledTask));
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/* 102 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong) {
/* 107 */     if (paramLong <= 0L) {
/* 108 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 109 */       paramLong = 1L;
/*     */     } 
/* 111 */     return wrapTask(this.globalRegionScheduler.runDelayed((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 116 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 118 */     if (paramLong <= 0L) {
/* 119 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 120 */       paramLong = 1L;
/*     */     } 
/* 122 */     this.globalRegionScheduler.runDelayed((Plugin)this.plugin, paramScheduledTask -> { paramConsumer.accept(wrapTask(paramScheduledTask)); paramCompletableFuture.complete(null); }paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 127 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLater(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 132 */     return runLater(paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLater(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 137 */     return runLater(paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong) {
/* 142 */     return runLaterAsync(paramRunnable, TimeConverter.toMillis(paramLong), TimeUnit.MILLISECONDS);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 147 */     return runLaterAsync(paramConsumer, TimeConverter.toMillis(paramLong), TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runLaterAsync(@NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 152 */     return wrapTask(this.asyncScheduler
/* 153 */         .runDelayed((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runLaterAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 159 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 161 */     this.asyncScheduler.runDelayed((Plugin)this.plugin, paramScheduledTask -> { paramConsumer.accept(wrapTask(paramScheduledTask)); paramCompletableFuture.complete(null); }paramLong, paramTimeUnit);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 166 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 171 */     if (paramLong1 <= 0L) {
/* 172 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 173 */       paramLong1 = 1L;
/*     */     } 
/* 175 */     if (paramLong2 <= 0L) {
/* 176 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 177 */       paramLong2 = 1L;
/*     */     } 
/* 179 */     return wrapTask(this.globalRegionScheduler
/* 180 */         .runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 186 */     if (paramLong1 <= 0L) {
/* 187 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 188 */       paramLong1 = 1L;
/*     */     } 
/* 190 */     if (paramLong2 <= 0L) {
/* 191 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 192 */       paramLong2 = 1L;
/*     */     } 
/* 194 */     this.globalRegionScheduler.runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramConsumer.accept(wrapTask(paramScheduledTask)), paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimer(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 199 */     return runTimer(paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runTimer(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 204 */     runTimer(paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 209 */     return runTimerAsync(paramRunnable, 
/* 210 */         TimeConverter.toMillis(paramLong1), TimeConverter.toMillis(paramLong2), TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 216 */     runTimerAsync(paramConsumer, 
/* 217 */         TimeConverter.toMillis(paramLong1), TimeConverter.toMillis(paramLong2), TimeUnit.MILLISECONDS);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public WrappedTask runTimerAsync(@NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 223 */     return wrapTask(this.asyncScheduler
/* 224 */         .runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramLong1, paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runTimerAsync(@NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 230 */     this.asyncScheduler.runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramConsumer.accept(wrapTask(paramScheduledTask)), paramLong1, paramLong2, paramTimeUnit);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocation(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 235 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 237 */     this.plugin.getServer().getRegionScheduler().run((Plugin)this.plugin, paramLocation, paramScheduledTask -> {
/*     */           paramConsumer.accept(wrapTask(paramScheduledTask));
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/* 242 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong) {
/* 247 */     if (paramLong <= 0L) {
/* 248 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 249 */       paramLong = 1L;
/*     */     } 
/* 251 */     return wrapTask(this.plugin
/* 252 */         .getServer().getRegionScheduler().runDelayed((Plugin)this.plugin, paramLocation, paramScheduledTask -> paramRunnable.run(), paramLong));
/*     */   }
/*     */ 
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 258 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */     
/* 260 */     if (paramLong <= 0L) {
/* 261 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 262 */       paramLong = 1L;
/*     */     } 
/* 264 */     this.plugin.getServer().getRegionScheduler().runDelayed((Plugin)this.plugin, paramLocation, paramScheduledTask -> { paramConsumer.accept(wrapTask(paramScheduledTask)); paramCompletableFuture.complete(null); }paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 269 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationLater(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 274 */     return runAtLocationLater(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtLocationLater(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 279 */     return runAtLocationLater(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 284 */     if (paramLong1 <= 0L) {
/* 285 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 286 */       paramLong1 = 1L;
/*     */     } 
/* 288 */     if (paramLong2 <= 0L) {
/* 289 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 290 */       paramLong2 = 1L;
/*     */     } 
/* 292 */     return wrapTask(this.plugin
/* 293 */         .getServer().getRegionScheduler().runAtFixedRate((Plugin)this.plugin, paramLocation, paramScheduledTask -> paramRunnable.run(), paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 299 */     if (paramLong1 <= 0L) {
/* 300 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 301 */       paramLong1 = 1L;
/*     */     } 
/* 303 */     if (paramLong2 <= 0L) {
/* 304 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 305 */       paramLong2 = 1L;
/*     */     } 
/* 307 */     this.plugin.getServer().getRegionScheduler().runAtFixedRate((Plugin)this.plugin, paramLocation, paramScheduledTask -> paramConsumer.accept(wrapTask(paramScheduledTask)), paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtLocationTimer(Location paramLocation, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 312 */     return runAtLocationTimer(paramLocation, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtLocationTimer(Location paramLocation, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 317 */     runAtLocationTimer(paramLocation, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntity(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer) {
/* 322 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/*     */     
/* 324 */     ScheduledTask scheduledTask = paramEntity.getScheduler().run((Plugin)this.plugin, paramScheduledTask -> { paramConsumer.accept(wrapTask(paramScheduledTask)); paramCompletableFuture.complete(EntityTaskResult.SUCCESS); }null);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 329 */     if (scheduledTask == null) {
/* 330 */       completableFuture.complete(EntityTaskResult.SCHEDULER_RETIRED);
/*     */     }
/*     */     
/* 333 */     return completableFuture;
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<EntityTaskResult> runAtEntityWithFallback(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable) {
/* 338 */     CompletableFuture<EntityTaskResult> completableFuture = new CompletableFuture();
/*     */     
/* 340 */     ScheduledTask scheduledTask = paramEntity.getScheduler().run((Plugin)this.plugin, paramScheduledTask -> {
/*     */           paramConsumer.accept(wrapTask(paramScheduledTask));
/*     */           
/*     */           paramCompletableFuture.complete(EntityTaskResult.SUCCESS);
/*     */         }() -> {
/*     */           paramRunnable.run();
/*     */           paramCompletableFuture.complete(EntityTaskResult.ENTITY_RETIRED);
/*     */         });
/* 348 */     if (scheduledTask == null) {
/* 349 */       completableFuture.complete(EntityTaskResult.SCHEDULER_RETIRED);
/*     */     }
/*     */     
/* 352 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong) {
/* 357 */     return runAtEntityLater(paramEntity, paramRunnable, (Runnable)null, paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable1, Runnable paramRunnable2, long paramLong) {
/* 362 */     if (paramLong <= 0L) {
/* 363 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 364 */       paramLong = 1L;
/*     */     } 
/* 366 */     return wrapTask(paramEntity.getScheduler().runDelayed((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramRunnable2, paramLong));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong) {
/* 371 */     return runAtEntityLater(paramEntity, paramConsumer, (Runnable)null, paramLong);
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong) {
/* 376 */     CompletableFuture<Void> completableFuture = new CompletableFuture();
/*     */ 
/*     */     
/* 379 */     if (paramRunnable != null) {
/* 380 */       Runnable runnable = paramRunnable;
/* 381 */       paramRunnable = (() -> {
/*     */           paramRunnable.run();
/*     */           
/*     */           paramCompletableFuture.complete(null);
/*     */         });
/*     */     } 
/* 387 */     if (paramLong <= 0L) {
/* 388 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong);
/* 389 */       paramLong = 1L;
/*     */     } 
/* 391 */     paramEntity.getScheduler().runDelayed((Plugin)this.plugin, paramScheduledTask -> { paramConsumer.accept(wrapTask(paramScheduledTask)); paramCompletableFuture.complete(null); }paramRunnable, paramLong);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 396 */     return completableFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityLater(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong, TimeUnit paramTimeUnit) {
/* 401 */     return runAtEntityLater(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   public CompletableFuture<Void> runAtEntityLater(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong, TimeUnit paramTimeUnit) {
/* 406 */     return runAtEntityLater(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 411 */     return runAtEntityTimer(paramEntity, paramRunnable, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable1, Runnable paramRunnable2, long paramLong1, long paramLong2) {
/* 416 */     if (paramLong1 <= 0L) {
/* 417 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 418 */       paramLong1 = 1L;
/*     */     } 
/* 420 */     if (paramLong2 <= 0L) {
/* 421 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 422 */       paramLong2 = 1L;
/*     */     } 
/* 424 */     return wrapTask(paramEntity
/* 425 */         .getScheduler().runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramRunnable.run(), paramRunnable2, paramLong1, paramLong2));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2) {
/* 431 */     runAtEntityTimer(paramEntity, paramConsumer, (Runnable)null, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, Runnable paramRunnable, long paramLong1, long paramLong2) {
/* 436 */     if (paramLong1 <= 0L) {
/* 437 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong1);
/* 438 */       paramLong1 = 1L;
/*     */     } 
/* 440 */     if (paramLong2 <= 0L) {
/* 441 */       InvalidTickDelayNotifier.notifyOnce(this.plugin.getLogger(), paramLong2);
/* 442 */       paramLong2 = 1L;
/*     */     } 
/* 444 */     paramEntity.getScheduler().runAtFixedRate((Plugin)this.plugin, paramScheduledTask -> paramConsumer.accept(wrapTask(paramScheduledTask)), paramRunnable, paramLong1, paramLong2);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask runAtEntityTimer(Entity paramEntity, @NotNull Runnable paramRunnable, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 449 */     return runAtEntityTimer(paramEntity, paramRunnable, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void runAtEntityTimer(Entity paramEntity, @NotNull Consumer<WrappedTask> paramConsumer, long paramLong1, long paramLong2, TimeUnit paramTimeUnit) {
/* 454 */     runAtEntityTimer(paramEntity, paramConsumer, TimeConverter.toTicks(paramLong1, paramTimeUnit), TimeConverter.toTicks(paramLong2, paramTimeUnit));
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelTask(WrappedTask paramWrappedTask) {
/* 459 */     paramWrappedTask.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   public void cancelAllTasks() {
/* 464 */     this.globalRegionScheduler.cancelTasks((Plugin)this.plugin);
/* 465 */     this.asyncScheduler.cancelTasks((Plugin)this.plugin);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllTasks() {
/*     */     try {
/* 472 */       return (List<WrappedTask>)getAllScheduledTasks().stream()
/* 473 */         .filter(paramScheduledTask -> paramScheduledTask.getOwningPlugin().equals(this.plugin))
/* 474 */         .map(this::wrapTask)
/* 475 */         .collect(Collectors.toList());
/* 476 */     } catch (Exception exception) {
/* 477 */       exception.printStackTrace();
/*     */ 
/*     */       
/* 480 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public List<WrappedTask> getAllServerTasks() {
/*     */     try {
/* 487 */       return (List<WrappedTask>)getAllScheduledTasks().stream()
/* 488 */         .map(this::wrapTask)
/* 489 */         .collect(Collectors.toList());
/* 490 */     } catch (Exception exception) {
/* 491 */       exception.printStackTrace();
/*     */ 
/*     */       
/* 494 */       return null;
/*     */     } 
/*     */   }
/*     */   
/*     */   @NotNull
/*     */   private List<ScheduledTask> getAllScheduledTasks() {
/* 500 */     Class<?> clazz1 = this.globalRegionScheduler.getClass();
/*     */     
/* 502 */     Field field1 = clazz1.getDeclaredField("tasksByDeadline");
/* 503 */     boolean bool = field1.isAccessible();
/* 504 */     field1.setAccessible(true);
/*     */ 
/*     */     
/* 507 */     Long2ObjectOpenHashMap long2ObjectOpenHashMap = (Long2ObjectOpenHashMap)field1.get(this.globalRegionScheduler);
/* 508 */     field1.setAccessible(bool);
/*     */ 
/*     */     
/* 511 */     Class<?> clazz2 = this.asyncScheduler.getClass();
/*     */     
/* 513 */     Field field2 = clazz2.getDeclaredField("tasks");
/* 514 */     bool = field2.isAccessible();
/* 515 */     field2.setAccessible(true);
/*     */     
/* 517 */     Set set = (Set)field2.get(this.asyncScheduler);
/* 518 */     field2.setAccessible(bool);
/*     */ 
/*     */     
/* 521 */     ArrayList arrayList = new ArrayList();
/* 522 */     for (ObjectIterator<List> objectIterator = long2ObjectOpenHashMap.values().iterator(); objectIterator.hasNext(); ) { List list = objectIterator.next();
/* 523 */       arrayList.addAll(list); }
/*     */ 
/*     */ 
/*     */     
/* 527 */     ArrayList<ScheduledTask> arrayList1 = new ArrayList(arrayList.size() + set.size());
/* 528 */     arrayList1.addAll(arrayList);
/* 529 */     arrayList1.addAll(set);
/* 530 */     return arrayList1;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer(String paramString) {
/* 536 */     return this.plugin.getServer().getPlayer(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayerExact(String paramString) {
/* 542 */     return this.plugin.getServer().getPlayerExact(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public Player getPlayer(UUID paramUUID) {
/* 548 */     return this.plugin.getServer().getPlayer(paramUUID);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation) {
/* 553 */     return paramEntity.teleportAsync(paramLocation);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompletableFuture<Boolean> teleportAsync(Entity paramEntity, Location paramLocation, PlayerTeleportEvent.TeleportCause paramTeleportCause) {
/* 558 */     return paramEntity.teleportAsync(paramLocation, paramTeleportCause);
/*     */   }
/*     */ 
/*     */   
/*     */   public WrappedTask wrapTask(Object paramObject) {
/* 563 */     if (paramObject == null) {
/* 564 */       return null;
/*     */     }
/*     */     
/* 567 */     if (!(paramObject instanceof ScheduledTask)) {
/* 568 */       String str = paramObject.getClass().getName();
/* 569 */       throw new IllegalArgumentException("The nativeTask provided must be a ScheduledTask. Got: " + str + " instead.");
/*     */     } 
/*     */     
/* 572 */     return (WrappedTask)new WrappedFoliaTask((ScheduledTask)paramObject);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\folialib\impl\FoliaImplementation.class
 * Java compiler version: 17 (61.0)
 * JD-Core Version:       1.1.3
 */