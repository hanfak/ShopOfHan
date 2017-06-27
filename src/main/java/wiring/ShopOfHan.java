package wiring;

import infrastructure.PropertiesReader;
import infrastructure.Settings;
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
        LOGGER.info("Starting web app");
        Settings settings = loadSettings("localhost");
        // TODO Webserver builder
        server = new ShopOfHanServer(settings);
        server.withContext(Handler.servletHandler());

        server.start();
    }

    private static Settings loadSettings(String propertyFile) {
        return new Settings(new PropertiesReader(propertyFile));
    }

    // For testsing only
    public void stopWebServer() throws Exception {
        server.stop();
    }
}
