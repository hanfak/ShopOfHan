package infrastructure.web.handler;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import wiring.Wiring;

import static wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY;
import static wiring.ShopOfHanURLs.STATUS_PAGE;
import static wiring.Wiring.productAvailabilityServlet;

public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        // TODO builder pattern to create servletHandler
        servletHandler.addServlet(new ServletHolder(productAvailabilityServlet()), PRODUCT_AVAILABILITY);
        servletHandler.addServlet(new ServletHolder(Wiring.statusProbeServlet()), STATUS_PAGE);
        return servletHandler;
    }
}
