package testinfrastructure;

import application.crosscutting.StockRepository;
import domain.ProductStock;
import domain.product.ProductId;
import domain.product.ProductName;

import java.util.Optional;

public class TestStockRepository implements StockRepository{

    // TODO add a map of data to mimic database

    @Override
    public Optional<ProductStock> checkStockByName(ProductName productName) {
        return Optional.of(ProductStock.productStock(ProductName.productName("Joy Of Java"), 4));
    }

    @Override
    public Optional<ProductStock> checkStockById(ProductId productId) {
        return null;
    }
}
