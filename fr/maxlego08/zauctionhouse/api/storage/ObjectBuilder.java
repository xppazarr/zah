package fr.maxlego08.zauctionhouse.api.storage;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface ObjectBuilder<T> {
  T build(ResultSet paramResultSet) throws SQLException;
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\api\storage\ObjectBuilder.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */