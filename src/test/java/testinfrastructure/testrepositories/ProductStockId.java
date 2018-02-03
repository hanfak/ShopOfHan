package testinfrastructure.testrepositories;

import hanfak.shopofhan.domain.crosscutting.ValueType;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.StockId;

public class ProductStockId extends ValueType {
    public final StockId stockId;
    public final ProductId productId;

    public ProductStockId(StockId stockId, ProductId productId) {
        this.stockId = stockId;
        this.productId = productId;
    }

    public static ProductStockId productStockId(StockId stockId, ProductId productId) {
        return new ProductStockId(stockId, productId);
    }
}
