package infrastructure.web.productavailability;

import infrastructure.web.JsonUnmarshaller;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ProductAvailabilityUnmarshaller implements JsonUnmarshaller<ProductAvailabilityRequest> {

    @Override
    public ProductAvailabilityRequest unmarshall(HttpServletRequest request) throws IOException {
        String requestBody = stringifyBody(request);
        System.out.println(format("body from request = %s\n", requestBody));

        JSONObject jsonObject = new JSONObject(requestBody);
        System.out.println(format("title from request = %s\n", jsonObject.getString("productName")));

        return new ProductAvailabilityRequest(
                jsonObject.getString("productName"));
    }

    private static String stringifyBody(HttpServletRequest request) throws IOException {
        return request.getReader()
                .lines()
                .collect(Collectors.joining(System.lineSeparator()));
    }

}
