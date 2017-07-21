package acceptancetests.thens;

import acceptancetests.TestState;
import com.googlecode.yatspec.state.givenwhenthen.CapturedInputAndOutputs;
import com.googlecode.yatspec.state.givenwhenthen.StateExtractor;
import httpclient.Response;

import static org.assertj.core.api.Assertions.assertThat;

//import org.apache.http.HttpResponse;

public class Thens {

    private TestState testState;
    private final CapturedInputAndOutputs capturedInputAndOutputs;

    public Thens(TestState testState, CapturedInputAndOutputs capturedInputAndOutputs) {
        this.testState = testState;
        this.capturedInputAndOutputs = capturedInputAndOutputs;
    }

    public void statusCode(int expected) throws Exception {
        StateExtractor<Integer> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getStatusCode();
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    public void body(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getBody();
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).isEqualTo(expected);
    }

    public void contentType(String expected) throws Exception {
        StateExtractor<String> contentTypeExtractor = capturedInputAndOutputs ->
                ((Response) testState.get("response")).getContentType();
        assertThat(contentTypeExtractor.execute(capturedInputAndOutputs)).contains(expected);
    }
}