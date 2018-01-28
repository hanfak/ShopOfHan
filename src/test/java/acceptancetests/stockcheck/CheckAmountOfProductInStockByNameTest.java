package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.domain.stock.StockAmount;
import hanfak.shopofhan.domain.stock.StockDescription;
import hanfak.shopofhan.domain.stock.StockId;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByNameTest extends AcceptanceTest {

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        given(aProductAlreadyExists(withProductId("JOJ1"), andProductName("Joy Of Java"), andProductDescription("Book about java")));
        given(stockAlreadyExistsForProductId("JOJ1", "STD1", "Single Pack", 4 )); // TODO add reader methods forargs

        when(weMake.aGetRequestTo(PATH + JOY_OF_JAVA));

        thenItReturnsAStatusCodeOf(200);
        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of Java\", \"amountInStock\": \"4\"}");
        andThenContentTypeIs("Content-Type: application/json");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMake.aGetRequestTo(PATH + HARRY_POTTER));
        thenItReturnsAStatusCodeOf(404);
        thenTheResponseCodeIs404AndTheBodyIs("Product 'Harry Potter' is not stocked java.lang.IllegalStateException: Product is not found");
        andThenContentTypeIs("Content-Type: text/plain");
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMake.aGetRequestTo(BAD_URL));
        thenItReturnsAStatusCodeOf(404);
    }

    // TODO multiple different stock checks will return first one only (new user story)

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        product = productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productName", productName);
        testState().interestingGivens.add("productId", productId);
        return givens -> givens;
    }

    private GivensBuilder stockAlreadyExistsForProductId(String productId, String stockId, String stockDescription, Integer stockAmount) throws SQLException {
        stockRepository.addStock(Stock.stock(StockAmount.stockAmount(stockAmount), StockId.stockId(stockId), StockDescription.stockDescription(stockDescription), ProductId.productId(productId)));
        stockRepository.addToProductStockList(product);
        System.out.println("asdfnaklsdjfk " + stockRepository.showAll());
        testState().interestingGivens.add("productId", productId);
        return givens -> givens;
    }

    private String andProductDescription(String productDescription) {
        return productDescription;
    }

    private String andProductName(String productName) {
        return productName;
    }

    private String withProductId(String productId) {
        return productId;
    }

    private GivensBuilder theSystemIsRunning() {

         stockRepository.remove();
        return givens -> givens;
    }

    private void thenTheResponseCodeIs404AndTheBodyIs(String expected) throws Exception {
        thenItReturnsAStatusCodeOf(404);
        andTheResponseBodyIs(expected);
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws Exception {
        thenItReturnsAStatusCodeOf(200);
        andTheResponseBodyIs(expected);
    }

    private void andThenContentTypeIs(String s) throws Exception {
        then.theContentTypeIs(s);
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        then.theStatusCodeIs(expected);
    }

    private void andTheResponseBodyIs(String expected) throws Exception {
        then.theBodyIs(expected);
    }

    private static final String HARRY_POTTER = "Harry%20Potter";
    private static final String PATH = "http://localhost:8081/productscheck/name/";
    private static final String JOY_OF_JAVA = "Joy%20Of%20Java";
    private static final String BAD_URL = "http://localhost:8081/a/bad/url";
    @SuppressWarnings("OptionalUsedAsFieldOrParameterType")
    private Optional<Product> product;

}