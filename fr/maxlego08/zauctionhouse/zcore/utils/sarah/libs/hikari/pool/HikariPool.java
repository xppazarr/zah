/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariConfig;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariPoolMXBean;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.MetricsTrackerFactory;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.PoolStats;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.dropwizard.CodahaleHealthChecker;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.dropwizard.CodahaleMetricsTrackerFactory;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.micrometer.MicrometerMetricsTrackerFactory;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ClockSource;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ConcurrentBag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.SuspendResumeLock;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.UtilityElf;
/*     */ import io.micrometer.core.instrument.MeterRegistry;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLTransientConnectionException;
/*     */ import java.util.Collection;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import java.util.Optional;
/*     */ import java.util.concurrent.Callable;
/*     */ import java.util.concurrent.ExecutorService;
/*     */ import java.util.concurrent.LinkedBlockingQueue;
/*     */ import java.util.concurrent.ScheduledExecutorService;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadLocalRandom;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import javax.sql.DataSource;
/*     */ import org.slf4j.Logger;
/*     */ import org.slf4j.LoggerFactory;
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
/*     */ public final class HikariPool
/*     */   extends PoolBase
/*     */   implements HikariPoolMXBean, ConcurrentBag.IBagStateListener
/*     */ {
/*  73 */   private final Logger logger = LoggerFactory.getLogger(HikariPool.class);
/*     */   
/*     */   public static final int POOL_NORMAL = 0;
/*     */   
/*     */   public static final int POOL_SUSPENDED = 1;
/*     */   
/*     */   public static final int POOL_SHUTDOWN = 2;
/*     */   public volatile int poolState;
/*  81 */   private final long aliveBypassWindowMs = Long.getLong("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.aliveBypassWindowMs", TimeUnit.MILLISECONDS.toMillis(500L)).longValue();
/*  82 */   private final long housekeepingPeriodMs = Long.getLong("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.housekeeping.periodMs", TimeUnit.SECONDS.toMillis(30L)).longValue();
/*     */   
/*     */   private static final String EVICTED_CONNECTION_MESSAGE = "(connection was evicted)";
/*     */   
/*     */   private static final String DEAD_CONNECTION_MESSAGE = "(connection is dead)";
/*  87 */   private final PoolEntryCreator poolEntryCreator = new PoolEntryCreator(null);
/*  88 */   private final PoolEntryCreator postFillPoolEntryCreator = new PoolEntryCreator("After adding ");
/*     */   
/*     */   private final Collection<Runnable> addConnectionQueueReadOnlyView;
/*     */   
/*     */   private final ThreadPoolExecutor addConnectionExecutor;
/*     */   
/*     */   private final ThreadPoolExecutor closeConnectionExecutor;
/*     */   
/*     */   private final ConcurrentBag<PoolEntry> connectionBag;
/*     */   
/*     */   private final ProxyLeakTaskFactory leakTaskFactory;
/*     */   
/*     */   private final SuspendResumeLock suspendResumeLock;
/*     */   
/*     */   private final ScheduledExecutorService houseKeepingExecutorService;
/*     */   
/*     */   private ScheduledFuture<?> houseKeeperTask;
/*     */ 
/*     */   
/*     */   public HikariPool(HikariConfig paramHikariConfig) {
/* 108 */     super(paramHikariConfig);
/*     */     
/* 110 */     this.connectionBag = new ConcurrentBag(this);
/* 111 */     this.suspendResumeLock = paramHikariConfig.isAllowPoolSuspension() ? new SuspendResumeLock() : SuspendResumeLock.FAUX_LOCK;
/*     */     
/* 113 */     this.houseKeepingExecutorService = initializeHouseKeepingExecutorService();
/*     */     
/* 115 */     checkFailFast();
/*     */     
/* 117 */     if (paramHikariConfig.getMetricsTrackerFactory() != null) {
/* 118 */       setMetricsTrackerFactory(paramHikariConfig.getMetricsTrackerFactory());
/*     */     } else {
/*     */       
/* 121 */       setMetricRegistry(paramHikariConfig.getMetricRegistry());
/*     */     } 
/*     */     
/* 124 */     setHealthCheckRegistry(paramHikariConfig.getHealthCheckRegistry());
/*     */     
/* 126 */     handleMBeans(this, true);
/*     */     
/* 128 */     ThreadFactory threadFactory = paramHikariConfig.getThreadFactory();
/*     */     
/* 130 */     int i = paramHikariConfig.getMaximumPoolSize();
/* 131 */     LinkedBlockingQueue<? extends Runnable> linkedBlockingQueue = new LinkedBlockingQueue(i);
/* 132 */     this.addConnectionQueueReadOnlyView = Collections.unmodifiableCollection(linkedBlockingQueue);
/* 133 */     this.addConnectionExecutor = UtilityElf.createThreadPoolExecutor(linkedBlockingQueue, this.poolName + " connection adder", threadFactory, new ThreadPoolExecutor.DiscardOldestPolicy());
/* 134 */     this.closeConnectionExecutor = UtilityElf.createThreadPoolExecutor(i, this.poolName + " connection closer", threadFactory, new ThreadPoolExecutor.CallerRunsPolicy());
/*     */     
/* 136 */     this.leakTaskFactory = new ProxyLeakTaskFactory(paramHikariConfig.getLeakDetectionThreshold(), this.houseKeepingExecutorService);
/*     */     
/* 138 */     this.houseKeeperTask = this.houseKeepingExecutorService.scheduleWithFixedDelay(new HouseKeeper(), 100L, this.housekeepingPeriodMs, TimeUnit.MILLISECONDS);
/*     */     
/* 140 */     if (Boolean.getBoolean("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.blockUntilFilled") && paramHikariConfig.getInitializationFailTimeout() > 1L) {
/* 141 */       this.addConnectionExecutor.setMaximumPoolSize(Math.min(16, Runtime.getRuntime().availableProcessors()));
/* 142 */       this.addConnectionExecutor.setCorePoolSize(Math.min(16, Runtime.getRuntime().availableProcessors()));
/*     */       
/* 144 */       long l = ClockSource.currentTime();
/* 145 */       while (ClockSource.elapsedMillis(l) < paramHikariConfig.getInitializationFailTimeout() && getTotalConnections() < paramHikariConfig.getMinimumIdle()) {
/* 146 */         UtilityElf.quietlySleep(TimeUnit.MILLISECONDS.toMillis(100L));
/*     */       }
/*     */       
/* 149 */       this.addConnectionExecutor.setCorePoolSize(1);
/* 150 */       this.addConnectionExecutor.setMaximumPoolSize(1);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 162 */     return getConnection(this.connectionTimeout);
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
/*     */   public Connection getConnection(long paramLong) {
/* 174 */     this.suspendResumeLock.acquire();
/* 175 */     long l = ClockSource.currentTime();
/*     */     
/*     */     try {
/* 178 */       long l1 = paramLong;
/*     */       do {
/* 180 */         PoolEntry poolEntry = (PoolEntry)this.connectionBag.borrow(l1, TimeUnit.MILLISECONDS);
/* 181 */         if (poolEntry == null) {
/*     */           break;
/*     */         }
/*     */         
/* 185 */         long l2 = ClockSource.currentTime();
/* 186 */         if (poolEntry.isMarkedEvicted() || (ClockSource.elapsedMillis(poolEntry.lastAccessed, l2) > this.aliveBypassWindowMs && !isConnectionAlive(poolEntry.connection))) {
/* 187 */           closeConnection(poolEntry, poolEntry.isMarkedEvicted() ? "(connection was evicted)" : "(connection is dead)");
/* 188 */           l1 = paramLong - ClockSource.elapsedMillis(l);
/*     */         } else {
/*     */           
/* 191 */           this.metricsTracker.recordBorrowStats(poolEntry, l);
/* 192 */           return poolEntry.createProxyConnection(this.leakTaskFactory.schedule(poolEntry), l2);
/*     */         } 
/* 194 */       } while (l1 > 0L);
/*     */       
/* 196 */       this.metricsTracker.recordBorrowTimeoutStats(l);
/* 197 */       throw createTimeoutException(l);
/*     */     }
/* 199 */     catch (InterruptedException interruptedException) {
/* 200 */       Thread.currentThread().interrupt();
/* 201 */       throw new SQLException(this.poolName + " - Interrupted during connection acquisition", interruptedException);
/*     */     } finally {
/*     */       
/* 204 */       this.suspendResumeLock.release();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void shutdown() {
/*     */     try {
/* 217 */       this.poolState = 2;
/*     */       
/* 219 */       if (this.addConnectionExecutor == null) {
/*     */         return;
/*     */       }
/*     */       
/* 223 */       logPoolState(new String[] { "Before shutdown " });
/*     */       
/* 225 */       if (this.houseKeeperTask != null) {
/* 226 */         this.houseKeeperTask.cancel(false);
/* 227 */         this.houseKeeperTask = null;
/*     */       } 
/*     */       
/* 230 */       softEvictConnections();
/*     */       
/* 232 */       this.addConnectionExecutor.shutdown();
/* 233 */       this.addConnectionExecutor.awaitTermination(getLoginTimeout(), TimeUnit.SECONDS);
/*     */       
/* 235 */       destroyHouseKeepingExecutorService();
/*     */       
/* 237 */       this.connectionBag.close();
/*     */       
/* 239 */       ThreadPoolExecutor threadPoolExecutor = UtilityElf.createThreadPoolExecutor(this.config.getMaximumPoolSize(), this.poolName + " connection assassinator", this.config
/* 240 */           .getThreadFactory(), new ThreadPoolExecutor.CallerRunsPolicy());
/*     */       try {
/* 242 */         long l = ClockSource.currentTime();
/*     */         do {
/* 244 */           abortActiveConnections(threadPoolExecutor);
/* 245 */           softEvictConnections();
/* 246 */         } while (getTotalConnections() > 0 && ClockSource.elapsedMillis(l) < TimeUnit.SECONDS.toMillis(10L));
/*     */       } finally {
/*     */         
/* 249 */         threadPoolExecutor.shutdown();
/* 250 */         threadPoolExecutor.awaitTermination(10L, TimeUnit.SECONDS);
/*     */       } 
/*     */       
/* 253 */       shutdownNetworkTimeoutExecutor();
/* 254 */       this.closeConnectionExecutor.shutdown();
/* 255 */       this.closeConnectionExecutor.awaitTermination(10L, TimeUnit.SECONDS);
/*     */     } finally {
/*     */       
/* 258 */       logPoolState(new String[] { "After shutdown " });
/* 259 */       handleMBeans(this, false);
/* 260 */       this.metricsTracker.close();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void evictConnection(Connection paramConnection) {
/* 271 */     ProxyConnection proxyConnection = (ProxyConnection)paramConnection;
/* 272 */     proxyConnection.cancelLeakTask();
/*     */     
/*     */     try {
/* 275 */       softEvictConnection(proxyConnection.getPoolEntry(), "(connection evicted by user)", !paramConnection.isClosed());
/*     */     }
/* 277 */     catch (SQLException sQLException) {}
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
/*     */   public void setMetricRegistry(Object paramObject) {
/* 290 */     if (paramObject != null && UtilityElf.safeIsAssignableFrom(paramObject, "com.codahale.metrics.MetricRegistry")) {
/* 291 */       setMetricsTrackerFactory((MetricsTrackerFactory)new CodahaleMetricsTrackerFactory((MetricRegistry)paramObject));
/*     */     }
/* 293 */     else if (paramObject != null && UtilityElf.safeIsAssignableFrom(paramObject, "io.micrometer.core.instrument.MeterRegistry")) {
/* 294 */       setMetricsTrackerFactory((MetricsTrackerFactory)new MicrometerMetricsTrackerFactory((MeterRegistry)paramObject));
/*     */     } else {
/*     */       
/* 297 */       setMetricsTrackerFactory((MetricsTrackerFactory)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setMetricsTrackerFactory(MetricsTrackerFactory paramMetricsTrackerFactory) {
/* 308 */     if (paramMetricsTrackerFactory != null) {
/* 309 */       this.metricsTracker = new PoolBase.MetricsTrackerDelegate(paramMetricsTrackerFactory.create(this.config.getPoolName(), getPoolStats()));
/*     */     } else {
/*     */       
/* 312 */       this.metricsTracker = new PoolBase.NopMetricsTrackerDelegate();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setHealthCheckRegistry(Object paramObject) {
/* 324 */     if (paramObject != null) {
/* 325 */       CodahaleHealthChecker.registerHealthChecks(this, this.config, (HealthCheckRegistry)paramObject);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void addBagItem(int paramInt) {
/* 337 */     boolean bool = (paramInt - this.addConnectionQueueReadOnlyView.size() >= 0) ? true : false;
/* 338 */     if (bool) {
/* 339 */       this.addConnectionExecutor.submit(this.poolEntryCreator);
/*     */     } else {
/*     */       
/* 342 */       this.logger.debug("{} - Add connection elided, waiting {}, queue {}", new Object[] { this.poolName, Integer.valueOf(paramInt), Integer.valueOf(this.addConnectionQueueReadOnlyView.size()) });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getActiveConnections() {
/* 354 */     return this.connectionBag.getCount(1);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getIdleConnections() {
/* 361 */     return this.connectionBag.getCount(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getTotalConnections() {
/* 368 */     return this.connectionBag.size();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getThreadsAwaitingConnection() {
/* 375 */     return this.connectionBag.getWaitingThreadCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void softEvictConnections() {
/* 382 */     this.connectionBag.values().forEach(paramPoolEntry -> softEvictConnection(paramPoolEntry, "(connection evicted)", false));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void suspendPool() {
/* 389 */     if (this.suspendResumeLock == SuspendResumeLock.FAUX_LOCK) {
/* 390 */       throw new IllegalStateException(this.poolName + " - is not suspendable");
/*     */     }
/* 392 */     if (this.poolState != 1) {
/* 393 */       this.suspendResumeLock.suspend();
/* 394 */       this.poolState = 1;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public synchronized void resumePool() {
/* 402 */     if (this.poolState == 1) {
/* 403 */       this.poolState = 0;
/* 404 */       fillPool();
/* 405 */       this.suspendResumeLock.resume();
/*     */     } 
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
/*     */   void logPoolState(String... paramVarArgs) {
/* 420 */     if (this.logger.isDebugEnabled()) {
/* 421 */       this.logger.debug("{} - {}stats (total={}, active={}, idle={}, waiting={})", new Object[] { this.poolName, 
/* 422 */             (paramVarArgs.length > 0) ? paramVarArgs[0] : "", 
/* 423 */             Integer.valueOf(getTotalConnections()), Integer.valueOf(getActiveConnections()), Integer.valueOf(getIdleConnections()), Integer.valueOf(getThreadsAwaitingConnection()) });
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void recycle(PoolEntry paramPoolEntry) {
/* 435 */     this.metricsTracker.recordConnectionUsage(paramPoolEntry);
/*     */     
/* 437 */     this.connectionBag.requite(paramPoolEntry);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void closeConnection(PoolEntry paramPoolEntry, String paramString) {
/* 448 */     if (this.connectionBag.remove(paramPoolEntry)) {
/* 449 */       Connection connection = paramPoolEntry.close();
/* 450 */       this.closeConnectionExecutor.execute(() -> {
/*     */             quietlyCloseConnection(paramConnection, paramString);
/*     */             if (this.poolState == 0) {
/*     */               fillPool();
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   int[] getPoolStateCounts() {
/* 461 */     return this.connectionBag.getStateCounts();
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
/*     */   private PoolEntry createPoolEntry() {
/*     */     try {
/* 476 */       PoolEntry poolEntry = newPoolEntry();
/*     */       
/* 478 */       long l1 = this.config.getMaxLifetime();
/* 479 */       if (l1 > 0L) {
/*     */         
/* 481 */         long l3 = (l1 > 10000L) ? ThreadLocalRandom.current().nextLong(l1 / 40L) : 0L;
/* 482 */         long l4 = l1 - l3;
/* 483 */         poolEntry.setFutureEol(this.houseKeepingExecutorService.schedule(new MaxLifetimeTask(poolEntry), l4, TimeUnit.MILLISECONDS));
/*     */       } 
/*     */       
/* 486 */       long l2 = this.config.getKeepaliveTime();
/* 487 */       if (l2 > 0L) {
/*     */         
/* 489 */         long l3 = ThreadLocalRandom.current().nextLong(l2 / 10L);
/* 490 */         long l4 = l2 - l3;
/* 491 */         poolEntry.setKeepalive(this.houseKeepingExecutorService.scheduleWithFixedDelay(new KeepaliveTask(poolEntry), l4, l4, TimeUnit.MILLISECONDS));
/*     */       } 
/*     */       
/* 494 */       return poolEntry;
/*     */     }
/* 496 */     catch (ConnectionSetupException connectionSetupException) {
/* 497 */       if (this.poolState == 0) {
/* 498 */         this.logger.error("{} - Error thrown while acquiring connection from data source", this.poolName, connectionSetupException.getCause());
/* 499 */         this.lastConnectionFailure.set(connectionSetupException);
/*     */       }
/*     */     
/* 502 */     } catch (Exception exception) {
/* 503 */       if (this.poolState == 0) {
/* 504 */         this.logger.debug("{} - Cannot acquire connection from data source", this.poolName, exception);
/*     */       }
/*     */     } 
/*     */     
/* 508 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void fillPool() {
/* 517 */     int i = Math.min(this.config.getMaximumPoolSize() - getTotalConnections(), this.config.getMinimumIdle() - getIdleConnections()) - this.addConnectionQueueReadOnlyView.size();
/* 518 */     if (i <= 0) this.logger.debug("{} - Fill pool skipped, pool is at sufficient level.", this.poolName);
/*     */     
/* 520 */     for (byte b = 0; b < i; b++) {
/* 521 */       this.addConnectionExecutor.submit((b < i - 1) ? this.poolEntryCreator : this.postFillPoolEntryCreator);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void abortActiveConnections(ExecutorService paramExecutorService) {
/* 532 */     for (PoolEntry poolEntry : this.connectionBag.values(1)) {
/* 533 */       Connection connection = poolEntry.close();
/*     */       try {
/* 535 */         connection.abort(paramExecutorService);
/*     */       }
/* 537 */       catch (Throwable throwable) {
/* 538 */         quietlyCloseConnection(connection, "(connection aborted during shutdown)");
/*     */       } finally {
/*     */         
/* 541 */         this.connectionBag.remove(poolEntry);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkFailFast() {
/* 554 */     long l1 = this.config.getInitializationFailTimeout();
/* 555 */     if (l1 < 0L) {
/*     */       return;
/*     */     }
/*     */     
/* 559 */     long l2 = ClockSource.currentTime();
/*     */     do {
/* 561 */       PoolEntry poolEntry = createPoolEntry();
/* 562 */       if (poolEntry != null) {
/* 563 */         if (this.config.getMinimumIdle() > 0) {
/* 564 */           this.connectionBag.add(poolEntry);
/* 565 */           this.logger.debug("{} - Added connection {}", this.poolName, poolEntry.connection);
/*     */         } else {
/*     */           
/* 568 */           quietlyCloseConnection(poolEntry.close(), "(initialization check complete and minimumIdle is zero)");
/*     */         } 
/*     */         
/*     */         return;
/*     */       } 
/*     */       
/* 574 */       if (getLastConnectionFailure() instanceof PoolBase.ConnectionSetupException) {
/* 575 */         throwPoolInitializationException(getLastConnectionFailure().getCause());
/*     */       }
/*     */       
/* 578 */       UtilityElf.quietlySleep(TimeUnit.SECONDS.toMillis(1L));
/* 579 */     } while (ClockSource.elapsedMillis(l2) < l1);
/*     */     
/* 581 */     if (l1 > 0L) {
/* 582 */       throwPoolInitializationException(getLastConnectionFailure());
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void throwPoolInitializationException(Throwable paramThrowable) {
/* 594 */     this.logger.error("{} - Exception during pool initialization.", this.poolName, paramThrowable);
/* 595 */     destroyHouseKeepingExecutorService();
/* 596 */     throw new PoolInitializationException(paramThrowable);
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
/*     */   private boolean softEvictConnection(PoolEntry paramPoolEntry, String paramString, boolean paramBoolean) {
/* 614 */     paramPoolEntry.markEvicted();
/* 615 */     if (paramBoolean || this.connectionBag.reserve(paramPoolEntry)) {
/* 616 */       closeConnection(paramPoolEntry, paramString);
/* 617 */       return true;
/*     */     } 
/*     */     
/* 620 */     return false;
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
/*     */   private ScheduledExecutorService initializeHouseKeepingExecutorService() {
/* 632 */     if (this.config.getScheduledExecutor() == null) {
/* 633 */       ThreadFactory threadFactory = Optional.<ThreadFactory>ofNullable(this.config.getThreadFactory()).orElseGet(() -> new UtilityElf.DefaultThreadFactory(this.poolName + " housekeeper", true));
/* 634 */       ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, threadFactory, new ThreadPoolExecutor.DiscardPolicy());
/* 635 */       scheduledThreadPoolExecutor.setExecuteExistingDelayedTasksAfterShutdownPolicy(false);
/* 636 */       scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
/* 637 */       return scheduledThreadPoolExecutor;
/*     */     } 
/*     */     
/* 640 */     return this.config.getScheduledExecutor();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void destroyHouseKeepingExecutorService() {
/* 649 */     if (this.config.getScheduledExecutor() == null) {
/* 650 */       this.houseKeepingExecutorService.shutdownNow();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private PoolStats getPoolStats() {
/* 661 */     return new PoolStats(TimeUnit.SECONDS.toMillis(1L))
/*     */       {
/*     */         protected void update() {
/* 664 */           this.pendingThreads = HikariPool.this.getThreadsAwaitingConnection();
/* 665 */           this.idleConnections = HikariPool.this.getIdleConnections();
/* 666 */           this.totalConnections = HikariPool.this.getTotalConnections();
/* 667 */           this.activeConnections = HikariPool.this.getActiveConnections();
/* 668 */           this.maxConnections = HikariPool.this.config.getMaximumPoolSize();
/* 669 */           this.minConnections = HikariPool.this.config.getMinimumIdle();
/*     */         }
/*     */       };
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
/*     */   private SQLException createTimeoutException(long paramLong) {
/* 688 */     logPoolState(new String[] { "Timeout failure " });
/* 689 */     this.metricsTracker.recordConnectionTimeout();
/*     */     
/* 691 */     String str = null;
/* 692 */     Exception exception = getLastConnectionFailure();
/* 693 */     if (exception instanceof SQLException) {
/* 694 */       str = ((SQLException)exception).getSQLState();
/*     */     }
/* 696 */     SQLTransientConnectionException sQLTransientConnectionException = new SQLTransientConnectionException(this.poolName + " - Connection is not available, request timed out after " + ClockSource.elapsedMillis(paramLong) + "ms.", str, exception);
/* 697 */     if (exception instanceof SQLException) {
/* 698 */       sQLTransientConnectionException.setNextException((SQLException)exception);
/*     */     }
/*     */     
/* 701 */     return sQLTransientConnectionException;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private final class PoolEntryCreator
/*     */     implements Callable<Boolean>
/*     */   {
/*     */     private final String loggingPrefix;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     PoolEntryCreator(String param1String) {
/* 718 */       this.loggingPrefix = param1String;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public Boolean call() {
/* 724 */       long l = 250L;
/* 725 */       while (HikariPool.this.poolState == 0 && shouldCreateAnotherConnection()) {
/* 726 */         PoolEntry poolEntry = HikariPool.this.createPoolEntry();
/* 727 */         if (poolEntry != null) {
/* 728 */           HikariPool.this.connectionBag.add(poolEntry);
/* 729 */           HikariPool.this.logger.debug("{} - Added connection {}", HikariPool.this.poolName, poolEntry.connection);
/* 730 */           if (this.loggingPrefix != null) {
/* 731 */             HikariPool.this.logPoolState(new String[] { this.loggingPrefix });
/*     */           }
/* 733 */           return Boolean.TRUE;
/*     */         } 
/*     */ 
/*     */         
/* 737 */         if (this.loggingPrefix != null) HikariPool.this.logger.debug("{} - Connection add failed, sleeping with backoff: {}ms", HikariPool.this.poolName, Long.valueOf(l)); 
/* 738 */         UtilityElf.quietlySleep(l);
/* 739 */         l = Math.min(TimeUnit.SECONDS.toMillis(10L), Math.min(HikariPool.this.connectionTimeout, (long)(l * 1.5D)));
/*     */       } 
/*     */ 
/*     */       
/* 743 */       return Boolean.FALSE;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     private synchronized boolean shouldCreateAnotherConnection() {
/* 753 */       return (HikariPool.this.getTotalConnections() < HikariPool.this.config.getMaximumPoolSize() && (HikariPool.this
/* 754 */         .connectionBag.getWaitingThreadCount() > 0 || HikariPool.this.getIdleConnections() < HikariPool.this.config.getMinimumIdle()));
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private final class HouseKeeper
/*     */     implements Runnable
/*     */   {
/* 763 */     private volatile long previous = ClockSource.plusMillis(ClockSource.currentTime(), -HikariPool.this.housekeepingPeriodMs);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     public void run() {
/*     */       try {
/* 770 */         HikariPool.this.connectionTimeout = HikariPool.this.config.getConnectionTimeout();
/* 771 */         HikariPool.this.validationTimeout = HikariPool.this.config.getValidationTimeout();
/* 772 */         HikariPool.this.leakTaskFactory.updateLeakDetectionThreshold(HikariPool.this.config.getLeakDetectionThreshold());
/* 773 */         HikariPool.this.catalog = (HikariPool.this.config.getCatalog() != null && !HikariPool.this.config.getCatalog().equals(HikariPool.this.catalog)) ? HikariPool.this.config.getCatalog() : HikariPool.this.catalog;
/*     */         
/* 775 */         long l1 = HikariPool.this.config.getIdleTimeout();
/* 776 */         long l2 = ClockSource.currentTime();
/*     */ 
/*     */         
/* 779 */         if (ClockSource.plusMillis(l2, 128L) < ClockSource.plusMillis(this.previous, HikariPool.this.housekeepingPeriodMs)) {
/* 780 */           HikariPool.this.logger.warn("{} - Retrograde clock change detected (housekeeper delta={}), soft-evicting connections from pool.", HikariPool.this.poolName, 
/* 781 */               ClockSource.elapsedDisplayString(this.previous, l2));
/* 782 */           this.previous = l2;
/* 783 */           HikariPool.this.softEvictConnections();
/*     */           return;
/*     */         } 
/* 786 */         if (l2 > ClockSource.plusMillis(this.previous, 3L * HikariPool.this.housekeepingPeriodMs / 2L))
/*     */         {
/* 788 */           HikariPool.this.logger.warn("{} - Thread starvation or clock leap detected (housekeeper delta={}).", HikariPool.this.poolName, ClockSource.elapsedDisplayString(this.previous, l2));
/*     */         }
/*     */         
/* 791 */         this.previous = l2;
/*     */         
/* 793 */         String str = "Pool ";
/* 794 */         if (l1 > 0L && HikariPool.this.config.getMinimumIdle() < HikariPool.this.config.getMaximumPoolSize()) {
/* 795 */           HikariPool.this.logPoolState(new String[] { "Before cleanup " });
/* 796 */           str = "After cleanup  ";
/*     */           
/* 798 */           List list = HikariPool.this.connectionBag.values(0);
/* 799 */           int i = list.size() - HikariPool.this.config.getMinimumIdle();
/* 800 */           for (PoolEntry poolEntry : list) {
/* 801 */             if (i > 0 && ClockSource.elapsedMillis(poolEntry.lastAccessed, l2) > l1 && HikariPool.this.connectionBag.reserve(poolEntry)) {
/* 802 */               HikariPool.this.closeConnection(poolEntry, "(connection has passed idleTimeout)");
/* 803 */               i--;
/*     */             } 
/*     */           } 
/*     */         } 
/*     */         
/* 808 */         HikariPool.this.logPoolState(new String[] { str });
/*     */         
/* 810 */         HikariPool.this.fillPool();
/*     */       }
/* 812 */       catch (Exception exception) {
/* 813 */         HikariPool.this.logger.error("Unexpected exception in housekeeping task", exception);
/*     */       } 
/*     */     }
/*     */     
/*     */     private HouseKeeper() {}
/*     */   }
/*     */   
/*     */   private final class MaxLifetimeTask implements Runnable {
/*     */     private final PoolEntry poolEntry;
/*     */     
/*     */     MaxLifetimeTask(PoolEntry param1PoolEntry) {
/* 824 */       this.poolEntry = param1PoolEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 829 */       if (HikariPool.this.softEvictConnection(this.poolEntry, "(connection has passed maxLifetime)", false)) {
/* 830 */         HikariPool.this.addBagItem(HikariPool.this.connectionBag.getWaitingThreadCount());
/*     */       }
/*     */     }
/*     */   }
/*     */   
/*     */   private final class KeepaliveTask
/*     */     implements Runnable
/*     */   {
/*     */     private final PoolEntry poolEntry;
/*     */     
/*     */     KeepaliveTask(PoolEntry param1PoolEntry) {
/* 841 */       this.poolEntry = param1PoolEntry;
/*     */     }
/*     */ 
/*     */     
/*     */     public void run() {
/* 846 */       if (HikariPool.this.connectionBag.reserve(this.poolEntry)) {
/* 847 */         if (!HikariPool.this.isConnectionAlive(this.poolEntry.connection)) {
/* 848 */           HikariPool.this.softEvictConnection(this.poolEntry, "(connection is dead)", true);
/* 849 */           HikariPool.this.addBagItem(HikariPool.this.connectionBag.getWaitingThreadCount());
/*     */         } else {
/*     */           
/* 852 */           HikariPool.this.connectionBag.unreserve(this.poolEntry);
/* 853 */           HikariPool.this.logger.debug("{} - keepalive: connection {} is alive", HikariPool.this.poolName, this.poolEntry.connection);
/*     */         } 
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static class PoolInitializationException
/*     */     extends RuntimeException
/*     */   {
/*     */     private static final long serialVersionUID = 929872118275916520L;
/*     */ 
/*     */ 
/*     */     
/*     */     public PoolInitializationException(Throwable param1Throwable) {
/* 869 */       super("Failed to initialize pool: " + param1Throwable.getMessage(), param1Throwable);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\HikariPool.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */