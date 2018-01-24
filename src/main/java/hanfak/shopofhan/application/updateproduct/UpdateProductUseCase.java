package hanfak.shopofhan.application.updateproduct;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;
import org.slf4j.Logger;

public class UpdateProductUseCase {
    private final ProductRepository productRepository;
    private final Logger logger;

    public UpdateProductUseCase(ProductRepository productRepository, Logger logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    //TODO unit test and add log message
    public void changeProductDetails(Product product) {
        productRepository.updateProduct(product);
    }
}
