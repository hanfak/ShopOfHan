package infrastructure.database;

import domain.ProductId;
import domain.ProductName;
import domain.ProductStock;
import domain.crosscutting.StockRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static domain.ProductStock.productStock;

public class JDBCStockRepository implements StockRepository {

    public static final String SQL_STATEMENT = "SELECT product_name, amount from stock where product_name=?";
    public static final String SQL_STATEMENT_TWO = "SELECT product_name, amount from stock where product_id=?";

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

                 stmt.setString(1, productName.value);

                 ResultSet resultSet = stmt.executeQuery();
                 if (!resultSet.next()) {
                     // TODO add a logger
                     return Optional.empty();
                 }
                 // TODO multiple different stock checks will return first one only (new user story)
                 if (resultSet.next()) {
                     return Optional.of(productStock(
                             ProductName.productName(resultSet.getString("product_name")),
                             resultSet.getInt("amount")));
                 }
                 // TODO  if (!resultSet.next) error
                 // TODO if (resultSet.next) error
                 resultSet.close();
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

                stmt.setString(1, productId.value);
                ResultSet resultSet = stmt.executeQuery();
                if (!resultSet.next()) {
                    // TODO add a logger
                    return Optional.empty();
                }
                if (resultSet.next()) {
                    String productName = resultSet.getString("product_name");
                    int amount = resultSet.getInt("amount");

                    return Optional.of(productStock(
                            ProductName.productName(productName),
                            amount));
                }
                resultSet.close();
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch(Exception e) { // TODO be more specific with exception
            System.out.println(e);
        }
        return Optional.empty();
    }
}
