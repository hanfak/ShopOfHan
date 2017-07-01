package infrastructure.web.productavailability;

import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static infrastructure.web.productavailability.ProductAvailabilityRequest.productAvailabilityRequest;


public class ProductAvailabilityUnmarshaller implements Unmarshaller<ProductAvailabilityRequest> {

    @Override
    public ProductAvailabilityRequest unmarshall(HttpServletRequest request) throws IOException {
        String productName = request.getParameter("productName");
        String productId = request.getParameter("productId");
        // TODO M001B avoid using null
        if (productId == null) {
            return productAvailabilityRequest(productName, "");
        }
        return productAvailabilityRequest("", productId);
    }
}
