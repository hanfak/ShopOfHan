package infrastructure.web.productavailabilityById;

import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


public class ProductAvailabilityByIdUnmarshaller implements Unmarshaller<ProductAvailabilityByIdRequest> {

    @Override
    public ProductAvailabilityByIdRequest unmarshall(HttpServletRequest request) throws IOException {
        String contextPath = request.getPathInfo().substring(1);

        return ProductAvailabilityByIdRequest.productAvailabilityRequest(extractName(contextPath));
    }

    private String extractName(String contextPath) {
        return contextPath.split("/")[0];
    }
}
