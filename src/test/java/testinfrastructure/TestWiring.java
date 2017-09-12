package testinfrastructure;

import hanfak.shopofhan.application.crosscutting.ProductStockRepository;
import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.infrastructure.properties.PropertiesReader;
import hanfak.shopofhan.infrastructure.properties.Settings;
import hanfak.shopofhan.wiring.Wiring;


// interface for both hanfak.shopofhan.wiring and TestWiring
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

    @Override
    public ProductStockRepository productStockRepository() {
        return new TestProductStockRepository();
    }

}
