package wiring;

import application.ProductCheckUseCase;
import domain.crosscutting.StockRepository;
import infrastructure.database.JDBCDatabaseConnectionManager;
import infrastructure.database.JDBCStockRepository;
import infrastructure.database.MySqlJDBCDatabaseConnectionManager;
import infrastructure.monitoring.DatabaseConnectionProbe;
import infrastructure.PropertiesReader;
import infrastructure.Settings;
import infrastructure.web.Marshaller;
import infrastructure.web.Unmarshaller;
import infrastructure.web.productavailability.ProductAvailabilityMarshaller;
import infrastructure.web.productavailability.ProductAvailabilityServlet;
import infrastructure.web.productavailability.ProductAvailabilityUnmarshaller;
import infrastructure.web.productavailability.ProductAvailabilityWebService;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Wiring {

    private static Settings settings() {
        return new Settings(new PropertiesReader("localhost"));
    }

    //Need to set generric here
    private static Unmarshaller productAvailabilityUnmarshaller() {
        return new ProductAvailabilityUnmarshaller();
    }

    private static Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }
    // TODO Extract to separate wiring for database
    // use a singleton???
    private static JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings());
    }

    private static StockRepository stockRepository() {
        return new JDBCStockRepository(databaseConnectionManager());
    }

    // new databaseconnectionprobe and pass to servlet
    private static DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    public static StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }


    private static ProductCheckUseCase productCheckUseCase() {
        return new ProductCheckUseCase(stockRepository(), logger(ProductCheckUseCase.class));
    }

    // TODO Is this the correct way of passing in a class as a parameter
    private static Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    public static ProductAvailabilityServlet productAvailabilityServlet() {
        return new ProductAvailabilityServlet(productAvailabilityUnmarshaller(), logger(ProductAvailabilityServlet.class), productAvailabilityWebService());
    }

    private static ProductAvailabilityWebService productAvailabilityWebService() {
        return new ProductAvailabilityWebService(productCheckUseCase(), productAvailabilityMarshaller(), logger(ProductAvailabilityWebService.class));
    }
}
