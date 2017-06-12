package domain;

import domain.crosscutting.ValueType;

// Extend valuetype for equals, hashcode etc
public class ProductStock extends ValueType {
    // Add ProductID, which is acquired from DB
    public final String productName;
    public final Integer amountInStock;
    // static factory method instead??
    private ProductStock(String productName, Integer amountInStock) {
        this.productName = productName;
        this.amountInStock = amountInStock;
    }

    public static ProductStock productStock(String productName, Integer amountInStock){
        return new ProductStock(productName, amountInStock);
    }
}
