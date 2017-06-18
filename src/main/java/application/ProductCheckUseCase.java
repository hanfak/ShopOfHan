package application;

import domain.ProductStock;
import domain.crosscutting.StockRepository;
import org.slf4j.Logger;

import java.util.Optional;

public class ProductCheckUseCase {

    private StockRepository stockRepository;
    private Logger logger;

    public ProductCheckUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }
    // TODO Should this be void??? Need to return different object for empty query??
    // What should I return
    // REturn rendered content???

    // TODO How to use interface instead of implementation? Do I need?
    // create new interface for usecase to access ProductAvailabilityRequest from infrastructure,
    // which also implements Request
    public ProductStock checkStock(String productToCheck) {
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = stockRepository.checkStock(productToCheck);
        logger.info("Stock checked");
        if (checkStock.isPresent()) {
            logger.info("Stock is there");
            return checkStock.get();
        } else {
            logger.warn("Stock not there");
            throw new IllegalStateException("Product is not found");
        }
    }
}
