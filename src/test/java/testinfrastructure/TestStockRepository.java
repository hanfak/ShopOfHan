package testinfrastructure;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.ProductStockList;
import domain.Stock;
import domain.product.ProductId;
import domain.product.ProductName;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static domain.ProductStock.productStock;
import static domain.ProductStockList.productStockList;
import static domain.Stock.stock;
import static domain.product.ProductDescription.productDescription;
import static domain.product.ProductId.productId;
import static domain.product.ProductName.productName;
import static java.util.Arrays.asList;

public class TestStockRepository implements StockRepository {

    @SuppressWarnings("WeakerAccess")
    public TestStockRepository() {
        populateProductStockLists();
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return productStockLists.stream()
                .filter(product -> product.productName.equals(productName))
                .findFirst()
                .map(getProductStock());
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return productStockLists.stream()
                .filter(product -> product.productId.equals(productId))
                .findFirst()
                .map(getProductStock());
    }

    @Override
    public Optional<ProductStockList> findListOfProductStock(ProductId productId) {
        return productStockLists.stream()
                .filter(product -> product.productId.equals(productId))
                .findFirst();
    }

    private Function<ProductStockList, ProductStock> getProductStock() {
        return productStockList -> productStock(productStockList.productName, getAmountFromFirstProductStockList(productStockList));
    }

    private Integer getAmountFromFirstProductStockList(ProductStockList productStockList) {
        return productStockList.stock.stream().findFirst().get().amount;
    }

    private void populateProductStockLists() {
        productStockLists.add(JOY_OF_JAVA);
        productStockLists.add(SQL_THE_SEQUEL);
    }

    private static final List<ProductStockList> productStockLists = new ArrayList<>();
    private static final List<Stock> JOY_OF_JAVA_STOCK = new ArrayList<>(asList(stock(4, "STD1", "Single Pack")));
    private static final ProductStockList JOY_OF_JAVA = productStockList(productName("Joy Of Java"), productId("JOJ1"), productDescription("Book about java"), JOY_OF_JAVA_STOCK);
    private static final List<Stock> SQL_THE_SEQUEL_STOCK = new ArrayList<>(asList(stock(0, "STD1", "Single Pack"), stock(3, "STD2", "Multi Pack")));
    private static final ProductStockList SQL_THE_SEQUEL = productStockList(productName("SQL the sequel"), productId("STS1"), productDescription("Book about SQL"), SQL_THE_SEQUEL_STOCK);
}
