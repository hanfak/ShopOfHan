package domain;

import domain.product.ProductId;
import domain.product.ProductName;

import java.util.List;

public class ProductStockList {
    public final ProductName productName;
    public final ProductId productId;
    public final List<Stock> stock;

    private ProductStockList(ProductName productName, ProductId productId, List<Stock> stock) {
        this.productName = productName;
        this.productId = productId;
        this.stock = stock;
    }

    public static ProductStockList productStockList(ProductName productName, ProductId productId, List<Stock> stock){
        return new ProductStockList(productName, productId, stock);
    }
}
