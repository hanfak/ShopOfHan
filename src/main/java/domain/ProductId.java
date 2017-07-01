package domain;

import domain.crosscutting.SingleValueType;

public class ProductId extends SingleValueType<String> {
    public ProductId(String value) {
        super(value);
    }

    public static ProductId productId(String productId) {
        //TODO: validation
        return new ProductId(productId);
    }
}
