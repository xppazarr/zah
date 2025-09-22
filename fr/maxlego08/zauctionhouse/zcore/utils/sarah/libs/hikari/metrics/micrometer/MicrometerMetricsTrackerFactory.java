/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.micrometer;
/*    */ 
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.IMetricsTracker;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.MetricsTrackerFactory;
/*    */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.PoolStats;
/*    */ import io.micrometer.core.instrument.MeterRegistry;
/*    */ 
/*    */ 
/*    */ public class MicrometerMetricsTrackerFactory
/*    */   implements MetricsTrackerFactory
/*    */ {
/*    */   private final MeterRegistry registry;
/*    */   
/*    */   public MicrometerMetricsTrackerFactory(MeterRegistry paramMeterRegistry) {
/* 15 */     this.registry = paramMeterRegistry;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public IMetricsTracker create(String paramString, PoolStats paramPoolStats) {
/* 21 */     return new MicrometerMetricsTracker(paramString, paramPoolStats, this.registry);
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\micrometer\MicrometerMetricsTrackerFactory.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */