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
import com.googlecode.yatspec.state.givenwhenthen.TestState;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
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
public class AcceptanceTests extends TestState implements WithCustomResultListeners   {

    private ShopOfHan shopOfHan = new ShopOfHan();
    private HttpResponse response;
    private String responseBody;
    // Need to show Interesting givens
    // Dictionary

    // Log4j is showing jetty in tests out, remove this
    @Before
    public void setUp() throws Exception {
        shopOfHan.startWebServer();//Should this be public??
    }

    @After
    public void tearDown() throws Exception {
        shopOfHan.stopWebServer();
        capturedInputAndOutputs.add("Sequence Diagram", generateSequenceDiagram());
    }

    // Need to test content type
    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8081/productscheck?productName=Joy%20Of%20Java"));
        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of java\",\"amountInStock\": \"4\"}");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8081/productscheck?productName=Harry%20Potter"));
        thenTheResponseCodeIs404AndTheBodyIs("Product 'Harry Potter' is not stocked java.lang.IllegalStateException: Product is not found");
    }

    @Test
    public void shouldFail() throws Exception {
        when(weMakeAGetRequestTo("http://localhost:8081/a/bad/url"));
        thenItReturnsAStatusCodeOf(404);
    }

    private void thenItReturnsAStatusCodeOf(int expected) {
        assertThat(response.getStatusLine().getStatusCode()).isEqualTo(expected);
    }

    private ActionUnderTest weMakeAGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s", "client", "ShopOfHan"), request);
        response = HttpClientBuilder.create().build().execute(request);
        responseBody = EntityUtils.toString(response.getEntity());
        capturedInputAndOutputs.add(format("Response from %s to %s", "ShopOfHan", "client"), response.getStatusLine().toString());
        return capturedInputAndOutputs;
    }

    private void thenTheResponseCodeIs200AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(200);
        assertThat(responseBody).isEqualTo(expected);
    }

    private void thenTheResponseCodeIs404AndTheBodyIs(String expected) throws IOException {
        thenItReturnsAStatusCodeOf(404);
        assertThat(responseBody).isEqualTo(expected);
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