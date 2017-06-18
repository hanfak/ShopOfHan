package domain.crosscutting;

import java.util.Optional;

public interface StockRepository<T> {
        Optional<T> checkStock(String queryParameters);
}
