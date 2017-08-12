package infrastructure.database.connection;


import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import infrastructure.properties.Settings;
import wiring.Wiring;

import javax.sql.DataSource;

public class HikariDatabaseConnectionPooling {
//    private final Settings settings;
    private static final String DATABASE_NAME = "shop_of_han_database";
    private static final Settings settings = Wiring.settings();
    private static DataSource datasource;

//    public HikariDatabaseConnectionPooling(Settings settings) {
//        this.settings = settings;
//    }

    static DataSource getDataSource() {
        // TODO: How to avoid new up settings here? How to new up this object? Does method need to be static?
//        Settings settings = new Settings(new PropertiesReader("localhost"));
        // TODO: Is using static for settings correct way
        if (datasource == null) {
            HikariConfig config = new HikariConfig();

            config.setJdbcUrl(settings.databaseURL() + DATABASE_NAME);
            config.setUsername(settings.databaseUsername());
            config.setPassword(settings.databasePassword());

            config.setMaximumPoolSize(10);
            config.setAutoCommit(false);
            config.addDataSourceProperty("cachePrepStmts", "true");
            config.addDataSourceProperty("prepStmtCacheSize", "250");
            config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
            config.addDataSourceProperty("verifyServerCertificate", "false");
            config.addDataSourceProperty("useSSL", "true");

            datasource = new HikariDataSource(config);
        }
        return datasource;
    }
}
