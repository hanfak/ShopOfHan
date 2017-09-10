package domain;

public class Stock {
    // TODO add wrappers for each
    // TODO Change package
    public final Integer amount;
    public final String StockId;


    private Stock(Integer amount, String stockId) {
        this.amount = amount;
        this.StockId = stockId;
    }

    public static Stock stock(Integer amount, String stockId) {
        return new Stock(amount, stockId);
    }


}
