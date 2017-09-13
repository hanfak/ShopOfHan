package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.application.createproduct.AddProductUseCase;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.RenderedContent;

public class AddProductWebService {
    private AddProductUseCase addProductUseCase;

    public AddProductWebService(AddProductUseCase AddProductUseCase) {
        addProductUseCase = AddProductUseCase;
    }

    public RenderedContent addProduct(Product unmarshall) {
        return null;
    }
}
