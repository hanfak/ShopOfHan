package infrastructure.database;

import domain.ProductStock;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static domain.ProductStock.productStock;
import static java.lang.String.format;

// implement interface in Domain.crosscutting
// -> used for test db, this class will be extended by test db
// -> interface in domain.crosscutting and used in usecase (JDBC implements stock)
public class StockRepository {

    private DatabaseConnectionManager databaseConnectionManager;

    public StockRepository(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    // What should this return
    public ProductStock checkStock(ProductAvailabilityRequest request) {
        //Avoid using null, -> use optional
        ProductStock producatAvailability = null;
        try {
            Connection dbConnection = databaseConnectionManager.getDBConnection();
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery(request));
                while(rs.next()) {
                    return productStock(
                            rs.getString("product_name"),
                            rs.getInt("amount"));
                }
            dbConnection.close();
        } catch(Exception e) {
            System.out.println("afse");
        }
        return producatAvailability;
    }


    private String sqlQuery(ProductAvailabilityRequest request) {
        return format("select * from stock where product_name='%s'", request.productName);
    }
}
