package domain.stock;

import domain.crosscutting.SingleValueType;

public class StockId extends SingleValueType<String> {
    private StockId(String value) {
        super(value);
    }

    public static StockId stockId(String amount) {
        return new StockId(amount);
    }
}
