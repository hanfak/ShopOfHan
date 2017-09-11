package domain.product;

import domain.crosscutting.SingleValueType;

public class ProductDescription extends SingleValueType<String> {
    private ProductDescription(String value) {
        super(value);
    }

    public static ProductDescription productDescription(String productDescription) {
            return new ProductDescription(productDescription);
    }
}
