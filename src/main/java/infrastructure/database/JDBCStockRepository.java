package infrastructure.database;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.ProductStockList;
import domain.Stock;
import domain.product.ProductId;
import domain.product.ProductName;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static domain.ProductStock.productStock;
import static domain.ProductStockList.productStockList;
import static domain.Stock.stock;
import static domain.product.ProductId.productId;
import static domain.product.ProductName.productName;

// TODO Module test to test database is working
@SuppressWarnings("Duplicates")
public class JDBCStockRepository implements StockRepository {

    private static final String SQL_STATEMENT = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";
    private static final String SQL_STATEMENT_TWO = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private static final String SQL_STATEMENT_THREE = "SELECT product.product_name, product.product_id, stock.amount, stock.stock_id FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";
    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    // TODO extract a reader and inject, which handles the connection and making the call pass into reader.getProductStock the sql
    public JDBCStockRepository(Logger logger, JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.logger = logger;
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // TODO M001B Extract duplication

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        // TODO: tidy this up
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT)) {

                    Optional<ProductStock> resultSet = getProductStock(productName.value, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_TWO)) {

                    Optional<ProductStock> resultSet = getProductStock(productId.value, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }

    //TODO better method name
    @Override
    public Optional<ProductStockList> blah(ProductId productId) {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_THREE)) {

                    Optional<ProductStockList> resultSet = setProductStock(productId.value, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e); // TODO test
        }
        return Optional.empty();
    }

    private Optional<ProductStockList> setProductStock(String value, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, value);

        ResultSet resultSet = stmt.executeQuery();

        List<Stock> stock = new ArrayList<>();
        ProductName productName = null;
        ProductId productId = null;
        // TODO multiple different stock checks will return first one only (new user story)
        while (resultSet.next()) {
            logger.info("Gettting data from database and storing in java pojo"); // TODO test
            productName = productName(resultSet.getString("product_name"));
            productId = productId(resultSet.getString("product_id"));
            stock.add(stock(resultSet.getInt("amount"), resultSet.getString("stock_id")));
        }

//        if (productName == null || productId == null) {
//            return Optional.empty();
//        }
        resultSet.close();
        return Optional.of(productStockList(productName, productId, stock));
    }

    private Optional<ProductStock> getProductStock(String productIdentifier, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productIdentifier);

        ResultSet resultSet = stmt.executeQuery();

        // TODO multiple different stock checks will return first one only (new user story)
        if (resultSet.next()) {
            logger.info("Gettting data from database and storing in java pojo");
            return Optional.of(productStock(
                    productName(resultSet.getString("product_name")),
                    resultSet.getInt("amount")));
        }
        resultSet.close();
        return Optional.empty();
    }
}
