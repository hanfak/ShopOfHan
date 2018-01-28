package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.Product;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
import static hanfak.shopofhan.domain.stock.Stock.stock;
import static hanfak.shopofhan.domain.stock.StockAmount.stockAmount;
import static hanfak.shopofhan.domain.stock.StockDescription.stockDescription;
import static hanfak.shopofhan.domain.stock.StockId.stockId;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByIdTest extends AcceptanceTest {

    private Optional<Product> product;

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        given(aProductAlreadyExists(withProductId("JOJ1"), andProductName("Joy Of Java"), andProductDescription("Book about java")));
        given(stockAlreadyExistsForProductId("JOJ1", "STD1", "Single Pack", 4 )); // TODO add reader methods forargs

        when(weMake.aGetRequestTo(PATH + JOY_OF_JAVA_ID));

        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of Java\", \"amountInStock\": \"4\"}");
        andThenContentTypeIs("Content-Type: application/json");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMake.aGetRequestTo(PATH + HARRY_POTTER));

        thenTheResponseCodeIs404AndTheBodyIs("Product 'HP1' is not stocked java.lang.IllegalStateException: Product is not found");
        andThenContentTypeIs("Content-Type: text/plain");
    }

    // TODO multiple different stock checks will return first one only (new user story)

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        product = productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productId", productId);
        return givens -> givens;
    }

    private GivensBuilder stockAlreadyExistsForProductId(String productId, String stockId, String stockDescription, Integer stockAmount) throws SQLException {
        stockRepository.addStock(stock(stockAmount(stockAmount), stockId(stockId), stockDescription(stockDescription), productId(productId)));
        stockRepository.addToProductStockList(product);
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
        testState().interestingGivens.add("productName","Joy Of Java");
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

    private static final String HARRY_POTTER = "HP1";
    private static final String PATH = "http://localhost:8081/productscheck/id/";
    private static final String JOY_OF_JAVA_ID = "JOJ1";
}