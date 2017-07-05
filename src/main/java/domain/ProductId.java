package domain;

import domain.crosscutting.SingleValueType;

public class ProductId extends SingleValueType<String> {
    public ProductId(String value) {
        super(value);
    }

    public static ProductId productId(String productId) {
        if (validate(productId))
            return new ProductId(productId);
        else
            throw new IllegalArgumentException();
    }

    private static boolean validate(String productId) {
        return productId.length() < 10;
    }
}
