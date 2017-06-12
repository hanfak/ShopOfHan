package infrastructure.web.server;

import infrastructure.properties.Settings;
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

    public void start() throws Exception {
        server.start();
    }

    // Add try/catch for start and stop
//    public void stop() {
//        try {
//            server.stop();
//        } catch (Exception e) {
//            throw new IllegalStateException(format("Could not stop server on port '%d'", httpPort), e);
//        }
//    }

    public void stop() throws Exception {
        server.stop();
    }

//    @Override
//    public String startupMessage() {
//        return server.getURI().toString();
//    }
}
