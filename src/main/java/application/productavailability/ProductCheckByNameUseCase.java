package application.productavailability;

import application.crosscutting.ProductToCheck;
import application.crosscutting.StockRepository;
import domain.ProductStock;
import org.slf4j.Logger;

import java.util.Optional;

@SuppressWarnings("Duplicates")
public class ProductCheckByNameUseCase {

    private final StockRepository stockRepository;
    private final Logger logger;

    public ProductCheckByNameUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }

    public ProductStock checkStock(ProductToCheck productToCheck) {
        logger.info("checking stock by Name...");
        Optional<ProductStock> checkStock = stockRepository.checkStockByName(productToCheck.getProductName());
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
