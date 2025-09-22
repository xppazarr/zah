package fr.maxlego08.zauctionhouse.zcore.utils.sarah.database;

import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.ColumnDefinition;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.JoinCondition;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.SelectCondition;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.conditions.WhereCondition;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface Schema {
  Schema uuid(String paramString);
  
  Schema uuid(String paramString, UUID paramUUID);
  
  Schema string(String paramString, int paramInt);
  
  Schema text(String paramString);
  
  Schema longText(String paramString);
  
  Schema decimal(String paramString);
  
  Schema decimal(String paramString, int paramInt1, int paramInt2);
  
  Schema string(String paramString1, String paramString2);
  
  Schema decimal(String paramString, Number paramNumber);
  
  Schema date(String paramString, Date paramDate);
  
  Schema bigInt(String paramString);
  
  Schema integer(String paramString);
  
  Schema bigInt(String paramString, long paramLong);
  
  Schema object(String paramString, Object paramObject);
  
  Schema bool(String paramString);
  
  Schema bool(String paramString, boolean paramBoolean);
  
  Schema json(String paramString);
  
  Schema blob(String paramString);
  
  Schema blob(String paramString, byte[] paramArrayOfbyte);
  
  Schema blob(String paramString, Object paramObject);
  
  Schema primary();
  
  Schema foreignKey(String paramString);
  
  Schema foreignKey(String paramString1, String paramString2, boolean paramBoolean);
  
  Schema createdAt();
  
  Schema updatedAt();
  
  Schema timestamps();
  
  Schema timestamp(String paramString);
  
  Schema autoIncrement(String paramString);
  
  Schema autoIncrementBigInt(String paramString);
  
  Schema nullable();
  
  Schema unique();
  
  Schema unique(boolean paramBoolean);
  
  Schema defaultValue(Object paramObject);
  
  Schema defaultCurrentTimestamp();
  
  Schema where(String paramString, Object paramObject);
  
  Schema where(String paramString, UUID paramUUID);
  
  Schema where(String paramString1, String paramString2, Object paramObject);
  
  Schema where(String paramString1, String paramString2, String paramString3, Object paramObject);
  
  Schema whereNotNull(String paramString);
  
  Schema whereNull(String paramString);
  
  Schema whereIn(String paramString, Object... paramVarArgs);
  
  Schema whereIn(String paramString, List<String> paramList);
  
  Schema whereIn(String paramString1, String paramString2, Object... paramVarArgs);
  
  Schema whereIn(String paramString1, String paramString2, List<String> paramList);
  
  Schema leftJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  Schema leftJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5, JoinCondition paramJoinCondition);
  
  Schema rightJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  Schema innerJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  Schema fullJoin(String paramString1, String paramString2, String paramString3, String paramString4, String paramString5);
  
  int execute(DatabaseConnection paramDatabaseConnection, Logger paramLogger) throws SQLException;
  
  List<Map<String, Object>> executeSelect(DatabaseConnection paramDatabaseConnection, Logger paramLogger) throws SQLException;
  
  long executeSelectCount(DatabaseConnection paramDatabaseConnection, Logger paramLogger) throws SQLException;
  
  <T> List<T> executeSelect(Class<T> paramClass, DatabaseConnection paramDatabaseConnection, Logger paramLogger) throws Exception;
  
  Migration getMigration();
  
  void setMigration(Migration paramMigration);
  
  String getTableName();
  
  void whereConditions(StringBuilder paramStringBuilder);
  
  void applyWhereConditions(PreparedStatement paramPreparedStatement, int paramInt) throws SQLException;
  
  List<ColumnDefinition> getColumns();
  
  List<String> getPrimaryKeys();
  
  List<String> getForeignKeys();
  
  List<JoinCondition> getJoinConditions();
  
  void orderBy(String paramString);
  
  void orderByDesc(String paramString);
  
  String getOrderBy();
  
  void distinct();
  
  boolean isDistinct();
  
  void addSelect(String paramString);
  
  void addSelect(String paramString1, String paramString2);
  
  void addSelect(String paramString1, String paramString2, String paramString3);
  
  void addSelect(String paramString1, String paramString2, String paramString3, Object paramObject);
  
  SchemaType getSchemaType();
  
  Schema addColumn(ColumnDefinition paramColumnDefinition);
  
  List<WhereCondition> getWhereConditions();
  
  List<SelectCondition> getSelectColumns();
  
  String getNewTableName();
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\database\Schema.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */