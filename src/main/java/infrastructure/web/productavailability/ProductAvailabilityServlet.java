package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import infrastructure.web.RenderedContent;
import org.slf4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class ProductAvailabilityServlet extends HttpServlet {

    private final ProductAvailabilityUnmarshaller unmarshaller;
    private final ProductAvailabilityMarshaller marshaller; //Use interface type?? Need to be injecte??
    private ProductCheckUseCase productCheckUseCase;
    private Logger logger;

    public ProductAvailabilityServlet(ProductAvailabilityUnmarshaller unmarshaller, ProductAvailabilityMarshaller marshaller, ProductCheckUseCase productCheckUseCase, Logger logger) {
        this.unmarshaller = unmarshaller;
        this.marshaller = marshaller;
        this.productCheckUseCase = productCheckUseCase;
        this.logger = logger;
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // move to a webervice class
        ProductAvailabilityRequest productAvailabilityRequest = unmarshaller.unmarshall(request);
        logger.info("Unmarshalling");
        // Need to change, this logic should be usecase
        responseReturned(response, productAvailabilityRequest);
    }

    private void responseReturned(HttpServletResponse response, ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        // Extract creation of rendered content and usecase to webservice
        try {
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
            RenderedContent renderedContent = new RenderedContent(format(marshaller.marshall(productStock), productAvailabilityRequest.productName), "application/json", 200);
            renderedContent.render(response);
        } catch (NullPointerException e) {
            logger.info("Product does not exist" + e);
            RenderedContent renderedContent = new RenderedContent(format("Product '%s' is not stocked", productAvailabilityRequest.productName), "text/plain", 404);
            renderedContent.render(response);
        }
    }
}

//    String body = readInputStream(request.getInputStream());
//    RenderedContent renderedContent = portInRequestWebService.requestPortIn(body);
//        renderedContent.render(response);