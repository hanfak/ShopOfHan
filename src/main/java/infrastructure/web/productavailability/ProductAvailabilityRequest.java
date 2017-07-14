package infrastructure.web.productavailability;

import domain.ProductName;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;

import static domain.ProductName.productName;

public class ProductAvailabilityRequest extends ValueType implements ProductToCheck {
    private final ProductName productName;

    // TODO use only one field 'productQuery'??
    private ProductAvailabilityRequest(ProductName productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityRequest(productName(productName));
    }

    @Override
    public ProductName getProductName() {
        return productName;
    }
}
