package infrastructure.web.productavailability;

import infrastructure.web.QueryUnmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static java.lang.String.format;
public class ProductAvailabilityUnmarshaller implements QueryUnmarshaller<ProductAvailabilityRequest> {

    @Override
    public ProductAvailabilityRequest unmarshall(HttpServletRequest request) throws IOException {
        String productName = request.getParameter("productName");
        System.out.println(format("body from request = %s\n", productName));

        return new ProductAvailabilityRequest(productName);
    }
}
