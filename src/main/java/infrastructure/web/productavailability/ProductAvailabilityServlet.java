package infrastructure.web.productavailability;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.lang.String.format;

public class ProductAvailabilityServlet extends HttpServlet {

    private ProductAvailabilityUnmarshaller unmarshaller;

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAvailabilityRequest productAvailabilityRequest = unmarshaller.unmarshall(request);
        System.out.println("productAvailabilityRequest = " + productAvailabilityRequest.productName);

        String checking = format("Going to check the product availability of %s", productAvailabilityRequest.productName);

        response.getWriter().write(checking);
    }
}
