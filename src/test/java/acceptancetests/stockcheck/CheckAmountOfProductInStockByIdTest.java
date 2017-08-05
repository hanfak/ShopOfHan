package acceptancetests.stockcheck;

import acceptancetests.AbstractAcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public class CheckAmountOfProductInStockByIdTest extends AbstractAcceptanceTest {

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        when(weMake.aGetRequestTo(PATH + JOY_OF_JAVA_ID));
        thenTheResponseCodeIs200AndTheBodyIs("{\"productName\": \"Joy Of Java\",\"amountInStock\": \"4\"}");
//        thenTheResponseCodeIs200AndTheBodyIs(EXPECTED_RESPONSE);
        andThenContentTypeIs("application/json");
    }

    @Test
    public void shouldReturnItemNotStocked() throws Exception {
        when(weMake.aGetRequestTo(PATH + HARRY_POTTER));
        thenTheResponseCodeIs404AndTheBodyIs("Product 'HP1' is not stocked java.lang.IllegalStateException: Product is not found");
        andThenContentTypeIs("Content-Type: text/plain;charset=iso-8859-1");
    }

    @Test
    @Ignore
    public void shouldReturnMultipleStockTypesForOneProduct() throws Exception {
        given(theSystemIsRunning());
        when(weMake.aGetRequestTo(PATH + JOY_OF_JAVA_ID));
    }

    private GivensBuilder theSystemIsRunning() {
        testState().interestingGivens.add("productName","Joy Of Java");
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
        the.contentType(s);
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        the.statusCode(expected);
    }

    private void andTheResponseBodyIs(String expected) throws Exception {
        the.body(expected);
    }

    private static final String HARRY_POTTER = "HP1";
    private static final String PATH = "http://localhost:8082/productscheck/id/";
    private static final String JOY_OF_JAVA_ID = "JOJ1";
    private static final String EXPECTED_RESPONSE =
            "{\"productName\": \"Joy Of Java\"," +
                    "\"productId\": \"JOJ1\"," +
                    "\"productDescription\": \"blah blah\"," +
                    "\"stock\":" +
                    "[" +
                    "{" +
                    "\"stockId\": \"1\"," +
                    "\"amountInStock\": \"4\"," +
                    "\"stockDescription\": \"blah\"" +
                    "}" +
                    "]}";
}