package infrastructure.database;

import java.sql.Connection;

public interface JDBCDatabaseConnectionManager {
    Connection getDBConnection();
}
