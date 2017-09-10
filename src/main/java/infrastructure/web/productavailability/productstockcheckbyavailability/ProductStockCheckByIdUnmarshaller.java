package infrastructure.web.productavailability.productstockcheckbyavailability;

import domain.product.ProductId;
import infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;

public class ProductStockCheckByIdUnmarshaller implements Unmarshaller<ProductId> {

    //TODO validation and test
    @Override
    public ProductId unmarshall(HttpServletRequest request) {
        return ProductId.productId(request.getPathInfo().substring(1));
    }
}
