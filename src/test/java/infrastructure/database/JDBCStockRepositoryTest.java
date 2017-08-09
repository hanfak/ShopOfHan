package infrastructure.database;

import domain.ProductStock;
import domain.product.ProductId;
import domain.product.ProductName;
import org.junit.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JDBCStockRepositoryTest  {

    private Logger logger = mock(Logger.class);
    private JDBCDatabaseConnectionManager databaseConnectionManager = mock(JDBCDatabaseConnectionManager.class);
    private JDBCStockRepository jdbcStockRepository = new JDBCStockRepository(logger, databaseConnectionManager);
    private Connection connection = mock(Connection.class);
    private PreparedStatement statement = mock(PreparedStatement.class);
    private ResultSet resultSet = mock(ResultSet.class);

    @Test
    public void returnProductById() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(connection);
        when(connection.prepareStatement("SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(ProductName.productName("A Product"), 5));
    }

    @Test
    public void returnProductByName() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(connection);
        when(connection.prepareStatement("SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockByName(ProductName.productName("A product name"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(ProductName.productName("A Product"), 5));
    }

}