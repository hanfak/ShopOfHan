package application;

import domain.ProductStock;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.StockRepository;
import org.slf4j.Logger;

import java.util.Optional;

public class ProductCheckByNameUseCase {

    private final StockRepository stockRepository;
    private final Logger logger;

    public ProductCheckByNameUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }

    // TODO REturn rendered content???
    public ProductStock checkStock(ProductToCheck productToCheck) {

        // TODO M001B throw illegal argument here if both getters are ""
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = Optional.empty();
        // TODO M001B use optional instead of ""
            logger.info("checking stock by Name...");
            checkStock = stockRepository.checkStockByName(productToCheck.getProductName());
        logger.info("Stock checked");
        return respondWithProduct(checkStock);
    }

    private ProductStock respondWithProduct(Optional<ProductStock> checkStock) {
        if (checkStock.isPresent()) {
            logger.info("Stock is there");
            return checkStock.get();
        } else {
            /*
            * Can throw error, or bubble up the optional to the webservice which will the decide on the response, thus no
            * if statement here
            * */
            logger.warn("Stock not there");
            throw new IllegalStateException("Product is not found");
        }
    }
}
