package wiring;

import application.ProductCheckByIdUseCase;
import application.ProductCheckByNameUseCase;
import domain.crosscutting.StockRepository;
import infrastructure.database.JDBCDatabaseConnectionManager;
import infrastructure.database.JDBCStockRepository;
import infrastructure.database.MySqlJDBCDatabaseConnectionManager;
import infrastructure.monitoring.DatabaseConnectionProbe;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import infrastructure.web.Marshaller;
import infrastructure.web.Unmarshaller;
import infrastructure.web.productavailabilityById.ProductAvailabilityByIdMarshaller;
import infrastructure.web.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailabilityById.ProductAvailabilityByIdUnmarshaller;
import infrastructure.web.productavailabilityById.ProductAvailabilityByIdWebService;
import infrastructure.web.productavailabilityname.ProductAvailabilityByNameMarshaller;
import infrastructure.web.productavailabilityname.ProductAvailabilityByNameServlet;
import infrastructure.web.productavailabilityname.ProductAvailabilityByNameUnmarshaller;
import infrastructure.web.productavailabilityname.ProductAvailabilityByNameWebService;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
// TODO make singleton
@SuppressWarnings("UseUtilityClass")
public class Wiring {

    // TODO singleton pattern
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

    private static Marshaller productAvailabilityByNameMarshaller() {
        return new ProductAvailabilityByNameMarshaller();
    }

    private static Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityByIdMarshaller();
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
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityByNameMarshaller());
    }

    public static ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    private static ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }
}
