package infrastructure.web.productavailability;

import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class ProductAvailabilityUnmarshaller implements Unmarshaller<ProductAvailabilityRequest> {

    @Override
    public ProductAvailabilityRequest unmarshall(HttpServletRequest request) throws IOException {
        String productName = request.getParameter("productName");

        return new ProductAvailabilityRequest(productName);
    }
}
