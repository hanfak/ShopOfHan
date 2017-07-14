package infrastructure.web.productavailability;

import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ProductAvailabilityUnmarshallerTest {
    HttpServletRequest request = mock(HttpServletRequest.class);

    @Test
    public void pathParamsshouldIgnoreEverythingAfterSecondForwardSlash() throws Exception {
        when(request.getPathInfo()).thenReturn("/hello/bye");
        ProductAvailabilityUnmarshaller productAvailabilityUnmarshaller = new ProductAvailabilityUnmarshaller();

        ProductAvailabilityRequest unmarshall = productAvailabilityUnmarshaller.unmarshall(request);

        assertThat(unmarshall).isEqualTo(ProductAvailabilityRequest.productAvailabilityRequest("hello"));
    }

}