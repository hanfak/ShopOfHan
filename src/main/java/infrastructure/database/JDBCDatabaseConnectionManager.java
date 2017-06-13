package infrastructure.database;

import java.sql.Connection;

public interface JDBCDatabaseConnectionManager {
    public Connection getDBConnection();
}
