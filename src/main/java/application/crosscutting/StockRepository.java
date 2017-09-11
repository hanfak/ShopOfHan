package application.crosscutting;

import domain.ProductStock;
import domain.ProductStockList;
import domain.product.ProductId;
import domain.product.ProductName;

import java.util.Optional;

public interface StockRepository {
    Optional<ProductStock> checkStockByName(ProductName productName);

    Optional<ProductStock> checkStockById(ProductId productId);

    Optional<ProductStockList> findListOfProductStock(ProductId productId);
}
