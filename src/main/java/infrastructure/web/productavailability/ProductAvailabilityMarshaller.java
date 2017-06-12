package infrastructure.web.productavailability;

import domain.ProductStock;
import java.io.IOException;

import static java.lang.String.format;

public class ProductAvailabilityMarshaller {

    protected static final String EXPECTED_BODY_FORMAT = "{\"productName\": \"%s\"," +
                    "\"amountInStock\": \"%s\"}";

    String marshall(ProductStock productStock) throws IOException {
        // toJson in here or in ProductStock??
        return toJson(productStock);
    }

    private String toJson(ProductStock productStock) {
        return format(EXPECTED_BODY_FORMAT, productStock.productName, productStock.amountInStock);
    }
}
