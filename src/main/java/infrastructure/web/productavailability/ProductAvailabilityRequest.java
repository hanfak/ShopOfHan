package infrastructure.web.productavailability;

import domain.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;

public class ProductAvailabilityRequest extends ValueType implements ProductToCheck {
    private final String productName;

    private ProductAvailabilityRequest(String productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityRequest(productName);
    }

    // TODO how to remove this getter?
    //  TODO test this
    @Override
    public String getProductName() {
        return productName;
    }
}
