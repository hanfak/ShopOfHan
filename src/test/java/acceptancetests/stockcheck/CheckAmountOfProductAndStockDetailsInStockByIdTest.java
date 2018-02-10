package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import hanfak.shopofhan.domain.product.Product;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.sql.SQLException;
import java.util.Optional;

import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductAndStockDetailsInStockByIdTest extends AcceptanceTest {

    private Optional<Product> product;

    // TODO pass when using database instead of stub
    @Test
    @Ignore
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        and(aProductAlreadyExists(withProductId("STS1"), andProductName("SQL the sequel"), andProductDescription("Book about SQL")));
        and(stockAlreadyExistsForProductId("STS1", "STD1", "Single Pack", 0));
        and(stockAlreadyExistsForProductId("STS1", "STD2", "Multi Pack", 3));

        when(weMake.aGetRequestTo(PATH + SQL_THE_SEQUEL));

        thenTheResponseCodeIs200AndTheBodyIs(EXPECTED_RESPONSE);
        andThenContentTypeIs("Content-Type: application/json");
    }

    @Test
    @Ignore
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMake.aGetRequestTo(PATH + HARRY_POTTER));

        thenTheResponseCodeIs404AndTheBodyIs("Product 'HP1' is not stocked java.lang.IllegalStateException: Product is not found");
        andThenContentTypeIs("Content-Type: text/plain");
    }

    // Todo: test if product is there but no stock

    private GivensBuilder aProductAlreadyExists(String productId, String productName, String productDescription) throws SQLException {
        product = productRepository.addProduct(product(productDescription(productDescription), productId(productId), productName(productName)));
        testState().interestingGivens.add("productId", productId);
        return givens -> givens;
    }

    private GivensBuilder stockAlreadyExistsForProductId(String productId, String stockId, String stockDescription, Integer stockAmount) throws SQLException {
//        testProductStockRepository.addStock(Stock.stock(StockAmount.stockAmount(stockAmount), StockId.stockId(stockId), StockDescription.stockDescription(stockDescription), ProductId.productId(productId)));
//        testProductStockRepository.addToProductStockList(product);
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
    private static final String PATH = "http://localhost:8081/fullProductStockCheck/id/";
    private static final String SQL_THE_SEQUEL = "STS1";

    private static final String EXPECTED_RESPONSE =
            "{\"productName\": \"SQL the sequel\"," +
                    "\"productId\": \"STS1\"," +
                    "\"productDescription\": \"Book about SQL\"," +
                    "\"stock\":" +
                    "[" +
                    "{" +
                    "\"stockId\": \"STD1\"," +
                    "\"amountInStock\": \"0\"," +
                    "\"stockDescription\": \"Single Pack\"" +
                    "}," +
                    "{" +
                    "\"stockId\": \"STD2\"," +
                    "\"amountInStock\": \"3\"," +
                    "\"stockDescription\": \"Multi Pack\"" +
                    "}" +
                    "]}";
}