package infrastructure.database;

import domain.ProductStock;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Optional;

import static domain.Product.product;
import static domain.ProductStock.productStock;
import static java.lang.String.format;

// implement interface in Domain.crosscutting
// -> used for test db, this class will be extended by test db
// -> interface in domain.crosscutting and used in usecase (JDBC implements stock)
public class StockRepository {

    private MySqlJDBCDatabaseConnectionManager mySqlDatabaseConnectionManager;

    public StockRepository(MySqlJDBCDatabaseConnectionManager mySqlDatabaseConnectionManager) {
        this.mySqlDatabaseConnectionManager = mySqlDatabaseConnectionManager;
    }

    public Optional<ProductStock> checkStock(ProductAvailabilityRequest request) {
        try {
            Connection dbConnection = mySqlDatabaseConnectionManager.getDBConnection();
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery(request));
                if (rs.next()) {
                    return Optional.of(productStock( //store productID instead
                            product("", rs.getString("product_name"), ""),
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
