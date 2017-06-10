package domain;

// Extend valuetype for equals, hashcode etc
public class ProductStock {
    public final String productName;
    public final Integer amountInStock;

    public ProductStock(String productName, Integer amountInStock) {
        this.productName = productName;
        this.amountInStock = amountInStock;
    }
}
