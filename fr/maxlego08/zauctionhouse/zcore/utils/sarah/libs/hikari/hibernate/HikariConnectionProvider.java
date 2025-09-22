/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.hibernate;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariConfig;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.HikariDataSource;
/*     */ import java.sql.Connection;
/*     */ import java.util.Map;
/*     */ import javax.sql.DataSource;
/*     */ import org.hibernate.HibernateException;
/*     */ import org.hibernate.Version;
/*     */ import org.hibernate.engine.jdbc.connections.spi.ConnectionProvider;
/*     */ import org.hibernate.service.UnknownUnwrapTypeException;
/*     */ import org.hibernate.service.spi.Configurable;
/*     */ import org.hibernate.service.spi.Stoppable;
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
/*     */ public class HikariConnectionProvider
/*     */   implements ConnectionProvider, Configurable, Stoppable
/*     */ {
/*     */   private static final long serialVersionUID = -9131625057941275711L;
/*  46 */   private static final Logger LOGGER = LoggerFactory.getLogger(HikariConnectionProvider.class);
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
/*  67 */   private HikariConfig hcfg = null;
/*  68 */   private HikariDataSource hds = null; public HikariConnectionProvider() {
/*  69 */     if (Version.getVersionString().substring(0, 5).compareTo("4.3.6") >= 1) {
/*  70 */       LOGGER.warn("fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.hibernate.HikariConnectionProvider has been deprecated for versions of Hibernate 4.3.6 and newer.  Please switch to org.hibernate.hikaricp.internal.HikariCPConnectionProvider.");
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
/*     */   public void configure(Map paramMap) {
/*     */     try {
/*  84 */       LOGGER.debug("Configuring HikariCP");
/*     */       
/*  86 */       this.hcfg = HikariConfigurationUtil.loadConfiguration(paramMap);
/*  87 */       this.hds = new HikariDataSource(this.hcfg);
/*     */     
/*     */     }
/*  90 */     catch (Exception exception) {
/*  91 */       throw new HibernateException(exception);
/*     */     } 
/*     */     
/*  94 */     LOGGER.debug("HikariCP Configured");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/* 104 */     Connection connection = null;
/* 105 */     if (this.hds != null) {
/* 106 */       connection = this.hds.getConnection();
/*     */     }
/*     */     
/* 109 */     return connection;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void closeConnection(Connection paramConnection) {
/* 115 */     paramConnection.close();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean supportsAggressiveRelease() {
/* 121 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUnwrappableAs(Class<?> paramClass) {
/* 128 */     return (ConnectionProvider.class.equals(paramClass) || HikariConnectionProvider.class.isAssignableFrom(paramClass));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public <T> T unwrap(Class<T> paramClass) {
/* 135 */     if (ConnectionProvider.class.equals(paramClass) || HikariConnectionProvider.class
/* 136 */       .isAssignableFrom(paramClass)) {
/* 137 */       return (T)this;
/*     */     }
/* 139 */     if (DataSource.class.isAssignableFrom(paramClass)) {
/* 140 */       return (T)this.hds;
/*     */     }
/*     */     
/* 143 */     throw new UnknownUnwrapTypeException(paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void stop() {
/* 154 */     this.hds.close();
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\hibernate\HikariConnectionProvider.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */