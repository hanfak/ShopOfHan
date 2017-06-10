package wiring;

import infrastructure.web.productavailability.ProductAvailabilityServlet;
import infrastructure.web.productavailability.ProductAvailabilityUnmarshaller;
import infrastructure.web.productavailability.ProductAvailabilityMarshaller;

public class Wiring {

    private static ProductAvailabilityUnmarshaller productAvailabilityUnmarshaller() {
        return new ProductAvailabilityUnmarshaller();
    }

    private static ProductAvailabilityMarshaller productAvailabilityMarshaller() {
        return new ProductAvailabilityMarshaller();
    }

    public static ProductAvailabilityServlet productAvailabilityServlet() {
        return new ProductAvailabilityServlet(productAvailabilityUnmarshaller(), productAvailabilityMarshaller());
    }
}
