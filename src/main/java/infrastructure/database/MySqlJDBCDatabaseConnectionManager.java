package infrastructure.database;

import infrastructure.properties.Settings;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;

public class MySqlJDBCDatabaseConnectionManager implements JDBCDatabaseConnectionManager {

    private static final String DATABASE_NAME = "shop_of_han_database";
    // TODO properties for timeout
    private static final String DATABASE_FLAGS = "?connectTimeout=3000&verifyServerCertificate=false&useSSL=true";

    private final Settings settings;
    private final Logger logger;

    public MySqlJDBCDatabaseConnectionManager(Settings settings, Logger logger) {
        this.settings = settings;
        this.logger = logger;
    }

    @Override
    public Connection getDBConnection() {
        try {
            Connection connection = DriverManager.getConnection(
                    settings.databaseURL() + DATABASE_NAME + DATABASE_FLAGS,
                    settings.databaseUsername(),
                    settings.databasePassword());
            logger.info("db url " + settings.databaseURL());
            return connection;
        } catch(Exception e) {
            logger.info("db url " + settings.databaseURL());
            logger.error("exception in connection " + e);
            logger.error(e.getMessage());
        }
        return null;
    }
}


/// Database pooling???