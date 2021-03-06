package hanfak.shopofhan.infrastructure.web.createstock;

import hanfak.shopofhan.domain.AddStockRequest;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.StockAmount;
import hanfak.shopofhan.domain.stock.StockDescription;
import hanfak.shopofhan.domain.stock.StockId;
import hanfak.shopofhan.infrastructure.web.InputStreamReader;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

import static hanfak.shopofhan.domain.AddStockRequest.addStockRequest;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.stock.Stock.stock;
import static hanfak.shopofhan.domain.stock.StockAmount.stockAmount;
import static hanfak.shopofhan.domain.stock.StockDescription.stockDescription;
import static hanfak.shopofhan.domain.stock.StockId.stockId;

public class AddStockUnmarshaller implements Unmarshaller<AddStockRequest> {

    @Override
    public AddStockRequest unmarshall(HttpServletRequest request) throws IOException {
        JSONObject jsonObject = new JSONObject(InputStreamReader.readInputStream(request.getInputStream()));
        return getStockRequest(jsonObject);
    }

    private AddStockRequest getStockRequest(JSONObject jsonObject) {
        StockAmount amount = stockAmount(jsonObject.getInt("amount"));
        StockId stockId = stockId(jsonObject.getString("stockId"));
        StockDescription stockDescription = stockDescription(jsonObject.getString("stockDescription"));
        ProductId productId = productId(jsonObject.getString("productId"));
        return addStockRequest(stock(amount, stockId, stockDescription, productId), productId);
    }
}

