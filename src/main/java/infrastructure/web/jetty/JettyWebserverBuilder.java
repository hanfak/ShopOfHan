package infrastructure.web.jetty;

import infrastructure.properties.Settings;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import infrastructure.web.server.EndPoint;
import infrastructure.web.server.WebServerBuilder;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.http.HttpServlet;

public class JettyWebserverBuilder implements WebServerBuilder {
    private final Settings settings;
    private final ServletContextHandler servletHandler = new ServletContextHandler();

    public JettyWebserverBuilder(Settings settings) {
        this.settings = settings;
    }

    @Override
    public WebServerBuilder registerProductAvailabilityByNameEndPoint(EndPoint endPoint, ProductAvailabilityByNameServlet productAvailabilityByNameServlet) {
        addServlet(productAvailabilityByNameServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerProductAvailabilityByIdEndPoint(EndPoint endPoint, ProductAvailabilityByIdServlet productAvailabilityByIdServlet) {
        addServlet(productAvailabilityByIdServlet, endPoint);
        return this;
    }

    @Override
    public WebServerBuilder registerStatusProbeEndPoint(EndPoint endPoint, StatusProbeServlet statusProbeServlet) {
        addServlet(statusProbeServlet, endPoint);
        return this;
    }

    @Override
    public ShopOfHanServer build() {
        return new ShopOfHanServer(settings).withContext(servletHandler);
    }

    private void addServlet(HttpServlet httpServlet, EndPoint endPoint) {
        servletHandler.addServlet(new ServletHolder(httpServlet), endPoint.path);
    }
}
