package infrastructure.database;

import domain.ProductStock;
import domain.crosscutting.StockRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static domain.ProductStock.productStock;

public class JDBCStockRepository implements StockRepository<ProductStock> {

    public static final String SQL_STATEMENT = "select * from stock where product_name=?";

    private JDBCDatabaseConnectionManager databaseConnectionManager;

    public JDBCStockRepository(JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    @Override
    public Optional<ProductStock> checkStock(String product) {
        try {
            Connection dbConnection = databaseConnectionManager.getDBConnection();
            PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT);
            stmt.setString(1, product);
            // TODO multiple different stock checks will return first one only (new user story)
            ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return Optional.of(productStock(
                            rs.getString("product_name"),
                            rs.getInt("amount")));
                }
                // TODO  if (!rs.next) error
                // TODO if (rs.next) error
            dbConnection.close();
        } catch(Exception e) {
            System.out.println("afse");
        }
        return Optional.empty();
    }
}

// Example JdbcReader
