package wiring;

import infrastructure.properties.Settings;
import infrastructure.web.handler.Handler;
import infrastructure.web.server.ShopOfHanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopOfHan {
    private final static Logger logger = LoggerFactory.getLogger(ShopOfHan.class);
    private static ShopOfHanServer server;

    public static void main(String... arguments) throws Exception {
        // TODO initialize wiring
        new ShopOfHan().startWebServer();
    }

    public void startWebServer() throws Exception {
        logger.info("Starting Shop Of Han app");
        Settings settings = loadSettings();
        // TODO Webserver builder
        startServer(settings);
    }

    private void startServer(Settings settings) {
        server = new ShopOfHanServer(settings);
        server.withContext(Handler.servletHandler());

        server.start();
    }

    private static Settings loadSettings() {
        return Wiring.settings();
    }

    // INFO: For testsing only
    public void stopWebServer() throws Exception {
        logger.info("Closing Shop Of Han app");
        server.stop();
    }
}
