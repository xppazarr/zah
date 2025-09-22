package fr.maxlego08.zauctionhouse.zcore.utils.sarah.database;

import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConfiguration;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.DatabaseConnection;
import fr.maxlego08.zauctionhouse.zcore.utils.sarah.logger.Logger;

public interface Executor {
  int execute(DatabaseConnection paramDatabaseConnection, DatabaseConfiguration paramDatabaseConfiguration, Logger paramLogger);
}


/* Location:              D:\ark\zAuctionHouse-3.2.3.7.jar!\fr\maxlego08\zauctionhouse\zcor\\utils\sarah\database\Executor.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */