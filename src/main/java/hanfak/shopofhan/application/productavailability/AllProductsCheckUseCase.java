package hanfak.shopofhan.application.productavailability;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;

import java.util.List;
import java.util.Optional;

public class AllProductsCheckUseCase {

    private final ProductRepository productRepository;

    public AllProductsCheckUseCase(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Optional<List<Product>> getAllProducts() {
        return productRepository.getAllProducts();
    }
}
