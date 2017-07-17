package infrastructure.web.server;

import infrastructure.web.jetty.JettyWebserverBuilder;
import infrastructure.web.productavailability.productavailabilityById.ProductAvailabilityByIdServlet;
import infrastructure.web.productavailability.productavailabilityname.ProductAvailabilityByNameServlet;

public interface WebServerBuilder {
    JettyWebserverBuilder registerProductAvailabilityByNameEndPoint(EndPoint endPoint, ProductAvailabilityByNameServlet productAvailabilityByNameServlet);
    JettyWebserverBuilder registerProductAvailabilityByIdEndPoint(EndPoint endPoint, ProductAvailabilityByIdServlet productAvailabilityByIdServlet);

    ShopOfHanServer build();
}
