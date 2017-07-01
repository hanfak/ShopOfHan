package domain.crosscutting;

import domain.ProductId;
import domain.ProductName;

//TODO M001B change name of interface and method from get to by
public interface ProductToCheck {
    ProductName getProductName();
    ProductId getProductId();
}
