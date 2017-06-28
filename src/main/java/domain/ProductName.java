package domain;

import domain.crosscutting.SingleValueType;

public class ProductName extends SingleValueType<String> {

    public ProductName(String value) {
        super(value);
    }

    public static ProductName productName(String productName) {
        //TODO: validation
        return new ProductName(productName);
    }
}
