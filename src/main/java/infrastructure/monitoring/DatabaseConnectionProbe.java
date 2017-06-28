package infrastructure.monitoring;



import domain.monitoring.ProbeResult;
import domain.monitoring.StatusProbe;
import infrastructure.database.JDBCDatabaseConnectionManager;
import infrastructure.properties.Settings;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static java.lang.String.format;

public class DatabaseConnectionProbe implements StatusProbe {

    private final Logger logger;
    private final Settings settings;
    private final JDBCDatabaseConnectionManager connectionProvider;

    public DatabaseConnectionProbe(Logger logger, Settings settings, JDBCDatabaseConnectionManager connectionProvider) {
        this.logger = logger;
        this.settings = settings;
        this.connectionProvider = connectionProvider;
    }

    @Override
    public ProbeResult probe() {
        try (Connection connection = connectionProvider.getDBConnection();
             PreparedStatement statement = connection.prepareStatement("SELECT 1 FROM DUAL");
             ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next() && resultSet.getInt(1) == 1) {
                return ProbeResult.success(name(), "Database test query 'SELECT 1 FROM DUAL' was successful");
            } else {
                return ProbeResult.failure(name(), "Database test query 'SELECT 1 FROM DUAL' was not successful. Result was not 1.");
            }
        } catch (SQLException e) {
            String message = format("SQLException: %s", e.getMessage());
            logger.error(message);
            return ProbeResult.failure(name(), message);
        }
    }

    @Override
    public String name() {
        return String.format("Database Connection to '%s'", settings.databaseURL());
    }
}
