package testinfrastructure;

import application.crosscutting.StockRepository;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wiring.Wiring;


// interface for both wiring and TestWiring
public class TestWiring extends Wiring {
    // TODO singleton pattern

    @Override
    public  Settings settings() {
        return new Settings(new PropertiesReader("work"));
    }


    // TODO singleton pattern
    public  Logger logger(Class<?> cls) {
        return LoggerFactory.getLogger(cls);
    }


    @Override
    public  StockRepository stockRepository() {
        return new TestStockRepository();
    }

}
