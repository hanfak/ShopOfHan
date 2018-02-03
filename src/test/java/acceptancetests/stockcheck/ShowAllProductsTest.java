package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class ShowAllProductsTest extends AcceptanceTest {

    @Test
    public void showAllProducts() throws Exception {
        given(aProductAlreadyExists(withProductId("CTD1"), andProductName("Clojure the door"), andProductDescription("Book about clojure")));

        when(weMake.aGetRequestTo(PATH));

        thenTheResponseCodeIs200AndTheBodyIs("{[{\"productId\": \"CTD1\", \"productName\": \"Clojure the door\", \"productDescription\": \"Book about clojure\"}]}");
    }

    // TODO test multiple products to be shown
    // TODO test if product not in database, usecase returns optional.empty, and  404

    private String andProductDescription(String productDescription) {
        return productDescription;
    }

    private String andProductName(String productName) {
        return productName;
    }

    private String withProductId(String productId) {
        return productId;
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws Exception {
        thenItReturnsAStatusCodeOf(200);
        andTheResponseBodyIs(expected);
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        then.theStatusCodeIs(expected);
    }

    private void andTheResponseBodyIs(String expected) throws Exception {
        // TODO test by comparing json objects, so order does not matter in string
        then.theBodyIs(expected);
    }

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productId", "CTD1");
        return givens -> givens;
    }

    private static final String PATH = "http://localhost:8081/products";
}

