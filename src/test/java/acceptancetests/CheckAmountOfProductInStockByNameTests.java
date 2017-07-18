package acceptancetests;

import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import httpclient.Request;
import httpclient.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static java.lang.String.format;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByNameTests extends AbstractAcceptanceTest  {

    public static final String HARRY_POTTER = "Harry%20Potter";
    public static final String PATH = "http://localhost:8081/productscheck/name/";
    public static final String JOY_OF_JAVA = "Joy%20Of%20Java";
    public static final String BAD_URL = "http://localhost:8081/a/bad/url";
    private Response domainResponse;

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        when(weMakeAGetRequestTo(PATH + JOY_OF_JAVA));
        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of Java\",\"amountInStock\": \"4\"}");
        andThenContentTypeIs("application/json");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMakeAGetRequestTo(PATH + HARRY_POTTER));
        thenTheResponseCodeIs404AndTheBodyIs("Product 'Harry Potter' is not stocked java.lang.IllegalStateException: Product is not found");
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMakeAGetRequestTo(BAD_URL));
        thenItReturnsAStatusCodeOf(404);
    }

    private GivensBuilder theSystemIsRunning() {
        testState().interestingGivens.add("productName","Joy Of Java");
        return givens -> givens;
    }

    private void andThenContentTypeIs(String s) {
//        assertThat(domainResponse.getContentType()).isEqualTo(s);
        assertThat(domainResponse.getContentType()).contains(s);
    }

    private void thenItReturnsAStatusCodeOf(int expected) {
        assertThat(domainResponse.getStatusCode()).isEqualTo(expected);
    }

    private ActionUnderTest weMakeAGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }
    //TODO move to a when class
    @SuppressWarnings("Duplicates")
    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        Request instance = Request.toNiceRequestForYatspec(request);
        capturedInputAndOutputs.add(format("Request from %s to %s", "client", "ShopOfHan"), instance);

        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        domainResponse = Response.fromApacheResponse(response);
        capturedInputAndOutputs.add(format("Response from %s to %s", "ShopOfHan", "client"), domainResponse);
        return capturedInputAndOutputs;
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(200);
        assertThat(domainResponse.getBody()).isEqualTo(expected);
    }

    private void thenTheResponseCodeIs404AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(404);
        assertThat(domainResponse.getBody()).isEqualTo(expected);
    }
}