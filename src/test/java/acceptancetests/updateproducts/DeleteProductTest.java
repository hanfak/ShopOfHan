package acceptancetests.updateproducts;

import acceptancetests.AcceptanceTest;
import acceptancetests.whens.WhenAProductHasBeenDeleted;
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
public class DeleteProductTest extends AcceptanceTest {
    // 204 no body returned, 202 aysnc
    @Test
    public void deleteAProductUsingItsUniqueId() throws Exception {
        given(aProductAlreadyExists(withProductId("CTD1"), andProductName("Clojure the door"), andProductDescription("Book about clojure")));

        when(aProduct.withProductId("CTD1").isDeleted());

        thenTheDatabaseDoesNotContainAProduct(withProductId("CTD1"));
        andTheResponseIsSuccessfulWithAStatusCodeOf(200);
        andTheResponseConfirmsTheProductHasBeenDeletedWithMessage("Product, CTD1, has been deleted");
    }

    // TODO test product has stock, and all stock must be removed as well as product

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

    private void andTheResponseConfirmsTheProductHasBeenDeletedWithMessage(String repsonsebody) throws Exception {
        then.theBodyIs(repsonsebody);
    }

    private void andTheResponseIsSuccessfulWithAStatusCodeOf(int statusCode) throws Exception {
        then.theStatusCodeIs(statusCode);
    }

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productId", "CTD1");
        return givens -> givens;
    }

    // TODO Add yatspec database recorder
    private void thenTheDatabaseDoesNotContainAProduct(String productId) {
        then.theProductDatabaseDoesnotHave(productId);
    }

    private final WhenAProductHasBeenDeleted aProduct = new WhenAProductHasBeenDeleted(testState);
}

