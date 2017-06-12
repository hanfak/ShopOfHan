package application;

import domain.ProductStock;
import infrastructure.database.StockRepository;
import infrastructure.web.productavailability.ProductAvailabilityRequest;
import org.slf4j.Logger;

import java.util.Optional;

public class ProductCheckUseCase {

    private StockRepository stockRepository;
    private Logger logger;

    public ProductCheckUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }
    //Should this be void??? Need to return different object for empty query??
    // What should I return
    // REturn rendered content

    // How to use interface instead of implementation
    public Optional<ProductStock> checkStock(ProductAvailabilityRequest request) {
        ProductStock productStock = stockRepository.checkStock(request);
        if (productStock == null) {
            return Optional.empty();
        }
        return Optional.of(productStock);

    }
}
