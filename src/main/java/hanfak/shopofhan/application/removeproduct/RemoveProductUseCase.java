package hanfak.shopofhan.application.removeproduct;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.ProductId;
import org.slf4j.Logger;

public class RemoveProductUseCase {
    private final Logger logger;
    private final ProductRepository productRepository;

    public RemoveProductUseCase(Logger logger, ProductRepository productRepository) {
        this.logger = logger;
        this.productRepository = productRepository;
    }

    public void removeProduct(ProductId productid) {
        productRepository.removeProduct(productid);
    }
}
