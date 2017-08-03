package testinfrastructure;

import application.crosscutting.StockRepository;
import application.productavailability.ProductCheckByIdUseCase;
import application.productavailability.ProductCheckByNameUseCase;
import infrastructure.database.JDBCDatabaseConnectionManager;
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
import wiring.Wiring;


// interface for both wiring and TestWiring
public class TestWiring extends Wiring {
    // TODO singleton pattern
    public  Settings settings() {
        return new Settings(new PropertiesReader("work"));
    }

    // TODO Extract to separate wiring for database
    private  JDBCDatabaseConnectionManager databaseConnectionManager() {
        return new MySqlJDBCDatabaseConnectionManager(settings());
    }
    // TODO singleton pattern
    private  Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }

    private  Unmarshaller productAvailabilityByNameUnmarshaller() {
        return new ProductAvailabilityByNameUnmarshaller();
    }

    private  Unmarshaller productAvailabilityByIdUnmarshaller() {
        return new ProductAvailabilityByIdUnmarshaller();
    }

    private  Marshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private  Marshaller productAvailabilityByIdMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    private  StockRepository stockRepository() {
        return new TestStockRepository();
    }

    private  DatabaseConnectionProbe databaseConnectionProbe() {
        return new DatabaseConnectionProbe(logger(DatabaseConnectionProbe.class), settings(), databaseConnectionManager());
    }

    public  StatusProbeServlet statusProbeServlet() {
        return new StatusProbeServlet(databaseConnectionProbe());
    }

    private  ProductCheckByNameUseCase productCheckByNameUseCase() {
        return new ProductCheckByNameUseCase(stockRepository(), logger(ProductCheckByNameUseCase.class));
    }

    private  ProductCheckByIdUseCase productCheckByIdUseCase() {
        return new ProductCheckByIdUseCase(stockRepository(), logger(ProductCheckByIdUseCase.class));
    }

    public  ProductAvailabilityByNameServlet productAvailabilityByNameServlet() {
        return new ProductAvailabilityByNameServlet(productAvailabilityByNameUnmarshaller(), productAvailabilityByNameWebService());
    }

    private  ProductAvailabilityByNameWebService productAvailabilityByNameWebService() {
        return new ProductAvailabilityByNameWebService(productCheckByNameUseCase(), productAvailabilityMarshaller());
    }

    public  ProductAvailabilityByIdServlet productAvailabilityByIdServlet() {
        return new ProductAvailabilityByIdServlet(productAvailabilityByIdUnmarshaller(), productAvailabilityByIdWebService());
    }

    private  ProductAvailabilityByIdWebService productAvailabilityByIdWebService() {
        return new ProductAvailabilityByIdWebService(productCheckByIdUseCase(), productAvailabilityByIdMarshaller());
    }

    public WebServerBuilder webserverBuilder(Settings settings) {
        return new JettyWebserverBuilder(settings);
    }
}
