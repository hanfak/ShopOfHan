package testinfrastructure;

import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcRecordReaderFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.JdbcWriterFactory;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCStockRepository;
import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.Wiring;
import testinfrastructure.testrepositories.TestStockRepository;


// interface for both hanfak.shopofhan.wiring and TestWiring
public class TestWiring extends Wiring {
    public static final String ENVIRONMENT = "test";  // to use test db, comment methods
//    public static final String ENVIRONMENT = "localhost"; /// to use test stub, uncomment methods
    // TODO singleton pattern

    @Override
    public Settings settings() {
        return new Settings(new PropertiesReader(ENVIRONMENT));
    }

//    @Override
//    public StockRepository stockRepository() {
//        return new TestStubStockRepository();
//    }

    public TestStockRepository testStockRepository() {
        return new TestStockRepository(logger(JDBCStockRepository.class), new JdbcRecordReaderFactory(logger(JdbcRecordReaderFactory.class), databaseConnectionManager()), new JdbcWriterFactory(logger(JdbcWriterFactory.class), databaseConnectionManager()));
    }


    @Override
    public ProductStockRepository productStockRepository() {
        return null;
//        return new TestStubProductStockRepository();
    }

//    @Override
//    public ProductRepository productRepository() {
//        return null;
////        return new TestStubProductRepository();
//    }
}
