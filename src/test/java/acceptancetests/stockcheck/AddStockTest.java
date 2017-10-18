package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.ProductId;
import org.junit.Test;

import java.io.IOException;
import java.sql.SQLException;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

public class AddStockTest extends AcceptanceTest {

    @Test
    public void shouldReturn200WhenStoringNewStock() throws Exception {
        given(theSystemIsRunning());
        and(aProductAlreadyExists());
        when(stockIsAdded.throughARequestTo("http://localhost:8081/stock", withProductName("Clojure the door"), withProductId("CTD1"), withStockId("Clojure the door"), withStockDescription("Book about Clojure"), andAmount(5)));
        thenItReturnsAStatusCodeOf(200);
        andTheResponseBodyIs("Product with id, 'CTD1', has been added.");
//        andTheDatabaseContainsAStockWithStockId("Clojure the door", withProductId("CTD1"), withStockDescription("Book about Clojure"), 5L);
    }

    private String withProductName(String productName) {
        return productName;
    }

    private long andAmount(long amount) {
        return amount;
    }

    private GivensBuilder aProductAlreadyExists() throws SQLException {
        productRepository.addProduct(product(productDescription("Book about SQL"), productId("STS1"), productName("SQL the sequel")));
        return givens -> givens;
    }

    private void andTheDatabaseContainsAStockWithStockId(String stockID, String productId, String stockDescription, long amount) {
        the.stockDatabase(stockID, productId, stockDescription, amount);
    }

    private String withStockDescription(String description) {
        return description;
    }

    private String withStockId(String name) {
        return name;
    }

    private String withProductId(String id) {
        actualProductId = ProductId.productId(id);
        return id;
    }

    private GivensBuilder theSystemIsRunning() throws IOException {
        testState().interestingGivens.add("productName", "Joy Of Java");
        // TODO add parts of products to interestinggivens
        return givens -> givens;
    }


    private void andTheResponseBodyIs(String expected) throws Exception {
        the.body(expected);
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        the.statusCode(expected);
    }

    private ProductId actualProductId;
}
