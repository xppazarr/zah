/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.prometheus;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.IMetricsTracker;
/*     */ import io.prometheus.client.CollectorRegistry;
/*     */ import io.prometheus.client.Counter;
/*     */ import io.prometheus.client.Histogram;
/*     */ import java.util.Map;
/*     */ import java.util.concurrent.ConcurrentHashMap;
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
/*     */ class PrometheusHistogramMetricsTracker
/*     */   implements IMetricsTracker
/*     */ {
/*  39 */   private static final Counter CONNECTION_TIMEOUT_COUNTER = ((Counter.Builder)((Counter.Builder)((Counter.Builder)Counter.build()
/*  40 */     .name("hikaricp_connection_timeout_total"))
/*  41 */     .labelNames(new String[] { "pool"
/*  42 */       })).help("Connection timeout total count"))
/*  43 */     .create();
/*     */ 
/*     */   
/*  46 */   private static final Histogram ELAPSED_ACQUIRED_HISTOGRAM = registerHistogram("hikaricp_connection_acquired_nanos", "Connection acquired time (ns)", 1000.0D);
/*     */ 
/*     */   
/*  49 */   private static final Histogram ELAPSED_BORROWED_HISTOGRAM = registerHistogram("hikaricp_connection_usage_millis", "Connection usage (ms)", 1.0D);
/*     */ 
/*     */   
/*  52 */   private static final Histogram ELAPSED_CREATION_HISTOGRAM = registerHistogram("hikaricp_connection_creation_millis", "Connection creation (ms)", 1.0D);
/*     */   
/*     */   private final Counter.Child connectionTimeoutCounterChild;
/*     */   
/*     */   private static Histogram registerHistogram(String paramString1, String paramString2, double paramDouble) {
/*  57 */     return ((Histogram.Builder)((Histogram.Builder)((Histogram.Builder)Histogram.build()
/*  58 */       .name(paramString1))
/*  59 */       .labelNames(new String[] { "pool"
/*  60 */         })).help(paramString2))
/*  61 */       .exponentialBuckets(paramDouble, 2.0D, 11)
/*  62 */       .create();
/*     */   }
/*     */   
/*  65 */   private static final Map<CollectorRegistry, PrometheusMetricsTrackerFactory.RegistrationStatus> registrationStatuses = new ConcurrentHashMap<>();
/*     */   
/*     */   private final String poolName;
/*     */   
/*     */   private final HikariCPCollector hikariCPCollector;
/*     */   private final Histogram.Child elapsedAcquiredHistogramChild;
/*     */   private final Histogram.Child elapsedBorrowedHistogramChild;
/*     */   private final Histogram.Child elapsedCreationHistogramChild;
/*     */   
/*     */   PrometheusHistogramMetricsTracker(String paramString, CollectorRegistry paramCollectorRegistry, HikariCPCollector paramHikariCPCollector) {
/*  75 */     registerMetrics(paramCollectorRegistry);
/*  76 */     this.poolName = paramString;
/*  77 */     this.hikariCPCollector = paramHikariCPCollector;
/*  78 */     this.connectionTimeoutCounterChild = (Counter.Child)CONNECTION_TIMEOUT_COUNTER.labels(new String[] { paramString });
/*  79 */     this.elapsedAcquiredHistogramChild = (Histogram.Child)ELAPSED_ACQUIRED_HISTOGRAM.labels(new String[] { paramString });
/*  80 */     this.elapsedBorrowedHistogramChild = (Histogram.Child)ELAPSED_BORROWED_HISTOGRAM.labels(new String[] { paramString });
/*  81 */     this.elapsedCreationHistogramChild = (Histogram.Child)ELAPSED_CREATION_HISTOGRAM.labels(new String[] { paramString });
/*     */   }
/*     */   
/*     */   private void registerMetrics(CollectorRegistry paramCollectorRegistry) {
/*  85 */     if (registrationStatuses.putIfAbsent(paramCollectorRegistry, PrometheusMetricsTrackerFactory.RegistrationStatus.REGISTERED) == null) {
/*  86 */       CONNECTION_TIMEOUT_COUNTER.register(paramCollectorRegistry);
/*  87 */       ELAPSED_ACQUIRED_HISTOGRAM.register(paramCollectorRegistry);
/*  88 */       ELAPSED_BORROWED_HISTOGRAM.register(paramCollectorRegistry);
/*  89 */       ELAPSED_CREATION_HISTOGRAM.register(paramCollectorRegistry);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordConnectionAcquiredNanos(long paramLong) {
/*  95 */     this.elapsedAcquiredHistogramChild.observe(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordConnectionUsageMillis(long paramLong) {
/* 100 */     this.elapsedBorrowedHistogramChild.observe(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordConnectionCreatedMillis(long paramLong) {
/* 105 */     this.elapsedCreationHistogramChild.observe(paramLong);
/*     */   }
/*     */ 
/*     */   
/*     */   public void recordConnectionTimeout() {
/* 110 */     this.connectionTimeoutCounterChild.inc();
/*     */   }
/*     */ 
/*     */   
/*     */   public void close() {
/* 115 */     this.hikariCPCollector.remove(this.poolName);
/* 116 */     CONNECTION_TIMEOUT_COUNTER.remove(new String[] { this.poolName });
/* 117 */     ELAPSED_ACQUIRED_HISTOGRAM.remove(new String[] { this.poolName });
/* 118 */     ELAPSED_BORROWED_HISTOGRAM.remove(new String[] { this.poolName });
/* 119 */     ELAPSED_CREATION_HISTOGRAM.remove(new String[] { this.poolName });
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\prometheus\PrometheusHistogramMetricsTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */