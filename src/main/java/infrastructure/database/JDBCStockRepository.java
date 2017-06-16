package infrastructure.database;

import domain.ProductStock;
import domain.crosscutting.StockRepository;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import static domain.ProductStock.productStock;
import static java.lang.String.format;

public class JDBCStockRepository implements StockRepository<ProductStock, ProductAvailabilityRequest> {

    private JDBCDatabaseConnectionManager databaseConnectionManager;

    public JDBCStockRepository(JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    @Override
    public Optional<ProductStock> checkStock(ProductAvailabilityRequest request) {
        try {
            Connection dbConnection = databaseConnectionManager.getDBConnection();
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery(request));
                if (rs.next()) {
                    return Optional.of(productStock(
                            rs.getString("product_name"),
                            rs.getInt("amount")));
                }
                // if (!rs.next) error
                // if (rs.next) error
            dbConnection.close();
        } catch(Exception e) {
            System.out.println("afse");
        }
        return Optional.empty();
    }

    private String sqlQuery(ProductAvailabilityRequest request) {
        return format("select * from stock where product_name='%s'", request.productName);
    }
}

// Example JdbcReader
