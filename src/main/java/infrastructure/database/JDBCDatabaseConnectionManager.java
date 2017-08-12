package infrastructure.database;

import java.sql.Connection;
import java.util.Optional;

public interface JDBCDatabaseConnectionManager {
    Optional<Connection> getDBConnection();
}
