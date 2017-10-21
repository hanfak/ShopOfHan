package acceptancetests.thens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;
import httpclient.Response;
import org.assertj.core.api.WithAssertions;

import java.util.Optional;

import static acceptancetests.AcceptanceTest.productRepository;
import static acceptancetests.AcceptanceTest.stockRepository;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductName.productName;

public class Thens implements WithAssertions {

    private TestState testState;
    private final CapturedInputAndOutputs capturedInputAndOutputs;

    public Thens(TestState testState, CapturedInputAndOutputs capturedInputAndOutputs) {
        this.testState = testState;
        this.capturedInputAndOutputs = capturedInputAndOutputs;
    }

    public void statusCode(int expected) throws Exception {
        StateExtractor<Integer> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).statusCode;
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    public void body(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).body;
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    public void contentType(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getContentType();
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).contains(expected);
    }

    //TODO naming of method for readbility in yatspec output
    public void productDatabase(ProductId actualProductId, String name, String id, String description) {
        Optional<Product> product = productRepository.checkProductById(actualProductId);
        if (product.isPresent()) {
            assertThat(product.get().productName).isEqualTo(productName(name));
            assertThat(product.get().productId).isEqualTo(ProductId.productId(id));
            assertThat(product.get().productDescription).isEqualTo(productDescription(description));
        } else {
            fail("Nothing matches in the productDatabase");
        }
    }

    public void stockDatabase(String stockId, String productId, String stockDescription, int amount) {
        // TODO: add to test db which inherits from JDBCStockRepository and returns the stock using the parameters
        Stock stock = stockRepository.checkStock(stockId, productId);
        assertThat(stock.amount.value).isEqualTo(amount);
        assertThat(stock.stockDescription.value).isEqualTo(stockDescription);
    }
}