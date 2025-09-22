/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariConfig;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.SQLExceptionOverride;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.metrics.IMetricsTracker;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ClockSource;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.DriverDataSource;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.PropertyElf;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.UtilityElf;
/*     */ import java.lang.management.ManagementFactory;
/*     */ import java.sql.Connection;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.SQLTransientConnectionException;
/*     */ import java.sql.Statement;
/*     */ import java.util.Properties;
/*     */ import java.util.concurrent.Executor;
/*     */ import java.util.concurrent.Executors;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import java.util.concurrent.ThreadPoolExecutor;
/*     */ import java.util.concurrent.TimeUnit;
/*     */ import java.util.concurrent.atomic.AtomicReference;
/*     */ import javax.management.MBeanServer;
/*     */ import javax.management.ObjectName;
/*     */ import javax.naming.InitialContext;
/*     */ import javax.naming.NamingException;
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
/*     */ abstract class PoolBase
/*     */ {
/*  55 */   private final Logger logger = LoggerFactory.getLogger(PoolBase.class);
/*     */   
/*     */   public final HikariConfig config;
/*     */   
/*     */   IMetricsTrackerDelegate metricsTracker;
/*     */   
/*     */   protected final String poolName;
/*     */   
/*     */   volatile String catalog;
/*     */   
/*     */   final AtomicReference<Exception> lastConnectionFailure;
/*     */   
/*     */   long connectionTimeout;
/*     */   long validationTimeout;
/*     */   SQLExceptionOverride exceptionOverride;
/*  70 */   private static final String[] RESET_STATES = new String[] { "readOnly", "autoCommit", "isolation", "catalog", "netTimeout", "schema" };
/*     */   
/*     */   private static final int UNINITIALIZED = -1;
/*     */   
/*     */   private static final int TRUE = 1;
/*     */   
/*     */   private static final int FALSE = 0;
/*     */   
/*     */   private int networkTimeout;
/*     */   
/*     */   private int isNetworkTimeoutSupported;
/*     */   private int isQueryTimeoutSupported;
/*     */   private int defaultTransactionIsolation;
/*     */   private int transactionIsolation;
/*     */   private Executor netTimeoutExecutor;
/*     */   private DataSource dataSource;
/*     */   private final String schema;
/*     */   private final boolean isReadOnly;
/*     */   private final boolean isAutoCommit;
/*     */   private final boolean isUseJdbc4Validation;
/*     */   private final boolean isIsolateInternalQueries;
/*     */   private volatile boolean isValidChecked;
/*     */   
/*     */   PoolBase(HikariConfig paramHikariConfig) {
/*  94 */     this.config = paramHikariConfig;
/*     */     
/*  96 */     this.networkTimeout = -1;
/*  97 */     this.catalog = paramHikariConfig.getCatalog();
/*  98 */     this.schema = paramHikariConfig.getSchema();
/*  99 */     this.isReadOnly = paramHikariConfig.isReadOnly();
/* 100 */     this.isAutoCommit = paramHikariConfig.isAutoCommit();
/* 101 */     this.exceptionOverride = (SQLExceptionOverride)UtilityElf.createInstance(paramHikariConfig.getExceptionOverrideClassName(), SQLExceptionOverride.class, new Object[0]);
/* 102 */     this.transactionIsolation = UtilityElf.getTransactionIsolation(paramHikariConfig.getTransactionIsolation());
/*     */     
/* 104 */     this.isQueryTimeoutSupported = -1;
/* 105 */     this.isNetworkTimeoutSupported = -1;
/* 106 */     this.isUseJdbc4Validation = (paramHikariConfig.getConnectionTestQuery() == null);
/* 107 */     this.isIsolateInternalQueries = paramHikariConfig.isIsolateInternalQueries();
/*     */     
/* 109 */     this.poolName = paramHikariConfig.getPoolName();
/* 110 */     this.connectionTimeout = paramHikariConfig.getConnectionTimeout();
/* 111 */     this.validationTimeout = paramHikariConfig.getValidationTimeout();
/* 112 */     this.lastConnectionFailure = new AtomicReference<>();
/*     */     
/* 114 */     initializeDataSource();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 121 */     return this.poolName;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   abstract void recycle(PoolEntry paramPoolEntry);
/*     */ 
/*     */ 
/*     */   
/*     */   void quietlyCloseConnection(Connection paramConnection, String paramString) {
/* 132 */     if (paramConnection != null) {
/*     */       try {
/* 134 */         this.logger.debug("{} - Closing connection {}: {}", new Object[] { this.poolName, paramConnection, paramString });
/*     */         
/*     */         try {
/* 137 */           setNetworkTimeout(paramConnection, TimeUnit.SECONDS.toMillis(15L));
/*     */         }
/* 139 */         catch (SQLException sQLException) {
/*     */ 
/*     */         
/*     */         } finally {
/* 143 */           paramConnection.close();
/*     */         }
/*     */       
/* 146 */       } catch (Exception exception) {
/* 147 */         this.logger.debug("{} - Closing connection {} failed", new Object[] { this.poolName, paramConnection, exception });
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isConnectionAlive(Connection paramConnection) {
/*     */     try {
/*     */       
/* 156 */       try { setNetworkTimeout(paramConnection, this.validationTimeout);
/*     */         
/* 158 */         int i = (int)Math.max(1000L, this.validationTimeout) / 1000;
/*     */         
/* 160 */         if (this.isUseJdbc4Validation) {
/* 161 */           return paramConnection.isValid(i);
/*     */         }
/*     */         
/* 164 */         Statement statement = paramConnection.createStatement(); 
/* 165 */         try { if (this.isNetworkTimeoutSupported != 1) {
/* 166 */             setQueryTimeout(statement, i);
/*     */           }
/*     */           
/* 169 */           statement.execute(this.config.getConnectionTestQuery());
/* 170 */           if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null)
/*     */             try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/*     */          }
/* 173 */       finally { setNetworkTimeout(paramConnection, this.networkTimeout);
/*     */         
/* 175 */         if (this.isIsolateInternalQueries && !this.isAutoCommit) {
/* 176 */           paramConnection.rollback();
/*     */         } }
/*     */ 
/*     */       
/* 180 */       return true;
/*     */     }
/* 182 */     catch (Exception exception) {
/* 183 */       this.lastConnectionFailure.set(exception);
/* 184 */       this.logger.warn("{} - Failed to validate connection {} ({}). Possibly consider using a shorter maxLifetime value.", new Object[] { this.poolName, paramConnection, exception
/* 185 */             .getMessage() });
/* 186 */       return false;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   Exception getLastConnectionFailure() {
/* 192 */     return this.lastConnectionFailure.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public DataSource getUnwrappedDataSource() {
/* 197 */     return this.dataSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   PoolEntry newPoolEntry() {
/* 206 */     return new PoolEntry(newConnection(), this, this.isReadOnly, this.isAutoCommit);
/*     */   }
/*     */ 
/*     */   
/*     */   void resetConnectionState(Connection paramConnection, ProxyConnection paramProxyConnection, int paramInt) {
/* 211 */     int i = 0;
/*     */     
/* 213 */     if ((paramInt & 0x1) != 0 && paramProxyConnection.getReadOnlyState() != this.isReadOnly) {
/* 214 */       paramConnection.setReadOnly(this.isReadOnly);
/* 215 */       i |= 0x1;
/*     */     } 
/*     */     
/* 218 */     if ((paramInt & 0x2) != 0 && paramProxyConnection.getAutoCommitState() != this.isAutoCommit) {
/* 219 */       paramConnection.setAutoCommit(this.isAutoCommit);
/* 220 */       i |= 0x2;
/*     */     } 
/*     */     
/* 223 */     if ((paramInt & 0x4) != 0 && paramProxyConnection.getTransactionIsolationState() != this.transactionIsolation) {
/* 224 */       paramConnection.setTransactionIsolation(this.transactionIsolation);
/* 225 */       i |= 0x4;
/*     */     } 
/*     */     
/* 228 */     if ((paramInt & 0x8) != 0 && this.catalog != null && !this.catalog.equals(paramProxyConnection.getCatalogState())) {
/* 229 */       paramConnection.setCatalog(this.catalog);
/* 230 */       i |= 0x8;
/*     */     } 
/*     */     
/* 233 */     if ((paramInt & 0x10) != 0 && paramProxyConnection.getNetworkTimeoutState() != this.networkTimeout) {
/* 234 */       setNetworkTimeout(paramConnection, this.networkTimeout);
/* 235 */       i |= 0x10;
/*     */     } 
/*     */     
/* 238 */     if ((paramInt & 0x20) != 0 && this.schema != null && !this.schema.equals(paramProxyConnection.getSchemaState())) {
/* 239 */       paramConnection.setSchema(this.schema);
/* 240 */       i |= 0x20;
/*     */     } 
/*     */     
/* 243 */     if (i != 0 && this.logger.isDebugEnabled()) {
/* 244 */       this.logger.debug("{} - Reset ({}) on connection {}", new Object[] { this.poolName, stringFromResetBits(i), paramConnection });
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   void shutdownNetworkTimeoutExecutor() {
/* 250 */     if (this.netTimeoutExecutor instanceof ThreadPoolExecutor) {
/* 251 */       ((ThreadPoolExecutor)this.netTimeoutExecutor).shutdownNow();
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   long getLoginTimeout() {
/*     */     try {
/* 258 */       return (this.dataSource != null) ? this.dataSource.getLoginTimeout() : TimeUnit.SECONDS.toSeconds(5L);
/* 259 */     } catch (SQLException sQLException) {
/* 260 */       return TimeUnit.SECONDS.toSeconds(5L);
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
/*     */   void handleMBeans(HikariPool paramHikariPool, boolean paramBoolean) {
/* 275 */     if (!this.config.isRegisterMbeans()) {
/*     */       return;
/*     */     }
/*     */     try {
/*     */       ObjectName objectName1, objectName2;
/* 280 */       MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
/*     */ 
/*     */       
/* 283 */       if ("true".equals(System.getProperty("hikaricp.jmx.register2.0"))) {
/* 284 */         objectName1 = new ObjectName("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari:type=PoolConfig,name=" + this.poolName);
/* 285 */         objectName2 = new ObjectName("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari:type=Pool,name=" + this.poolName);
/*     */       } else {
/* 287 */         objectName1 = new ObjectName("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari:type=PoolConfig (" + this.poolName + ")");
/* 288 */         objectName2 = new ObjectName("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari:type=Pool (" + this.poolName + ")");
/*     */       } 
/* 290 */       if (paramBoolean) {
/* 291 */         if (!mBeanServer.isRegistered(objectName1)) {
/* 292 */           mBeanServer.registerMBean(this.config, objectName1);
/* 293 */           mBeanServer.registerMBean(paramHikariPool, objectName2);
/*     */         } else {
/* 295 */           this.logger.error("{} - JMX name ({}) is already registered.", this.poolName, this.poolName);
/*     */         }
/*     */       
/* 298 */       } else if (mBeanServer.isRegistered(objectName1)) {
/* 299 */         mBeanServer.unregisterMBean(objectName1);
/* 300 */         mBeanServer.unregisterMBean(objectName2);
/*     */       }
/*     */     
/* 303 */     } catch (Exception exception) {
/* 304 */       this.logger.warn("{} - Failed to {} management beans.", new Object[] { this.poolName, paramBoolean ? "register" : "unregister", exception });
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
/*     */   private void initializeDataSource() {
/* 317 */     String str1 = this.config.getJdbcUrl();
/* 318 */     String str2 = this.config.getUsername();
/* 319 */     String str3 = this.config.getPassword();
/* 320 */     String str4 = this.config.getDataSourceClassName();
/* 321 */     String str5 = this.config.getDriverClassName();
/* 322 */     String str6 = this.config.getDataSourceJNDI();
/* 323 */     Properties properties = this.config.getDataSourceProperties();
/*     */     
/* 325 */     DataSource dataSource = this.config.getDataSource();
/* 326 */     if (str4 != null && dataSource == null) {
/* 327 */       dataSource = (DataSource)UtilityElf.createInstance(str4, DataSource.class, new Object[0]);
/* 328 */       PropertyElf.setTargetFromProperties(dataSource, properties);
/*     */     } else {
/* 330 */       DriverDataSource driverDataSource; if (str1 != null && dataSource == null) {
/* 331 */         driverDataSource = new DriverDataSource(str1, str5, properties, str2, str3);
/*     */       }
/* 333 */       else if (str6 != null && driverDataSource == null) {
/*     */         try {
/* 335 */           InitialContext initialContext = new InitialContext();
/* 336 */           dataSource = (DataSource)initialContext.lookup(str6);
/* 337 */         } catch (NamingException namingException) {
/* 338 */           throw new HikariPool.PoolInitializationException(namingException);
/*     */         } 
/*     */       } 
/*     */     } 
/* 342 */     if (dataSource != null) {
/* 343 */       setLoginTimeout(dataSource);
/* 344 */       createNetworkTimeoutExecutor(dataSource, str4, str1);
/*     */     } 
/*     */     
/* 347 */     this.dataSource = dataSource;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private Connection newConnection() {
/* 357 */     long l = ClockSource.currentTime();
/*     */     
/* 359 */     Connection connection = null;
/*     */     try {
/* 361 */       String str1 = this.config.getUsername();
/* 362 */       String str2 = this.config.getPassword();
/*     */       
/* 364 */       connection = (str1 == null) ? this.dataSource.getConnection() : this.dataSource.getConnection(str1, str2);
/* 365 */       if (connection == null) {
/* 366 */         throw new SQLTransientConnectionException("DataSource returned null unexpectedly");
/*     */       }
/*     */       
/* 369 */       setupConnection(connection);
/* 370 */       this.lastConnectionFailure.set(null);
/* 371 */       return connection;
/*     */     }
/* 373 */     catch (Exception exception) {
/* 374 */       if (connection != null) {
/* 375 */         quietlyCloseConnection(connection, "(Failed to create/setup connection)");
/*     */       }
/* 377 */       else if (getLastConnectionFailure() == null) {
/* 378 */         this.logger.debug("{} - Failed to create/setup connection: {}", this.poolName, exception.getMessage());
/*     */       } 
/*     */       
/* 381 */       this.lastConnectionFailure.set(exception);
/* 382 */       throw exception;
/*     */     }
/*     */     finally {
/*     */       
/* 386 */       if (this.metricsTracker != null) {
/* 387 */         this.metricsTracker.recordConnectionCreated(ClockSource.elapsedMillis(l));
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
/*     */   private void setupConnection(Connection paramConnection) {
/*     */     try {
/* 401 */       if (this.networkTimeout == -1) {
/* 402 */         this.networkTimeout = getAndSetNetworkTimeout(paramConnection, this.validationTimeout);
/*     */       } else {
/*     */         
/* 405 */         setNetworkTimeout(paramConnection, this.validationTimeout);
/*     */       } 
/*     */       
/* 408 */       if (paramConnection.isReadOnly() != this.isReadOnly) {
/* 409 */         paramConnection.setReadOnly(this.isReadOnly);
/*     */       }
/*     */       
/* 412 */       if (paramConnection.getAutoCommit() != this.isAutoCommit) {
/* 413 */         paramConnection.setAutoCommit(this.isAutoCommit);
/*     */       }
/*     */       
/* 416 */       checkDriverSupport(paramConnection);
/*     */       
/* 418 */       if (this.transactionIsolation != this.defaultTransactionIsolation) {
/* 419 */         paramConnection.setTransactionIsolation(this.transactionIsolation);
/*     */       }
/*     */       
/* 422 */       if (this.catalog != null) {
/* 423 */         paramConnection.setCatalog(this.catalog);
/*     */       }
/*     */       
/* 426 */       if (this.schema != null) {
/* 427 */         paramConnection.setSchema(this.schema);
/*     */       }
/*     */       
/* 430 */       executeSql(paramConnection, this.config.getConnectionInitSql(), true);
/*     */       
/* 432 */       setNetworkTimeout(paramConnection, this.networkTimeout);
/*     */     }
/* 434 */     catch (SQLException sQLException) {
/* 435 */       throw new ConnectionSetupException(sQLException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void checkDriverSupport(Connection paramConnection) {
/* 446 */     if (!this.isValidChecked) {
/* 447 */       checkValidationSupport(paramConnection);
/* 448 */       checkDefaultIsolation(paramConnection);
/*     */       
/* 450 */       this.isValidChecked = true;
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
/*     */   private void checkValidationSupport(Connection paramConnection) {
/*     */     try {
/* 463 */       if (this.isUseJdbc4Validation) {
/* 464 */         paramConnection.isValid(1);
/*     */       } else {
/*     */         
/* 467 */         executeSql(paramConnection, this.config.getConnectionTestQuery(), false);
/*     */       }
/*     */     
/* 470 */     } catch (Exception|AbstractMethodError exception) {
/* 471 */       this.logger.error("{} - Failed to execute{} connection test query ({}).", new Object[] { this.poolName, this.isUseJdbc4Validation ? " isValid() for connection, configure" : "", exception.getMessage() });
/* 472 */       throw exception;
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
/*     */   private void checkDefaultIsolation(Connection paramConnection) {
/*     */     try {
/* 485 */       this.defaultTransactionIsolation = paramConnection.getTransactionIsolation();
/* 486 */       if (this.transactionIsolation == -1) {
/* 487 */         this.transactionIsolation = this.defaultTransactionIsolation;
/*     */       }
/*     */     }
/* 490 */     catch (SQLException sQLException) {
/* 491 */       this.logger.warn("{} - Default transaction isolation level detection failed ({}).", this.poolName, sQLException.getMessage());
/* 492 */       if (sQLException.getSQLState() != null && !sQLException.getSQLState().startsWith("08")) {
/* 493 */         throw sQLException;
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
/*     */   private void setQueryTimeout(Statement paramStatement, int paramInt) {
/* 506 */     if (this.isQueryTimeoutSupported != 0) {
/*     */       try {
/* 508 */         paramStatement.setQueryTimeout(paramInt);
/* 509 */         this.isQueryTimeoutSupported = 1;
/*     */       }
/* 511 */       catch (Exception exception) {
/* 512 */         if (this.isQueryTimeoutSupported == -1) {
/* 513 */           this.isQueryTimeoutSupported = 0;
/* 514 */           this.logger.info("{} - Failed to set query timeout for statement. ({})", this.poolName, exception.getMessage());
/*     */         } 
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
/*     */ 
/*     */   
/*     */   private int getAndSetNetworkTimeout(Connection paramConnection, long paramLong) {
/* 530 */     if (this.isNetworkTimeoutSupported != 0) {
/*     */       try {
/* 532 */         int i = paramConnection.getNetworkTimeout();
/* 533 */         paramConnection.setNetworkTimeout(this.netTimeoutExecutor, (int)paramLong);
/* 534 */         this.isNetworkTimeoutSupported = 1;
/* 535 */         return i;
/*     */       }
/* 537 */       catch (Exception|AbstractMethodError exception) {
/* 538 */         if (this.isNetworkTimeoutSupported == -1) {
/* 539 */           this.isNetworkTimeoutSupported = 0;
/*     */           
/* 541 */           this.logger.info("{} - Driver does not support get/set network timeout for connections. ({})", this.poolName, exception.getMessage());
/* 542 */           if (this.validationTimeout < TimeUnit.SECONDS.toMillis(1L)) {
/* 543 */             this.logger.warn("{} - A validationTimeout of less than 1 second cannot be honored on drivers without setNetworkTimeout() support.", this.poolName);
/*     */           }
/* 545 */           else if (this.validationTimeout % TimeUnit.SECONDS.toMillis(1L) != 0L) {
/* 546 */             this.logger.warn("{} - A validationTimeout with fractional second granularity cannot be honored on drivers without setNetworkTimeout() support.", this.poolName);
/*     */           } 
/*     */         } 
/*     */       } 
/*     */     }
/*     */     
/* 552 */     return 0;
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
/*     */   private void setNetworkTimeout(Connection paramConnection, long paramLong) {
/* 565 */     if (this.isNetworkTimeoutSupported == 1) {
/* 566 */       paramConnection.setNetworkTimeout(this.netTimeoutExecutor, (int)paramLong);
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
/*     */   private void executeSql(Connection paramConnection, String paramString, boolean paramBoolean) {
/* 580 */     if (paramString != null) {
/* 581 */       Statement statement = paramConnection.createStatement();
/*     */       
/* 583 */       try { statement.execute(paramString);
/* 584 */         if (statement != null) statement.close();  } catch (Throwable throwable) { if (statement != null)
/*     */           try { statement.close(); } catch (Throwable throwable1) { throwable.addSuppressed(throwable1); }   throw throwable; }
/* 586 */        if (this.isIsolateInternalQueries && !this.isAutoCommit) {
/* 587 */         if (paramBoolean) {
/* 588 */           paramConnection.commit();
/*     */         } else {
/*     */           
/* 591 */           paramConnection.rollback();
/*     */         } 
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createNetworkTimeoutExecutor(DataSource paramDataSource, String paramString1, String paramString2) {
/* 600 */     if ((paramString1 != null && paramString1.contains("Mysql")) || (paramString2 != null && paramString2
/* 601 */       .contains("mysql")) || (paramDataSource != null && paramDataSource
/* 602 */       .getClass().getName().contains("Mysql"))) {
/* 603 */       this.netTimeoutExecutor = new SynchronousExecutor();
/*     */     } else {
/*     */       
/* 606 */       ThreadFactory threadFactory = this.config.getThreadFactory();
/* 607 */       threadFactory = (threadFactory != null) ? threadFactory : (ThreadFactory)new UtilityElf.DefaultThreadFactory(this.poolName + " network timeout executor", true);
/* 608 */       ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor)Executors.newCachedThreadPool(threadFactory);
/* 609 */       threadPoolExecutor.setKeepAliveTime(15L, TimeUnit.SECONDS);
/* 610 */       threadPoolExecutor.allowCoreThreadTimeOut(true);
/* 611 */       this.netTimeoutExecutor = threadPoolExecutor;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setLoginTimeout(DataSource paramDataSource) {
/* 622 */     if (this.connectionTimeout != 2147483647L) {
/*     */       try {
/* 624 */         paramDataSource.setLoginTimeout(Math.max(1, (int)TimeUnit.MILLISECONDS.toSeconds(500L + this.connectionTimeout)));
/*     */       }
/* 626 */       catch (Exception exception) {
/* 627 */         this.logger.info("{} - Failed to set login timeout for data source. ({})", this.poolName, exception.getMessage());
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
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private String stringFromResetBits(int paramInt) {
/* 644 */     StringBuilder stringBuilder = new StringBuilder();
/* 645 */     for (byte b = 0; b < RESET_STATES.length; b++) {
/* 646 */       if ((paramInt & 1 << b) != 0) {
/* 647 */         stringBuilder.append(RESET_STATES[b]).append(", ");
/*     */       }
/*     */     } 
/*     */     
/* 651 */     stringBuilder.setLength(stringBuilder.length() - 2);
/* 652 */     return stringBuilder.toString();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   static class ConnectionSetupException
/*     */     extends Exception
/*     */   {
/*     */     private static final long serialVersionUID = 929872118275916521L;
/*     */ 
/*     */ 
/*     */     
/*     */     ConnectionSetupException(Throwable param1Throwable) {
/* 665 */       super(param1Throwable);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static class SynchronousExecutor
/*     */     implements Executor
/*     */   {
/*     */     private SynchronousExecutor() {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void execute(Runnable param1Runnable) {
/*     */       try {
/* 680 */         param1Runnable.run();
/*     */       }
/* 682 */       catch (Exception exception) {
/* 683 */         LoggerFactory.getLogger(PoolBase.class).debug("Failed to execute: {}", param1Runnable, exception);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   static interface IMetricsTrackerDelegate
/*     */     extends AutoCloseable
/*     */   {
/*     */     default void recordConnectionUsage(PoolEntry poolEntry) {}
/*     */ 
/*     */     
/*     */     default void recordConnectionCreated(long connectionCreatedMillis) {}
/*     */ 
/*     */     
/*     */     default void recordBorrowTimeoutStats(long startTime) {}
/*     */ 
/*     */     
/*     */     default void recordBorrowStats(PoolEntry poolEntry, long startTime) {}
/*     */ 
/*     */     
/*     */     default void recordConnectionTimeout() {}
/*     */     
/*     */     default void close() {}
/*     */   }
/*     */   
/*     */   static class MetricsTrackerDelegate
/*     */     implements IMetricsTrackerDelegate
/*     */   {
/*     */     final IMetricsTracker tracker;
/*     */     
/*     */     MetricsTrackerDelegate(IMetricsTracker param1IMetricsTracker) {
/* 715 */       this.tracker = param1IMetricsTracker;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void recordConnectionUsage(PoolEntry param1PoolEntry) {
/* 721 */       this.tracker.recordConnectionUsageMillis(param1PoolEntry.getMillisSinceBorrowed());
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void recordConnectionCreated(long param1Long) {
/* 727 */       this.tracker.recordConnectionCreatedMillis(param1Long);
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void recordBorrowTimeoutStats(long param1Long) {
/* 733 */       this.tracker.recordConnectionAcquiredNanos(ClockSource.elapsedNanos(param1Long));
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void recordBorrowStats(PoolEntry param1PoolEntry, long param1Long) {
/* 739 */       long l = ClockSource.currentTime();
/* 740 */       param1PoolEntry.lastBorrowed = l;
/* 741 */       this.tracker.recordConnectionAcquiredNanos(ClockSource.elapsedNanos(param1Long, l));
/*     */     }
/*     */ 
/*     */     
/*     */     public void recordConnectionTimeout() {
/* 746 */       this.tracker.recordConnectionTimeout();
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void close() {
/* 752 */       this.tracker.close();
/*     */     }
/*     */   }
/*     */   
/*     */   static final class NopMetricsTrackerDelegate implements IMetricsTrackerDelegate {}
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\PoolBase.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */