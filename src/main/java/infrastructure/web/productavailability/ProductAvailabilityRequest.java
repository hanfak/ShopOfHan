package infrastructure.web.productavailability;

import domain.crosscutting.Request;
import domain.crosscutting.ValueType;

public class ProductAvailabilityRequest extends ValueType implements Request {
    public final String productName;

    public ProductAvailabilityRequest(String productName) {
        this.productName = productName;
    }
}
