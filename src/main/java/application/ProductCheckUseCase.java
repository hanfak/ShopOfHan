package application;

import domain.ProductName;
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

    // TODO REturn rendered content???
    public ProductStock checkStock(ProductName productName) {
        logger.info("checking stock...");
        Optional<ProductStock> checkStock = stockRepository.checkStockByName(productName.value);
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
