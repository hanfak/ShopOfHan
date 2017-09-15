package hanfak.shopofhan.infrastructure.database.jdbc;

import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.ProductStock.productStock;
import static hanfak.shopofhan.domain.product.ProductName.productName;

// TODO Module test to test database is working
@SuppressWarnings("Duplicates")
public class JDBCStockRepository implements StockRepository {

    private static final String SQL_STATEMENT = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";
    private static final String SQL_STATEMENT_TWO = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private static final String SQL_STATEMENT_THREE = "SELECT product.product_name, product.product_id, product.product_description, stock.amount, stock.stock_id, stock.stock_description FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    // TODO extract a reader and inject, which handles the connection and making the call pass into reader.readProductStock the sql
    public JDBCStockRepository(Logger logger, JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.logger = logger;
        this.databaseConnectionManager = databaseConnectionManager;
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return executeSql(productName.value, SQL_STATEMENT);
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return executeSql(productId.value, SQL_STATEMENT_TWO);
    }

    private Optional<ProductStock> executeSql(String queryParameter, String sqlStatement) {
        // TODO tidy up
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement)) {

                    Optional<ProductStock> resultSet = readProductStock(queryParameter, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }


    private Optional<ProductStock> readProductStock(String productIdentifier, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productIdentifier);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            logger.info("Getting data from database and storing in java pojo");
            return Optional.of(productStock(
                    productName(resultSet.getString("product_name")),
                    resultSet.getInt("amount")));
        }
        resultSet.close();
        return Optional.empty();
    }
}