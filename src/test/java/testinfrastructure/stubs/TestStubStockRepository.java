package testinfrastructure.stubs;

import hanfak.shopofhan.application.crosscutting.StockRepository;
import hanfak.shopofhan.domain.ProductStock;
import hanfak.shopofhan.domain.ProductStockList;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.product.ProductName;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.domain.stock.StockAmount;
import hanfak.shopofhan.domain.stock.StockId;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static hanfak.shopofhan.domain.ProductStock.productStock;
import static hanfak.shopofhan.domain.ProductStockList.productStockList;
import static hanfak.shopofhan.domain.product.Product.product;
import static hanfak.shopofhan.domain.product.ProductDescription.productDescription;
import static hanfak.shopofhan.domain.product.ProductId.productId;
import static hanfak.shopofhan.domain.product.ProductName.productName;
import static hanfak.shopofhan.domain.stock.Stock.stock;
import static hanfak.shopofhan.domain.stock.StockAmount.stockAmount;
import static hanfak.shopofhan.domain.stock.StockDescription.stockDescription;
import static hanfak.shopofhan.domain.stock.StockId.stockId;
import static java.util.Arrays.asList;

public class TestStubStockRepository implements StockRepository {

    @SuppressWarnings("WeakerAccess")
    public TestStubStockRepository() {
        populateProductStockLists();
    }

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        System.out.println("Before  " + productStockLists);
        Optional<ProductStock> productStock = productStockLists.stream()
                .filter(productStockList -> productStockList.product.productName.equals(productName))
                .findFirst()
                .map(getProductStock());
        return productStock;
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return productStockLists.stream()
                .peek(productStockList -> System.out.println("blllll" + productStockList.product.productId))
                .filter(productStockList -> productStockList.product.productId.equals(productId))
                .findFirst()
                .map(getProductStock());
    }

    @Override
    public void addStock(Stock stock) {
        System.out.println(stockList);
        stockList.add(stock);
        System.out.println("changed  " + stockList);
    }

    // TODO create a real one of these
    public Stock checkStock(String stockId, String productId) {
        return stockList.stream()
                .filter(stock -> stock.stockId.value.equals(stockId) && stock.productId.value.equals(productId)).findFirst().get();
    }


    private Function<ProductStockList, ProductStock> getProductStock() {
        return productStockList -> productStock(productStockList.product.productName, getAmountFromFirstProductStockList(productStockList));
    }

    private Integer getAmountFromFirstProductStockList(ProductStockList productStockList) {
        StockAmount amount = productStockList.stock.stream().findFirst().get().amount;
        return amount.value;
    }

    private void populateProductStockLists() {
        // TODO remove when know how to get working
        productStockLists.add(JOY_OF_JAVA);
        productStockLists.add(SQL_THE_SEQUEL);
    }

    public static final List<ProductStockList> productStockLists = new ArrayList<>();
    private static final List<Stock> JOY_OF_JAVA_STOCK = new ArrayList<>(asList(stock(stockAmount(4), stockId("STD1"), stockDescription("Single Pack"), productId("JOJ1"))));
    private static final Product  JOY_OF_JAVA_PRODUCT = product(productDescription("Book about java"), productId("JOJ1"), productName("Joy Of Java"));
    private static final ProductStockList JOY_OF_JAVA = productStockList(JOY_OF_JAVA_PRODUCT, JOY_OF_JAVA_STOCK);
    private static final List<Stock> SQL_THE_SEQUEL_STOCK = new ArrayList<>(asList(stock(stockAmount(0), stockId("STD1"), stockDescription("Single Pack"), productId("STS1")), stock(stockAmount(3), StockId.stockId("STD2"), stockDescription("Multi Pack"),productId("STS1"))));
    private static final Product  SQL_THE_SEQUEL_PRODUCT = product(productDescription("SQL then sequel"), productId("STS1"), productName("Book about SQL"));
    private static final ProductStockList SQL_THE_SEQUEL = productStockList(SQL_THE_SEQUEL_PRODUCT, SQL_THE_SEQUEL_STOCK);


    private static final List<Stock> stockList = new ArrayList<>();

    public void addToProductStockList(Optional<Product> product) {
        product.ifPresent(product1 -> productStockLists.add(productStockList(product1, stockList)));
    }

    public void remove() {
        productStockLists.clear();
    }

    public List<ProductStockList> showAll() {
        return productStockLists;
    }
}
