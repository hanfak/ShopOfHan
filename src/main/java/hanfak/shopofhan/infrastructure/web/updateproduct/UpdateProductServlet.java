package hanfak.shopofhan.infrastructure.web.updateproduct;

import hanfak.shopofhan.application.updateproduct.UpdateProductUseCase;
import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class UpdateProductServlet extends HttpServlet {
    private final Unmarshaller<Product> unmarshaller;
    private final UpdateProductUseCase updateProductUseCase;


    public UpdateProductServlet(Unmarshaller<Product> unmarshaller, UpdateProductUseCase updateProductUseCase) {
        this.unmarshaller = unmarshaller;
        this.updateProductUseCase = updateProductUseCase;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = unmarshaller.unmarshall(request);
        System.out.println(product);

        updateProductUseCase.changeProductDetails(product);

        response.setStatus(200);
        response.getWriter().write("Product updated");
    }
}
