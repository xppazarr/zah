/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.prometheus;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.IMetricsTracker;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.MetricsTrackerFactory;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.PoolStats;
/*    */ import io.prometheus.client.Collector;
/*    */ import io.prometheus.client.CollectorRegistry;
/*    */ import java.util.Map;
/*    */ import java.util.concurrent.ConcurrentHashMap;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrometheusHistogramMetricsTrackerFactory
/*    */   implements MetricsTrackerFactory
/*    */ {
/* 39 */   private static final Map<CollectorRegistry, PrometheusMetricsTrackerFactory.RegistrationStatus> registrationStatuses = new ConcurrentHashMap<>();
/*    */   
/* 41 */   private final HikariCPCollector collector = new HikariCPCollector();
/*    */ 
/*    */ 
/*    */   
/*    */   private final CollectorRegistry collectorRegistry;
/*    */ 
/*    */ 
/*    */   
/*    */   public PrometheusHistogramMetricsTrackerFactory() {
/* 50 */     this(CollectorRegistry.defaultRegistry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrometheusHistogramMetricsTrackerFactory(CollectorRegistry paramCollectorRegistry) {
/* 58 */     this.collectorRegistry = paramCollectorRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   public IMetricsTracker create(String paramString, PoolStats paramPoolStats) {
/* 63 */     registerCollector(this.collector, this.collectorRegistry);
/* 64 */     this.collector.add(paramString, paramPoolStats);
/* 65 */     return new PrometheusHistogramMetricsTracker(paramString, this.collectorRegistry, this.collector);
/*    */   }
/*    */   
/*    */   private void registerCollector(Collector paramCollector, CollectorRegistry paramCollectorRegistry) {
/* 69 */     if (registrationStatuses.putIfAbsent(paramCollectorRegistry, PrometheusMetricsTrackerFactory.RegistrationStatus.REGISTERED) == null)
/* 70 */       paramCollector.register(paramCollectorRegistry); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\prometheus\PrometheusHistogramMetricsTrackerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */