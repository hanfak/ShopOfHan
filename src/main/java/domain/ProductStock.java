package domain;

// Extend valuetype for equals, hashcode etc
public class ProductStock {
    // Add ProductID, which is acquired from DB
    public final String productName;
    public final Integer amountInStock;
    // static factory method instead??
    public ProductStock(String productName, Integer amountInStock) {
        this.productName = productName;
        this.amountInStock = amountInStock;
    }
}
