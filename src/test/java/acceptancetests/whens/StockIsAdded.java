package acceptancetests.whens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import httpclient.Header;
import httpclient.Request;
import httpclient.RequestBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicHeader;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static acceptancetests.AcceptanceTest.APPLICATION_NAME;
import static httpclient.Header.APPLICATION_JSON;
import static httpclient.Header.CONTENT_TYPE_KEY;
import static java.lang.String.format;

public class StockIsAdded {
    private static final String CALLER = "Shop User";
    private TestState testState;

    public StockIsAdded(TestState testState) {
        this.testState = testState;
    }

    public ActionUnderTest throughARequestTo(String uri, String productId, String stockId, String stockDescription, long amount) {
        //TODO Add builder to build request
        // hydra/src/test/java/sky/sns/hydra/hanfak.shopofhan.infrastructure/httpclient/TrackingApacheHttpClientFactoryTest.java:38
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeARequestTo(capturedInputAndOutputs, buildRequest(uri, productId, stockId, stockDescription, amount));
    }

    private Request buildRequest(String uri, String productId, String stockId, String stockDescription, long amount) {
        return new RequestBuilder()
                .url(uri)
                .header(CONTENT_TYPE_KEY, APPLICATION_JSON)
                .method("POST")
                .body(format("{" +
                        "\"productId\" : \"%s\", " +
                        "\"stockId\" : \"%s\", " +
                        "\"stockDescription\" : \"%s\", " +
                        "\"amount\" : %s}", productId, stockId, stockDescription, amount))
                .build();
    }

    private CapturedInputAndOutputs whenWeMakeARequestTo(CapturedInputAndOutputs capturedInputAndOutputs, Request request) throws IOException {
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
