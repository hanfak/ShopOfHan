package infrastructure.web.handler;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY;
import static wiring.Wiring.productAvailabilityServlet;

public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        // TODO builder pattern to create servletHandler
        servletHandler.addServlet(new ServletHolder(productAvailabilityServlet()), PRODUCT_AVAILABILITY);
        return servletHandler;
    }
}
