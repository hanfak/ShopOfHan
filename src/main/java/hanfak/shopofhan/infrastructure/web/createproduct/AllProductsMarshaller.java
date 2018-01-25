package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.Marshaller;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class AllProductsMarshaller implements Marshaller<List<Product>> {
    private static final String EXPECTED_BODY_FORMAT =
            "{[" + "%s" + "]}";

    private static final String EXPECTED_PRODUCT_FORMAT = "{\"productId\": \"%s\", " +
            "\"productName\": \"%s\", " +
            "\"productDescription\": \"%s\"" +
            "}";

    @Override
    public String marshall(List<Product> products) {
        return toJson(products);
    }

    private String toJson(List<Product> products) {
        return format(EXPECTED_BODY_FORMAT, productFill(products));
    }

    private String productFill(List<Product> products) {
        return products.stream()
                .map(product -> format(EXPECTED_PRODUCT_FORMAT, product.productId, product.productName, product.productDescription))
                .collect(Collectors.joining(","));
    }
}
