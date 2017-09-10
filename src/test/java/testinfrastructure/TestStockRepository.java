package testinfrastructure;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.ProductStockList;
import domain.product.ProductId;
import domain.product.ProductName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static domain.ProductStock.productStock;
import static domain.product.ProductId.productId;
import static domain.product.ProductName.productName;

public class TestStockRepository implements StockRepository {

    @SuppressWarnings("WeakerAccess")
    public TestStockRepository() {
        populateStock();
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return stock.stream()
                .filter(stock -> getProductName(stock).equals(productName))
                .findFirst()
                .map(getStockProductStockFunction());
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return stock.stream()
                .filter(stock -> getProductId(stock).equals(productId))
                .findFirst()
                .map(getStockProductStockFunction());
    }

    @Override
    public Optional<ProductStockList> blah(ProductId productId) {
        return null;
    }

    private void populateStock() {
        stock.add(JOY_OF_JAVA);
        stock.add(SQL_THE_SEQUEL);
    }

    private Function<Stock, ProductStock> getStockProductStockFunction() {
        return requiredStock -> productStock(getProductName(requiredStock), requiredStock.amount);
    }

    private ProductId getProductId(Stock stock) {
        return stock.product.productId;
    }

    private ProductName getProductName(Stock requiredStock) {
        return requiredStock.product.productName;
    }

    private static final List<ProductStock> productStocks = new ArrayList<>();
    private static final List<Stock> stock = new ArrayList<>();
    private static final Stock JOY_OF_JAVA = new Stock(new Product(productId("JOJ1"), productName("Joy Of Java")), 4);
    private static final Stock SQL_THE_SEQUEL = new Stock(new Product(productId("STS1"), productName("SQL the sequel")), 0);

    static class Stock {
        Product product;
        Integer amount;

        Stock(Product product, Integer amount) {
            this.product = product;
            this.amount = amount;
        }
    }

    static class Product {
        ProductId productId;
        ProductName productName;

        Product(ProductId productId, ProductName productName) {
            this.productId = productId;
            this.productName = productName;
        }
    }
}
