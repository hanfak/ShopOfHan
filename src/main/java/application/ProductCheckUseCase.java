package application;

import domain.ProductStock;
import domain.crosscutting.Request;
import infrastructure.web.productavailability.ProductAvailabilityRequest;

/**
 * Created by hanfak on 10/06/2017.
 */
public class ProductCheckUseCase {
    //Should this be void???

    // How to use interface instead of implementation
    public ProductStock checkStock(ProductAvailabilityRequest request) {
        if (request.productName.equals("Han")) {
            return new ProductStock(request.productName, 5);
        }
        else {
            return new ProductStock(request.productName, 0);
        }
    }
}
