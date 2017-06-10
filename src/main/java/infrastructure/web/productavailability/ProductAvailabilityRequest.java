package infrastructure.web.productavailability;

import domain.crosscutting.Request;

public class ProductAvailabilityRequest implements Request {
    public final String productName;

    public ProductAvailabilityRequest(String productName) {
        this.productName = productName;
    }

    @Override
    public String toJson() {
        return null;
    }
}
