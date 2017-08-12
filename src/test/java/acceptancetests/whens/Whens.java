package acceptancetests.whens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import httpclient.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static acceptancetests.AcceptanceTest.APPLICATION_NAME;
import static java.lang.String.format;

public class Whens {
    private static final String CALLER = "Shop User";
    private TestState testState;

    public Whens(TestState testState) {
        this.testState = testState;
    }

    public ActionUnderTest aGetRequestTo(String uri) {
        //TODO Add builder to build request
        // hydra/src/test/java/sky/sns/hydra/infrastructure/httpclient/TrackingApacheHttpClientFactoryTest.java:38
//        Request request = new RequestBuilder().url(uri).method("GET").body("").build();
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        Request domainRequest = Request.toNiceRequestForYatspec(request);
        capturedInputAndOutputs.add(format("Request from %s to %s",CALLER, APPLICATION_NAME), domainRequest);
        testState.add("request", domainRequest); //Need???
        System.out.println("request = " + request);
        CloseableHttpResponse execute = HttpClientBuilder.create().build().execute(request);
        httpclient.Response domainResponse = httpclient.Response.fromApacheResponse(execute);
        testState.add("response", domainResponse);
        capturedInputAndOutputs.add(format("Response from %s to %s", APPLICATION_NAME, CALLER), domainResponse);
        return capturedInputAndOutputs;
    }
}
