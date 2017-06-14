package infrastructure.web;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RenderedContent {

    public final String body;
    public final String contentType;
    public final int statusCode;

    private RenderedContent(String body, String contentType, int statusCode) {
        this.body = body;
        this.contentType = contentType;
        this.statusCode = statusCode;
    }

    public static RenderedContent renderedContent(String body, String contentType, int statusCode) {
        return new RenderedContent(body, contentType, statusCode);
    }

    // good way??
    public static RenderedContent jsonContent(String body) {
        return new RenderedContent(body, "application/json", 200);
    }
    public static RenderedContent errorContent(String body) {
        return new RenderedContent(body, "text/plain", 404);
    }

    public void render(HttpServletResponse response) throws IOException {
        response.setStatus(statusCode);
        response.setContentType(contentType);
        response.getWriter().print(body);
    }
}
