package infrastructure.web;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface JsonUnmarshaller<Request> {
    Request unmarshall(HttpServletRequest requestBody) throws IOException;
}
