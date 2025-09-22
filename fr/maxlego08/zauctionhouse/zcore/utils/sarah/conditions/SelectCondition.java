/*    */ package fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions;
/*    */ 
/*    */ import java.util.Objects;
/*    */ 
/*    */ public class SelectCondition {
/*    */   private final String tablePrefix;
/*    */   private final String column;
/*    */   private final String aliases;
/*    */   private final boolean isCoalesce;
/*    */   private final Object defaultValue;
/*    */   
/*    */   public SelectCondition(String paramString1, String paramString2, String paramString3, boolean paramBoolean, Object paramObject) {
/* 13 */     this.tablePrefix = paramString1;
/* 14 */     this.column = paramString2;
/* 15 */     this.aliases = paramString3;
/* 16 */     this.isCoalesce = paramBoolean;
/* 17 */     this.defaultValue = paramObject;
/*    */   }
/*    */   
/*    */   public String getTablePrefix() {
/* 21 */     return this.tablePrefix;
/*    */   }
/*    */   
/*    */   public String getColumn() {
/* 25 */     return this.column;
/*    */   }
/*    */   
/*    */   public boolean isCoalesce() {
/* 29 */     return this.isCoalesce;
/*    */   }
/*    */   
/*    */   public Object getDefaultValue() {
/* 33 */     return this.defaultValue;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String getSelectColumn() {
/* 46 */     String str = (this.tablePrefix == null) ? getColumnAndAliases() : (this.tablePrefix + "." + getColumnAndAliases());
/* 47 */     if (this.isCoalesce) {
/* 48 */       String str1 = (this.tablePrefix == null) ? ("`" + this.column + "`") : (this.tablePrefix + ".`" + this.column + "`");
/* 49 */       return "COALESCE(" + str1 + ", " + this.defaultValue + ")" + getAliases();
/*    */     } 
/* 51 */     return str;
/*    */   }
/*    */   
/*    */   private String getColumnAndAliases() {
/* 55 */     return "`" + this.column + "`" + getAliases();
/*    */   }
/*    */   
/*    */   private String getAliases() {
/* 59 */     return (this.aliases == null) ? "" : (" as " + this.aliases);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean equals(Object paramObject) {
/* 64 */     if (this == paramObject) return true; 
/* 65 */     if (paramObject == null || getClass() != paramObject.getClass()) return false; 
/* 66 */     SelectCondition selectCondition = (SelectCondition)paramObject;
/* 67 */     return (this.isCoalesce == selectCondition.isCoalesce && 
/* 68 */       Objects.equals(this.tablePrefix, selectCondition.tablePrefix) && 
/* 69 */       Objects.equals(this.column, selectCondition.column) && 
/* 70 */       Objects.equals(this.aliases, selectCondition.aliases) && 
/* 71 */       Objects.equals(this.defaultValue, selectCondition.defaultValue));
/*    */   }
/*    */ 
/*    */   
/*    */   public int hashCode() {
/* 76 */     return Objects.hash(new Object[] { this.tablePrefix, this.column, this.aliases, Boolean.valueOf(this.isCoalesce), this.defaultValue });
/*    */   }
/*    */ 
/*    */   
/*    */   public String toString() {
/* 81 */     return "SelectCondition{tablePrefix='" + this.tablePrefix + '\'' + ", column='" + this.column + '\'' + ", aliases='" + this.aliases + '\'' + ", isCoalesce=" + this.isCoalesce + ", defaultValue=" + this.defaultValue + '}';
/*    */   }
/*    */ }


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\conditions\SelectCondition.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */