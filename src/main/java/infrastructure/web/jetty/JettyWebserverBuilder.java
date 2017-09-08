package infrastructure.web.jetty;

import infrastructure.properties.Settings;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import infrastructure.web.productavailability.productstockcheckbyavailability.ProductStockCheckByIdServlet;
import infrastructure.web.server.EndPoint;
import infrastructure.web.server.WebServerBuilder;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.zalando.logbook.DefaultHttpLogWriter;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.servlet.LogbookFilter;

import javax.servlet.http.HttpServlet;
import java.util.EnumSet;

import static javax.servlet.DispatcherType.*;
import static org.slf4j.LoggerFactory.getLogger;
import static org.zalando.logbook.DefaultHttpLogWriter.Level.INFO;

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
    public WebServerBuilder registerproductStockCheckByIdEndPoint(EndPoint endPoint, ProductStockCheckByIdServlet productStockCheckByIdServlet) {
        addServlet(productStockCheckByIdServlet, endPoint);
        return this;
    }

    @Override
    public ShopOfHanServer build() {
        addLoggingFilter();
        return new ShopOfHanServer(settings).withContext(servletHandler);
    }

    private void addServlet(HttpServlet httpServlet, EndPoint endPoint) {
        servletHandler.addServlet(new ServletHolder(httpServlet), endPoint.path);
    }

    private void addLoggingFilter() {
        Logbook logbook = Logbook.builder()
                .writer(new DefaultHttpLogWriter(getLogger(JettyWebserverBuilder.class), INFO))
                .build();
        FilterHolder filterHolder = new FilterHolder(new LogbookFilter(logbook));
        servletHandler.addFilter(filterHolder, "/*", EnumSet.of(REQUEST, ASYNC, ERROR));
    }
}
