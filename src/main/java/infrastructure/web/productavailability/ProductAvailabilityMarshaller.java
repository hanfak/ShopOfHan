package infrastructure.web.productavailability;

import domain.ProductStock;
import infrastructure.web.Marshaller;

import java.io.IOException;

import static java.lang.String.format;

// implement Marshaller ??
public class ProductAvailabilityMarshaller implements Marshaller {

    private static final String EXPECTED_BODY_FORMAT = "{\"productName\": \"%s\"," +
                    "\"amountInStock\": \"%s\"}";

    // abstract the param
    public String marshall(ProductStock productStock) throws IOException {
        // toJson in here or in ProductStock??
        return toJson(productStock);
    }
// should this be part of marshaller method and thus public?
    private String toJson(ProductStock productStock) {
        return format(EXPECTED_BODY_FORMAT, productStock.product.productName, productStock.amountInStock);
    }

    // toJson as template distinct and Marhsaller taking STring jsonResult???
}
