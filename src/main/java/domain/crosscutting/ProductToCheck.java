package domain.crosscutting;

import domain.ProductName;

//TODO M001B change name of interface and method from get to by
public interface ProductToCheck {
    // getParameter() method instead
    ProductName getProductName();
}
