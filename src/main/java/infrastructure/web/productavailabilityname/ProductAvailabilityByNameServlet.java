package infrastructure.web.productavailabilityname;

import infrastructure.web.RenderedContent;
import infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ProductAvailabilityByNameServlet extends HttpServlet {

    private final Unmarshaller<ProductAvailabilityByNameRequest> unmarshaller;
    private final ProductAvailabilityByNameWebService productAvailabilityByNameWebService;

    public ProductAvailabilityByNameServlet(Unmarshaller<ProductAvailabilityByNameRequest> unmarshaller, ProductAvailabilityByNameWebService productAvailabilityByNameWebService) {
        this.unmarshaller = unmarshaller;
        this.productAvailabilityByNameWebService = productAvailabilityByNameWebService;
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RenderedContent renderedContent = productAvailabilityByNameWebService.requestProductCheck(unmarshaller.unmarshall(request));
        renderedContent.render(response);
    }
}
