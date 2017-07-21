package acceptancetests.stockcheck;

import acceptancetests.AbstractAcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import httpclient.Response;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByNameTests extends AbstractAcceptanceTest {

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        when(weMake.aGetRequestTo(PATH + JOY_OF_JAVA));
        thenItReturnsAStatusCodeOf(200);
        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of Java\",\"amountInStock\": \"4\"}");
        andThenContentTypeIs("application/json");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMake.aGetRequestTo(PATH + HARRY_POTTER));
        thenTheResponseCodeIs404AndTheBodyIs("Product 'Harry Potter' is not stocked java.lang.IllegalStateException: Product is not found");
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMake.aGetRequestTo(BAD_URL));
        thenItReturnsAStatusCodeOf(404);
    }

    private GivensBuilder theSystemIsRunning() {
        testState().interestingGivens.add("productName", "Joy Of Java");
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
        StateExtractor<String> contentType = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getContentType();
        assertThat(contentType.execute(capturedInputAndOutputs)).contains(s);
    }


    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        StateExtractor<Integer> statusCode = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getStatusCode();
        assertThat(statusCode.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    private void andTheResponseBodyIs(String expected) throws Exception {
        StateExtractor<String> body = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getBody();
        String actual = body.execute(capturedInputAndOutputs);
        assertThat(actual).isEqualTo(expected);
    }

    private static final String HARRY_POTTER = "Harry%20Potter";
    private static final String PATH = "http://localhost:8081/productscheck/name/";
    private static final String JOY_OF_JAVA = "Joy%20Of%20Java";
    private static final String BAD_URL = "http://localhost:8081/a/bad/url";
}