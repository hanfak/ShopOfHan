package testinfrastructure.testrepositories;

import hanfak.shopofhan.domain.product.ProductId;
import hanfak.shopofhan.domain.stock.Stock;
import hanfak.shopofhan.domain.stock.StockAmount;
import hanfak.shopofhan.domain.stock.StockDescription;
import hanfak.shopofhan.domain.stock.StockId;
import hanfak.shopofhan.infrastructure.database.jdbc.helperlibrary.*;
import hanfak.shopofhan.infrastructure.database.jdbc.repositories.JDBCStockRepository;
import org.slf4j.Logger;

import java.sql.SQLException;
import java.util.Optional;

public class TestStockRepository extends JDBCStockRepository {
    private static final String SQL_STATEMENT = "SELECT amount, stock_id, stock_description, product_id FROM stock WHERE stock_id=? and product_id=?";

    private JdbcRecordReader<ProductStockId, Stock> checkStockByProductIdAndStockIdReader;

    public TestStockRepository(Logger logger, JdbcRecordReaderFactory jdbcRecordReaderFactory, JdbcWriterFactory jdbcWriterFactory) {
        super(logger, jdbcRecordReaderFactory, jdbcWriterFactory);
        this.checkStockByProductIdAndStockIdReader = jdbcRecordReaderFactory.create(SQL_STATEMENT, this::extractResultSet2, this::blah, Stock.class);
    }


    protected Stock extractResultSet2(EnhancedResultSet enhancedResultSet) throws SQLException {
        return Stock.stock(
                StockAmount.stockAmount(enhancedResultSet.getInt("amount")),
                StockId.stockId(enhancedResultSet.getString("stock_id")),
                StockDescription.stockDescription(enhancedResultSet.getString("stock_description")),
                ProductId.productId(enhancedResultSet.getString("product_id")))
                ;
    }


    private void blah(EnhancedPreparedStatement enhancedPreparedStatement, ProductStockId productStockId) throws SQLException {
        enhancedPreparedStatement.setString(productStockId.stockId.value);
        enhancedPreparedStatement.setString(productStockId.productId.value);
    }

    public Stock checkStock(String stockId, String productId) {
        Optional<Stock> stock = checkStockByProductIdAndStockIdReader.readRecord(ProductStockId.productStockId(StockId.stockId(stockId), ProductId.productId(productId)));
        System.out.println(stock.get());
        return stock.get();
    }
}
