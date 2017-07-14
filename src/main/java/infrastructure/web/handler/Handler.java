package infrastructure.web.handler;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static wiring.ShopOfHanURLs.*;
import static wiring.Wiring.*;

@SuppressWarnings("UseUtilityClass")
public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        // TODO builder pattern to create servletHandler
        servletHandler.addServlet(new ServletHolder(productAvailabilityByNameServlet()), PRODUCT_AVAILABILITY_BY_NAME);
        servletHandler.addServlet(new ServletHolder(productAvailabilityByIdServlet()), PRODUCT_AVAILABILITY_BY_ID);
        servletHandler.addServlet(new ServletHolder(statusProbeServlet()), STATUS_PAGE);
        return servletHandler;
    }
}
