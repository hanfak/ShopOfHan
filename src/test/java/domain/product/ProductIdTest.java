package domain.product;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


public class ProductIdTest {
    @Test
    public void productIdMustBeLessThan10Characters() throws Exception {
        ProductId validProductId = ProductId.productId("abcdefgh");

        assertThat(validProductId).isNotInstanceOf(IllegalArgumentException.class);
        assertThat(validProductId).isInstanceOf(ProductId.class);
    }

    @Test
    public void productIdThrowsExceptionIfThereIsASpace() throws Exception {
        Throwable thrown = catchThrowable(() -> {  ProductId.productId("abc def"); });
        assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
    }
}