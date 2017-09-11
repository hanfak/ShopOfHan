package testinfrastructure;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.ProductStockList;
import domain.product.Product;
import domain.product.ProductId;
import domain.product.ProductName;
import domain.stock.Stock;
import domain.stock.StockAmount;
import domain.stock.StockDescription;
import domain.stock.StockId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static domain.ProductStock.productStock;
import static domain.ProductStockList.productStockList;
import static domain.product.Product.product;
import static domain.product.ProductDescription.productDescription;
import static domain.product.ProductId.productId;
import static domain.product.ProductName.productName;
import static domain.stock.Stock.stock;
import static domain.stock.StockAmount.stockAmount;
import static domain.stock.StockId.stockId;
import static java.util.Arrays.asList;

public class TestStockRepository implements StockRepository {

    @SuppressWarnings("WeakerAccess")
    public TestStockRepository() {
        populateProductStockLists();
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return productStockLists.stream()
                .filter(productStockList -> productStockList.product.productName.equals(productName))
                .findFirst()
                .map(getProductStock());
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return productStockLists.stream()
                .filter(productStockList -> productStockList.product.productId.equals(productId))
                .findFirst()
                .map(getProductStock());
    }

    @Override
    public Optional<ProductStockList> findListOfProductStock(ProductId productId) {
        return productStockLists.stream()
                .filter(productStockList -> productStockList.product.productId.equals(productId))
                .findFirst();
    }

    private Function<ProductStockList, ProductStock> getProductStock() {
        return productStockList -> productStock(productStockList.product.productName, getAmountFromFirstProductStockList(productStockList));
    }

    private Integer getAmountFromFirstProductStockList(ProductStockList productStockList) {
        StockAmount amount = productStockList.stock.stream().findFirst().get().amount;
        return amount.value;
    }

    private void populateProductStockLists() {
        productStockLists.add(JOY_OF_JAVA);
        productStockLists.add(SQL_THE_SEQUEL);
    }

    private static final List<ProductStockList> productStockLists = new ArrayList<>();
    private static final List<Stock> JOY_OF_JAVA_STOCK = new ArrayList<>(asList(stock(stockAmount(4), stockId("STD1"), StockDescription.stockDescription("Single Pack"))));
    private static final Product  JOY_OF_JAVA_PRODUCT = product(productDescription("Book about java"), productId("JOJ1"), productName("Joy Of Java"));
    private static final ProductStockList JOY_OF_JAVA = productStockList(JOY_OF_JAVA_PRODUCT, JOY_OF_JAVA_STOCK);
    private static final List<Stock> SQL_THE_SEQUEL_STOCK = new ArrayList<>(asList(stock(stockAmount(0), stockId("STD1"), StockDescription.stockDescription("Single Pack")), stock(stockAmount(3), StockId.stockId("STD2"), StockDescription.stockDescription("Multi Pack"))));
    private static final Product  SQL_THE_SEQUEL_PRODUCT = product(productDescription("SQL the sequel"), productId("STS1"), productName("Book about SQL"));
    private static final ProductStockList SQL_THE_SEQUEL = productStockList(SQL_THE_SEQUEL_PRODUCT, SQL_THE_SEQUEL_STOCK);
}
