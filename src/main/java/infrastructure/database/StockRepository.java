package infrastructure.database;

import domain.ProductStock;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static java.lang.String.format;


// implement interface in Domain.crosscutting
public class StockRepository {

    private DatabaseConnectionManager databaseConnectionManager;

    public StockRepository(DatabaseConnectionManager databaseConnectionManager) {
        this.databaseConnectionManager = databaseConnectionManager;
    }

    public ProductStock checkStock(ProductAvailabilityRequest request) {
        //Avoid using null
        ProductStock producatAvailability = null;
        try {
            Connection dbConnection = databaseConnectionManager.getDBConnection();
            Statement stmt = dbConnection.createStatement();
            ResultSet rs = stmt.executeQuery(sqlQuery(request));

            // more elegent (functional) way of doing this
            while(rs.next()) {
                producatAvailability = new ProductStock(
                        rs.getString(2),
                        rs.getInt(3));
            }

            dbConnection.close();
        } catch(Exception e) {
            System.out.println(e);
        }
        return producatAvailability;
    }


    private String sqlQuery(ProductAvailabilityRequest request) {
        return format("select * from stock where product_name='%s'", request.productName);
    }
}
