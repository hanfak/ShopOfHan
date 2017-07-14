package infrastructure.web.productavailabilityById;

import domain.ProductId;
import domain.ProductName;
import domain.crosscutting.ProductToCheck;
import domain.crosscutting.ValueType;

public class ProductAvailabilityByIdRequest extends ValueType implements ProductToCheck {
    private final ProductId productId;

    // TODO use only one field 'productQuery'??
    private ProductAvailabilityByIdRequest(ProductId productId) {
        this.productId = productId;
    }

    public static ProductAvailabilityByIdRequest productAvailabilityRequest(String productId) {
        return new ProductAvailabilityByIdRequest(ProductId.productId(productId));
    }

    @Override
    public ProductName getProductName() {
        return null;
    }

    @Override
    public ProductId getProductId() {
        return productId;
    }
}
