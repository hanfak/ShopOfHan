package hanfak.shopofhan.wiring;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.assertj.core.api.WithAssertions;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static hanfak.shopofhan.wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY_BY_ID;
import static hanfak.shopofhan.wiring.ShopOfHanURLs.PRODUCT_AVAILABILITY_BY_NAME;
import static hanfak.shopofhan.wiring.ShopOfHanURLs.PRODUCT_STOCK_CHECK_BY_ID;
import static hanfak.shopofhan.wiring.ShopOfHanURLs.STATUS_PAGE;
import static java.util.Arrays.stream;

@RunWith(TableRunner.class)
public class ShopOfHanURLsTest implements WithAssertions {
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME = "/produc" + "tscheck/n" + "ame/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID = "/produc" + "tsch" + "eck/i" + "d/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_STOCK_CHECK_BY_ID = "/fullProd" + "uctStockCheck/id/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE = "/stat" + "us";

    @Table({
            @Row({PRODUCT_AVAILABILITY_BY_NAME, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME}),
            @Row({PRODUCT_AVAILABILITY_BY_ID, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID}),
            @Row({PRODUCT_STOCK_CHECK_BY_ID, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_STOCK_CHECK_BY_ID}),
            @Row({STATUS_PAGE, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE})
    })
    @Test
    public void externalUrlsShouldNotChangeByAccident(String actual, String expected) {
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    @Ignore // TODO add test for url
    public void blowUpIfAddANewUrlToEnsureItsEndpointIsTestedHere() throws Exception {
        assertThat(cerberusEndpointUrls().size()).isEqualTo(ShopOfHanURLsTest.class.getDeclaredFields().length);
    }

    private List<Field> cerberusEndpointUrls() {
        return stream(ShopOfHanURLs.class.getDeclaredFields()).collect(Collectors.toList());
    }
}