package wiring;

import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;
import infrastructure.web.handler.Handler;
import infrastructure.web.server.ShopOfHanServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShopOfHan {
    static Logger LOGGER = LoggerFactory.getLogger(ShopOfHan.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("Starting web app");
        Settings settings = new Settings(new PropertiesReader("localhost"));

        ShopOfHanServer server = new ShopOfHanServer(settings);
        server.withContext(Handler.servletHandler());

        server.start();
    }
}
