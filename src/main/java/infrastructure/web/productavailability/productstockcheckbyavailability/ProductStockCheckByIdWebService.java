package infrastructure.web.productavailability.productstockcheckbyavailability;

import application.productavailability.ProductStockCheckByIdUseCase;
import domain.ProductStockList;
import domain.product.ProductId;
import infrastructure.web.Marshaller;
import infrastructure.web.RenderedContent;

import static infrastructure.web.RenderedContent.jsonContent;

public class ProductStockCheckByIdWebService {
    private final ProductStockCheckByIdUseCase productStockCheckByIdUseCase;
    private final Marshaller<ProductStockList> marshaller;

    public ProductStockCheckByIdWebService(Marshaller<ProductStockList> marshaller, ProductStockCheckByIdUseCase productStockCheckByIdUseCase) {
        this.marshaller = marshaller;
        this.productStockCheckByIdUseCase = productStockCheckByIdUseCase;
    }

    public RenderedContent requestProductCheck(ProductId productId) {
        ProductStockList productStockList = productStockCheckByIdUseCase.checkStock(productId);
        return jsonContent(marshaller.marshall(productStockList));
    }
}
