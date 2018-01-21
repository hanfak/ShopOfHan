package hanfak.shopofhan.infrastructure.web.removeproduct;

import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class RemoveProductUnmarshaller implements Unmarshaller<ProductId> {
    public ProductId unmarshall(HttpServletRequest request) throws IOException {
        String contextPath = request.getPathInfo().substring(1);
        return ProductId.productId(contextPath);
    }
}
