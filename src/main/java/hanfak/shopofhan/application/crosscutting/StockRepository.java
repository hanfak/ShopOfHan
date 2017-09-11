package hanfak.shopofhan.application.crosscutting;

import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;

import java.util.Optional;

public interface StockRepository {
    Optional<ProductStock> checkStockByName(ProductName productName);

    Optional<ProductStock> checkStockById(ProductId productId);

    Optional<ProductStockList> findListOfProductStock(ProductId productId);
}
