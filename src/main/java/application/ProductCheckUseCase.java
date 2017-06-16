package application;

import domain.ProductStock;
import domain.crosscutting.StockRepository;
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
    // REturn rendered content???

    // How to use interface instead of implementation
    public ProductStock checkStock(ProductAvailabilityRequest request) {
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = stockRepository.checkStock(request);
        logger.info("Stock checked");
        if (checkStock.isPresent()) {
            logger.info("Stock is there");
            return checkStock.get();
        } else {
            logger.info("Stock not there");
            throw new IllegalStateException("Product is not found");
        }
    }
}
