package wiring;

import application.crosscutting.StockRepository;
import application.productavailability.ProductCheckByIdUseCase;
import application.productavailability.ProductCheckByNameUseCase;
import infrastructure.database.JDBCDatabaseConnectionManager;
import infrastructure.database.JDBCStockRepository;
import infrastructure.database.MySqlJDBCDatabaseConnectionManager;
import infrastructure.monitoring.DatabaseConnectionProbe;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import infrastructure.web.Marshaller;
import infrastructure.web.Unmarshaller;
import infrastructure.web.jetty.JettyWebserverBuilder;
import infrastructure.web.productavailability.ProductAvailabilityMarshaller;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdUnmarshaller;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdWebService;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameUnmarshaller;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameWebService;
import infrastructure.web.server.WebServerBuilder;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO make singleton
@SuppressWarnings("UseUtilityClass")
public class Wiring {

    // TODO singleton pattern
    // Change depending on machine
    public static Settings settings() {
        return new Settings(new PropertiesReader("work"));
    }

    // TODO singleton pattern
    // TODO Extract to separate wiring for database
    private static JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings());
    }
    // TODO singleton pattern
    private static Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    // TODO Need to set generric here
    private static Unmarshaller productAvailabilityByNameUnmarshaller() {
        return new ProductAvailabilityByNameUnmarshaller();
    }

    private static Unmarshaller productAvailabilityByIdUnmarshaller() {
        return new ProductAvailabilityByIdUnmarshaller();
    }

    private static Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private static Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private static StockRepository stockRepository() {
        return new JDBCStockRepository(databaseConnectionManager());
    }

    private static DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    public static StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }

    private static ProductCheckByNameUseCase productCheckByNameUseCase() {
        return new ProductCheckByNameUseCase(stockRepository(), logger(ProductCheckByNameUseCase.class));
    }

    private static ProductCheckByIdUseCase productCheckByIdUseCase() {
        return new ProductCheckByIdUseCase(stockRepository(), logger(ProductCheckByIdUseCase.class));
    }

    public static ProductAvailabilityByNameServlet productAvailabilityByNameServlet() {
        return new ProductAvailabilityByNameServlet(productAvailabilityByNameUnmarshaller(), productAvailabilityByNameWebService());
    }

    private static ProductAvailabilityByNameWebService productAvailabilityByNameWebService() {
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityMarshaller());
    }

    public static ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    private static ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }

    public static WebServerBuilder webserverBuilder(Settings settings) {
        return new JettyWebserverBuilder(settings) ;
    }
}
