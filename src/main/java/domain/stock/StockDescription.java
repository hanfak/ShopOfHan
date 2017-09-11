package domain.stock;

import domain.crosscutting.SingleValueType;

public class StockDescription extends SingleValueType<String> {
    private StockDescription(String value) {
        super(value);
    }

    public static StockDescription stockDescription(String amount) {
        return new StockDescription(amount);
    }
}
