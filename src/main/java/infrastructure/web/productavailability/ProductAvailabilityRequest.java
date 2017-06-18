package infrastructure.web.productavailability;

import domain.crosscutting.Request;
import domain.crosscutting.ValueType;

public class ProductAvailabilityRequest extends ValueType implements Request {
    public final String productName;

    private ProductAvailabilityRequest(String productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityRequest(productName);
    }
}
