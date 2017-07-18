package wiring;

import infrastructure.properties.Settings;
import infrastructure.web.server.EndPoint;
import infrastructure.web.server.WebServer;
import infrastructure.web.server.WebServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static wiring.ShopOfHanURLs.*;
import static wiring.Wiring.*;

public class ShopOfHan {
    private final static Logger logger = LoggerFactory.getLogger(ShopOfHan.class);
    private static WebServer webserver;

    public static void main(String... arguments) throws Exception {
        // TODO initialize wiring
        new ShopOfHan().startWebServer();
    }

    public void startWebServer() throws Exception {
        logger.info("Starting Shop Of Han app");
        Settings settings = loadSettings();
        startServer(webserverBuilder(settings));
    }

    private void startServer(WebServerBuilder webServerBuilder) {
        webserver = webServerBuilder
                .registerProductAvailabilityByNameEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_NAME), productAvailabilityByNameServlet())
                .registerProductAvailabilityByIdEndPoint(EndPoint.get(PRODUCT_AVAILABILITY_BY_ID), productAvailabilityByIdServlet())
                .registerStatusProbeEndPoint(EndPoint.get(STATUS_PAGE), statusProbeServlet())
                .build();
        webserver.start();
    }

    private static Settings loadSettings() {
        return Wiring.settings();
    }

    // INFO: For testsing only
    public void stopWebServer() throws Exception {
        logger.info("Closing Shop Of Han app");
        webserver.stop();
    }
}
