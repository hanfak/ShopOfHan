package infrastructure.web.productavailability.productavailabilityById;

import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;


public class ProductAvailabilityByIdUnmarshaller implements Unmarshaller<ProductAvailabilityByIdRequest> {
    // TODO try returning string of JSON in request body
    @Override
    public ProductAvailabilityByIdRequest unmarshall(HttpServletRequest request) {
        // validate request.getPathInfo() is size > 1
        String contextPath = request.getPathInfo().substring(1);

        return ProductAvailabilityByIdRequest.productAvailabilityRequest(extractName(contextPath));
    }

    private String extractName(String contextPath) {
        return contextPath.split("/")[0];
    }
}
