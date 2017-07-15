package domain.product;

import domain.crosscutting.SingleValueType;

public class ProductName extends SingleValueType<String> {

    private ProductName(String value) {
        super(value);
    }

    public static ProductName productName(String productName) {
        //TODO: validation
        return new ProductName(productName);
    }
}
