package domain.crosscutting;

import infrastructure.web.productavailability.ProductAvailabilityRequest;

import java.util.Optional;

public interface StockRepository<T> {
    Optional<T> checkStock(ProductAvailabilityRequest request);
}
