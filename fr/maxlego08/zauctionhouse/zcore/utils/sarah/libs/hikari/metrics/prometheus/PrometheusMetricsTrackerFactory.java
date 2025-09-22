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
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class PrometheusMetricsTrackerFactory
/*    */   implements MetricsTrackerFactory
/*    */ {
/* 46 */   private static final Map<CollectorRegistry, RegistrationStatus> registrationStatuses = new ConcurrentHashMap<>();
/*    */   
/* 48 */   private final HikariCPCollector collector = new HikariCPCollector();
/*    */   
/*    */   private final CollectorRegistry collectorRegistry;
/*    */   
/*    */   enum RegistrationStatus
/*    */   {
/* 54 */     REGISTERED;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrometheusMetricsTrackerFactory() {
/* 63 */     this(CollectorRegistry.defaultRegistry);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public PrometheusMetricsTrackerFactory(CollectorRegistry paramCollectorRegistry) {
/* 72 */     this.collectorRegistry = paramCollectorRegistry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMetricsTracker create(String paramString, PoolStats paramPoolStats) {
/* 78 */     registerCollector(this.collector, this.collectorRegistry);
/* 79 */     this.collector.add(paramString, paramPoolStats);
/* 80 */     return new PrometheusMetricsTracker(paramString, this.collectorRegistry, this.collector);
/*    */   }
/*    */ 
/*    */   
/*    */   private void registerCollector(Collector paramCollector, CollectorRegistry paramCollectorRegistry) {
/* 85 */     if (registrationStatuses.putIfAbsent(paramCollectorRegistry, RegistrationStatus.REGISTERED) == null)
/* 86 */       paramCollector.register(paramCollectorRegistry); 
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\prometheus\PrometheusMetricsTrackerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */