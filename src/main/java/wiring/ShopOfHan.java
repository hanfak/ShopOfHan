package wiring;

import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import infrastructure.web.handler.Handler;
import infrastructure.web.server.ShopOfHanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopOfHan {
    static Logger LOGGER = LoggerFactory.getLogger(ShopOfHan.class);
    private static ShopOfHanServer server;

    public static void main(String[] args) throws Exception {
        new ShopOfHan().startWebServer();
    }

    public void startWebServer() throws Exception {
        LOGGER.info("Starting Shop Of Han app");
        Settings settings = loadSettings("localhost");
        // TODO Webserver builder
        startServer(settings);
    }

    private void startServer(Settings settings) {
        server = new ShopOfHanServer(settings);
        server.withContext(Handler.servletHandler());

        server.start();
    }

    private static Settings loadSettings(String propertyFile) {
        return new Settings(new PropertiesReader(propertyFile));
    }

    // INFO: For testsing only
    public void stopWebServer() throws Exception {
        LOGGER.info("Closing Shop Of Han app");
        server.stop();
    }
}
