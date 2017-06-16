package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;
import org.slf4j.Logger;

import java.io.IOException;

import static infrastructure.web.RenderedContent.errorContent;
import static infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityWebService {

    private final ProductCheckUseCase productCheckUseCase;
    private final Marshaller<ProductStock> marshaller;
    private Logger logger;

    public ProductAvailabilityWebService(ProductCheckUseCase productCheckUseCase, Marshaller<ProductStock> marshaller, Logger logger) {
        this.productCheckUseCase = productCheckUseCase;
        this.marshaller = marshaller;
        this.logger = logger;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        try {
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
            logger.info("Product does exist " + productStock.productName); //TODO use the name from stock or from request (to keep consistent?
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            logger.info("Product does not exist " + productAvailabilityRequest.productName);
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.productName, e.toString()));
        }
    }
}
