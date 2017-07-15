package application.crosscutting;

import domain.product.ProductId;
import domain.product.ProductName;

//TODO M001B change name of interface and method from get to by
public interface ProductToCheck {
    // getParameter() method instead
    ProductName getProductName();
    ProductId getProductId();
}
