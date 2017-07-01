package infrastructure.web.productavailability;

import infrastructure.web.RenderedContent;
import infrastructure.web.Unmarshaller;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAvailabilityServlet extends HttpServlet {

    private final Unmarshaller<ProductAvailabilityRequest> unmarshaller;
    private final ProductAvailabilityWebService productAvailabilityWebService;

    public ProductAvailabilityServlet(Unmarshaller<ProductAvailabilityRequest> unmarshaller, ProductAvailabilityWebService productAvailabilityWebService) {
        this.unmarshaller = unmarshaller;
        this.productAvailabilityWebService = productAvailabilityWebService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAvailabilityRequest unmarshall = unmarshaller.unmarshall(request);
        RenderedContent renderedContent = productAvailabilityWebService.requestProductCheck(unmarshall);
        renderedContent.render(response);
    }
}
