package infrastructure.web.productavailability.productavailabilityname;

import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class ProductAvailabilityByNameUnmarshaller implements Unmarshaller<ProductAvailabilityByNameRequest> {

    @Override
    public ProductAvailabilityByNameRequest unmarshall(HttpServletRequest request) throws IOException {
        String contextPath = request.getPathInfo().substring(1);

        return ProductAvailabilityByNameRequest.productAvailabilityRequest(extractName(contextPath));
    }

    private String extractName(String contextPath) {
        return contextPath.split("/")[0];
    }
}
