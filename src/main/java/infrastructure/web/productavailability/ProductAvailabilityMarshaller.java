package infrastructure.web.productavailability;

import domain.ProductStock;
import java.io.IOException;

import static java.lang.String.format;

public class ProductAvailabilityMarshaller {

    String marshall(ProductStock productStock) throws IOException {
        // toJson in here or in ProductStock??
        return toJson(productStock);
    }

    private String toJson(ProductStock productStock) {
        return format("{\"productName\": \"%s\"," +
                        "\"amountInStock\": \"%s\"}",
                        productStock.productName, productStock.amountInStock);
    }
}
