package application;

import domain.ProductStock;
import infrastructure.database.StockRepository;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

public class ProductCheckUseCase {

    private StockRepository stockRepository;

    public ProductCheckUseCase(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }
    //Should this be void??? Need to return different object for empty query??
    // What should I return
    // REturn rendered content

    // How to use interface instead of implementation
    public ProductStock checkStock(ProductAvailabilityRequest request) {
        return stockRepository.checkStock(request);
    }
}
