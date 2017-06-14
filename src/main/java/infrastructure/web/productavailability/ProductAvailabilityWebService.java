package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;
import org.slf4j.Logger;

import java.io.IOException;
import java.util.Optional;

import static java.lang.String.format;

public class ProductAvailabilityWebService {

    private final ProductCheckUseCase productCheckUseCase;
    private final Marshaller marshaller; //Use interface type?? Need to be injecte??
    private Logger logger;

    public ProductAvailabilityWebService(ProductCheckUseCase productCheckUseCase, ProductAvailabilityMarshaller marshaller, Logger logger) {
        this.productCheckUseCase = productCheckUseCase;
        this.marshaller = marshaller;
        this.logger = logger;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        try {
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
            logger.info("Product does exist " + productStock.product.productName); //use the name from stock or from request (to keep consistent?
            return new RenderedContent(marshaller.marshall(productStock), "application/json", 200);
        } catch (IllegalStateException e) {
            logger.info("Product does not exist " + productAvailabilityRequest.productName);
            return new RenderedContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.productName, e.toString()), "text/plain", 404);
        }
    }
}
