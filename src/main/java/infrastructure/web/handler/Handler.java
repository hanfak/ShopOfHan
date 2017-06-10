package infrastructure.web.handler;

import infrastructure.web.productavailability.ProductAvailabilityServlet;
import infrastructure.web.productavailability.ProductAvailabilityUnmarshaller;
import infrastructure.web.productavailability.ProductAvaliabilityMarshaller;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY;

public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        // Need to start wiring in dependencies in new class, get to big newing up
        servletHandler.addServlet(new ServletHolder(new ProductAvailabilityServlet(new ProductAvailabilityUnmarshaller(), new ProductAvaliabilityMarshaller())), PRODUCT_AVAILABILITY);
        return servletHandler;
    }
}
