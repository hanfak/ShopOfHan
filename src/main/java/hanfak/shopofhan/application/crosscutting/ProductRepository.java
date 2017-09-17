package hanfak.shopofhan.application.crosscutting;

import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;

import java.sql.SQLException;
import java.util.Optional;

public interface ProductRepository {
    Optional<Product> checkProductById(ProductId productId);

    void addProduct(Product product) throws SQLException;
}
