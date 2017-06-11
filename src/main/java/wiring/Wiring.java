package wiring;

import application.ProductCheckUseCase;
import infrastructure.database.DatabaseConnectionManager;
import infrastructure.database.StockRepository;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import infrastructure.web.productavailability.ProductAvailabilityMarshaller;
import infrastructure.web.productavailability.ProductAvailabilityServlet;
import infrastructure.web.productavailability.ProductAvailabilityUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wiring {

    private static Settings settings() {
        return new Settings(new PropertiesReader("localhost"));
    }

    private static ProductAvailabilityUnmarshaller productAvailabilityUnmarshaller() {
        return new ProductAvailabilityUnmarshaller();
    }

    private static ProductAvailabilityMarshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }
    // Extract to separate wiring for database
    // use a singleton???
    private static DatabaseConnectionManager databaseConnectionManager() {
        return new DatabaseConnectionManager(settings());
    }

    private static StockRepository stockRepository(){
        return new StockRepository(databaseConnectionManager());
    }

    private static ProductCheckUseCase productCheckUseCase() {
        return new ProductCheckUseCase(stockRepository());
    }

    // Is this the correct way of passing in a class as a parameter
    private static Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    public static ProductAvailabilityServlet productAvailabilityServlet() {
        return new ProductAvailabilityServlet(productAvailabilityUnmarshaller(), productAvailabilityMarshaller(), productCheckUseCase(), logger(ProductAvailabilityServlet.class));
    }
}
