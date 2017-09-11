package domain;

import domain.crosscutting.ValueType;

public class Stock extends ValueType {
    // TODO add wrappers for each
    // TODO Change package
    public final Integer amount;
    public final String stockId;
    public final String stockDescription;


    private Stock(Integer amount, String stockId, String stockDescription) {
        this.amount = amount;
        this.stockId = stockId;
        this.stockDescription = stockDescription;
    }

    public static Stock stock(Integer amount, String stockId, String stockDescription) {
        return new Stock(amount, stockId, stockDescription);
    }


}
