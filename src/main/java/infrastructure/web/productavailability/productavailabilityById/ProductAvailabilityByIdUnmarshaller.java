package infrastructure.web.productavailability.productavailabilityById;

import domain.product.ProductId;
import infrastructure.web.Unmarshaller;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;


public class ProductAvailabilityByIdUnmarshaller implements Unmarshaller<ProductId> {

    private final Logger logger;

    public ProductAvailabilityByIdUnmarshaller(Logger logger) {
        this.logger = logger;
    }

    // TODO try returning string of JSON in request body
    @Override
    public ProductId unmarshall(HttpServletRequest request) {
        // validate request.getPathInfo() is size > 1
        String contextPath = request.getPathInfo().substring(1);

        try {
            return ProductId.productId(contextPath);
        } catch (Exception e) {
            logger.error("Bad product id argument");
            throw e;
        }
    }

    private String extractName(String contextPath) {
        return contextPath.split("/")[0];
    }
}
