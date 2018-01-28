package acceptancetests.updateproducts;

import acceptancetests.AcceptanceTest;
import acceptancetests.whens.WhenAProductHasBeenUpdated;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.ProductId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class UpdateProductTest extends AcceptanceTest {
    private ProductId actualProductId;

    @Test
    public void updateAProductUsingItsUniqueId() throws Exception {
        given(aProductAlreadyExists(withProductId("CTD1"), andProductName("Clojure the door"), andProductDescription("Book about clojure")));

        when(aProduct.withProductId("CTD1").withProductDescription("The best book about clojure").isUpdated());

        thenTheDatabaseContainsAProductWithName(withProductId("CTD1"), andProductName("Clojure the door"), andProductDescription("The best book about clojure"));
        andTheResponseIsSuccessfulWithAStatusCodeOf(200);
        andTheResponseConfirmsTheProductHasBeenUpdatedWithMessage("Product updated");

    }

    // TODO test if product not in database, usecase returns optional.empty, and  404

    // TODO unit test cannot change the product id


    private void andTheResponseConfirmsTheProductHasBeenUpdatedWithMessage(String repsonsebody) throws Exception {
        then.theBodyIs(repsonsebody);
    }

    private void thenTheDatabaseContainsAProductWithName(String id, String name, String description) {
        then.theProductDatabaseHasA(actualProductId, name, id, description);
    }

    private String andProductDescription(String productDescription) {
        return productDescription;
    }

    private String andProductName(String productName) {
        return productName;
    }

    private String withProductId(String productId) {
        actualProductId = ProductId.productId(productId);
        return productId;
    }

    private void andTheResponseIsSuccessfulWithAStatusCodeOf(int statusCode) throws Exception {
        then.theStatusCodeIs(statusCode);
    }

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productId", productId);
        return givens -> givens;
    }

    private final WhenAProductHasBeenUpdated aProduct = new WhenAProductHasBeenUpdated(testState);
}

