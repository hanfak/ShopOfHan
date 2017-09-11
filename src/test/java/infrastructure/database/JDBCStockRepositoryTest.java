package infrastructure.database;

import domain.ProductStock;
import domain.product.ProductId;
import infrastructure.database.jdbc.JDBCDatabaseConnectionManager;
import infrastructure.database.jdbc.JDBCStockRepository;
import org.junit.Test;
import org.slf4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;

import static domain.product.ProductName.productName;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class JDBCStockRepositoryTest {

    @Test
    public void returnProductById() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(productName("A Product"), 5));
    }

    @Test
    public void returnNoProductByIdIfNothingInTheDatabase() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product.product_id=?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(false);

        Optional<ProductStock> result = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        assertThat(result).isEqualTo(Optional.empty());
    }

    @Test
    public void returnProductByName() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("SELECT product.product_name, stock.amount FROM product INNER JOIN stock ON stock.product_id = product.id WHERE product_name=?")).thenReturn(statement);
        when(statement.executeQuery()).thenReturn(resultSet);
        when(resultSet.getString("product_name")).thenReturn("A Product");
        when(resultSet.getInt("amount")).thenReturn(5);
        when(resultSet.next()).thenReturn(true);

        Optional<ProductStock> result = jdbcStockRepository.checkStockByName(productName("A product name"));

        assertThat(result.get()).isEqualTo(ProductStock.productStock(productName("A Product"), 5));
    }

    @Test
    public void throwDatabaseErrorForName() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("sql statement")).thenThrow(new RuntimeException());


        Optional<ProductStock> productStock = jdbcStockRepository.checkStockByName(productName("A product name"));

        verify(logger).error("error java.lang.NullPointerException");
    }

    @Test
    public void throwDatabaseErrorForId() throws Exception {
        when(databaseConnectionManager.getDBConnection()).thenReturn(Optional.of(connection));
        when(connection.prepareStatement("sql statement")).thenThrow(new RuntimeException());


        Optional<ProductStock> productStock = jdbcStockRepository.checkStockById(ProductId.productId("abc1"));

        verify(logger).error("error java.lang.NullPointerException");
    }

    private final Logger logger = mock(Logger.class);
    private final JDBCDatabaseConnectionManager databaseConnectionManager = mock(JDBCDatabaseConnectionManager.class);
    private final Connection connection = mock(Connection.class);
    private final PreparedStatement statement = mock(PreparedStatement.class);
    private final ResultSet resultSet = mock(ResultSet.class);
    private final JDBCStockRepository jdbcStockRepository = new JDBCStockRepository(logger, databaseConnectionManager);
}