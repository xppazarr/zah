/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.dropwizard;
/*     */ 
/*     */ import com.codahale.metrics.Metric;
/*     */ import com.codahale.metrics.MetricRegistry;
/*     */ import com.codahale.metrics.Timer;
/*     */ import com.codahale.metrics.health.HealthCheck;
/*     */ import com.codahale.metrics.health.HealthCheckRegistry;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariConfig;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool.HikariPool;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.util.Map;
/*     */ import java.util.Properties;
/*     */ import java.util.SortedMap;
/*     */ import java.util.concurrent.TimeUnit;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class CodahaleHealthChecker
/*     */ {
/*     */   public static void registerHealthChecks(HikariPool paramHikariPool, HikariConfig paramHikariConfig, HealthCheckRegistry paramHealthCheckRegistry) {
/*  58 */     Properties properties = paramHikariConfig.getHealthCheckProperties();
/*  59 */     MetricRegistry metricRegistry = (MetricRegistry)paramHikariConfig.getMetricRegistry();
/*     */     
/*  61 */     long l1 = Long.parseLong(properties.getProperty("connectivityCheckTimeoutMs", String.valueOf(paramHikariConfig.getConnectionTimeout())));
/*  62 */     paramHealthCheckRegistry.register(MetricRegistry.name(paramHikariConfig.getPoolName(), new String[] { "pool", "ConnectivityCheck" }), new ConnectivityHealthCheck(paramHikariPool, l1));
/*     */     
/*  64 */     long l2 = Long.parseLong(properties.getProperty("expected99thPercentileMs", "0"));
/*  65 */     if (metricRegistry != null && l2 > 0L) {
/*  66 */       SortedMap sortedMap = metricRegistry.getTimers((paramString, paramMetric) -> paramString.equals(MetricRegistry.name(paramHikariConfig.getPoolName(), new String[] { "pool", "Wait" })));
/*     */       
/*  68 */       if (!sortedMap.isEmpty()) {
/*  69 */         Timer timer = (Timer)((Map.Entry)sortedMap.entrySet().iterator().next()).getValue();
/*  70 */         paramHealthCheckRegistry.register(MetricRegistry.name(paramHikariConfig.getPoolName(), new String[] { "pool", "Connection99Percent" }), new Connection99Percent(timer, l2));
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class ConnectivityHealthCheck
/*     */     extends HealthCheck
/*     */   {
/*     */     private final HikariPool pool;
/*     */ 
/*     */     
/*     */     private final long checkTimeoutMs;
/*     */ 
/*     */     
/*     */     ConnectivityHealthCheck(HikariPool param1HikariPool, long param1Long) {
/*  87 */       this.pool = param1HikariPool;
/*  88 */       this.checkTimeoutMs = (param1Long > 0L && param1Long != 2147483647L) ? param1Long : TimeUnit.SECONDS.toMillis(10L);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     protected HealthCheck.Result check() {
/*     */       
/*  95 */       try { Connection connection = this.pool.getConnection(this.checkTimeoutMs); 
/*  96 */         try { HealthCheck.Result result = HealthCheck.Result.healthy();
/*  97 */           if (connection != null) connection.close();  return result; } catch (Throwable throwable) { if (connection != null)
/*  98 */             try { connection.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (SQLException sQLException)
/*  99 */       { return HealthCheck.Result.unhealthy(sQLException); }
/*     */     
/*     */     }
/*     */   }
/*     */   
/*     */   private static class Connection99Percent
/*     */     extends HealthCheck
/*     */   {
/*     */     private final Timer waitTimer;
/*     */     private final long expected99thPercentile;
/*     */     
/*     */     Connection99Percent(Timer param1Timer, long param1Long) {
/* 111 */       this.waitTimer = param1Timer;
/* 112 */       this.expected99thPercentile = param1Long;
/*     */     }
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     protected HealthCheck.Result check() {
/* 119 */       long l = TimeUnit.NANOSECONDS.toMillis(Math.round(this.waitTimer.getSnapshot().get99thPercentile()));
/* 120 */       return (l <= this.expected99thPercentile) ? HealthCheck.Result.healthy() : HealthCheck.Result.unhealthy("99th percentile connection wait time of %dms exceeds the threshold %dms", new Object[] { Long.valueOf(l), Long.valueOf(this.expected99thPercentile) });
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\metrics\dropwizard\CodahaleHealthChecker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */