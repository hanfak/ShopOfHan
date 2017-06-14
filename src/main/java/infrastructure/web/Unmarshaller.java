package infrastructure.web;


import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface Unmarshaller<Request> {
    Request unmarshall(HttpServletRequest request) throws IOException;
}
