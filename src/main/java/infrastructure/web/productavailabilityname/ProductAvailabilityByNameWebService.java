package infrastructure.web.productavailabilityname;

import application.ProductCheckByNameUseCase;
import domain.ProductStock;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;

import java.io.IOException;

import static infrastructure.web.RenderedContent.errorContent;
import static infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityByNameWebService {

    private final ProductCheckByNameUseCase productCheckByNameUseCase;
    private final Marshaller<ProductStock> marshaller;

    public ProductAvailabilityByNameWebService(ProductCheckByNameUseCase productCheckByNameUseCase, Marshaller<ProductStock> marshaller) {
        this.productCheckByNameUseCase = productCheckByNameUseCase;
        this.marshaller = marshaller;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityByNameRequest productAvailabilityRequest) throws IOException {
        try {
            ProductStock productStock = productCheckByNameUseCase.checkStock(productAvailabilityRequest);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.getProductName().value, e.toString()));
        }
    }

}
