package infrastructure.database;

import infrastructure.properties.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySqlJDBCDatabaseConnectionManager implements JDBCDatabaseConnectionManager {
    public static final String DATABASE_NAME = "shop_of_han_database";
    private static Settings settings;

    public MySqlJDBCDatabaseConnectionManager(Settings settings) {
        this.settings = settings;
    }

    @Override
    public Connection getDBConnection() {
        try {
            Connection con = DriverManager.getConnection(
                    settings.databaseURL() + DATABASE_NAME + "?verifyServerCertificate=false&useSSL=true",
                    settings.databaseUsername(),
                    settings.databasePassword());
            return con;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }
}


/// Database pooling???