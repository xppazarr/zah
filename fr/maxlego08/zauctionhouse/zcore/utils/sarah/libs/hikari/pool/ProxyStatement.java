/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
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
/*     */ public abstract class ProxyStatement
/*     */   implements Statement
/*     */ {
/*     */   protected final ProxyConnection connection;
/*     */   final Statement delegate;
/*     */   private boolean isClosed;
/*     */   private ResultSet proxyResultSet;
/*     */   
/*     */   ProxyStatement(ProxyConnection paramProxyConnection, Statement paramStatement) {
/*  39 */     this.connection = paramProxyConnection;
/*  40 */     this.delegate = paramStatement;
/*     */   }
/*     */ 
/*     */   
/*     */   final SQLException checkException(SQLException paramSQLException) {
/*  45 */     return this.connection.checkException(paramSQLException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/*  52 */     String str = this.delegate.toString();
/*  53 */     return getClass().getSimpleName() + '@' + System.identityHashCode(this) + " wrapping " + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final void close() {
/*  64 */     synchronized (this) {
/*  65 */       if (this.isClosed) {
/*     */         return;
/*     */       }
/*     */       
/*  69 */       this.isClosed = true;
/*     */     } 
/*     */     
/*  72 */     this.connection.untrackStatement(this.delegate);
/*     */     
/*     */     try {
/*  75 */       this.delegate.close();
/*     */     }
/*  77 */     catch (SQLException sQLException) {
/*  78 */       throw this.connection.checkException(sQLException);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Connection getConnection() {
/*  86 */     return this.connection;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String paramString) {
/*  93 */     this.connection.markCommitStateDirty();
/*  94 */     return this.delegate.execute(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String paramString, int paramInt) {
/* 101 */     this.connection.markCommitStateDirty();
/* 102 */     return this.delegate.execute(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet executeQuery(String paramString) {
/* 109 */     this.connection.markCommitStateDirty();
/* 110 */     ResultSet resultSet = this.delegate.executeQuery(paramString);
/* 111 */     return ProxyFactory.getProxyResultSet(this.connection, this, resultSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String paramString) {
/* 118 */     this.connection.markCommitStateDirty();
/* 119 */     return this.delegate.executeUpdate(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int[] executeBatch() {
/* 126 */     this.connection.markCommitStateDirty();
/* 127 */     return this.delegate.executeBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String paramString, int paramInt) {
/* 134 */     this.connection.markCommitStateDirty();
/* 135 */     return this.delegate.executeUpdate(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String paramString, int[] paramArrayOfint) {
/* 142 */     this.connection.markCommitStateDirty();
/* 143 */     return this.delegate.executeUpdate(paramString, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeUpdate(String paramString, String[] paramArrayOfString) {
/* 150 */     this.connection.markCommitStateDirty();
/* 151 */     return this.delegate.executeUpdate(paramString, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String paramString, int[] paramArrayOfint) {
/* 158 */     this.connection.markCommitStateDirty();
/* 159 */     return this.delegate.execute(paramString, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean execute(String paramString, String[] paramArrayOfString) {
/* 166 */     this.connection.markCommitStateDirty();
/* 167 */     return this.delegate.execute(paramString, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long[] executeLargeBatch() {
/* 174 */     this.connection.markCommitStateDirty();
/* 175 */     return this.delegate.executeLargeBatch();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long executeLargeUpdate(String paramString) {
/* 182 */     this.connection.markCommitStateDirty();
/* 183 */     return this.delegate.executeLargeUpdate(paramString);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long executeLargeUpdate(String paramString, int paramInt) {
/* 190 */     this.connection.markCommitStateDirty();
/* 191 */     return this.delegate.executeLargeUpdate(paramString, paramInt);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long executeLargeUpdate(String paramString, int[] paramArrayOfint) {
/* 198 */     this.connection.markCommitStateDirty();
/* 199 */     return this.delegate.executeLargeUpdate(paramString, paramArrayOfint);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long executeLargeUpdate(String paramString, String[] paramArrayOfString) {
/* 206 */     this.connection.markCommitStateDirty();
/* 207 */     return this.delegate.executeLargeUpdate(paramString, paramArrayOfString);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getResultSet() {
/* 213 */     ResultSet resultSet = this.delegate.getResultSet();
/* 214 */     if (resultSet != null) {
/* 215 */       if (this.proxyResultSet == null || ((ProxyResultSet)this.proxyResultSet).delegate != resultSet) {
/* 216 */         this.proxyResultSet = ProxyFactory.getProxyResultSet(this.connection, this, resultSet);
/*     */       }
/*     */     } else {
/*     */       
/* 220 */       this.proxyResultSet = null;
/*     */     } 
/* 222 */     return this.proxyResultSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ResultSet getGeneratedKeys() {
/* 229 */     ResultSet resultSet = this.delegate.getGeneratedKeys();
/* 230 */     if (this.proxyResultSet == null || ((ProxyResultSet)this.proxyResultSet).delegate != resultSet) {
/* 231 */       this.proxyResultSet = ProxyFactory.getProxyResultSet(this.connection, this, resultSet);
/*     */     }
/* 233 */     return this.proxyResultSet;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T unwrap(Class<T> paramClass) {
/* 241 */     if (paramClass.isInstance(this.delegate)) {
/* 242 */       return (T)this.delegate;
/*     */     }
/* 244 */     if (this.delegate != null) {
/* 245 */       return this.delegate.unwrap(paramClass);
/*     */     }
/*     */     
/* 248 */     throw new SQLException("Wrapped statement is not an instance of " + paramClass);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyStatement.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */