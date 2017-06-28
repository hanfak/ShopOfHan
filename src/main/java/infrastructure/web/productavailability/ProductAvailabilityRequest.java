package infrastructure.web.productavailability;

import domain.ProductName;
import domain.crosscutting.ValueType;

public class ProductAvailabilityRequest extends ValueType {
    public final ProductName productName;

    private ProductAvailabilityRequest(ProductName productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityRequest(ProductName.productName(productName));
    }

//    //  TODO test this
//    @Override
//    public String getProductName() {
//        return productName;
//    }
}
