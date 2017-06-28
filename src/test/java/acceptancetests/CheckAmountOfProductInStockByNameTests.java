package acceptancetests;

import com.googlecode.yatspec.junit.SpecResultListener;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.junit.WithCustomResultListeners;
import com.googlecode.yatspec.plugin.sequencediagram.ByNamingConventionMessageProducer;
import com.googlecode.yatspec.plugin.sequencediagram.SequenceDiagramGenerator;
import com.googlecode.yatspec.plugin.sequencediagram.SvgWrapper;
import com.googlecode.yatspec.rendering.html.DontHighlightRenderer;
import com.googlecode.yatspec.rendering.html.HtmlResultRenderer;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import httpclient.Request;
import httpclient.Response;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import wiring.ShopOfHan;

import java.io.IOException;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByNameTests extends TestState implements WithCustomResultListeners   {

    public static final String HARRY_POTTER = "Harry%20Potter";
    public static final String PATH = "http://localhost:8081/productscheck?productName=";
    public static final String JOY_OF_JAVA = "Joy%20Of%20Java";
    public static final String BAD_URL = "http://localhost:8081/a/bad/url";
    private ShopOfHan shopOfHan = new ShopOfHan();
    private Response domainResponse;

    // Dictionary

    @Before
    public void setUp() throws Exception {
        shopOfHan.startWebServer();// TODO Should this be public??
    }

    @After
    public void tearDown() throws Exception {
        shopOfHan.stopWebServer();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
    }

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

    //Need to show body of response and queries of request in diagrams
    private SvgWrapper generateSequenceDiagram() {
        return new SequenceDiagramGenerator().generateSequenceDiagram(new ByNamingConventionMessageProducer().messages(capturedInputAndOutputs));
    }

    @Override
    public Iterable<SpecResultListener> getResultListeners() throws Exception {
        return singletonList(new HtmlResultRenderer()
                .withCustomHeaderContent(SequenceDiagramGenerator.getHeaderContentForModalWindows())
                .withCustomRenderer(SvgWrapper.class, new DontHighlightRenderer<>()));
    }
}