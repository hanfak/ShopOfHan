package domain.crosscutting;

import java.util.Optional;

public interface StockRepository<T, I> {
        Optional<T> checkStock(I queryParameters);
}
