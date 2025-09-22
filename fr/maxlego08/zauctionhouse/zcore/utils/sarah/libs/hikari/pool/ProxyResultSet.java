/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
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
/*     */ public abstract class ProxyResultSet
/*     */   implements ResultSet
/*     */ {
/*     */   protected final ProxyConnection connection;
/*     */   protected final ProxyStatement statement;
/*     */   final ResultSet delegate;
/*     */   
/*     */   protected ProxyResultSet(ProxyConnection paramProxyConnection, ProxyStatement paramProxyStatement, ResultSet paramResultSet) {
/*  36 */     this.connection = paramProxyConnection;
/*  37 */     this.statement = paramProxyStatement;
/*  38 */     this.delegate = paramResultSet;
/*     */   }
/*     */ 
/*     */   
/*     */   final SQLException checkException(SQLException paramSQLException) {
/*  43 */     return this.connection.checkException(paramSQLException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String toString() {
/*  50 */     return getClass().getSimpleName() + '@' + System.identityHashCode(this) + " wrapping " + this.delegate;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Statement getStatement() {
/*  61 */     return this.statement;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateRow() {
/*  68 */     this.connection.markCommitStateDirty();
/*  69 */     this.delegate.updateRow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void insertRow() {
/*  76 */     this.connection.markCommitStateDirty();
/*  77 */     this.delegate.insertRow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteRow() {
/*  84 */     this.connection.markCommitStateDirty();
/*  85 */     this.delegate.deleteRow();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T unwrap(Class<T> paramClass) {
/*  93 */     if (paramClass.isInstance(this.delegate)) {
/*  94 */       return (T)this.delegate;
/*     */     }
/*  96 */     if (this.delegate != null) {
/*  97 */       return this.delegate.unwrap(paramClass);
/*     */     }
/*     */     
/* 100 */     throw new SQLException("Wrapped ResultSet is not an instance of " + paramClass);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyResultSet.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */