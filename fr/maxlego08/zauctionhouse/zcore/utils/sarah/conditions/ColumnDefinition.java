/*     */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions;
/*     */ 
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
/*     */ import fr.maxlego08.zauctionhouse.zcore.utils.sarah.database.DatabaseType;
/*     */ 
/*     */ public class ColumnDefinition
/*     */ {
/*     */   private String name;
/*     */   private String type;
/*     */   private int length;
/*     */   private int decimal;
/*     */   private boolean nullable = false;
/*     */   private String defaultValue;
/*     */   private boolean isPrimaryKey = false;
/*     */   private String referenceTable;
/*     */   private Object object;
/*     */   private boolean isAutoIncrement;
/*     */   private boolean unique = false;
/*     */   
/*     */   public ColumnDefinition(String paramString1, String paramString2) {
/*  21 */     this.name = paramString1;
/*  22 */     this.type = paramString2;
/*     */   }
/*     */   
/*     */   public ColumnDefinition(String paramString) {
/*  26 */     this.name = paramString;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String build(DatabaseConfiguration paramDatabaseConfiguration) {
/*  36 */     StringBuilder stringBuilder = new StringBuilder("`" + this.name + "` " + this.type);
/*     */     
/*  38 */     if (this.length != 0 && this.decimal != 0) {
/*  39 */       stringBuilder.append("(").append(this.length).append(",").append(this.decimal).append(")");
/*  40 */     } else if (this.length != 0) {
/*  41 */       stringBuilder.append("(").append(this.length).append(")");
/*     */     } 
/*     */     
/*  44 */     if (this.isAutoIncrement && 
/*  45 */       paramDatabaseConfiguration.getDatabaseType() != DatabaseType.SQLITE) {
/*  46 */       stringBuilder.append(" AUTO_INCREMENT");
/*     */     }
/*     */ 
/*     */     
/*  50 */     if (this.nullable) {
/*  51 */       stringBuilder.append(" NULL");
/*     */     } else {
/*  53 */       stringBuilder.append(" NOT NULL");
/*     */     } 
/*     */     
/*  56 */     if (this.defaultValue != null) {
/*  57 */       stringBuilder.append(" DEFAULT ").append(this.defaultValue);
/*     */     }
/*     */     
/*  60 */     if (this.unique) {
/*  61 */       stringBuilder.append(" UNIQUE");
/*     */     }
/*     */     
/*  64 */     return stringBuilder.toString();
/*     */   }
/*     */   
/*     */   public String getName() {
/*  68 */     return this.name;
/*     */   }
/*     */   
/*     */   public void setName(String paramString) {
/*  72 */     this.name = paramString;
/*     */   }
/*     */   
/*     */   public String getSafeName() {
/*  76 */     return String.format("`%s`", new Object[] { this.name });
/*     */   }
/*     */   
/*     */   public String getType() {
/*  80 */     return this.type;
/*     */   }
/*     */   
/*     */   public void setType(String paramString) {
/*  84 */     this.type = paramString;
/*     */   }
/*     */   
/*     */   public Integer getLength() {
/*  88 */     return Integer.valueOf(this.length);
/*     */   }
/*     */   
/*     */   public ColumnDefinition setLength(Integer paramInteger) {
/*  92 */     this.length = paramInteger.intValue();
/*  93 */     return this;
/*     */   }
/*     */   
/*     */   public ColumnDefinition setLength(int paramInt) {
/*  97 */     this.length = paramInt;
/*  98 */     return this;
/*     */   }
/*     */   
/*     */   public ColumnDefinition setDecimal(Integer paramInteger) {
/* 102 */     this.decimal = paramInteger.intValue();
/* 103 */     return this;
/*     */   }
/*     */   
/*     */   public Boolean getNullable() {
/* 107 */     return Boolean.valueOf(this.nullable);
/*     */   }
/*     */   
/*     */   public String getDefaultValue() {
/* 111 */     return this.defaultValue;
/*     */   }
/*     */   
/*     */   public void setDefaultValue(String paramString) {
/* 115 */     this.defaultValue = paramString;
/*     */   }
/*     */   
/*     */   public boolean isPrimaryKey() {
/* 119 */     return this.isPrimaryKey;
/*     */   }
/*     */   
/*     */   public void setPrimaryKey(boolean paramBoolean) {
/* 123 */     this.isPrimaryKey = paramBoolean;
/*     */   }
/*     */   
/*     */   public String getReferenceTable() {
/* 127 */     return this.referenceTable;
/*     */   }
/*     */   
/*     */   public void setReferenceTable(String paramString) {
/* 131 */     this.referenceTable = paramString;
/*     */   }
/*     */   
/*     */   public boolean isNullable() {
/* 135 */     return this.nullable;
/*     */   }
/*     */   
/*     */   public void setNullable(Boolean paramBoolean) {
/* 139 */     this.nullable = paramBoolean.booleanValue();
/*     */   }
/*     */   
/*     */   public void setNullable(boolean paramBoolean) {
/* 143 */     this.nullable = paramBoolean;
/*     */   }
/*     */   
/*     */   public void setUnique(boolean paramBoolean) {
/* 147 */     this.unique = paramBoolean;
/*     */   }
/*     */   
/*     */   public Object getObject() {
/* 151 */     return this.object;
/*     */   }
/*     */   
/*     */   public ColumnDefinition setObject(Object paramObject) {
/* 155 */     this.object = paramObject;
/* 156 */     return this;
/*     */   }
/*     */   
/*     */   public ColumnDefinition setAutoIncrement(boolean paramBoolean) {
/* 160 */     this.isAutoIncrement = paramBoolean;
/* 161 */     return this;
/*     */   }
/*     */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\conditions\ColumnDefinition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */