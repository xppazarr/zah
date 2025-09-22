/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.libs.hikari.pool;
/*     */ 
/*     */ import java.sql.Connection;
/*     */ import java.sql.DatabaseMetaData;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class ProxyDatabaseMetaData
/*     */   implements DatabaseMetaData
/*     */ {
/*     */   protected final ProxyConnection connection;
/*     */   protected final DatabaseMetaData delegate;
/*     */   
/*     */   ProxyDatabaseMetaData(ProxyConnection paramProxyConnection, DatabaseMetaData paramDatabaseMetaData) {
/*  18 */     this.connection = paramProxyConnection;
/*  19 */     this.delegate = paramDatabaseMetaData;
/*     */   }
/*     */ 
/*     */   
/*     */   final SQLException checkException(SQLException paramSQLException) {
/*  24 */     return this.connection.checkException(paramSQLException);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final String toString() {
/*  31 */     String str = this.delegate.toString();
/*  32 */     return getClass().getSimpleName() + '@' + System.identityHashCode(this) + " wrapping " + str;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final Connection getConnection() {
/*  43 */     return this.connection;
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getProcedures(String paramString1, String paramString2, String paramString3) {
/*  48 */     ResultSet resultSet = this.delegate.getProcedures(paramString1, paramString2, paramString3);
/*  49 */     Statement statement = resultSet.getStatement();
/*  50 */     if (statement != null) {
/*  51 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/*  53 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getProcedureColumns(String paramString1, String paramString2, String paramString3, String paramString4) {
/*  58 */     ResultSet resultSet = this.delegate.getProcedureColumns(paramString1, paramString2, paramString3, paramString4);
/*  59 */     Statement statement = resultSet.getStatement();
/*  60 */     if (statement != null) {
/*  61 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/*  63 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getTables(String paramString1, String paramString2, String paramString3, String[] paramArrayOfString) {
/*  68 */     ResultSet resultSet = this.delegate.getTables(paramString1, paramString2, paramString3, paramArrayOfString);
/*  69 */     Statement statement = resultSet.getStatement();
/*  70 */     if (statement != null) {
/*  71 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/*  73 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getSchemas() {
/*  78 */     ResultSet resultSet = this.delegate.getSchemas();
/*  79 */     Statement statement = resultSet.getStatement();
/*  80 */     if (statement != null) {
/*  81 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/*  83 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getCatalogs() {
/*  88 */     ResultSet resultSet = this.delegate.getCatalogs();
/*  89 */     Statement statement = resultSet.getStatement();
/*  90 */     if (statement != null) {
/*  91 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/*  93 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getTableTypes() {
/*  98 */     ResultSet resultSet = this.delegate.getTableTypes();
/*  99 */     Statement statement = resultSet.getStatement();
/* 100 */     if (statement != null) {
/* 101 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 103 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getColumns(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 108 */     ResultSet resultSet = this.delegate.getColumns(paramString1, paramString2, paramString3, paramString4);
/* 109 */     Statement statement = resultSet.getStatement();
/* 110 */     if (statement != null) {
/* 111 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 113 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getColumnPrivileges(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 118 */     ResultSet resultSet = this.delegate.getColumnPrivileges(paramString1, paramString2, paramString3, paramString4);
/* 119 */     Statement statement = resultSet.getStatement();
/* 120 */     if (statement != null) {
/* 121 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 123 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getTablePrivileges(String paramString1, String paramString2, String paramString3) {
/* 128 */     ResultSet resultSet = this.delegate.getTablePrivileges(paramString1, paramString2, paramString3);
/* 129 */     Statement statement = resultSet.getStatement();
/* 130 */     if (statement != null) {
/* 131 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 133 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getBestRowIdentifier(String paramString1, String paramString2, String paramString3, int paramInt, boolean paramBoolean) {
/* 138 */     ResultSet resultSet = this.delegate.getBestRowIdentifier(paramString1, paramString2, paramString3, paramInt, paramBoolean);
/* 139 */     Statement statement = resultSet.getStatement();
/* 140 */     if (statement != null) {
/* 141 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 143 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getVersionColumns(String paramString1, String paramString2, String paramString3) {
/* 148 */     ResultSet resultSet = this.delegate.getVersionColumns(paramString1, paramString2, paramString3);
/* 149 */     Statement statement = resultSet.getStatement();
/* 150 */     if (statement != null) {
/* 151 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 153 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getPrimaryKeys(String paramString1, String paramString2, String paramString3) {
/* 158 */     ResultSet resultSet = this.delegate.getPrimaryKeys(paramString1, paramString2, paramString3);
/* 159 */     Statement statement = resultSet.getStatement();
/* 160 */     if (statement != null) {
/* 161 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 163 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getImportedKeys(String paramString1, String paramString2, String paramString3) {
/* 168 */     ResultSet resultSet = this.delegate.getImportedKeys(paramString1, paramString2, paramString3);
/* 169 */     Statement statement = resultSet.getStatement();
/* 170 */     if (statement != null) {
/* 171 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 173 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getExportedKeys(String paramString1, String paramString2, String paramString3) {
/* 178 */     ResultSet resultSet = this.delegate.getExportedKeys(paramString1, paramString2, paramString3);
/* 179 */     Statement statement = resultSet.getStatement();
/* 180 */     if (statement != null) {
/* 181 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 183 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getCrossReference(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, String paramString6) {
/* 188 */     ResultSet resultSet = this.delegate.getCrossReference(paramString1, paramString2, paramString3, paramString4, paramString5, paramString6);
/* 189 */     Statement statement = resultSet.getStatement();
/* 190 */     if (statement != null) {
/* 191 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 193 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getTypeInfo() {
/* 198 */     ResultSet resultSet = this.delegate.getTypeInfo();
/* 199 */     Statement statement = resultSet.getStatement();
/* 200 */     if (statement != null) {
/* 201 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 203 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getIndexInfo(String paramString1, String paramString2, String paramString3, boolean paramBoolean1, boolean paramBoolean2) {
/* 208 */     ResultSet resultSet = this.delegate.getIndexInfo(paramString1, paramString2, paramString3, paramBoolean1, paramBoolean2);
/* 209 */     Statement statement = resultSet.getStatement();
/* 210 */     if (statement != null) {
/* 211 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 213 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getUDTs(String paramString1, String paramString2, String paramString3, int[] paramArrayOfint) {
/* 218 */     ResultSet resultSet = this.delegate.getUDTs(paramString1, paramString2, paramString3, paramArrayOfint);
/* 219 */     Statement statement = resultSet.getStatement();
/* 220 */     if (statement != null) {
/* 221 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 223 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getSuperTypes(String paramString1, String paramString2, String paramString3) {
/* 228 */     ResultSet resultSet = this.delegate.getSuperTypes(paramString1, paramString2, paramString3);
/* 229 */     Statement statement = resultSet.getStatement();
/* 230 */     if (statement != null) {
/* 231 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 233 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getSuperTables(String paramString1, String paramString2, String paramString3) {
/* 238 */     ResultSet resultSet = this.delegate.getSuperTables(paramString1, paramString2, paramString3);
/* 239 */     Statement statement = resultSet.getStatement();
/* 240 */     if (statement != null) {
/* 241 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 243 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getAttributes(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 248 */     ResultSet resultSet = this.delegate.getAttributes(paramString1, paramString2, paramString3, paramString4);
/* 249 */     Statement statement = resultSet.getStatement();
/* 250 */     if (statement != null) {
/* 251 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 253 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getSchemas(String paramString1, String paramString2) {
/* 258 */     ResultSet resultSet = this.delegate.getSchemas(paramString1, paramString2);
/* 259 */     Statement statement = resultSet.getStatement();
/* 260 */     if (statement != null) {
/* 261 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 263 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getClientInfoProperties() {
/* 268 */     ResultSet resultSet = this.delegate.getClientInfoProperties();
/* 269 */     Statement statement = resultSet.getStatement();
/* 270 */     if (statement != null) {
/* 271 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 273 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getFunctions(String paramString1, String paramString2, String paramString3) {
/* 278 */     ResultSet resultSet = this.delegate.getFunctions(paramString1, paramString2, paramString3);
/* 279 */     Statement statement = resultSet.getStatement();
/* 280 */     if (statement != null) {
/* 281 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 283 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getFunctionColumns(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 288 */     ResultSet resultSet = this.delegate.getFunctionColumns(paramString1, paramString2, paramString3, paramString4);
/* 289 */     Statement statement = resultSet.getStatement();
/* 290 */     if (statement != null) {
/* 291 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 293 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */   
/*     */   public ResultSet getPseudoColumns(String paramString1, String paramString2, String paramString3, String paramString4) {
/* 298 */     ResultSet resultSet = this.delegate.getPseudoColumns(paramString1, paramString2, paramString3, paramString4);
/* 299 */     Statement statement = resultSet.getStatement();
/* 300 */     if (statement != null) {
/* 301 */       statement = ProxyFactory.getProxyStatement(this.connection, statement);
/*     */     }
/* 303 */     return ProxyFactory.getProxyResultSet(this.connection, (ProxyStatement)statement, resultSet);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public final <T> T unwrap(Class<T> paramClass) {
/* 311 */     if (paramClass.isInstance(this.delegate)) {
/* 312 */       return (T)this.delegate;
/*     */     }
/* 314 */     if (this.delegate != null) {
/* 315 */       return this.delegate.unwrap(paramClass);
/*     */     }
/*     */     
/* 318 */     throw new SQLException("Wrapped DatabaseMetaData is not an instance of " + paramClass);
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\libs\hikari\pool\ProxyDatabaseMetaData.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */