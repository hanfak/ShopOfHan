package acceptancetests.stockcheck;

import acceptancetests.AcceptanceTest;
import com.googlecode.yatspec.junit.SpecRunner;
import com.googlecode.yatspec.state.givenwhenthen.GivensBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(SpecRunner.class)
public class AddProductTest extends AcceptanceTest {

    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        given(theSystemIsRunning());
        when(whenAPostIsMadeToShopOfHanToCreateANewProduct.aPostRequestTo(PATH));
        // TODO show key parts of JSON ie name, description and id
        thenItReturnsAStatusCodeOf(200);
        // TODO asssert on body stating what was added
        // TODO assert on database contain new data
    }


    // SAD path
    // techinical failure

    private GivensBuilder theSystemIsRunning() {
//        testState().interestingGivens.add("productName", "Joy Of Java");
        // TODO add parts of products to interestinggivens
        return givens -> givens;
    }

    private void thenItReturnsAStatusCodeOf(int expected) throws Exception {
        the.statusCode(expected);
    }

    private static final String PATH = "http://localhost:8081/products";
}