package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

public class ProductAvailabilityWebService {

    private final ProductCheckUseCase productCheckUseCase;
    private final ProductAvailabilityMarshaller marshaller; //Use interface type?? Need to be injecte??
    private Logger logger;

    public ProductAvailabilityWebService(ProductCheckUseCase productCheckUseCase, ProductAvailabilityMarshaller marshaller, Logger logger) {
        this.productCheckUseCase = productCheckUseCase;
        this.marshaller = marshaller;
        this.logger = logger;
    }

//    private void responseReturned(HttpServletResponse response, ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
//        try {
//            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
//            response.setContentType("application/json");
//            response.getWriter().write(marshaller.marshall(productStock));
//        } catch (NullPointerException e) {
//            logger.info("Product does not exist" + e);
//            response.setContentType("text/plain");
//            response.setStatus(404);
//            response.getWriter().write(format("Product '%s' is not stocked", productAvailabilityRequest.productName));
//        }
//    }
}
