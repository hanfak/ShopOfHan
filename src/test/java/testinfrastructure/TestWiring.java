package testinfrastructure;

import application.crosscutting.StockRepository;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import wiring.Wiring;


// interface for both wiring and TestWiring
public class TestWiring extends Wiring {
    // TODO singleton pattern

    @Override
    public Settings settings() {
        return new Settings(new PropertiesReader("test"));
    }

    @Override
    public StockRepository stockRepository() {
        return new TestStockRepository();
    }

}
