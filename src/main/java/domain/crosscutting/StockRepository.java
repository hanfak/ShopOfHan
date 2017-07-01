package domain.crosscutting;

import java.util.Optional;

public interface StockRepository<T> {
        Optional<T> checkStockByName(String queryParameters);
}
