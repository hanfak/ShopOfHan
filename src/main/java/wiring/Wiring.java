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

// TODO find way of not making all methods public
// TODO make singleton
@SuppressWarnings("UseUtilityClass")
public class Wiring {

    // TODO singleton pattern
    public Settings settings() {
        return new Settings(new PropertiesReader("work"));
    }

    // TODO Extract to separate wiring for database
    public JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings(), logger(MySqlJDBCDatabaseConnectionManager.class));
    }

    // TODO singleton pattern
    public Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    public Unmarshaller productAvailabilityByNameUnmarshaller() {
        return new ProductAvailabilityByNameUnmarshaller();
    }

    public Unmarshaller productAvailabilityByIdUnmarshaller() {
        return new ProductAvailabilityByIdUnmarshaller();
    }

    public Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    public Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    public StockRepository stockRepository() {
        return new JDBCStockRepository(logger(JDBCStockRepository.class), databaseConnectionManager());
    }

    public DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    public StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }

    public ProductCheckByNameUseCase productCheckByNameUseCase() {
        return new ProductCheckByNameUseCase(stockRepository(), logger(ProductCheckByNameUseCase.class));
    }

    public ProductCheckByIdUseCase productCheckByIdUseCase() {
        return new ProductCheckByIdUseCase(stockRepository(), logger(ProductCheckByIdUseCase.class));
    }

    public ProductAvailabilityByNameServlet productAvailabilityByNameServlet() {
        return new ProductAvailabilityByNameServlet(productAvailabilityByNameUnmarshaller(), productAvailabilityByNameWebService());
    }

    public ProductAvailabilityByNameWebService productAvailabilityByNameWebService() {
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityMarshaller());
    }

    public ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    public ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }

    public WebServerBuilder webserverBuilder(Settings settings) {
        return new JettyWebserverBuilder(settings);
    }
}
