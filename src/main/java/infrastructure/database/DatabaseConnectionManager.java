package infrastructure.database;

import infrastructure.properties.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
//Implement interface for DBconnection manager, make more explicit
public class DatabaseConnectionManager {
    private static Settings settings;

    public DatabaseConnectionManager(Settings settings) {
        this.settings = settings;
    }

    public Connection getDBConnection() {
        try {
            Connection con = DriverManager.getConnection(
                    settings.databaseURL() + "shop_of_han_database",
                    settings.databaseUsername(),
                    settings.databasePassword());
            return con;
        } catch(Exception e) {
            System.out.println(e);
        }
        return null;
    }

    private void closeDBConnection(Connection con) throws SQLException {
        con.close();
    }

}
