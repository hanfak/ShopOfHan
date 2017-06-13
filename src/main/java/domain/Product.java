package domain;

import domain.crosscutting.ValueType;

public class Product extends ValueType{
    private final String productID;
    public final String productName;
    private final String description;

    private Product(String productID, String productName, String description) {
        this.productID = productID;
        this.productName = productName;
        this.description = description;
    }

    public static Product product(String productID, String productName, String description){
        return new Product(productID, productName, description);
    }
}
