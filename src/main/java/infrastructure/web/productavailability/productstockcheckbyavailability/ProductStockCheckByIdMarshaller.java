package infrastructure.web.productavailability.productstockcheckbyavailability;

import domain.ProductStockList;
import domain.Stock;
import infrastructure.web.Marshaller;

import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class ProductStockCheckByIdMarshaller implements Marshaller<ProductStockList> {
    private static final String EXPECTED_BODY_FORMAT =
            "{\"productName\": \"%s\"," +
                    "\"productId\": \"%s\"," +
                    "\"productDescription\": \"%s\"," +
                    "\"stock\":" +
                    "[" +
                    "%s" +
                    "]}";

    private static final String EXPECTED_STOCK_FORMAT = "{\"stockId\": \"%s\"," +
            "\"amountInStock\": \"%s\"," +
            "\"stockDescription\": \"%s\"" +
            "}";

    @Override
    public String marshall(ProductStockList productStockList) {
        return toJson(productStockList);
    }

    private String toJson(ProductStockList productStockList) {
        return format(EXPECTED_BODY_FORMAT, productStockList.productName, productStockList.productId, productStockList.productDescription, listStockfill(productStockList.stock));
    }

    private String listStockfill(List<Stock> stock) {
        return stock.stream()
                .map(aStock -> format(EXPECTED_STOCK_FORMAT, aStock.stockId, aStock.amount, aStock.stockDescription))
                .collect(Collectors.joining(","));
    }
}
