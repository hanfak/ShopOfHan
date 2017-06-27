package infrastructure.web.server;

import infrastructure.Settings;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;

import static java.lang.String.format;

// Check out hydra/infras/web/jetty and /web/server
public class ShopOfHanServer {

    private final Server server;

    public ShopOfHanServer(Settings settings) {
        this.server = new Server(settings.serverPort());
    }

    public void withContext(ServletContextHandler servletHandler) {
        server.setHandler(servletHandler);
    }

    public void start() {
        try {
            server.start();
        } catch (Exception e) {
            throw new IllegalStateException(format("Could not start server on port '%d'", server.getURI().getPort()), e);
        }
    }

    public void stop() {
        try {
            server.stop();
        } catch (Exception e) {
            throw new IllegalStateException(format("Could not stop server on port '%d'", server.getURI().getPort()), e);
        }
    }

//    @Override
//    public String startupMessage() {
//        return server.getURI().toString();
//    }
}
