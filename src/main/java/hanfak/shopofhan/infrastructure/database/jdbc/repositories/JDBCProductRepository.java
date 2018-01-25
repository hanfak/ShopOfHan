package hanfak.shopofhan.infrastructure.database.jdbc.repositories;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
import static java.lang.String.format;

// TODO Module test to test database is working
@SuppressWarnings("Duplicates")
public class JDBCProductRepository implements ProductRepository {

    private static final String SQL_STATEMENT = "SELECT product_name, product_id, product_description FROM product WHERE product_id=?";
    private static final String SQL_SELECT_ALL_PRODUCTS = "SELECT product_name, product_id, product_description FROM product";
    private static final String SQL_STATEMENT_1 = "INSERT INTO product (product_name, product_description, product_id) VALUES (?,?,?)";
    private static final String SQL_REMOVE_PRODUCT = "DELETE FROM product WHERE product_id = ?";
    private static final String SQL_REMOVE_ALL_PRODUCT = "DELETE FROM product";
    private static final String SQL_UPDATE_PRODUCT = "UPDATE product SET product_name = ?, product_description = ? WHERE product_id = ?";
    private final Logger logger;
    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    // TODO extract a reader and inject, which handles the connection and making the call pass into reader.readProductStock the sql
    public JDBCProductRepository(Logger logger, JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.logger = logger;
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // TODO only used in test, will be used in prod when checking for duplicates
    @Override
    public Optional<Product> checkProductById(ProductId productId) {
        return executeReadSql(productId.value, SQL_STATEMENT);
    }

    @Override
    public Optional<Product> addProduct(Product product) throws SQLException {
        return executeAddProduct(product, SQL_STATEMENT_1);
    }

    @Override
    public void removeProduct(ProductId productid) {
        executeRemoveProduct(productid, SQL_REMOVE_PRODUCT);
    }

    //TODO unit test log, verify order
    @Override
    public void updateProduct(Product product) {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_UPDATE_PRODUCT)) {
                    String productId = product.productId.value;
                    stmt.setString(1, product.productName.value);
                    stmt.setString(2, product.productDescription.value);
                    stmt.setString(3, productId);

                    logger.info(format("Updating product %s from database", productId));
                    stmt.executeUpdate();
//                    dbConnection.commit();
                    logger.info(format("Updated product %s from database", productId));
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
    }

    @Override
    public Optional<List<Product>> getAllProducts() {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_SELECT_ALL_PRODUCTS)) {

                    Optional<List<Product>> resultSet = readProduct(stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }

    // TODO Removed and passed to testJdbc only
    // FOr testing only
    @Override
    public void removeAllProducts() {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement stmt = dbConnection.prepareStatement(SQL_REMOVE_ALL_PRODUCT)) {


                    logger.info("Removing all product rom database");
                    stmt.execute();
                    logger.info("Removed all product from database");
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
    }

    private Optional<List<Product>> readProduct(PreparedStatement stmt) throws SQLException {

        ResultSet resultSet = stmt.executeQuery(); // TODO try catch and log error uinit test

        // tODO will be a while loop for more than one product
        if (resultSet.next()) {
            logger.info("Getting data from database");
            return Optional.of(Collections.singletonList(product(productDescription(resultSet.getString("product_description")),
                    productId(resultSet.getString("product_id")),
                    productName(resultSet.getString("product_name")))));
        }
        resultSet.close();
        return Optional.empty();
    }


    private void executeRemoveProduct(ProductId productid, String sqlRemoveProduct) {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get();
                     PreparedStatement stmt = dbConnection.prepareStatement(sqlRemoveProduct)) {

                    stmt.setString(1, productid.value);

                    logger.info(format("Removing product %s from database", productid.value));
                    stmt.execute();
                    logger.info(format("Removed product %s from database", productid.value));
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
    }

    private Optional<Product> executeAddProduct(Product queryParameter, String sqlStatement) throws SQLException {
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement)) {

                    stmt.setString(1, queryParameter.productName.value);
                    stmt.setString(2, queryParameter.productDescription.value);
                    stmt.setString(3, queryParameter.productId.value);
                    logger.info("Storing data from database");
                    stmt.execute();
                    logger.info("Stored data from database");
                    return Optional.of(queryParameter);
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }

    private Optional<Product> executeReadSql(String queryParameter, String sqlStatement) {
        // TODO tidy up
        try {
            Optional<Connection> connection = databaseConnectionManager.getDBConnection();
            if (connection.isPresent()) {
                try (Connection dbConnection = connection.get(); //TODO change to the above
                     PreparedStatement stmt = dbConnection.prepareStatement(sqlStatement)) {

                    Optional<Product> resultSet = readProductStock(queryParameter, stmt);
                    if (resultSet.isPresent()) return resultSet;
                }
            }
        } catch (Exception e) {
            logger.error("error " + e);
        }
        return Optional.empty();
    }


    private Optional<Product> readProductStock(String productIdentifier, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productIdentifier);

        ResultSet resultSet = stmt.executeQuery();

        if (resultSet.next()) {
            logger.info("Getting data from database");
            return Optional.of(product(productDescription(resultSet.getString("product_description")),
                    productId(resultSet.getString("product_id")),
                    productName(resultSet.getString("product_name"))));
        }
        resultSet.close();
        return Optional.empty();
    }
}
