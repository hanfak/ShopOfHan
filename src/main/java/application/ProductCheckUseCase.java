package application;

import domain.ProductStock;
import domain.crosscutting.ProductToCheck;
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

    // TODO REturn rendered content???

    // TODO How to use interface instead of implementation? Do I need?

    public ProductStock checkStock(ProductToCheck productToCheck) {
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = stockRepository.checkStock(productToCheck.getProductName());
        logger.info("Stock checked");
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
