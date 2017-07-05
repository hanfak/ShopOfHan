package infrastructure.web.productavailability;

import domain.ProductId;
import domain.ProductName;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;

import static domain.ProductId.productId;
import static domain.ProductName.productName;

public class ProductAvailabilityRequest extends ValueType implements ProductToCheck {
    private final ProductName productName;
    private final ProductId productId;

    // TODO use only one field 'productQuery'??
    private ProductAvailabilityRequest(ProductName productName, ProductId productId) {
        this.productName = productName;
        this.productId = productId;
    }

    public static ProductAvailabilityRequest productAvailabilityRequest(String productName, String productId) {
        return new ProductAvailabilityRequest(productName(productName), productId(productId));
    }

    @Override
    public ProductName getProductName() {
        return productName;
    }

    @Override
    public ProductId getProductId() {
        return productId;
    }
}
