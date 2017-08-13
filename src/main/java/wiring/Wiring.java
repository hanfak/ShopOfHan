package wiring;

import application.crosscutting.StockRepository;
import application.productavailability.ProductCheckByIdUseCase;
import application.productavailability.ProductCheckByNameUseCase;
import infrastructure.database.JDBCDatabaseConnectionManager;
import infrastructure.database.JDBCStockRepository;
import infrastructure.database.connection.MySqlJDBCDatabaseConnectionManager;
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

// TODO find way of not making all methods public
// TODO make singleton
@SuppressWarnings("UseUtilityClass")
public class Wiring {

    // TODO singleton pattern
    public Settings settings() {
        return new Settings(new PropertiesReader("localhost"));
    }

    // TODO Extract to separate wiring for database
    private JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings(), logger(MySqlJDBCDatabaseConnectionManager.class));
//        return new PoolingJDBCDatabasConnectionManager(logger(PoolingJDBCDatabasConnectionManager.class));
    }

    // TODO singleton pattern
    public Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    private Unmarshaller productAvailabilityByNameUnmarshaller() {
        return new ProductAvailabilityByNameUnmarshaller();
    }

    private Unmarshaller productAvailabilityByIdUnmarshaller() {
        return new ProductAvailabilityByIdUnmarshaller(logger(ProductAvailabilityByIdUnmarshaller.class));
    }

    private Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    public StockRepository stockRepository() {
        return new JDBCStockRepository(logger(JDBCStockRepository.class), databaseConnectionManager());
    }

    private DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }

    private ProductCheckByNameUseCase productCheckByNameUseCase() {
        return new ProductCheckByNameUseCase(stockRepository(), logger(ProductCheckByNameUseCase.class));
    }

    private ProductCheckByIdUseCase productCheckByIdUseCase() {
        return new ProductCheckByIdUseCase(stockRepository(), logger(ProductCheckByIdUseCase.class));
    }

    ProductAvailabilityByNameServlet productAvailabilityByNameServlet() {
        return new ProductAvailabilityByNameServlet(productAvailabilityByNameUnmarshaller(), productAvailabilityByNameWebService());
    }

    private ProductAvailabilityByNameWebService productAvailabilityByNameWebService() {
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityMarshaller());
    }

    ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    private ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }

    WebServerBuilder webserverBuilder(Settings settings) {
        return new JettyWebserverBuilder(settings);
    }
}
