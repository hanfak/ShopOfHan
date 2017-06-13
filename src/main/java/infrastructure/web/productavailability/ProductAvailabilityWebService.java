package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import infrastructure.web.RenderedContent;
import org.slf4j.Logger;

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

    public RenderedContent requestProductCheck(ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        try {
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
            return new RenderedContent(format(marshaller.marshall(productStock), productAvailabilityRequest.productName), "application/json", 200);
        } catch (NullPointerException e) {
            logger.info("Product does not exist" + e);
            return new RenderedContent(format("Product '%s' is not stocked", productAvailabilityRequest.productName), "text/plain", 404);
        }
    }
}
