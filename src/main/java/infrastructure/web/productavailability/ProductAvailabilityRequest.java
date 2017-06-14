package infrastructure.web.productavailability;

import domain.crosscutting.Request;
// -> put into domain.crosscutting
// -> implement value type
// -> No need to implement request???
public class ProductAvailabilityRequest implements Request {
    public final String productName;

    public ProductAvailabilityRequest(String productName) {
        this.productName = productName;
    }
}
