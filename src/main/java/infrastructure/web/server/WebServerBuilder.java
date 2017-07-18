package infrastructure.web.server;

import infrastructure.web.jetty.ShopOfHanServer;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;
import infrastructure.web.statusprobeservlet.StatusProbeServlet;

public interface WebServerBuilder {
    WebServerBuilder registerProductAvailabilityByNameEndPoint(EndPoint endPoint, ProductAvailabilityByNameServlet productAvailabilityByNameServlet);
    WebServerBuilder registerProductAvailabilityByIdEndPoint(EndPoint endPoint, ProductAvailabilityByIdServlet productAvailabilityByIdServlet);
    WebServerBuilder registerStatusProbeEndPoint(EndPoint endPoint, StatusProbeServlet statusProbeServlet);
    ShopOfHanServer build();
}
