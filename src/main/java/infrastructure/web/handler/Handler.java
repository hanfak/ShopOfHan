package infrastructure.web.handler;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY_BY_NAME;
import static wiring.ShopOfHanURLs.STATUS_PAGE;
import static wiring.Wiring.productAvailabilityServlet;
import static wiring.Wiring.statusProbeServlet;

@SuppressWarnings("UseUtilityClass")
public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        // TODO builder pattern to create servletHandler
        servletHandler.addServlet(new ServletHolder(productAvailabilityServlet()), PRODUCT_AVAILABILITY_BY_NAME);
        servletHandler.addServlet(new ServletHolder(statusProbeServlet()), STATUS_PAGE);
        return servletHandler;
    }
}
