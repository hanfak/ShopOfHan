package domain;

import domain.crosscutting.ValueType;
import domain.product.Product;
import domain.stock.Stock;

import java.util.List;

public class ProductStockList extends ValueType {
    // TODO extract Product object
    public final Product product;
    public final List<Stock> stock;

    private ProductStockList(Product product, List<Stock> stock) {
        this.product = product;
        this.stock = stock;
    }

    public static ProductStockList productStockList(Product product, List<Stock> stock) {
        return new ProductStockList(product, stock);
    }
}
