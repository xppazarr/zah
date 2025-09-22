/*      */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari;
/*      */ 
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.MetricsTrackerFactory;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.PropertyElf;
/*      */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.UtilityElf;
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.lang.reflect.Field;
/*      */ import java.lang.reflect.Modifier;
/*      */ import java.security.AccessControlException;
/*      */ import java.util.Properties;
/*      */ import java.util.TreeSet;
/*      */ import java.util.concurrent.ScheduledExecutorService;
/*      */ import java.util.concurrent.ThreadFactory;
/*      */ import java.util.concurrent.ThreadLocalRandom;
/*      */ import java.util.concurrent.TimeUnit;
/*      */ import javax.naming.InitialContext;
/*      */ import javax.naming.NamingException;
/*      */ import javax.sql.DataSource;
/*      */ import org.slf4j.Logger;
/*      */ import org.slf4j.LoggerFactory;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class HikariConfig
/*      */   implements HikariConfigMXBean
/*      */ {
/*   51 */   private static final Logger LOGGER = LoggerFactory.getLogger(HikariConfig.class);
/*      */   
/*   53 */   private static final char[] ID_CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
/*   54 */   private static final long CONNECTION_TIMEOUT = TimeUnit.SECONDS.toMillis(30L);
/*   55 */   private static final long VALIDATION_TIMEOUT = TimeUnit.SECONDS.toMillis(5L);
/*   56 */   private static final long SOFT_TIMEOUT_FLOOR = Long.getLong("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.timeoutMs.floor", 250L).longValue();
/*   57 */   private static final long IDLE_TIMEOUT = TimeUnit.MINUTES.toMillis(10L);
/*   58 */   private static final long MAX_LIFETIME = TimeUnit.MINUTES.toMillis(30L);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static boolean unitTest = false;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*  113 */   private Properties dataSourceProperties = new Properties();
/*  114 */   private Properties healthCheckProperties = new Properties();
/*      */   
/*  116 */   private volatile int minIdle = -1;
/*  117 */   private volatile int maxPoolSize = -1;
/*  118 */   private volatile long maxLifetime = MAX_LIFETIME;
/*  119 */   private volatile long connectionTimeout = CONNECTION_TIMEOUT;
/*  120 */   private volatile long validationTimeout = VALIDATION_TIMEOUT;
/*  121 */   private volatile long idleTimeout = IDLE_TIMEOUT;
/*  122 */   private long initializationFailTimeout = 1L; private boolean isAutoCommit = true; private static final long DEFAULT_KEEPALIVE_TIME = 0L; private static final int DEFAULT_POOL_SIZE = 10; private volatile String catalog; private volatile long leakDetectionThreshold; private volatile String username; private volatile String password;
/*      */   private String connectionInitSql;
/*  124 */   private long keepaliveTime = 0L; private String connectionTestQuery; private String dataSourceClassName; private String dataSourceJndiName; private String driverClassName; private String exceptionOverrideClassName; private String jdbcUrl;
/*      */   public HikariConfig() {
/*  126 */     String str = System.getProperty("hikaricp.configurationFile");
/*  127 */     if (str != null)
/*  128 */       loadProperties(str); 
/*      */   }
/*      */   private String poolName; private String schema; private String transactionIsolationName; private boolean isReadOnly; private boolean isIsolateInternalQueries; private boolean isRegisterMbeans; private boolean isAllowPoolSuspension; private DataSource dataSource;
/*      */   private ThreadFactory threadFactory;
/*      */   private ScheduledExecutorService scheduledExecutor;
/*      */   private MetricsTrackerFactory metricsTrackerFactory;
/*      */   private Object metricRegistry;
/*      */   private Object healthCheckRegistry;
/*      */   private volatile boolean sealed;
/*      */   
/*      */   public HikariConfig(Properties paramProperties) {
/*  139 */     this();
/*  140 */     PropertyElf.setTargetFromProperties(this, paramProperties);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public HikariConfig(String paramString) {
/*  152 */     this();
/*      */     
/*  154 */     loadProperties(paramString);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getCatalog() {
/*  165 */     return this.catalog;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setCatalog(String paramString) {
/*  172 */     this.catalog = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getConnectionTimeout() {
/*  180 */     return this.connectionTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionTimeout(long paramLong) {
/*  187 */     if (paramLong == 0L) {
/*  188 */       this.connectionTimeout = 2147483647L;
/*      */     } else {
/*  190 */       if (paramLong < SOFT_TIMEOUT_FLOOR) {
/*  191 */         throw new IllegalArgumentException("connectionTimeout cannot be less than " + SOFT_TIMEOUT_FLOOR + "ms");
/*      */       }
/*      */       
/*  194 */       this.connectionTimeout = paramLong;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getIdleTimeout() {
/*  202 */     return this.idleTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIdleTimeout(long paramLong) {
/*  209 */     if (paramLong < 0L) {
/*  210 */       throw new IllegalArgumentException("idleTimeout cannot be negative");
/*      */     }
/*  212 */     this.idleTimeout = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getLeakDetectionThreshold() {
/*  219 */     return this.leakDetectionThreshold;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setLeakDetectionThreshold(long paramLong) {
/*  226 */     this.leakDetectionThreshold = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getMaxLifetime() {
/*  233 */     return this.maxLifetime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaxLifetime(long paramLong) {
/*  240 */     this.maxLifetime = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMaximumPoolSize() {
/*  247 */     return this.maxPoolSize;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMaximumPoolSize(int paramInt) {
/*  254 */     if (paramInt < 1) {
/*  255 */       throw new IllegalArgumentException("maxPoolSize cannot be less than 1");
/*      */     }
/*  257 */     this.maxPoolSize = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int getMinimumIdle() {
/*  264 */     return this.minIdle;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMinimumIdle(int paramInt) {
/*  271 */     if (paramInt < 0) {
/*  272 */       throw new IllegalArgumentException("minimumIdle cannot be negative");
/*      */     }
/*  274 */     this.minIdle = paramInt;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPassword() {
/*  283 */     return this.password;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPassword(String paramString) {
/*  293 */     this.password = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getUsername() {
/*  303 */     return this.username;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setUsername(String paramString) {
/*  314 */     this.username = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getValidationTimeout() {
/*  321 */     return this.validationTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setValidationTimeout(long paramLong) {
/*  328 */     if (paramLong < SOFT_TIMEOUT_FLOOR) {
/*  329 */       throw new IllegalArgumentException("validationTimeout cannot be less than " + SOFT_TIMEOUT_FLOOR + "ms");
/*      */     }
/*      */     
/*  332 */     this.validationTimeout = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConnectionTestQuery() {
/*  346 */     return this.connectionTestQuery;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionTestQuery(String paramString) {
/*  358 */     checkIfSealed();
/*  359 */     this.connectionTestQuery = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getConnectionInitSql() {
/*  370 */     return this.connectionInitSql;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setConnectionInitSql(String paramString) {
/*  382 */     checkIfSealed();
/*  383 */     this.connectionInitSql = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public DataSource getDataSource() {
/*  394 */     return this.dataSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSource(DataSource paramDataSource) {
/*  405 */     checkIfSealed();
/*  406 */     this.dataSource = paramDataSource;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getDataSourceClassName() {
/*  416 */     return this.dataSourceClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setDataSourceClassName(String paramString) {
/*  426 */     checkIfSealed();
/*  427 */     this.dataSourceClassName = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void addDataSourceProperty(String paramString, Object paramObject) {
/*  445 */     checkIfSealed();
/*  446 */     this.dataSourceProperties.put(paramString, paramObject);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDataSourceJNDI() {
/*  451 */     return this.dataSourceJndiName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDataSourceJNDI(String paramString) {
/*  456 */     checkIfSealed();
/*  457 */     this.dataSourceJndiName = paramString;
/*      */   }
/*      */ 
/*      */   
/*      */   public Properties getDataSourceProperties() {
/*  462 */     return this.dataSourceProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDataSourceProperties(Properties paramProperties) {
/*  467 */     checkIfSealed();
/*  468 */     this.dataSourceProperties.putAll(paramProperties);
/*      */   }
/*      */ 
/*      */   
/*      */   public String getDriverClassName() {
/*  473 */     return this.driverClassName;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setDriverClassName(String paramString) {
/*  478 */     checkIfSealed();
/*      */     
/*  480 */     Class<?> clazz = attemptFromContextLoader(paramString);
/*      */     try {
/*  482 */       if (clazz == null) {
/*  483 */         clazz = getClass().getClassLoader().loadClass(paramString);
/*  484 */         LOGGER.debug("Driver class {} found in the HikariConfig class classloader {}", paramString, getClass().getClassLoader());
/*      */       } 
/*  486 */     } catch (ClassNotFoundException classNotFoundException) {
/*  487 */       LOGGER.error("Failed to load driver class {} from HikariConfig class classloader {}", paramString, getClass().getClassLoader());
/*      */     } 
/*      */     
/*  490 */     if (clazz == null) {
/*  491 */       throw new RuntimeException("Failed to load driver class " + paramString + " in either of HikariConfig class loader or Thread context classloader");
/*      */     }
/*      */     
/*      */     try {
/*  495 */       clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
/*  496 */       this.driverClassName = paramString;
/*      */     }
/*  498 */     catch (Exception exception) {
/*  499 */       throw new RuntimeException("Failed to instantiate class " + paramString, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public String getJdbcUrl() {
/*  505 */     return this.jdbcUrl;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setJdbcUrl(String paramString) {
/*  510 */     checkIfSealed();
/*  511 */     this.jdbcUrl = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAutoCommit() {
/*  521 */     return this.isAutoCommit;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAutoCommit(boolean paramBoolean) {
/*  531 */     checkIfSealed();
/*  532 */     this.isAutoCommit = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isAllowPoolSuspension() {
/*  542 */     return this.isAllowPoolSuspension;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setAllowPoolSuspension(boolean paramBoolean) {
/*  554 */     checkIfSealed();
/*  555 */     this.isAllowPoolSuspension = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getInitializationFailTimeout() {
/*  567 */     return this.initializationFailTimeout;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setInitializationFailTimeout(long paramLong) {
/*  605 */     checkIfSealed();
/*  606 */     this.initializationFailTimeout = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isIsolateInternalQueries() {
/*  617 */     return this.isIsolateInternalQueries;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setIsolateInternalQueries(boolean paramBoolean) {
/*  628 */     checkIfSealed();
/*  629 */     this.isIsolateInternalQueries = paramBoolean;
/*      */   }
/*      */ 
/*      */   
/*      */   public MetricsTrackerFactory getMetricsTrackerFactory() {
/*  634 */     return this.metricsTrackerFactory;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setMetricsTrackerFactory(MetricsTrackerFactory paramMetricsTrackerFactory) {
/*  639 */     if (this.metricRegistry != null) {
/*  640 */       throw new IllegalStateException("cannot use setMetricsTrackerFactory() and setMetricRegistry() together");
/*      */     }
/*      */     
/*  643 */     this.metricsTrackerFactory = paramMetricsTrackerFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getMetricRegistry() {
/*  653 */     return this.metricRegistry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setMetricRegistry(Object paramObject) {
/*  663 */     if (this.metricsTrackerFactory != null) {
/*  664 */       throw new IllegalStateException("cannot use setMetricRegistry() and setMetricsTrackerFactory() together");
/*      */     }
/*      */     
/*  667 */     if (paramObject != null) {
/*  668 */       paramObject = getObjectOrPerformJndiLookup(paramObject);
/*      */       
/*  670 */       if (!UtilityElf.safeIsAssignableFrom(paramObject, "com.codahale.metrics.MetricRegistry") && 
/*  671 */         !UtilityElf.safeIsAssignableFrom(paramObject, "io.micrometer.core.instrument.MeterRegistry")) {
/*  672 */         throw new IllegalArgumentException("Class must be instance of com.codahale.metrics.MetricRegistry or io.micrometer.core.instrument.MeterRegistry");
/*      */       }
/*      */     } 
/*      */     
/*  676 */     this.metricRegistry = paramObject;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public Object getHealthCheckRegistry() {
/*  687 */     return this.healthCheckRegistry;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setHealthCheckRegistry(Object paramObject) {
/*  698 */     checkIfSealed();
/*      */     
/*  700 */     if (paramObject != null) {
/*  701 */       paramObject = getObjectOrPerformJndiLookup(paramObject);
/*      */       
/*  703 */       if (!(paramObject instanceof com.codahale.metrics.health.HealthCheckRegistry)) {
/*  704 */         throw new IllegalArgumentException("Class must be an instance of com.codahale.metrics.health.HealthCheckRegistry");
/*      */       }
/*      */     } 
/*      */     
/*  708 */     this.healthCheckRegistry = paramObject;
/*      */   }
/*      */ 
/*      */   
/*      */   public Properties getHealthCheckProperties() {
/*  713 */     return this.healthCheckProperties;
/*      */   }
/*      */ 
/*      */   
/*      */   public void setHealthCheckProperties(Properties paramProperties) {
/*  718 */     checkIfSealed();
/*  719 */     this.healthCheckProperties.putAll(paramProperties);
/*      */   }
/*      */ 
/*      */   
/*      */   public void addHealthCheckProperty(String paramString1, String paramString2) {
/*  724 */     checkIfSealed();
/*  725 */     this.healthCheckProperties.setProperty(paramString1, paramString2);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public long getKeepaliveTime() {
/*  735 */     return this.keepaliveTime;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setKeepaliveTime(long paramLong) {
/*  745 */     this.keepaliveTime = paramLong;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isReadOnly() {
/*  755 */     return this.isReadOnly;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setReadOnly(boolean paramBoolean) {
/*  765 */     checkIfSealed();
/*  766 */     this.isReadOnly = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public boolean isRegisterMbeans() {
/*  777 */     return this.isRegisterMbeans;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setRegisterMbeans(boolean paramBoolean) {
/*  787 */     checkIfSealed();
/*  788 */     this.isRegisterMbeans = paramBoolean;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getPoolName() {
/*  795 */     return this.poolName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setPoolName(String paramString) {
/*  806 */     checkIfSealed();
/*  807 */     this.poolName = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ScheduledExecutorService getScheduledExecutor() {
/*  817 */     return this.scheduledExecutor;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setScheduledExecutor(ScheduledExecutorService paramScheduledExecutorService) {
/*  827 */     checkIfSealed();
/*  828 */     this.scheduledExecutor = paramScheduledExecutorService;
/*      */   }
/*      */ 
/*      */   
/*      */   public String getTransactionIsolation() {
/*  833 */     return this.transactionIsolationName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getSchema() {
/*  843 */     return this.schema;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setSchema(String paramString) {
/*  853 */     checkIfSealed();
/*  854 */     this.schema = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String getExceptionOverrideClassName() {
/*  865 */     return this.exceptionOverrideClassName;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setExceptionOverrideClassName(String paramString) {
/*  876 */     checkIfSealed();
/*      */     
/*  878 */     Class<?> clazz = attemptFromContextLoader(paramString);
/*      */     try {
/*  880 */       if (clazz == null) {
/*  881 */         clazz = getClass().getClassLoader().loadClass(paramString);
/*  882 */         LOGGER.debug("SQLExceptionOverride class {} found in the HikariConfig class classloader {}", paramString, getClass().getClassLoader());
/*      */       } 
/*  884 */     } catch (ClassNotFoundException classNotFoundException) {
/*  885 */       LOGGER.error("Failed to load SQLExceptionOverride class {} from HikariConfig class classloader {}", paramString, getClass().getClassLoader());
/*      */     } 
/*      */     
/*  888 */     if (clazz == null) {
/*  889 */       throw new RuntimeException("Failed to load SQLExceptionOverride class " + paramString + " in either of HikariConfig class loader or Thread context classloader");
/*      */     }
/*      */     
/*      */     try {
/*  893 */       clazz.getConstructor(new Class[0]).newInstance(new Object[0]);
/*  894 */       this.exceptionOverrideClassName = paramString;
/*      */     }
/*  896 */     catch (Exception exception) {
/*  897 */       throw new RuntimeException("Failed to instantiate class " + paramString, exception);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setTransactionIsolation(String paramString) {
/*  910 */     checkIfSealed();
/*  911 */     this.transactionIsolationName = paramString;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ThreadFactory getThreadFactory() {
/*  921 */     return this.threadFactory;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void setThreadFactory(ThreadFactory paramThreadFactory) {
/*  931 */     checkIfSealed();
/*  932 */     this.threadFactory = paramThreadFactory;
/*      */   }
/*      */ 
/*      */   
/*      */   void seal() {
/*  937 */     this.sealed = true;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void copyStateTo(HikariConfig paramHikariConfig) {
/*  947 */     for (Field field : HikariConfig.class.getDeclaredFields()) {
/*  948 */       if (!Modifier.isFinal(field.getModifiers())) {
/*  949 */         field.setAccessible(true);
/*      */         try {
/*  951 */           field.set(paramHikariConfig, field.get(this));
/*      */         }
/*  953 */         catch (Exception exception) {
/*  954 */           throw new RuntimeException("Failed to copy HikariConfig state: " + exception.getMessage(), exception);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */     
/*  959 */     paramHikariConfig.sealed = false;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Class<?> attemptFromContextLoader(String paramString) {
/*  967 */     ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
/*  968 */     if (classLoader != null) {
/*      */       try {
/*  970 */         Class<?> clazz = classLoader.loadClass(paramString);
/*  971 */         LOGGER.debug("Driver class {} found in Thread context class loader {}", paramString, classLoader);
/*  972 */         return clazz;
/*  973 */       } catch (ClassNotFoundException classNotFoundException) {
/*  974 */         LOGGER.debug("Driver class {} not found in Thread context class loader {}, trying classloader {}", new Object[] { paramString, classLoader, 
/*  975 */               getClass().getClassLoader() });
/*      */       } 
/*      */     }
/*      */     
/*  979 */     return null;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void validate() {
/*  985 */     if (this.poolName == null) {
/*  986 */       this.poolName = generatePoolName();
/*      */     }
/*  988 */     else if (this.isRegisterMbeans && this.poolName.contains(":")) {
/*  989 */       throw new IllegalArgumentException("poolName cannot contain ':' when used with JMX");
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  994 */     this.catalog = UtilityElf.getNullIfEmpty(this.catalog);
/*  995 */     this.connectionInitSql = UtilityElf.getNullIfEmpty(this.connectionInitSql);
/*  996 */     this.connectionTestQuery = UtilityElf.getNullIfEmpty(this.connectionTestQuery);
/*  997 */     this.transactionIsolationName = UtilityElf.getNullIfEmpty(this.transactionIsolationName);
/*  998 */     this.dataSourceClassName = UtilityElf.getNullIfEmpty(this.dataSourceClassName);
/*  999 */     this.dataSourceJndiName = UtilityElf.getNullIfEmpty(this.dataSourceJndiName);
/* 1000 */     this.driverClassName = UtilityElf.getNullIfEmpty(this.driverClassName);
/* 1001 */     this.jdbcUrl = UtilityElf.getNullIfEmpty(this.jdbcUrl);
/*      */ 
/*      */     
/* 1004 */     if (this.dataSource != null) {
/* 1005 */       if (this.dataSourceClassName != null) {
/* 1006 */         LOGGER.warn("{} - using dataSource and ignoring dataSourceClassName.", this.poolName);
/*      */       }
/*      */     }
/* 1009 */     else if (this.dataSourceClassName != null) {
/* 1010 */       if (this.driverClassName != null) {
/* 1011 */         LOGGER.error("{} - cannot use driverClassName and dataSourceClassName together.", this.poolName);
/*      */ 
/*      */         
/* 1014 */         throw new IllegalStateException("cannot use driverClassName and dataSourceClassName together.");
/*      */       } 
/* 1016 */       if (this.jdbcUrl != null) {
/* 1017 */         LOGGER.warn("{} - using dataSourceClassName and ignoring jdbcUrl.", this.poolName);
/*      */       }
/*      */     }
/* 1020 */     else if (this.jdbcUrl == null && this.dataSourceJndiName == null) {
/*      */ 
/*      */       
/* 1023 */       if (this.driverClassName != null) {
/* 1024 */         LOGGER.error("{} - jdbcUrl is required with driverClassName.", this.poolName);
/* 1025 */         throw new IllegalArgumentException("jdbcUrl is required with driverClassName.");
/*      */       } 
/*      */       
/* 1028 */       LOGGER.error("{} - dataSource or dataSourceClassName or jdbcUrl is required.", this.poolName);
/* 1029 */       throw new IllegalArgumentException("dataSource or dataSourceClassName or jdbcUrl is required.");
/*      */     } 
/*      */     
/* 1032 */     validateNumerics();
/*      */     
/* 1034 */     if (LOGGER.isDebugEnabled() || unitTest) {
/* 1035 */       logConfiguration();
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   private void validateNumerics() {
/* 1041 */     if (this.maxLifetime != 0L && this.maxLifetime < TimeUnit.SECONDS.toMillis(30L)) {
/* 1042 */       LOGGER.warn("{} - maxLifetime is less than 30000ms, setting to default {}ms.", this.poolName, Long.valueOf(MAX_LIFETIME));
/* 1043 */       this.maxLifetime = MAX_LIFETIME;
/*      */     } 
/*      */ 
/*      */     
/* 1047 */     if (this.keepaliveTime != 0L && this.keepaliveTime < TimeUnit.SECONDS.toMillis(30L)) {
/* 1048 */       LOGGER.warn("{} - keepaliveTime is less than 30000ms, disabling it.", this.poolName);
/* 1049 */       this.keepaliveTime = 0L;
/*      */     } 
/*      */ 
/*      */     
/* 1053 */     if (this.keepaliveTime != 0L && this.maxLifetime != 0L && this.keepaliveTime >= this.maxLifetime) {
/* 1054 */       LOGGER.warn("{} - keepaliveTime is greater than or equal to maxLifetime, disabling it.", this.poolName);
/* 1055 */       this.keepaliveTime = 0L;
/*      */     } 
/*      */     
/* 1058 */     if (this.leakDetectionThreshold > 0L && !unitTest && (
/* 1059 */       this.leakDetectionThreshold < TimeUnit.SECONDS.toMillis(2L) || (this.leakDetectionThreshold > this.maxLifetime && this.maxLifetime > 0L))) {
/* 1060 */       LOGGER.warn("{} - leakDetectionThreshold is less than 2000ms or more than maxLifetime, disabling it.", this.poolName);
/* 1061 */       this.leakDetectionThreshold = 0L;
/*      */     } 
/*      */ 
/*      */     
/* 1065 */     if (this.connectionTimeout < SOFT_TIMEOUT_FLOOR) {
/* 1066 */       LOGGER.warn("{} - connectionTimeout is less than {}ms, setting to {}ms.", new Object[] { this.poolName, Long.valueOf(SOFT_TIMEOUT_FLOOR), Long.valueOf(CONNECTION_TIMEOUT) });
/* 1067 */       this.connectionTimeout = CONNECTION_TIMEOUT;
/*      */     } 
/*      */     
/* 1070 */     if (this.validationTimeout < SOFT_TIMEOUT_FLOOR) {
/* 1071 */       LOGGER.warn("{} - validationTimeout is less than {}ms, setting to {}ms.", new Object[] { this.poolName, Long.valueOf(SOFT_TIMEOUT_FLOOR), Long.valueOf(VALIDATION_TIMEOUT) });
/* 1072 */       this.validationTimeout = VALIDATION_TIMEOUT;
/*      */     } 
/*      */     
/* 1075 */     if (this.maxPoolSize < 1) {
/* 1076 */       this.maxPoolSize = 10;
/*      */     }
/*      */     
/* 1079 */     if (this.minIdle < 0 || this.minIdle > this.maxPoolSize) {
/* 1080 */       this.minIdle = this.maxPoolSize;
/*      */     }
/*      */     
/* 1083 */     if (this.idleTimeout + TimeUnit.SECONDS.toMillis(1L) > this.maxLifetime && this.maxLifetime > 0L && this.minIdle < this.maxPoolSize) {
/* 1084 */       LOGGER.warn("{} - idleTimeout is close to or more than maxLifetime, disabling it.", this.poolName);
/* 1085 */       this.idleTimeout = 0L;
/*      */     }
/* 1087 */     else if (this.idleTimeout != 0L && this.idleTimeout < TimeUnit.SECONDS.toMillis(10L) && this.minIdle < this.maxPoolSize) {
/* 1088 */       LOGGER.warn("{} - idleTimeout is less than 10000ms, setting to default {}ms.", this.poolName, Long.valueOf(IDLE_TIMEOUT));
/* 1089 */       this.idleTimeout = IDLE_TIMEOUT;
/*      */     }
/* 1091 */     else if (this.idleTimeout != IDLE_TIMEOUT && this.idleTimeout != 0L && this.minIdle == this.maxPoolSize) {
/* 1092 */       LOGGER.warn("{} - idleTimeout has been set but has no effect because the pool is operating as a fixed size pool.", this.poolName);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private void checkIfSealed() {
/* 1098 */     if (this.sealed) throw new IllegalStateException("The configuration of the pool is sealed once started. Use HikariConfigMXBean for runtime changes.");
/*      */   
/*      */   }
/*      */   
/*      */   private void logConfiguration() {
/* 1103 */     LOGGER.debug("{} - configuration:", this.poolName);
/* 1104 */     TreeSet treeSet = new TreeSet(PropertyElf.getPropertyNames(HikariConfig.class));
/* 1105 */     for (String str : treeSet) {
/*      */       try {
/* 1107 */         Object object = PropertyElf.getProperty(str, this);
/* 1108 */         if ("dataSourceProperties".equals(str)) {
/* 1109 */           Properties properties = PropertyElf.copyProperties(this.dataSourceProperties);
/* 1110 */           properties.setProperty("password", "<masked>");
/* 1111 */           object = properties;
/*      */         } 
/*      */         
/* 1114 */         if ("initializationFailTimeout".equals(str) && this.initializationFailTimeout == Long.MAX_VALUE) {
/* 1115 */           object = "infinite";
/*      */         }
/* 1117 */         else if ("transactionIsolation".equals(str) && this.transactionIsolationName == null) {
/* 1118 */           object = "default";
/*      */         }
/* 1120 */         else if (str.matches("scheduledExecutorService|threadFactory") && object == null) {
/* 1121 */           object = "internal";
/*      */         }
/* 1123 */         else if (str.contains("jdbcUrl") && object instanceof String) {
/* 1124 */           object = ((String)object).replaceAll("([?&;]password=)[^&#;]*(.*)", "$1<masked>$2");
/*      */         }
/* 1126 */         else if (str.contains("password")) {
/* 1127 */           object = "<masked>";
/*      */         }
/* 1129 */         else if (object instanceof String) {
/* 1130 */           object = "\"" + object + "\"";
/*      */         }
/* 1132 */         else if (object == null) {
/* 1133 */           object = "none";
/*      */         } 
/* 1135 */         LOGGER.debug("{}{}", str + "................................................".substring(0, 32), object);
/*      */       }
/* 1137 */       catch (Exception exception) {}
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void loadProperties(String paramString) {
/* 1145 */     File file = new File(paramString); 
/* 1146 */     try { InputStream inputStream = file.isFile() ? new FileInputStream(file) : getClass().getResourceAsStream(paramString); 
/* 1147 */       try { if (inputStream != null) {
/* 1148 */           Properties properties = new Properties();
/* 1149 */           properties.load(inputStream);
/* 1150 */           PropertyElf.setTargetFromProperties(this, properties);
/*      */         } else {
/*      */           
/* 1153 */           throw new IllegalArgumentException("Cannot find property file: " + paramString);
/*      */         } 
/* 1155 */         if (inputStream != null) inputStream.close();  } catch (Throwable throwable) { if (inputStream != null)
/* 1156 */           try { inputStream.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }  } catch (IOException iOException)
/* 1157 */     { throw new RuntimeException("Failed to read property file", iOException); }
/*      */   
/*      */   }
/*      */ 
/*      */   
/*      */   private String generatePoolName() {
/* 1163 */     String str = "HikariPool-";
/*      */     
/*      */     try {
/* 1166 */       synchronized (System.getProperties()) {
/* 1167 */         String str1 = String.valueOf(Integer.getInteger("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool_number", 0).intValue() + 1);
/* 1168 */         System.setProperty("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool_number", str1);
/* 1169 */         return "HikariPool-" + str1;
/*      */       } 
/* 1171 */     } catch (AccessControlException accessControlException) {
/*      */ 
/*      */       
/* 1174 */       ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
/* 1175 */       StringBuilder stringBuilder = new StringBuilder("HikariPool-");
/*      */       
/* 1177 */       for (byte b = 0; b < 4; b++) {
/* 1178 */         stringBuilder.append(ID_CHARACTERS[threadLocalRandom.nextInt(62)]);
/*      */       }
/*      */       
/* 1181 */       LOGGER.info("assigned random pool name '{}' (security manager prevented access to system properties)", stringBuilder);
/*      */       
/* 1183 */       return stringBuilder.toString();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private Object getObjectOrPerformJndiLookup(Object paramObject) {
/* 1189 */     if (paramObject instanceof String) {
/*      */       try {
/* 1191 */         InitialContext initialContext = new InitialContext();
/* 1192 */         return initialContext.lookup((String)paramObject);
/*      */       }
/* 1194 */       catch (NamingException namingException) {
/* 1195 */         throw new IllegalArgumentException(namingException);
/*      */       } 
/*      */     }
/* 1198 */     return paramObject;
/*      */   }
/*      */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\HikariConfig.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */