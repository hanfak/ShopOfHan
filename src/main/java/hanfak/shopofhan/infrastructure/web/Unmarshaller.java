package hanfak.shopofhan.infrastructure.web;


import javax.servlet.http.HttpServletRequest;

public interface Unmarshaller<Request> {
    Request unmarshall(HttpServletRequest request);
}
