package infrastructure.web.productavailability.productavailabilityname;

import domain.ProductId;
import domain.ProductName;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;

import static domain.ProductName.productName;

public class ProductAvailabilityByNameRequest extends ValueType implements ProductToCheck {
    private final ProductName productName;

    // TODO use only one field 'productQuery'??
    private ProductAvailabilityByNameRequest(ProductName productName) {
        this.productName = productName;
    }

    public static ProductAvailabilityByNameRequest productAvailabilityRequest(String productName) {
        return new ProductAvailabilityByNameRequest(productName(productName));
    }

    @Override
    public ProductName getProductName() {
        return productName;
    }

    @Override
    public ProductId getProductId() {
        return null;
    }
}
