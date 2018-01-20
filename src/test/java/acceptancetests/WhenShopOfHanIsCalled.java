package acceptancetests;

import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import httpclient.Header;
import httpclient.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static acceptancetests.AcceptanceTest.APPLICATION_NAME;
import static java.lang.String.format;

public abstract class WhenShopOfHanIsCalled {
    private static final String CALLER = "Shop User";

    private TestState testState;

    public WhenShopOfHanIsCalled(TestState testState) {
        this.testState = testState;
    }

    protected CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, Request request) throws IOException {
        capturedInputAndOutputs.add(format("Request from %s to %s",CALLER, APPLICATION_NAME), request);
        testState.add("request", request.body); //Need???
        System.out.println(request.toString());
        HttpPost httpPost = setPostRequest(request);
        CloseableHttpResponse execute = HttpClientBuilder.create().build().execute(httpPost);
        httpclient.Response domainResponse = httpclient.Response.fromApacheResponse(execute);
        testState.add("response", domainResponse);
        capturedInputAndOutputs.add(format("Response from %s to %s", APPLICATION_NAME, CALLER), domainResponse);
        return capturedInputAndOutputs;
    }

    private HttpPost setPostRequest(Request request) throws UnsupportedEncodingException {
        HttpPost httpPost = new HttpPost(request.url);
        httpPost.setEntity(new StringEntity(request.body));
        httpPost.setHeader(new BasicHeader(Header.CONTENT_TYPE_KEY, Header.APPLICATION_JSON));
        return httpPost;
    }
}
