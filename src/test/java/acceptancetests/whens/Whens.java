package acceptancetests.whens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import httpclient.Request;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

import java.io.IOException;

import static acceptancetests.AbstractAcceptanceTest.APPLICATION_NAME;
import static java.lang.String.format;

public class Whens {
    private static final String CALLER = "Shop User";
    private TestState testState;

    public Whens(TestState testState) {
        this.testState = testState;
    }

    public ActionUnderTest aGetRequestTo(String uri) {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, new HttpGet(uri));
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, HttpGet request) throws IOException {
        Request instance = Request.toNiceRequestForYatspec(request);
        capturedInputAndOutputs.add(format("Request from %s to %s",CALLER, APPLICATION_NAME), instance);
        HttpResponse response = HttpClientBuilder.create().build().execute(request);
        httpclient.Response domainResponse = httpclient.Response.fromApacheResponse(response);
        testState.add("response", domainResponse);
        capturedInputAndOutputs.add(format("Response from %s to %s", APPLICATION_NAME, CALLER), domainResponse);
        return capturedInputAndOutputs;
    }
}
