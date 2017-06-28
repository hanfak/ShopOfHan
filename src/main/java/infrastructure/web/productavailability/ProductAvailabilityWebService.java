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

    //TODO should param be ProductAvailabilityRequest or ProductToCheck
    public RenderedContent requestProductCheck(ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        try {
            // TODO KEY: Should productAvailabilityRequest be passed as argument or change to an interface or use the String value??
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest); // request parrt of sdomain
            logger.info("Product does exist " + productStock.productName);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            String productName = productAvailabilityRequest.getProductName();
            logger.info("Product does not exist " + productName);
            return errorContent(format("Product '%s' is not stocked %s", productName, e.toString()));
        }
    }
}
