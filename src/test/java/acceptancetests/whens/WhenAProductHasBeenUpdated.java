package acceptancetests.whens;

import acceptancetests.TestState;
import acceptancetests.WhenShopOfHanIsCalled;
import com.googlecode.yatspec.state.givenwhenthen.ActionUnderTest;
import httpclient.Request;
import httpclient.RequestBuilder;

import static httpclient.Header.APPLICATION_JSON;
import static httpclient.Header.CONTENT_TYPE_KEY;
import static java.lang.String.format;

public class WhenAProductHasBeenUpdated extends WhenShopOfHanIsCalled {

    private String productId;
    private String productDescription;
    private String productName = "Clojure the door";

    public WhenAProductHasBeenUpdated(TestState testState) {
        super(testState);
    }

    public ActionUnderTest isUpdated() {
        return (interestingGivens, capturedInputAndOutputs) -> whenWeMakeAPostRequestTo(capturedInputAndOutputs, buildRequest());
    }

    @Override
    protected Request buildRequest() {
        return new RequestBuilder()
                .url("http://localhost:8081/updateProduct")
                .header(CONTENT_TYPE_KEY, APPLICATION_JSON)
                .method("POST")
                .body(format("{\"productName\" : \"%s\", " +
                        "\"productId\" : \"%s\", " +
                        "\"productDescription\" : \"%s\"}", productName, productId, productDescription))
                .build();
    }

    public WhenAProductHasBeenUpdated withProductId(String productId) {
        this.productId = productId;
        return this;
    }

    public WhenAProductHasBeenUpdated withProductDescription(String productDescription) {
        this.productDescription = productDescription;
        return this;
    }

    public WhenAProductHasBeenUpdated withProductName(String productName) {
        this.productName = productName;
        return this;
    }
}
