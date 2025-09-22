/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ClockSource;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ConcurrentBag;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.FastList;
/*     */ import java.sql.Connection;
/*     */ import java.sql.Statement;
/*     */ import java.util.concurrent.ScheduledFuture;
/*     */ import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
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
/*     */ final class PoolEntry
/*     */   implements ConcurrentBag.IConcurrentBagEntry
/*     */ {
/*  38 */   private static final Logger LOGGER = LoggerFactory.getLogger(PoolEntry.class);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  45 */   private volatile int state = 0;
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
/*  60 */   private static final AtomicIntegerFieldUpdater<PoolEntry> stateUpdater = AtomicIntegerFieldUpdater.newUpdater(PoolEntry.class, "state");
/*     */   Connection connection;
/*     */   long lastAccessed;
/*     */   
/*     */   PoolEntry(Connection paramConnection, PoolBase paramPoolBase, boolean paramBoolean1, boolean paramBoolean2) {
/*  65 */     this.connection = paramConnection;
/*  66 */     this.hikariPool = (HikariPool)paramPoolBase;
/*  67 */     this.isReadOnly = paramBoolean1;
/*  68 */     this.isAutoCommit = paramBoolean2;
/*  69 */     this.lastAccessed = ClockSource.currentTime();
/*  70 */     this.openStatements = new FastList(Statement.class, 16);
/*     */   }
/*     */   long lastBorrowed; private volatile boolean evict; private volatile ScheduledFuture<?> endOfLife;
/*     */   private volatile ScheduledFuture<?> keepalive;
/*     */   private final FastList<Statement> openStatements;
/*     */   private final HikariPool hikariPool;
/*     */   private final boolean isReadOnly;
/*     */   private final boolean isAutoCommit;
/*     */   
/*     */   void recycle(long paramLong) {
/*  80 */     if (this.connection != null) {
/*  81 */       this.lastAccessed = paramLong;
/*  82 */       this.hikariPool.recycle(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   void setFutureEol(ScheduledFuture<?> paramScheduledFuture) {
/*  93 */     this.endOfLife = paramScheduledFuture;
/*     */   }
/*     */   
/*     */   public void setKeepalive(ScheduledFuture<?> paramScheduledFuture) {
/*  97 */     this.keepalive = paramScheduledFuture;
/*     */   }
/*     */ 
/*     */   
/*     */   Connection createProxyConnection(ProxyLeakTask paramProxyLeakTask, long paramLong) {
/* 102 */     return ProxyFactory.getProxyConnection(this, this.connection, this.openStatements, paramProxyLeakTask, paramLong, this.isReadOnly, this.isAutoCommit);
/*     */   }
/*     */ 
/*     */   
/*     */   void resetConnectionState(ProxyConnection paramProxyConnection, int paramInt) {
/* 107 */     this.hikariPool.resetConnectionState(this.connection, paramProxyConnection, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   String getPoolName() {
/* 112 */     return this.hikariPool.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   boolean isMarkedEvicted() {
/* 117 */     return this.evict;
/*     */   }
/*     */ 
/*     */   
/*     */   void markEvicted() {
/* 122 */     this.evict = true;
/*     */   }
/*     */ 
/*     */   
/*     */   void evict(String paramString) {
/* 127 */     this.hikariPool.closeConnection(this, paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   long getMillisSinceBorrowed() {
/* 133 */     return ClockSource.elapsedMillis(this.lastBorrowed);
/*     */   }
/*     */ 
/*     */   
/*     */   PoolBase getPoolBase() {
/* 138 */     return this.hikariPool;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/* 145 */     long l = ClockSource.currentTime();
/* 146 */     return this.connection + ", accessed " + 
/* 147 */       ClockSource.elapsedDisplayString(this.lastAccessed, l) + " ago, " + 
/* 148 */       stateToString();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getState() {
/* 159 */     return stateUpdater.get(this);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean compareAndSet(int paramInt1, int paramInt2) {
/* 166 */     return stateUpdater.compareAndSet(this, paramInt1, paramInt2);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setState(int paramInt) {
/* 173 */     stateUpdater.set(this, paramInt);
/*     */   }
/*     */ 
/*     */   
/*     */   Connection close() {
/* 178 */     ScheduledFuture<?> scheduledFuture1 = this.endOfLife;
/* 179 */     if (scheduledFuture1 != null && !scheduledFuture1.isDone() && !scheduledFuture1.cancel(false)) {
/* 180 */       LOGGER.warn("{} - maxLifeTime expiration task cancellation unexpectedly returned false for connection {}", getPoolName(), this.connection);
/*     */     }
/*     */     
/* 183 */     ScheduledFuture<?> scheduledFuture2 = this.keepalive;
/* 184 */     if (scheduledFuture2 != null && !scheduledFuture2.isDone() && !scheduledFuture2.cancel(false)) {
/* 185 */       LOGGER.warn("{} - keepalive task cancellation unexpectedly returned false for connection {}", getPoolName(), this.connection);
/*     */     }
/*     */     
/* 188 */     Connection connection = this.connection;
/* 189 */     this.connection = null;
/* 190 */     this.endOfLife = null;
/* 191 */     this.keepalive = null;
/* 192 */     return connection;
/*     */   }
/*     */ 
/*     */   
/*     */   private String stateToString() {
/* 197 */     switch (this.state) {
/*     */       case 1:
/* 199 */         return "IN_USE";
/*     */       case 0:
/* 201 */         return "NOT_IN_USE";
/*     */       case -1:
/* 203 */         return "REMOVED";
/*     */       case -2:
/* 205 */         return "RESERVED";
/*     */     } 
/* 207 */     return "Invalid";
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\PoolEntry.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */