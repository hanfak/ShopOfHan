package wiring;

import infrastructure.web.server.ShopOfHanServer;
import infrastructure.web.handler.Handler;
import infrastructure.properties.PropertiesReader;
import infrastructure.properties.Settings;

public class ShopOfHan {
    public static void main(String[] args) throws Exception {
        Settings settings = new Settings(new PropertiesReader("localhost"));

        ShopOfHanServer server = new ShopOfHanServer(settings);
        server.withContext(Handler.servletHandler());

        server.start();
    }
}
