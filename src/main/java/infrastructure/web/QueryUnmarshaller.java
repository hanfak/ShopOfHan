package infrastructure.web;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface QueryUnmarshaller<Request> {
    Request unmarshall(HttpServletRequest requestBody) throws IOException;
}
