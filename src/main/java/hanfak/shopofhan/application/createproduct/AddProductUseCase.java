package hanfak.shopofhan.application.createproduct;

import hanfak.shopofhan.application.crosscutting.ProductRepository;
import hanfak.shopofhan.domain.product.Product;
import org.slf4j.Logger;

import java.sql.SQLException;

public class AddProductUseCase {
    private ProductRepository productRepository;
    private Logger logger;

    public AddProductUseCase(ProductRepository productRepository, Logger logger) {
        this.productRepository = productRepository;
        this.logger = logger;
    }

    public void addProduct(Product product) throws SQLException {
        // TODO test this check
//        Optional<Product> exisitingProduct = productRepository.checkProductById(product.productId);
//        if (exisitingProduct.isPresent()) {
//            String message = format("Product with id, '%s', has not been added, as it already exists.", exisitingProduct.get().productId);
//            logger.error(message);
//            throw new IllegalStateException(message);
//        }
        // Add product to repository

        productRepository.addProduct(product);
        // Return product
    }

}
