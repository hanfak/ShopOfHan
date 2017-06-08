package infrastructure.web.productavailability;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

public class ProductAvailabilityServlet extends HttpServlet {
    private ProductAvailabilityUnmarshaller unmarshaller;

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller) {
        this.unmarshaller = unmarshaller;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String productName = request.getParameter("productName");

        ProductAvailabilityRequest unmarshall = unmarshaller.unmarshall(request);

        response.getWriter().write(format("Going to check the product availability of %s", ));
    }
}
