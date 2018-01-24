package hanfak.shopofhan.infrastructure.web.updateproduct;

import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductDescription;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import hanfak.shopofhan.infrastructure.web.InputStreamReader;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
// TODO abstract out
public class UpdateProductUnmarshaller implements Unmarshaller<Product> {
    public Product unmarshall(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject(InputStreamReader.readInputStream(request.getInputStream()));
        // TODO Add validation
        return getProduct(jsonObject);
    }

    private Product getProduct(JSONObject jsonObject) {
        ProductName productName = productName(jsonObject.getString("productName"));
        ProductId productId = productId(jsonObject.getString("productId"));
        ProductDescription productDescription = productDescription(jsonObject.getString("productDescription"));
        return product(productDescription, productId, productName);
    }
}
