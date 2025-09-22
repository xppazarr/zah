/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.dropwizard;
/*    */ 
/*    */ import com.codahale.metrics.MetricRegistry;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.IMetricsTracker;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.MetricsTrackerFactory;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.PoolStats;
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
/*    */ public final class CodahaleMetricsTrackerFactory
/*    */   implements MetricsTrackerFactory
/*    */ {
/*    */   private final MetricRegistry registry;
/*    */   
/*    */   public CodahaleMetricsTrackerFactory(MetricRegistry paramMetricRegistry) {
/* 30 */     this.registry = paramMetricRegistry;
/*    */   }
/*    */ 
/*    */   
/*    */   public MetricRegistry getRegistry() {
/* 35 */     return this.registry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMetricsTracker create(String paramString, PoolStats paramPoolStats) {
/* 41 */     return new CodaHaleMetricsTracker(paramString, paramPoolStats, this.registry);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\dropwizard\CodahaleMetricsTrackerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */