package application;

import domain.ProductId;
import domain.ProductName;
import domain.ProductStock;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.StockRepository;
import infrastructure.web.productavailability.ProductAvailabilityRequest;
import org.slf4j.Logger;

import java.util.Optional;

import static domain.ProductId.productId;
import static domain.ProductName.productName;

public class ProductCheckUseCase {

    private StockRepository stockRepository;
    private Logger logger;

    public ProductCheckUseCase(StockRepository stockRepository, Logger logger) {
        this.stockRepository = stockRepository;
        this.logger = logger;
    }

    // TODO REturn rendered content???
    public ProductStock checkStock(ProductToCheck productToCheck) {
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = Optional.empty();
        // TODO M001B use optional instead of ""
        if (!productToCheck.getProductName().equals(productName(""))){
            // TODO M001B pass object not primitive
            logger.info("checking stock by Name...");
            checkStock = stockRepository.checkStockByName(productToCheck.getProductName().value);
        }
        if (!productToCheck.getProductId().equals(productId(""))){
            logger.info("checking stock by Id...");
            checkStock = stockRepository.checkStockById(productToCheck.getProductId().value);
        }
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
