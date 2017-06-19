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
    private final Logger logger;
    private final ProductAvailabilityWebService productAvailabilityWebService;

    public ProductAvailabilityServlet(Unmarshaller<ProductAvailabilityRequest> unmarshaller, Logger logger, ProductAvailabilityWebService productAvailabilityWebService) {
        this.unmarshaller = unmarshaller;
        this.logger = logger;
        this.productAvailabilityWebService = productAvailabilityWebService;
    }
    // TODO Should I test this?
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ProductAvailabilityRequest productAvailabilityRequestRequest = unmarshaller.unmarshall(request);
        logger.info("Unmarshalling");
        RenderedContent renderedContent = productAvailabilityWebService.requestProductCheck(productAvailabilityRequestRequest);
        renderedContent.render(response);
    }
}
