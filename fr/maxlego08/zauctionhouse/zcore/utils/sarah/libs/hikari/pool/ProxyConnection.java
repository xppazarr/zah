/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.SQLExceptionOverride;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.ClockSource;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.util.FastList;
/*     */ import java.lang.reflect.InvocationHandler;
/*     */ import java.lang.reflect.Method;
/*     */ import java.lang.reflect.Proxy;
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Savepoint;
/*     */ import java.sql.Statement;
/*     */ import java.util.HashSet;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.Executor;
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
/*     */ public abstract class ProxyConnection
/*     */   implements Connection
/*     */ {
/*     */   static final int DIRTY_BIT_READONLY = 1;
/*     */   static final int DIRTY_BIT_AUTOCOMMIT = 2;
/*     */   static final int DIRTY_BIT_ISOLATION = 4;
/*     */   static final int DIRTY_BIT_CATALOG = 8;
/*     */   static final int DIRTY_BIT_NETTIMEOUT = 16;
/*     */   static final int DIRTY_BIT_SCHEMA = 32;
/*  72 */   private static final Logger LOGGER = LoggerFactory.getLogger(ProxyConnection.class);
/*     */   
/*  74 */   private static final Set<String> ERROR_STATES = new HashSet<>(); static {
/*  75 */     ERROR_STATES.add("0A000");
/*  76 */     ERROR_STATES.add("57P01");
/*  77 */     ERROR_STATES.add("57P02");
/*  78 */     ERROR_STATES.add("57P03");
/*  79 */     ERROR_STATES.add("01002");
/*  80 */     ERROR_STATES.add("JZ0C0");
/*  81 */     ERROR_STATES.add("JZ0C1");
/*     */   }
/*  83 */   protected Connection delegate; private final PoolEntry poolEntry; private final ProxyLeakTask leakTask; private static final Set<Integer> ERROR_CODES = new HashSet<>(); private final FastList<Statement> openStatements; private int dirtyBits; private long lastAccess; static {
/*  84 */     ERROR_CODES.add(Integer.valueOf(500150));
/*  85 */     ERROR_CODES.add(Integer.valueOf(2399));
/*     */   }
/*     */   private boolean isCommitStateDirty; private boolean isReadOnly;
/*     */   private boolean isAutoCommit;
/*     */   private int networkTimeout;
/*     */   private int transactionIsolation;
/*     */   private String dbcatalog;
/*     */   private String dbschema;
/*     */   
/*     */   protected ProxyConnection(PoolEntry paramPoolEntry, Connection paramConnection, FastList<Statement> paramFastList, ProxyLeakTask paramProxyLeakTask, long paramLong, boolean paramBoolean1, boolean paramBoolean2) {
/*  95 */     this.poolEntry = paramPoolEntry;
/*  96 */     this.delegate = paramConnection;
/*  97 */     this.openStatements = paramFastList;
/*  98 */     this.leakTask = paramProxyLeakTask;
/*  99 */     this.lastAccess = paramLong;
/* 100 */     this.isReadOnly = paramBoolean1;
/* 101 */     this.isAutoCommit = paramBoolean2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/* 108 */     return getClass().getSimpleName() + '@' + System.identityHashCode(this) + " wrapping " + this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final boolean getAutoCommitState() {
/* 117 */     return this.isAutoCommit;
/*     */   }
/*     */ 
/*     */   
/*     */   final String getCatalogState() {
/* 122 */     return this.dbcatalog;
/*     */   }
/*     */ 
/*     */   
/*     */   final String getSchemaState() {
/* 127 */     return this.dbschema;
/*     */   }
/*     */ 
/*     */   
/*     */   final int getTransactionIsolationState() {
/* 132 */     return this.transactionIsolation;
/*     */   }
/*     */ 
/*     */   
/*     */   final boolean getReadOnlyState() {
/* 137 */     return this.isReadOnly;
/*     */   }
/*     */ 
/*     */   
/*     */   final int getNetworkTimeoutState() {
/* 142 */     return this.networkTimeout;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   final PoolEntry getPoolEntry() {
/* 151 */     return this.poolEntry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   final SQLException checkException(SQLException paramSQLException) {
/* 157 */     boolean bool = false;
/* 158 */     SQLException sQLException = paramSQLException;
/* 159 */     SQLExceptionOverride sQLExceptionOverride = (this.poolEntry.getPoolBase()).exceptionOverride;
/* 160 */     for (byte b = 0; this.delegate != ClosedConnection.CLOSED_CONNECTION && sQLException != null && b < 10; b++) {
/* 161 */       String str = sQLException.getSQLState();
/* 162 */       if ((str != null && str.startsWith("08")) || sQLException instanceof java.sql.SQLTimeoutException || ERROR_STATES
/*     */         
/* 164 */         .contains(str) || ERROR_CODES
/* 165 */         .contains(Integer.valueOf(sQLException.getErrorCode()))) {
/*     */         
/* 167 */         if (sQLExceptionOverride != null && sQLExceptionOverride.adjudicate(sQLException) == SQLExceptionOverride.Override.DO_NOT_EVICT) {
/*     */           break;
/*     */         }
/*     */ 
/*     */         
/* 172 */         bool = true;
/*     */         
/*     */         break;
/*     */       } 
/* 176 */       sQLException = sQLException.getNextException();
/*     */     } 
/*     */ 
/*     */     
/* 180 */     if (bool) {
/* 181 */       SQLException sQLException1 = (sQLException != null) ? sQLException : paramSQLException;
/* 182 */       LOGGER.warn("{} - Connection {} marked as broken because of SQLSTATE({}), ErrorCode({})", new Object[] { this.poolEntry
/* 183 */             .getPoolName(), this.delegate, sQLException1.getSQLState(), Integer.valueOf(sQLException1.getErrorCode()), sQLException1 });
/* 184 */       this.leakTask.cancel();
/* 185 */       this.poolEntry.evict("(connection is broken)");
/* 186 */       this.delegate = ClosedConnection.CLOSED_CONNECTION;
/*     */     } 
/*     */     
/* 189 */     return paramSQLException;
/*     */   }
/*     */ 
/*     */   
/*     */   final synchronized void untrackStatement(Statement paramStatement) {
/* 194 */     this.openStatements.remove(paramStatement);
/*     */   }
/*     */ 
/*     */   
/*     */   final void markCommitStateDirty() {
/* 199 */     if (this.isAutoCommit) {
/* 200 */       this.lastAccess = ClockSource.currentTime();
/*     */     } else {
/*     */       
/* 203 */       this.isCommitStateDirty = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   void cancelLeakTask() {
/* 209 */     this.leakTask.cancel();
/*     */   }
/*     */ 
/*     */   
/*     */   private synchronized <T extends Statement> T trackStatement(T paramT) {
/* 214 */     this.openStatements.add(paramT);
/*     */     
/* 216 */     return paramT;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private synchronized void closeStatements() {
/* 222 */     int i = this.openStatements.size();
/* 223 */     if (i > 0) {
/* 224 */       for (byte b = 0; b < i && this.delegate != ClosedConnection.CLOSED_CONNECTION; b++) { try {
/* 225 */           Statement statement = (Statement)this.openStatements.get(b);
/*     */           
/* 227 */           if (statement != null) statement.close(); 
/* 228 */         } catch (SQLException sQLException) {
/* 229 */           LOGGER.warn("{} - Connection {} marked as broken because of an exception closing open statements during Connection.close()", this.poolEntry
/* 230 */               .getPoolName(), this.delegate);
/* 231 */           this.leakTask.cancel();
/* 232 */           this.poolEntry.evict("(exception closing Statements during Connection.close())");
/* 233 */           this.delegate = ClosedConnection.CLOSED_CONNECTION;
/*     */         }  }
/*     */ 
/*     */       
/* 237 */       this.openStatements.clear();
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
/*     */   public final void close() {
/* 250 */     closeStatements();
/*     */     
/* 252 */     if (this.delegate != ClosedConnection.CLOSED_CONNECTION) {
/* 253 */       this.leakTask.cancel();
/*     */       
/*     */       try {
/* 256 */         if (this.isCommitStateDirty && !this.isAutoCommit) {
/* 257 */           this.delegate.rollback();
/* 258 */           this.lastAccess = ClockSource.currentTime();
/* 259 */           LOGGER.debug("{} - Executed rollback on connection {} due to dirty commit state on close().", this.poolEntry.getPoolName(), this.delegate);
/*     */         } 
/*     */         
/* 262 */         if (this.dirtyBits != 0) {
/* 263 */           this.poolEntry.resetConnectionState(this, this.dirtyBits);
/* 264 */           this.lastAccess = ClockSource.currentTime();
/*     */         } 
/*     */         
/* 267 */         this.delegate.clearWarnings();
/*     */       }
/* 269 */       catch (SQLException sQLException) {
/*     */         
/* 271 */         if (!this.poolEntry.isMarkedEvicted()) {
/* 272 */           throw checkException(sQLException);
/*     */         }
/*     */       } finally {
/*     */         
/* 276 */         this.delegate = ClosedConnection.CLOSED_CONNECTION;
/* 277 */         this.poolEntry.recycle(this.lastAccess);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isClosed() {
/* 287 */     return (this.delegate == ClosedConnection.CLOSED_CONNECTION);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement() {
/* 294 */     return ProxyFactory.getProxyStatement(this, trackStatement(this.delegate.createStatement()));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement(int paramInt1, int paramInt2) {
/* 301 */     return ProxyFactory.getProxyStatement(this, trackStatement(this.delegate.createStatement(paramInt1, paramInt2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Statement createStatement(int paramInt1, int paramInt2, int paramInt3) {
/* 308 */     return ProxyFactory.getProxyStatement(this, trackStatement(this.delegate.createStatement(paramInt1, paramInt2, paramInt3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String paramString) {
/* 316 */     return ProxyFactory.getProxyCallableStatement(this, trackStatement(this.delegate.prepareCall(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2) {
/* 323 */     return ProxyFactory.getProxyCallableStatement(this, trackStatement(this.delegate.prepareCall(paramString, paramInt1, paramInt2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCall(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 330 */     return ProxyFactory.getProxyCallableStatement(this, trackStatement(this.delegate.prepareCall(paramString, paramInt1, paramInt2, paramInt3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString) {
/* 337 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString, int paramInt) {
/* 344 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString, paramInt)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2) {
/* 351 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString, paramInt1, paramInt2)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString, int paramInt1, int paramInt2, int paramInt3) {
/* 358 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString, paramInt1, paramInt2, paramInt3)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString, int[] paramArrayOfint) {
/* 365 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString, paramArrayOfint)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public PreparedStatement prepareStatement(String paramString, String[] paramArrayOfString) {
/* 372 */     return ProxyFactory.getProxyPreparedStatement(this, trackStatement(this.delegate.prepareStatement(paramString, paramArrayOfString)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DatabaseMetaData getMetaData() {
/* 379 */     markCommitStateDirty();
/* 380 */     return ProxyFactory.getProxyDatabaseMetaData(this, this.delegate.getMetaData());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void commit() {
/* 387 */     this.delegate.commit();
/* 388 */     this.isCommitStateDirty = false;
/* 389 */     this.lastAccess = ClockSource.currentTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback() {
/* 396 */     this.delegate.rollback();
/* 397 */     this.isCommitStateDirty = false;
/* 398 */     this.lastAccess = ClockSource.currentTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void rollback(Savepoint paramSavepoint) {
/* 405 */     this.delegate.rollback(paramSavepoint);
/* 406 */     this.isCommitStateDirty = false;
/* 407 */     this.lastAccess = ClockSource.currentTime();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setAutoCommit(boolean paramBoolean) {
/* 414 */     this.delegate.setAutoCommit(paramBoolean);
/* 415 */     this.isAutoCommit = paramBoolean;
/* 416 */     this.dirtyBits |= 0x2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setReadOnly(boolean paramBoolean) {
/* 423 */     this.delegate.setReadOnly(paramBoolean);
/* 424 */     this.isReadOnly = paramBoolean;
/* 425 */     this.isCommitStateDirty = false;
/* 426 */     this.dirtyBits |= 0x1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTransactionIsolation(int paramInt) {
/* 433 */     this.delegate.setTransactionIsolation(paramInt);
/* 434 */     this.transactionIsolation = paramInt;
/* 435 */     this.dirtyBits |= 0x4;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCatalog(String paramString) {
/* 442 */     this.delegate.setCatalog(paramString);
/* 443 */     this.dbcatalog = paramString;
/* 444 */     this.dirtyBits |= 0x8;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setNetworkTimeout(Executor paramExecutor, int paramInt) {
/* 451 */     this.delegate.setNetworkTimeout(paramExecutor, paramInt);
/* 452 */     this.networkTimeout = paramInt;
/* 453 */     this.dirtyBits |= 0x10;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSchema(String paramString) {
/* 460 */     this.delegate.setSchema(paramString);
/* 461 */     this.dbschema = paramString;
/* 462 */     this.dirtyBits |= 0x20;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final boolean isWrapperFor(Class<?> paramClass) {
/* 469 */     return (paramClass.isInstance(this.delegate) || (this.delegate != null && this.delegate.isWrapperFor(paramClass)));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T unwrap(Class<T> paramClass) {
/* 477 */     if (paramClass.isInstance(this.delegate)) {
/* 478 */       return (T)this.delegate;
/*     */     }
/* 480 */     if (this.delegate != null) {
/* 481 */       return this.delegate.unwrap(paramClass);
/*     */     }
/*     */     
/* 484 */     throw new SQLException("Wrapped connection is not an instance of " + paramClass);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static final class ClosedConnection
/*     */   {
/* 493 */     static final Connection CLOSED_CONNECTION = getClosedConnection();
/*     */ 
/*     */     
/*     */     private static Connection getClosedConnection() {
/* 497 */       InvocationHandler invocationHandler = (param1Object, param1Method, param1ArrayOfObject) -> {
/*     */           String str = param1Method.getName();
/*     */           
/*     */           if ("isClosed".equals(str)) {
/*     */             return Boolean.TRUE;
/*     */           }
/*     */           
/*     */           if ("isValid".equals(str)) {
/*     */             return Boolean.FALSE;
/*     */           }
/*     */           if ("abort".equals(str)) {
/*     */             return void.class;
/*     */           }
/*     */           if ("close".equals(str)) {
/*     */             return void.class;
/*     */           }
/*     */           if ("toString".equals(str)) {
/*     */             return ClosedConnection.class.getCanonicalName();
/*     */           }
/*     */           throw new SQLException("Connection is closed");
/*     */         };
/* 518 */       return (Connection)Proxy.newProxyInstance(Connection.class.getClassLoader(), new Class[] { Connection.class }, invocationHandler);
/*     */     }
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyConnection.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */