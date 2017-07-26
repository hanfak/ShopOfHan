package infrastructure.database;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.product.ProductId;
import domain.product.ProductName;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

import static domain.ProductStock.productStock;

@SuppressWarnings("Duplicates")
public class JDBCStockRepository implements StockRepository {

    private static final String SQL_STATEMENT = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?";
    private static final String SQL_STATEMENT_TWO = "SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?";

    private final JDBCDatabaseConnectionManager databaseConnectionManager;

    // TODO extract a reader and inject, which handles the connection and making the call pass into reader.getProductStock the sql
    public JDBCStockRepository(JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // TODO M001B Extract duplication

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        try {
             try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                  PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT)) {

                 Optional<ProductStock> resultSet = getProductStock(productName.value, stmt);
                 if (resultSet.isPresent()) return resultSet;
             }

        } catch(Exception e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        try {
            try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                 PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_TWO)) {

                Optional<ProductStock> resultSet = getProductStock(productId.value, stmt);
                if (resultSet.isPresent()) return resultSet;
            }

        } catch(Exception e) {
            System.out.println(e);
        }
        return Optional.empty();
    }

    private Optional<ProductStock> getProductStock(String productIdentifier, PreparedStatement stmt) throws SQLException {
        stmt.setString(1, productIdentifier);

        ResultSet resultSet = stmt.executeQuery();

        // TODO multiple different stock checks will return first one only (new user story)
        if (resultSet.next()) {
            return Optional.of(productStock(
                    ProductName.productName(resultSet.getString("product_name")),
                    resultSet.getInt("amount")));
        }

        resultSet.close();
        return Optional.empty();
    }
}
