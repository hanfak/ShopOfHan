package infrastructure.web.handler;


import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import static wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY;

public class Handler {

    public static ServletContextHandler servletHandler() {
        ServletContextHandler servletHandler = new ServletContextHandler();
        servletHandler.addServlet(new ServletHolder(new ProductAvailabilityServlet()), PRODUCT_AVAILABILITY);
        return servletHandler;
    }
}
