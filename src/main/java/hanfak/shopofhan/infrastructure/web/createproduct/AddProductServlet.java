package hanfak.shopofhan.infrastructure.web.createproduct;

import hanfak.shopofhan.domain.product.Product;
import hanfak.shopofhan.infrastructure.web.RenderedContent;
import hanfak.shopofhan.infrastructure.web.Unmarshaller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static java.lang.String.format;

public class AddProductServlet extends HttpServlet {
    private Unmarshaller<Product> unmarshaller;
    private AddProductWebService addProductWebService;

    public AddProductServlet(Unmarshaller<Product> unmarshaller, AddProductWebService addProductWebService) {
        this.unmarshaller = unmarshaller;
        this.addProductWebService = addProductWebService;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Product product = unmarshaller.unmarshall(request);
        response.getWriter().write(format("New Product added: Product name: %s", product.productName));

//        RenderedContent renderedContent = addProductWebService.addProduct(unmarshaller.product(request));
//        renderedContent.render(response);
    }
}
