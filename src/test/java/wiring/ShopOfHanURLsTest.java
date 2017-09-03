package wiring;

import com.googlecode.yatspec.junit.Row;
import com.googlecode.yatspec.junit.Table;
import com.googlecode.yatspec.junit.TableRunner;
import org.assertj.core.api.WithAssertions;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;
import static wiring.ShopOfHanURLs.*;

@RunWith(TableRunner.class)
public class ShopOfHanURLsTest implements WithAssertions {
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME = "/produc" + "tscheck/n" + "ame/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID = "/produc" + "tsch" + "eck/i" + "d/*";
    private static final String INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE = "/stat" + "us";

    @Table({
            @Row({PRODUCT_AVAILABILITY_BY_NAME, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_NAME}),
            @Row({PRODUCT_AVAILABILITY_BY_ID, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_PRODUCT_AVAILABILITY_BY_ID}),
            @Row({STATUS_PAGE, INTENTIONALLY_SPLIT_UP_TO_AVOID_REFACTORING_STATUS_PAGE})
    })
    @Test
    public void externalUrlsShouldNotChangeByAccident(String actual, String expected) {
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    public void blowUpIfAddANewUrlToEnsureItsEndpointIsTestedHere() throws Exception {
        assertThat(cerberusEndpointUrls().size()).isEqualTo(ShopOfHanURLsTest.class.getDeclaredFields().length);
    }

    private List<Field> cerberusEndpointUrls() {
        return stream(ShopOfHanURLs.class.getDeclaredFields()).collect(Collectors.toList());
    }
}