package infrastructure.web.productavailability.productavailabilityById;

import application.productavailability.ProductCheckByIdUseCase;
import domain.ProductStock;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;

import static infrastructure.web.RenderedContent.errorContent;
import static infrastructure.web.RenderedContent.jsonContent;
import static java.lang.String.format;

public class ProductAvailabilityByIdWebService {

    private final ProductCheckByIdUseCase productCheckByIdUseCase;
    private final Marshaller<ProductStock> marshaller;

    public ProductAvailabilityByIdWebService(ProductCheckByIdUseCase productCheckByIdUseCase, Marshaller<ProductStock> marshaller) {
        this.productCheckByIdUseCase = productCheckByIdUseCase;
        this.marshaller = marshaller;
    }

    public RenderedContent requestProductCheck(ProductAvailabilityByIdRequest productAvailabilityRequest) {
        try {
            ProductStock productStock = productCheckByIdUseCase.checkStock(productAvailabilityRequest);
            return jsonContent(marshaller.marshall(productStock));
        } catch (IllegalStateException e) {
            return errorContent(format("Product '%s' is not stocked %s", productAvailabilityRequest.getProductId().value, e.toString()));
        }
    }

}
