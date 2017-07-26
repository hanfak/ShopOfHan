package infrastructure.web.productavailability.productavailabilityname;

import application.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;
import domain.product.ProductId;
import domain.product.ProductName;

import static domain.product.ProductName.productName;

public class ProductAvailabilityByNameRequest extends ValueType implements ProductToCheck {
    private final ProductName productName;

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
