package infrastructure.database;

import domain.ProductId;
import domain.ProductName;
import domain.ProductStock;
import domain.crosscutting.StockRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static domain.ProductStock.productStock;

public class JDBCStockRepository implements StockRepository {

    public static final String SQL_STATEMENT = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";
    public static final String SQL_STATEMENT_TWO = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";

    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    public JDBCStockRepository(JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // TODO M001B Extract duplication
    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        try {
             try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                  PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT)) {

                 Optional<ProductStock> resultSet = getProductStockByName(productName, stmt);
                 if (resultSet.isPresent()) return resultSet;
             }

        } catch(Exception e) { // TODO be more specific with exception
            System.out.println(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        try {
            try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                 PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_TWO)) {

                Optional<ProductStock> resultSet = getProductStockById(productId, stmt);
                if (resultSet.isPresent()) return resultSet;
            }

        } catch(Exception e) { // TODO be more specific with exception
            System.out.println(e);
        }
        return Optional.empty();
    }

    // TODO Remove dupliat
    private Optional<ProductStock> getProductStockByName(ProductName productName, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productName.value);

        ResultSet resultSet = stmt.executeQuery();

        // TODO multiple different stock checks will return first one only (new user story)
        if (resultSet.next()) {
            return Optional.of(productStock(
                    ProductName.productName(resultSet.getString("product_name")),
                    resultSet.getInt("amount")));
        }
        // TODO  if (!resultSet.next) error
        // TODO if (resultSet.next) error
        resultSet.close();
        return Optional.empty();
    }

    private Optional<ProductStock> getProductStockById(ProductId productId, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productId.value);

        ResultSet resultSet = stmt.executeQuery();

        // TODO multiple different stock checks will return first one only (new user story)
        if (resultSet.next()) {
            return Optional.of(productStock(
                    ProductName.productName(resultSet.getString("product_name")),
                    resultSet.getInt("amount")));
        }
        // TODO  if (!resultSet.next) error
        // TODO if (resultSet.next) error
        resultSet.close();
        return Optional.empty();
    }
}
