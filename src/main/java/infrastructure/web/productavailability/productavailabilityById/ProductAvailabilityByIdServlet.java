package infrastructure.web.productavailability.productavailabilityById;

import domain.product.ProductId;
import infrastructure.web.RenderedContent;
import infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAvailabilityByIdServlet extends HttpServlet {

    private final Unmarshaller<ProductId> unmarshaller;
    private final ProductAvailabilityByIdWebService productAvailabilityByIdWebService;

    public ProductAvailabilityByIdServlet(Unmarshaller<ProductId> unmarshaller, ProductAvailabilityByIdWebService productAvailabilityByIdWebService) {
        this.unmarshaller = unmarshaller;
        this.productAvailabilityByIdWebService = productAvailabilityByIdWebService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RenderedContent renderedContent = productAvailabilityByIdWebService.requestProductCheck(unmarshaller.unmarshall(request));
        renderedContent.render(response);
    }
}
