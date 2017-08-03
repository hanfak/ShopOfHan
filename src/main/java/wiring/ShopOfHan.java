package wiring;

import infrastructure.properties.Settings;
import infrastructure.web.server.EndPoint;
import infrastructure.web.server.WebServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static wiring.ShopOfHanURLs.*;

public class ShopOfHan {
    private final static Logger logger = LoggerFactory.getLogger(ShopOfHan.class);
    private static WebServer webserver;

    public static void main(String... arguments) throws Exception {
        new ShopOfHan().startWebServer(loadSettings(new Wiring()), new Wiring());
    }

    public void startWebServer(Settings settings, Wiring wiring) throws Exception {
        logger.info("Starting Shop Of Han app");
        startServer(settings, wiring);
    }

    private void startServer(Settings settings, Wiring wiring) {
        webserver = wiring.webserverBuilder(settings)
                .registerProductAvailabilityByNameEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_NAME), wiring.productAvailabilityByNameServlet())
                .registerProductAvailabilityByIdEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_ID), wiring.productAvailabilityByIdServlet())
                .registerStatusProbeEndPoint(EndPoint.get(STATUS_PAGE), wiring.statusProbeServlet())
                .build();
        webserver.start();
    }

    private static Settings loadSettings(Wiring wiring) {
        return wiring.settings();
    }

    // INFO: For testsing only
    public void stopWebServer() throws Exception {
        logger.info("Closing Shop Of Han app");
        webserver.stop();
    }
}
