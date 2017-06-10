package infrastructure.web.server;

import infrastructure.properties.ServerSettings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

public class ShopOfHanServer {

    private final Server server;

    public ShopOfHanServer(ServerSettings settings) {
        this.server = new Server(settings.serverPort());
    }

    public void withContext(ServletContextHandler servletHandler) {
        server.setHandler(servletHandler);
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }
}
