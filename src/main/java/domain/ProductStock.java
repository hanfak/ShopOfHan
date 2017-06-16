package domain;

import domain.crosscutting.ValueType;

public class ProductStock extends ValueType {
    public final String productName;
    public final Integer amountInStock;

    private ProductStock(String productName, Integer amountInStock) {
        this.productName = productName;
        this.amountInStock = amountInStock;
    }

    public static ProductStock productStock(String productName, Integer amountInStock){
        return new ProductStock(productName, amountInStock);
    }
}
