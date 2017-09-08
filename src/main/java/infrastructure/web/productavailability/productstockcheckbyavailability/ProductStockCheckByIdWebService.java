package infrastructure.web.productavailability.productstockcheckbyavailability;

import domain.product.ProductId;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;

public class ProductStockCheckByIdWebService {
    private final ProductStockCheckByIdUseCase productStockCheckByIUseCase;
    private final Marshaller<ProductStockList> marshaller;


    public RenderedContent requestProductCheck(ProductId productId) {
        return null;
    }
}
