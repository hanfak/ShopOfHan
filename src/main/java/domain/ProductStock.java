package domain;

import domain.crosscutting.ValueType;

public class ProductStock extends ValueType {
    public final Product product;
    public final Integer amountInStock;

    private ProductStock( Product product, Integer amountInStock) {
        this.product = product;
        this.amountInStock = amountInStock;
    }

    public static ProductStock productStock(Product product, Integer amountInStock){
        return new ProductStock(product, amountInStock);
    }
}
