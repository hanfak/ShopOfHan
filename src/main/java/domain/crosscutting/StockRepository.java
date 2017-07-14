package domain.crosscutting;

import domain.ProductId;
import domain.ProductName;
import domain.ProductStock;

import java.util.Optional;

public interface StockRepository {
        Optional<ProductStock> checkStockByName(ProductName productName);
        Optional<ProductStock> checkStockById(ProductId productId);
}
