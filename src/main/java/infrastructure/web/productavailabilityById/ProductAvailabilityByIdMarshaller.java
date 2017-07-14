package infrastructure.web.productavailabilityById;

import domain.ProductStock;
import infrastructure.web.Marshaller;

import java.io.IOException;

import static java.lang.String.format;

public class ProductAvailabilityByIdMarshaller implements Marshaller<ProductStock> {

    private static final String EXPECTED_BODY_FORMAT = "{\"productName\": \"%s\"," +
                    "\"amountInStock\": \"%s\"}";

    @Override
    public String marshall(ProductStock productStock) throws IOException {
        return toJson(productStock);
    }
    private String toJson(ProductStock productStock) {
        return format(EXPECTED_BODY_FORMAT, productStock.productName, productStock.amountInStock);
    }

    // TODO toJson as template distinct and Marhsaller taking STring jsonResult???
}
