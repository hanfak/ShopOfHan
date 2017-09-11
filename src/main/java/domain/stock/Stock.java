package domain.stock;

import domain.crosscutting.ValueType;

public class Stock extends ValueType {
    // TODO add wrappers for each
    // TODO Change package
    public final StockAmount amount;
    public final StockId stockId;
    public final StockDescription stockDescription;


    private Stock(StockAmount amount, StockId stockId, StockDescription stockDescription) {
        this.amount = amount;
        this.stockId = stockId;
        this.stockDescription = stockDescription;
    }

    public static Stock stock(StockAmount amount, StockId stockId, StockDescription stockDescription) {
        return new Stock(amount, stockId, stockDescription);
    }


}
