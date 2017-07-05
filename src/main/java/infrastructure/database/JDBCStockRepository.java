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

    private JDBCDatabaseConnectionManager databaseConnectionManager;

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

                 ResultSet rs = stmt.executeQuery();
                 if (!rs.next()) {
                     // TODO add a logger
                     return Optional.empty();
                 }
                 // TODO multiple different stock checks will return first one only (new user story)
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
    public Optional<ProductStock> checkStockById(ProductId productId) {
        try {
            try (Connection dbConnection = databaseConnectionManager.getDBConnection();
                 PreparedStatement stmt = dbConnection.prepareStatement(SQL_STATEMENT_TWO)) {

                stmt.setString(1, productId.value);
                ResultSet rs = stmt.executeQuery();
                if (!rs.next()) {
                    // TODO add a logger
                    return Optional.empty();
                }
                if (rs.next()) {
                    String product_name = rs.getString("product_name");
                    int amount = rs.getInt("amount");

                    return Optional.of(productStock(
                            ProductName.productName(product_name),
                            amount));
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
