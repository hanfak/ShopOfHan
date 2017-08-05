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

        /*
        * Can throw error, or bubble up the optional to the webservice which will the decide on the response, thus no
        * if statement here
        **/
        ProductStock productStock = checkStock.orElseThrow(this::illegalStateException);

        logger.info("Stock is there");

        return productStock;
    }

    private IllegalStateException illegalStateException() {
        logger.warn("Stock not there");
        return new IllegalStateException("Product is not found");
    }
}
