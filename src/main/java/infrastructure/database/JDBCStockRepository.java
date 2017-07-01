package infrastructure.database;

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

    private JDBCDatabaseConnectionManager databaseConnectionManager;

    public JDBCStockRepository(JDBCDatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // TODO M001B Extract duplication
    @Override
    public Optional<ProductStock> checkStockByName(String productName) {
        try {
             try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                  PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT)) {

                 stmt.setString(1, productName);
                 // TODO multiple different stock checks will return first one only (new user story)
                 ResultSet rs = stmt.executeQuery();
                 if (rs.next()) {
                     return Optional.of(productStock(
                             ProductName.productName(rs.getString("product_name")),
                             rs.getInt("amount")));
                 }
                 // TODO  if (!rs.next) error
                 // TODO if (rs.next) error
            } catch (Exception e) {
             System.out.println(e);
            }

        } catch(Exception e) { // TODO be more specific with exception
            System.out.println(e);
        }
        return Optional.empty();
    }


    @Override
    public Optional<ProductStock> checkStockById(String productId) {
        try {
            try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                 PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_TWO)) {

                stmt.setString(1, productId);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    String product_name = rs.getString("product_name");
                    int amount = rs.getInt("amount");
                    Optional<ProductStock> productStock = Optional.of(productStock(
                            ProductName.productName(product_name),
                            amount));
                    return productStock;
                }
            } catch (Exception e) {
                System.out.println(e);
            }

        } catch(Exception e) { // TODO be more specific with exception
            System.out.println(e);
        }
        return Optional.empty();
    }
}
