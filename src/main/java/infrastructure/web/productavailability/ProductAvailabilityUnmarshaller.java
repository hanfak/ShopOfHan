package infrastructure.web.productavailability;

import infrastructure.web.QueryUnmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class ProductAvailabilityUnmarshaller implements QueryUnmarshaller<ProductAvailabilityRequest> {

    @Override
    public ProductAvailabilityRequest unmarshall(HttpServletRequest request) throws IOException {
        String productName = request.getParameter("productName");

        return new ProductAvailabilityRequest(productName);
    }
}
