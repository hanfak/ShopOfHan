package domain.crosscutting;

import domain.ProductStock;

import java.util.Optional;

public interface StockRepository {
        Optional<ProductStock> checkStockByName(String queryParameters);
        Optional<ProductStock> checkStockById(String queryParameters);
}
