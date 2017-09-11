package domain;

import domain.crosscutting.ValueType;
import domain.product.ProductDescription;
import domain.product.ProductId;
import domain.product.ProductName;

import java.util.List;

public class ProductStockList extends ValueType {
    public final ProductName productName;
    public final ProductId productId;
    public final ProductDescription productDescription;
    public final List<Stock> stock;

    private ProductStockList(ProductName productName, ProductId productId, ProductDescription productDescription, List<Stock> stock) {
        this.productName = productName;
        this.productId = productId;
        this.productDescription = productDescription;
        this.stock = stock;
    }

    public static ProductStockList productStockList(ProductName productName, ProductId productId, ProductDescription productDescription, List<Stock> stock){
        return new ProductStockList(productName, productId, productDescription, stock);
    }
}
