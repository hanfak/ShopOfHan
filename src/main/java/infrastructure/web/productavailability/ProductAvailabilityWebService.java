package infrastructure.web.productavailability;

import application.ProductCheckUseCase;
import domain.ProductStock;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;

import java.io.IOException;

import static infrastructure.web.RenderedContent.errorContent;
import static infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityWebService {

    private final ProductCheckUseCase productCheckUseCase;
    private final Marshaller<ProductStock> marshaller;

    public ProductAvailabilityWebService(ProductCheckUseCase productCheckUseCase, Marshaller<ProductStock> marshaller) {
        this.productCheckUseCase = productCheckUseCase;
        this.marshaller = marshaller;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityRequest productAvailabilityRequest) throws IOException {
        try {
            ProductStock productStock = productCheckUseCase.checkStock(productAvailabilityRequest);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            return productNotStockedRenderedContent(productAvailabilityRequest, e);
        }
        // TODO M001B catch illegal argument here
//        catch (IllegalArgumentException e) {
//            return errorContent(format("Product name or id not supplied, cannot find product in stock\n%s", e.toString()));
//        }
    }

    private RenderedContent productNotStockedRenderedContent(ProductAvailabilityRequest productAvailabilityRequest, IllegalStateException e) {
        if (productAvailabilityRequest.getProductId() == null) {
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.getProductName().value, e.toString()));
        } else {
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.getProductId().value, e.toString()));
        }
    }
}
