package documentationtests;

import application.ProductCheckUseCase;
import com.googlecode.yatspec.junit.SpecRunner;
import domain.ProductStock;
import domain.crosscutting.StockRepository;
import infrastructure.web.productavailability.ProductAvailabilityRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.ThrowableAssert.catchThrowable;
import static org.mockito.Mockito.*;

@RunWith(SpecRunner.class)
public class ProductAvailabilityRequestCheckTest {
    @Test
    public void shouldReturnStockAmountForItem() throws Exception {
        givenStockRepositoryContainsTheProduct(LORD_OF_THE_RINGS, withStock(5));
        whenCheckingStockOfProduct(requestOne);
//        thenLogThatTheProductWasCheckedAndFound();
        andTheProducHasAmountOfStockOf(5, forProduct(LORD_OF_THE_RINGS));
    }

    @Test
    public void shouldReturnZeroStockAmountForItem() throws Exception {
        givenStockRepositoryContainsTheProduct(CATCH_22, withStock(0));
        whenCheckingStockOfProduct(requestTwo);
        //TODO is called twice, how to clear invocation of log,
        // Should I use real logger and assert?
//        thenLogThatTheProductWasCheckedAndFound();
        andTheProducHasAmountOfStockOf(0, forProduct(CATCH_22));
    }

    @Test
    public void shouldThrowErrorIfProductIsNotStocker() {
        givenSTockRepositoryDoesNotContainTheProduct(FIFTY_SHADES);
//        whenCheckingStockOfProduct(FIFTY_SHADES);
        // TODO find a better way of doing this
        // TODO Should I be testing that exception was thrown??
        Throwable thrown = catchThrowable(() -> { whenCheckingStockOfProduct(requestThree); });
        assertThat(thrown).isInstanceOf(IllegalStateException.class)
                            .hasMessage("Product is not found");
        thenLogThatTheProductWasNotFound();
//        andAnErrorIsThrown();
    }

    private void andAnErrorIsThrown() {
//        assertThatThrownBy().hasMessage("Product is not found");
    }

    private void givenSTockRepositoryDoesNotContainTheProduct(String product) {
        when(stockRepository.checkStock(product)).thenReturn(Optional.empty());
    }

    private Integer withStock(int stock) {
        return stock;
    }

    private String forProduct(String product) {
        return product;
    }

    private void andTheProducHasAmountOfStockOf(int expectedAmount, String product) {
        assertThat(checkedStock.productName).isEqualTo(product);
        assertThat(checkedStock.amountInStock).isEqualTo(expectedAmount);
    }

    private void thenLogThatTheProductWasCheckedAndFound() {
        verify(logger).info("checking stock...");
        verify(logger).info("Stock is there");
    }

    private void thenLogThatTheProductWasNotFound() {
        verify(logger).warn("Stock not there");
    }

    private void whenCheckingStockOfProduct(ProductAvailabilityRequest product) {
        checkedStock = productCheckUseCase.checkStock(product);
    }

    private void givenStockRepositoryContainsTheProduct(String product, Integer amount) {
        when(stockRepository.checkStock(product)).thenReturn(Optional.of(ProductStock.productStock(product, amount)));
    }

    // TODO should be static and final?/
    private static final StockRepository stockRepository = mock(StockRepository.class);
    private static final Logger logger = mock(Logger.class); // TODO static call cannot do this
    private static final String LORD_OF_THE_RINGS = "Lord Of The Rings"; // TODO should I use String instead
    private static final String CATCH_22 = "Catch-22";
    private static final String FIFTY_SHADES = "50 Shades";
    private static final ProductAvailabilityRequest requestOne = ProductAvailabilityRequest.productAvailabilityRequest(LORD_OF_THE_RINGS);
    private static final ProductAvailabilityRequest requestTwo = ProductAvailabilityRequest.productAvailabilityRequest(CATCH_22);
    private static final ProductAvailabilityRequest requestThree = ProductAvailabilityRequest.productAvailabilityRequest(FIFTY_SHADES);
    private ProductCheckUseCase productCheckUseCase = new ProductCheckUseCase(stockRepository, logger);
    private ProductStock checkedStock;
}

//@After
//public void teardown() {
//    logger.
//}
